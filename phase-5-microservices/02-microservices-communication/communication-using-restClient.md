# RestClient: Notes

**Topics covered:**

1. RestTemplate Recap
2. Fluent API
3. RestClient Flow
4. RestClient Demo

---

## 1. RestTemplate Recap

Pichle lecture me hum ne **RestTemplate** se ek service se doosri service ko HTTP call kiya tha.

```java
String url = "http://localhost:8082/users/" + userId;
UserDTO user = restTemplate.getForObject(url, UserDTO.class);
```

### RestTemplate me kya dikkatein thi

| Dikkat           | Explanation                                                                                   |
| ---------------- | --------------------------------------------------------------------------------------------- |
| Maintenance mode | Spring ab isme naye features nahi daalta                                                      |
| Verbose          | Har call ke liye alag-alag overloaded methods (`getForObject`, `getForEntity`, `exchange`...) |
| Fluent nahi      | Method chaining nahi hoti, code padhna mushkil                                                |
| URL string jodna | `"..." + userId` jaise string concatenation, galti hone ka chance                             |
| Purana design    | Naye Spring ke saath fit nahi baithta                                                         |

**Isi liye Spring ne naya client diya: `RestClient`.**

---

## 2. RestClient: Introduction

**RestClient** Spring ka **naya synchronous HTTP client** hai. Ye RestTemplate ka modern replacement hai.

- Ye bhi **synchronous (blocking)** hai: request bhejta hai aur response aane tak wait karta hai.
- Iska API **fluent** hai (method chaining wala).
- Ye Spring Framework 6.1 (Spring Boot 3.2) se available hai.
- Iske liye `spring-boot-starter-web` hi kaafi hai, alag dependency nahi chahiye.

### RestTemplate vs RestClient

| Point              | RestTemplate                  | RestClient                     |
| ------------------ | ----------------------------- | ------------------------------ |
| Status             | Purana, maintenance mode      | Naya, recommended              |
| API style          | Template methods (overloaded) | **Fluent** (method chaining)   |
| Readability        | Kam                           | Zyada                          |
| Sync / Async       | Synchronous                   | Synchronous                    |
| Base URL set karna | Mushkil (har baar poori URL)  | Aasan (`baseUrl()` ek baar)    |
| Error handling     | `ResponseErrorHandler`        | `onStatus()` (seedha chain me) |

---

## 3. Fluent API

### Fluent API kya hota hai?

Fluent API me hum **ek ke baad ek method chain** karte hain, aur code padhne me ek sentence jaisa lagta hai.

```java
restClient.get()
          .uri("/inventory/1")
          .retrieve()
          .body(Product.class);
```

Isko aise padho: _"GET request banao, is URI pe, response retrieve karo, aur body ko Product me badlo."_

### Fluent vs non-fluent (samajhne ke liye)

```java
// Non-fluent (RestTemplate style): sab kuch ek method ke arguments me
Product p = restTemplate.getForObject(url, Product.class);

// Fluent (RestClient style): step by step, har step ka naam clear
Product p = restClient.get()
        .uri("/inventory/{id}", id)
        .retrieve()
        .body(Product.class);
```

### Fluent API ke faayde

| Faayda          | Explanation                                             |
| --------------- | ------------------------------------------------------- |
| Padhne me aasan | Code ek sentence jaisa lagta hai                        |
| Har step clear  | Method, URI, headers, body alag-alag dikhte hain        |
| Flexible        | Sirf wahi step lagao jo chahiye (jaise header optional) |
| Kam galti       | Step by step likhne se galti kam hoti hai               |

### Builder ka concept

RestClient banane ke liye bhi fluent style (builder) use hota hai:

```java
RestClient restClient = RestClient.builder()
        .baseUrl("http://localhost:8082")
        .build();
```

---

## 4. RestClient Flow

Har RestClient call ka ek **fixed flow** hota hai. Ye 4 steps yaad rakho:

```
1. HTTP method chuno     →  get() / post() / put() / delete()
2. URI batao             →  uri("/inventory/{id}", id)
3. (Optional) Details    →  header(), contentType(), body()
4. Response lo           →  retrieve() ke baad body() / toEntity() / toBodilessEntity()
```

### Flow ka diagram

```
restClient
    │
    ├── .get() / .post() / .put() / .delete()     ← Step 1: kaunsa HTTP method
    │
    ├── .uri("/inventory/{id}", id)                ← Step 2: kahan bhejna hai
    │
    ├── .contentType(...)  .body(...)  .header()   ← Step 3: optional (POST/PUT me body)
    │
    ├── .retrieve()                                ← Step 4: request bhejo, response lo
    │
    └── .body(Product.class)                       ← Step 5: response ko object me badlo
        .toEntity(Product.class)                      (ya status/headers ke saath)
        .toBodilessEntity()                           (ya bina body ke, jaise DELETE)
```

### Har step ka matlab

| Step | Method                                 | Kaam                                                          |
| ---- | -------------------------------------- | ------------------------------------------------------------- |
| 1    | `get()`, `post()`, `put()`, `delete()` | HTTP method chunna                                            |
| 2    | `uri(...)`                             | Kis URL pe call karni hai. `{id}` jaise placeholders ke saath |
| 3    | `contentType()`, `header()`, `body()`  | Request me JSON body ya headers bhejna                        |
| 4    | `retrieve()`                           | Request bhejna aur response lena                              |
| 5    | `body(Class)`                          | Response body ko Java object me badalna                       |

