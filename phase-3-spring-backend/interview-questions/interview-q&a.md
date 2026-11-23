#### 1. What is HTTP? What is HTTPS?

HTTP is a communication protocol used to transfer data between a client and a server over the internet.
<br>
HTTPS is the secure version of HTTP. It uses TLS encryption to protect data transferred between the client and the server.

---

#### 2. What is a client-server architecture?

Client-Server Architecture is a software architecture in which an application is divided into two main components: client and server. The client sends requests to the server, and the server processes those requests and sends responses back to the client.

---

#### 3. What is a request and a response? What does each contain?

An HTTP request is a message sent by the client to the server to access a resource or perform an operation. It contains a request line, headers, and an optional body.
<br>
An HTTP response is a message sent by the server to the client after processing the request. It contains a status line, headers, and an optional body

---

#### 4. what are HTTP methods. When do you use each?

HTTP methods define the type of operation a client wants to perform on a server. GET is used to retrieve data, POST to create resources, PUT to replace a resource, PATCH to partially update it, and DELETE to remove a resource.

---

#### 5. GET vs POST?

GET is used to retrieve data from the server, whereas POST is used to send data and create a new resource on the server.

---

#### 6. What is idempotency? Which methods are idempotent?

Idempotency means if we send the same request multiple times, the result on the server remains the same as sending it once. GET, PUT, DELETE, are idempotent methods. POST is generally not idempotent.

---

#### 7. What are HTTP status codes?

HTTP status codes are three-digit numbers returned by the server to indicate whether an HTTP request was successful or failed. They are divided into five categories: 1xx for informational, 2xx for success, 3xx for redirection, 4xx for client errors, and 5xx for server errors.

---

#### 8. 401 vs 403?

401 means the user is not properly authenticated, for example, when the token is missing or invalid. 403 means the user is authenticated but does not have permission to access the requested resource.

---

#### 9. What is REST API?

REST stands for Representational State Transfer API. It allows a client to communicate with a server over HTTP using standard methods like GET, POST, PUT, and DELETE.

---

#### 10. What is an API?

An API is a way that allows two applications to communicate with each other.

---

#### 11. What is JSON? Why is it used?

JSON stands for JavaScript Object Notation. It is a lightweight data format used to exchange data between applications. It is easy to read, understand, and commonly used in REST APIs to send and receive data.

---

#### 12. What is Path variable vs query parameter vs request body?

Path Variable is used to pass a value as part of the URL, usually to identify a specific resource. Query Parameter used to pass additional information through the URL, usually for filtering, searching, or pagination. Request Body Used to send data inside the request body, usually when creating or updating a resource.

---

#### 13. What is an embedded server?

An embedded server is a server that is included inside an application. In Spring Boot, an embedded Tomcat server starts automatically when we run the application, so we don't need to deploy it on a separate external server.

---

#### 14. What is Maven?

Maven is a project management and build automation tool used in Java projects. It provides a standard project structure, manages dependencies, and automates tasks like compiling, testing, and packaging the application.

---

#### 15. What is pom.xml?

pom.xml stands for Project Object Model. It is the main configuration file in Maven where we define project information, dependencies, plugins, and build configurations.

---

#### 16. What is Spring Framework? What problem does it solve?

Spring is a Java framework used to build enterprise applications. It reduces boilerplate code and makes application development easier by providing features like Dependency Injection, Inversion of Control, and transaction management.

---

#### 17. What is IoC (Inversion of Control)?

IoC stands for Inversion of Control. It is a principle in which the Spring container takes responsibility for creating and managing objects instead of us creating and managing them manually.

---

#### 18. What is Dependency Injection?

Dependency Injection is a design pattern in which an object receives its required dependencies from outside instead of creating them itself. In Spring, the IoC container provides these dependencies automatically.

---

#### 19. Constructor injection vs field injection: which is better and why?

Constructor injection is better than field injection because it makes dependencies mandatory, supports immutability using final fields, and makes unit testing easier.

