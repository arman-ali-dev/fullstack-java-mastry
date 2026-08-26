- Till now, we learned how Spring Core manages objects using the IoC container. In a pure Spring Core project, we usually start the container manually
- Then we provide a configuration class:
- Then we fetch a bean manually and call a method:
- This is useful for learning because it clearly shows how Spring works internally. But in real applications, this setup becomes repetitive
- Spring Boot solves this repetitive startup problem.

---

Spring Boot does not replace Spring Core. Spring Boot uses Spring Core and gives us a simpler way to start, configure, and run the Spring container. 

- Spring Boot itself is not only about web development. Web behavior comes later when we add web-related dependencies.
- Spring Boot can be used for:
    - Console applications
    - Web applications
    - Database applications
    - Batch applications
    - Microservices

- Spring Initializr is usually preferred because it creates the correct project structure, parent configuration, dependencies, and plugin setup.


---

In Spring Boot projects, we commonly use: spring-boot-starter-parent 

- The main benefit is version compatibility.
- For example, if we later add dependencies like Spring JDBC
- Maven can ask the Spring Boot parent:
    - Which version of this dependency should I use?
- The Spring Boot parent provides compatible versions.

 ---

What Is a Spring Boot Starter?

- A starter is a dependency shortcut.
- Instead of adding many related dependencies one by one, we add one starter, and Maven brings the required dependencies transitively.

---

So in Spring Core, we usually handle these steps ourselves:
1. Create ApplicationContext manually
2. Provide configuration class manually
3. Fetch bean manually
4. Trigger application logic manually

In Spring Boot - Spring Boot gives us a standard startup mechanism:

```java
SpringApplication.run(MyApplication.class, args);
```

This line starts and prepares the Spring application context for us.


- The most important line in a Spring Boot application is:
- SpringApplication.run(SpringBootCoreDemoApplication.class, args);
- This line:
    - Starts the Spring Boot application
    - Creates the application context
    - Reads configuration and properties
    - Performs component scanning
    - Create beans
    - Applies auto-configuration
    - Injects dependencies
- run() also returns the application context:

---

@SpringBootApplication is not a small annotation. It is a combination annotation. It roughly combines:
1. @SpringBootConfiguration - Marks the main configuration class of the Spring Boot application
2. @EnableAutoConfiguration - Enables Spring Boot's automatic configuration mechanism
3. @ComponentScan - Scans the current package and subpackages for Spring components
