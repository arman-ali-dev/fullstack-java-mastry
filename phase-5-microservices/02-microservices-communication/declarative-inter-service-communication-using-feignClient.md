# Declarative Inter-Service Communication Using FeignClient: Notes

**Topics covered:**

1. What Is Feign?
2. How To Enable Feign In Spring Boot
3. Create Feign Client
4. Use Feign Client For Inter-Service Communication
5. FeignClient Flow & Internal Working

---

## 0. Pehle ka safar (recap)

| Approach         | Style                                  | Problem                                                     |
| ---------------- | -------------------------------------- | ----------------------------------------------------------- |
| **RestTemplate** | Template methods                       | Purana, verbose, maintenance mode                           |
| **RestClient**   | Fluent API (method chaining)           | Better, par har call ke liye code **khud likhna** padta hai |
| **FeignClient**  | **Declarative** (sirf interface likho) | Isi lecture me                                              |

RestClient me bhi hume har call ke liye `get().uri().retrieve().body()` jaisa code likhna padta tha. Feign me ye bhi nahi likhna padta.

---

## 1. What Is Feign?

**Feign** ek **declarative HTTP client** hai. Hume sirf ek **interface** likhna hota hai, aur baaki HTTP call ka saara code Spring khud bana deta hai.

Spring Boot me iska use **Spring Cloud OpenFeign** ke through hota hai.

### Declarative ka matlab

| Style                       | Matlab                                                                                                |
| --------------------------- | ----------------------------------------------------------------------------------------------------- |
| **Imperative** (RestClient) | Tum batate ho **kaise** karna hai: step by step code                                                  |
| **Declarative** (Feign)     | Tum batate ho **kya** chahiye: interface me bas declare karo, kaise karna hai wo framework dekhta hai |

### Same kaam, teen tareeke se

```java
// RestTemplate
String url = "http://localhost:8082/inventory/" + id;
ProductDTO p = restTemplate.getForObject(url, ProductDTO.class);

// RestClient
ProductDTO p = restClient.get()
        .uri("/inventory/{id}", id)
        .retrieve()
        .body(ProductDTO.class);

// Feign: bas ek method call
ProductDTO p = inventoryClient.getProduct(id);
```

Feign me call aisi lagti hai jaise **apni hi service ka koi local method** call kar rahe ho, jabki peeche se network call ho rahi hai.

### Feign ke faayde

| Faayda                           | Explanation                                                                           |
| -------------------------------- | ------------------------------------------------------------------------------------- |
| Kam code                         | Sirf interface, koi implementation nahi                                               |
| Readable                         | Method call jaisa lagta hai                                                           |
| Spring annotations               | `@GetMapping`, `@PathVariable` jaise wahi annotations jo controller me use karte hain |
| Service discovery ke saath achha | Eureka ke saath sirf service ka naam likhna hota hai (aage ke lecture me)             |
| Integration                      | Load balancing, circuit breaker jaise features ke saath aasani se judta hai           |

---

## 2. How To Enable Feign In Spring Boot

Feign enable karne ke 2 steps hain.

### Step 1: Dependency

Feign **Spring Cloud** ka hissa hai, isliye `pom.xml` me OpenFeign dependency aur Spring Cloud ka BOM (version management) chahiye.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

Spring Cloud ka version Spring Boot ke version ke saath **compatible** hona chahiye, isliye version ko BOM se manage karte hain:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**Tip:** Spring Initializr me dependency add karte waqt **"OpenFeign"** search karke select kar lo. Ye BOM apne aap daal deta hai.

### Step 2: `@EnableFeignClients`

Main class pe ye annotation lagao:

```java
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
```

**`@EnableFeignClients` kya karta hai?** Spring ko batata hai: _"Mere project me jo interfaces `@FeignClient` se marked hain, unhe dhundo aur unke liye implementation bana do."_

**Ye kis service me lagana hai?** **Client service** me (Order Service), kyunki wahi call kar rahi hai. Server (Inventory Service) me nahi.

---

## 3. Create Feign Client

Order Service me ek **interface** banao.

```java
@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryClient {

    @GetMapping("/inventory/{id}")
    ProductDTO getProduct(@PathVariable("id") Long id);

    @PostMapping("/inventory")
    ProductDTO addProduct(@RequestBody ProductDTO product);

    @PutMapping("/inventory/{id}")
    void updateStock(@PathVariable("id") Long id, @RequestBody ProductDTO product);

    @DeleteMapping("/inventory/{id}")
    void deleteProduct(@PathVariable("id") Long id);
}
```

