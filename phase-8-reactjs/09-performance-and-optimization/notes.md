# React Performance and Optimization — Notes

## 1. React.memo for component memoization

Humne **memoization** ka concept Hooks notes me `useMemo`/`useCallback` ke saath dekha tha — "result ko yaad rakh lena taaki dobara calculate na karna pade". `React.memo` bhi wahi idea hai, lekin **poore component** pe apply hota hai (function/value ke bajaye).

Pehle samjho normal behavior kya hai: jab bhi ek **parent** component re-render hota hai, uske **saare children bhi re-render hote hain by default** — chahe un children ke props change hue ho ya na hue ho. Ye zyadatar time koi problem nahi hai (React bahut fast hai), lekin agar koi child component **heavy** hai (jaise bahut saara data render kar raha ho, ya complex calculations kar raha ho), to ye unnecessary re-render performance issue bana sakta hai.

`React.memo` ek component ko wrap karke usse batata hai: "agar tumhare props pichli baar jaise hi hain (same), to dobara render mat ho, purana result hi use kar lo":

```jsx
const UserCard = React.memo(function UserCard({ name, email }) {
  console.log("UserCard rendered");
  return (
    <div>
      <h3>{name}</h3>
      <p>{email}</p>
    </div>
  );
});
```

Ab agar `UserCard` ka parent re-render hota hai, lekin `name` aur `email` props same hain (koi change nahi hua), to React `UserCard` ko dobara render nahi karega — bas purana rendered output reuse kar lega.

`React.memo` internally props ko **shallow comparison** se check karta hai — matlab simple values (string, number, boolean) ke liye ye theek se kaam karta hai, lekin agar prop ek **object ya function** hai, to har render pe naya object/function ban jata hai (JavaScript me ye normal hai), aur shallow comparison ko lagega "prop badal gaya" — chahe andar ka data same ho. Isi problem ko solve karne ke liye `useCallback` (functions ke liye) aur `useMemo` (objects/arrays ke liye) use karte hain — jo humne Hooks notes me dekha tha — taaki `React.memo` sahi se kaam kare.

Kab use karo `React.memo`:

- Jab component **heavy** ho (complex UI, bahut data)
- Jab component **frequently re-render** hone wale parent ke andar ho, lekin uske apne props kam badalte ho
- **Har jagah use karna zaroori nahi** — chote, simple components pe `React.memo` lagana ulta thoda overhead add kar sakta hai (kyunki comparison karna bhi ek kaam hai). Pehle actual performance problem identify karo (React DevTools Profiler se), phir target karke `React.memo` lagao.

## 2. Code splitting with React.lazy and Suspense

Normally jab tum React app build karte ho, saara JavaScript code ek (ya bahut kam) badi file(s) me bundle ho jata hai. User jab app pehli baar open karta hai, to browser ko ye **poori file download** karni padti hai — chahe user ko us waqt sirf ek page (jaise Home page) hi dikhna ho, baaki sab pages ka code bhi already download ho chuka hota hai, jo slow ho sakta hai especially bade apps me.

**Code splitting** iska solution hai — poore app ka code ek hi file me daalne ke bajaye, usse **chote-chote pieces** me tod dete hain, aur har piece sirf **tab download hota hai jab uski zaroorat pade** (jaise jab user us particular page pe navigate kare).

`React.lazy` isko implement karne ka tarika hai:

```jsx
import { lazy } from "react";

const Dashboard = lazy(() => import("./Dashboard"));
```

`import("./Dashboard")` (function ki tarah call karna, normal `import Dashboard from "./Dashboard"` ke bajaye) ek **dynamic import** hai — ye Dashboard ka code turant load nahi karta, balki ek alag chote file (**chunk**) me rakh deta hai jo sirf tab download hoga jab ye component actually render karne ki koshish ki jaye.

Lekin problem — jab tak wo chunk download ho raha hai (thoda time lagta hai, chahe kitna bhi kam ho), React ko pata nahi ki tab tak kya dikhaye. Isi ke liye `Suspense` chahiye:

```jsx
import { Suspense } from "react";

<Suspense fallback={<Spinner />}>
  <Dashboard />
</Suspense>;
```

