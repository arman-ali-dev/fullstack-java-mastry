# React Fundamentals — Notes 

## 1. JSX syntax and compilation

JSX wo syntax hai jisme hum HTML jaisa code JavaScript ke andar likhte hain, jaise:

```jsx
const el = <h1 className="title">Hello</h1>;
```

Ab problem ye hai ki browser sirf plain JavaScript samajhta hai — `<h1>` jaisa HTML tag JS ke andar likhna browser ke liye invalid hai. To ye code chalne se pehle ek tool isse convert karta hai plain JS me. Us tool ka naam **Babel** hai (kabhi kabhi Vite ke andar iski jagah **SWC** ya **esbuild** naam ke tools bhi use hote hain — kaam sabka same hai: JSX ko JS me badalna).

Ye conversion **build time pe** hoti hai — matlab jab tum code run/build karte ho, tab hi ye hota hai, browser me nahi. Upar wala code convert hoke ye ban jata hai:

```jsx
const el = React.createElement("h1", { className: "title" }, "Hello");
```

`React.createElement` ek function hai jo call hone pe ek plain JavaScript object return karta hai. Iska matlab: JSX likhna sirf ek shortcut hai — asal me tum ye function hi call kar rahe ho, bas React ne easy likhne ka tarika de diya hai taaki har jagah `createElement(...)` na likhna pade.

Ye jo object return hota hai, usme 3 cheezein hoti hain:

- `type` — kaunsa element hai (`h1`, `div`, ya koi custom component)
- `props` — us element ke attributes/data (jaise `className`)
- `children` — uske andar kya content hai

React internally is object ko use karke decide karta hai ki actual browser DOM me kya dikhana hai. Isi process ko **reconciliation** kehte hain — matlab React purane aur naye version ko compare karke sirf wahi cheez update karta hai jo actually badli hai, poora page dubara nahi banata.

Kuch important rules:

- `{ }` ke andar tum koi bhi JS value ya expression daal sakte ho (jaise `{name}`, `{1+1}`) — lekin `if` jaisa statement seedha nahi likh sakte, uske liye ternary (`? :`) ya `&&` use karna padta hai (isko aage explain karenge)
- HTML me `class` likhte hain, lekin JSX me `className` likhna padta hai — kyunki `class` JavaScript ka already ek reserved keyword hai (class banane ke liye), to naam clash na ho isliye React ne `className` naam rakha
- Isi tarah `for` (HTML attribute) JSX me `htmlFor` ban jata hai
- Pehle React me har file me `import React from 'react'` likhna zaroori tha, kyunki JSX internally `React.createElement` use karta tha. Ab (React 17 ke baad) ek naya conversion tarika aaya hai jisme ye import likhna zaroori nahi — compiler khud peeche se sab handle kar leta hai
- Ek JSX block sirf **ek hi root element** return kar sakta hai. Agar tumhe multiple cheezein return karni hain bina ek extra `<div>` wrap kiye, to `<>...</>` (isko **Fragment** kehte hain) use kar sakte ho — ye sirf grouping ke liye hai, actual DOM me kuch add nahi karta

## 2. Functional vs Class Components

React me component banane ke 2 tarike hain — purana (**class components**) aur naya (**functional components**).

**Class component** — JavaScript ki `class` syntax use karke banaya jata hai. State (component ka apna data) `this.state` me store hota hai aur update karne ke liye `this.setState()` call karte hain. Isme kuch special methods bhi hote hain jo React khud-ba-khud call karta hai component ki life ke alag-alag stages pe — inhe **lifecycle methods** kehte hain:

- `componentDidMount` — jab component pehli baar screen pe aa jaye
- `componentDidUpdate` — jab component update ho
- `componentWillUnmount` — jab component screen se hatne wala ho

```jsx
class Counter extends React.Component {
  state = { count: 0 };
  componentDidMount() {
    console.log("mounted");
  }
  render() {
    return (
      <button onClick={() => this.setState({ count: this.state.count + 1 })}>
        {this.state.count}
      </button>
    );
  }
}
```

**Functional component** — ek normal JavaScript function hai jo JSX return karta hai. Pehle functions state handle nahi kar sakte the, lekin React version 16.8 me **Hooks** naam ka feature aaya (jaise `useState`, `useEffect`) jisse functions bhi state aur lifecycle jaisa behavior handle kar sakte hain — bina class likhe:

```jsx
function Counter() {
  const [count, setCount] = useState(0);
  useEffect(() => console.log("mounted"), []);
  return <button onClick={() => setCount(count + 1)}>{count}</button>;
}
```

Aaj kal functional components hi standard kyun hain, iski wajah:

- Class components me `this` keyword ka use hota tha, aur `this` ka value context ke hisaab se badalta rehta hai — isse confusion aur bugs hote the. Event handlers me `this` sahi se kaam kare isliye `.bind(this)` likhna padta tha ya arrow function ka trick use karna padta tha. Functional components me `this` ka jhanjhat hi nahi hai
- Agar tumhe same logic (jaise "window resize track karna") multiple components me chahiye, functional components me tum ek **custom hook** bana ke wo logic reuse kar sakte ho — simple function ki tarah. Class components me yehi kaam karne ke liye **HOC (Higher-Order Component)** ya **render props** jaise complex patterns use karne padte the, jisme code zyada aur samajhna mushkil hota tha
- React team khud ab naye features (jaise **Suspense** — loading state handle karne ka tarika, aur **Concurrent rendering** — React ka rendering ko smartly manage karne ka naya tarika) sirf hooks ke saath design kar rahi hai
- Class components abhi bhi kaam karte hain (React ne unhe hataya nahi hai), aur purane projects me tumhe milenge — lekin naya code likhte waqt sab functional components hi use karte hain

