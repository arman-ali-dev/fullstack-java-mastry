# Stateful se Stateless Authentication tak — JWT Kaise Bana (My Notes)

---

## Recap: Stateful Authentication me Kya Hota Hai

Har request jo client server ko bhejta hai, wo ek fresh/independent request hoti hai — HTTP naturally stateless hai, matlab server ko by default yaad nahi rehta ki pichli request kisne bheji thi.

Agar humein stateful authentication banana hai (yaani server user ko login ke baad "yaad" rakhe), to humein har login ke baad ek session ID generate karke store karna padega — taaki agli request aane par server us ID se pehchaan sake ki ye wahi user hai jo pehle login kar chuka tha.

Hamara server ye kaam by default khud handle karta hai:
- Jaise hi user login karta hai, server automatically ek unique session ID generate karta hai, jiska naam hota hai `JSESSIONID`
- Ye `JSESSIONID` server apni memory me store rakhta hai, us user ki session information (jaise `SecurityContext`, `Authentication` object) ke saath link karke
- Ye same `JSESSIONID` client ko response me cookie ke roop me bhej diya jata hai
- Agli har request me client ye cookie automatically bhejta hai, aur server us ID se apni memory me dekh leta hai ki ye kaunsa user hai — bina password dobara maange

---

## Stateful me Problem Kya Hai?

Ab problem kya hai, fir hum stateless ko zyada prefer karte hain, stateful ko kyun nahi? Har particular user ke liye cookie send karna aur user ki information (Authentication object) store rakhne se kya problem aati hai?

> **Explanation:** Problem hai **scalability** ki.
>
> Jab tumhara server sirf **ek hi machine** pe chal raha ho, to stateful thik chalta hai — session memory me store hoti hai, sab kaam ho jata hai. Lekin real-world apps me traffic badhne par ek se zyada server instances (multiple machines) chalane padte hain, ek **load balancer** ke peeche.
>
> Ab problem ye hai: agar user ne **Server A** pe login kiya aur uski session **Server A ki memory** me store ho gayi, lekin agli request load balancer ne **Server B** ko bhej di — to Server B ke paas us user ki koi session hi nahi hai! Server B kahega "ye user kaun hai, mujhe nahi pata" — aur user ko dubara login karna padega, jab ki usne already login kiya tha.
>
> Isko solve karne ke do tarike hain, dono ke apne nuksan hain:
> 1. **Sticky sessions** — load balancer ko force karo ki ek user ki har request hamesha **usi server** pe jaye jahan usne pehli baar login kiya tha. Lekin isse load balancing ka fayda hi kam ho jata hai (traffic evenly distribute nahi ho pata), aur agar wahi server crash ho jaye to user ki session hi gayab ho jati hai.
> 2. **Shared session store** — sab servers ek common jagah (jaise Redis) pe session data store karein, taaki koi bhi server kisi bhi user ko pehchaan sake. Lekin isse ek extra system (Redis) maintain karna padta hai, aur har request pe ek extra network call (server → Redis) lagti hai — jo speed thodi kam kar deta hai.
>
> Iske alawa, har logged-in user ka data server ki memory me baithe rehna bhi ek cost hai — jitne zyada users, utni zyada memory server ko use karni padegi, sirf "yaad rakhne" ke liye.
>
> Isi wajah se, especially large-scale/distributed systems me, log **stateless authentication** ko prefer karte hain — jaha server ko **kuch bhi memory me store nahi karna padta**, aur koi bhi server kisi bhi request ko independently handle kar sakta hai, bina doosre server se coordinate kiye.

---

## Stateless Ki Taraf Pehla Kadam — Token Wapas Karna

Ab jaise maano aapne first request ki aur aapko `/login` par redirect kar diya gaya, aur aapne login bhi kar liya aur aapke liye ek `Authentication` response object bana diya gaya.

Aur hamara server ye nahi chahta ki main is Authentication ke object ko store karke rakhu baad ke liye.

