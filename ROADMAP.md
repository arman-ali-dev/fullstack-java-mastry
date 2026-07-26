# Java Full Stack Mastery Roadmap

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

## Phase 4 — Databases (MongoDB + MySQL Deep Dive)

### MongoDB Basics

- Document-oriented model: collections, documents, BSON
- CRUD operations: insertOne, find, updateOne, deleteOne and their bulk variants
- Query operators: comparison ($eq, $gt, $lt), logical ($and, $or, $not), element ($exists, $type)
- Schema design principles: embedding vs referencing
- Indexes: single-field, compound, and their effect on query performance
- Aggregation pipeline basics: $match, $group, $project, $sort, $limit
- Connecting MongoDB with Spring Boot using Spring Data MongoDB
- MongoTemplate vs MongoRepository

### MySQL — In Depth

- Relational model fundamentals: tables, rows, columns, primary/foreign keys
- Data types and storage considerations
- DDL, DML, DQL, DCL, TCL command categories
- Joins: inner, left, right, full outer, self join, cross join
- Subqueries and correlated subqueries
- Aggregate functions: COUNT, SUM, AVG, MIN, MAX with GROUP BY and HAVING
- Set operations: UNION, UNION ALL, INTERSECT (via workarounds), EXCEPT
- Normalization: 1NF, 2NF, 3NF, BCNF — and when to denormalize
- Indexing: B-Tree indexes, composite indexes, covering indexes, index selectivity
- Query execution plan: reading EXPLAIN output, identifying full table scans
- Transactions: ACID properties, COMMIT, ROLLBACK, SAVEPOINT
- Isolation levels: Read Uncommitted, Read Committed, Repeatable Read, Serializable
- Locking: row-level vs table-level locks, deadlocks and how to avoid them
- Stored procedures, functions, and triggers
- Views and materialized view concepts
- Window functions: ROW_NUMBER, RANK, DENSE_RANK, LEAD, LAG, PARTITION BY
- Partitioning strategies for large tables
- Replication basics: master-slave, master-master
- Connection pooling (HikariCP) with Spring Boot
- Database migration tools: Flyway, Liquibase

### SQL vs NoSQL

- When to choose relational vs document-based storage
- Trade-offs: consistency vs flexibility vs scalability
- Polyglot persistence in real-world applications

---

## Phase 5 — HTML & CSS

### HTML Fundamentals

- Document structure: DOCTYPE, html, head, body
- Semantic elements: header, nav, main, section, article, aside, footer
- Text elements, lists, tables
- Forms: input types, labels, fieldsets, form validation attributes
- Links, images, and media embedding (audio, video)
- Attributes: id, class, data-\* attributes
- Accessibility basics: alt text, ARIA roles, semantic structure for screen readers
- Meta tags: viewport, charset, SEO-relevant tags

### CSS Fundamentals

- Selectors: type, class, id, attribute, pseudo-class, pseudo-element
- Specificity and the cascade
- Box model: content, padding, border, margin
- Display types: block, inline, inline-block, none
- Positioning: static, relative, absolute, fixed, sticky
- Flexbox: container and item properties, common layout patterns
- CSS Grid: grid-template-columns/rows, grid-area, gap, placing items
- Responsive design: media queries, mobile-first approach
- Units: px, %, em, rem, vw, vh and when to use which
- CSS variables (custom properties)
- Transitions and animations: keyframes, timing functions
- Typography: font-family, line-height, letter-spacing, web fonts
- CSS methodologies: BEM naming convention
- Preprocessors: SCSS/SASS basics — variables, nesting, mixins
- Utility-first CSS: Tailwind CSS fundamentals

---

## Phase 6 — JavaScript

### Core JavaScript

- Variables: var, let, const and scoping differences
- Data types: primitives vs reference types
- Operators and type coercion
- Control flow: if-else, switch, ternary
- Loops: for, while, do-while, for-in, for-of
- Functions: declarations, expressions, arrow functions, default parameters, rest parameters
- Hoisting and the temporal dead zone
- Closures and lexical scope
- The `this` keyword: behavior in different contexts

### Objects and Arrays

- Object creation, property access, computed properties
- Array methods: map, filter, reduce, forEach, find, some, every
- Destructuring: object and array
- Spread and rest operators
- Immutability patterns

### Asynchronous JavaScript

- Call stack, event loop, and task queue (macrotasks vs microtasks)
- Callbacks and callback hell
- Promises: creation, chaining, Promise.all, Promise.race, Promise.allSettled
- async/await syntax and error handling with try-catch

### DOM Manipulation

- Selecting elements: querySelector, getElementById, etc.
- Creating, modifying, and removing DOM elements
- Event handling: addEventListener, event bubbling and capturing, event delegation
- Form handling and validation in vanilla JS

### Browser APIs

- Fetch API for HTTP requests
- LocalStorage vs SessionStorage vs Cookies
- JSON parsing and stringifying

### ES6+ Features

- Template literals
- Modules: import/export
- Classes: constructor, methods, inheritance with extends/super
- Generators and iterators (conceptual understanding)
- Optional chaining and nullish coalescing

### Tooling Basics

- npm/yarn package management
- Understanding package.json
- Basic bundler concepts (Vite/Webpack)

---

## Phase 7 — React.js

### React Fundamentals

- JSX syntax and how it compiles to JavaScript
- Functional components vs class components (functional-first approach)
- Props: passing data, prop types, default props
- Rendering lists with keys
- Conditional rendering patterns

### Hooks

- useState: managing local state
- useEffect: side effects, dependency arrays, cleanup functions
- useContext: consuming context without prop drilling
- useRef: DOM references and mutable values
- useMemo and useCallback: memoization for performance
- useReducer: managing complex state logic
- Custom hooks: extracting reusable logic

### Component Design

- Component composition vs inheritance
- Controlled vs uncontrolled components
- Lifting state up
- Container/Presentational component pattern

### Routing

- React Router: Routes, Route, Link, useNavigate, useParams
- Nested routes and layouts
- Protected/private routes

### State Management

- Context API for global state
- Introduction to Redux Toolkit: store, slices, actions, reducers
- Zustand as a lightweight alternative
- When to reach for a state management library vs local state

### Forms in React

- Controlled form inputs
- Form libraries: React Hook Form basics
- Validation with libraries like Zod or Yup

### API Integration

- Fetching data with useEffect + fetch/axios
- Introduction to React Query (TanStack Query): caching, refetching, mutations
- Handling loading and error states

### Performance and Optimization

- React.memo for component memoization
- Code splitting with React.lazy and Suspense
- Avoiding unnecessary re-renders

### Testing

- Component testing with React Testing Library
- Jest basics for unit testing

### Build and Deployment

- Vite as the build tool
- Environment variables in React apps
- Production build and static hosting basics

---

## Phase 8 — DevOps and Deployment

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
- Writing a workflow for a full stack app (backend + frontend)
- Automated testing and deployment
- Jenkins basics

### Redis and Caching

- What is caching and why it matters
- Redis data structures
- Spring Boot Redis integration
- Cache strategies: Write-through, Write-back, Cache-aside

### Hosting and Infrastructure

- Deploying Spring Boot backend (Render/Railway/AWS EC2 basics)
- Deploying React frontend (Vercel/Netlify)
- Environment configuration across dev/staging/prod
- Basics of Nginx as a reverse proxy
