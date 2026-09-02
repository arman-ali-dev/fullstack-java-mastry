# Database Authentication & Password Security — Simple Notes

Only the important points, in plain simple English. Tricky topics explained again in Hinglish at the end.

---

## 1. Authentication Needs a "Source of Truth"

When a user submits `username + password`, the app can't magically know if it's correct. It needs to check against **trusted, stored information** (a source of truth).

```
Claimed identity + password
        ↓
   Compare against
        ↓
   Trusted stored info (database)
```

This trusted info can live in: memory, a database (MySQL/Mongo), LDAP, or an external identity service. In this app, it's a **MySQL database**.

---

## 2. Why the Default Spring Boot User Isn't Enough

Spring Boot's auto-generated `user` (with a random password) is fine for quick testing — but a real app can have **thousands of users**, each with their own password, roles, and status. This info must survive restarts and deployments — so it must be stored in a **real database**, not memory.

---

## 3. UserDetails — Spring Security ko Aapke Database ka Structure Pata Nahi Hota

Har app apna database alag tarike se design karti hai — table names alag, column names alag (username/email, password/pwd, role/userType). Spring Security sabko ek hi structure follow karne ke liye force nahi kar sakta, kyunki wo ek generic framework hai jo har tarah ke project me use hota hai.

Isiliye Spring Security ek standard interface deta hai jiska naam hai UserDetails. Aapke app ka apna database model (jaisa bhi ho) is standard shape me adapt/convert kiya jata hai, taaki Spring Security uske saath kaam kar sake — chahe aapka actual database kaisa bhi dikhta ho.

```
Aapka database model → Adapter → Spring Security ka UserDetails
```

Simple words me: Table structure jo bhi ho, ek "adapter" class use karke usko UserDetails ke fixed format me convert kar diya jata hai — uske baad Spring Security authentication ka poora kaam khud handle kar leta hai.


### UserDetails contains:
1. **Identity** → `getUsername()`
2. **Credential** → `getPassword()`
3. **Authorities & account status** → roles, and flags like enabled/locked/expired

---

## 4. Identity vs Credential

- **Identity** = "who are you" → username or email
- **Credential** = "proof" → password

You don't have to use a traditional "username" — email, employee ID, phone number, anything can work, as long as it's **unique** for each account (should have a unique constraint in the database).

---

## 5. Account Status Flags

| Flag | Meaning |
|---|---|
| `enabled` | Account exists, but is it currently allowed to log in? |
| `Locked` | Account is fine, but temporarily blocked (e.g. too many failed logins) |
| `Account expired` | The account itself is no longer valid (e.g. contract ended) |
| `Credentials expired` | Password itself needs to be renewed (e.g. after 90 days) |

Not every app needs all of these — if your app doesn't support expiry, you can just always return `true` for that check.

---

## 6. UserDetails vs Your Own User Entity

Your app's `User` entity (with `email`, `profile`, `createdAt`, etc.) and Spring Security's `UserDetails` are **two different things**, from two different layers. You connect them with an **adapter**, but they're not treated as the same object.

```
Your User entity → adapt → Spring Security's UserDetails
```

---

## 7. Roles

- **Authentication** = who you are
- **Authorization** = what you're allowed to do (this is where roles matter)

A user can have one or more roles (e.g. `ROLE_USER`, `ROLE_ADMIN`). This is usually stored using 3 tables: `users`, `roles`, and a `user_roles` join table (because one user can have many roles, and one role can belong to many users — a many-to-many relationship).

---

## 8. The Missing Bridge — UserDetailsService

Even after you build a `UserRepository` to fetch users from the database, **Spring Security still doesn't automatically call it**. There's a missing piece:

```
Database → UserRepository → [Missing bridge] → Spring Security
```

That missing bridge is `UserDetailsService`. It's the class that actually connects your repository to Spring Security's authentication process — telling it "here's how to load a user's username, password, roles, and status."

---

## 9. Why Raw Passwords Should Never Be Stored

If you save the password exactly as the user typed it, and your database ever gets hacked, **every password is instantly exposed** — and since people often reuse passwords across websites, the damage spreads beyond just your app.

**Rule: never store a raw (plain-text) password.**

---

## 10. Why Encryption Is NOT the Right Fix

Encryption can be **reversed** (decrypted) using a key. If an attacker steals both the database AND the key, they can recover every original password — so encryption alone doesn't really solve the problem.

The real question: does the app ever actually need to see the original password again? **No** — it only needs to check "does this new attempt match what was saved before." So passwords need a **one-way** transformation, not a reversible one.

---

## 11. Password Hashing

Hashing = a one-way transformation. Once hashed, there's no way to turn it back into the original password.

```
secret123 → one-way hashing function → encoded value (stored in DB)
```

To check login: take the entered password, run the same check, and see if it matches the stored encoded value. Spring Security does this with:
```java
passwordEncoder.matches(rawPassword, encodedPassword);
```

---

## 12. Why a Fast Hash (like SHA-256) Isn't Enough

If a hacker steals your hashed passwords, they can try millions of common passwords (`123456`, `password`, etc.), hash each guess, and compare — this is called an **offline guessing attack**. A **fast** hash lets them try huge numbers of guesses quickly.

So password-hashing algorithms are deliberately made **slow/expensive** on purpose — this is called an "adaptive" hash. Spring Security recommends **BCrypt** for this.

---

## 13. BCrypt and PasswordEncoder

`PasswordEncoder` is the interface; `BCryptPasswordEncoder` is Spring Security's built-in implementation.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Two main operations:
```java
String encoded = passwordEncoder.encode("secret123");        // for saving
passwordEncoder.matches("secret123", encoded);                 // for checking login → true/false
```

