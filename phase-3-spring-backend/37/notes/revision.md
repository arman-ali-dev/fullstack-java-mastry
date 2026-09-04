# Spring Security — Filter Architecture & Authentication Flow 

---

## UserDetails, CustomUserDetails, CustomUserDetailsService

**UserDetails** = ye jo hai wo ek wrapper hai hamare user model ka, ye bas ek interface hai — iski implementation chahiye.

**CustomUserDetails** = isme hum implementation likhte hain.

Lekin koi chahiye jo iska object banaye aur Spring Security ko de authentication ke liye.

**CustomUserDetailsService** = ye ek service class hai jo ki username ke basis par ek user ko `UserRepository` ke jariye database se load karta hai, aur `CustomUserDetails` object me wrap karke return kar deta hai. Fir Spring Security ka ek service class is `UserDetails` ke object ke saath authentication kar sakti hai.

---

## Tomcat aur DelegatingFilterProxy

Hamara Tomcat sirf ek servlet se baat karta hai Spring Security me, jiska naam hai **DelegatingFilterProxy**.

`DelegatingFilterProxy` iklota aisa filter hai jiske baare me Tomcat ko pata hai.

Tomcat sirf isi ko call kar sakta hai.

Tomcat ko koi aur filter nahi pata jo exist karta hai.

Tomcat ko lagta hai ki honge aur bahut saare filters.

Bas mera kaam hai koi bhi request aayegi to main `DelegatingFilterProxy` par bhej dunga.

`DelegatingFilterProxy` khud ek filter hai.

Iska kaam hai ki aayi hui request ko aage forward kar dena — kahan par? → **FilterChainProxy**

`DelegatingFilterProxy` ne call kiya `FilterChainProxy` ko.

---

## FilterChainProxy aur SecurityFilterChain

`FilterChainProxy` ke paas multiple chains hoti hain filters ki, jaise:
```
chain1
chain2
chain3
and so on...
```

Har chain ka naam **SecurityFilterChain** hota hai.

`FilterChainProxy` ke paas multiple `SecurityFilterChain` hue.

Ab jaise ki request sabse pahle Tomcat par aayi, fir `DelegatingFilterProxy` ke paas, yahan se `FilterChainProxy` par aayi. Ab inme se request sirf **ek** `SecurityFilterChain` ke paas jayegi.

### Ye kaise decide hota hai ki konsi request kis SecurityFilterChain ke paas jayegi?

Ye decide hota hai ki konsa `SecurityFilterChain` kis API endpoint ke upar lag sakta hai.

Jaise ki ek `SecurityFilterChain` sirf `/api/**` par lag sakta hai — matlab jo requests aayengi unme se jinka endpoint `/api/**` se start hoga, wo requests is `SecurityFilterChain` ke paas jayengi.

Matlab ye iska ek tarah se **matcher** ho gaya `/api/**`.

Ab jaise dusra `SecurityFilterChain` sirf lagta hai un requests ke liye jinka endpoint `/admin/**` se start hota hai.

Aur ek jo hai wo sabke liye lagega jo bhi bach gayi, jaise ki `/**`.

Isme basically **if-else ladder** ki tarah kaam hota hai — jaise ki ek `SecurityFilterChain` ka matcher satisfy nahi hua to dusra check hoga. Agar dusra satisfy hota hai to usi `SecurityFilterChain` me chale jao, agar nahi to next check karo ki aapka endpoint iske kisi matcher se match hota hai kya.

Aur har `SecurityFilterChain` ke andar **Filters ki list** hoti hai.

Har `SecurityFilterChain` me filters ek dusre se connect hote hain — ek dusre ka `doFilter()` method call kar rahe hote hain.

Sabme alag-alag filters ki chain hoti hai.

### SecurityFilterChain ka structure

```
SecurityFilterChain: {
    matcher : "/admin/**",
    filters : [ filter1, filter2, filter3 ]
}
```

Aise hi dusra `SecurityFilterChain` ho sakta hai jiska matcher alag hoga, aur aise hi teesra jiska bhi matcher alag hoga — and so on.

