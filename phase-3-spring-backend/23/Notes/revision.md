# Spring AOP - Complete Notes

## 1. What Is Spring AOP

Spring AOP allows us to execute additional logic **before, after, or around** selected Spring bean methods **without mixing that logic into the business code**.

### Core AOP Terminology *(added — terms were used but not formally defined)*

| Term | Meaning |
|---|---|
| **Aspect** | A class containing cross-cutting logic (e.g. logging, security) — marked with `@Aspect` |
| **Advice** | The actual logic that runs at a certain point (before/after/around a method) |
| **Pointcut** | An expression that defines *which* methods the advice applies to |
| **Join Point** | A specific point during execution where advice can be applied (e.g. a method call) |
| **Target** | The actual bean whose method is being intercepted |
| **Weaving** | The process of linking aspects with target objects to create the advised behavior |

- A **target bean** is the ordinary Spring bean whose method we want to intercept.
- **`@Aspect`** declares that the class contains AOP configuration, such as pointcuts and advice.
- **`@Before`** declares that the advice method must execute before every method matched by its pointcut expression.

### Enabling AOP in Spring Boot *(added — was missing)*
To use AOP, you need the `spring-boot-starter-aop` dependency. Spring Boot then auto-configures AOP support automatically — no extra `@EnableAspectJAutoProxy` needed in most Spring Boot setups (it's required in plain Spring, non-Boot projects).

---

## 2. Pointcut Expressions

```java
"execution(String com.coderarmy.studentmanagement.service.StudentService.createStudent())"
```

| Part | Meaning |
|---|---|
| `execution` | Match a method execution |
| `String` | The method must return `String` |
| `StudentService` | The method must belong to this class |
| `createStudent` | The method name must be `createStudent` |
| `()` | The method must take no arguments |

The complete string inside `@Before` is a **pointcut expression**. The matched business method is the **target method**.

### Wildcards for more flexible matching *(added — was missing)*
Writing the exact class + method every time is rigid. Pointcut expressions support wildcards:

```java
// any return type, any method in StudentService, any arguments
"execution(* com.coderarmy.studentmanagement.service.StudentService.*(..))"
```

| Wildcard | Meaning |
|---|---|
| `*` (as return type) | Matches any return type |
| `*` (as method name) | Matches any method name |
| `..` (in arguments) | Matches any number/type of arguments |
| `..` (in package) | Matches the package and all sub-packages |

### Reusable Pointcuts with @Pointcut *(added — was missing)*
Instead of repeating the same expression in every advice method, you can define it once:

```java
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.coderarmy.studentmanagement.service.StudentService.*(..))")
    public void serviceMethods() {} // empty method, just a named pointcut

    @Before("serviceMethods()")
    public void logBeforeMethodExecution() {
        System.out.println("Method is about to execute");
    }
}
```

---

## 3. How AOP Actually Works: Proxies

The `StudentService` class does not explicitly call `LoggingAspect`. Still, the advice executes before the service method. **Spring makes this possible by placing a proxy object in front of the target object.**

Conceptually, the proxy behaves like a wrapper:

```java
public String createStudent() {
    loggingAspect.logBeforeMethodExecution();
    return target.createStudent();
}
```

### Two kinds of proxies Spring uses *(added — was missing)*
Spring AOP creates this proxy using one of two strategies, chosen automatically:

| Proxy type | Used when |
|---|---|
| **JDK Dynamic Proxy** | The target bean implements an interface — proxy is based on that interface |
| **CGLIB Proxy** | The target bean does *not* implement an interface — proxy is a runtime-generated subclass of the target class |

### Important limitation: Self-invocation *(added — a common gotcha, missing entirely)*
Because advice is applied through an external proxy wrapping the bean, **calling a method on `this` from inside the same class bypasses the proxy** — so the advice will NOT run:

```java
@Service
public class StudentService {
    public void createStudent() {
        this.validateStudent(); // called directly on 'this' — proxy is skipped!
    }

    @Before("...") // pointcut targeting validateStudent()
    public void validateStudent() { ... }
}
```
This is a well-known AOP limitation to be aware of — advice only triggers when the method is called *through the proxy*, i.e. from **outside** the class (like from a Controller calling into the Service).

---

## 4. Advice — Additional Logic Around a Method

An **advice** tells Spring additional logic should run before, after, or around a matched method execution.

Spring provides **five principal advice types**:
1. `@Before`
2. `@AfterReturning`
3. `@AfterThrowing`
4. `@After`
5. `@Around`

### Mapping to try/catch/finally

| Position | Advice Type |
|---|---|
| Before entering `try` | `@Before` |
| After a successful return | `@AfterReturning` |
| Inside `catch` | `@AfterThrowing` |
| Inside `finally` | `@After` |
| The entire surrounding block | `@Around` |

### Behavior of Each Type

- **`@Before`** executes before the matched target method begins.
- **`@AfterReturning`** executes only when the matched method completes normally.
- **`@AfterThrowing`** runs when the matched target method exits by throwing an exception.
- **`@After`** does not mean "after successful completion." It behaves like a `finally` block — it executes whether the target method:
  - Returns successfully
  - Throws an exception
- **`@Around`** surrounds the complete invocation.

### Code examples for each advice type *(added — was missing)*

```java
@Aspect
@Component
public class LoggingAspect {

    @Before("serviceMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        System.out.println("Returned: " + result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        System.out.println("Exception: " + ex.getMessage());
    }

    @After("serviceMethods()")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("Method finished (success or failure)");
    }

    @Around("serviceMethods()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // actually runs the target method
        long duration = System.currentTimeMillis() - start;
        System.out.println("Execution took: " + duration + "ms");
        return result;
    }
}
```

### JoinPoint vs ProceedingJoinPoint *(added — was missing)*
- **`JoinPoint`** — used in `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`. Gives you info about the method being called (`getSignature()`, `getArgs()`) but you cannot control execution — it happens automatically around your advice.
- **`ProceedingJoinPoint`** — used only in `@Around`. Extends `JoinPoint`, but adds `proceed()`, which you must explicitly call to actually let the target method run. This is what makes `@Around` the most powerful advice type — you can skip calling `proceed()` entirely (blocking the method), call it multiple times (retry logic), or wrap it in timing/try-catch logic.

---

## 5. Common Real-World Uses of AOP *(added)*

AOP is commonly used for cross-cutting concerns similar to what Filters/Interceptors handle, but at the **method level** rather than the HTTP request level:
- Logging method calls and execution time
- Security checks before sensitive methods
- Transaction management (`@Transactional` is actually implemented using AOP internally)
- Caching (`@Cacheable` also uses AOP under the hood)
- Auditing/tracking changes
