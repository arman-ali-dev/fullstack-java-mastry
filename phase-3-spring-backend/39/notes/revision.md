# OAuth 2.0, OpenID Connect aur Google Login — My Notes

---

## 1. Apna Authentication System Chhodkar Google Pe Kyun Jaate Hain

Abhi tak hamara flow aisa tha:

User username aur password bhejta tha, Spring Boot use authenticate karta tha, application ek JWT banata tha, aur client us JWT ko future requests me bhejta tha.

Ab ek naya requirement aata hai — user chahta hai:

```
Continue with Google
```

Ab yahan par problem ye hai — hum user se ye nahi keh sakte:

```
POST /login-with-google

email=student@gmail.com
googlePassword=???
```

Hamari application ko user ka Google password kabhi nahi milna chahiye, na hi wo process karna chahiye, na store karna chahiye. Ye ek boundary hai jo cross nahi honi chahiye.

---

## 2. Delegated Access Ki Problem

Maano humne ek productivity app banayi, aur usme ek feature add kiya:

```
Connect your Google Calendar
```

Ab hamari application ko user ka Google Calendar padhne ki permission chahiye. Ek dangerous design ye ho sakta tha:

```
User apne Google credentials hamari application ko de deta hai
        ↓
Hamari application Google me login kar leti hai user ki taraf se
        ↓
Hamari application Google Calendar access kar leti hai
```

Isme do badi problems hain:

1. User ko apna Google password ek doosri (third-party) application ko dena padega.
2. Application ko zaroorat se zyada access mil jayega.

Same Google credentials se Gmail, Drive, Photos, Calendar, YouTube sab kuch expose ho sakta hai — jab ki application ko sirf calendar access chahiye tha.

**OAuth 2.0** isi problem ko solve karta hai — shared credentials ki jagah, ek **delegated, limited access** deta hai:

```
User seedha Google me sign in karta hai
        ↓
User application ko calendar padhne ki permission deta hai
        ↓
Google application ko ek access token deta hai
        ↓
Application us token ka use karke Google Calendar API se baat karti hai
```

> OAuth 2.0 basically ek framework hai **delegated authorization** ke liye.

Authorization ye answer karta hai: "aap kya karne ke liye allowed ho?"

---

## 3. OAuth Ke Charon Roles

### Resource Owner

Resource owner wo user hota hai jo protected data ka malik hota hai ya uspar control rakhta hai. Example: user khud.

### Client

Yahan par ek important cheez samajhni hai — OAuth me "client" ka matlab **browser nahi** hota. Iska matlab hai wo application jo access maang rahi hai.

Client ye ho sakta hai:
- Spring Boot application
- Mobile application
- Desktop application
- Browser-based application

### Authorization Server

Authorization server ye kaam karta hai:

```
User ko authenticate karna
        ↓
Authorization ya consent maangna
        ↓
Tokens issue karna
```

Example: Google ka authorization system.

### Resource Server

Resource server ke paas protected resource hota hai. Example: Google Calendar API. Ye request receive karta hai jisme access token attach hota hai:

```
Authorization: Bearer <access-token>
```

Ye token ko validate karta hai aur decide karta hai ki requested operation allowed hai ya nahi.

---

## 4. Scope Aur Consent

Authorization server ko exactly pata hona chahiye ki client kya access karna chahta hai. Isko represent karte hain **scopes** ke through.

Jaise, ek calendar application request kar sakti hai:

```
calendar.read
```

Iske bajaye ki wo pura unrestricted access maange user ke pure Google account ka.

> Scope represent karta hai wo access jo maanga ja raha hai ya diya ja raha hai.

Google fir ek consent screen dikha sakta hai:

```
CoderArmy App wants permission to:

✓ View your basic profile
✓ Read your calendar
```

User is request ko approve ya reject kar sakta hai. Yahi consent step hai.

