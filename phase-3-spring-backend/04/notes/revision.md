# Spring Core: Dependency Injection and IoC - Complete Notes

## 1. What Spring Core Actually Does

Spring Core is not mainly about building web applications. That part comes later with **Spring MVC** and **Spring Boot**.

Spring helps us **create objects, manage dependencies, and connect objects together** in a clean and scalable way.

---

## 2. The Problem: Tight Coupling

Suppose `OrderService` needs to send an email after placing an order. Here, `OrderService` depends on `EmailService`.

```java
public class OrderService {
    private EmailService emailService = new EmailService();

    public void placeOrder() {
        // order logic
        emailService.sendEmail();
    }
}
```

`OrderService` is creating its own dependency. This works, but it creates a design problem.

| Design Type | Result |
|---|---|
| Tightly Coupled Design | Hard to change |
| Loosely Coupled Design | Easier to change |

Now suppose the requirement changes — instead of email, we now need to send an SMS. We must modify `OrderService`.

**The real problem:** A change in notification logic forced us to modify `OrderService`. That means `OrderService` is tightly coupled to a specific notification class.

**Tight coupling** means: one class is directly dependent on a specific concrete class. This makes the code harder to change, test, and reuse.

---

## 3. First Improvement: Use an Interface

To make the design better, we can introduce an interface:

```java
public interface NotificationService {
    void send();
}

public class EmailService implements NotificationService {
    public void send() {
        // send email
    }
}
```

Now `OrderService` can depend on the interface instead of a concrete class. This is better than before because the variable type is now an interface:

```java
private NotificationService notificationService = new EmailService();
```

---

## 4. Interface Alone Is Not Enough

Even though we are using an interface, this line is still a problem:

```java
private NotificationService notificationService = new EmailService();
```

Why? Because the object creation is still concrete: `new EmailService()`. So `OrderService` is still deciding **which** implementation to use.

**Creating objects is not the problem. Creating them in the wrong place is the problem.**

`OrderService` is a business class. Its job should be order-related logic, not deciding which notification object to create.

Currently, `OrderService` is doing two things:
1. Handling order-related logic
2. Creating and deciding the notification service

This breaks **S → Single Responsibility Principle** (from SOLID).

---

## 5. The Solution: Dependency Injection

Instead of creating the dependency inside `OrderService`, we provide the dependency from outside.

Now `OrderService` does not create `EmailService` or `SmsService`. It simply says: *"Give me something that can send a notification."*

```java
public class OrderService {
    private NotificationService notificationService;

    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void placeOrder() {
        notificationService.send();
    }
}
```

This means:
- `OrderService` depends only on `NotificationService` (interface), not `EmailService` (concrete class).
- `OrderService` does not create `EmailService`.
- `OrderService` receives the dependency from outside.

**This is called Dependency Injection.**

A class receives the objects it depends on from outside, instead of creating those objects itself.

- Dependency Injection is not only a Spring concept — it is a design principle that we can use in plain Java also.
- **Spring did not invent Dependency Injection. Spring automates it.**

### DI vs Dependency Inversion Principle (the "D" in SOLID) *(added)*
These sound similar but are different:
- **Dependency Inversion Principle** = the *design rule*: "depend on abstractions (interfaces), not concrete classes."
- **Dependency Injection** = the *technique* used to actually achieve that — by providing the dependency from outside instead of creating it inside.

In short: Dependency Inversion Principle is the *goal*, Dependency Injection is *how* you get there.

---

## 6. Benefits of Dependency Injection

**Benefit 1: Easy to Change Implementation**
If we want to switch from email to SMS, we do not need to change `OrderService`. We only change the object passed from outside.

**Benefit 2: Easier to Test**
For testing, we may not want to send a real email or SMS. So we can create a fake implementation and pass that in instead.

**Benefit 3: More Reusable Code**
The same `OrderService` can work with multiple implementations.

**This makes the design flexible and reusable.**

---

## 7. Types of Dependency Injection

There are mainly three common types of Dependency Injection.

### 1. Constructor Injection
The dependency is provided through the constructor. Usually preferred because it makes required dependencies clear.

