# Microservices - Foundations (Deep Notes)

## 1. Monolith vs Microservices

### Monolith kya hota hai

Monolith matlab poora application ek hi codebase, ek hi deployable unit (ek WAR/JAR file) ke andar hota hai. User Service, Order Service, Payment Service, sab classes/packages ke roop mein ek hi Spring Boot application ke andar rehte hain, aur sab ek hi database use karte hain.

Real-world analogy: Ek monolith ek joint family jaisa hai jahan sab log ek hi ghar mein rehte hain. Kitchen (database) common hai, ghar ka main gate (deployment) ek hi hai. Agar ek room renovate karna hai, poora ghar disturb hota hai.

### Microservices kya hota hai

Microservices mein har business capability (User, Order, Payment) apna alag chhota application hota hai — apna alag codebase, apna alag database, apna alag deployment. Ye services HTTP/messaging ke through ek dusre se baat karti hain.

Real-world analogy: Ye alag-alag flats jaisa hai jisme har family (service) apna alag ghar, apna alag kitchen (database) rakhti hai. Ek flat mein renovation ho rahi ho, dusre flats ko koi farak nahi padta.

### Trade-offs — dono side dekho

**Monolith ke fayde:**

- Development shuru mein simple hai — ek hi codebase, ek hi IDE project, debugging easy
- Testing easy hai kyunki sab kuchh ek jagah hai, network calls nahi hain beech mein
- Transaction management simple hai — ek hi database hone se ACID transactions directly kaam karte hain
- Deployment simple hai — ek hi artifact deploy karna hai

**Monolith ke nuksan:**

- Codebase badha toh naya developer samajhne mein time leta hai
- Ek chhota sa change bhi poore application ko rebuild + redeploy karwata hai
- Scaling mushkil hai — agar sirf Order Service pe load zyada hai, tumhe poora application scale karna padega (sab kuchh, chahe zaroorat ho ya na ho)
- Ek module crash ho jaye (jaise memory leak), toh poora application down ho sakta hai
- Naya technology/language adopt karna mushkil hai kyunki poora app ek hi stack mein bandha hai

**Microservices ke fayde:**

- Independent deployment — sirf Order Service change hui, sirf usko deploy karo, baaki services untouched
- Independent scaling — jis service pe load zyada hai, sirf usko scale karo (jaise Black Friday pe Order Service ke 10 instances, baaki services ke 2-2)
- Fault isolation — Payment Service crash ho jaye, User Service aur Product Service chalte rahenge (agar properly design kiya ho)
- Alag-alag teams alag-alag services pe independently kaam kar sakti hain, alag tech stack bhi use kar sakti hain
- Naya developer ek chhoti service samajh sakta hai, poora system samajhna zaroori nahi

**Microservices ke nuksan:**

- Distributed system ki complexity — network calls fail ho sakte hain, latency aati hai
- Data consistency mushkil ho jaati hai kyunki har service ka apna database hai (isko Phase 5 ke "Data Consistency" section mein detail se cover karenge — Saga pattern)
- Testing mushkil — ek feature test karne ke liye multiple services chalani padti hain
- Operational overhead badh jaata hai — deployment, monitoring, logging sab distributed ho jaata hai (isliye Service Discovery, API Gateway, Distributed Tracing jaise tools chahiye hote hain)
- Debugging mushkil — ek request 5 services se guzarti hai, error kahan aaya track karna hard hai

### Kab microservices use karni chahiye, kab monolith

Ye interview mein bahut common question hai — seedha "microservices hamesha better hain" mat bolna, ye galat hai.

**Monolith se start karo jab:**

- Startup/small team hai, product-market fit dhoondh rahe ho (requirements badalti rahengi)
- Team size chhoti hai (5-10 developers)
- Domain abhi clear nahi hai — pehle samajh lo business kaise kaam karta hai

**Microservices ki taraf jao jab:**

- Application bahut bada ho gaya hai, alag modules ka load pattern alag hai
- Multiple teams independently kaam karna chahti hain bina ek dusre ko block kiye
- Kuchh modules ko alag se scale karna zaroori ho gaya hai
- Domain boundaries clear ho chuki hain (isiliye niche wala "Service Decomposition" step monolith ke baad hi aata hai — pehle domain samjho, phir todo)

Industry mein common pattern: "Monolith first" approach — Martin Fowler jaise experts bhi yahi kehte hain ki shuru mein monolith banao, jab pain points clear ho jayen (scaling issue, team conflicts) tab specific parts ko microservices mein nikalo.

---

## 2. Service Decomposition Strategies

Service decomposition matlab: monolith ko chhote services mein todne ka tareeka — kaunsa code kis service mein jayega, ye kaise decide karein.

### Galat approach (jo log shuruat mein karte hain)