```
Client specific scopes request karta hai
        ↓
Authorization server consent dikhata hai
        ↓
User requested access ko approve ya reject karta hai
```

---

## 5. OAuth Aur OIDC Ke Tokens

### Access Token

Authorization ke baad, client ko ek credential chahiye jo wo resource server ko de sake. Wo credential hai **access token**.

```
Authorization Server
        ↓ access token
Client
        ↓ bearer token
Resource Server
```

> Access token ek credential hai jo client use karta hai ek protected resource access karne ke liye.

Access token JWT ho sakta hai, lekin OAuth 2.0 iska JWT hona zaroori nahi maanta. Ye ek opaque, random-jaisi dikhne wali value bhi ho sakti hai.

Client ko generally access token ko ek opaque credential ki tarah treat karna chahiye, jab tak authorization server explicitly uska format document na kare.

### Authentication Ki Problem

OAuth delegated authorization solve karta hai: "ye client kya access kar sakta hai?"

Lekin login ke liye humein ek identity ka answer chahiye: "kaun user sign in hua hai?"

OAuth 2.0 akela ek identity protocol nahi hai. **OpenID Connect**, jise commonly OIDC bola jata hai, OAuth 2.0 ke upar authentication aur identity concepts add karta hai.

| Protocol | Main purpose | Main question |
|---|---|---|
| OAuth 2.0 | Delegated authorization | Ye application kya access kar sakti hai? |
| OpenID Connect | Authentication aur identity | Kaun sign in hua? |

### ID Token

OIDC ek naya token laata hai — **ID Token**. Ye client ke liye hota hai aur usme authentication event aur user ki identity ki information hoti hai.

ID token normally ek signed JWT hota hai aur usme claims aise ho sakte hain:

```json
{
  "sub": "123456789",
  "iss": "https://accounts.google.com",
  "aud": "our-google-client-id",
  "name": "Aditya",
  "email": "adi@gmail.com"
}
```

Important claims:

| Claim | Matlab |
|---|---|
| sub | User ki stable identifier is provider ke paas |
| iss | Kisne token issue kiya |
| aud | Kis client ke liye token issue hua |
| exp | Kab expire hoga |

### Refresh Token

Access tokens ko expire hona chahiye. Isliye ek **refresh token** issue kiya ja sakta hai, taaki client naya access token le sake bina user se dobara complete login aur consent process karwaye.

```
Access token expire ho jata hai
        ↓
Client refresh token bhejta hai authorization server ko
        ↓
Authorization server naya access token issue karta hai
```

Refresh token authorization server ke token endpoint ko bheja jata hai, kisi normal resource API ko nahi.

### Teeno Tokens Ka Comparison

| Token | Use kaun karta hai | Kisko bheja jata hai | Purpose |
|---|---|---|---|
| Access token | Client | Resource server | Protected API access karna |
| ID token | Client | Client application khud | Authentication verify karna aur identity info lena |
| Refresh token | Client | Authorization server | Naya access token lena |

> ID token access token ka replacement nahi hai. ID token ko use karke protected API mat call karo, jab tak wo API specifically ise support na kare.

---

## 6. Authorization Code Flow

Client ko tokens obtain karne hain bina unhe casually browser redirects me expose kiye. Authorization Code Flow ise 2 stages me handle karta hai:

1. Browser ko ek short-lived authorization code milta hai.
2. Backend us code ko tokens se exchange karta hai ek direct server-to-server request ke through.

### Step 1: Authorization Request Start Karna

User application pe aata hai aur click karta hai:

```
Continue with Google
```

Application browser ko redirect kar deti hai Google ke authorization endpoint pe.

Authorization request me ye values hoti hain:

```
client_id
redirect_uri
scope
response_type=code
state
```

#### client_id

Google application ko ek client ID deta hai jab wo register hoti hai.

```
client_id = coderarmy-app-123
```