Server chahta hai ki aap baad me koi bhi request karo na, to aap mujhe apne baare me sab kuchh bata do — mujhe bata do ki aap kaun ho, aapka role kya hai — aur main aapke liye dobara se ek Authentication ka object create kar dunga.

Kya matlab? Matlab jab aapne login kiya to server ne ek token generate karke diya aapko, aur aapne us token ko store rakh liya apne paas — yaani client ne wo token store kar liya apne paas.

Ab jab bhi aap ek naya request bhejoge hamare server ko, to aap kya karoge? Aap is token ko request ke header me attach karke bhejoge.

For example:
```
Headers:
    token : <value>
```

Ab jab ye token hamare server ko milega, to hamara server us token me se saari values read karke ek naya `Authentication` ka object bana dega.

Aur ye jo token hai wo basically **JWT-Token** hota hai.

Ye aapka pura ka pura **stateless architecture**.

---

## JWT Kya Hai — Pehla Attempt (Sirf Encoding)

Ab humein JWT-Token ke baare me samajhna hai.

JWT ka pura naam hota hai **Json Web Token**. Ye ek special format hai token banane ka.

Maanlo aapne ek request bheji server ko — yaani client ne server ko request send ki `POST /login`. Request body me bheja aapne:
```json
{
    "username" : "arman",
    "password" : "5911"
}
```

Ab server ne isko authenticate kiya aur ek authentication ka object create kar liya. Theek hai, ye sab to server kar lega, isme koi dikkat nahi hai.

Ab hum first-principle se samajhte hain ki JWT tak kaise pahunche.

Ab server na aapko ek token de dega. Ab us token me wo cheezein — yaani information — honi chahiye jo aapko dobara authenticate karne ke liye kaafi ho, taaki server usi information se wapas se authentication ka object bana sake.

To server kehta hai: "theek hai dost, main aapko ek JSON return karta hoon aur usme main kuch information daal dunga", jaise ki:
```json
{
    "sub" : "Arman",
    "role" : "ROLE_USER"
}
```

Server kehta hai ki ye wali information main aapko de deta hoon, aur aap is information ko apne paas rakh lo, aur jab bhi wapas request bhejo na to is information ke saath bhejna.

Server kahega ki information kaafi hai mere liye authentication ka object wapas banane ke liye.

Authentication ka object banega is information se, like:
```json
{
    "principal" : "Arman",
    "authorities" : ["ROLE_USER"],
    "isAuthenticated" : true
}
```

(credentials ki field hoti nahi hai Authentication Response Object me.)

To server ne in information se Authentication ka object bhi bana liya.

Lekin jo aapko server ne diya tha JSON me, usko aap wapas server par kaise bhejoge — ek nayi request body banake? Answer hai **nahi**.

To server ko aapko kaise na kaise ek **String** me bhejna chahiye — yaani ki ek token banake bhejna chahiye. Kyunki ye to ek JSON hai, aap isko kaise bhejoge — bahut problem ho jayegi.

To server ne socha ki main kisi tarike se is JSON ko ek string me — ek token me — convert kar doon.

To maanlo mere paas ek string aa jayegi, jaise `qiuuiewyuty23453jughf`, jo ki us JSON ko represent karegi.

To kaise hum ek JSON ko ek string me convert karein? Answer hai **Base64URL**.

To aap kisi bhi object ka, kisi bhi JSON ka, Base64URL nikal sakte ho.

> **Correction:** Ye string "random" nahi hoti — Base64URL ek **fixed, deterministic** conversion hai (same JSON hamesha same string dega). Isko "random-jaisi dikhne wali" bolna zyada sahi hoga — ye bas ek encoded, non-readable-at-first-glance string lagti hai, lekin actually predictable hai agar aapko conversion rule pata ho.

Aur ye string **URL-friendly** hoti hai, matlab aap ise easily headers waghera me bhej sakte ho.

