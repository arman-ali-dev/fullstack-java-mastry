# React Error Handling — Notes

## 1. Error Boundaries: catching render-time errors in the component tree, fallback UI

Normally, agar React ke kisi component ke **render karte waqt** (matlab JSX return karte waqt) koi JavaScript error aa jaye (jaise `undefined` property ko access karna, ya kisi function ka crash ho jana), to **poora app white screen ho jata hai** — React us error ko silently ignore nahi karta, balki poora component tree unmount kar deta hai. Ye bahut bura user experience hai — ek chote se bug ki wajah se poora app crash dikh jata hai.

**Error Boundary** ek special component hai jo iska solution deta hai — ye apne **andar wale (child) components** me aane wali render-time errors ko **pakad (catch)** leta hai, aur poora app crash hone ke bajaye sirf ek **fallback UI** (jaise "Something went wrong" message) dikha deta hai, baaki app normal chalta rehta hai.

Important: Error Boundary **sirf class component** ki tarah likha ja sakta hai — abhi tak koi hook version (`useErrorBoundary` jaisa) React me officially nahi hai. Ye ek exception hai us general rule se ki "naya code functional components me likhte hain" — Error Boundaries ke liye class component hi use karna padta hai.

```jsx
class ErrorBoundary extends React.Component {
  state = { hasError: false };

  static getDerivedStateFromError(error) {
    // agla render pe fallback UI dikhane ke liye state update karo
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    // error ko log karne ke liye (jaise Sentry jaisi service me bhejna)
    console.log(error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return <h2>Something went wrong.</h2>; // fallback UI
    }
    return this.props.children; // normal case, children render karo
  }
}
```

- `getDerivedStateFromError` — jab bhi child me koi error aati hai, React ye method call karta hai, aur jo state ye return karta hai, wahi component ki nayi state ban jaati hai. Isi state ke basis pe hum decide karte hain fallback UI dikhana hai ya nahi
- `componentDidCatch` — error ka detail (kya error hai, kaunse component me hui) yahan milta hai, jisse tum error ko log kar sakte ho (jaise kisi error-tracking tool me bhej ke), taaki baad me debug kar sako

Use karne ka tarika — poore app ko, ya kisi specific "risky" part ko, `ErrorBoundary` se wrap kar dete hain:

```jsx
<ErrorBoundary>
  <Dashboard />
</ErrorBoundary>
```

Ab agar `Dashboard` ke andar kahin bhi (kitna bhi deep nested ho) koi render-time error aaye, to poora app crash nahi hoga — sirf `ErrorBoundary` apna fallback UI dikha dega, uske bahar ka baaki app (jaise Navbar, Sidebar) normal chalta rahega.

Tum multiple, chote-chote Error Boundaries bhi use kar sakte ho alag-alag sections ke liye — taaki agar ek section crash ho, to sirf wahi section fallback dikhaye, poora page nahi.

## 2. Difference between Error Boundaries and try-catch in async code

Ye samajhna zaroori hai kyunki dono "errors handle karna" ke liye hain, lekin **bilkul alag situations** ke liye kaam karte hain — ek doosre ka substitute nahi hain.

**Error Boundary kya catch karta hai:**

- Sirf **render ke dauraan** hone wali errors — matlab jab component apna JSX bana raha ho
- Sirf **synchronous** code me hone wali errors (jo turant, bina wait kiye, chalti hain)

**Error Boundary kya NAHI catch karta** (ye important hai):

- **Event handlers** ke andar hone wali errors (jaise `onClick` ke andar agar error aaye) — ye catch nahi hoti Error Boundary se
- **Asynchronous code** — jaise `setTimeout`, Promises, `async/await`, ya API calls ke andar agar error aaye
- Khud Error Boundary component ke **apne** render me error (obviously, kyunki wahi to error catch kar raha hai)
- Server-side rendering (SSR) ke errors

Iska matlab: agar tumhara `useEffect` ke andar ek API call fail hoti hai (jaise humne API Integration notes me dekha), to **Error Boundary usse catch nahi karega**. Uske liye tumhe normal **try-catch** (ya `.catch()`) use karna padta hai:

```jsx
useEffect(() => {
  const fetchData = async () => {
    try {
      const res = await axios.get("/users");
      setUsers(res.data);
    } catch (error) {
      setError(error.message); // yahan khud handle karna padega
    }
  };
  fetchData();
}, []);
```

Ye try-catch aur Error Boundary **alag-alag kaam** karte hain:

|                  | Error Boundary                                                             | try-catch                                                                                                            |
| ---------------- | -------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| Kab use hota hai | Render-time errors (component crash)                                       | Async code, event handlers ke andar errors                                                                           |
| Kya karta hai    | Poore component tree ko crash hone se bachata hai, fallback UI dikhata hai | Specific block ke andar error ko pakad ke manually handle karta hai (jaise state update karke error message dikhana) |
| Scope            | Poora component subtree (jo isse wrap hai)                                 | Sirf wahi specific code block jahan likha gaya hai                                                                   |

Simple tarike se samjho: **Error Boundary ek "safety net" hai poore component tree ke liye** — agar kahin render crash ho jaye, ye poore app ko bachata hai. **try-catch specific jagah pe use hota hai** — jaha tumhe pata hai ki koi particular operation (API call, JSON parse karna, etc.) fail ho sakta hai, aur tum khud decide karna chahte ho ki fail hone pe kya karna hai (error message set karna, retry karna, etc.).

Real apps me dono saath use hote hain — API calls, form submissions jaisi async cheezon ke liye try-catch (jaisa humne API Integration notes me dekha), aur poore app ya major sections ke liye Error Boundary — taaki koi bhi unexpected render crash poore app ko down na kar de.