Ye identify karta hai ki kaunsi application request kar rahi hai. Client ID ek identifier hai, secret nahi hai.

#### redirect_uri

Ye wo jagah hai jaha Google browser ko bhejega authentication aur authorization complete hone ke baad.

Local Spring Boot application ke liye:

```
http://localhost:8080/login/oauth2/code/google
```

Redirect URI exactly match honi chahiye us authorized URI se jo Google ke paas register ki gayi hai.

#### scope

Basic OIDC login ke liye, application request karti hai:

```
openid
profile
email
```

`openid` scope provider ko batata hai ki ye ek OpenID Connect request hai.

#### response_type=code

Ye authorization server ko batata hai ki final tokens directly browser redirect me daalne ke bajaye ek authorization code return kare.

#### state

Client redirect se pahle ek random state value generate karta hai:

```
Client state banata hai
        ↓
State authorization request ke saath travel karta hai
        ↓
Authorization server wahi state wapas return karta hai
        ↓
Client use verify karta hai
```

State value callback ko original request se correlate karta hai, aur authorization flow ko attacks jaise login CSRF se protect karne me help karta hai.

### Step 2: Google User Ko Authenticate Karta Hai

Ab browser Google ke domain pe hai. Google user ko authenticate kar sakta hai password, passkey, two-factor authentication, ya kisi aur supported mechanism se.

Important security boundary ye hai:

```
Google credentials browser se Google ko jaate hain.
Wo kabhi bhi hamari Spring Boot application ko nahi jaate.
```

### Step 3: User Consent Deta Hai

Google wo permissions dikhata hai jo application ne request ki hain. Basic login ke liye, ismein profile aur email information ho sakti hai. Calendar integration ke liye, screen calendar access bhi maang sakti hai.

Approval ke baad, Google ko pata hota hai ki:

```
User successfully authenticate hua hai
                +
User ne requested access approve kiya hai
```

### Step 4: Google Ek Authorization Code Return Karta Hai

Google browser ko redirect kar deta hai registered callback pe:

```
http://localhost:8080/login/oauth2/code/google
    ?code=A7D91XYZ
    &state=abc123
```

Authorization code ek short-lived, temporary credential hai jo successful authorization step ko represent karta hai.

```
Authorization code ≠ Access token
```

Code normally kisi resource API ko call karne ke liye use nahi hota. Iska purpose hai — exchange hona.

### Step 5: Backend Code Ko Tokens Se Exchange Karta Hai

Spring Boot backend Google ke token endpoint se contact karta hai:

```
Spring Boot backend
    ├── authorization code
    ├── redirect URI
    └── client authentication and/or PKCE proof
              ↓
Google token endpoint
              ↓
Access token + ID token + optional refresh token
```

### Front Channel vs Back Channel

| Channel | Communication path | Typical data |
|---|---|---|
| Front channel | User ke browser ke through | Authorization request, code aur state |
| Back channel | Directly backend aur token endpoint ke beech | Code exchange aur tokens |

Authorization code browser se guzarta hai, lekin final tokens back channel ke through obtain hote hain — isliye zyada secure hai.

---

## 7. PKCE — First Principles Se Samjho

Ek attacker jo authorization code chura leta hai, wo legitimate client se pehle usse exchange karne ki koshish kar sakta hai. PKCE authorization request ko us client instance se bind kar deta hai jo baad me token exchange karta hai.

PKCE ka matlab hai:

```
Proof Key for Code Exchange
```

Ise commonly bola jata hai "pixy".

### Proof Banana

Authorization start karne se pahle, client ek lamba, random value generate karta hai:

```
code_verifier = a-long-random-secret
```

Wo verifier ko hash karta hai, normally SHA-256 use karke:

```
SHA-256(code_verifier)
        ↓
code_challenge
```

### Authorization Request Ke Dauraan

Client `code_challenge` authorization server ko bhejta hai, lekin `code_verifier` apne paas rakhta hai.

