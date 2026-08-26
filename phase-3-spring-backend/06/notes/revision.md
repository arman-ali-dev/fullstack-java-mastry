An important point is that @Configuration itself internally contains @Component .
- That means a class annotated with @Configuration is also detected by component scanning and registered as a Spring bean.
- Spring does not create beans blindly in random order. It creates beans based on their dependencies
- Spring tries to create the dependency first, and then the class that depends on it.

---

Now imagine this situation:
- A needs B
- B needs A
- Spring starts creating A .
- But to create A , it needs B .
- Then Spring starts creating B .
- But to create B , it needs A .
- So the container gets stuck in a loop:
- **This is the circular dependency problem.**
- ****Circular dependency means two or more classes depend on each other directly or indirectly.****
- Circular dependency is not only a Spring-specific issue. It can happen in normal Java as well.
- Constructor injection has one strict rule: An object cannot be created until all constructor arguments are available.
- So constructor injection is not the problem. The real problem is the circular design.


---

Circular Dependency Using Setter Injection
- In setter injection, object creation and dependency injection are separate steps. Spring can conceptually do this:
- Step 1: Create empty OrderService object
- Step 2: Create empty PaymentService object
- Step 3: Inject PaymentService into OrderService
- Step 4: Inject OrderService into PaymentService
- Object can be created first.
- Dependency can be injected later.

---

The same thing can happen with field injection.
- Here also, Spring can create the object first and inject the dependency later. But this does not mean circular dependency is a good practice.

```java
A a = new A(); // A exists, but dependencies are not injected yet
B b = new B(); // B exists

b.setA(a); // B receives early reference of A
a.setB(b); // A receives B
```

An early reference means:
- Spring exposes a reference of a bean before the bean is fully initialized, so another bean can temporarily use that reference during circular dependency resolution.

---

How many objects will Spring create for one bean definition?
- Spring provides different scopes for different use cases.
- The two most important core scopes are:
1. singleton
2. prototype

- Singleton Scope - Spring creates exactly one object for a bean definition inside the Spring container, stores it, and returns the same object whenever needed. Singleton is the default scope in Spring.
- Prototype Scope - Spring creates a new object every time that bean is requested from the container. each request gives a new object.











