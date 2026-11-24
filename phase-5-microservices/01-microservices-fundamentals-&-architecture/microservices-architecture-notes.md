# Microservices Architecture: Overview Notes

Is note me sirf **architecture ka bird's-eye view** hai. Har component ki depth me padhai aage ke dedicated lectures me hogi.

**Components:**
1. API Gateway
2. Services
3. Management / Orchestration
4. Inter-service Communication (Synchronous, Message-oriented middleware)
5. Data Management
6. Observability

---

## Architecture ka big picture

### Diagram 1: Request kaise chalti hai

```
Client (Web / Mobile)
        |
        v
   API Gateway
        |
        +-----> User Service -------> User DB
        |
        +-----> Order Service ------> Order DB
        |
        +-----> Payment Service ----> Payment DB
```

**Padhne ka tarika:** Client sirf API Gateway se baat karta hai. Gateway request ko sahi service ko bhejta hai. Har service apne **alag database** se baat karti hai.

### Diagram 2: Services aapas me kaise baat karti hain

```
Synchronous (wait karta hai):

  Order Service ---- REST call ----> User Service
                <--- response ------


Asynchronous (wait nahi karta):

  Order Service ---> Message Broker ---> Payment Service
                     (Kafka / RabbitMQ)
```

**Padhne ka tarika:** Synchronous me Order Service seedha call karke jawab ka wait karti hai. Async me wo broker ko message de deti hai aur aage badh jaati hai, Payment Service baad me message padhti hai.

### Diagram 3: Services ke peeche kaun sa support system hai

| Layer | Kaam |
|---|---|
| Management / Orchestration | Services ko deploy, scale aur restart karna |
| Observability | Logs, metrics aur tracing se dekhna ki system me kya ho raha hai |

Ye dono layers saari services ko cover karti hain, kisi ek service ka hissa nahi hain.

---

## 1. API Gateway

**Kya hai:** Client aur services ke beech ka **single entry point**. Client har service ko alag-alag call nahi karta, sab requests pehle gateway pe aati hain, aur gateway unhe sahi service tak bhejta hai.

**Kyu chahiye:**
- Client ko 10 services ke address yaad rakhne nahi padte
- Common kaam ek jagah ho jaata hai (jaise authentication, routing)

**Example:** Client `/orders` bhejta hai, gateway use Order Service ko forward kar deta hai.

---

## 2. Services

**Kya hai:** Application ke **chhote, independent hisse**. Har service ek business function karti hai.

**Key points:**
- Har service alag Spring Boot project hai
- Har service ka apna database hai
- Har service independently develop, deploy aur scale hoti hai

**Example:** User Service, Product Service, Order Service, Payment Service.

---

## 3. Management / Orchestration

**Kya hai:** Bahut saari services ko **chalana aur sambhalna**. Services ki sankhya badhne par manually manage karna mushkil ho jaata hai, isliye ye kaam tools karte hain.

**Ye kaam karta hai:**
- Services ko deploy karna
- Zaroorat ke hisaab se copies badhana ya ghatana (scaling)
- Service crash ho toh automatically restart karna
- Services ka address track karna (service discovery)
- Configuration ek jagah se manage karna

**Tools (naam yaad rakho, depth baad me):** Docker, Kubernetes, Eureka, Config Server.

---

## 4. Inter-service Communication

**Kya hai:** Services alag-alag processes me hain, toh unhe ek dusre se **network ke through** baat karni padti hai. Iske do tarike hain.

### 4.1 Synchronous

- Ek service doosri ko request bhejti hai aur **response aane tak wait** karti hai
- Direct call hota hai, jaise Order Service seedha User Service ko call kare
- Protocol: mostly HTTP / REST
- Tools: `RestTemplate`, `Feign Client`

### 4.2 Message-oriented Middleware (Async)

- Service ek **message bhejti hai aur wait nahi karti**
- Beech me ek **message broker** hota hai jo message rakhta hai, aur doosri service apne time pe use padhti hai
- Tools: Kafka, RabbitMQ

### Ek nazar me fark

| Point | Synchronous | Asynchronous |
|---|---|---|
| Wait | Response ka wait karta hai | Wait nahi karta |
| Beech me | Koi nahi (direct call) | Message broker |
| Example tool | Feign, RestTemplate | Kafka, RabbitMQ |

*(Detail aage ke dedicated lecture me.)*

---

## 5. Data Management

**Kya hai:** Microservices me data kaise store aur manage hota hai.

**Core idea: Database per service.**
- Har service ka apna database hota hai
- Koi doosri service uska database seedha access nahi karti, sirf us service ki API se data maangti hai

**Kyu:**
- Services independent rehti hain
- Ek service ka database change hone se baaki toot-te nahi

**Challenge:** Alag databases hone se **data consistency** mushkil ho jaati hai (ek flow jo kai services me hai, usme single `@Transactional` kaam nahi karta).

---

## 6. Observability

**Kya hai:** Kai services chal rahi hon toh ye **dekh pana ki system ke andar kya ho raha hai**. Kuch fail ho toh pata chale ki kahan aur kyu.

**Teen cheezein:**

| Cheez | Kaam |
|---|---|
| **Logging** | Har service ke events aur errors ka record |
| **Monitoring / Metrics** | System ki health (CPU, memory, response time, error count) |
| **Distributed Tracing** | Ek request kai services se guzarti hai, uska poora path track karna |

**Kyu zaruri:** Monolith me log ek jagah hota hai. Microservices me log alag-alag services me bikhre hote hain, toh unhe ek jagah jodna padta hai.

**Tools (naam yaad rakho):** ELK Stack, Prometheus, Grafana, Zipkin.

---

## Quick Revision (ek line me)

| Component | Ek line me |
|---|---|
| API Gateway | Client ka single entry point, requests ko sahi service tak bhejta hai |
| Services | Chhote, independent hisse, har ek ka apna kaam aur database |
| Management / Orchestration | Services ko deploy, scale aur restart karna |
| Synchronous communication | Direct call, response ka wait (REST) |
| Async communication | Message broker ke through, wait nahi (Kafka, RabbitMQ) |
| Data Management | Database per service |
| Observability | Logs, metrics aur tracing se system ko dekhna |