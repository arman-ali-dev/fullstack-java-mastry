# React API Integration — Notes

## 1. Fetching data with useEffect + fetch/axios

React components sirf UI render karte hain — data khud fetch nahi karte. Backend se data lene ke liye `fetch` (browser ka built-in API) ya `axios` (ek popular library) use karte hain, aur ye call `useEffect` ke andar karte hain (kyunki API call ek **side effect** hai — humne ye concept Hooks notes me dekha tha).

```jsx
function UserList() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch("https://api.example.com/users")
      .then((res) => res.json())
      .then((data) => setUsers(data));
  }, []); // [] matlab sirf ek baar chalega, jab component pehli baar render ho

  return (
    <ul>
      {users.map((user) => (
        <li key={user.id}>{user.name}</li>
      ))}
    </ul>
  );
}
```

`fetch` vs `axios` ka farak:

- `fetch` browser me already available hai, kuch install nahi karna padta — lekin thoda manual kaam zyada hai (jaise `res.json()` explicitly call karna padta hai, aur error handling bhi manually karni padti hai — `fetch` 404/500 jaisi errors ko "error" nahi maanta, sirf network fail hone pe hi catch block chalta hai)
- `axios` ek separate library hai (`npm install axios` karna padta hai) — lekin zyada convenient hai: response directly JSON hota hai, errors (4xx/5xx status codes bhi) automatically catch block me chale jaate hain, aur bahut saare useful features milte hain (jinme se ek — interceptors — agle section me dekhenge)

`axios` se same example:

```jsx
useEffect(() => {
  axios.get("https://api.example.com/users").then((res) => setUsers(res.data));
}, []);
```

Industry me `axios` zyada commonly use hota hai bade projects me, especially jab authentication, error handling, ya request customization chahiye ho.

## 2. Axios interceptors: attaching JWT tokens, refresh-token flow, centralized error handling

**Interceptor** ka matlab hai — ek function jo har API request ya response ke "beech me" (before it goes out, ya before it comes back) automatically chal jata hai — bina tumhe har jagah manually wahi code likhna pade.

**Request interceptor** — har request jaane se **pehle** kuch add/modify karta hai. Sabse common use: **JWT token** attach karna. JWT (JSON Web Token) ek tarika hai jisse backend ko pata chalta hai ki request bhejne wala user logged-in aur authenticated hai — login ke baad ye token milta hai aur har request ke saath bhejna padta hai:

```jsx
axios.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

Isse har `axios.get(...)`, `axios.post(...)` call me automatically token attach ho jayega — har component me manually `headers: { Authorization: ... }` likhne ki zaroorat nahi padti.

**Response interceptor** — response aane ke **baad**, component tak pahunchne se pehle, kuch check/handle karta hai. Common use: **centralized error handling** (jaise agar backend `401 Unauthorized` bheje, to user ko automatically logout kar dena):

```jsx
axios.interceptors.response.use(
  (response) => response, // sab theek hai to as-it-is pass kar do
  (error) => {
    if (error.response.status === 401) {
      // token invalid/expired hai, user ko login page pe bhejo
      window.location.href = "/login";
    }
    return Promise.reject(error);
  },
);
```

Isse **har jagah** alag se 401 check karne ki zaroorat nahi — ek hi jagah likha, poore app me apply ho gaya.

**Refresh-token flow** — JWT tokens usually **thodi der ke liye valid** hote hain (jaise 15 minutes), security ke liye. Expire hone ke baad naya token chahiye hota hai — isके liye ek dusra **refresh token** hota hai (jo zyada der tak valid rehta hai) jisse naya access token maang sakte hain, bina user ko dobara login karwaye.

Response interceptor me ye flow aise handle hota hai:

```jsx
axios.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response.status === 401) {
      // naya token mangao refresh token se
      const newToken = await refreshAccessToken();
      localStorage.setItem("token", newToken);
      // jo request fail hui thi, usko naye token ke saath dobara try karo
      error.config.headers.Authorization = `Bearer ${newToken}`;
      return axios(error.config);
    }
    return Promise.reject(error);
  },
);
```

Isse user ko experience hota hai ki wo "kabhi logout hua hi nahi" — background me token silently refresh hota rehta hai jab tak refresh token khud expire na ho jaye.

## 3. Introduction to React Query (TanStack Query): caching, refetching, mutations

Jab data `useEffect` + `useState` se manually fetch karte hain, kuch cheezein tumhe khud handle karni padti hain: loading state, error state, agar wahi data doosre component me bhi chahiye to dobara fetch karna, data purana ho gaya hai to refresh karna, wagera. **React Query** (jiska naya naam **TanStack Query** hai) ye sab automatically handle kar deta hai.

Basic usage:

```jsx
import { useQuery } from "@tanstack/react-query";

