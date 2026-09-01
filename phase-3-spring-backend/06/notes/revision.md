# Spring Core: Circular Dependency, Bean Scopes, and Bean Lifecycle - Complete Notes

## 1. @Configuration and @Component Relationship

An important point is that `@Configuration` itself internally contains `@Component`.

That means a class annotated with `@Configuration` is also detected by component scanning and registered as a Spring bean.

Spring does not create beans blindly in random order. It creates beans based on their dependencies. Spring tries to create the dependency first, and then the class that depends on it.

---

## 2. Circular Dependency Problem

Now imagine this situation:
- A needs B
- B needs A

What happens:
1. Spring starts creating `A`.
2. But to create `A`, it needs `B`.
3. Then Spring starts creating `B`.
4. But to create `B`, it needs `A`.
5. So the container gets stuck in a loop.

**This is the circular dependency problem.**

**Circular dependency** means two or more classes depend on each other, directly or indirectly.

Circular dependency is not only a Spring-specific issue — it can happen in normal Java as well.

### Circular Dependency with Constructor Injection

Constructor injection has one strict rule: **an object cannot be created until all constructor arguments are available.**

So constructor injection is not the problem — the real problem is the **circular design** itself. With constructor injection, Spring genuinely cannot resolve this, because both objects are waiting for a fully-built version of each other before either can even start being constructed.

### What actually happens if you try this *(added)*
If you use constructor injection with a circular dependency, Spring throws an error at startup:
```
BeanCurrentlyInCreationException: 
Error creating bean with name 'a': Requested bean is currently in creation: 
Is there an unresolvable circular reference?
```

---

## 3. Circular Dependency Using Setter Injection

In setter injection, object creation and dependency injection are **separate steps**. Spring can conceptually do this:

1. Create empty `OrderService` object.
2. Create empty `PaymentService` object.
3. Inject `PaymentService` into `OrderService`.
4. Inject `OrderService` into `PaymentService`.

- Object can be created first.
- Dependency can be injected later.

This works because the object *exists* (even if not fully wired) before the dependency is injected — unlike constructor injection, where the object literally cannot exist without the dependency already in hand.

---

## 4. Circular Dependency Using Field Injection

The same thing can happen with field injection. Here also, Spring can create the object first and inject the dependency later.

**But this does not mean circular dependency is a good practice.**

```java
A a = new A(); // A exists, but dependencies are not injected yet
B b = new B(); // B exists

b.setA(a); // B receives early reference of A
a.setB(b); // A receives B
```

An **early reference** means: Spring exposes a reference of a bean before the bean is fully initialized, so another bean can temporarily use that reference during circular dependency resolution.

### How should you actually fix a circular dependency? *(added)*
Setter/field injection resolving a circular dependency is a **workaround**, not a real fix. The underlying design problem (two classes needing each other) is still there. Better long-term solutions:
- **Redesign**: Extract the shared logic into a third class that both A and B can depend on, breaking the cycle entirely.
- **Use `@Lazy`** on one of the dependencies, so it's resolved only when actually needed instead of at startup (see section 6 below).
- Setter/field injection is a last-resort patch — constructor injection failing on a circular dependency is often a useful signal that your design needs rethinking.

---

## 5. Bean Scopes

**How many objects will Spring create for one bean definition?** Spring provides different scopes for different use cases. The two most important **core** scopes are:
1. **singleton**
2. **prototype**

### Singleton Scope
Spring creates **exactly one object** for a bean definition inside the Spring container, stores it, and returns the same object whenever needed. **Singleton is the default scope in Spring.**

### Prototype Scope
Spring creates a **new object every time** that bean is requested from the container — each request gives a new object.

### Setting a scope explicitly *(added)*
```java
@Component
@Scope("prototype")
public class ReportGenerator {
    // a new instance every time this bean is requested
}
```

### Tricky Question: Singleton Injecting a Prototype

If we inject a prototype bean into a singleton bean, will the singleton get a new prototype object every time?

**No.**

### Why not? *(added — was missing)*
A singleton bean is created **only once**, at container startup. The prototype dependency is injected into it **at that one moment** — so the singleton keeps holding onto that *same* prototype instance for its entire lifetime, even though prototype beans are normally supposed to give a fresh instance on every request. The "new instance every time" behavior of prototype only applies when you fetch it fresh via `getBean()` or through special proxy-based workarounds — plain injection into a singleton doesn't trigger that.

