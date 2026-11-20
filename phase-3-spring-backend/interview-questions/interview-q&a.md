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
