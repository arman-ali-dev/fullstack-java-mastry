# React Hooks — Notes

**Hooks** kya hote hain — ye special functions hain jo React ne diye hain taaki functional components me bhi state, side effects, aur baaki React features use kar sako (jo pehle sirf class components me possible tha). Sab hooks ka naam `use` se start hota hai — `useState`, `useEffect`, wagera. Ye React version 16.8 me introduce hue the.

Ek important rule: Hooks ko hamesha component ke **top level** pe call karna hota hai — kisi `if`, loop, ya nested function ke andar nahi. Wajah: React internally hooks ko unke **call order** se track karta hai (kaunsa hook pehle call hua, kaunsa doosra). Agar condition ke hisaab se koi hook kabhi call ho aur kabhi na ho, to ye order har render pe alag ho sakta hai, aur React confuse ho jayega ki kaunsi state kis hook ki hai — isse bugs aate hain.

## 1. useState — managing local state

**State** matlab component ka apna data jo time ke saath change ho sakta hai (jaise counter ki value, form ka input, koi toggle on/off). Jab state change hoti hai, React us component ko **re-render** karta hai (matlab dobara render karke UI update karta hai).

```jsx
const [count, setCount] = useState(0);
```

- `useState(0)` — `0` yahan **initial value** hai, matlab pehli baar component render hone pe `count` ki value `0` hogi
- Return hota hai ek array jisme 2 cheezein hoti hain: current value (`count`) aur ek function (`setCount`) jo us value ko update karne ke liye use hota hai
- `setCount(5)` call karne se React ko pata chal jata hai ki state change hui hai, aur wo component ko re-render kar deta hai naye value ke saath

Ek zaroori baat: state ko **directly modify nahi karte** (jaise `count = 5` nahi likhte) — hamesha `setCount` function hi use karna hota hai. Agar direct modify karoge, React ko pata hi nahi chalega ki kuch update hua hai, aur UI purani hi dikhti rahegi.

Agar naya value purani value pe depend karta hai (jaise counter +1 karna), to **function form** use karna safe hota hai:

```jsx
setCount(prevCount => prevCount + 1);
```

Isse React guarantee deta hai ki tumhe hamesha latest value milegi, chahe multiple updates ek saath ho rahe ho.

## 2. useEffect — side effects, dependency arrays, cleanup functions

**Side effect** ka matlab hai koi bhi aisa kaam jo component ke render hone ke "bahar" ka hai — jaise API call karna, browser ka title change karna, timer chalana, ya kisi external system se connect hona. Normal render logic sirf UI decide karta hai, lekin side effects alag se handle karne padte hain — isi ke liye `useEffect` hai:

```jsx
useEffect(() => {
  console.log("component render hua ya update hua");
}, [count]);
```

- Pehla argument ek function hai — ye woh code hai jo side effect perform karega
- Doosra argument ek array hai, jisse **dependency array** kehte hain — ye batata hai ki useEffect kab dobara chalega

Dependency array ke 3 possible cases:
- `[]` (khali array) — effect sirf **ek baar** chalega, jab component pehli baar screen pe aaye (isko **mount** hona kehte hain)
- `[count]` — effect tab tab chalega jab bhi `count` ki value change hogi
- Kuch bhi nahi diya (array hi nahi diya) — effect **har render** pe chalega (generally isse avoid karte hain, performance issue ho sakta hai)

**Cleanup function** — agar tumhara effect kuch aisa start karta hai jo band bhi karna padta hai (jaise timer, event listener, ya subscription), to `useEffect` ke andar wale function se ek doosra function return kar sakte ho — wahi cleanup function hai:

```jsx
useEffect(() => {
  const timer = setInterval(() => console.log("tick"), 1000);
  return () => clearInterval(timer); // cleanup
}, []);
```

Ye cleanup function tab chalta hai jab component screen se hatne wala ho (**unmount**), ya agle effect run hone se pehle (agar dependency change hui ho). Cleanup na karo to memory leak ya duplicate timers/listeners jaisi problems ho sakti hain.

## 3. useContext — consuming context without prop drilling

**Prop drilling** ek problem hai jo tab hoti hai jab tumhe koi data deep nested component tak pahunchana ho, aur beech me aane wale saare components se wo data as prop pass karna pade — chahe unhe khud us data ki zaroorat na ho, sirf aage forward karne ke liye:

```
App → Layout → Sidebar → Menu → MenuItem (jisko actual data chahiye)
```

Isme Layout, Sidebar, Menu — teeno ko sirf pass-through ke liye props leni padegi, jo messy ho jata hai.

**Context** iska solution hai — ek global data store jo koi bhi nested component directly access kar sakta hai, bina beech ke components se pass kiye.

Setup 2 steps me hota hai:

```jsx
// 1. Context banao
const ThemeContext = createContext();

// 2. Provider se data available karao
<ThemeContext.Provider value="dark">
  <App />
</ThemeContext.Provider>
```

Ab koi bhi nested component, chahe kitni bhi deep ho, `useContext` se directly value nikal sakta hai:

```jsx
const theme = useContext(ThemeContext);
```

Beech ke `Layout`, `Sidebar` components ko is data ke baare me kuch pata hone ki zaroorat nahi — wo bas normal render karte rehte hain.

Common use cases: theme (dark/light mode), logged-in user info, language/locale settings.

## 4. useRef — DOM references and mutable values

