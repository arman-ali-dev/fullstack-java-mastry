# Synchronous Microservice Communication: RestTemplate (Legacy Approach)

**Topics covered:**

1. Setup 2 Spring Boot Services
2. Communication Using RestTemplate
3. Why RestTemplate Is Deprecated

---

## 0. Recap: Synchronous communication

Ek service doosri service ko HTTP request bhejti hai aur **response aane tak wait** karti hai. Is lecture me ye kaam **RestTemplate** se karenge.

**RestTemplate kya hai?** Spring ka ek class jo ek Java application se doosre REST API ko HTTP call karne ke liye use hota tha. Matlab hamara Java code ek **client** ban jaata hai, aur doosri service **server**.

**Legacy approach kyu kehte hain?** Kyunki ye purana tareeka hai. Naye projects me Spring ab iski jagah naya client (RestClient) recommend karta hai. Iske details section 3 me hain.

---

## 1. Setup 2 Spring Boot Services

Communication samajhne ke liye hume **do alag Spring Boot projects** chahiye, jo alag ports pe chalein.

```
┌─────────────────┐                      ┌─────────────────┐
│  Order Service  │ ---- HTTP call ----> │  User Service   │
│   port: 8081    │ <--- response -----  │   port: 8082    │
└─────────────────┘                      └─────────────────┘
   (client banti hai)                      (server banti hai)
```

**Scenario:** Order Service ko order banate waqt user ki details chahiye. Wo User Service se maangti hai.

### Dono services ke liye setup

| Cheez      | User Service              | Order Service                       |
| ---------- | ------------------------- | ----------------------------------- |
| Project    | Alag Spring Boot project  | Alag Spring Boot project            |
| Dependency | `spring-boot-starter-web` | `spring-boot-starter-web`           |
| Port       | `8082`                    | `8081`                              |
| Kaam       | User ki details deti hai  | User Service se details maangti hai |

**Port alag kyu?** Dono services ek hi machine pe chal rahi hain, aur ek port pe ek hi application chal sakti hai. Isliye alag ports chahiye.

Port badalne ke liye `application.properties` me:

```properties
# User Service
server.port=8082
```

```properties
# Order Service
server.port=8081
```

### User Service ka code (server side)

**DTO:**

```java
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    // constructor, getters, setters
}
```

**Controller:**

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return new UserDTO(id, "Aditya", "aditya@example.com");
    }
}
```

Ab browser ya Postman me `http://localhost:8082/users/1` hit karo, toh user ki JSON details milengi. **Pehle ye test kar lo**, taaki pata chale User Service sahi chal rahi hai.

---

## 2. Communication Using RestTemplate

Ab Order Service se User Service ko call karenge.

### Step 1: RestTemplate ka bean banana

`RestTemplate` apne aap bean nahi banta. Hume ek `@Bean` banana padta hai, taaki Spring ise inject kar sake.

```java
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

**Bean kyu?** Taaki ek hi `RestTemplate` object poori application me reuse ho, aur hum use `@Autowired` / constructor injection se le sakein.

### Step 2: Order Service me UserDTO

Order Service ko response ko Java object me badalna hai, isliye wahan bhi ek `UserDTO` chahiye jisme **wahi fields** hon jo User Service bhej rahi hai.

```java
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    // constructor, getters, setters
}
```

**Alag copy kyu?** Dono services alag projects hain, wo code share nahi karte. Isliye har service apna DTO rakhti hai. Jackson JSON ko field names ke basis pe map karta hai.

### Step 3: Order Service se call karna

```java
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{userId}")
    public String placeOrder(@PathVariable Long userId) {

        String url = "http://localhost:8082/users/" + userId;

        UserDTO user = restTemplate.getForObject(url, UserDTO.class);

        return "Order placed for " + user.getName();
    }
}
```

### Is code me kya ho raha hai

1. Client `http://localhost:8081/orders/1` hit karta hai.
2. Order Service ka `placeOrder()` chalta hai.
3. Wo `restTemplate.getForObject(...)` se User Service ko HTTP GET bhejti hai.
4. **Order Service yahan wait karti hai**, jab tak User Service jawab nahi deti (ye synchronous hai).
5. User Service JSON bhejti hai, aur RestTemplate (Jackson ke through) use `UserDTO` me badal deta hai.
6. Order Service us user ka naam use karke response banati hai.

### Flow ka diagram

```
Client
  │  GET /orders/1
  ▼
Order Service (8081)
  │  restTemplate.getForObject("http://localhost:8082/users/1")
  ▼
User Service (8082)
  │  returns JSON {id, name, email}
  ▼
Order Service (JSON → UserDTO, aage ka kaam)
  │
  ▼
Client ko response
```

### RestTemplate ke important methods

| Method                                 | Kaam                                                                            |
| -------------------------------------- | ------------------------------------------------------------------------------- |
| `getForObject(url, Class)`             | GET call, response seedha object me                                             |
| `getForEntity(url, Class)`             | GET call, response `ResponseEntity` me (status code aur headers bhi milte hain) |
| `postForObject(url, request, Class)`   | POST call, response object me                                                   |
| `postForEntity(url, request, Class)`   | POST call, response `ResponseEntity` me                                         |
| `put(url, request)`                    | PUT call                                                                        |
| `delete(url)`                          | DELETE call                                                                     |
| `exchange(url, method, entity, Class)` | Kisi bhi HTTP method ke liye, full control (headers, body)                      |