### `@FeignClient` ke attributes

| Attribute | Matlab                                                                                                                 |
| --------- | ---------------------------------------------------------------------------------------------------------------------- |
| `name`    | Is client ka naam (service ka logical naam). **Zaruri hai.** Eureka ke saath ye service discovery ke liye use hota hai |
| `url`     | Server ka address. Abhi hum Eureka use nahi kar rahe, isliye seedha URL dete hain                                      |

### Interface ke andar kya likha hai

Interface me **wahi annotations** hain jo Inventory Service ke **Controller** me hain:

| Inventory Service Controller     | Order Service Feign Client        |
| -------------------------------- | --------------------------------- |
| `@GetMapping("/inventory/{id}")` | `@GetMapping("/inventory/{id}")`  |
| `@PathVariable Long id`          | `@PathVariable("id") Long id`     |
| `@PostMapping("/inventory")`     | `@PostMapping("/inventory")`      |
| `@RequestBody Product product`   | `@RequestBody ProductDTO product` |

Matlab Feign client interface ek tarah se **server ke controller ka mirror** hai. Jo endpoint server pe hai, wahi yahan declare karte hain.

### Dhyan rakhne wali baatein

1. **Interface hai, class nahi.** Koi implementation nahi likhni.
2. **`@PathVariable("id")`** me naam explicitly likhna safe hai. Feign me ye likhna chahiye, warna kuch setups me error aa sakta hai.
3. **DTO wahi structure rakho** jo server bhej raha hai (field names same). Order Service ka apna `ProductDTO` hoga.
4. **Method ka naam kuch bhi ho sakta hai**, matter karti hain annotations aur URL.

---

## 4. Use Feign Client For Inter-Service Communication

Feign client ko **normal bean ki tarah inject** karo. Wo Spring ka bean ban jaata hai.

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

    @PostMapping("/add-product")
    public ProductDTO addProduct(@RequestBody ProductDTO product) {
        return inventoryClient.addProduct(product);
    }

    @PutMapping("/update-stock/{id}")
    public void updateStock(@PathVariable Long id, @RequestBody ProductDTO product) {
        inventoryClient.updateStock(id, product);
    }

    @DeleteMapping("/remove-product/{id}")
    public void removeProduct(@PathVariable Long id) {
        inventoryClient.deleteProduct(id);
    }
}
```

### Order Service ka flow

```
Client (Postman)
   │  GET localhost:8081/orders/check/1
   ▼
OrderController.checkStock()
   │  inventoryClient.getProduct(1)      ← bas ek method call
   ▼
Feign (peeche se HTTP call banata hai)
   │  GET http://localhost:8082/inventory/1
   ▼
Inventory Service → JSON response
   │
   ▼
Feign JSON ko ProductDTO me badalta hai
   │
   ▼
OrderController ko ProductDTO milta hai
```

### Error aane par

Agar server 4xx ya 5xx bheje, toh Feign **`FeignException`** throw karta hai (jaise `FeignException.NotFound` for 404). Isse ya toh `try-catch` me pakdo, ya apne `@RestControllerAdvice` (global exception handler) me handle karo.

### Services ko test karne ka order

1. Pehle Inventory Service chalao aur Postman se seedha test karo.
2. Phir Order Service chalao aur Feign ke through test karo.
3. Dono services chalni chahiye, warna connection error aayega.

---

## 5. FeignClient Flow & Internal Working

### Sabse bada sawaal: interface ka implementation kaun likhta hai?

Hum ne sirf interface likha, koi class nahi. Phir bhi `inventoryClient.getProduct(1)` chal jaata hai. **Ye magic Spring ka proxy mechanism karta hai.**

Ye wahi **proxy** ka concept hai jo AOP aur Spring Data JPA ke repository me padha tha. Jaise `JpaRepository` ka implementation Spring runtime pe banata hai, waise hi Feign ke interface ka implementation bhi runtime pe banta hai.

### Startup pe kya hota hai

```
1. Application start hoti hai
        ↓
2. @EnableFeignClients Spring ko batata hai: "@FeignClient wale interfaces dhundo"
        ↓
3. Spring interfaces scan karta hai (jaise InventoryClient)
        ↓
4. Har interface ke liye ek PROXY object banata hai (runtime pe)
        ↓
5. Ye proxy Spring ka bean ban jaata hai (container me register)
        ↓
6. Jahan @Autowired / constructor injection hota hai, ye proxy inject hota hai
```

### Method call hone par kya hota hai

```
orderController
   │  inventoryClient.getProduct(1)
   ▼