`useRef` do kaam ke liye use hota hai:

**(a) DOM element ko directly access karna** — jaise kisi input box pe focus karna, ya kisi element ki height/width nikalna:

```jsx
const inputRef = useRef(null);

useEffect(() => {
  inputRef.current.focus(); // input pe cursor le jao
}, []);

return <input ref={inputRef} />;
```

`ref={inputRef}` likhne se React us actual DOM element ko `inputRef.current` me store kar deta hai — ab tum us element pe direct JS methods (`.focus()`, `.scrollIntoView()`, etc.) call kar sakte ho.

**(b) Aisi value store karna jo change to ho sakti hai, lekin uske change hone pe re-render nahi chahiye:**

```jsx
const renderCount = useRef(0);
renderCount.current += 1;
```

Yahan `useState` se farak ye hai — `useState` ki value change hone pe component re-render hota hai, lekin `useRef` ki `.current` value change hone pe **koi re-render nahi hota**. Isliye ye un cheezon ke liye useful hai jo "yaad" rakhni hain but UI pe directly nahi dikhani (jaise previous value track karna, timer ID store karna).

## 5. useMemo and useCallback — memoization for performance

**Memoization** ka matlab hai — kisi calculation ka result "yaad" rakh lena, taaki agli baar same input aane pe dobara calculate na karna pade, bas purana saved result use kar lena.

**useMemo** — kisi **value/calculation** ko memoize karta hai:

```jsx
const expensiveResult = useMemo(() => {
  return heavyCalculation(data);
}, [data]);
```

`heavyCalculation(data)` sirf tab dobara chalega jab `data` change hoga. Agar component kisi aur reason se re-render ho raha hai (aur `data` same hai), to purana saved result hi use ho jayega, calculation dobara nahi chalegi.

**useCallback** — kisi **function** ko memoize karta hai:

```jsx
const handleClick = useCallback(() => {
  doSomething(id);
}, [id]);
```

Normally, jab bhi component re-render hota hai, uske andar defined saare functions **naye sire se ban jaate hain** (JavaScript me function bhi ek value hai, aur har render pe naya function object create hota hai — chahe code same ho). Ye tab problem banta hai jab tum wo function kisi child component ko prop ki tarah pass kar rahe ho, kyunki child ko lagega "prop badal gaya" (kyunki technically ek naya function object hai), aur wo bhi unnecessarily re-render ho jayega. `useCallback` isko rokta hai — same function reference tab tak reuse hoti hai jab tak dependency (`id`) change na ho.

Important: `useMemo`/`useCallback` **har jagah** use karna zaroori nahi — inka khud ka bhi thoda overhead hota hai. Inka use tab karo jab:
- Calculation genuinely heavy ho (jaise bada data process karna)
- Function ko child component me pass kar rahe ho jo `React.memo` se wrapped hai (taaki unnecessary re-render na ho)

## 6. useReducer — managing complex state logic

`useState` chote, simple state ke liye theek hai. Lekin jab state complex ho jaye — multiple related values, ya state update karne ka logic complicated ho (jaise ek form jisme multiple fields, validation, aur alag-alag actions hain) — tab `useReducer` better structure deta hai.

Ye Redux jaisa pattern hai: ek **reducer function** hota hai jo current state aur ek **action** leke naya state return karta hai:

```jsx
function reducer(state, action) {
  switch (action.type) {
    case "increment":
      return { count: state.count + 1 };
    case "decrement":
      return { count: state.count - 1 };
    default:
      return state;
  }
}

const [state, dispatch] = useReducer(reducer, { count: 0 });

dispatch({ type: "increment" }); // state update trigger karta hai
```

- `reducer` — function jo batata hai ki har action pe state kaise change hogi
- `dispatch` — function jo action "bhejta" hai reducer ko
- `action` — ek object jo batata hai konsa update karna hai (usually `{ type: "..." }` format me)

`useReducer` use karne ka fayda tab dikhta hai jab state update logic bahut jagah scattered ho jata — ek reducer me sara logic ek jagah collect ho jata hai, aur components sirf `dispatch` karte hain, actual logic ki detail unhe pata nahi honi chahiye.

## 7. Custom hooks — extracting reusable logic

**Custom hook** basically ek normal function hai jiska naam `use` se start hota hai, aur jiske andar tum built-in hooks (`useState`, `useEffect`, etc.) use kar sakte ho. Iska purpose hai — koi bhi logic jo multiple components me repeat ho raha hai, usko ek jagah nikal ke reusable bana dena.

Example — window ki width track karna, jo shayad kai components me chahiye:

```jsx
function useWindowWidth() {
  const [width, setWidth] = useState(window.innerWidth);

  useEffect(() => {
    const handleResize = () => setWidth(window.innerWidth);
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return width;
}
```

Ab koi bhi component ye custom hook use kar sakta hai:

```jsx
function MyComponent() {
  const width = useWindowWidth();
  return <p>Window width: {width}</p>;
}
```

Poora logic (state + effect + cleanup) `useWindowWidth` ke andar hi ek baar likha gaya, aur jitne bhi components ko chahiye, wo sirf ek line me use kar sakte hain — code duplicate nahi karna padta.

Zaroori baat: custom hook internally apna alag state maintain karta hai har component ke liye — matlab agar 2 components `useWindowWidth()` use kar rahe hain, dono ki apni-apni independent state hogi, ek doosre se share nahi hogi.