Yahan humne **encryption nahi ki hai**, yahan humne **encoding** ki hai. Matlab aapne apne JSON ko ek alag format me kar diya jo ki **reversible** hai — matlab usi string se wapas aap JSON format me reverse-back kar sakte ho.

Maine kya kiya — maine wo JSON ko ek URL-friendly random-si string me convert kar diya, aur client ko de diya, aur bola ki jab bhi aap koi dusri request karo, to ye token — matlab ye string — mujhe wapas de dena, main isko decode karke isme se information nikaal ke aapke liye Authentication object bana liya karunga.

Ab meri ye wali problem to solve ho gayi, jahan main ek JSON ko nahi bhej pa raha tha apne client ko.

---

## Problem: Client Khud Token Change Kar Sakta Hai

To ab yahan par bhi ek dikkat hai. Basically maine apne client ko token send kar diya. Ab client ko bhi to ise decode karna aata hai — wo usko decode karke usme information change kar lega, fir changed token send kar dega next request me.

Kya matlab?

Jaise ki maano server ne client ko ek token (`qwer34y47tyu`) diya jisme information hai like:
```json
{
    "sub" : "Arman",
    "role" : "ROLE_USER"
}
```

Ab client ne yahan apna shatir dimaag lagaya — khud se tigdam lagaya — aur is token (`qwer34y47tyu`) ko decode kar liya. Ab usko information mil gayi:
```json
{
    "sub" : "Arman",
    "role" : "ROLE_USER"
}
```

Ab client isme change kar lega, like:
```json
{
    "sub" : "Arman",
    "role" : "ROLE_ADMIN"
}
```

Aur fir wapas Base64URL me encode kar lega, aur token (`yuoi74643ghij`) — ab ye bhi change ho jayega kyunki value change hui hai.

Next request me fir client bolega, "main ye token bhej deta hoon" aur ADMIN ban jaunga.

To ye ek problem hai.

Ab client ne ek request me ye token bheja, to kya hamara server us token ko verify kar paayega? Answer hai **nahi**, kyunki server ne apne paas to koi token save nahi kiya.

Agar store hi karna tha to wo Authentication ka object to store kar hi raha tha stateful me — aur kyunki humein stateful se stateless me jaana tha, tabhi to hum token ka use kar rahe hain.

To basically jo client ne token send kiya, usme wo bol raha hai ki "main Arman hoon aur mera role ADMIN hai" — to server ko us par believe nahi karna chahiye, usko **verify** karna chahiye ki wo Arman hai kya, aur uska role admin hai kya.

---

## Doosra Attempt: Encoding + Hashing Combo

To yahan par server ne socha ki kyun na main isko encode karne ke bajaye isko **hash** kar doon.

Aur hashing **one-way** hoti hai, matlab aapne us JSON ka hash generate karke ek string bana di, jaise ki `qwertyuiop`.

To aap hash (`qwertyuiop`) ka use karke wapas JSON nahi nikaal sakte.

To server ne as-token wo hash send kiya client ko. Aur fir next requests me client ne wahi hash header me daal ke server ko bheja — to us jagah to server bhi us se information nahi nikaal paayega.

Hamara main motive kya tha — ki hum ek token dein client ko, aur client humein request me wahi token wapas de, taaki hum us token ka use karke Authentication ka object bana paayein.

To jab tak main hashed token se wapas information nahi nikaal paunga, to main Authentication object kaise banaunga? Aap hash ko reverse nahi kar sakte.

### Yahan par aayega Encoding + Hashing Combination