---

#### 20. What is the difference between Constructor and Setter and Field Injection?

Constructor injection provides dependencies through the constructor, setter injection provides them through setter methods, and field injection injects them directly into class fields using @Autowired.

---

#### 21. What is a Bean?

A Spring Bean is an object that is created and managed by the Spring IoC container. We can create beans using annotations like @Component, @Service, @Repository, or the @Bean annotation.

---

#### 22. What is the ApplicationContext vs BeanFactory?

ApplicationContext is an advanced version of BeanFactory. It extends the BeanFactory interface. BeanFactory and ApplicationContext are both Spring containers used to create and manage beans. BeanFactory provides basic bean management and uses lazy initialization by default, which means beans are created when they are requested. ApplicationContext provides additional features like event publishing and integration with Spring AOP. It uses eager initialization by default for singleton beans, which means beans are created when the container starts.

---

#### 23. @Autowired, @Qualifier, @Primary: when do you use each?

@Autowired is used to inject a dependency automatically by type. @Qualifier is used when multiple beans of the same type are available and we want to specify which bean to inject. @Primary is used to define a default bean when multiple beans of the same type are available.

---

#### 24. Spring vs Spring Boot?

Spring is a Java framework used to build enterprise applications. Spring Boot is built on top of the Spring Framework and makes development easier by providing auto-configuration, starter dependencies, and embedded servers. It reduces boilerplate code and manual configuration.

---

#### 25. Bean lifecycle?

The Spring Bean Lifecycle describes how Spring creates, initializes, uses, and destroys a bean. First, Spring creates the bean and injects its dependencies. Then it performs initialization callbacks and makes the bean ready to use. Finally, when the container is closed, Spring calls the destruction methods.

---

#### 26. What does @SpringBootApplication contain?

@SpringBootApplication is a combination of three annotations:

1. @SpringBootConfiguration Indicates that the class is a configuration class. It allows us to define beans using @Bean.
2. @EnableAutoConfiguration Enables Spring Boot's auto-configuration based on the dependencies available in the project.
3. @ComponentScan Tells Spring to scan the current package and its sub-packages to find components like @Component, @Service, and @Repository.

---

#### 27. How does auto-configuration work?

Auto-configuration is a feature of Spring Boot that automatically configures the required components based on the dependencies available in our project. It reduces the need for manual configuration.

---

#### 28. What are Spring Boot starters?

Spring Boot starters are predefined dependency packages that contain commonly required libraries for specific functionalities. They help us avoid adding multiple dependencies separately and make dependency management easier.

---

#### 29. application.properties vs application.yml?

Both application.properties and application.yml are used to configure Spring Boot applications. The main difference is their format. Properties uses key-value pairs, whereas YAML uses indentation and a nested structure. Both serve the same purpose, but YAML can be more readable for complex configurations.

---

#### 30. What are profiles? How do you use dev/prod configs?

Spring Boot profiles are used to maintain different configurations for different environments, such as development and production. We can create separate configuration files like application-dev.properties and application-prod.properties and activate a profile using the spring.profiles.active property.

---

#### 31. @Component vs @Service vs @Repository vs @Controller?

All four annotations are used to register classes as Spring Beans, but they serve different purposes. `@Component` is a general-purpose annotation, `@Service` is used for business logic, `@Repository` is used for database operations, and `@Controller` is used to handle web requests. `@Service`, `@Repository`, and `@Controller` are specialized forms of `@Component`.

---

#### 32. @Configuration and @Bean: what is the difference from @Component?

`@Component` is used to register a class as a Spring Bean through component scanning. `@Configuration` is used to define a configuration class, and `@Bean` is used on methods to register the objects returned by those methods as Spring Beans. We commonly use `@Bean` when we want to configure third-party classes.

---

#### 33. @Controller vs @RestController?