Toh jaise hum ek Spring Security ke project ko kholte hain, to by default ek hi `SecurityFilterChain` hota hai jiska matcher hota hai `/**` — iska matlab har request authenticated honi chahiye, har request iske paas jaati hai by default.

### SecurityFilterChain — interface

```java
public interface SecurityFilterChain {
    boolean matches(HttpServletRequest request);
    // ye match karta hai ki jo request ka endpoint aaya hai
    // wo is SecurityFilterChain ke matcher se match karti hai ki nahi — true/false return karta hai

    List<Filter> getFilters();
    // aapko wo saare filters mil jaayenge jo bhi is SecurityFilterChain me aate honge
}
```

Jaise ki maano ki koi request aayi jiska endpoint hai `/admin/add`, to sabse pahle ye ek `SecurityFilterChain` ke paas jayegi jiska matcher hoga `/api/**` — ye usse poochhegi "kya aap mujhe entertain karoge?" wo bolega "nahi".

Fir dusre `SecurityFilterChain` ke paas jayegi jiska matcher hoga `/admin/**` — usse poochhegi, to wo bolega "haan main to isi endpoint ki requests ko entertain karta hoon".

### Kya hum is SecurityFilterChain ki implementation manually banayenge?

**Answer hai: Nahi.**

Yahan hamare paas Spring Security me **HttpSecurity** class hoti hai jo ki basically return karti hai ek `SecurityFilterChain`.

```
HttpSecurity returns SecurityFilterChain
```

Hum kya karte hain? Hum `HttpSecurity` class ka use karke ek object banate hain `SecurityFilterChain` ka.

Hum `SecurityFilterChain` ka object banayenge using `HttpSecurity` class, fir hum us object me bahut saare filters daalenge, aur matcher bhi daalenge.

**That's it — yahi hai pura ka pura architecture.**

---

## DefaultSecurityFilterChain

Ab jaise ki maine koi bhi `SecurityFilterChain` ka object nahi banaya, to hamari har request authenticated kyu hoti hai by default? Iska matlab hai ki koi na koi by default `SecurityFilterChain` ka object Spring Security ne banaya hoga — to answer hai **yes**.

Uska naam hai **DefaultSecurityFilterChain**.

```
DefaultSecurityFilterChain: {
    matcher : any request,
    filters : [
        SecurityContextHolderFilter,
        CsrfFilter,
        LogoutFilter,
        UsernamePasswordAuthenticationFilter,
        BasicAuthenticationFilter,
        AnonymousAuthenticationFilter,
        ExceptionTranslationFilter,
        AuthorizationFilter
    ]
}
```

Kya humein sab filters ke saath kaam karna hai? Answer hai **nahi**.

Humein sirf `UsernamePasswordAuthenticationFilter`, `BasicAuthenticationFilter` in filters ke saath kaam karna hai.

---

## Form Login vs HTTP Basic

Ab dekho, authentication karne ke basically **2 tarike** hote hain:
1. **Form login** — basically form ke through, jisme ek form hota hai aur ek submit button hota hai
2. **HTTP Basic** — basically Postman ke through

Form login ko handle karne ke liye use karte hain `UsernamePasswordAuthenticationFilter`.

HTTP Basic ke through login karoge to filter use hoga `BasicAuthenticationFilter`.

Ab hum 2 filters ke saath kaam karne wale, jo ki ye hai kyunki hum chahte hain ki hamara jo user hai wo Postman se yaani HTTP Basic se bhi login kar sake, aur form ke through bhi login kar sake.

Agar wo Postman ke through login karega to `BasicAuthenticationFilter` lagega, aur agar form ke through login karega to `UsernamePasswordAuthenticationFilter` lagega.

### Flow

```
FilterChainProxy → SecurityFilterChain (jo by default hai) → filters (BasicAuthenticationFilter, UsernamePasswordAuthenticationFilter)
```

Kisi bhi `SecurityFilterChain` me filters hote hain na, unka kaam hota hai basically **Authentication Object Create karna**.