> **Explanation:** Ab server ek naya tarika try karta hai — **dono** cheezein bhejo: ek encoded part (jisme actual info ho, taaki decode karke wapas info mile) aur ek hash part (jo verification ke liye ho). Flow aise chalta hai:
>
> 1. Server JSON ko **encode** karta hai (Base64URL) → milta hai ek encoded string, jaise `qwertyuiop`
> 2. Server usi encoded string ka **hash** bhi generate karta hai (jaise SHA-256 se) → milta hai ek hash, jaise `abc123`
> 3. Server dono ko **dot (.) se concat** karke ek combined token banata hai: `qwertyuiop.abc123`
> 4. Ye token client ko bhej diya jata hai
>
> Server ki soch: "agar client ne encoded part (`qwertyuiop`) ko change kiya, to uska hash bhi change ho jayega — aur agar naya hash, purane hash (`abc123`) se match nahi karega, to main pakad lunga ki tampering hui hai!"
>
> **Lekin problem ye hai:** Client bhi **wahi hashing algorithm** use kar sakta hai jo server use karta hai — kyunki hashing algorithms (jaise SHA-256) **public/standard** hote hain, koi secret nahi hote. To client kya karega:
>
> 1. Client pehle encoded part (`qwertyuiop`) ko **decode** karega → usko asli JSON mil jayega
> 2. Client JSON me apni marzi ka change karega (jaise `ROLE_USER` ko `ROLE_ADMIN` bana dega)
> 3. Client naye JSON ko phir se **encode** karega → naya encoded part milega, jaise `zxcvbnm`
> 4. Client is naye encoded part (`zxcvbnm`) ka **khud hi hash generate** kar lega (kyunki hashing algorithm sabko pata hai, koi secret key nahi lagti) → naya hash milega, jaise `def456`
> 5. Client dono ko dot se concat karega: `zxcvbnm.def456`
> 6. Client ye poora naya token server ko bhej dega
>
> Ab jab server ko ye token milega, wo check karega: "encoded part ka hash nikaalo aur dekho ki wo doosre part (jo bheja gaya hash hai) se match karta hai ya nahi." Aur guess kya — **match ho jayega!** Kyunki client ne khud hi consistent tarike se dono parts banaye — usne encoded part change kiya, aur uska sahi hash bhi khud generate karke bhej diya.
>
> **Isliye ye scheme bhi fail ho jati hai** — kyunki hashing me koi **secret** involved nahi hoti. Server aur client dono ke paas **same public algorithm** hai, isliye client bhi bilkul wahi verification-passing combination bana sakta hai jo server expect karta hai. Server ko koi tareeka nahi mila ye pata karne ka ki ye hash **sirf server ne** banaya tha ya **client ne khud bana liya.**

To agar hum Encoding par trust nahi kar sakte, agar hum Hashing par trust nahi kar sakte — to hum kya kar sakte hain?

---

## Teesra Attempt: Encryption (JWE)

Hum in sab se move kar sakte hain ek better technique par, jiska naam hai **Encryption**.

Agar aapne kisi ko encrypt kar diya, to usko aap decrypt bhi kar sakte ho — lekin aapko ek **Key** ki zaroorat padegi.

To ab wapas samajhte hain — jaise server ne ek JSON banaya login successful hone ke baad. Konsa JSON? — jo server chahta ho ki client ke paas store ho, aur wo usme kuch change bhi na kar paaye.

Ab hamara server sabse pehle isko karega **encode**.

Encode kyun kar raha hai? Kyunki JSON se ek string mil paaye. Jaise JSON hai, usko encode karne ke baad string mili — `qwertyuiop`.

Fir is encoded string ko **encrypt** karega, using a key. Maanlo encrypt karne ke baad humein mila — `zxcvbnm`.

Ab server is encrypted string ko client ke paas bhej dega.

Ab client jo hai, isme kuch **read hi nahi kar sakta** — matlab is token se actual JSON me convert hi nahi kar sakta. Kyun nahi kar sakta? Kyunki iske paas wo **Key** nahi hogi.

Chunki client ab is token ko modify nahi kar sakta, to wo agli request me bhi yahi same token bhejega.

Fir hamara server us encrypted string me se information nikaal lega using that key, aur Authentication object bana lega. Matlab server us string ko **decrypt** kar lega.