`@Controller` is mainly used in MVC applications to return views like HTML pages, whereas `@RestController` is used to build REST APIs and directly return data in the response body, usually in JSON format. `@RestController` is a combination of `@Controller` and `@ResponseBody`.

---

#### 34. @RequestParam vs @PathVariable vs @RequestBody?

`@PathVariable` is used to extract values from the URL path. `@RequestParam` is used to extract query parameters. `@RequestBody` is used to receive data from the HTTP request body, usually in JSON format.

---

#### 35. @GetMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping: PUT vs PATCH?

GET is used to fetch data, POST is used to create a resource, PUT is used to completely update or replace a resource, PATCH is used to partially update a resource, and DELETE is used to remove a resource.

---

#### 36. Lazy vs Eager loading?

Lazy loading fetches related data only when we access it, whereas eager loading fetches related data immediately along with the main entity. Lazy loading helps avoid unnecessary data fetching, while eager loading is useful when we always need the related data.

---

#### 37. What is ResponseEntity? Why use it?

ResponseEntity is a class in Spring that represents the complete HTTP response. We use it when we want to control the response status code, headers, and body.

---

#### 38. How do you handle exceptions globally?

In Spring Boot, we handle exceptions globally using @RestControllerAdvice and @ExceptionHandler. We create a common exception handler class and define methods to handle specific exceptions.

---

#### 39. What are @ControllerAdvice, @ExceptionHandler?

@ControllerAdvice is used to create a global exception-handling class that can handle exceptions across multiple controllers. @ExceptionHandler is used to handle specific exceptions

---

#### 40. How do you validate request data?

In Spring Boot, we validate request data using Bean Validation annotations like @NotNull, @NotBlank, @Email, and @Size. We apply these annotations to DTO fields and use @Valid in the controller method to trigger validation. If validation fails, Spring throws an exception that we can handle globally using @RestControllerAdvice.

---

#### 41. What is a DTO? Why not expose the Entity directly?

DTO stands for Data Transfer Object. It is a simple Java object used to transfer data between the client and the backend. We use DTOs instead of exposing entities directly for security, better separation, and flexibility. For example, a User entity may contain sensitive information like a password, but the User DTO can contain only the name and email.

---

#### 42. What is Spring AOP? Where have you used it?

Spring AOP stands for Aspect-Oriented Programming. It is used to separate common functionalities like logging, security, and transaction management from the main business logic.
<br>
For example, instead of writing logging code in every service method, we can create a separate Aspect and apply logging around those methods.
<br>
This helps us reduce code duplication and keep our business logic clean.

---

#### 43. What are filters?

Filters are components in Java web applications that intercept HTTP requests and responses. They are mainly used for tasks like authentication, logging, CORS handling, and modifying requests or responses. In Spring Boot, filters execute before the request reaches the DispatcherServlet.

---

#### 44. What are interceptors?

Interceptors are components in Spring MVC that intercept requests before and after controller execution.

We use interceptors for tasks like authorization, logging, and checking request information.

---

#### 45. What is the difference between filter, interceptor and spring aop?

A Filter belongs to the Servlet API and intercepts HTTP requests and responses before they reach the DispatcherServlet.
<br>
An Interceptor belongs to Spring MVC and works before and after controller execution.
<br>
Spring AOP works at the method execution level.
<br>
For example, we can use a Filter for JWT validation, an Interceptor for authorization checks, and Spring AOP for logging service method execution.

---

#### 46. JDBC vs JDBC Driver vs Spring JDBC?

JDBC stands for Java Database Connectivity. It is a Java API used to connect and interact with databases.
<br>
A JDBC Driver is a database-specific implementation of the JDBC API that allows Java applications to communicate with a particular database, such as MySQL.
<br>
Spring JDBC is a module of the Spring Framework that simplifies JDBC operations. It provides JdbcTemplate, which handles repetitive tasks like connection management, statement execution, and resource cleanup.

---

#### 47. What is ORM? Why use it?

