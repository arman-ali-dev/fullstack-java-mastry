# Microservices Communication: Notes

**Topics covered:**

1. Inter-service Communication
2. Synchronous Communication & Characteristics
   - Advantages & Disadvantages
3. Asynchronous Communication
   - Advantages & Disadvantages

_(Is note me code nahi hai. Implementation agle lecture me aayega.)_

---

## 1. Inter-service Communication

### Ye kyu chahiye?

Monolith me saare features ek hi application ke andar hote hain, toh ek feature doosre ko seedha **method call** se bula leta hai. Ye same process ke andar hota hai, isliye fast aur simple hai.

Microservices me har service **alag application** hai, alag process me chalti hai. Toh ek service doosri ko method call se nahi bula sakti. Use **network ke through** baat karni padti hai.

```
Monolith:
  OrderModule  ---- method call ---->  UserModule      (same application)

Microservices:
  Order Service  ---- network ---->  User Service       (alag applications)
```

### Example

Ek user order place karta hai. Order Service ko kuch cheezein chahiye:

- User ki details (User Service se)
- Product available hai ya nahi (Product Service se)
- Payment karwana hai (Payment Service se)

Order Service ye sab khud nahi karti. Wo baaki services se baat karti hai. **Is baat-cheet ko inter-service communication kehte hain.**

### Definition

> Inter-service communication means how independent services talk to each other over the network to complete a business task.

### Communication ke do main tareeke

| Type             | Ek line me                                  |
| ---------------- | ------------------------------------------- |
| **Synchronous**  | Request bhejo aur jawab aane tak wait karo  |
| **Asynchronous** | Message bhejo aur wait kiye bina aage badho |

---

## 2. Synchronous Communication

### Kya hai?

Ek service doosri service ko **request bhejti hai** aur **response aane tak wait karti hai**. Jab tak jawab nahi aata, wo aage ka kaam nahi karti.

```
Order Service                         User Service
     │                                      │
     │ ---- 1. Request (user details?) ---> │
     │                                      │
     │   (Order Service wait kar rahi hai)  │  2. User Service kaam karti hai
     │                                      │
     │ <--- 3. Response (user details) ---- │
     │                                      │
     ▼ 4. Ab aage ka kaam karti hai
```

### Real life example

**Phone call:** Tum kisi ko call karte ho, wo jawab deta hai, tab hi baat aage badhti hai. Jab tak wo phone nahi uthata, tum wait karte ho.

### Characteristics (khaasiyat)

| Characteristic              | Matlab                                                         |
| --------------------------- | -------------------------------------------------------------- |
| **Blocking**                | Caller service tab tak ruki rehti hai jab tak response na aaye |
| **Request-Response model**  | Ek request, ek response. Seedha sawaal-jawab                   |
| **Direct communication**    | Caller ko pata hota hai ki kis service ko call karna hai       |
| **Tight timing dependency** | Dono services us waqt **up aur available** honi chahiye        |
| **Immediate result**        | Jawab usi waqt mil jaata hai                                   |
| **Protocol**                | Aam taur pe HTTP / REST                                        |

### Advantages

| Advantage                | Explanation                                                                                 |
| ------------------------ | ------------------------------------------------------------------------------------------- |
| **Simple to understand** | Seedha flow hai: request bhejo, jawab lo. Samajhna aur implement karna aasan                |
| **Immediate response**   | Jawab usi waqt milta hai, jab result turant chahiye ho (jaise user ki details dikhani hain) |
| **Easy to debug**        | Flow ek line me chalta hai, toh error trace karna aasan hai                                 |
| **Easy error handling**  | Response me hi pata chal jaata hai ki success hua ya fail                                   |
| **Familiar**             | REST APIs ka concept pehle se sab jaante hain                                               |

### Disadvantages

| Disadvantage                    | Explanation                                                                                                      |
| ------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| **Tight coupling**              | Caller ko doosri service ka address aur API pata hona chahiye. Ek service badli toh doosri pe asar pad sakta hai |
| **Blocking = slow**             | Jab tak response nahi aata, caller wait karta hai, jisse total time badhta hai                                   |
| **Failure spreads**             | Agar User Service down hai, toh Order Service bhi atak jaati hai ya fail ho jaati hai                            |
| **Chain of calls = slow**       | A → B → C → D chain me har service ka time jud jaata hai                                                         |
| **Scaling pe asar**             | Ek service pe load badha toh us par depend karne wali saari services slow ho sakti hain                          |
| **Dono available honi chahiye** | Agar doosri service us waqt busy ya down hai, toh request fail                                                   |

### Chain of calls ka example

```
Client → Order → User → Address → Payment
                  (har ek ka wait)
```

Agar har service 1 second leti hai, toh total 4 second lag sakte hain. Ek bhi service slow hui toh poori chain slow.

---

## 3. Asynchronous Communication

### Kya hai?

Ek service doosri service ko **message bhejti hai** aur **jawab ka wait nahi karti**. Wo apna aage ka kaam karti rehti hai. Doosri service apne time pe message padhti hai aur kaam karti hai.

Beech me ek **message broker** hota hai, jo message ko tab tak sambhal ke rakhta hai jab tak receiver use padh na le.

