# React Routing — Notes

React khud se **routing** (matlab alag-alag URL pe alag-alag page/component dikhana) handle nahi karta — ye sirf UI banane ki library hai, browser ke URL ko manage karna iska kaam nahi. Isliye ek separate library use karte hain jiska naam **React Router** hai.

React ek **SPA (Single Page Application)** banata hai — matlab poora app ek hi HTML page pe chalta hai, aur jab URL change hota hai, poora page reload nahi hota — bas React internally decide karta hai ki kaunsa component dikhana hai. Yehi kaam React Router karta hai.

## 1. React Router: Routes, Route, Link, useNavigate, useParams

Sabse pehle poore app ko `BrowserRouter` (ya `Router`) se wrap karte hain — ye setup karta hai ki app URL ko track kare:

```jsx
import { BrowserRouter } from "react-router-dom";

<BrowserRouter>
  <App />
</BrowserRouter>
```

**`Routes` aur `Route`** — inse define karte hain ki kaunsa URL pe kaunsa component dikhana hai:

```jsx
import { Routes, Route } from "react-router-dom";

<Routes>
  <Route path="/" element={<Home />} />
  <Route path="/about" element={<About />} />
  <Route path="/products/:id" element={<ProductDetail />} />
</Routes>
```

- `Routes` ek wrapper hai jiske andar saare `Route` definitions rehte hain
- Har `Route` ek `path` (URL pattern) aur `element` (kaunsa component render hoga) leta hai
- `Routes` khud check karta hai ki current URL kaunse `path` se match karta hai, aur sirf wahi ek `Route` render karta hai (agar koi match na ho, to kuch bhi render nahi hota, jab tak koi catch-all route na ho)
- `:id` jaisa syntax ek **dynamic segment** hai — matlab yahan URL me koi bhi value aa sakti hai (jaise `/products/5`, `/products/42`), aur wo value baad me `useParams` se nikal sakte ho

**`Link`** — normal HTML me hum `<a href="...">` use karte hain page navigate karne ke liye, lekin isse **poora page reload** ho jata hai (jo SPA me nahi chahiye). Isliye React Router `Link` deta hai:

```jsx
import { Link } from "react-router-dom";

<Link to="/about">About Page</Link>
```

Ye dikhega bilkul `<a>` tag jaisa, lekin click karne pe poora page reload nahi hota — React Router internally URL change karta hai aur sirf naya component render kar deta hai, baaki page same rehta hai.

**`useNavigate`** — jab tumhe **code se** (kisi button click, form submit, ya condition ke baad) navigate karna ho, na ki user ke `Link` pe click karne se:

```jsx
import { useNavigate } from "react-router-dom";

function LoginForm() {
  const navigate = useNavigate();

  const handleLogin = () => {
    // login logic...
    navigate("/dashboard"); // login ke baad redirect
  };

  return <button onClick={handleLogin}>Login</button>;
}
```

`useNavigate()` ek function return karta hai jise call karke tum kisi bhi URL pe programmatically bhej sakte ho — jaise login ke baad dashboard pe redirect karna, ya form submit hone ke baad kisi aur page pe le jana.

**`useParams`** — dynamic URL segments (jaise `:id`) ki actual value nikalne ke liye:

```jsx
import { useParams } from "react-router-dom";

function ProductDetail() {
  const { id } = useParams(); // agar URL hai /products/5, to id = "5"
  return <p>Product ID: {id}</p>;
}
```

Agar `Route path="/products/:id"` define kiya hai, aur user `/products/5` pe visit karta hai, to `useParams()` se `{ id: "5" }` milega — is data ko use karke tum us specific product ka detail fetch kar sakte ho.

## 2. Nested routes and layouts

**Nested routes** ka matlab hai — ek route ke andar aur routes define karna, jab UI ka structure bhi nested ho (jaise ek dashboard jisme sidebar hamesha rehta hai, aur beech ka content URL ke hisaab se badalta hai).

```jsx
<Routes>
  <Route path="/dashboard" element={<DashboardLayout />}>
    <Route path="profile" element={<Profile />} />
    <Route path="settings" element={<Settings />} />
  </Route>
</Routes>
```

Yahan `/dashboard/profile` aur `/dashboard/settings` dono `DashboardLayout` ke **andar** render honge. `DashboardLayout` component kuch aisa dikhega:

```jsx
import { Outlet } from "react-router-dom";

function DashboardLayout() {
  return (
    <div>
      <Sidebar />
      <Outlet /> {/* yahan child route ka content aayega */}
    </div>
  );
}
```

`Outlet` ek special component hai jo batata hai "yahan par child route ka element render karo". Matlab `/dashboard/profile` pe jaoge to `Sidebar` hamesha dikhega (kyunki wo `DashboardLayout` ka part hai), aur `Outlet` ki jagah `Profile` component render hoga.

Ye **layout pattern** kaafi common hai — jaise navbar/sidebar jo saare pages me common rehta hai, sirf beech ka content change hota hai. Isse har page pe baar-baar `<Sidebar />` likhne ki zaroorat nahi padti — ek hi layout component sab handle kar leta hai.

## 3. Protected/private routes

**Protected route** (ise **private route** bhi kehte hain) wo route hai jo sirf tab access ho sake jab user **logged in** ho (ya koi aur condition satisfy ho, jaise admin role hona). Agar condition satisfy nahi hoti, to user ko kisi aur page (jaise login page) pe redirect kar dete hain.

React Router isko directly built-in support nahi karta, lekin ek simple wrapper component bana ke achieve karte hain:

```jsx
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }) {
  const isLoggedIn = checkIfUserIsLoggedIn(); // apna auth check logic

  if (!isLoggedIn) {
    return <Navigate to="/login" />;
  }

  return children;
}
```

`Navigate` component `useNavigate` jaisa hi kaam karta hai, lekin JSX ke andar directly use hota hai (render ke waqt hi redirect kar deta hai), function call ke bajaye.

Ab isse routes pe use karte hain:

```jsx
<Routes>
  <Route path="/login" element={<Login />} />
  <Route
    path="/dashboard"
    element={
      <ProtectedRoute>
        <Dashboard />
      </ProtectedRoute>
    }
  />
</Routes>
```

Yahan `Dashboard` component `ProtectedRoute` ke `children` ki tarah pass ho raha hai. Jab user `/dashboard` pe jaayega:
- Agar logged in hai → `ProtectedRoute` andar `children` (matlab `Dashboard`) return kar dega, wahi render hoga
- Agar logged in nahi hai → `Navigate to="/login"` chal jayega, user login page pe redirect ho jayega, `Dashboard` render hi nahi hoga

Isi pattern ko extend karke **role-based protection** bhi bana sakte ho (jaise sirf admin users kisi page ko access kar sakein) — bas `isLoggedIn` ke bajaye `user.role === "admin"` jaisi condition check karni hogi.

Real apps me `isLoggedIn` ka data usually **Context API** (jo humne Hooks notes me cover kiya tha) ya kisi state management library se aata hai, taaki poore app me ek hi jagah se login status check ho sake.
