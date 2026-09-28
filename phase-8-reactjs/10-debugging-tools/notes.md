# React Debugging Tools — Notes

## 1. React DevTools: inspecting component tree, props, and state

**React DevTools** ek browser extension hai (Chrome, Firefox, Edge sab me available hai) jo React apps ke liye specially bana hai. Normal browser DevTools (jo `F12` dabane se khulta hai) tumhe HTML/CSS/JS dikhata hai, lekin wo React ki apni internal duniya (components, props, state) nahi samajhta. React DevTools install karne ke baad, browser ke DevTools me 2 naye tabs add ho jaate hain — **Components** aur **Profiler**.

Install karne ke baad, jab tum apni React app ko browser me kholte ho aur DevTools (`F12`) open karte ho, "Components" tab dikhega.

**Components tab me kya milta hai:**

Ye tumhe tumhara **poora component tree** dikhata hai — matlab kaunsa component kis component ke andar hai, bilkul waise hi jaise tumne code me likha hai:

```
App
 ├─ Navbar
 ├─ Dashboard
 │   ├─ Sidebar
 │   └─ UserList
 │       ├─ UserCard
 │       └─ UserCard
 └─ Footer
```

Kisi bhi component pe click karo, to right side me uske:

- **Props** — us component ko kya-kya data parent se mila hai
- **State** — us component ke apne `useState` (aur `useReducer`) se banaye gaye states, unki current values ke saath
- **Hooks** — kaunse hooks use ho rahe hain us component me (jaise `useEffect`, `useContext`), aur unki current values

Ye sab **live** hota hai — matlab agar app me koi state change ho, DevTools me turant naya value dikh jata hai, bina page reload kiye.

Ye kaafi useful hai jab:

- Tumhe pata karna ho ki koi particular component ko galat prop mil raha hai (jaise `undefined` aa raha hai jaha string expect kar rahe the)
- State ki current value directly dekhni ho, bina `console.log` likhe har jagah
- Component tree ka structure samajhna ho — especially kisi doosre ke bade codebase me kaam karte waqt, jaldi pata chal jata hai kaunsa component kaha use ho raha hai

Ek aur useful feature — DevTools ke andar hi kisi component ki state ya props ko **manually edit** kar sakte ho (temporarily, sirf testing ke liye) — jaise `count` ki value ko directly `5` set kar dena, ye dekhne ke liye ki UI kaisa react karta hai, bina actual code change kiye.

## 2. Profiler tab: identifying unnecessary re-renders and performance bottlenecks

**Profiler** tab specifically **performance** dekhne ke liye hai — matlab kaunsa component render hone me kitna time le raha hai, aur kaunse components **baar-baar unnecessarily re-render** ho rahe hain (jo humne Performance notes me discuss kiya tha).

Kaise use karte hain:

1. Profiler tab kholo
2. **Record** button dabao (ek circle icon hota hai)
3. Apni app me kuch interact karo (jaise button click karo, form fill karo, jo bhi tumhe check karna hai)
4. **Stop** dabao

Ab tumhe ek **flame graph** ya **ranked chart** dikhta hai — ye visually dikhata hai:

- Kaunsa component render hua us interaction ke dauran
- Har component ko render hone me **kitna time** laga (color se bhi indicate hota hai — jitna zyada peela/orange, utna zyada time laga)
- Component **kyun** re-render hua (DevTools ki latest versions me ye reason bhi bata deta hai — jaise "props changed", "state changed", "parent re-rendered")

Isse tum easily identify kar sakte ho:

- **Unnecessary re-renders** — jaise agar tumne dekha ki `Footer` component har baar re-render ho raha hai jab tum kisi form ka input type kar rahe ho, jabki `Footer` ka us form se koi lena-dena nahi hai — ye batata hai ki state kahi galat jagah (bahut upar) rakhi hai, ya `React.memo` ki zaroorat hai (jo humne Performance notes me dekha)
- **Slow components** — agar koi component render hone me bahut zyada time le raha hai (jaise 50ms+), to wo candidate hai optimize karne ke liye — shayad heavy calculation ho raha hai jo `useMemo` se cache ho sakta hai

Real workflow jo professionals follow karte hain: pehle **guess mat karo** ki kaunsa component slow hai — Profiler se **measure** karo, phir specifically usi component ko target karke `React.memo`, `useMemo`, ya `useCallback` lagao (jo humne Hooks aur Performance notes me detail me dekha). Bina measure kiye optimization karna time waste hai aur kabhi kabhi code ko unnecessarily complex bhi bana deta hai, bina real benefit ke.

Dono tools (Components + Profiler) milke debugging ka core workflow banate hain — Components tab se "kya ho raha hai" samajhna (data/structure), aur Profiler se "kitna time lag raha hai aur kyun" samajhna (performance). Interview me bhi ye common question aata hai — "app slow hai, kaise debug karoge" — iska ek standard answer hi hai: React DevTools Profiler khol ke measure karna, guess se optimize nahi karna.
