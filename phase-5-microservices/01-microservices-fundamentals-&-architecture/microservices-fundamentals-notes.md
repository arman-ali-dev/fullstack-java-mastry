# Microservices Basics: Detailed Notes

**Topics covered:**

1. Monolith
2. Why Monolith Fails
3. Microservices
4. When to Use and When to Avoid

---

## 1. Monolith

### Definition

> _"A monolith is an application where all features, like user, order and payment, are built as one single unit. It has one codebase, one database, and is deployed as one package."_

### Simple explanation

Socho ek e-commerce app hai. Usme ye sab features hain:

- Login / Register (User)
- Product listing
- Cart
- Order
- Payment

Monolith me **ye sab ek hi Spring Boot project ke andar** hote hain. Build karne par ek hi `.jar` file banti hai, aur wahi ek file server pe deploy hoti hai. Saare features ek hi database use karte hain.

### Structure ka diagram

```
┌─────────────────────────────────────────┐
│           ONE SPRING BOOT APP           │
│                                         │
│   [User]  [Product]  [Cart]  [Order]    │
│                  [Payment]              │
│                                         │
└─────────────────────┬───────────────────┘
                      │
              ┌───────▼───────┐
              │  ONE DATABASE │
              └───────────────┘
```

### Monolith ke faayde

| Faayda             | Explanation                                                                        |
| ------------------ | ---------------------------------------------------------------------------------- |
| Shuru karna aasan  | Ek project banao aur kaam shuru. Alag setup nahi chahiye                           |
| Debug karna aasan  | Sab code ek jagah hai, toh error trace karna simple hai                            |
| Testing aasan      | Ek hi app run karke poora flow test ho jaata hai                                   |
| Deployment simple  | Ek `.jar` ek baar deploy karo                                                      |
| Fast communication | Modules ek dusre ko normal **method call** se bulate hain, network ki zarurat nahi |
| Transactions aasan | Ek hi database hai, toh `@Transactional` seedha kaam karta hai                     |

### Kab monolith achha hai?

Chhoti app, naya startup idea, chhoti team. Wahan simple cheez hi best hoti hai.

---

## 2. Why Monolith Fails

Monolith shuru me achha lagta hai, par **app bada hone par** problems aati hain.

### Problem 1: Chhota change, poora deploy

**Example:** Sirf Payment module me ek chhota bug fix kiya. Par poori app (User, Product, Cart, Order, sab) ko dobara build, test aur deploy karna padega.

Isse releases slow ho jaate hain, aur risk badh jaata hai kyunki ek chhote change se kuch aur toot sakta hai.

### Problem 2: Ek hissa fail, sab fail

**Example:** Product module me memory leak aa gaya. Kyunki sab ek hi process me chal raha hai, **poori app crash** ho jaati hai. Login aur payment bhi band, jabki unme koi problem nahi thi.

### Problem 3: Scaling mehnga padta hai

**Example:** Sale ke time sirf Product Search pe load badhta hai. Par monolith me tum sirf search ko scale nahi kar sakte. **Poori app ki copies** chalani padti hain, jisse server aur paisa dono zyada lagte hain.

```
Monolith scale karna:    [Poori App] [Poori App] [Poori App]   <- sab features ki copy
Chahiye tha:             [Search] [Search] [Search]            <- sirf search ki copy
```

### Problem 4: Ek hi technology me bandhe

Poori app ek hi language aur framework me hoti hai. Agar ek module ke liye alag technology behtar hai (jaise recommendation ke liye Python), toh use nahi kar sakte.

### Problem 5: Bada codebase

- Naye developer ko poora code samajhna mushkil
- Alag teams ek hi codebase me kaam karti hain, **merge conflicts** aur dependency issues aate hain
- Code ek dusre se tightly jud jaata hai (tight coupling)

### Problem 6: Slow releases

Poori app test karke hi release hota hai, isliye naye features users tak der se pahunchte hain.

---

## 3. Microservices

### Definition

> _"Microservices is an architecture where an application is divided into small, independent services. Each service handles one business function, has its own database, and can be developed, deployed and scaled independently. Services talk to each other using REST APIs or messaging."_

### Simple explanation