```java
public class OrderService {
    private final NotificationService notificationService;

    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

**Why constructor injection is preferred** *(added)*: The dependency can be marked `final`, meaning it must be provided when the object is created and cannot be changed later — this makes the object immutable and always in a valid, fully-initialized state. It also makes required dependencies obvious just by reading the constructor.

### 2. Setter Injection
The dependency is provided through a setter method. Useful when the dependency is optional or can be changed later.

```java
public class OrderService {
    private NotificationService notificationService;

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

### 3. Field Injection
The dependency is injected directly into the field, usually using `@Autowired` in Spring.

```java
public class OrderService {
    @Autowired
    private NotificationService notificationService;
}
```

**Why field injection is generally discouraged** *(added)*: It hides the dependency (you can't tell what an object needs just by looking at its constructor), makes the class harder to test without Spring, and doesn't allow the field to be `final`.

---

## 8. IoC (Inversion of Control)

**IoC stands for Inversion of Control.**

In the earlier code, who was controlling the creation of `EmailService`?

| Before | After |
|---|---|
| The class created what it needed | The class receives what it needs |

**That reversal is called Inversion of Control.**

It is called "inversion" because the normal control flow is reversed. So the control moved from **inside** the class to **outside** the class.

### IoC vs DI

**IoC and DI are closely related, but they are not exactly the same.**

In simple words:
- **IoC** is the idea.
- **Dependency Injection** is one way to implement that idea.

When we give dependencies from outside instead of creating them inside the class, we are using Dependency Injection to achieve Inversion of Control.

---

## 9. Why We Need a Container

In plain Java, we moved object creation outside `OrderService` — but now `Main` is doing all the object creation and wiring:

```java
public class Main {
    public static void main(String[] args) {
        NotificationService notificationService = new EmailService();
        OrderService orderService = new OrderService(notificationService);
        orderService.placeOrder();
    }
}
```

This is fine for small applications. But in large applications, there may be hundreds of classes and dependencies.

If `Main` creates and connects everything manually, it becomes messy.

So we need something that can:
- Create objects
- Manage objects
- Connect objects together

**That is where Spring comes in.**

---

## 10. Spring IoC Container

Spring provides an external system called the **Spring IoC Container**. The Spring IoC Container is responsible for:
- Creating objects
- Managing objects
- Injecting dependencies
- Connecting classes together

In plain Java, `Main` was acting like a small container. In Spring, the **Spring IoC Container** does this work automatically.

### ApplicationContext *(added)*
The most commonly used form of the Spring IoC Container is called `ApplicationContext`. It's the actual object you interact with to get Spring-managed objects:

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
OrderService orderService = context.getBean(OrderService.class);
```

Spring reads your configuration (annotations or XML), figures out what objects to create and how to connect them, and hands you the finished, wired-up object.

---

## 11. Beans

In normal Java, we call them **objects**. In Spring, objects managed by Spring are called **beans**.

- Every Spring bean is an object, but every object is not necessarily a Spring bean.
- But if Spring creates and manages `EmailService`, then it becomes a **Spring bean**.

### How Spring knows what to manage as a bean *(added)*
You mark a class so Spring knows to create and manage it as a bean, commonly using **stereotype annotations**:

| Annotation | Used for |
|---|---|
| `@Component` | A generic Spring-managed bean |
| `@Service` | A class containing business logic (specialized `@Component`) |
| `@Repository` | A class handling database access (specialized `@Component`) |
| `@Controller` / `@RestController` | A class handling web requests (specialized `@Component`) |
| `@Bean` | Used inside a `@Configuration` class to manually define a bean (often for third-party classes you can't annotate directly) |

Example:
```java
@Service
public class OrderService {
    private final NotificationService notificationService;

    @Autowired
    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

Here, Spring sees `@Service`, creates an `OrderService` bean, notices it needs a `NotificationService` in its constructor, finds a matching bean for that, and injects it automatically — this whole process is called **autowiring**.

### Bean Scope *(added)*
By default, Spring creates only **one instance** of a bean and reuses it everywhere it's needed — this default scope is called **singleton**. Spring also supports other scopes like **prototype** (a new instance every time it's requested), but singleton is what you'll use most often.

---

## Final Summary

**Dependency Injection** helps us design classes that do not create their own dependencies. Instead, dependencies are provided from outside.

This makes the code:
- Easier to change
- Easier to test
- Easier to reuse
- More loosely coupled

**IoC** means the control of object creation moves from the class itself to an external system.

**DI** is one way to achieve IoC.

**Spring** takes this idea and automates it using the **Spring IoC Container**.

At the core level, Spring is mainly helping us with **object creation, object management, and object wiring**.

That is the foundation of Spring Core.
