# React Component Design — Notes

## 1. Component composition vs inheritance

**Inheritance** OOP (Object-Oriented Programming) ka concept hai — ek class doosri class se properties/behavior "extend" (inherit) karti hai. Java me tum ye bahut use karte ho (`class Dog extends Animal`).

React me bhi technically class components inheritance use karte hain (`class Counter extends React.Component`) — lekin ye sirf React ke base features (jaise `render`, `state`) lene ke liye hota hai, apne components ko ek doosre se inherit karne ke liye nahi. React team khud recommend karti hai ki components ko reuse karne ke liye **composition** use karo, inheritance nahi.

**Composition** ka matlab hai — chote components ko jodkar (combine karke) bada component banana, jaise Lego blocks. Ek component doosre component ko **prop ki tarah** ya **children ki tarah** use karta hai:

```jsx
function Card({ children }) {
  return <div className="card">{children}</div>;
}

function ProfileCard() {
  return (
    <Card>
      <h2>Jagir</h2>
      <p>Java Full Stack Developer</p>
    </Card>
  );
}
```

Yahan `ProfileCard`, `Card` component ko **use** kar raha hai (uske andar content daal ke), na ki usse **inherit** kar raha hai. Ye flexible hai — `Card` ko kisi bhi tarah ke content ke saath use kiya ja sakta hai, bina `Card` ke andar ka code touch kiye.

Composition ko inheritance se better kyun mana jata hai:
- Inheritance me parent-child ka rishta **fixed** ho jata hai (tight coupling) — child hamesha parent ke structure se bandha rehta hai
- Composition me components independent rehte hain, aur unhe alag-alag tarike se combine kiya ja sakta hai — zyada flexible aur reusable
- JavaScript (aur JSX) composition ko naturally support karta hai — props aur children ke through hi ye ho jata hai, koi special syntax nahi chahiye

## 2. Controlled vs uncontrolled components

Ye pattern mostly **form inputs** (jaise `<input>`, `<textarea>`) ke context me use hota hai.

**Controlled component** — jab input ki value React ke **state** se control hoti hai. Matlab input box khud apni value store nahi karta, balki React state usko batata hai ki kya dikhana hai:

```jsx
function NameInput() {
  const [name, setName] = useState("");

  return (
    <input
      value={name}
      onChange={(e) => setName(e.target.value)}
    />
  );
}
```

Yahan `value={name}` input ko batata hai ki kya show karna hai, aur `onChange` har keystroke pe state update karta hai. React hi "source of truth" (asal sahi data kahan hai) hai — input sirf usko reflect karta hai.

**Uncontrolled component** — jab input apni value **khud** (browser ke andar, DOM ke andar) store karta hai, aur React usse directly track nahi karta. Value nikalne ke liye `useRef` use karte hain:

```jsx
function NameInput() {
  const inputRef = useRef(null);

  const handleSubmit = () => {
    console.log(inputRef.current.value); // value seedha DOM se nikali
  };

  return <input ref={inputRef} />;
}
```

Yahan React ko har keystroke pe pata nahi chalta — value sirf tab check ki jaati hai jab zaroorat ho (jaise submit hone pe).

Kab kya use karo:
- **Controlled** — jab tumhe real-time validation chahiye (jaise typing ke saath hi error dikhana), ya input ki value kisi aur cheez pe depend karti ho
- **Uncontrolled** — simple forms ke liye jahan sirf submit pe value chahiye, thoda kam code likhna padta hai. File inputs (`<input type="file">`) almost hamesha uncontrolled hi hote hain kyunki security reasons se React unki value set nahi kar sakta

Controlled approach zyada common aur "React way" mana jata hai, lekin uncontrolled bhi valid pattern hai simple cases ke liye.

## 3. Lifting state up

Jab 2 ya zyada components ko **same state share** karni ho (ek ka change doosre pe effect dale), to solution hai — us state ko unke **common parent** component me le jana (upar uthana). Isi ko **lifting state up** kehte hain.

Example: 2 input boxes hain jinhe same value me sync rehna hai — Celsius aur Fahrenheit temperature converter.

```jsx
function Parent() {
  const [celsius, setCelsius] = useState(0);

  return (
    <>
      <CelsiusInput value={celsius} onChange={setCelsius} />
      <FahrenheitDisplay celsius={celsius} />
    </>
  );
}
```