### `getForObject` vs `getForEntity`

|              | `getForObject`     | `getForEntity`                             |
| ------------ | ------------------ | ------------------------------------------ |
| Return       | Sirf body (object) | `ResponseEntity` (body + status + headers) |
| Kab use karo | Sirf data chahiye  | Status code ya headers bhi dekhne hain     |

### POST ka example

```java
UserDTO newUser = new UserDTO(null, "Rahul", "rahul@example.com");

UserDTO saved = restTemplate.postForObject(
        "http://localhost:8082/users", newUser, UserDTO.class);
```

### Agar User Service down ho toh?

Order Service ko exception milti hai (jaise `ResourceAccessException` agar connection nahi hua, ya `HttpClientErrorException` agar 4xx aaya, `HttpServerErrorException` agar 5xx aaya). Agar hum ise handle nahi karte, toh Order Service ka request bhi fail ho jaata hai. Ye synchronous communication ka wahi nuksaan hai jo pehle padha tha: **ek service fail toh doosri bhi atak jaati hai.**

---

## 3. Why RestTemplate Is Deprecated

### Pehle ek clarification

RestTemplate ke baare me "deprecated" shabd aam taur pe bola jaata hai, par thoda sa sahi tareeka ye hai:

| Kab                    | Kya hua                                                                                                |
| ---------------------- | ------------------------------------------------------------------------------------------------------ |
| Spring 5.0 se          | RestTemplate **maintenance mode** me gaya. Sirf bug fixes, koi naya feature nahi                       |
| Spring 6.1             | Naya **RestClient** aaya (modern replacement)                                                          |
| Spring 7 / Boot 4      | RestTemplate ko hatane ka plan shuru hua (deprecation path), aur Spring RestClient recommend karta hai |
| Aage ke version (plan) | Poori tarah remove hone ka plan                                                                        |

Matlab abhi purane projects me RestTemplate **chalta hai**, par naye projects me use karna **recommended nahi** hai. Exact version aur removal timeline ke liye official Spring documentation dekhna.

### Problems jo RestTemplate me hain

| Problem                      | Explanation                                                                                                                        |
| ---------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| **Maintenance mode**         | Naye features nahi aate, Spring ka focus naye clients pe hai                                                                       |
| **Verbose / boilerplate**    | Har call ke liye URL banao, class do, exception handle karo. Code lamba hota hai                                                   |
| **Purana API design**        | Bahut saare overloaded methods (`getForObject`, `getForEntity`, `exchange`...), confusing aur fluent nahi                          |
| **Hardcoded URLs**           | `http://localhost:8082/...` code me likha hai. Service ka address badla toh code badalna padega. Production me ye bahut kharab hai |
| **Service discovery nahi**   | Ye apne aap nahi jaanta ki User Service kahan chal rahi hai ya uski kitni copies hain                                              |
| **Blocking**                 | Thread tab tak ruka rehta hai jab tak response na aaye. Zyada load pe threads khatam ho sakte hain                                 |
| **Resilience built-in nahi** | Retry, timeout handling, circuit breaker jaise features apne aap nahi milte                                                        |
| **Type-safety kam**          | URL string se banti hai, typo hui toh error runtime pe pata chalta hai, compile time pe nahi                                       |

### Hardcoded URL ka problem aur detail me

```java
String url = "http://localhost:8082/users/" + userId;
```

Ye line microservices me kai problems laati hai:

- Production me User Service ka address `localhost:8082` nahi hoga.
- Agar User Service ki **3 copies** chal rahi hain (scaling), toh RestTemplate ko pata nahi kaunsi copy ko call kare.
- Address badla toh **har jagah code badalna** padega.

Isi problem ko hal karne ke liye aage ke lectures me **Service Discovery (Eureka)** aur **Feign Client** aayenge.

### Iska replacement kya hai?

| Replacement      | Kya hai                                                                             |
| ---------------- | ----------------------------------------------------------------------------------- |
| **RestClient**   | Naya synchronous client, fluent aur modern API. RestTemplate ka seedha replacement  |
| **WebClient**    | Non-blocking (reactive) client, async/streaming ke liye                             |
| **Feign Client** | Sirf ek interface likho, baaki Spring sambhalta hai. Microservices me bahut popular |

_(RestClient, Feign aur service discovery agle lectures me aayenge.)_

---

## Quick Revision (ek line me)

| Topic             | Ek line me                                                                      |
| ----------------- | ------------------------------------------------------------------------------- |
| RestTemplate      | Ek Java service se doosri service ko HTTP call karne ka Spring ka purana client |
| 2 services setup  | Alag projects, alag ports (jaise 8081 aur 8082), `spring-boot-starter-web`      |
| Bean kyu          | `RestTemplate` ko `@Bean` banana padta hai, taaki Spring inject kar sake        |
| `getForObject`    | GET call, sirf body milti hai                                                   |
| `getForEntity`    | GET call, body + status + headers milte hain                                    |
| Synchronous kaise | Order Service tab tak wait karti hai jab tak User Service jawab nahi deti       |
| Legacy kyu        | Maintenance mode me hai, verbose hai, hardcoded URL, resilience nahi            |
| Replacement       | RestClient, WebClient, Feign                                                    |
