#### 1. What is the difference between a client and a server?

A client is an application or device that sends requests to a server and receives responses. A server is a system or application that receives client requests, processes them, and sends back responses.

---

#### 2. What is HTTP? What is the difference between HTTP and HTTPS?

HTTP stands for HyperText Transfer Protocol. It is a communication protocol used to transfer data between a client and a server.
<br>
HTTPS stands for HyperText Transfer Protocol Secure. It is the secure version of HTTP that uses TLS to encrypt communication between the client and the server.

---

#### 3. What does "HTTP is stateless" mean?

HTTP is a stateless protocol, which means the server does not automatically remember previous requests from a client. Each HTTP request is treated independently.

---

#### 4. What are the main parts of an HTTP request and an HTTP response?

An HTTP request mainly consists of three parts: Request Line, Headers, and Body. The Request Line contains the HTTP method, request URL or path, and HTTP version. Headers provide additional information, such as content type, authorization, and accepted response formats. The Body contains the actual data sent by the client,

---

#### 5. PUT vs PATCH? (And when do you use GET, POST and DELETE?)

GET is used to retrieve data from the server. POST is used to create a new resource or perform an operation on the server. PUT is used to completely replace an existing resource. PATCH is used to partially update an existing resource. DELETE is used to remove an existing resource from the server.

---

#### 6. Path variable vs query parameter vs request body?

@PathVariable extracts values directly from the URL path. @RequestParam extracts query parameters from the URL. @RequestBody reads data from the HTTP request body.

---

#### 7. What are HTTP status codes?

HTTP status codes are three-digit numbers returned by the server to indicate the result of a client's request.

---

#### 8. What is REST API?

REST API stands for Representational State Transfer Application Programming Interface. used for communication between clients and servers over HTTP.

---

#### 9. What is an API?

API stands for Application Programming Interface. It is a way for communication between client and server.

---

#### 10. What is JSON? Why is it used?

JSON stands for JavaScript Object Notation. It is a lightweight, text-based data format used to store and exchange data between different applications.

---

#### 11. What is the difference between an IP address and a port number?

An IP address identifies a device. whereas a port number identifies a specific application or service running on that device.

---

#### 12. What is DNS and how does it work?

DNS stands for Domain Name System. It is used to translate human-readable domain names into IP addresses because computers use IP addresses to communicate over a network.
<br>
When a user enters a domain name in the browser, the browser first checks its cache. If the IP address is not available, the request goes to a DNS resolver.

---

#### 13. What is Spring Initializr?

Spring Initializr is a web-based tool used to quickly generate Spring Boot projects. It allows developers to select the project type, programming language, Spring Boot version, Java version, and required dependencies. Based on these selections, it generates a ready-to-use project structure with the necessary configuration files.

---

#### 14. What is the difference between JAR and WAR packaging?

JAR stands for Java Archive, and WAR stands for Web Application Archive. Both are packaging formats used to distribute Java applications.
<br>
A JAR file is commonly used for standalone Java and Spring Boot applications. It can include an embedded server like Tomcat, so we can run it directly using the java -jar command.
<br>
A WAR file is mainly used for web applications that are deployed to an external application server like Tomcat.

---

#### 15. What is a dependency? How are dependencies managed in a Spring Boot project?

A dependency is an external library or module that our project needs to use specific functionality without implementing everything from scratch. Dependencies are managed using build tools like Maven or Gradle.

---

#### 16. What is embedded Tomcat?

Embedded Tomcat is a web server that is included inside a Spring Boot application. Normally, we need to install Tomcat separately and deploy our web application to it. But with embedded Tomcat, we don't need to install or configure an external server. Spring Boot automatically starts Tomcat when we run the application.

---

#### 17. What is a fat JAR, and why does Spring Boot use it?

A Fat JAR, also known as executable JAR file that contains the application's compiled classes, resources, and required dependencies. Spring Boot uses Fat JARs to make application deployment easier.

---

#### 18. What is the classpath?

Classpath is the path or set of locations where the Java compiler and JVM search for required classes, resources, and libraries.

---

#### 19. What is Maven and why do we need it?

Maven is a build automation and dependency management tool mainly used for Java projects. We use Maven to manage project dependencies, compile source code, run tests, and package applications into JAR or WAR files.
<br>
Maven uses a configuration file called pom.xml, where we define project information, dependencies, plugins, and build configurations.

---

#### 20. Maven vs Gradle?

Maven and Gradle are both build automation and dependency management tools used in Java projects. The main difference is their configuration Maven uses XML-based configuration through the pom.xml file, whereas Gradle uses Script-Based configuration through build.gradle file.

---

#### 21. What is pom.xml?

POM stands for Project Object Model. The pom.xml file is the main configuration file of a Maven project. It contains project information, dependencies, plugins, and build configurations.

---

#### 22. What is a starter dependency?

A starter dependency is a group of dependencies for a specific functionality. Instead of adding multiple related dependencies manually, we can add a single starter dependency, and It brings the required libraries.

---

#### 23. What are transitive dependencies?

Transitive dependencies are the required dependencies of a particular dependency that are automatically included in our project.

