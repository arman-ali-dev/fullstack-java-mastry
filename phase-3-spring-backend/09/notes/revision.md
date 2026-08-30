# Spring Boot: Why It Exists and How It Starts - Complete Notes

## 1. Recap: The Manual Way (Pure Spring Core)

Till now, we learned how Spring Core manages objects using the IoC container. In a pure Spring Core project, we usually start the container manually.

**The manual steps look like this** *(added — code example was missing)*:

```java
// 1. Start the container manually
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// 2. Fetch a bean manually
OrderService orderService = context.getBean(OrderService.class);

// 3. Call a method manually
orderService.placeOrder();
```

This is useful for learning because it clearly shows how Spring works internally. But in real applications, this setup becomes repetitive — every project needs the same boilerplate: create context, pass config class, fetch bean, call logic.

**Spring Boot solves this repetitive startup problem.**

---

## 2. Spring Boot Does Not Replace Spring Core

Spring Boot uses Spring Core underneath and gives us a simpler way to **start, configure, and run** the Spring container.

Spring Boot itself is not only about web development. Web behavior comes later when we add web-related dependencies.

**Spring Boot can be used for:**
- Console applications
- Web applications
- Database applications
- Batch applications
- Microservices

**Spring Initializr** is usually preferred because it creates the correct project structure, parent configuration, dependencies, and plugin setup.

---

## 3. spring-boot-starter-parent

In Spring Boot projects, we commonly use: `spring-boot-starter-parent`.

**The main benefit is version compatibility.** For example, if we later add dependencies like Spring JDBC, Maven can ask the Spring Boot parent: *"Which version of this dependency should I use?"* The Spring Boot parent provides compatible versions.

### How does the parent actually achieve this? *(added)*
Internally, `spring-boot-starter-parent` contains a **BOM (Bill of Materials)** — a big list of tested, compatible versions for hundreds of common libraries, stored in a `<dependencyManagement>` section. So when you add a dependency without specifying a version, Maven looks it up in this BOM and picks the version Spring Boot has already tested to work well together — you avoid manually figuring out "does version X of this work with version Y of that."

### What if my project already has a different parent? *(added)*
If your project can't use `spring-boot-starter-parent` directly (e.g. it already has a company parent POM), you can still get the same version management by importing `spring-boot-dependencies` as a BOM inside your own `<dependencyManagement>` section.

---

## 4. What Is a Spring Boot Starter?

A **starter** is a dependency shortcut. Instead of adding many related dependencies one by one, we add one starter, and Maven brings the required dependencies transitively.

**Common starter examples** *(added)*:

| Starter | Brings in |
|---|---|
| `spring-boot-starter-web` | Spring MVC, embedded Tomcat, JSON support — everything for building web apps/REST APIs |
| `spring-boot-starter-data-jpa` | Spring Data JPA, Hibernate, JDBC support |
| `spring-boot-starter-test` | JUnit, Mockito, and other testing tools |
| `spring-boot-starter-security` | Spring Security for authentication/authorization |

---

## 5. Manual Steps vs Spring Boot's Startup Mechanism

In Spring Core, we usually handle these steps ourselves:
1. Create `ApplicationContext` manually
2. Provide configuration class manually
3. Fetch bean manually
4. Trigger application logic manually

**In Spring Boot**, Spring Boot gives us a standard startup mechanism:

```java
SpringApplication.run(MyApplication.class, args);
```

This line starts and prepares the Spring application context for us.

### The Most Important Line

The most important line in a Spring Boot application is:
```java
SpringApplication.run(SpringBootCoreDemoApplication.class, args);
```

**This line:**
- Starts the Spring Boot application
- Creates the application context
- Reads configuration and properties
- Performs component scanning
- Creates beans
- Applies auto-configuration
- Injects dependencies

`run()` also returns the application context — meaning you can still use it manually if needed, just like in Spring Core:
```java
ApplicationContext context = SpringApplication.run(MyApplication.class, args);
```

### The complete main class *(added — full picture was missing)*
```java
@SpringBootApplication
public class SpringBootCoreDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCoreDemoApplication.class, args);
    }
}
```

This one small class replaces all the manual setup — no `AnnotationConfigApplicationContext`, no separate `AppConfig`, no manual `getBean()` calls needed to get the application running.

---

## 6. @SpringBootApplication

`@SpringBootApplication` is not a small annotation — it is a **combination annotation**. It roughly combines:

1. **`@SpringBootConfiguration`** — Marks the main configuration class of the Spring Boot application (internally this is just `@Configuration`, so it's still a valid source of bean definitions).
2. **`@EnableAutoConfiguration`** — Enables Spring Boot's automatic configuration mechanism.
3. **`@ComponentScan`** — Scans the current package and subpackages for Spring components.

### How does Auto-Configuration actually work? *(added — was mentioned but not explained)*

`@EnableAutoConfiguration` tells Spring Boot: *"Look at what's on the classpath (which dependencies/JARs are present), and automatically configure beans that make sense for this setup — without me writing that configuration myself."*

**Example:** If `spring-boot-starter-web` is on the classpath, Spring Boot detects it and automatically configures an embedded Tomcat server, a `DispatcherServlet`, JSON message converters, etc. — none of which you had to set up by hand.

This works using **conditional annotations** internally, like `@ConditionalOnClass` (only apply this config if a certain class is present on the classpath) and `@ConditionalOnMissingBean` (only apply this config if you haven't already defined your own bean of that type).

### You can always override auto-configuration *(added)*
If you define your own bean of a type that Spring Boot would normally auto-configure, **your bean wins** — Spring Boot's `@ConditionalOnMissingBean` checks ensure auto-configuration backs off and lets your custom configuration take priority.

### Why @ComponentScan here matters *(added)*
Since `@SpringBootApplication` includes `@ComponentScan` with no arguments, it scans starting from the **package of the class it's placed on**, and all sub-packages. This is exactly why Spring Boot convention says: place your main application class in the **root package**, above all your other classes — otherwise some of your components won't be found.