The app never needs to "decrypt" anything — it always stays one-way.

---

## 14. Salt — Why Two Same Passwords Look Different

Even if two users pick the exact same password, a **salt** (random extra data) is added before hashing, so their stored values still come out **different**. This protects against attackers using pre-made lists of common password hashes (rainbow-table attacks).

Good news: BCrypt stores the salt **inside** its own output — so you don't need a separate salt column.

---

## 15. Registration Flow (Putting It All Together)

```
Client sends: { username, password }
        ↓
   PasswordEncoder.encode(password)
        ↓
   BCrypt-encoded password
        ↓
   Save into User entity → Database
```

**Key rule:** the raw password should only exist briefly, while handling the request — it should never be logged, sent back in a response, or saved anywhere.

Also, a registration service should:
1. Check if the username is already taken
2. Encode the password
3. Save the user

---

## Quick Summary Table

| Topic | One-line meaning |
|---|---|
| Source of truth | Trusted stored data used to verify login claims |
| Default Spring Boot user | Good for testing only — not a real user system |
| UserDetails | Standard shape Spring Security needs, regardless of your DB design |
| Identity vs Credential | Who you are vs proof you're really them |
| Enabled/Locked/Expired | Different ways an account can be blocked from logging in |
| Domain User vs UserDetails | Two separate concepts, connected via an adapter |
| Roles | Define what an authenticated user is allowed to do |
| UserDetailsService | The missing bridge connecting your database to Spring Security |
| Raw password storage | Never do this — huge security risk |
| Encryption | Reversible — not right for passwords |
| Hashing | One-way — correct approach for passwords |
| Fast hash (SHA-256) | Too fast, makes guessing attacks easy — avoid for passwords |
| BCrypt | Slow/adaptive one-way hash, recommended for passwords |
| Salt | Random extra data added so identical passwords hash differently |
| encode() | Used when saving a new password |
| matches() | Used to check login (raw password vs stored hash) |

---

## Hinglish me thoda detail — kuch tricky topics

### Encryption aur Hashing me confusion kyun hota hai?
Log aksar sochte hain "encryption use kar lo, secure ho jayega" — lekin encryption **reversible** hoti hai, matlab agar tumhare paas sahi "key" ho, to encrypted value ko wapas original password me convert kar sakte ho. Ye password ke liye khatarnak hai — agar attacker ko database + key dono mil jaye, to sab passwords wapas nikal sakta hai.

Hashing bilkul alag hai — ye **one-way** hoti hai, matlab ek baar hash ho gaya, to usse wapas original password me convert karne ka koi tarika hi nahi hota. Login check karne ke liye bas ye dekha jata hai ki naya entered password, hash karne ke baad, wahi result deta hai jo pehle se stored hai ya nahi. Isiliye password ke liye hamesha **hashing** use hoti hai, encryption nahi.

### SHA-256 jaisa fast hash password ke liye kyun bekar hai?
SHA-256 bahut fast hai — file integrity check karne jaise cheezon ke liye ye acchi baat hai. Lekin password ke liye ye **problem** ban jati hai — agar attacker ko tumhara hashed password database mil jaye, to wo apne computer pe lakhon common passwords (`123456`, `password`, etc.) try kar sakta hai, unko hash karke compare kar sakta hai — aur fast hash hone ki wajah se ye process bahut jaldi ho jata hai.

Isiliye BCrypt jaisa algorithm jaan-boojh kar **slow** banaya jata hai — har guess ko check karne me thoda zyada time lagta hai. Ek user ke liye ye delay kuch milliseconds ka hota hai (koi farak nahi padta), lekin attacker jab lakhon guesses try karta hai, to total time bahut zyada badh jata hai — isse attack impractical ho jata hai.

### Salt hone ke bawajood matches() kaise kaam karta hai?
Ye confusing lagta hai — agar har encoding alag random salt use karti hai, to `matches()` kaise pata lagata hai ki password sahi hai?

Trick ye hai: BCrypt jo encoded value store karta hai, usme **salt khud bhi shamil hota hai** (ek hi string ke andar). Jab `matches(rawPassword, storedEncodedPassword)` call hota hai, to Spring us stored value ko padh ke usme se salt nikal leta hai, fir **wahi salt** use karke naye entered password ko hash karta hai, aur dono values compare karta hai. Isliye galat approach ye hoti "encode karke fir directly compare karo" — kyunki har baar naya random salt milega, values kabhi match hi nahi karengi. Sahi tarika hai `matches()` use karna, jo internally purana salt reuse karta hai verification ke liye.

### UserDetails aur apni khud ki User entity alag kyun rakhte hain?
Ye thoda confusing lag sakta hai ki humne ek `User` entity bhi banayi hai aur Spring Security ka `UserDetails` bhi hai — dono same jaisa lagta hai. Lekin inka purpose alag hai:

- **`User` entity** = tumhare app ka apna data model — jisme email, profile, createdAt jaisi cheezein ho sakti hain, jo Spring Security se koi lena dena nahi rakhtin.
- **`UserDetails`** = sirf ek standard "contract" jo Spring Security ko chahiye authentication ke waqt — sirf username, password, authorities, account status.

In dono ko humesha ek "adapter" ke through connect karte hain (jaise `UserDetailsService` isi kaam ke liye hota hai) — taaki tumhara business data model (`User`) aur Spring Security ka authentication requirement (`UserDetails`) ek doosre se tightly coupled na ho. Kal agar tumhe `User` entity me naye fields add karne hon (jaise `profilePicture`), to Spring Security ke authentication logic ko touch karne ki zaroorat nahi padegi.
