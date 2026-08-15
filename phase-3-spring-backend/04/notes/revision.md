- Spring Core is not mainly about building web applications. That part comes later with Spring MVC and Spring Boot.
- Spring helps us create objects, manage dependencies, and connect objects together in a clean and scalable way.


---

- Now suppose OrderService needs to send an email after placing an order. Here, OrderService depends on EmailService .
- OrderService is creating its own dependency. This works, but it creates a design problem.
- The Problem: Tight Coupling
- Tightly Coupled Design → Hard to change
- Loosely Coupled Design → Easier to change

In our previous example, OrderService directly creates EmailService. Now suppose the requirement changes. Instead of email, we now need to send an SMS. Now we must modify OrderService. The problem is not that the notification requirement changed. The real problem is: **A change in notification logic forced us to modify OrderService ** That means OrderService is tightly coupled to a specific notification class.

- Tight coupling means: One class is directly dependent on a specific concrete class.
- This makes the code harder to change, test, and reuse.


--- 

- First Improvement: Use an Interface
- To make the design better, we can introduce an interface
- Now OrderService can depend on the interface instead of a concrete class.
- This is better than before because the variable type is now an interface:

---

But there is still one issue
- Interface Alone Is Not Enough
- Even though we are using an interface, this line is still a problem:
- private NotificationService notificationService = new EmailService();
- Why? Because the object creation is still concrete: new EmailService()
- So OrderService is still deciding which implementation to use.
- Creating objects is not the problem. Creating them in the wrong place is the problem.
- OrderService is a business class. Its job should be order-related logic, not deciding which notification object to create
- Currently, OrderService is doing two things:
1. Handling order-related logic
2. Creating and deciding the notification service
- this breaks S → Single Responsibility Principle

---

The Solution: Dependency Injection

- Instead of creating the dependency inside OrderService , we provide the dependency from outside.
- Now OrderService does not create EmailService or SmsService.
- It simply says: “Give me something that can send a notification.”
- Now the dependency is provided from outside
- This means:
- OrderService depends only on NotificationService (interface) not EmailService (concrete class).
- OrderService does not create EmailService.
- OrderService receives the dependency from outside.

**This is called Dependency Injection.**

- A class receives the objects it depends on from outside, instead of creating those objects itself.
- Dependency Injection is not only a Spring concept.
- It is a design principle that we can use in plain Java also.
- Spring did not invent Dependency Injection. Spring automates it.

---

- Benefit 1: Easy to Change Implementation
- If we want to switch from email to SMS, we do not need to change OrderService. We only change the object passed from outside.

- Benefit 2: Easier to Test
- For testing, we may not want to send a real email or SMS. So we can create a fake implementation.

- Benefit 3: More Reusable Code
- The same OrderService can work with multiple implementations

**This makes the design flexible and reusable.**

---

There are mainly three common types of Dependency Injection.

1. Constructor Injection - In constructor injection, the dependency is provided through the constructor. Constructor injection is usually preferred because it makes required dependencies clear
2. Setter Injection - In setter injection, the dependency is provided through a setter method. Setter injection is useful when the dependency is optional or can be changed later.
3. Field Injection In Spring, we will also discuss about field injection later

---

**IoC stands for Inversion of Control.**
- In the earlier code, who was controlling the creation of EmailService ? 
- Earlier: The class created what it needed
- Now: The class receives what it needs.

**That reversal is called Inversion of Control**

- It is called “inversion” because the normal control flow is reversed.
- So the control moved from inside the class to outside the class

- **IoC and DI are closely related, but they are not exactly the same.**
<br>
- In simple words:
- IoC is the idea.
- Dependency Injection is one way to implement that idea

When we give dependencies from outside instead of creating them inside the class, we are using Dependency Injection to achieve Inversion of Control.


---

In plain Java, we moved object creation outside OrderService. But now Main is doing all the object creation and wiring. This is fine for small applications. But in large applications, there may be hundreds of classes and dependencies. 

- If Main creates and connects everything manually, it becomes messy.
- So we need something that can:
  - Create objects
  - Manage objects
  - Connect objects together

**That is where Spring comes in.**
  
---

Spring provides an external system called the Spring IoC Container. The Spring IoC Container is responsible for:
- Creating objects
- Managing objects
- Injecting dependencies
- Connecting classes together
In plain Java, Main was acting like a small container. In Spring, the Spring IoC Container does this work automatically

---

n normal Java, we call them objects. In Spring, objects managed by Spring are called beans
- Every Spring bean is an object, but every object is not necessarily a Spring bean
- But if Spring creates and manages EmailService , then it becomes a Spring bean

```txt
Final Summary
Dependency Injection helps us design classes that do not create their own
dependencies.

Instead, dependencies are provided from outside.

This makes the code:

Easier to change
Easier to test
Easier to reuse
More loosely coupled

IoC means the control of object creation moves from the class itself to an external
system.

DI is one way to achieve IoC.

Spring takes this idea and automates it using the Spring IoC Container.

At the core level, Spring is mainly helping us with object creation, object
management, and object wiring.

That is the foundation of Spring Core.
```