Konsa Authentication object? → Ek **Request Authentication** object.

### Konsa filter chalega ye kaise decide hota hai?

Ye dekhna hai ki `BasicAuthenticationFilter`, `UsernamePasswordAuthenticationFilter` me se konsa filter chalega jab request aayegi.

> **Correction:** Content-type in dono filters ka trigger **nahi** hai. Actual mechanism:
> - **`BasicAuthenticationFilter`** activate hota hai jab request me `Authorization: Basic <base64>` **header present** ho — content-type se koi lena dena nahi.
> - **`UsernamePasswordAuthenticationFilter`** activate hota hai jab request ka **URL match** kare login processing URL se (default `/login`) aur method `POST` ho — ye `request.getParameter("username")` se values leta hai, jo normally form-urlencoded body se kaam karta hai.
>
> Sahi soch: "kaunsa filter activate hoga" ye depend karta hai ki request me kya **present hai** (header ho ya URL+params ho), content-type pe nahi.

---

## Authentication Object / Token Banna

Ab jaise ki maan ke chalo ek `POST /login` ki request aati hai aur uski request body hoti hai:
```json
{
    "username" : "Arman",
    "password" : "5911"
}
```

Ab un dono filters ka kaam hai ek **Authentication Object** banana.

Ab ye jo `Authentication` hai wo khud ek interface hai, usko implement karega `UsernamePasswordAuthenticationToken`.

Agar ye build hoga to aapko pata hai iske paas kon-konsi fields hongi?
```
principal = "Arman"
credentials = "5911"
authorities = []
isAuthenticated = false
```

### Is object ko kon create karega?

`BasicAuthenticationFilter`, `UsernamePasswordAuthenticationFilter` — ye filters create karenge.

Inme se konsa filter is authentication ke object ko create karega → depending upon content-type.

`UsernamePasswordAuthenticationToken` ek Authentication ka **type** hai.

Ab jis filter ne is authentication ke object ko create kiya, us filter ka kaam khatam.

Ab is request authentication ke object ko authenticate karne ka kaam kisi aur ka hai.

---

## AuthenticationManager

Ab ye filters kya karte hain — is request object ko authentication ke liye kisi dusre object ko lekar de dete hain, jiska naam hota hai **AuthenticationManager**.

Filters ne bola `AuthenticationManager` ke object ko ki "ye lo sir, Authentication request ka object, aur ab aap isko authenticate karo".

Ab hamare `AuthenticationManager` ki zimmedari hai ek authentication object (jo abhi tak authenticated nahi hua) usko convert karna ek aise authentication ke object me jiska authentication ho chuka hai.

Jiska authentication nahi hua uske paas field hogi `isAuthenticated : false`, aur jiska ho gaya uske paas bhi field hogi `isAuthenticated` bas wo `true` ho jayegi.

### Jo authenticated nahi hua us authentication ka object:

```json
{
    "principal" : "Arman",
    "credentials" : "5911",
    "authorities" : [],
    "isAuthenticated" : false
}
```

### Jo authenticated ho gaya us authentication ka object:

```json
{
    "principal" : "UserDetails(\"Arman\")",
    "credentials" : null,
    "authorities" : ["ROLE_USER", "ROLE_ADMIN"],
    "isAuthenticated" : true
}
```

### AuthenticationManager — interface

```java
public interface AuthenticationManager {
    Authentication authenticate(Authentication auth);
}
```

`AuthenticationManager` jo hai wo kisi bhi `Authentication` ki implementation ko accept kar sakta hai.

### Implementations of Authentication:
1. `UsernamePasswordAuthenticationToken`
2. `JwtAuthentication`
3. `One-Time Password`

Ab ye alag-alag kyu hain kyunki `UsernamePasswordAuthenticationToken` me aap principal doge aur credentials doge. `JwtAuthentication` me JWT token doge. `One-Time Password` me aap OTP doge.

In sabka parent kon hai: **Authentication**.

Iska matlab `AuthenticationManager` kisi bhi type ko authenticate kar sakta hai.

