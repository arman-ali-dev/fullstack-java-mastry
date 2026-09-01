# Spring Core: Container, Configuration, and Annotations - Complete Notes

## 1. Recap: Who Creates the Objects?

In a well-designed application, a class should focus on its own responsibility. For example, `OrderService` should focus on placing an order. It should not be responsible for creating the object of `PaymentService`.

Now `OrderService` does not create its dependency. It receives the dependency from outside. **This is Dependency Injection.**

### The Real Questions
At the most basic level, every application needs objects. Those objects may also need other objects. So the real questions are:
- Who will create these objects?
- Who will connect them together?
- Who will manage their lifecycle?

| Without Spring | With Spring |
|---|---|
| `main()` creates objects | Spring IoC container creates objects |
| `main()` connects objects | Spring IoC container connects objects |
| `main()` behaves like a small manual container | Spring IoC container manages their lifecycle |

---

## 2. Setting Up Spring Core (Annotation-Based)

To work with Spring Core using annotation-based configuration:
1. Create a Maven project.
2. Add the `spring-context` dependency.

`spring-context` gives us important container features such as:
- `ApplicationContext`
- Annotation-based configuration
- Component scanning
- Bean creation and dependency injection

**A Spring Bean is an object whose creation, dependency wiring, and lifecycle are managed by the Spring IoC container.**

---

## 3. Two Configuration Styles

Spring can manage objects mainly through two configuration styles:
1. **Annotation-based configuration**
2. **XML-based configuration**

### XML-based configuration example *(added)*
Before annotations became popular, Spring beans were defined in an XML file like this:

```xml
<beans>
    <bean id="paymentService" class="com.coderarmy.service.PaymentService" />
    <bean id="orderService" class="com.coderarmy.service.OrderService">
        <constructor-arg ref="paymentService" />
    </bean>
</beans>
```

This is rarely used in modern Spring/Spring Boot projects — annotation-based (or Java-based `@Configuration`) is the standard today. It's still worth knowing it exists, especially for older/legacy codebases.

---

## 4. Reflection: Why `Student.class` Matters

When we write something like: `Student.class` — we are not creating a `Student` object. Instead, we are referring to a special object of type `Class`.

Example: `Class<Student> c = Student.class;`

It contains metadata about the `Student` class, such as:

```
Class name    -> Student
Fields        -> name, age
Methods       -> study()
Constructors  -> Student()
Annotations   -> @Component, @Service, etc.
```

### Why this matters for Spring *(added)*
Spring uses **reflection** to read this metadata at runtime — to discover which classes are annotated with `@Component`, to find their constructors, and to actually create and inject objects — all *without you writing any `new SomeClass()` code yourself*. This is the underlying mechanism that makes annotation-based Spring possible.

---

## 5. Telling Spring Which Classes to Manage

Spring does not automatically manage every class in the project. We need to tell Spring which classes are eligible to become beans.

- One common way is by using `@Component`.
- But just writing `@Component` is not enough — Spring also needs to know **where** it should search for such classes. That is where `@ComponentScan` comes in.

```java
@Component
public class PaymentService {
    // ...
}
```

---

## 6. ApplicationContext: The Spring IoC Container

`ApplicationContext` represents the Spring IoC container. It is responsible for:
- Reading configuration
- Creating beans
- Resolving dependencies
- Managing bean lifecycle
- Providing beans when requested

`ApplicationContext` is an **interface**.

For annotation-based configuration, we commonly use: `AnnotationConfigApplicationContext` — an implementation of `ApplicationContext`. It starts a Spring container using Java annotation-based configuration.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

This means:
- Start the Spring container.
- Read instructions from `AppConfig.class`.
- Use annotation-based configuration.
- Create and manage beans accordingly.

### ApplicationContext vs BeanFactory *(added)*
`ApplicationContext` is actually built on top of a more basic interface called `BeanFactory`. `BeanFactory` provides the core container functionality (creating/managing beans), while `ApplicationContext` adds extra features on top — like event handling, internationalization support, and easier integration with annotations. In almost all real Spring applications, you use `ApplicationContext`, not `BeanFactory` directly.

---

## 7. AppConfig: The Configuration Class

`AppConfig` is a configuration class. This class tells Spring:
- This is a configuration class.
- Scan the package `in.coderarmy` (or your base package).
- Find classes marked with annotations like `@Component`.
- Create their beans.
- Wire their dependencies.

```java
@Configuration
@ComponentScan(basePackages = "com.coderarmy")
public class AppConfig {
    // configuration instructions live here
}
```

### @Configuration
`@Configuration` tells Spring that a class contains Spring configuration instructions. When Spring sees this, it understands:
- This is not just a normal class.
- This class may contain Spring setup instructions.
- This class can be a source of bean definitions.

A configuration class may contain:
- `@ComponentScan`
- `@Bean` methods
- Other configuration-related instructions

### @ComponentScan
When Spring starts, it needs to know where to search for classes marked with annotations like `@Component`. `@ComponentScan` tells Spring:
- Start scanning from `com.coderarmy`.
- Also scan its sub-packages.
- Find classes marked with `@Component`, `@Service`, `@Repository`, `@Controller`, etc.
- Register them as beans.

---

## 8. Getting a Bean from the Container

After Spring creates and stores beans inside the container, we can ask the container for a bean:

```java
OrderService service = context.getBean(OrderService.class);
```

### Other ways to call getBean() *(added)*
```java
// by bean name (returns Object, needs casting)
OrderService service = (OrderService) context.getBean("orderService");

// by name and type (no casting needed)
OrderService service = context.getBean("orderService", OrderService.class);
```

---

