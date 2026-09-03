# Spring Security Database Authentication — Simple Notes

## 1. Where Spring Security Fits
Before your request reaches the Controller, it passes through filters.
Order: `Request → Servlet Filters → Spring Security Filters → Controller`
Security checks "who are you" and "are you allowed" before the Controller runs.

## 2. DelegatingFilterProxy
This is a bridge between Tomcat (servlet container) and Spring.
It just forwards the request to the real Spring-managed filter.

## 3. FilterChainProxy
The main security filter. It picks the correct `SecurityFilterChain` for each request based on URL pattern.

## 4. SecurityFilterChain
A chain = a URL pattern + a list of filters to run for that pattern.
Example: `/api/**` has one set of filters, `/admin/**` has another.

## 5. HttpSecurity
A builder used in your config class to set up rules — CSRF, login type, which URLs need authentication, etc.
```java
http.formLogin(...).authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
```

## 6. Authentication Filters
Different login types use different filters:
- Form login (`username=...&password=...`) → `UsernamePasswordAuthenticationFilter`
- HTTP Basic (`Authorization: Basic ...`) → `BasicAuthenticationFilter`

A filter only acts when it sees a matching request type — it doesn't run auth logic on every request.

## 7. Authentication Token
Username & password get wrapped into a `UsernamePasswordAuthenticationToken`.
Before verification, it's just a **claim** (not proven yet):
```
principal = "aditya"
credentials = "secret123"
authenticated = false
```

## 8. AuthenticationManager
The entry point that verifies the token. It either returns success (verified) or throws an error.

## 9. ProviderManager & AuthenticationProvider
`ProviderManager` is the common implementation of `AuthenticationManager`.
It has many `AuthenticationProvider`s (one for DB login, one for JWT, one for OTP, etc.) and picks the right one.

## 10. Connecting Your Database
Spring Security doesn't know your `User` entity directly. So two contracts exist:
- `UserDetails` → Spring's view of one user
- `UserDetailsService` → logic to find that user

## 11. UserDetails
An interface with basic info Spring Security needs: username, password, roles, and account status (enabled/locked/expired).

## 12. Two Different "User" Classes
Your own `User` (database entity) is different from Spring's built-in `User` class. You usually write a `CustomUserDetails` class to convert one into the other.

## 13. UserDetailsService
Just one method:
```java
UserDetails loadUserByUsername(String username);
```
It's the "finder" — goes to your repository/database and gets the user.

## 14. DaoAuthenticationProvider
The component that actually does the DB login work:
1. Load user via `UserDetailsService`
2. Get stored (encoded) password
3. Compare with entered password using `PasswordEncoder`
4. Check account status (enabled, locked, etc.)
5. Return success or failure

## 15. Complete Login Flow (Short Version)
```
Request → Auth Filter → Unverified Token → AuthenticationManager
→ ProviderManager → DaoAuthenticationProvider
   ├── UserDetailsService (loads user from DB)
   └── PasswordEncoder (checks password)
→ Verified Token → SecurityContext → Authorization → Controller
```

## 16. When Authentication Fails
- User not found → `UsernameNotFoundException`
- Wrong password → `BadCredentialsException`
- Account disabled/locked/expired → login rejected even with correct password

## 17. Form Login vs HTTP Basic
Both use different filters to grab credentials, but both end up going through the same `DaoAuthenticationProvider` for DB checking.

## 18. Registration vs Login
- **Registration:** raw password → `passwordEncoder.encode()` → save encoded password to DB
- **Login:** raw password → `passwordEncoder.matches(raw, storedEncoded)` → true/false

⚠️ Never let the user pick their own role (like `ROLE_ADMIN`) during registration — server should always assign `ROLE_USER` by default.

## 19. Typical Project Structure
```
entity/       → User.java, Role.java
repository/   → UserRepository, RoleRepository
dto/          → RegisterRequest, UserResponse
security/     → CustomUserDetails, CustomUserDetailsService
service/      → AuthService
controller/   → AuthController
config/       → SecurityConfig
```