Lekin ye jo humne dekha, wo JWT nahi hai — wo hai **JWE**.

**JWE** ko hum bolte hain **JSON Web Encryption**.

Aap isko use kar sakte ho, koi dikkat nahi hai — lekin aap itna hardly kyun use karo? Ab ek simple cheez use karo — jaise ki **JWT**.

Isme aap encryption ki bajaye use karte ho ek **Signature**.

---

## JWT — Asli Solution: Signature

Signature kya hota hai? Signature thoda alag kaam karta hai encryption se.

Signature ka matlab hota hai ki main text me agar kuch change hua, to hamare server ko pata lag jayega.

Ab server kya karega:

Server ke paas login request aayi, usne login successful hone ke baad information ka JSON banaya. Ab uska token banana hai hamare client ko bhejne ke liye, kyunki client usko store kar le aur aage ke liye usko baar-baar login na karna pade.

Ab isme bhi server pehle is JSON ko **encode** karega. Encode karne ke baad string mili — `qwertyuiop`.

Ab isko maine kar diya **Sign**.

Sign ka matlab hota hai maine ek **Key** ka use kiya. Example → `Signature(qwertyuiop, key)`.

Isko karne ke baad maanlo mujhe kuch aur String milti hai, jaise ki `asdfghjkl`. `asdfghjkl` mere signature ko represent karta hai.

Ab yahan par main kya karunga — jo encoded String hai aur maine signature generate kiya, unko encoded+hashing wale ki tarah `encoded.signature` bana dunga, aur client ko bhej dunga.

`qwertyuiop.asdfghjkl` — hamara token bana, jisme pehla part encoded part hai, aur second me hamara signature hai.

Ab isme client first part ko change kar sakta hai — matlab decode karke, change karke, wapas encode kar sakta hai. Usne ye to change kar diye —

**Lekin hamara client chahkar bhi second part ko — yaani signature wale ko — change nahi kar sakta.**

Kyunki maine ek **key** ka use karke isko sign kiya hai. Mere client ko nahi pata ki wo key kya hai — agar usko wo key pata hai, to hi wo jaake change kar sakta hai. Maine hashing to ki nahi ki wo ek hash method uthayega aur isko hash kar dega.

Agar maanlo client ne encoded wale part ko change kiya, aur second part se attach karke bhej diya — yaani signature wahi hai jo humne diya tha, bas usne encoded wale part ko change kiya —

Fir server pehla wala part pakdega, usko karega `sign(user ka diye hue token ka first part, key)` — to ek naya signature generate hoga, aur server check kar lega ki user wale token me jo second part hai, us se to ye alag hai.

Server ne client ko bol diya: "bhai wapas login karo, wapas authenticate karo."

---

## JWT Structure — Teen Parts

Ek JWT token me 3 parts hote hain:
```
HEADER.PAYLOAD.SIGNATURE
```

Teeno parts ko isne Base64URL me encode kar rakha hai.

### Header
Header ke andar 2-3 information hoti hai:
```json
{
    "alg" : "HS256",
    "typ" : "jwt"
}
```
(`alg` batata hai kaunsa algorithm use kiya hai isko sign karne ke liye)

### Payload
Isme wo saari informations hoti hain jinka use karke hamara server Authentication ka object bana sakta hai.

### Signature banane ka process

Ab aap in dono ka Base64URL nikaloge, let's say:
```
Base64URL(header).Base64URL(payload)
```
Ye aapka ban jayega **signing input**.

Isko aapko sign karna hai. Fir aap ek signature generate karoge:
```
signature = sign(signingInput, key)
```

Fir aap is signature ka bhi Base64URL generate karoge:
```
Base64URL(header).Base64URL(payload).Base64URL(signature)
```

Ye combined string aapki kehlayegi **JWT token**.

Ye aap client ko bhej doge, aur client aapko bhej dega jab bhi koi request aayegi.