```
Client ── code_challenge ──→ Authorization server
```

Authorization server us challenge ko us request ke saath yaad rakhta hai.

### Token Exchange Ke Dauraan

Client baad me bhejta hai:

```
authorization_code
code_verifier
```

Authorization server received verifier ko hash karta hai aur result ko original challenge se compare karta hai:

```
SHA-256(received code_verifier)
        ↓
   calculated challenge
        ↓
calculated challenge == original code_challenge?
```

Agar match ho gaye, server tokens issue kar sakta hai. Agar match nahi hue, request reject ho jati hai.

Attacker authorization code chura sakta hai, lekin verifier ke bina, chura hua code successfully exchange nahi ho payega.

> Client secret ek confidential client application ko authenticate karta hai. PKCE code exchange ko us client instance se bind karta hai jisne authorization request start ki thi. Ye related lekin different problems solve karte hain.

---

## 8. Application Ko Google Ke Saath Register Karna

Google ko pata hona chahiye kaunsi application login request bhej rahi hai, aur kaunse callback addresses trust kar sakta hai.

```
Application ko Google ke saath register karo
              ↓
Client ID aur Client Secret milega
              ↓
Allowed redirect URI register karo
```

Is local demo ke liye, authorized redirect URI hai:

```
http://localhost:8080/login/oauth2/code/google
```

Spring Security is default redirect URI template ko use karta hai:

```
{baseUrl}/login/oauth2/code/{registrationId}
```

Yahan:
```
baseUrl        = http://localhost:8080
registrationId = google
```

---

## 9. Spring Boot Application Ka Apna Flow

Application authentication ke liye Google use karti hai, lekin apna internal user record khud maintain karti hai:

```
Google user ko authenticate karta hai
              ↓
Spring ko ek OidcUser milta hai
              ↓
provider + provider subject se lookup karo
        ┌────┴────┐
    Nahi mila    Mil gaya
        ↓            ↓
     Insert      Update/load
        └────┬─────┘
              ↓
      Internal AppUser
              ↓
   Spring Security session
```

Google external identity ka malik hai. Hamari database application-specific information ki malik hai, jaise:
- Internal user ID
- Application role
- Account status
- Preferences
- Subscription ya membership data

---

## 10. Zaroori Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```

Ye OAuth2 client starter khud hi Spring Security support de deta hai OAuth2 Login ke liye. Alag se `spring-boot-starter-security` add karna optional ho jata hai jab ye starter already present ho.

Hamari application ko "OAuth client" kyu bolte hain?

```
Spring Boot application
              ↓
Google se authentication aur delegated access request karti hai
```

---

## 11. User Entity — Password Field Nahi Hai

`AppUser` entity me deliberately koi password field nahi hai. Google Google ka password verify karta hai aur uska malik hai — hamari application ko wo kabhi milta hi nahi.

### providerSubject Kyu Store Karte Hain?

Google ID token me `sub` claim include karta hai:

```json
{
  "sub": "109324823948234",
  "email": "student@gmail.com",
  "name": "Rohit"
}
```

`sub` ka matlab hai **subject**. Ye provider ka stable identifier hai us user ke liye.

Isliye hum store karte hain:

```
provider        = google
providerSubject = sub ki value
```

Provider aur subject ka combination email se better external identity key hai, kyunki email address badal sakta hai, missing ho sakta hai, ya unverified ho sakta hai depending on provider aur scopes.

Ek real database me, ye pair normally unique hona chahiye.

---

## 12. Repository — Existence Check Karna

```java
Optional<AppUser> findByProviderAndProviderSubject(
        String provider,
        String providerSubject
);
```

Ye method poochta hai: "kya hamari database me already ek user hai jiska `provider = google` aur `providerSubject = 123456` ho?"

---

## 13. Signup Logic — registerOrUpdate

Signup operation hamari application ke andar hota hai, Google ke authenticate karne ke baad.