Ab `AuthenticationManager` kehta hai ki "main sab type ko accept kar lunga, lekin ab main khud ek interface hoon isliye mujhe bhi implementations chahiye sabke liye alag-alag".

---

## ProviderManager aur AuthenticationProvider

### AuthenticationManager's Implementations:

Hum in sabhi implementation classes ko **Provider** bolte hain.

1. `DaoAuthenticationProvider`
2. `JwtAuthenticationProvider`
3. `CustomOtpAuthenticationProvider`

> **Correction:** Ye teeno `AuthenticationProvider` (interface) ki implementations hain — **"ProviderManager ke types" nahi hain**. Sahi relationship:
> ```
> AuthenticationManager (interface)
>        ↑ implement karta hai
> ProviderManager   ← ye AuthenticationManager ki main implementation hai
>
> ProviderManager ke paas hoti hai ek LIST of →
> AuthenticationProvider (interface)
>        ↑ implement karte hain
> DaoAuthenticationProvider, JwtAuthenticationProvider, CustomOtpAuthenticationProvider
> ```
> `ProviderManager` in sabki ek list rakhta hai, aur jo authentication token aaya hai uske hisaab se sahi wala provider chun leta hai (`supports()` check karke).

---

## DaoAuthenticationProvider — Kyun Use Karenge?

Abhi `DaoAuthenticationProvider` use karenge.

**Yahi kyun use karenge? — claude bhai tum batao**

> **Answer:** Kyunki hamara use-case hai **username + password se database ke against login karna** — aur `DaoAuthenticationProvider` specifically isi kaam ke liye design kiya gaya hai. "DAO" ka matlab hai Data Access Object — yaani ye provider pehle apne "data source" (hamare case me database) se user ka data fetch karta hai, fir uske stored password ko submitted password se compare karta hai.
>
> Agar hum JWT token se login karwa rahe hote, to hum `JwtAuthenticationProvider` use karte. Agar OTP-based login hota, to `CustomOtpAuthenticationProvider`. Har `AuthenticationProvider` ek specific "authentication mechanism" ke liye bana hota hai, aur uska kaam hota hai sirf us ek type ke `Authentication` token ko samajhna aur verify karna.
>
> `DaoAuthenticationProvider` ke andar do cheezon ki zaroorat padti hai:
> 1. **UserDetailsService** — taaki wo username se user ko database se load kar sake (`UserDetails` object ke roop me)
> 2. **PasswordEncoder** — taaki wo submitted raw password ko database me stored encoded password se compare kar sake (`matches()` method se)
>
> Chunki hamara poora use-case (`username + password` se DB-backed login) exactly yehi requirement match karta hai, isliye `DaoAuthenticationProvider` hi sabse sahi choice hai — ye Spring Security ka **ready-made component** hai jo specifically username-password + database authentication ke liye banaya gaya hai, humein khud se ye logic likhne ki zaroorat nahi padti.

Kya humein manually ise banana padega? Answer hai **nahi** — Spring Security khud se humein bean banake dega, lekin hum seekhne ke liye bana sakte hain.

```java
@Bean
public DaoAuthenticationProvider authenticationProvider(
        CustomUserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder) {

    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
}
```

Ab humne ek bean bana liya `DaoAuthenticationProvider` ka, aur aapko pata hai `DaoAuthenticationProvider` 2 interfaces se interact karta hai:
1. Pehla `UserDetailsService`, kyunki isko `UserDetails` ka object chahiye
2. Fir ek `PasswordEncoder` se interact karta hai — for matching the password

Ab depending upon `matches()` `true` return karega ya `false`, hum ya to `Authentication` ka object return kar denge, otherwise ek `Exception`.

---

## Apni Khud Ki SecurityFilterChain Banana

Ab dekho, aapko pata hai humne koi `SecurityFilterChain` nahi banayi, to Spring Security by default ek `SecurityFilterChain` banake rakhta hai jo ki har endpoint ko authenticate rakhta hai.