Yahan `celsius` state `Parent` me hai (na ki `CelsiusInput` ke andar) — isliye `Parent` ye value `FahrenheitDisplay` ko bhi pass kar sakta hai. Agar `celsius` state `CelsiusInput` ke andar hi rehti, to `FahrenheitDisplay` ko us tak pahunch hi nahi hoti.

Ye pattern React ke **unidirectional data flow** (upar se neeche data jana) se directly connected hai — kyunki child se parent ko directly data nahi bhej sakte, isliye jab bhi 2 siblings ko data share karna ho, unka common parent hi wo state hold karta hai, aur dono children ko props ke through de deta hai.

Rule of thumb: state ko hamesha **uske sabse upar wale common component** me rakho jisko us state ki zaroorat hai — na usse zyada upar (unnecessary), na usse neeche (jahan se data share nahi ho payega).

## 4. Container/Presentational component pattern

Ye ek **design pattern** hai jisme components ko 2 categories me divide karte hain:

**Presentational component** — sirf UI dikhane ka kaam karta hai. Isko data kahan se aa raha hai, ya kaise process ho raha hai — iski koi fikar nahi. Bas props leta hai aur render karta hai:

```jsx
function UserCard({ name, email }) {
  return (
    <div>
      <h3>{name}</h3>
      <p>{email}</p>
    </div>
  );
}
```

**Container component** — data fetch karna, state manage karna, business logic handle karna — ye sab iska kaam hai. Ye khud kam UI render karta hai, balki presentational components ko data pass karta hai:

```jsx
function UserCardContainer() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    fetchUser().then(setUser);
  }, []);

  if (!user) return <Spinner />;
  return <UserCard name={user.name} email={user.email} />;
}
```

Fayda: **separation of concerns** — matlab "UI kaisa dikhega" aur "data kaise aayega" — ye dono alag-alag jagah handle hote hain, ek dusre me mix nahi hote. Isse:
- Presentational components **test karna aasan** hai (bas props do, output check karo — koi API call ya state nahi)
- Same presentational component **multiple jagah reuse** ho sakta hai, alag-alag containers ke saath
- Code padhna aasan hota hai — logic aur UI clearly separate dikhte hain

Note: Hooks aane ke baad (custom hooks se logic extract karna easy ho gaya), ye pattern utna strictly follow nahi hota jitna pehle hota tha — ab log custom hooks (jaise `useUser()`) use karke bhi yahi separation achieve kar lete hain. Lekin concept (UI aur logic ko separate rakhna) abhi bhi valid aur useful hai.

## 5. Folder structure / project architecture

Jaise-jaise app bada hota hai, files ko organize karne ka tarika important ho jata hai. 2 main approaches hain:

**Type-based structure** — files ko unke **type** ke hisaab se folders me rakhte hain:

```
src/
  components/
    Button.jsx
    UserCard.jsx
    ProductCard.jsx
  hooks/
    useUser.js
    useProducts.js
  pages/
    HomePage.jsx
    ProductPage.jsx
```

Chote projects ke liye ye simple aur samajhne me aasan hai — sab similar cheezein ek jagah milti hain (saare components ek folder me, saare hooks ek folder me).

**Feature-based structure** — files ko unke **feature/module** ke hisaab se group karte hain, na ki type ke hisaab se:

```
src/
  features/
    user/
      UserCard.jsx
      useUser.js
      userApi.js
    product/
      ProductCard.jsx
      useProducts.js
      productApi.js
```

Yahan `user` se related sab kuch (component, hook, API call) ek hi folder me hai — chahe wo alag-alag "type" ka ho.

Bada app grow hone pe feature-based structure zyada better kaam karta hai, kyunki:
- Jab tumhe kisi feature pe kaam karna ho, sab related files ek hi jagah milti hain — bar-bar alag-alag folders me jaana nahi padta
- Naya feature add karna aasan hai — bas ek naya folder bana do, baaki app se isolated rehta hai
- Type-based structure me app bada hone pe `components/` folder me 100+ files ho jaati hain jo dhundna mushkil ho jata hai

Kaunsa use karo:
- Chota project / seekhne ke liye → type-based theek hai, simple hai
- Real, growing project (jaise tumhara koi production app) → feature-based better scale karta hai

Dono approaches ko mix bhi kar sakte ho — jaise truly shared/reusable cheezein (`Button`, `Input` jaise generic components) ek common `components/` folder me, aur baaki sab feature-based folders me.
