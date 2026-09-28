# React State Management — Notes

Pehle ek cheez clear kar lete hain — **state management** ka matlab hai app ka data (jaise logged-in user, cart items, theme setting) kaha store hoga aur kaise components tak pahunchega. Chote apps me `useState` hi kaafi hota hai, lekin jaise app bada hota hai aur bahut saare components ko same data chahiye hota hai, tab dedicated "state management" tools/patterns kaam aate hain.

## 1. Context API for global state

Ye humne Hooks wali notes me `useContext` ke saath thoda cover kiya tha — yahan thoda aur detail me:

**Context API** React ka apna built-in tarika hai kisi data ko **globally** (poore app me kahin se bhi) available karane ka, bina har component ko manually props pass kiye (jise **prop drilling** kehte hain — humne pehle explain kiya tha).

```jsx
const ThemeContext = createContext();

function App() {
  const [theme, setTheme] = useState("dark");

  return (
    <ThemeContext.Provider value={{ theme, setTheme }}>
      <Dashboard />
    </ThemeContext.Provider>
  );
}
```

Ab `Dashboard` ke andar kahin bhi, chahe kitni bhi deep nesting ho:

```jsx
const { theme, setTheme } = useContext(ThemeContext);
```

Context "global state" ke liye kaam to karta hai, lekin iski kuch **limitations** hain jo bade apps me problem banti hain:

- Jab bhi `Provider` ki `value` change hoti hai, **saare components jo us Context ko use kar rahe hain, re-render hote hain** — chahe unhe sirf ek chota part chahiye ho us data ka. Isse performance issue ho sakta hai bade apps me
- Context me koi built-in tarika nahi hai state update karne ka "structured" logic likhne ka (jaisa Redux me reducer hota hai) — tum khud manage karte ho ki state kaise update hogi
- Multiple unrelated global states ke liye multiple Contexts banane padte hain, jo thoda messy ho sakta hai

Isliye Context API chote-medium apps ke liye (jaise theme, auth status, language) theek hai, lekin bade complex apps me (jaise ek e-commerce app jisme cart, user, filters, notifications sab global state hai) log dedicated libraries use karte hain — jaise **Redux Toolkit** ya **Zustand**.

## 2. Introduction to Redux Toolkit: store, slices, actions, reducers

**Redux** ek popular state management library hai jo bahut saal se React ke saath use ho rahi hai. **Redux Toolkit (RTK)** Redux ka hi official, modern version hai — purana Redux likhne me bahut boilerplate (extra repetitive code) lagta tha, RTK usko kaafi simplify kar deta hai. Aaj kal jab log "Redux" bolte hain, mostly Redux Toolkit hi matlab hota hai.

Redux ka core idea: **poore app ka global state ek hi jagah (single source of truth) store hota hai**, aur usko update karne ka ek strict, predictable tarika hota hai — direct mutation allowed nahi hai.

**Store** — wo jagah jahan poora global state store hota hai. Pure app me ek hi store hota hai:

```jsx
import { configureStore } from "@reduxjs/toolkit";

const store = configureStore({
  reducer: {
    cart: cartReducer,
    user: userReducer,
  },
});
```

**Slice** — Redux Toolkit ka concept hai jisme ek particular feature/domain (jaise "cart") ka state, uske update karne wale functions, sab ek jagah define kiya jata hai:

```jsx
import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: { items: [] },
  reducers: {
    addItem: (state, action) => {
      state.items.push(action.payload); // RTK me direct "mutate" karna dikhta hai, safe hai
    },
    removeItem: (state, action) => {
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
  },
});

export const { addItem, removeItem } = cartSlice.actions;
export default cartSlice.reducer;
```

(Note: Upar `state.items.push(...)` dikh raha hai jaise direct state modify ho raha hai — lekin RTK internally ek library `Immer` use karta hai jo peeche se safe, immutable update me convert kar deta hai. Isliye likhna easy lagta hai, lekin actual me Redux ka rule (state ko directly mutate mat karo) tootta nahi hai.)

**Actions** — ye batate hain "kya karna hai". Upar wale `addItem`, `removeItem` khud **action creators** hain — inko call karne se ek action object banta hai jaisे `{ type: "cart/addItem", payload: item }`.

**Reducer** — wo function jo current state aur ek action leke naya state return karta hai (humne ye concept `useReducer` me bhi dekha tha — Redux ka `useReducer` isi idea pe based hai, bas bahut bada scale pe).

