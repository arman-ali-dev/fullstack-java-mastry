# Java Mastery Roadmap

## Phase 1 — Core Java

### Java Fundamentals

- Java program structure: main method, class, packages, imports
- Primitive data types: int, float, double, char, boolean, long, byte, short
- Type casting: implicit (widening) and explicit (narrowing)
- Operators: arithmetic, relational, logical, bitwise, ternary, assignment
- Control flow: if, else if, else, switch
- Loops: for, while, do-while, enhanced for-each
- Arrays: single-dimensional, multi-dimensional, array traversal
- Methods: defining, calling, return types, method overloading
- Scope and lifetime of variables: local, instance, static
- String basics: String, StringBuilder, StringBuffer, common methods
- Wrapper classes: Integer, Double, Character, Boolean, autoboxing & unboxing
- Generics basics: generic methods and generic classes (<T>)
- Scanner class for user input

### OOP Concepts

- Classes, Objects, Constructors
- Inheritance, Polymorphism, Encapsulation, Abstraction
- Interfaces vs Abstract Classes

### Collections Framework

- List: ArrayList, LinkedList
- Map: HashMap, LinkedHashMap, TreeMap
- Set: HashSet, TreeSet
- Queue, Deque, PriorityQueue

### Exception Handling

- Checked vs Unchecked exceptions
- Try-catch-finally
- Custom exceptions
- Try-with-resources

### Multithreading and Concurrency

- Thread lifecycle
- Runnable vs Callable
- ExecutorService, ThreadPool
- Synchronized, volatile keywords
- CompletableFuture

### Java 8+ Features

- Lambda expressions
- Stream API
- Optional class
- Functional interfaces
- Method references
- Default and static methods in interfaces

### JVM and Memory Management

- JVM architecture
- Heap vs Stack memory
- Garbage Collection
- ClassLoader mechanism
- Java memory model

### Resources

- Book: Head First Java
- YouTube: Telusko, CodeWithHarry (Java playlist)

---

## Phase 2 — DSA in Java

This is usually the biggest gap. Companies check DSA before anything else.

### Arrays and Strings

- Sliding window technique
- Two pointers approach
- Prefix sum
- Kadane's algorithm

### Linked Lists