Database flow:
```sql
SELECT *
FROM app_user
WHERE provider = 'google'
  AND provider_subject = '123';
```

- Agar user exist karta hai, latest profile details update karo aur internal account load karo.
- Agar user exist nahi karta, ek naya `AppUser` insert karo default `USER` role ke saath.

Yehi application ka OAuth/OIDC signup process hai.

---

## 14. Custom OIDC User Service — Built-in Processing Ke Upar Apna Logic

Spring Security ko already pata hai kaise OIDC provider se baat karni hai. Humein wo built-in processing preserve karni hai aur sirf apna application-specific database logic add karna hai.

```
Spring normal OIDC processing karta hai
              ↓
Spring ek OidcUser return karta hai
              ↓
Hamara code internal user save ya update karta hai
              ↓
OidcUser ko wapas Spring Security ko return karo
```

Is line ka matlab hai significant built-in processing:
```java
OidcUser oidcUser = delegate.loadUser(userRequest);
```

Conceptually, is stage tak:
```
Authorization code exchange ho chuka hai
              ↓
Access token aur ID token available hain
              ↓
Spring OIDC information validate aur process karta hai
              ↓
Spring UserInfo endpoint call kar sakta hai
              ↓
Spring ek OidcUser create karta hai
```

---

## 15. Security Configuration — oauth2Login()

```java
http
    .authorizeHttpRequests(auth -> auth
            .requestMatchers("/").permitAll()
            .anyRequest().authenticated()
    )
    .oauth2Login(oauth -> oauth
            .userInfoEndpoint(userInfo -> userInfo
                    .oidcUserService(customOidcUserService)
            )
            .defaultSuccessUrl("/profile", true)
    );
```

`oauth2Login()` configuration OAuth 2.0 Login aur OIDC flow ko enable karta hai. Spring Security in sab protocol endpoints ko manage karta hai:

```
Authorization endpoint → user ko Google pe redirect karna
Token endpoint         → code ko tokens se exchange karna
Redirection endpoint   → /login/oauth2/code/google
UserInfo endpoint      → extra profile claims obtain karna jab zaroori ho
```

---

## 16. Controller — Do Alag Systems Se Data

Login ke baad, response me ye milta hai:

```json
{
  "internalUserId": 1,
  "provider": "google",
  "subject": "108293472983749823749",
  "name": "Rohit Negi",
  "email": "example@gmail.com",
  "role": "USER"
}
```

Data do alag systems se aata hai:

| Data | Source |
|---|---|
| subject, name, email | Google/OIDC claims |
| internalUserId, role | Hamari application database |

---

## 17. Client Registration Properties

```properties
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
spring.security.oauth2.client.registration.google.scope=openid,profile,email
```

Kyunki Google Spring Boot ke liye ek recognized provider hai, normal authorization, token aur UserInfo endpoint URLs ko manually configure karne ki zaroorat nahi hoti.

### Client Secret Ko Hardcode Mat Karo

`application.properties` me placeholder rakho, aur actual values environment variables se do:
```
GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET
```

IntelliJ me: Run → Edit Configurations → Spring Boot application select karo → Environment variables. Yahan dono variables add karo.

Real client secret ko kabhi Git par commit mat karo ya public recording me mat dikhao.

---

## 18. Google Cloud Console Setup — Step by Step

1. **Google Cloud Project banao** — jaise `spring-oauth-demo`
2. **Google Auth Platform kholo** — pehli baar ho to "Get Started" select karo
3. **Branding configure karo** — App name, support email
4. **Audience select karo** — normal Gmail accounts ke liye `External` choose karo. Testing mode me ho to test users add karo.
5. **Scopes configure karo** — sirf `openid`, `profile`, `email` maango (least privilege principle — jitna zaroorat ho utna hi maango)
6. **OAuth Client banao** — Google Auth Platform → Clients → Create Client → Application type: `Web application`
7. **Authorized JavaScript origins khali chhodo** — kyunki ye server-side demo hai, browser-side JS SDK use nahi ho raha
8. **Authorized Redirect URI add karo** — exactly `http://localhost:8080/login/oauth2/code/google` — scheme, host, port, path sab match hona chahiye, warna `redirect_uri_mismatch` error aayega
9. **Client create karo** — Google `Client ID` aur `Client Secret` generate karega — dono ko environment variables me store karo