---

#### 24. Where does Maven download dependencies from?

Maven downloads dependencies from repositories.
<br>
There are three main types of Maven repositories:

1. Local Repository: It is located on our computer.
2. Central Repository: It is the default public repository.
3. Remote Repository: These are additional repositories, such as company-specific or third-party repositories.

---

#### 25. What is tight coupling?

Tight coupling occurs when one class is highly dependent on another specific class. For example, if a UserService directly creates an EmailService object, then UserService becomes tightly coupled to EmailService.

---

#### 26. What is Dependency Injection?

Dependency Injection is a design technique in which an object receives its required dependencies from an external source instead of creating them itself.
<br>
In Spring Boot, the IoC container manages beans and injects the required dependencies automatically.

---

#### 27. What is IOC? What is the relationship between IoC and DI?

IoC (Inversion of Control) is a design principle in which the control of object creation and dependency management is transferred from our application code to a framework like Spring. Dependency Injection is a technique used to achieve IoC.

---

#### 28. What are the types of DI? which is better and why?

There are three main types of Dependency Injection in Spring:

1. Constructor Injection: Dependencies are provided through the class constructor.
2. Setter Injection: Dependencies are provided through setter methods.
3. Field Injection: Dependencies are injected directly into fields using the @Autowired annotation.

Constructor Injection is generally recommended because it makes dependencies mandatory, supports immutability using final fields, and makes unit testing easier.

---

#### 29. What is the Spring IoC Container? What is ApplicationContext?

The Spring IoC Container is the core part of the Spring Framework that creates, configures, and manages application objects, known as beans.
<br>
It is responsible for dependency injection and managing the bean lifecycle.
<br>
ApplicationContext is an interface and it is the way to access and work with the Spring IoC Container.

---

#### 30. ApplicationContext vs BeanFactory

Both BeanFactory and ApplicationContext are interfaces of the Spring IoC Container.
<br>
BeanFactory is the basic container that creates and manages beans and supports dependency injection. It initializes beans lazily by default.
<br>
ApplicationContext extends BeanFactory and provides additional features like event handling, internationalization, resource loading, and environment management. It eagerly initializes singleton beans by default.
<br>
In most Spring applications, we use ApplicationContext because it provides more features and is more suitable for application development.

---

#### 31. What is a Spring bean?

A Spring Bean is simply a Java object that is created, configured, and managed by the Spring IoC Container.

---

#### 32. What is autowiring? How does Spring inject a dependency into a bean?

Autowiring is a feature of Spring that automatically injects the required dependencies into a Spring Bean. For example, if UserService requires EmailService, Spring finds the EmailService bean and injects it through the constructor.

---

#### 33. What is bean scope? What is the default scope?

Bean Scope defines the lifecycle and the number of instances Spring creates for a particular bean.
<br>
Spring provides six main scopes: Singleton, Prototype, Request, Session, Application, and WebSocket.
<br>
The default bean scope is Singleton.
<br>
In Singleton scope, Spring creates only one instance per bean definition within a Spring IoC Container and reuses that same instance whenever it is requested.
<br>
We can also define a bean's scope using the @Scope annotation.

---

#### 34. What do @Configuration and @ComponentScan do?

@Configuration tells Spring that a class contains configuration information. We commonly use it with the @Bean annotation to manually define Spring Beans.
<br>
@ComponentScan tells Spring which packages to scan to find components like @Component, @Service, @Repository, and @Controller, and register them as beans.
<br>
In Spring Boot, @SpringBootApplication already includes both @Configuration and @ComponentScan.

---

#### 35. @Component vs @Bean: when do you use each?

Both @Component and @Bean are used to register objects as Spring Beans, but they work differently.
<br>
@Component is a class-level annotation. We use it when we want Spring to automatically detect and manage our application classes through component scanning.
<br>
@Bean is a method-level annotation, usually used inside a @Configuration class. We use it when we want to manually define a bean, especially for third-party library classes.
<br>
So, we use @Component for automatic bean detection and @Bean for explicit bean creation.

---

#### 36. What is a BeanDefinition?

A BeanDefinition is metadata that contains information about how Spring should create and configure a bean.
<br>
It contains details like the bean's class name, scope, constructor arguments, dependencies, and lifecycle methods.
<br>
The Spring IoC Container uses this metadata to create and manage the actual bean.

---

#### 37. What happens if multiple beans of the same type exist? How do @Primary and @Qualifier help, and which one wins?

If multiple beans of the same type exist in the Spring Container and we try to inject that type without specifying which bean we need, Spring may throw a NoUniqueBeanDefinitionException.
<br>
We can solve this using @Primary and @Qualifier.
<br>
@Primary tells Spring which bean should be selected by default when multiple candidates exist.
<br>
@Qualifier allows us to explicitly specify which bean we want to inject.

---

#### 38. How does Auto-configuration work?

Auto-configuration is a feature of Spring Boot that automatically configures beans based on the dependencies available in the pom.xml. It is enabled through the @EnableAutoConfiguration annotation, which is included in @SpringBootApplication.