## 3. Props

**Props** (short for "properties") wo tarika hai jisse ek parent component apne child component ko data bhejta hai.

```jsx
function Greeting({ name, age = 18 }) {
  // ye { name, age = 18 } destructuring hai — props object se
  // seedha name aur age nikal liya, aur age ki default value 18 rakh di
  return (
    <p>
      {name} is {age} years old
    </p>
  );
}

<Greeting name="Jagir" />; // age nahi diya, to default 18 use hoga
```

Kuch important baatein:

- Props hamesha **upar se neeche** (parent se child) jaate hain, kabhi child se parent ko wapas nahi ja sakte. Isko **unidirectional data flow** kehte hain — matlab data sirf ek hi direction me flow karta hai
- Component function ko `props` ek normal parameter ki tarah milta hai (upar wale example me humne seedha `{ name, age }` destructure kar liya, warna `props.name`, `props.age` likhna padta)
- `props.children` ek special prop hai — jab tum kisi component ke andar kuch likhte ho, wo `children` prop ban jata hai:

```jsx
<Card>
  <p>Ye children prop ke through pass hota hai</p>
</Card>
```

Yahan `<p>...</p>` wala content `Card` component ko `props.children` ke through milega.

- Props ko **mutate (directly change) nahi kar sakte** — ye read-only hote hain. Aisa isliye hai kyunki React ye assume karke chalta hai ki agar props same hain to component ko dubara render karne ki zaroorat nahi. Agar tum props ko chup-chaap badal doge, React ko pata hi nahi chalega ki kuch update hua hai, aur UI galat dikhega
- Agar TypeScript use nahi kar rahe (jaisa tumhare case me hai), to props ka type check karne ke liye pehle `prop-types` naam ki ek library use hoti thi — ab kam use hoti hai kyunki zyadatar log TypeScript pe shift ho gaye hain

## 4. Rendering lists with keys

Jab tumhare paas array of data hota hai aur usse UI me list dikhani hoti hai, to `.map()` (JavaScript ka array method) use karte hain:

```jsx
{
  items.map((item) => <li key={item.id}>{item.name}</li>);
}
```

Yahan har `item` ke liye ek `<li>` element ban raha hai.

Ab `key` kya hai aur zaroori kyun hai — React jab bhi kuch update hota hai to check karta hai ki purani list aur nayi list me kya farak hai, taaki sirf changed cheez hi update kare, poori list dubara na banaye. Isi comparison process ko **reconciliation** kehte hain (jo humne Section 1 me bhi mention kiya tha). Is comparison ke liye React ko har list item ki ek unique pehchaan chahiye hoti hai — wahi `key` hai.

- `key` **stable** honi chahiye (baar baar na badle) aur **unique** honi chahiye us list ke andar — sabse best hota hai koi database ID jaisi cheez
- Array ka **index** (0, 1, 2...) key ki tarah use karna galat practice hai — lekin sirf tab jab list me items reorder ho sakte hain, ya beech me se add/delete ho sakte hain. Wajah: agar list ka order badal jaye, to index bhi badal jayega, aur React confuse ho jayega ki kaunsa element actually kaunsa hai. Isse bugs ho sakte hain — jaise galat item ka data kisi aur item pe show hona, ya form ke input box me galat value reh jana
- Agar list **kabhi bhi reorder/change nahi hoti** (bilkul static hai), tab index ko key ki tarah use karna theek hai
- `key` sirf seedhe array ke andar wale elements pe lagani hoti hai — agar array ke andar koi aur nested element hai, unpe key ki zaroorat nahi

## 5. Conditional rendering patterns

JSX asal me ek **expression** hai (koi value return karta hai), statement nahi (jaise `if` ek statement hai, value return nahi karta). Isi wajah se JSX ke andar direct `if` nahi likh sakte. Isliye condition ke hisaab se kuch dikhane ke liye alag tarike use karte hain:

```jsx
// Ternary operator — jab dono cases (true aur false) me kuch dikhana hai
{
  isLoggedIn ? <Dashboard /> : <Login />;
}

// Logical && — jab sirf true hone pe kuch dikhana hai, false pe kuch nahi
{
  hasError && <ErrorMessage />;
}

// Early return — function ke andar hi upar condition check karke return kar do
function Profile({ user }) {
  if (!user) return <Spinner />;
  return <div>{user.name}</div>;
}

// Variable me store karna — jab conditions zyada aur complex hain
let content;
if (status === "loading") content = <Spinner />;
else if (status === "error") content = <ErrorMessage />;
else content = <Data />;
return <div>{content}</div>;
```

Ek common mistake jo naye log karte hain: `{count && <p>Items: {count}</p>}`

Agar `count` ki value `0` ho, to JavaScript me `0` ek **falsy value** hoti hai (matlab condition check karte waqt ye "false" jaisa treat hota hai). Lekin React `&&` ke left side ka result agar `0` nikle to usse bhi ek "renderable value" samajh leta hai, aur screen pe literal **`0` likha hua dikha deta hai** — jo tum nahi chahte the.

Fix: condition ko explicitly boolean banao —

```jsx
{
  count > 0 && <p>Items: {count}</p>;
}
```

ya

```jsx
{
  Boolean(count) && <p>Items: {count}</p>;
}
```