---

## 19. Running Aur Testing — Kya Actually Hota Hai

App start karo aur kholo `http://localhost:8080/`. Google login shuru karne ke liye jao:
```
http://localhost:8080/oauth2/authorization/google
```

### Spring Is Request Ke Saath Kya Karta Hai

```
GET /oauth2/authorization/google
              ↓
Spring ClientRegistration "google" dhoondta hai
              ↓
Spring authorization request construct karta hai
              ↓
Spring ek 302 redirect bhejta hai
              ↓
Browser Google ke authorization endpoint pe khulta hai
```

Google authentication ke dauraan, credentials `localhost:8080` ko kabhi submit nahi hote — seedha Google ko jaate hain.

### Callback Aur Code Exchange

Successful authentication aur consent ke baad, Google browser ko redirect karta hai:
```
http://localhost:8080/login/oauth2/code/google?code=ABC...&state=XYZ...
```

Spring response validate karta hai aur back-channel code exchange karta hai Google ke token endpoint se. Fir OIDC result process karke ek `OidcUser` banata hai.

`CustomOidcUserService` corresponding internal `AppUser` save ya update karta hai, aur success handler redirect kar deta hai `/profile` pe.

---

## 20. Login Ke Baad `/profile` Kaise Authenticate Hota Hai

Ye ek **important point** hai. Is traditional server-side OAuth2 Login flow me, browser Google ka ID token har request ke saath **nahi bhejta**.

Google login complete hone ke baad:
```
Spring user ko authenticate karta hai
              ↓
Authentication SecurityContext me store hota hai
              ↓
SecurityContext HTTP session se associate hota hai
              ↓
Browser session cookie bhejta hai future requests me
```

Isliye ye application login ke baad **session-based** hai.

`oauth2Login()` aur `oauth2ResourceServer()` do alag use-cases hain:

| Feature | Typical use |
|---|---|
| `oauth2Login()` | Browser user ko application me login karwana, session maintain karna |
| `oauth2ResourceServer().jwt()` | API ko protect karna jo bearer access tokens receive karta hai |

Agar ek alag frontend ya mobile application ko hamari API ko bearer tokens se call karna ho, to us architecture ke liye ye server-side session login demo alag approach chahiye hoga.

---

## 21. Complete Flow Recap

1. User `/oauth2/authorization/google` kholta hai
2. Spring browser ko Google pe redirect karta hai
3. Google user ko authenticate karta hai
4. User requested scopes approve karta hai
5. Google authorization code ke saath wapas redirect karta hai
6. Spring back channel ke through code exchange karta hai
7. Spring OIDC response validate aur process karta hai
8. Spring ek `OidcUser` create karta hai
9. `CustomOidcUserService` `AppUser` save ya update karta hai
10. Spring authenticated state ko session me store karta hai
11. User `/profile` ko ek authenticated user ki tarah access karta hai

### Final Mental Model

```
Google password       → Google ke paas hi rehta hai
Authorization code    → browser se guzarta short-lived value
Access token          → protected resource access karne ke liye
ID token              → client ko batata hai kaun authenticate hua
Refresh token         → naye access tokens lene ke liye
provider + subject    → Google identity ko internal user se link karta hai
session cookie        → browser ko humari Spring app ke saath authenticated rakhta hai
```

> Google external identity prove karta hai. Hamari application abhi bhi apne internal user record, roles aur business rules ki malik hai.