Wahi e-commerce app ko ab **chhote chhote alag projects** me tod do:

- User Service
- Product Service
- Order Service
- Payment Service

Har service:

- Alag Spring Boot project hai
- Alag database use karti hai
- Alag deploy hoti hai
- Ek hi kaam karti hai (single responsibility)

### Structure ka diagram

```
                    ┌──────────┐
   Client  ───────► │ API      │
                    │ Gateway  │
                    └────┬─────┘
          ┌──────────┬───┴──────┬───────────┐
          ▼          ▼          ▼           ▼
     ┌────────┐ ┌─────────┐ ┌───────┐ ┌─────────┐
     │  User  │ │ Product │ │ Order │ │ Payment │
     │Service │ │ Service │ │Service│ │ Service │
     └───┬────┘ └────┬────┘ └───┬───┘ └────┬────┘
         ▼           ▼          ▼          ▼
      [User DB]  [Product DB] [Order DB] [Payment DB]
```

(API Gateway aur baaki tools aage ke lectures me aayenge. Abhi bas ye samjho ki har service ka apna database hai.)

### Services ek dusre se kaise baat karti hain?

1. **Synchronous (REST):** Order Service seedha User Service ko HTTP call karti hai. Tools: `RestTemplate`, `Feign Client`.
2. **Asynchronous (Messaging):** Ek service message bhejti hai, doosri baad me padhti hai. Tools: Kafka, RabbitMQ.

### Microservices ke faayde

| Faayda                 | Explanation                                              |
| ---------------------- | -------------------------------------------------------- |
| Independent deployment | Sirf Payment badla toh sirf Payment deploy hoga          |
| Fault isolation        | Ek service fail hui toh baaki chalti rehti hain          |
| Independent scaling    | Sirf zaruri service ki copies badhao (jaise sirf Search) |
| Technology freedom     | Har service alag language ya database use kar sakti hai  |
| Team independence      | Alag teams alag services pe parallel kaam karti hain     |
| Chhota codebase        | Har service chhoti hai, toh samajhna aasan               |

### Microservices ke nuksaan (interview me ye zaroor bolo)

| Nuksaan              | Explanation                                                             |
| -------------------- | ----------------------------------------------------------------------- |
| Complexity           | Ek app ki jagah 10 apps manage karni padti hain                         |
| Network issues       | Services network se baat karti hain, jo slow ya fail ho sakta hai       |
| Debugging mushkil    | Ek request kai services se guzarti hai, error dhundhna mushkil          |
| Data consistency     | Alag databases hain, toh `@Transactional` poore flow me kaam nahi karta |
| Testing mushkil      | Integration testing ke liye kai services chalani padti hain             |
| Extra infrastructure | Monitoring, logging, service discovery, gateway, sab chahiye            |

> **Ye line yaad rakho:** Microservices complexity ko khatam nahi karte, **complexity ko code se infrastructure me shift karte hain.**

---

## 4. When to Use and When to Avoid

### Microservices kab use karo

| Situation                                    | Kyu                                                 |
| -------------------------------------------- | --------------------------------------------------- |
| App bada aur complex hai                     | Monolith manage karna mushkil ho jaata hai          |
| Kai teams alag modules pe kaam kar rahi hain | Har team apni service independently chala sakti hai |
| Kuch features pe zyada load hai              | Sirf unhi ko scale kar sakte ho                     |
| Frequent aur independent deployments chahiye | Ek service badlo, sirf wahi deploy karo             |
| Alag modules ko alag technology chahiye      | Har service ki apni technology ho sakti hai         |

### Microservices kab avoid karo

| Situation                     | Kyu                                                                       |
| ----------------------------- | ------------------------------------------------------------------------- |
| App chhota ya naya hai        | Complexity ka fayda nahi, bas overhead badhega                            |
| Team chhoti hai (2-5 log)     | Itni services manage karne ke log hi nahi                                 |
| Requirements clear nahi hain  | Service boundaries galat ban sakti hain, baad me badalna mehnga padta hai |
| DevOps ka experience nahi hai | Docker, deployment, monitoring sambhalna padta hai                        |
| Budget aur time kam hai       | Microservices me setup time aur cost zyada hota hai                       |
