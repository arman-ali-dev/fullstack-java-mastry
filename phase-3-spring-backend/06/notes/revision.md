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

- If we inject a prototype bean into a singleton bean, will the singleton get a new prototype object every time? -- No

---

- Request Scope - Spring creates one bean object for one HTTP request.
- Session Scope - Spring creates one bean object for one user session.
- Application Scope - Spring creates one bean object for the entire web application

---

Spring has to answer one important question: When should I create this bean?
- Eager initialization - Spring creates the bean as soon as the application context starts.
- Lazy initialization - Do not create the bean during startup. Create it only when someone actually asks for it.
- Singleton beans are eagerly initialized.
- Prototype beans are created lazily, when requested.

- **There is another interesting use of @Lazy .**
- Instead of making the whole bean lazy, we can mark the injection point as lazy.
- Here, Spring does not inject the real EmailService object immediately. Instead, Spring injects a proxy object.
- UserService does not receive the real EmailService immediately.
- UserService receives an object that looks like EmailService.
- When someone actually calls a method on it,
- Spring creates or fetches the real EmailService.

- @Lazy on class - Do not create this bean until it is requested
- @Lazy on injection point - Inject a proxy and resolve the real dependency only when it's method used

- Spring Boot can make the whole application lazy using this property: spring.main.lazy-initialization=true
- By default: spring.main.lazy-initialization=false

--- 

**Complete Journey of a Spring Bean**

Step 1: Bean Definition Is Created
- Spring first discovers the bean
- At this stage, Spring has not necessarily created the actual object
- The bean definition contains information like:
1. bean name
2. class name
3. scope
4. dependencies
5. lazy/eager behavior
6. lifecycle methods

Step 2: Object Is Created
- Spring creates the actual object using the constructor.

Step 3: Dependencies Are Injected
- After object creation, Spring injects the required dependencies.

Step 4: Bean Is Initialized
- After dependency injection, Spring runs initialization logic
- This is the stage where we can perform setup tasks such as:
1. validating configuration
2. opening resources
3. loading required data
4. checking required fields

Initialization can be done using different mechanisms such as:

1. @PostConstruct
2. InitializingBean
3. custom init method

Step 5: Bean Is Ready to Use
- After initialization, the bean is ready.
- Other beans can use it.
- The application can now call its methods.

Step 6: Bean Is Destroyed
- When the Spring container shuts down, singleton beans are destroyed
- This is where cleanup logic can run, such as:
1. closing resources
2. releasing connections
3. stopping background tasks

Destruction can be handled using:
1. @PreDestroy
2. DisposableBean
3. custom destroy method