`Suspense` ek wrapper hai jo batata hai: "jab tak andar wala component (jo lazy-loaded hai) ready nahi hota, tab tak ye `fallback` UI dikhao". Jaise hi `Dashboard` ka code download ho jata hai, `Suspense` khud-ba-khud `fallback` ko hata ke actual `Dashboard` dikha deta hai.

Isko **Routing** ke saath combine karna bahut common hai — har route ka component alag chunk ban jaye:

```jsx
const Home = lazy(() => import("./Home"));
const Dashboard = lazy(() => import("./Dashboard"));

<Suspense fallback={<Spinner />}>
  <Routes>
    <Route path="/" element={<Home />} />
    <Route path="/dashboard" element={<Dashboard />} />
  </Routes>
</Suspense>;
```

Isse jab user sirf Home page pe hai, `Dashboard` ka code download hi nahi hota jab tak wo `/dashboard` pe navigate na kare. Isse initial load fast hota hai — user ko jaldi kuch dikh jata hai, baaki cheezein background me/zaroorat pe load hoti hain.

## 3. Avoiding unnecessary re-renders

Ye ek broad topic hai jisme upar wali dono techniques (memo, code splitting) bhi contribute karti hain, lekin kuch aur common practices bhi hain jo re-renders kam karne me help karti hain:

**(a) State ko sahi jagah rakho (state colocation)** — agar koi state sirf ek chote se part me use ho rahi hai, use waha tak hi rakho, poore app ke top-level pe mat rakho. Agar tum state ko unnecessarily upar (jaise `App` component me) rakhte ho jabki sirf ek chota nested component use karta hai, to jab bhi wo state change hogi, **poora tree neeche tak re-render hoga** — chahe baaki components ko us data se koi matlab na ho.

```jsx
// Bad — poora App re-render hoga jab search text change ho
function App() {
  const [search, setSearch] = useState("");
  return (
    <>
      <SearchBox value={search} onChange={setSearch} />
      <Header />
      <Footer />
    </>
  );
}

// Better — sirf SearchBox ka apna internal state, baaki app untouched
function App() {
  return (
    <>
      <SearchBox />
      <Header />
      <Footer />
    </>
  );
}
```

**(b) `children` prop ka smart use** — agar koi component sirf ek wrapper hai aur uska apna state change ho raha hai, to `children` ke through pass kiya gaya content re-render nahi hota (kyunki `children` already ek "ready-made" React element hai, wo dobara nahi banta):

```jsx
function Wrapper({ children }) {
  const [count, setCount] = useState(0);
  return (
    <div>
      <button onClick={() => setCount(count + 1)}>{count}</button>
      {children} {/* ye re-render nahi hoga jab count change ho */}
    </div>
  );
}
```

**(c) Keys sahi se use karo** — humne Rendering Lists notes me dekha tha ki galat `key` (jaise array index jab list reorder ho) se React confuse ho jata hai aur galat elements update/reuse karta hai — jo bugs aur extra re-renders dono ki wajah ban sakta hai.

**(d) Objects/arrays/functions ko render ke andar naya mat banao agar avoid ho sake** — jaise `<Component style={{ color: "red" }} />` — ye har render pe ek **naya object** banata hai (`{ color: "red" }`), chahe value same ho. Agar `Component` ko `React.memo` se wrap kiya hai, to ye naya object shallow comparison ko fail kar dega aur unnecessary re-render hoga. Isko fix karne ke liye style/object ko component ke bahar define karo, ya `useMemo` use karo.

**(e) Performance problem ko measure karo, guess mat karo** — sabse important practice ye hai ki `React.memo`, `useMemo`, `useCallback` jaisi cheezein "just in case" har jagah mat lagao. Pehle **React DevTools ka Profiler tab** use karke dekho ki actually kaunsa component slow hai ya bahut zyada re-render ho raha hai, phir specifically usी jagah optimize karo. Bina measure kiye optimization karna code ko unnecessarily complex bana deta hai, bina real benefit ke.

Overall idea: React zyadatar cases me already fast hai — inn techniques ki zaroorat tab padti hai jab app bada ho jaye, ya specific heavy components ho. Chote-medium apps me in sabki chinta karne se pehle pehle app ko sahi se banao, phir jab actual slowness mehsoos ho, tab profile karke targeted optimization karo.