### Response lene ke 3 tareeke

| Method                     | Kab use karo                                                             |
| -------------------------- | ------------------------------------------------------------------------ |
| `.body(Product.class)`     | Sirf response body chahiye                                               |
| `.toEntity(Product.class)` | Body ke saath **status code aur headers** bhi chahiye (`ResponseEntity`) |
| `.toBodilessEntity()`      | Response me body nahi hai (jaise DELETE, PUT)                            |

### `retrieve()` kya karta hai?

`retrieve()` request ko actually bhejta hai aur response ke saath kaam karne ka tareeka deta hai. Agar response me **4xx ya 5xx** status aaye toh RestClient by default **exception throw** karta hai.

### Error handling: `onStatus()`

```java
Product product = restClient.get()
        .uri("/inventory/{id}", id)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
            throw new RuntimeException("Product not found");
        })
        .body(Product.class);
```

Matlab: _"Agar 4xx error aaye, toh ye exception throw karo."_

---

## 5. RestClient Demo

**Setup:** do services.

```
┌─────────────────┐                      ┌───────────────────┐
│  Order Service  │ --- RestClient ----> │ Inventory Service │
│   port: 8081    │ <--- response -----  │    port: 8082     │
└─────────────────┘                      └───────────────────┘
  (client)                                 (server)
```

Inventory Service ke endpoints:

| Method | Endpoint          | Kaam                   |
| ------ | ----------------- | ---------------------- |
| GET    | `/inventory/{id}` | Product ka stock dekho |
| POST   | `/inventory`      | Naya product add karo  |
| PUT    | `/inventory/{id}` | Stock update karo      |
| DELETE | `/inventory/{id}` | Product hatao          |

### Step 1: RestClient ka bean (Order Service me)

```java
@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8082")
                .build();
    }
}
```

**`baseUrl` kyu?** Taaki har call me poori URL na likhni pade. Baad me sirf `/inventory/1` likhna hai.

### Step 2: Product DTO (Order Service me)

```java
public class ProductDTO {
    private Long id;
    private String name;
    private int stock;

    // constructors, getters, setters
}
```

### Step 3: Order Service me calls

```java
@Service
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // GET
    public ProductDTO getProduct(Long id) {
        return restClient.get()
                .uri("/inventory/{id}", id)
                .retrieve()
                .body(ProductDTO.class);
    }

    // POST
    public ProductDTO addProduct(ProductDTO product) {
        return restClient.post()
                .uri("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .body(ProductDTO.class);
    }

    // PUT
    public void updateStock(Long id, ProductDTO product) {
        restClient.put()
                .uri("/inventory/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .toBodilessEntity();
    }

    // DELETE
    public void deleteProduct(Long id) {
        restClient.delete()
                .uri("/inventory/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
```

### Step 4: Controller se use karna

```java
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final InventoryClient inventoryClient;

    public OrderController(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    @GetMapping("/check/{productId}")
    public String checkStock(@PathVariable Long productId) {
        ProductDTO product = inventoryClient.getProduct(productId);
        return product.getName() + " ka stock: " + product.getStock();
    }
}
```

### Testing ka flow (Postman se)

| Kya test karna hai       | Request                                      |
| ------------------------ | -------------------------------------------- |
| Order Service se GET     | `GET localhost:8081/orders/check/1`          |
| Inventory me product add | `POST localhost:8082/inventory` (JSON body)  |
| Stock update             | `PUT localhost:8082/inventory/1` (JSON body) |
| Product delete           | `DELETE localhost:8082/inventory/1`          |

Pehle Inventory Service ko **seedha Postman se** test karo, phir Order Service ke through RestClient se. Isse pata chalega ki galti server me hai ya client me.

### RestTemplate vs RestClient: ek hi call, side by side

```java
// RestTemplate
String url = "http://localhost:8082/inventory/" + id;
ProductDTO p = restTemplate.getForObject(url, ProductDTO.class);

// RestClient
ProductDTO p = restClient.get()
        .uri("/inventory/{id}", id)
        .retrieve()
        .body(ProductDTO.class);
```

RestClient me base URL alag hai, URL placeholder (`{id}`) se banti hai, aur har step ka naam dikhta hai.

### Ab bhi jo problem bachi hai

RestClient RestTemplate se behtar hai, par **hardcoded URL** (`http://localhost:8082`) ab bhi hai. Production me service ka address badalta hai ya kai copies hoti hain. Isko aage **Service Discovery (Eureka)** aur **Feign Client** se solve karenge.

---

## Quick Revision (ek line me)

| Topic                | Ek line me                                                       |
| -------------------- | ---------------------------------------------------------------- |
| RestTemplate         | Purana client, maintenance mode, verbose                         |
| RestClient           | Naya synchronous client, fluent API, RestTemplate ka replacement |
| Fluent API           | Method chaining, code ek sentence jaisa padhta hai               |
| Flow                 | method chuno → `uri()` → (header/body) → `retrieve()` → `body()` |
| `baseUrl()`          | Ek baar URL set karo, baad me sirf path likho                    |
| `body(Class)`        | Response ko object me badalta hai                                |
| `toEntity()`         | Body + status + headers                                          |
| `toBodilessEntity()` | Jab response me body nahi (DELETE, PUT)                          |
| `onStatus()`         | 4xx / 5xx errors handle karne ke liye                            |
| Bachi hui problem    | Hardcoded URL, jo Eureka aur Feign se solve hogi                 |