Technical layers ke hisaab se todna — jaise "Controller Service", "Service Layer Service", "Repository Service". Ye galat hai kyunki ek simple feature (jaise "order place karo") ke liye teeno services ko baat karni padegi — matlab tight coupling ho gayi, bas naam microservices ho gaya, fayda kuchh nahi mila.

### Sahi approach — Domain-Driven Design (DDD)

DDD ek software design approach hai jisme tum business domain (real business problem) ke hisaab se code organize karte ho, technical layers ke hisaab se nahi.

**Bounded Context kya hai:**

Bounded Context DDD ka sabse important concept hai. Ye ek boundary hai jiske andar ek specific business term ka ek hi matlab hota hai.

Real-world analogy: "Customer" word lo. Sales team ke liye "Customer" matlab jo lead hai, jisko convert karna hai — uska interest, budget important hai. Support team ke liye "Customer" matlab jo already product use kar raha hai — uska ticket history, subscription plan important hai. Same word "Customer" hai, lekin dono contexts mein uska matlab aur attributes alag hain. Isiliye "Sales" aur "Support" alag bounded contexts hain — dono ka apna "Customer" model hoga.

Microservices mein har bounded context ek microservice ban jaata hai (ya ek microservice group ban sakta hai agar context bada hai).

**Bounded context kaise identify karein — practical steps:**

1. Business domain ko samjho — pehle business logic samjho, code nahi. Jaise ek e-commerce app mein: User Management, Product Catalog, Order Processing, Payment, Shipping/Inventory — ye alag-alag business capabilities hain
2. Har capability ke andar dekho konse entities/terms use ho rahe hain, aur kya wo term poore system mein same matlab rakhta hai ya context ke hisaab se badalta hai
3. High cohesion, low coupling ka principle follow karo — jo cheezein saath badalti hain (jaise Order create karna, Order status update karna) unko ek service mein rakho. Jo cheezein independently badalti hain (jaise Payment logic vs Shipping logic) unko alag services mein todo
4. Data ownership check karo — agar ek entity (jaise "Product") ko do teams alag-alag reasons se access kar rahi hain (Catalog team product info ke liye, Inventory team stock count ke liye), toh dono responsibilities ko alag services mein todne ka soch sakte ho

**Example — E-commerce app decomposition:**

- User Service: registration, login, profile
- Product Service: catalog, product details, search
- Order Service: cart, order placement, order status
- Payment Service: payment processing, refunds
- Inventory Service: stock management
- Notification Service: emails, SMS, push notifications

Har service apne bounded context ke andar hi decisions leti hai, aur dusri services se sirf zaroorat padne pe communicate karti hai (jaise Order Service, Payment Service ko call karegi jab order place ho).

---

## 3. Database Per Service Pattern

### Pattern kya kehta hai

Har microservice ka apna alag, private database hota hai. Koi doosri service us database ko directly access nahi kar sakti — sirf us service ke API (REST/messaging) ke through hi data access hota hai.

Real-world analogy: Ye alag bank accounts jaisa hai. Tumhara aur tumhare dost ka alag-alag bank account hai. Agar tumhe dost ke paise chahiye, tum uske account mein directly nahi ghus sakte — usse request karni padegi ("mujhe X amount transfer karo"), aur wo apne rules ke hisaab se decide karega.

### Kyun zaroori hai — shared database kyun problem create karta hai

Agar saari microservices ek hi shared database use karti hain, toh:

1. **Tight coupling ban jaati hai**: Agar Order Service database ka schema (table structure) change karti hai, toh jo bhi services us table ko access kar rahi thi, wo sab break ho sakti hain. Matlab "independent deployment" wala poora fayda khatam ho gaya
2. **Ownership unclear ho jaati hai**: Agar 3 services ek hi "products" table ko read-write kar rahi hain, toh data consistency kaun ensure karega? Kisi ek service ka bug dusri service ka data corrupt kar sakta hai
3. **Scaling independent nahi rehti**: Agar ek service ka load badhta hai aur wo database pe zyada queries chalati hai, toh dusri services bhi slow ho jaayengi kyunki database shared hai
4. **Technology choice lock ho jaati hai**: Agar Product Service ke liye MongoDB better fit hai (flexible schema, catalog data) aur Order Service ke liye MySQL better hai (strong consistency, transactions), shared database mein ye choice possible nahi hai

### Database per service se kya milta hai

- Har service apna database independently design kar sakti hai (SQL ya NoSQL, jo bhi uske use case ke liye best ho — isko "polyglot persistence" kehte hain)
- Schema changes sirf us service ke andar impact karte hain
- Data access sirf us service ke API se hota hai, isliye business rules bhi wahi enforce hote hain (jaise "order tabhi place hoga jab stock available ho" — ye rule Order/Inventory Service ke andar hi handle hoga, koi dusri service seedha database mein ghus ke row insert nahi kar sakti)