Lekin humein kuch endpoints aise rakhne chahiye jo authenticate na ho, jaise for example `/register`.

To hum khud ka ek `SecurityFilterChain` ka bean bana sakte hain.

Agar hum `SecurityFilterChain` nahi banayenge, to Spring Security apna by default `SecurityFilterChain` bana lega.

Agar hum khud ka `SecurityFilterChain` bana lenge, to Spring Security apna by default `SecurityFilterChain` nahi banayega.

Ab **HttpSecurity** builder pattern use karega `SecurityFilterChain` ke object ko create karne ke liye.

```java
@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity,
        DaoAuthenticationProvider provider) {

    httpSecurity.authenticationProvider(provider)
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers("/api/users/register").permitAll()
                            .anyRequest().authenticated()
            );

    return httpSecurity.build();
}
```

### Is method ka line-by-line explanation

```java
@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity,
        DaoAuthenticationProvider provider) {
```
Ye ek `@Bean` method hai — Spring isse call karega aur jo return hoga (`SecurityFilterChain`), use container me register kar dega. Isko do parameters chahiye: `HttpSecurity` (builder object, Spring khud provide karta hai) aur hamara khud ka banaya `DaoAuthenticationProvider` bean (jo humne upar define kiya tha) — Spring dono ko automatically inject kar dega.

```java
httpSecurity.authenticationProvider(provider)
```
Ye `HttpSecurity` ko batata hai: "authentication ke liye is `DaoAuthenticationProvider` ko use karna" — yaani jab bhi koi login attempt aayega, uska verification isi provider se hoga (jo humne `UserDetailsService` + `PasswordEncoder` ke saath banaya tha).

```java
.formLogin(Customizer.withDefaults())
```
Ye form-login support **enable** karta hai — yaani `UsernamePasswordAuthenticationFilter` chain me add ho jayega. `Customizer.withDefaults()` ka matlab hai "Spring ki default settings use karo" (jaise default `/login` URL, default login page, etc.) — hum chahein to isme custom settings bhi de sakte hain, lekin abhi defaults hi kaafi hain.

```java
.httpBasic(Customizer.withDefaults())
```
Isi tarah, ye HTTP Basic authentication **enable** karta hai — yaani `BasicAuthenticationFilter` bhi chain me add ho jayega. Isi wajah se ab user dono tarike se login kar sakta hai: form ke through bhi, Postman/`Authorization: Basic` header ke through bhi.

```java
.authorizeHttpRequests(auth ->
        auth.requestMatchers("/api/users/register").permitAll()
                .anyRequest().authenticated()
);
```
Ye **authorization rules** define karta hai — yaani kaunsi request ke liye login zaroori hai, kaunsi ke liye nahi:
- `.requestMatchers("/api/users/register").permitAll()` → is specific endpoint (register) ke liye **koi authentication nahi chahiye** — koi bhi bina login kiye ise call kar sakta hai (jo sahi bhi hai, kyunki naya user register hi tab kar payega jab wo abhi tak login nahi kar sakta).
- `.anyRequest().authenticated()` → **baaki har request** ke liye authentication zaroori hai. Ye rule hamesha **sabse last** likha jata hai kyunki rules order me check hote hain — agar isko pehle likh dete, to `/register` ke liye bhi authentication maang leta.

```java
return httpSecurity.build();
```
Aakhri me, `.build()` call karke saari ye configuration (`authenticationProvider`, `formLogin`, `httpBasic`, `authorizeHttpRequests`) ko combine karke ek actual **`SecurityFilterChain` object** bana deta hai — yahi object return hota hai aur Spring container me bean ban jata hai. Yehi wo custom chain hai jo ab **default** `SecurityFilterChain` ki jagah use hogi.

**Poore method ka summary ek line me:** "Is DaoAuthenticationProvider ko use karo authentication ke liye, form-login aur HTTP-basic dono enable karo, `/register` ko khula rakho baaki sab endpoints ko login-protected banao, aur ye poori configuration ek SecurityFilterChain object me convert karke do."