- Reversal (iterative and recursive)
- Cycle detection (Floyd's algorithm)
- Merge sorted lists
- Find middle element

### Stack and Queue

- Monotonic stack problems
- BFS using Queue
- Implement stack using queue and vice versa
- Next greater element

### Trees

- Binary tree traversals: inorder, preorder, postorder, level order
- Binary Search Tree operations
- Height, diameter of tree
- Lowest Common Ancestor

### Graphs

- BFS and DFS traversal
- Detect cycle in directed and undirected graph
- Topological sort
- Shortest path: Dijkstra, Bellman-Ford

### Dynamic Programming

- Memoization vs Tabulation
- 0/1 Knapsack
- Longest Common Subsequence
- Longest Increasing Subsequence
- Coin change problem

### Sorting and Searching

- Binary search and its variations
- Merge sort, Quick sort
- Counting sort

---

## Phase 3 — Spring Boot and Backend

### Pre-Spring Essentials (Web Fundamentals + Maven)

- Client-Server architecture (how a browser/app talks to a server)
- HTTP protocol basics: request-response cycle
- HTTP methods: GET, POST, PUT, PATCH, DELETE and when to use which
- HTTP status codes: 2xx, 3xx, 4xx, 5xx (and the commonly used ones: 200, 201, 400, 401, 403, 404, 500)
- Request and Response structure: headers, body, query params, path params
- Statelessness of HTTP
- JSON as a data exchange format
- What is an API, and what makes an API "RESTful"
- Basics of Postman (testing APIs before building a frontend)
- Maven: project structure, pom.xml, dependencies, build lifecycle, plugins

### Spring Core (Framework Fundamentals)

- IoC container and Dependency Injection (constructor vs setter vs field injection)
- Bean lifecycle (instantiation, initialization, destruction)
- Bean scopes: Singleton, Prototype, Request, Session
- ApplicationContext vs BeanFactory
- @Component, @Service, @Repository, @Configuration, @Bean
- Aspect-Oriented Programming (AOP): cross-cutting concerns, @Aspect, @Before, @After, @Around, @Pointcut expressions, weaving (compile-time vs runtime)

### Spring Boot Essentials

- Auto-configuration (how Spring Boot decides beans automatically)
- application.properties / application.yml and profiles
- Starter dependencies
- Spring Boot Actuator (health checks, metrics)
- Externalized configuration with @Value and @ConfigurationProperties
- Custom auto-configuration and conditional beans (@Conditional, @ConditionalOnProperty)

### Spring MVC Architecture

- DispatcherServlet request flow (front controller pattern)
- @Controller vs @RestController
- @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
- @PathVariable, @RequestParam, @RequestBody, @ResponseBody

### REST API Development

- DTO pattern (separating entity from API contract)
- Request validation with @Valid and Bean Validation annotations
- Global exception handling with @ControllerAdvice and @ExceptionHandler
- Pagination and sorting (Pageable, Sort)
- ResponseEntity and proper HTTP status codes
- API versioning strategies
- HATEOAS basics

### Spring Data JPA and Hibernate

- Entity mapping (@Entity, @Id, @Column, @Table)
- Relationships: One-to-One, One-to-Many, Many-to-Many
- Repository pattern: JpaRepository, CrudRepository
- JPQL and native queries
- Lazy vs Eager loading (N+1 problem)
- Transaction management (@Transactional, propagation, isolation levels)
- Second-level caching (Hibernate + Ehcache/Redis)
- Query optimization and entity graphs

### Spring Security (Advanced)

- Authentication vs Authorization
- Security filter chain internals
- JWT implementation from scratch (access + refresh tokens)
- Role-based and permission-based access control (RBAC, method security with @PreAuthorize/@PostAuthorize)
- OAuth2 and OpenID Connect (Authorization Code flow, resource server, auth server)
- Password encoding (BCrypt, Argon2)
- CORS and CSRF handling
- Custom authentication providers and filters
- Securing microservices with a central identity provider (Keycloak basics)

### Reactive Programming (Spring WebFlux)

- Imperative vs reactive programming model
- Mono, Flux, and the Reactive Streams spec
- WebClient for non-blocking HTTP calls
- R2DBC for reactive database access
- Backpressure handling

### Messaging and Event-Driven Architecture

- Apache Kafka basics: producers, consumers, topics, partitions
- Spring Kafka integration
- RabbitMQ basics and Spring AMQP
- Event-driven vs request-driven design
- Idempotency and message ordering concerns

### Batch Processing

- Spring Batch: Jobs, Steps, ItemReader/Processor/Writer
- Chunk-oriented processing
- Scheduling batch jobs

### Testing

- Unit testing with JUnit 5
- Mocking with Mockito
- @WebMvcTest, @DataJpaTest, @SpringBootTest
- Integration testing with TestContainers
- Contract testing basics (Spring Cloud Contract)

### API Gateway

- Spring Cloud Gateway
- Routing and filters
- Rate limiting
- Load balancing concepts

### Microservices

- Service decomposition
- Eureka service discovery
- Feign client for inter-service communication
- Config server
- Circuit breaker with Resilience4j
- Distributed tracing (Sleuth/Micrometer Tracing + Zipkin)
- Saga pattern and distributed transactions
- API composition and BFF (Backend for Frontend) pattern

---

## Phase 4 — DevOps Tools

### Docker

- What is containerization
- Writing Dockerfile for Spring Boot app
- Docker commands: build, run, push, pull
- Docker Compose for multi-container apps
- Docker Hub

### Kubernetes

- Pods, Nodes, Clusters
- Deployments and ReplicaSets
- Services: ClusterIP, NodePort, LoadBalancer
- ConfigMaps and Secrets
- Basic kubectl commands

### CI/CD Pipeline

- GitHub Actions basics
- Writing a workflow for Java app
- Automated testing and deployment
- Jenkins basics

### Redis and Caching

- What is caching and why it matters
- Redis data structures
- Spring Boot Redis integration
- Cache strategies: Write-through, Write-back, Cache-aside
