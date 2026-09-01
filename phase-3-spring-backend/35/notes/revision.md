# Spring Security — Simple Notes

Only the important points, in plain simple English. A few tricky topics are explained again in Hinglish at the end.

---

## 1. What Is Application Security

Security means protecting important things in an app from people who shouldn't touch them.

It's not just about data. It can also mean protecting:
- Money and transactions
- User accounts
- The app staying online (not crashed by attackers)
- Admin powers
- Secret keys and passwords

**Key idea:** Even if a frontend hides a button (like "Delete"), a user can still call that API directly using tools like Postman. So real protection must happen on the **server**, not just in the UI.

---

## 2. Assets, Threats, Trust Boundaries

- **Asset** = anything valuable you want to protect (data, money transfer, admin access, etc.)
- **Threat** = something that *could* harm an asset (a hacker, a bot, a bad script)
- **Vulnerability** = a weakness that makes an attack possible (e.g. app allows unlimited password tries)

**Simple formula:**
```
Threat + Vulnerability = Possible Attack
```

**Trust boundary** = any point where data moves from a "less trusted" place to a "more trusted" place. Example: Browser → Server is a trust boundary, because the browser is controlled by the user, not by you.

**Why is the browser "untrusted"?** Because the user controls their own browser. They can change values, use Postman, or send requests directly — bypassing whatever the frontend shows them. So the server must always double-check everything, not just trust what the frontend sends.

---

## 3. Authentication vs Authorization

- **Authentication** = "Who are you?" (proving identity)
- **Authorization** = "What are you allowed to do?" (checking permission)

Authentication always happens **first**, then authorization.

```
Request → Authenticate (who is this?) → Authorize (are they allowed?) → Allow/Deny
```

**Important:** Even a "successful login" doesn't mean the request is 100% safe — someone could have stolen valid login details from another website (called credential stuffing). So extra checks (like OTP, rate limiting) still matter.

---

## 4. Core Vocabulary in Spring Security

| Term | Simple Meaning |
|---|---|
| **Identity** | The "who" — a person, admin, or even another app/service |
| **Account** | The actual database record representing that identity |
| **Credentials** | Proof used to confirm identity (password, OTP, token) |
| **Principal** | The identity that's currently recognized inside Spring Security |
| **Authority** | A specific permission (e.g. `USER_DELETE`, `ROLE_ADMIN`) |
| **Role** | A named group of permissions (e.g. `ADMIN` role = many permissions bundled) |

**Important rule:** A user can never just *claim* a role themselves (like sending `"role": "ADMIN"` in a request). The role must come from a trusted place — the database, a verified token, etc.

---

## 5. The Authentication Object

`Authentication` is one of the most important things in Spring Security. It has **two stages**:

**Stage 1 — Before login is checked:**
```
principal = "aditya"
credentials = "password123"
authenticated = false
```
This just means: "someone says they are aditya, here's their password" — not yet confirmed.

**Stage 2 — After login is confirmed:**
```
principal = UserDetails(aditya)
authorities = [ROLE_USER]
authenticated = true
```
Now the system trusts this identity for the current request.

---

## 6. SecurityContext and SecurityContextHolder

- **SecurityContext** = a small box that holds the current `Authentication` (the logged-in user info)
- **SecurityContextHolder** = the place where Spring keeps that box, so any part of your code can access "who is the current user" without you passing it manually everywhere

Instead of writing:
```java
courseService.createCourse(request, currentUser); // passing user manually everywhere - messy
```
Spring lets any method just ask:
```java
SecurityContextHolder.getContext().getAuthentication();
```

**Why it must be cleared:** Servers reuse the same "thread" for different requests. If Spring didn't clear this info after each request, the next unrelated user might accidentally see the previous user's identity. So Spring always clears it at the end of each request.

---

## 7. Stateful vs Stateless Authentication

This answers: *"How does the server remember that I already logged in, for my next request?"*

### Stateful (Session-based)
- You log in once.
- Server stores your login info in a **session**, on the server.
- Server gives your browser a small ID (`JSESSIONID`) in a cookie.
- Next requests: browser sends that ID, server looks up who you are.

**Good:** Server can instantly cancel a session (force logout).
**Costly:** Server has to store data for every logged-in user.

### Stateless (Token-based)
- No session stored on the server.
- Every request must carry proof again — usually a **token** (like a JWT) in the request header.
- Server checks the token fresh, every single time.

**Good:** No server storage needed, scales easily.
**Downside:** Harder to instantly cancel a token (it stays valid until it expires, unless extra systems are built).

---

## 8. Tokens — JWT vs Opaque

| Type | What it means |
|---|---|
| **Self-contained token (JWT)** | The token itself holds readable info (like username, expiry) that the server can check without asking anyone else |
| **Opaque token** | Just a random string — server must ask another system ("is this token valid? who does it belong to?") |

**Bearer token** = "whoever has this token can use it" — just like cash. If someone steals it, they can use it too. That's why tokens must be protected (HTTPS, safe storage, not logged anywhere).

---

## 9. Spring Boot's Default Security Setup

When you add the Spring Security dependency, Spring Boot **automatically**:
1. Requires login for every request
2. Enables both **form login** (HTML login page) and **HTTP Basic** login
3. Creates one temporary **default user**:
   - username: `user`
   - password: a random value printed in your console at startup