### Web-Aware Scopes

- **Request Scope** — Spring creates one bean object for one HTTP request.
- **Session Scope** — Spring creates one bean object for one user session.
- **Application Scope** — Spring creates one bean object for the entire web application.

### Note on web scopes *(added)*
These three scopes (`request`, `session`, `application`) only make sense in a **web application context** — they require a web-aware `ApplicationContext` (like the one Spring Boot sets up automatically for a web app). A plain `AnnotationConfigApplicationContext` (non-web, console-style app) cannot use these scopes.

---

## 6. Eager vs Lazy Initialization

Spring has to answer one important question: **When should I create this bean?**

- **Eager initialization** — Spring creates the bean as soon as the application context starts.
- **Lazy initialization** — do not create the bean during startup; create it only when someone actually asks for it.

| Scope | Initialization |
|---|---|
| Singleton beans | Eagerly initialized (by default) |
| Prototype beans | Created lazily, when requested |

### @Lazy on a Class

```java
@Lazy
@Component
public class EmailService {
    // not created until it's actually requested
}
```

**`@Lazy` on class** → do not create this bean until it is requested.

### @Lazy on an Injection Point

There is another interesting use of `@Lazy`. Instead of making the whole bean lazy, we can mark the **injection point** as lazy.

```java
@Component
public class UserService {
    @Lazy
    @Autowired
    private EmailService emailService;
}
```

Here, Spring does not inject the real `EmailService` object immediately. Instead, Spring injects a **proxy object**.

- `UserService` does not receive the real `EmailService` immediately.
- `UserService` receives an object that *looks like* `EmailService`.
- When someone actually calls a method on it, Spring creates or fetches the real `EmailService`.

**`@Lazy` on injection point** → inject a proxy, and resolve the real dependency only when its method is used.

### Making the Whole Application Lazy

Spring Boot can make the whole application lazy using this property:
```properties
spring.main.lazy-initialization=true
```

By default: `spring.main.lazy-initialization=false`

---

## 7. Complete Journey of a Spring Bean (Lifecycle)

### Step 1: Bean Definition Is Created
Spring first discovers the bean. At this stage, Spring has not necessarily created the actual object.

The bean definition contains information like:
1. bean name
2. class name
3. scope
4. dependencies
5. lazy/eager behavior
6. lifecycle methods

### Step 2: Object Is Created
Spring creates the actual object using the constructor.

### Step 3: Dependencies Are Injected
After object creation, Spring injects the required dependencies.

### Step 4: Bean Is Initialized
After dependency injection, Spring runs initialization logic. This is the stage where we can perform setup tasks such as:
1. validating configuration
2. opening resources
3. loading required data
4. checking required fields

Initialization can be done using different mechanisms such as:
1. `@PostConstruct`
2. `InitializingBean`
3. custom init method

**Example using `@PostConstruct`** *(added)*:
```java
@Component
public class DatabaseConnector {
    @PostConstruct
    public void init() {
        System.out.println("Connection pool ready");
    }
}
```

### Step 5: Bean Is Ready to Use
After initialization, the bean is ready. Other beans can use it. The application can now call its methods.

### Step 6: Bean Is Destroyed
When the Spring container shuts down, singleton beans are destroyed. This is where cleanup logic can run, such as:
1. closing resources
2. releasing connections
3. stopping background tasks

Destruction can be handled using:
1. `@PreDestroy`
2. `DisposableBean`
3. custom destroy method

**Example using `@PreDestroy`** *(added)*:
```java
@Component
public class DatabaseConnector {
    @PreDestroy
    public void cleanup() {
        System.out.println("Closing connection pool");
    }
}
```

### Important note on prototype bean destruction *(added)*
`@PreDestroy` and destroy callbacks only run for **singleton** beans, because Spring keeps track of and manages singleton beans for their entire lifetime. For **prototype** beans, once Spring hands the object over, it no longer manages that instance — so Spring does **not** call destroy methods on prototype beans. Your code becomes responsible for cleaning those up if needed.