function UserList() {
  const { data, isLoading, isError } = useQuery({
    queryKey: ["users"],
    queryFn: () => axios.get("/users").then((res) => res.data),
  });

  if (isLoading) return <Spinner />;
  if (isError) return <p>Something went wrong</p>;

  return (
    <ul>
      {data.map((user) => (
        <li key={user.id}>{user.name}</li>
      ))}
    </ul>
  );
}
```

- `queryKey` — ek unique identifier is data ke liye (jaise `["users"]`). React Query isse use karta hai data ko **cache** karne ke liye
- `queryFn` — actual function jo data fetch karta hai
- `isLoading`, `isError`, `data` — React Query khud track karta hai, tumhe manually `useState` banane ki zaroorat nahi

**Caching** — agar same `queryKey` (`["users"]`) wala data pehle se fetch ho chuka hai, aur koi doosra component bhi yehi data maangta hai, to React Query **dobara API call nahi karta** — cache se hi data de deta hai (jab tak wo "stale" — purana — na ho jaye). Isse app fast lagta hai aur unnecessary network calls bachte hain.

**Refetching** — React Query kuch situations me khud-ba-khud data **dobara fetch** kar leta hai — jaise jab user tab switch karke wapas app pe aaye (**window refocus**), ya internet connection wapas aaye. Ye automatic hai, but configure bhi kar sakte ho (kab refetch ho, kab na ho).

**Mutations** — jab data **fetch** nahi balki **change** karna ho (create, update, delete — matlab POST/PUT/DELETE requests), tab `useMutation` use karte hain:

```jsx
import { useMutation, useQueryClient } from "@tanstack/react-query";

function AddUserForm() {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (newUser) => axios.post("/users", newUser),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["users"] }); // list ko refresh karo
    },
  });

  return (
    <button onClick={() => mutation.mutate({ name: "Jagir" })}>Add User</button>
  );
}
```

`invalidateQueries` React Query ko batata hai "ye cached data ab purana ho gaya hai, agli baar zaroorat pade to fresh fetch karo" — isse naya user add hone ke baad list automatically update ho jaati hai, bina manually state manage kiye.

React Query, `useEffect` + manual state se bahut zyada powerful aur less error-prone hai — isi liye bade production apps me ye almost default choice ban gaya hai data fetching ke liye.

## 4. Handling loading and error states

Chahe manual `useEffect` approach ho ya React Query — **loading** aur **error** states handle karna UX (user experience) ke liye zaroori hai, taaki user ko pata chale kya ho raha hai (data aa raha hai, ya kuch galat hua).

Manual approach me teeno states khud track karte hain:

```jsx
function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("/users")
      .then((res) => setUsers(res.data))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <Spinner />;
  if (error) return <p>Error: {error}</p>;
  return (
    <ul>
      {users.map((u) => (
        <li key={u.id}>{u.name}</li>
      ))}
    </ul>
  );
}
```

- `.finally()` — chahe request success ho ya fail, ye hamesha chalta hai — isliye `loading` ko `false` karne ke liye best jagah hai
- Teeno states (`loading`, `error`, `data`) ko explicitly check karke UI decide karte hain kya dikhana hai

React Query me ye kaafi simpler hai kyunki `isLoading`, `isError`, `data` already provided hote hain (jaisa upar dikhaya), manually 3 alag `useState` banane ki zaroorat nahi padti.

Best practice: hamesha teeno states handle karo — sirf "success" case ke liye UI likh ke chhod dena bad UX deta hai (user ko lagega app "hang" ho gaya, ya kuch bhi nahi dikhega agar error aaye).

## 5. API service layer: organizing API calls into a reusable layer

Jaise-jaise app bada hota hai, agar har component apne andar directly `axios.get("/users")` jaisi calls likhta hai, to problems aati hain:

- Same URL/logic multiple jagah repeat hota hai
- Agar API ka URL ya structure change ho, to har component me jaake update karna padta hai
- Components ka code messy ho jata hai — UI logic aur API call logic mix ho jate hain

Isका solution hai — ek **API service layer** banana, matlab saari API calls ek separate jagah (alag files/folder) me define karna, aur components sirf un functions ko **call** karein:

```
src/
  api/
    axiosInstance.js   // axios ka common config (baseURL, interceptors)
    userApi.js          // user-related API calls
    productApi.js        // product-related API calls
```

`axiosInstance.js`:

```jsx
import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "https://api.example.com",
});

export default axiosInstance;
```

`userApi.js`:

```jsx
import axiosInstance from "./axiosInstance";

export const getUsers = () =>
  axiosInstance.get("/users").then((res) => res.data);
export const getUserById = (id) =>
  axiosInstance.get(`/users/${id}`).then((res) => res.data);
export const createUser = (data) =>
  axiosInstance.post("/users", data).then((res) => res.data);
```

Ab component me sirf ye functions import karke use karte hain:

```jsx
import { getUsers } from "../api/userApi";

useEffect(() => {
  getUsers().then(setUsers);
}, []);
```

(Ya isi function ko React Query ke `queryFn` me bhi directly use kar sakte ho — `queryFn: getUsers`.)

Fayde:

- Component ko API ki "internal details" (URL, method, headers) pata hi nahi honi chahiye — sirf function call karta hai aur data milta hai
- API URL/structure change ho to sirf `userApi.js` update karna padta hai, components touch nahi karne padte
- Testing aasan hoti hai — API functions ko mock karna easy hai component se alag hone ki wajah se
- Ye pattern Component Design notes wale **Container/Presentational** idea se bhi jud jata hai — API service layer "data logic" hai, components "UI logic" hai, dono clean separate rehte hain

Real, growing projects me ye pattern almost hamesha use hota hai — chhote projects/learning ke liye directly component me call karna bhi chalega, lekin professional/production code me service layer standard practice hai.