Component me use karne ke liye:

```jsx
import { useSelector, useDispatch } from "react-redux";

function Cart() {
  const items = useSelector((state) => state.cart.items); // state read karna
  const dispatch = useDispatch();

  return (
    <button onClick={() => dispatch(addItem({ id: 1, name: "Shoes" }))}>
      Add to Cart
    </button>
  );
}
```

- `useSelector` — store se data **read** karne ke liye
- `dispatch` — koi action **trigger** karne ke liye (jo reducer ke through state update karega)

Redux ka fayda bade apps me: state kaha se aa raha hai aur kaise change hota hai — sab predictable aur trace-able hota hai (Redux DevTools se pura history bhi dekh sakte ho ki kaunsa action kab fire hua).

## 3. Zustand as a lightweight alternative

**Zustand** ek naya, simpler state management library hai jo Redux jitna boilerplate nahi maangti. "Zustand" German word hai jiska matlab hota hai "state".

Redux me store, slice, actions, reducers, Provider setup — sab alag-alag likhna padta hai. Zustand me ek hi jagah sab define ho jata hai:

```jsx
import { create } from "zustand";

const useCartStore = create((set) => ({
  items: [],
  addItem: (item) => set((state) => ({ items: [...state.items, item] })),
  removeItem: (id) =>
    set((state) => ({
      items: state.items.filter((item) => item.id !== id),
    })),
}));
```

Component me use karna:

```jsx
function Cart() {
  const items = useCartStore((state) => state.items);
  const addItem = useCartStore((state) => state.addItem);

  return (
    <button onClick={() => addItem({ id: 1, name: "Shoes" })}>
      Add to Cart
    </button>
  );
}
```

Dhyan do — koi `Provider` wrap karne ki zaroorat nahi (Context/Redux dono me Provider chahiye hota hai), aur syntax bhi kaafi kam hai. Zustand internally bhi smart hai — sirf wahi component re-render hota hai jo actually use ki hui state change hoti hai, poora tree nahi (jo Context API ki limitation thi jo humne upar dekhi).

Zustand kab use karo Redux ke bajaye:

- Jab tumhe global state chahiye but Redux ka poora setup/boilerplate overkill lagta hai
- Chote-medium size apps, ya startups jaha development speed zyada priority hai
- Jab team Redux ke concepts (actions, reducers, dispatch) se unfamiliar ho aur simpler API chahiye

Redux abhi bhi prefer kiya jata hai bahut badi, enterprise-scale apps me jaha strict structure, predictability, aur powerful devtools (time-travel debugging jaisa) chahiye hota hai.

## 4. When to reach for a state management library vs local state

Ye sabse important decision hai — **har cheez ke liye Redux/Zustand use karna zaroori nahi**, zyada state management add karna bhi unnecessary complexity la sakta hai. Kuch guidelines:

**Local state (`useState`/`useReducer`) kaafi hai jab:**

- Data sirf **ek component** (ya uske direct children) tak relevant hai — jaise ek form ka input value, ek dropdown open/closed hai ya nahi, ek modal dikhana hai ya nahi
- Data **share nahi karna** padta kisi unrelated component ke saath

**Context API use karo jab:**

- Data poore app me chahiye, lekin **kam frequently change** hota hai (jaise theme, language, logged-in user info) — kyunki Context har change pe consuming components ko re-render karta hai, to frequently-changing data ke liye ye best nahi hai
- App zyada bada nahi hai, aur simple solution chahiye without extra library install kiye

**Dedicated library (Redux Toolkit ya Zustand) use karo jab:**

- App bada hai, aur **bahut saara global state** hai jo frequently update hota hai (jaise cart, real-time notifications, complex filters)
- Multiple unrelated components ko same data read/update karna padta hai
- Debugging/predictability important hai — kaun sa action kab fire hua, state kaise change hui, ye trace karna zaroori hai
- Performance matter karta hai — Context ke unnecessary re-renders avoid karne hain

Simple rule of thumb jo bahut log follow karte hain: **"State ko jitna neeche rakh sako utna neeche rakho, jitna upar/global le jana zaroori ho utna hi le jao"** — matlab pehle local state try karo, agar sharing ki zaroorat pade to lifting state up (jo humne Component Design notes me dekha tha) ya Context try karo, aur sirf tab dedicated library lao jab genuinely complex, large-scale global state ho.