Proxy (Feign ka banaya hua object)
   │
   ├─ 1. Method ke annotations padhta hai
   │     (@GetMapping("/inventory/{id}"), @PathVariable)
   │
   ├─ 2. HTTP request template banata hai
   │     (method: GET, URL: http://localhost:8082/inventory/1)
   │
   ├─ 3. Request body ko JSON me badalta hai (Encoder)
   │     (POST/PUT me, Jackson se)
   │
   ├─ 4. HTTP client se request bhejta hai
   │
   ├─ 5. Response aata hai
   │
   ├─ 6. Response JSON ko Java object me badalta hai (Decoder)
   │
   └─ 7. ProductDTO return karta hai
```

### Internal components (naam yaad rakho)

| Component        | Kaam                                                                                                                        |
| ---------------- | --------------------------------------------------------------------------------------------------------------------------- |
| **Proxy**        | Interface ka runtime implementation, jisme har method call intercept hoti hai                                               |
| **Contract**     | Interface ke annotations (`@GetMapping` etc.) padhna aur samajhna. Spring ke case me ye Spring MVC annotations samajhta hai |
| **Encoder**      | Java object ko request body (JSON) me badalna                                                                               |
| **Decoder**      | Response (JSON) ko Java object me badalna                                                                                   |
| **HTTP Client**  | Asli HTTP call bhejna                                                                                                       |
| **ErrorDecoder** | Error response (4xx/5xx) ko exception me badalna                                                                            |

### RestClient aur Feign ka fark, internal level pe

|                       | RestClient                              | Feign                                        |
| --------------------- | --------------------------------------- | -------------------------------------------- |
| Call kaun banata hai  | Hum khud code me                        | Proxy interface ke annotations se banata hai |
| Kitna code            | Har call ke liye chain likhni padti hai | Sirf method declare                          |
| Kahan define hota hai | Service/client class me                 | Interface me                                 |
| Magic                 | Kam (sab explicit)                      | Zyada (proxy peeche se kaam karta hai)       |

### Feign ki limitations / dhyan rakhne wali baatein

| Baat                         | Explanation                                                                                                                                                                                                                      |
| ---------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Abhi bhi synchronous hai** | Feign bhi blocking hai. Jab tak response nahi aata, caller wait karta hai                                                                                                                                                        |
| **Hardcoded URL ki problem** | Abhi `url = "http://localhost:8082"` likha hai. Eureka ke saath sirf `name` rakhna hoga aur URL hat jaayega                                                                                                                      |
| **Network call hi hai**      | Method call jaisi dikhti hai, par peeche se network call hai. Fail ho sakti hai, slow ho sakti hai                                                                                                                               |
| **Resilience alag se**       | Retry, timeout, circuit breaker alag se configure karne padte hain                                                                                                                                                               |
| **Version ka dhyan**         | Spring ab apna **HTTP Interface** (`@HttpExchange`) bhi deta hai, jo isi declarative idea ka Spring ka apna version hai. Feign abhi bhi bahut use hota hai, par naye Spring versions ke documentation me ye option bhi dekh lena |

### Aage kya aayega

Hardcoded URL ki problem **Service Discovery (Eureka)** se solve hogi. Tab Feign client me `url` nahi dena padega, sirf service ka `name`, aur Eureka khud batayega ki wo service kahan chal rahi hai.

---

## Quick Revision (ek line me)

| Topic                 | Ek line me                                                                                |
| --------------------- | ----------------------------------------------------------------------------------------- |
| Feign                 | Declarative HTTP client: sirf interface likho, call ka code Spring banata hai             |
| Declarative           | Batao **kya** chahiye, **kaise** ye framework dekhta hai                                  |
| Dependency            | `spring-cloud-starter-openfeign` (+ Spring Cloud BOM)                                     |
| `@EnableFeignClients` | Main class pe, Spring ko `@FeignClient` interfaces dhundne ko kehta hai                   |
| `@FeignClient`        | Interface pe lagta hai, `name` (zaruri) aur `url` (abhi hardcoded) deta hai               |
| Interface ke andar    | Server ke controller wale hi annotations (`@GetMapping`, `@PathVariable`, `@RequestBody`) |
| Use kaise             | Normal bean ki tarah inject karo aur method call karo                                     |
| Internal working      | Runtime pe **proxy** banta hai, jo annotations padh ke HTTP request banata aur bhejta hai |
| Error                 | `FeignException`                                                                          |
| Bachi problem         | Hardcoded URL, jo Eureka se solve hogi                                                    |