This default user is only for quick testing — never for real production use.

**If you write your own security configuration** (`SecurityFilterChain` bean), Spring Boot backs off and lets you fully control it. But note: your custom filter chain does **not** automatically remove the default user — you also need to define your own `UserDetailsService` (or similar) to replace it.

---

## 10. The CSRF "403 Forbidden" Surprise

If a logged-in user sends a `POST`/`PUT`/`DELETE` request and gets `403 Forbidden`, it's easy to wrongly assume "they don't have permission."

But Spring Security **enables CSRF protection by default** — so a state-changing request can get blocked simply because it's **missing a CSRF token**, even if the login and permissions were totally fine.

**Quick way to tell errors apart:**
- `401 Unauthorized` → login/authentication itself failed or missing
- `403 Forbidden` → request was understood, but blocked (could be missing role, OR missing CSRF token)

---

## Quick Summary Table

| Topic | One-line meaning |
|---|---|
| Authentication | Confirms who you are |
| Authorization | Confirms what you're allowed to do |
| Asset | Anything valuable that needs protecting |
| Threat | Something that could cause harm |
| Vulnerability | A weakness that enables an attack |
| Trust boundary | A point where untrusted data enters your system |
| Principal | The currently recognized identity |
| Authority/Role | Specific permission / named group of permissions |
| Authentication object | Holds identity + credentials + permission info, before and after login check |
| SecurityContext | Small box holding the current login info |
| SecurityContextHolder | Place where Spring stores that box for the current request |
| Stateful auth | Server remembers you via a stored session |
| Stateless auth | No server memory — proof sent again every request |
| JWT | Self-readable token |
| Opaque token | Random token, needs external lookup to verify |
| Default Spring Boot user | Auto-created test user, not for production |
| CSRF 403 issue | A blocked write-request may be a missing CSRF token, not a permission issue |

---

## Hinglish me thoda detail — kuch tricky topics

### Authentication object ke do stages kyun hote hai?
Jab tum login form bharte ho, Spring pehle ek "unverified" `Authentication` object banata hai — usme bas tumhara claim hota hai ("main aditya hoon, ye mera password hai"), lekin `authenticated = false` hota hai, matlab abhi verify nahi hua. Fir Spring uss password ko database se compare karta hai. Agar match ho jaye, to Spring ek **naya** `Authentication` object banata hai jisme `authenticated = true` hota hai aur usme tumhari roles/permissions bhi add ho jati hain. Ye dono alag-alag objects hain — pehla sirf "claim", doosra "confirmed identity".

### SecurityContextHolder ki zaroorat kyun padi?
Socho tumhare paas Controller → Service → Repository jaisi layers hain, aur har jagah tumhe pata hona chahiye "current user kaun hai". Agar tum har method me manually current user pass karte, to code bahut messy ho jata. Isliye Spring ek global si jagah rakhta hai (`SecurityContextHolder`), jahan se koi bhi method, kisi bhi jagah se, bas "current user kaun hai" pooch sakta hai — bina usse manually pass kiye. Lekin ye jagah request-specific honi chahiye, isliye Spring har request ke end me isse clear kar deta hai — warna agla request (jo shayad kisi aur user ka ho) galti se pichhle user ki identity dekh sakta hai, kyunki server thread ko reuse karta hai.

### Stateful vs Stateless — kaunsa kab use karein?
Agar tumhara app ek traditional website hai jaha browser hi client hai (jaise ek college portal), to **Stateful (session)** simpler hai — server sab kuch yaad rakhta hai, user ko baar baar credentials nahi bhejne padte.

Agar tumhara app ek REST API hai jise mobile app, frontend, aur external clients sab use karte hain (jaise modern microservices), to **Stateless (token/JWT)** better hai — server ko kuch bhi store nahi karna padta, scaling easy hoti hai (multiple servers hone par bhi koi dikkat nahi hoti, kyunki koi session sync karne ki zaroorat nahi).

### JWT vs Opaque token — real difference kya hai?
JWT ek "self-explanatory" token hai — usme khud hi likha hota hai ki ye kiska hai, kab expire hoga, kya permissions hain — server sirf uska signature check karta hai (jaise ek sealed letter jisme sab kuch likha hai, bas seal check karni hai ki asli hai ya nahi).

Opaque token sirf ek random string hoti hai — usme khud kuch nahi likha hota. Server ko ek doosre system (jaise ek "token database") se poochna padta hai "ye token valid hai kya, kiska hai" — jaise ek locker key jo khud kuch nahi batati, sirf locker room walo se poochna padta hai kis locker ki hai.

### CSRF wala 403 error itna confusing kyun hai?
Jab tum login karke ek POST/PUT/DELETE request bhejte ho aur `403 Forbidden` milta hai, to sabse pehla khayal aata hai "shayad mere paas permission nahi hai". Lekin Spring Security by default **CSRF protection** on rakhta hai — matlab har state-changing request (jo kuch change karti hai, sirf padhti nahi) ke saath ek extra "CSRF token" bhi hona chahiye. Agar wo missing ho, to request reject ho jati hai — chahe tumhare paas sahi permissions ho ya na ho. Isliye jab bhi `403` mile aur permissions sahi lagein, to CSRF token check karna bhi ek zaroori step hai.
