# React Build and Deployment — Notes

## 1. Vite as the build tool

**Build tool** ka matlab hai — ek tool jo tumhara development code (jisme JSX hai, multiple files hain, modern JS features hain jo purane browsers nahi samajhte) leke usse ek aisi form me convert karta hai jo browser me actually chal sake, fast ho, aur production (real users) ke liye ready ho.

**Vite** (pronounce "veet", French word for "fast") aaj kal React projects banane ke liye sabse popular tool hai. Pehle **Create React App (CRA)** popular tha, lekin Vite usse kaafi tezi se replace kar chuka hai kyunki Vite bahut fast hai.

Vite fast kyun hai — ye samajhna important hai:

**Development mode me** — purane tools (jaise CRA, jo Webpack use karta tha) poore app ka code **pehle se bundle** (sab files ko jod ke ek bada file banana) kar dete the, tabhi dev server start hota tha. App jitna bada hota, start hone me utna time lagta.

Vite alag approach use karta hai — ye **ES Modules** (browser ka native `import`/`export` support) ka fayda uthata hai. Dev server almost turant start ho jata hai, kyunki Vite files ko **on-demand** serve karta hai — jab browser kisi file ko `import` karta hai, tabhi Vite usse process karke deta hai, na ki poore app ko pehle se bundle kar ke.

**Production build ke liye** — jab tum `npm run build` chalate ho, Vite internally **Rollup** (ek aur bundler) use karta hai jo poore app ko optimize karke chota, fast-loading bundle banata hai — minification (code ko chota karna, spaces/comments hata ke), tree-shaking (jo code use hi nahi ho raha wo hata dena), aur code splitting (jo humne Performance notes me dekha tha) sab automatically hota hai.

Basic Vite commands jo tum use karoge:

```bash
npm create vite@latest my-app -- --template react
npm run dev      # development server start karta hai
npm run build    # production ke liye optimized build banata hai
npm run preview  # production build ko locally test karne ke liye
```

## 2. Environment variables in React apps

**Environment variable** ek aisi value hai jo tumhare code ke bahar (ek separate file me) store hoti hai, aur alag-alag environments (development, production) me alag ho sakti hai — jaise API ka URL, ya koi secret key. Inhe code me directly hardcode nahi karte, taaki:

- Sensitive values (jaise API keys) code/GitHub me expose na ho
- Development aur production me alag-alag values easily switch ho sakein (jaise dev me `localhost` API, production me real server ka URL)

Vite me environment variables ek `.env` file me define karte hain, project ke root me:

```
VITE_API_URL=https://api.example.com
VITE_APP_NAME=MyApp
```

Zaroori rule: Vite me har environment variable ka naam **`VITE_` se start hona chahiye** — warna Vite usse app ke code me access karne nahi dega (ye ek security measure hai, taaki galti se koi sensitive server-side variable browser tak na pahunch jaye).

Code me use karna:

```jsx
const apiUrl = import.meta.env.VITE_API_URL;

axios.get(`${apiUrl}/users`);
```

`import.meta.env` Vite ka special object hai jisse saare `VITE_` prefix wale variables access hote hain.

Alag-alag environments ke liye alag files bhi bana sakte ho:

- `.env` — sab environments me common
- `.env.development` — sirf dev mode me (`npm run dev`)
- `.env.production` — sirf production build (`npm run build`) ke waqt

Ek **important security point**: React (ya koi bhi frontend framework) me environment variables **browser tak pahunch jaate hain** — matlab ye "secret" nahi rehte, koi bhi browser ke DevTools khol ke inhe dekh sakta hai. Isliye **kabhi bhi truly sensitive cheezein** (jaise database password, private API secret keys) frontend `.env` me mat daalo — sirf wo values daalo jo public hone me koi problem nahi (jaise public API URL). Genuinely secret cheezein hamesha backend (server-side) pe hi rehni chahiye.

`.env` file ko **Git me commit nahi karte** (usually `.gitignore` me already add hota hai) — kyunki alag developers/environments ke apne-apne values ho sakte hain, aur agar koi sensitive value ho bhi gayi ho galti se, wo public repo me expose nahi honi chahiye.

## 3. Production build and static hosting basics

Jab development complete ho jaye aur app ko real users ke liye deploy karna ho, sabse pehla step hai **production build** banana:

```bash
npm run build
```

Ye command ek `dist` (ya `build`) naam ka folder banata hai jisme tumhara poora app **optimized** form me hota hai:

- Saari JS/CSS files **minified** hoti hain (chota size, fast download)
- Multiple files smartly **bundled/split** hoti hain (code splitting, jo humne Performance notes me dekha)
- Sirf plain HTML, CSS, JS files hoti hain — koi source code (JSX, unprocessed files) nahi

React app (jab tak tum server-side rendering — SSR — nahi kar rahe, jo advanced topic hai) basically ek **static site** ban jati hai — matlab sirf HTML/CSS/JS files hain jo koi bhi simple web server serve kar sakta hai, koi special backend logic nahi chahiye deployment ke liye.

**Static hosting** ka matlab hai — aisi hosting service jo bas ye files serve kar de, browser tak pahuncha de. Kuch popular options React apps ke liye:

- **Vercel** — bahut popular, easy setup (GitHub repo connect karo, automatic deploy ho jata hai har push pe)
- **Netlify** — Vercel jaisa hi, similar simplicity
- **GitHub Pages** — free, GitHub repo se directly

Typical deployment flow (Vercel/Netlify jaise platforms ke saath):

1. Code ko GitHub pe push karo
2. Platform pe apna repo connect karo
3. Platform khud detect kar leta hai ki ye Vite/React project hai, aur build command (`npm run build`) khud chala deta hai
4. `dist` folder ka content ek global **CDN** (Content Delivery Network — servers jo duniya bhar me alag-alag jagah files ka copy rakhte hain, taaki user jaha bhi ho, usse closest server se fast content mile) pe deploy ho jata hai
5. Har baar jab tum GitHub pe naya code push karoge, platform automatically naya build bana ke deploy kar deta hai (isko **CI/CD** — Continuous Integration/Continuous Deployment — kehte hain, jo tumhare roadmap ke Phase 9 me bhi hai)

Ek zaroori concept jo React Router use karte waqt yaad rakhna padta hai — **client-side routing** (jo humne Routing notes me dekha) ka matlab hai URL change React khud JS se handle karta hai, server ko nahi pata ki `/dashboard` jaisa koi "real" page hai. Isliye agar user directly `/dashboard` URL type karke ya refresh kare, to server ko batana padta hai "koi bhi URL ho, hamesha `index.html` hi bhejo, React khud decide kar lega kya dikhana hai" — isko **rewrite rule** kehte hain. Vercel/Netlify jaise platforms me ye usually automatically configure ho jata hai React apps ke liye, lekin agar khud kisi generic server pe deploy kar rahe ho, to ye manually set karna padta hai — warna refresh karne pe "404 Not Found" jaisi error aa sakti hai.