ORM stands for Object-Relational Mapping. It is a technique that maps Java objects to database tables and their fields to table columns.
<br>
We use ORM to perform database operations using Java objects instead of writing SQL queries manually for every operation.
<br>
It reduces boilerplate code, improves productivity, and makes database operations easier.

---

#### 48. What is JPA vs Hibernate vs Spring Data JPA?

JPA, Hibernate, and Spring Data JPA are related but have different roles.

JPA is a specification that defines standards for object-relational mapping in Java.

Hibernate is an ORM framework that provides an implementation of the JPA specification.

Spring Data JPA is a Spring module that simplifies database operations by providing interfaces like JpaRepository, so we don't have to write basic CRUD methods manually.

---

#### 49. JpaRepository vs CrudRepository?

CrudRepository and JpaRepository are interfaces provided by Spring Data. CrudRepository provides basic CRUD operations like save, findById, findAll, and deleteById. JpaRepository also provides CRUS operations along with pagination and sorting. It also provides JPA-specific methods like flush().

---

#### 50. What does @Transactional do? Where does it not work?

@Transactional is an annotation provided by Spring that is used to manage database transactions.
<br>
It ensures that multiple database operations are treated as a single unit. If all operations succeed, the transaction is committed. If an unchecked exception occurs, the transaction is rolled back by default.
<br>
It may not work in some cases like self-invocation, object created manually, private methods and also checked exceptions do not trigger rollback of transaction.

---

#### 51. OneToOne, OneToMany, ManyToOne, ManyToMany: how do you map them? What is mappedBy?

@OneToOne is used when one entity is associated to only one other entity, like User and Profile.
<br>
@OneToMany is used when one entity is associated to multiple entities, like one Department having many Employees.
<br>
@ManyToOne is used when multiple entities are associated to one entity, like many Employees belonging to one Department.
<br>
@ManyToMany is used when multiple entities are associated to multiple entities, like Students and Courses.
<br>
The mappedBy attribute is used on the inverse side of a bidirectional relationship. It specifies the field in the owning entity that manages the relationship.

---

#### 52. What is a servlet? What is the DispatcherServlet in Spring MVC?

A Servlet is a Java server-side component that handles client requests and generates responses. It runs inside a Servlet Container like Tomcat.
<br>
DispatcherServlet is a special Servlet provided by Spring MVC. It acts as a Front Controller, receives incoming HTTP requests, finds the appropriate controller, and manages the request processing flow. So, DispatcherServlet is the central component that handles request routing in Spring MVC.

---

#### 53. What is Spring Boot?

Spring Boot is a Java-based framework built on top of the Spring Framework. It makes application development easier by reducing manual configuration and boilerplate code. It provides features like auto-configuration, starter dependencies, and embedded servers like Tomcat.

---

#### 54. What is Spring Initializr?

Spring Initializr is a web-based tool used to quickly generate Spring Boot projects. We can select the project type, programming language, Spring Boot version, and required dependencies. It generates a ready-to-use project structure that we can download and import into an IDE like IntelliJ IDEA. It saves time and reduces manual project setup.

---

#### 55. What are the basic Spring Boot Annotations?

Some commonly used Spring Boot annotations are:

1. `@SpringBootApplication`: Marks the main Spring Boot application class and enables auto-configuration and component scanning.
2. `@Component`: Marks a class as a Spring-managed bean.
3. `@Service`: Marks the service layer, where we write business logic.
4. `@Repository`: Marks the repository layer, which handles database operations.
5. `@Controller`: Used to create MVC controllers.
6. `@RestController`: Used to create REST APIs. It combines `@Controller` and `@ResponseBody`.
7. `@Autowired`: Used for dependency injection.
8. `@Configuration`: Marks a class that contains bean configuration.
9. `@Bean`: Registers an object as a Spring bean.
10. `@RequestMapping`, `@GetMapping`, and `@PostMapping`: Used to map HTTP requests to controller methods.

These annotations help us configure the application, manage dependencies, and handle HTTP requests.