---

#### 39. What are request, session and application scopes? Where can they be used?

Request, Session, and Application are three web-aware bean scopes in Spring.

1. Request Scope: Spring creates a separate bean instance for each HTTP request. It is useful for storing request-specific data.
2. Session Scope: Spring creates one bean instance for each HTTP session. It is useful for storing user-specific session data, such as a shopping cart.
3. Application Scope: Spring creates one bean instance per ServletContext. It is useful for application-level shared data.

---

#### 40. Eager vs lazy initialization. How do you make the whole application lazy?

Eager initialization means Spring creates a bean during application startup, even if it is not immediately required. It is the default behavior for singleton beans.
<br>
Lazy initialization means Spring creates a bean only when it is first requested or needed. We can enable it using the @Lazy annotation.
<br>
To make the whole Spring Boot application lazy, we can add the following property in application.properties:
<br>
spring.main.lazy-initialization=true

---

#### 41. Explain the Spring bean lifecycle.

The Spring Bean Lifecycle describes the different stages a bean goes through, from creation to destruction.
<br>
Spring creates the bean object. Spring injects the required dependencies. Initialization callbacks execute, such as @PostConstruct.Bean is ready for application use. Spring invokes cleanup callbacks when the container shuts down.

---

#### 42. What are @PostConstruct and @PreDestroy? Are destroy callbacks called for prototype beans?

@PostConstruct and @PreDestroy are Spring bean lifecycle annotations.
<br>
@PostConstruct is executed after the bean is created and its dependencies are injected. We use it for initialization tasks.
<br>
@PreDestroy is executed before a managed bean is destroyed. We use it for cleanup tasks, such as releasing resources.
<br>
Spring does not automatically call their destruction callbacks. Spring creates and initializes prototype beans, but after handing them to the application, it does not manage their complete lifecycle.

---

#### 43. What is Spring Boot?

Spring Boot is a framework built on top of the Spring Framework that simplifies Java application development.
<br>
It provides features like auto-configuration, starter dependencies, and embedded servers, which reduce manual configuration and boilerplate code.
<br>
With Spring Boot, we can quickly develop and run standalone applications without requiring a separate external server.

---

#### 44. What does SpringApplication.run() do?

`SpringApplication.run()` is used to start a Spring Boot application.
<br>
It creates and initializes the ApplicationContext, performs auto-configuration, scans components, and creates and manages Spring beans. it also starts the embedded server, such as Tomcat.

---

#### 45. What does @SpringBootApplication contain?

`@SpringBootApplication` is a combination of three main annotations:

1. `@SpringBootConfiguration` – Marks the class as a configuration class.
2. `@EnableAutoConfiguration` – Enables automatic configuration based on the dependencies available in the classpath.
3. `@ComponentScan` – Scans the current package and its sub-packages to find Spring components and register them as beans.

Instead of writing these three annotations separately, we can simply use `@SpringBootApplication`.

---

#### 46. What is externalized configuration, and why do we need it?

Externalized configuration means keeping application configuration outside the Java code instead of hardcoding it.
<br>
In Spring Boot, we can define configuration using application.properties, application.yml, environment variables, or command-line arguments.
<br>
We need it because different environments, such as development, testing, and production, may require different configurations.

---

#### 47. application.properties vs application.yml?

Both application.properties and application.yml are used to define external configuration in Spring Boot.
<br>
The main difference is their syntax.
<br>
In application.properties, we use a key-value format, whereas application.yml uses a hierarchical structure with indentation.
<br>
YAML is generally more readable for complex and nested configurations, while properties files are simple and straightforward.
<br>
Both formats provide the same configuration capabilities, so we can choose either based on our preference.

---

#### 48. If the same property is defined in multiple places, which one wins?

In Spring Boot, if the same property is defined in multiple configuration sources, the value from the higher-priority source wins.
<br>
For example, command-line arguments have higher priority than environment variables, system properties, and application.properties or application.yml.

---

#### 49. What are profiles?

Profiles in Spring Boot allow us to maintain different configurations for different environments, such as development, testing, and production.
<br>
We can create separate configuration files like application-dev.properties and application-prod.properties.
<br>
We can activate a profile using the spring.profiles.active property.
<br>
Based on the active profile, Spring Boot loads the corresponding configuration.

---

#### 50. @Value vs @ConfigurationProperties: when do you use which?

Both @Value and @ConfigurationProperties are used to read external configuration in Spring Boot.
<br>
I use @Value when I need to inject a few individual properties directly into a class.
<br>
I use @ConfigurationProperties when I have multiple related properties that need to be grouped and managed together.

---

### 51. CommandLineRunner vs ApplicationRunner?

Both CommandLineRunner and ApplicationRunner are interfaces in Spring Boot used to execute code after the application context has been initialized and before the application is considered fully started.
<br>
The main difference is how they handle command-line arguments.
<br>
CommandLineRunner receives arguments as a String array, whereas ApplicationRunner receives an ApplicationArguments object.

---

#### 52. What is a Servlet?

A Servlet is a Java server-side component used to handle client requests and generate responses