```
Order Service            Message Broker             Notification Service
     │                         │                            │
     │ -- 1. Message bhejo --> │                            │
     │                         │                            │
     ▼ 2. Aage badh gayi       │  (message safe rakha hai)  │
        (wait nahi kiya)       │                            │
                               │ -- 3. Message deliver ---> │
                               │                            ▼
                               │                  4. Apne time pe kaam karti hai
```

### Real life example

**WhatsApp message / email:** Tum message bhej dete ho aur apna kaam karte rehte ho. Samne wala jab free hoga tab padhega aur jawab dega. Tum jawab ke liye ruke nahi rehte.

### Characteristics (khaasiyat)

| Characteristic             | Matlab                                                                    |
| -------------------------- | ------------------------------------------------------------------------- |
| **Non-blocking**           | Sender wait nahi karta, turant aage badh jaata hai                        |
| **Message-based**          | Request-response ki jagah message bheja jaata hai                         |
| **Indirect communication** | Sender aur receiver seedha baat nahi karte, beech me broker hota hai      |
| **Loose coupling**         | Sender ko pata nahi hota ki message kaun padhega                          |
| **Time independence**      | Receiver us waqt available na ho toh bhi message broker me safe rehta hai |
| **Eventually done**        | Kaam turant nahi, thodi der baad hota hai                                 |

### Advantages

| Advantage                     | Explanation                                                                                    |
| ----------------------------- | ---------------------------------------------------------------------------------------------- |
| **Loose coupling**            | Services ek dusre ko directly nahi jaanti, toh ek me change se doosri toot-ti nahi             |
| **Fast for sender**           | Sender wait nahi karta, toh uska response time kam rehta hai                                   |
| **Fault tolerance**           | Receiver down ho toh bhi message safe rehta hai, wapas aane pe process ho jaata hai            |
| **Better scalability**        | Load zyada ho toh receiver ki copies badha sakte ho, aur messages unme baant jaate hain        |
| **Handles traffic spikes**    | Achanak zyada requests aayein toh broker unhe line me rakhta hai (buffer ki tarah)             |
| **Ek message, kai receivers** | Ek event ko kai services sun sakti hain (jaise order hone par email, SMS aur inventory update) |

### Disadvantages

| Disadvantage                      | Explanation                                                                   |
| --------------------------------- | ----------------------------------------------------------------------------- |
| **Complexity**                    | Message broker ka setup aur management alag se karna padta hai                |
| **Immediate response nahi milta** | Jab result turant chahiye ho (jaise login check), tab ye sahi nahi            |
| **Debugging mushkil**             | Flow ek line me nahi hota, toh message kahan atka ye dhundhna mushkil         |
| **Eventual consistency**          | Data sab jagah ek saath update nahi hota, thodi der baad hota hai             |
| **Duplicate ya lost messages**    | Message do baar aa sakta hai ya kabhi kho sakta hai, isse sambhalna padta hai |
| **Order ki guarantee kam**        | Messages kabhi kabhi alag order me process ho sakte hain                      |
| **Extra infrastructure**          | Broker (jaise Kafka, RabbitMQ) chalane ka extra cost aur kaam                 |

---

## Synchronous vs Asynchronous: Comparison

| Point             | Synchronous                         | Asynchronous                          |
| ----------------- | ----------------------------------- | ------------------------------------- |
| Wait              | Response ka wait karta hai          | Wait nahi karta                       |
| Style             | Request-Response                    | Message-based                         |
| Beech me          | Koi nahi (direct call)              | Message broker                        |
| Coupling          | Tight                               | Loose                                 |
| Response          | Turant milta hai                    | Baad me (ya kabhi nahi)               |
| Failure           | Ek service down toh caller bhi fail | Receiver down ho toh bhi message safe |
| Complexity        | Kam                                 | Zyada                                 |
| Real life example | Phone call                          | WhatsApp / email                      |
| Kab use karo      | Jab result turant chahiye           | Jab turant result ki zarurat nahi     |

---

## Kab kaunsa use karein? (samajhne ke liye)

**Synchronous tab:**

- User ko jawab usi waqt dikhana hai
- Example: login check karna, product ki price dikhana, user ki details lana

**Asynchronous tab:**

- Kaam baad me ho sakta hai aur user ko wait nahi karwana
- Example: order ke baad confirmation email bhejna, notification bhejna, invoice banana, analytics update karna

**Ek real example (dono saath):**

```
User order place karta hai
   │
   ├── Payment check karna     → Synchronous (result turant chahiye)
   │
   └── Email aur SMS bhejna    → Asynchronous (baad me bhi chalega)
```

Real projects me aksar **dono ka mix** hota hai.

---

## Quick Revision (ek line me)

| Topic                       | Ek line me                                                 |
| --------------------------- | ---------------------------------------------------------- |
| Inter-service communication | Alag services ka network ke through aapas me baat karna    |
| Synchronous                 | Request bhejo, jawab aane tak wait karo (phone call jaisa) |
| Sync ka fayda               | Simple, turant response                                    |
| Sync ka nuksaan             | Tight coupling, blocking, ek fail toh chain fail           |
| Asynchronous                | Message bhejo, wait mat karo (WhatsApp jaisa)              |
| Async ka fayda              | Loose coupling, fast, fault tolerant, scalable             |
| Async ka nuksaan            | Complex, debug mushkil, turant response nahi               |
| Message broker              | Beech ka system jo messages ko sambhal ke rakhta hai       |
