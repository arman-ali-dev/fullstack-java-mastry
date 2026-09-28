# React Forms — Notes

## 1. Controlled form inputs

Ye concept humne Component Design notes me bhi dekha tha — yahan forms ke context me thoda extend karte hain.

**Controlled input** ka matlab hai — input ki value React **state** se aati hai, aur har change pe state update hoti hai. React hi "source of truth" hota hai (matlab asal sahi data kahan hai) — input box khud kuch store nahi karta, sirf state ko reflect karta hai:

```jsx
function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault(); // page reload hone se roko (default HTML form behavior)
    console.log(email, password);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input value={email} onChange={(e) => setEmail(e.target.value)} />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="submit">Login</button>
    </form>
  );
}
```

Kuch important points:

- `e.preventDefault()` — normal HTML forms submit hone pe **poora page reload** kar dete hain (browser ka default behavior). React apps SPA (Single Page Application) hote hain jaha page reload nahi chahiye hota, isliye ye line zaroori hai
- Har field ka apna `value` aur `onChange` hota hai — jitni zyada fields, utna zyada repetitive code
- Har keystroke pe state update hoti hai, matlab component har keystroke pe **re-render** hota hai — chote forms me ye koi issue nahi hai, lekin bade complex forms (bahut saari fields, real-time validation) me thoda performance overhead ho sakta hai

Multiple fields ka code repeat na ho, isliye ek common trick hai — sab fields ke liye ek hi state **object** use karna:

```jsx
const [formData, setFormData] = useState({ email: "", password: "" });

const handleChange = (e) => {
  setFormData({ ...formData, [e.target.name]: e.target.value });
};

<input name="email" value={formData.email} onChange={handleChange} />
<input name="password" value={formData.password} onChange={handleChange} />
```

Yahan `[e.target.name]` **computed property name** hai — matlab `input` ka `name` attribute (jaise `"email"`) dynamically object ki key ban jata hai. `...formData` (spread operator) purana data copy karta hai, aur sirf jo field change hui hai wahi update hoti hai.

Is manual approach ka problem ye hai ki jaise-jaise form bada hota hai (5-6+ fields, validation, error messages), code kaafi lamba aur repetitive ho jata hai — isi wajah se form libraries use hoti hain.

## 2. Form libraries: React Hook Form basics

**React Hook Form (RHF)** ek popular library hai jo forms handle karna easy banati hai — bina itna repetitive code likhe, aur better performance ke saath.

Sabse bada farak: RHF **uncontrolled approach** use karta hai internally (humne "Controlled vs Uncontrolled" notes me ye concept dekha tha) — matlab ye har keystroke pe React state update nahi karta, balki DOM se directly value track karta hai. Isse **unnecessary re-renders nahi hote**, aur bade forms me performance kaafi better hoti hai.

Basic usage:

```jsx
import { useForm } from "react-hook-form";

function LoginForm() {
  const { register, handleSubmit } = useForm();

  const onSubmit = (data) => {
    console.log(data); // { email: "...", password: "..." }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register("email")} />
      <input type="password" {...register("password")} />
      <button type="submit">Login</button>
    </form>
  );
}
```

- `useForm()` — ek hook hai jo form ke saare tools deta hai
- `register("email")` — ye input ko RHF ke saath "register" (jod) karta hai. Ye internally `{ name, onChange, onBlur, ref }` jaisi properties return karta hai, jinhe `{...register("email")}` se spread karke input pe laga dete hain — isliye alag se `value`/`onChange` likhne ki zaroorat nahi padti
- `handleSubmit(onSubmit)` — form submit hone pe RHF khud saari values collect karke ek object bana deta hai (`data`), aur tumhara `onSubmit` function usko receive karta hai

RHF errors bhi handle karta hai:

```jsx
const {
  register,
  handleSubmit,
  formState: { errors },
} = useForm();

<input {...register("email", { required: "Email is required" })} />;
{
  errors.email && <p>{errors.email.message}</p>;
}
```

`formState.errors` object me har field ki validation error store hoti hai, agar koi ho.

Fayde RHF ke:

- Kam code, kam boilerplate — especially bade forms me
- Better performance (unnecessary re-renders avoid hote hain)
- Built-in validation support (basic rules ke liye alag library ki zaroorat nahi, though complex validation ke liye Zod/Yup ke saath combine karte hain — agle section me dekhenge)

## 3. Validation with libraries like Zod or Yup

Form validation ka matlab hai — user ka diya hua data submit karne se pehle check karna ki wo sahi format/rules follow karta hai ya nahi (jaise email valid hai ya nahi, password minimum length hai ya nahi).

React Hook Form me basic validation directly likh sakte ho (jaisa upar `required: "Email is required"` dikhaya), lekin jab rules complex ho jaate hain (multiple conditions, ek field doosri field pe depend kare, nested objects, etc.), to ek dedicated **schema validation library** use karna better hota hai — jaise **Zod** ya **Yup**.

**Schema** ka matlab hai — ek definition jo batati hai data ka "shape" kaisa hona chahiye (kaunse fields hain, kya type hai, kya rules hain).

**Zod** example:

```jsx
import { z } from "zod";

const loginSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});
```

Yahan humne ek schema define kiya — `email` ek string honi chahiye aur valid email format me, `password` ek string honi chahiye kam se kam 6 characters ki.

Isko React Hook Form ke saath jodne ke liye ek **resolver** use karte hain (`@hookform/resolvers` package se):

```jsx
import { zodResolver } from "@hookform/resolvers/zod";

const {
  register,
  handleSubmit,
  formState: { errors },
} = useForm({
  resolver: zodResolver(loginSchema),
});
```

Ab RHF khud submit hone se pehle Zod schema ke against data check karega, aur agar koi rule fail hui to `errors` object me wo message aa jayega automatically — manually har field ki validation likhne ki zaroorat nahi.

**Yup** bhi bilkul similar kaam karta hai, syntax thoda alag hai:

```jsx
import * as yup from "yup";

const loginSchema = yup.object({
  email: yup.string().email("Invalid email address").required(),
  password: yup
    .string()
    .min(6, "Password must be at least 6 characters")
    .required(),
});
```

Zod vs Yup — dono ka kaam same hai (schema define karke validate karna), farak zyada tar syntax aur kuch additional features ka hai. Zod aajkal thoda zyada popular ho raha hai kyunki TypeScript ke saath bahut acha kaam karta hai (though tum TypeScript use nahi kar rahe, to ye farak utna matter nahi karega tumhare liye). Dono achhe options hain — jo bhi team/project use kare, dono equally valid hain.

Overall flow jo real projects me common hai: **React Hook Form** (form state handle karne ke liye) + **Zod/Yup** (validation rules define karne ke liye) — dono mil ke kaam karte hain, ek dusre ko replace nahi karte.
