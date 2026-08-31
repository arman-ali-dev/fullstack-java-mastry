# Spring AOP: Pointcut Designators - Complete Notes

## 1. Advice vs Pointcut — Recap

- An **advice** defines **what** additional behaviour should run, such as logging, security, auditing, or performance tracking.
- A **pointcut expression** defines **where** that advice should run.

---

## 2. execution() — The Most Common Designator

`execution()` is the most commonly used pointcut designator in Spring AOP. It selects methods from their signatures.

- `execution()` focuses on a **method signature**.
- `execution()` can filter by **modifier, return type, method name, and parameters**.

### Example *(added — was missing)*
```java
@Before("execution(public String com.coderarmy.service.StudentService.createStudent(String, int))")
```
This matches only a `public` method named `createStudent`, returning `String`, taking exactly `(String, int)` as parameters, in that exact class.

---

## 3. within() — Class/Package-Level Matching

`within()` focuses on the **class or package** where the method is declared.

- `within()` only restricts the **declaring class or package** — it does not care about method name, return type, or parameters.

### Example *(added — was missing)*
```java
// matches every method in StudentService, regardless of name/return type/params
@Before("within(com.coderarmy.service.StudentService)")

// matches every method in every class inside this package and sub-packages
@Before("within(com.coderarmy.service..*)")
```

### execution() vs within() — Quick Comparison *(added)*

| | execution() | within() |
|---|---|---|
| Can filter by return type | Yes | No |
| Can filter by method name | Yes | No |
| Can filter by parameters | Yes | No |
| Can filter by modifier (public/private) | Yes | No |
| Restricts by class/package | Yes | Yes |
| Best for | Precise, targeted matching | Broad, "apply to everything in this class/package" matching |

---

## 4. @annotation() — Matching Method-Level Annotations

`@annotation()` matches methods that carry a specified annotation. It means: *match a method execution when the method has the specified annotation.*

`@annotation()` is used for **method-level** annotations. Class-level annotation matching is handled by `@within()` and `@target()`.

### Example — using a custom annotation *(added — was missing)*
First, define your own annotation:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
}
```

Apply it to any method you want tracked:
```java
@Service
public class StudentService {
    @LogExecutionTime
    public String createStudent() {
        // ...
    }
}
```

Then match it in your aspect:
```java
@Around("@annotation(com.coderarmy.aop.LogExecutionTime)")
public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    System.out.println("Took: " + (System.currentTimeMillis() - start) + "ms");
    return result;
}
```
This is a very common real-world pattern — you mark exactly the methods you care about with a custom annotation, instead of writing broad class/package-based pointcuts.

---

## 5. @within() vs @target() — Class-Level Annotation Matching *(expanded — was only briefly mentioned)*

Both match based on a **class-level** annotation, but they differ in *how* they check it:

| | @within() | @target() |
|---|---|---|
| Checks | The **declared type** where the method is defined | The **runtime type** of the actual target object |
| Matters when | Method is inherited from an annotated class | Object's actual class carries the annotation, even via inheritance |

In most everyday cases they behave the same way — the difference mainly shows up with inheritance hierarchies, so `@within()` is the more commonly used of the two for simple "this whole class is annotated" scenarios.

---

## 6. bean() — Matching by Bean Name

`bean()` matches methods using the **Spring bean name**.

### Example *(added — was missing)*
```java
// matches all methods on the bean named "studentService"
@Before("bean(studentService)")

// wildcard — matches any bean whose name ends with "Service"
@Before("bean(*Service)")
```

---

## 7. args() — Matching by Argument Types *(added — was missing entirely)*

`args()` matches methods based on the **type of arguments** passed at runtime, regardless of which class or method it is.

```java
// matches any method that takes exactly one String argument
@Before("args(String)")

// combine with execution for more control
@Before("execution(* com.coderarmy.service.*.*(..)) && args(String, int)")
```

---

## 8. Combining Pointcut Expressions with Logical Operators

Long expressions are often reused across multiple advice methods. Repeating them creates duplication and makes maintenance harder — this is part of why `@Pointcut` (covered earlier) is useful for naming and reusing them.

| Operator | Symbol | Meaning |
|---|---|---|
| AND | `&&` | Both expressions must match |
| OR | `\|\|` | At least one expression must match |
| NOT | `!` | The negated expression must not match |

### Example combining designators *(added — was missing)*
```java
@Before("within(com.coderarmy.service..*) && @annotation(LogExecutionTime)")
// matches methods that are BOTH inside the service package AND annotated with @LogExecutionTime

@Before("execution(* com.coderarmy.service.*.*(..)) && !execution(* com.coderarmy.service.*.delete*(..))")
// matches all service methods EXCEPT ones starting with "delete"
```