### Isse jo naya problem aata hai (preview)

Database per service ka sabse bada trade-off ye hai ki ab tumhare paas distributed data hai — agar ek business operation (jaise "order place karna") mein Order database aur Inventory database dono ko update karna hai, toh traditional single-database transaction (ACID) kaam nahi karega, kyunki dono alag databases hain.

Ye problem "Data Consistency Across Services" topic mein detail se cover hogi — Saga pattern is exact problem ko solve karta hai. Abhi ke liye itna samajh lo: database per service independence deta hai, lekin cross-service transactions ke liye naya approach chahiye hota hai.

---

## 4. Synchronous vs Asynchronous Communication

Jab do microservices ko baat karni hoti hai, do broad tareeke hain:

### Synchronous Communication

Service A, Service B ko call karti hai aur response ka wait karti hai — jab tak B jawab nahi deta, A block rehti hai (ruki rehti hai).

Real-world analogy: Ye phone call jaisa hai. Tum kisi ko call karte ho aur line pe rukte ho jab tak wo jawab nahi deta. Jab tak call chal rahi hai, tum kuchh aur nahi kar sakte.

**Kaise implement hota hai:** REST APIs (HTTP calls), Feign Client, WebClient, gRPC — ye sab synchronous communication ke tools hain.

**Kab use karo:**

- Jab immediate response chahiye (jaise "is user ka balance kitna hai" — turant jawab chahiye)
- Jab operation simple request-response hai, koi lambi processing nahi

**Problems:**

- Agar Service B slow hai ya down hai, Service A bhi block ho jayegi ya fail ho jayegi — ye "cascading failure" create karta hai (ek service ka problem chain reaction ki tarah dusri services tak failure phaila deta hai)
- Tight temporal coupling — Service B ka available hona zaroori hai jab Service A use kar rahi ho
- Isi problem ko handle karne ke liye Circuit Breaker, Timeout, Retry patterns use hote hain (jo Resilience section mein cover honge)

### Asynchronous Communication

Service A, Service B ko ek message/event bhej deti hai aur turant aage badh jaati hai — response ka wait nahi karti. Service B apni marzi se, apne time pe us message ko process karegi.

Real-world analogy: Ye WhatsApp message jaisa hai. Tum message bhej dete ho aur apna kaam karte raho. Doosra insaan jab free hoga, message dekhega aur reply karega. Tumhe wait nahi karna padta.

**Kaise implement hota hai:** Message brokers/queues — Apache Kafka, RabbitMQ. Service A ek event publish karti hai (jaise "OrderPlaced"), aur jo bhi services us event mein interested hain (Inventory Service, Notification Service) wo us event ko consume kar leti hain.

**Kab use karo:**

- Jab immediate response ki zaroorat nahi hai (jaise order place hone ke baad email bhejna — ye turant hona zaroori nahi)
- Jab ek event mein multiple services interested ho sakti hain (event-driven architecture)
- Jab tum services ke beech loose coupling chahte ho — Service A ko pata bhi nahi hona chahiye ki kaun-kaun services us event ko sun rahi hain

**Fayde:**

- Service B down bhi ho toh Service A block nahi hoti — message queue mein wait karega jab tak B wapas up na ho
- Better scalability — multiple consumers ek saath messages process kar sakte hain
- Loose coupling — naya consumer add karna easy hai bina existing services ko touch kiye

**Problems:**

- Debugging harder hai — ek event kaha process hua, kab hua, track karna mushkil
- Eventual consistency — data turant sync nahi hota, thodi der lagti hai (isliye "eventual" consistency kehte hain, "immediate" nahi)
- Message ordering aur duplicate messages jaise concerns handle karne padte hain (idempotency)

### Konsa use karein — decision practically kaise lein

Simple rule: agar caller ko turant result chahiye (jaise checkout page pe "payment successful" dikhana), sync use karo. Agar operation background mein ho sakta hai (jaise invoice email bhejna, analytics update karna, recommendation engine ko notify karna), async use karo.

Real systems mein dono mix hote hain — jaise Order Service, Payment Service ko sync call karegi (kyunki payment ka result turant chahiye order confirm karne ke liye), lekin Order Service, Notification Service ko async event bhejegi (email/SMS turant jaana zaroori nahi hai).

---

## Quick Revision Summary

- Monolith = ek codebase, ek DB, simple shuru mein, scaling/deployment mushkil badhne pe
- Microservices = alag services, independent scaling/deployment, lekin distributed complexity
- Decomposition = business domain (DDD, bounded context) ke hisaab se todo, technical layers ke hisaab se nahi
- Database per service = har service ka apna DB, tight coupling todta hai, lekin cross-service transactions ka naya problem create karta hai (Saga se solve hoga)
- Sync = turant response chahiye, tight coupling, cascading failure risk
- Async = background processing, loose coupling, eventual consistency