## 20. Key Beans in SecurityConfig
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
public DaoAuthenticationProvider authenticationProvider(...) {
    // links UserDetailsService + PasswordEncoder
}
```

## 21. CSRF Note
For simple API testing (like Postman demos), CSRF is often disabled temporarily:
```java
.csrf(csrf -> csrf.disable())
```
This is fine for learning/demo — not a general production rule.

## 22. Testing a Protected Endpoint
```java
@GetMapping("/me")
public Map<String, Object> me(Authentication authentication) {
    return Map.of("username", authentication.getName(), ...);
}
```
Call this without login → blocked. Call with correct credentials → returns user info.

---

## Quick Summary Table

| Topic | One-line takeaway |
|---|---|
| Filter order | Security filters run before Controller |
| DelegatingFilterProxy | Bridges Tomcat filters to Spring beans |
| FilterChainProxy | Picks the right SecurityFilterChain by URL |
| SecurityFilterChain | URL pattern + list of filters |
| HttpSecurity | Builder to configure the security chain |
| Authentication Filters | Extract credentials, don't verify DB |
| Auth Token | Represents claim before, proof after |
| AuthenticationManager | Entry point to verify identity |
| ProviderManager | Picks the right AuthenticationProvider |
| UserDetails | Spring's view of one user |
| UserDetailsService | Finds the user (via your DB) |
| DaoAuthenticationProvider | Does actual DB + password check |
| Full Flow | Filter → Token → Manager → Provider → DB check → SecurityContext |
| Auth Failures | Wrong user / wrong password / bad account state |
| Registration | encode() password, assign default role |
| Login | matches() raw vs stored password |

---

## Hinglish me thoda detail

### 1. DelegatingFilterProxy vs FilterChainProxy (confusing pair)
Ye dono alag cheez hain. `DelegatingFilterProxy` ek chhota sa "postman" hai — Tomcat se request aati hai, ye usko bas Spring ke andar bheji deta hai, khud kuch logic nahi karta. Uske andar jo asli kaam karne wala hai wo hai `FilterChainProxy` — ye decide karta hai ki is request ke liye kaunsa `SecurityFilterChain` chalega (jaise `/api/**` ke liye alag rules, `/admin/**` ke liye alag). Simple socho: DelegatingFilterProxy = reception counter, FilterChainProxy = us building ka manager jo decide karta hai konsa department (chain) handle karega.

### 2. AuthenticationManager vs ProviderManager vs AuthenticationProvider (sabse confusing)
Teeno naam similar lagte hain isliye confusion hoti hai. Socho ek company hai:
- `AuthenticationManager` = ek job description (interface) — "mujhe koi verify kare"
- `ProviderManager` = HR manager jo ye kaam actually karta hai. Uske paas alag-alag specialists (providers) hote hain
- `AuthenticationProvider` = actual specialist jo ek specific type ka login samajhta hai (DB wala alag, JWT wala alag, OTP wala alag)

Jab username-password aata hai, `ProviderManager` dekhta hai "isko kaun handle kar sakta hai?" aur DB wale case me `DaoAuthenticationProvider` ko bhej deta hai.

### 3. Authentication Token — "before" aur "after" wala confusion
Same class `UsernamePasswordAuthenticationToken` do jagah use hoti hai but do alag matlab ke saath:
- **Pehle (unauthenticated):** sirf ek claim hai — "main aditya hoon, password ye hai" — abhi prove nahi hua
- **Baad me (authenticated):** ab ye verified identity ban gaya, password field clear/erase kar diya jata hai (security ke liye)

Isko aise yaad rakho: pehle wala ek "form fill karke submit karna", baad wala "verified ID card mil jana".

### 4. UserDetails vs UserDetailsService (naam bahut similar hai)
Ye dono ek doosre ke saath confuse hote hain kyunki naam almost same hai.
- `UserDetails` = **ek result** — ek user ki info (jaise ek object jisme username, password, roles hai)
- `UserDetailsService` = **ek dhoondhne wala method** — "username do, main tumhe user dhoondh ke doonga"

Analogy: `UserDetailsService` ek waiter hai jo order leta hai aur kitchen (database) se plate (UserDetails) laa ke deta hai.

### 5. Apna User class vs Spring ka User class
Confusion ye hoti hai ki dono ka naam `User` hi hai. Tumhara `com.yourapp.entity.User` wo hai jo tum database me store karte ho (id, username, password, roles etc.). Spring Security ka apna bhi ek `User` class hai (`org.springframework.security.core.userdetails.User`) jo sirf login ke liye use hota hai. Tumhe apna `User` seedha Spring ko nahi dena — beech me ek adapter (`CustomUserDetails`) banana padta hai jo tumhare `User` ko Spring ke samajhne wale format me convert kare.

### 6. DaoAuthenticationProvider ka pura kaam ek line me
"DAO" ka matlab hai Data Access Object — yaani ye provider database se user nikaalta hai aur fir password match karta hai. Isko chhota sa 3-step process samjho: **User dhoondo → Password check karo → Account status check karo (disabled/locked toh nahi)**. Agar teeno pass ho gaye tabhi login successful hota hai — sirf sahi password kaafi nahi hai agar account disabled hai toh bhi login fail hoga.