## 9. Constructor Injection (Detailed)

In constructor injection, dependencies are provided through the constructor.

So if `OrderService` needs `PaymentService`, the cleanest time to provide `PaymentService` is while creating `OrderService`. That means the object is created in a complete and usable state.

- If `OrderService` cannot work without `PaymentService`, constructor injection makes that requirement clear.
- With constructor injection, we can manually create and test the class.
- We can use `final` — this means once the dependency is assigned, it cannot be changed accidentally.

```java
@Service
public class OrderService {
    private final PaymentService paymentService;

    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

---

## 10. Field Injection (Detailed)

In field injection, Spring directly injects the dependency into a field. This works because Spring can use **reflection** to set the field value.

However, field injection is generally not preferred. Reasons:
- The dependency is hidden.
- The class cannot be easily tested without Spring.
- The field cannot be marked as `final`.
- The object can exist in an incomplete state before Spring injects the field.

```java
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;
}
```

---

## 11. Setter Injection (Detailed)

In setter injection, Spring creates the object first and then calls a setter method to provide the dependency.

Steps:
1. Create `OrderService` object using the no-argument constructor.
2. Call `setPaymentService()`.
3. Pass `PaymentService` into the setter.

Setter injection is useful when a dependency is **optional** or can be **changed after object creation**.

```java
@Service
public class OrderService {
    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

---

## 12. What Happens Step-by-Step When the Container Starts

When we write:
```java
new AnnotationConfigApplicationContext(AppConfig.class);
```

**Step 1: Spring Starts the Container**
Spring creates an `ApplicationContext`. This becomes the IoC container for our application.

**Step 2: Spring Reads AppConfig.class**
Spring looks at the class passed to it and understands:
- This class contains configuration instructions.
- It needs to read annotations present on this class.

**Step 3: Spring Processes @ComponentScan**
Spring understands: search inside `com.coderarmy` and its sub-packages.

**Step 4: Spring Finds Component Classes**
Spring searches the given package and finds classes marked with annotations such as `@Component`, `@Service`, `@Repository`, `@Controller`.

**Step 5: Spring Creates Bean Definitions**
Before creating actual objects, Spring first stores information about those objects. For example, for `PaymentService`, Spring may store metadata like:

```
Bean name          -> paymentService
Bean class         -> com.coderarmy.service.PaymentService
Scope              -> singleton
Dependencies       -> none
Creation strategy  -> constructor
```

This metadata is called a **BeanDefinition**. A `BeanDefinition` is not the actual object — it's just the blueprint/instructions for creating it.

**Step 6: Spring Creates Bean Objects**
Now Spring has a list of bean definitions. If `PaymentService` has no dependency, Spring can create it easily.

**Step 7: Spring Creates OrderService**
To create `OrderService`, Spring needs `PaymentService` first. This is called **dependency resolution**.

**Step 8: Spring Injects Dependencies**
Spring injects the already-created `PaymentService` into `OrderService`.

**Step 9: Our Application Uses the Bean**
The fully created and wired object is now ready to use via `getBean()`.

### What if a dependency is missing? *(added — was cut off)*
Suppose `OrderService` needs `PaymentService`, but Spring does not have any `PaymentService` bean. Then Spring cannot create `OrderService`. We may get an error like:
```
No qualifying bean of type 'PaymentService' available
```

---

## 13. Default Bean Naming *(added)*

By default, Spring creates the bean name from the class name — using the class name with the **first letter lowercased**.

```java
@Component
public class PaymentService { }
// default bean name -> "paymentService"
```

We can give a custom name to a component:

```java
@Component("myPaymentService")
public class PaymentService { }
// bean name is now -> "myPaymentService"
```

---

## 14. Handling Multiple Beans of the Same Type

### @Primary
`@Primary` is used when one implementation should be the default choice. If multiple `PaymentService` beans are available, prefer this one by default.

```java
@Primary
@Component
public class CreditCardPayment implements PaymentService { }
```

### @Qualifier
`@Qualifier` is used when we want to **explicitly choose** a specific bean.

```java
@Component
public class OrderService {
    @Autowired
    @Qualifier("upiPayment")
    private PaymentService paymentService;
}
```

- `@Qualifier` can also be used with field injection.
- `@Qualifier` can also be used with setter injection.

### @Primary vs @Qualifier
If both `@Primary` and `@Qualifier` are present, **`@Qualifier` gets priority**.

| Annotation | Role |
|---|---|
| `@Primary` | Gives a default choice |
| `@Qualifier` | Gives a specific choice — specific choice wins over default choice |

### What if there's no @Primary or @Qualifier and multiple matches exist? *(added)*
Spring cannot decide which bean to use, and throws an error like:
```
NoUniqueBeanDefinitionException: expected single matching bean but found 2
```

---

## 15. @Bean — When @Component Isn't Possible

Sometimes we cannot use `@Component` — for example, if a class comes from an external library, we cannot add annotations to its source code. But we may still want Spring to manage its object. In such cases, we use `@Bean`.

Another case: **custom object creation**, where you need some manual logic to build the object.

`@Bean` is used on a method inside a configuration class:

```java
@Configuration
public class AppConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }
}
```

### Simple comparison
| | Behavior |
|---|---|
| `@Component` | Spring finds the class automatically (via component scanning) |
| `@Bean` | We manually tell Spring how to create the object |

---

## 16. Why Do We Need AppConfig?

Why not just create everything in `main()`?

Because `main()` should only **start** the application — not be responsible for creating, wiring, and managing every object in a large project. `AppConfig` keeps that configuration responsibility separate and organized, and lets Spring handle the actual object creation and wiring automatically.
