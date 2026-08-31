# Custom Annotations in Java - Complete Notes

## 1. What Is an Annotation

An annotation is only **metadata** attached to a program element — it doesn't do anything by itself. It's just information that some other tool, framework, or reflection code can read and act on.

A custom annotation is declared using `@interface`.

### Basic declaration example *(added — was missing)*
```java
public @interface LogExecutionTime {
}
```

This alone creates a valid, usable annotation — but two important questions need to be answered:
1. **Where** can this annotation be used?
2. **How long** does it remain available (source code, compiled class, or runtime)?

**These rules are defined through meta-annotations** — annotations that describe *other* annotations.

---

## 2. @Target — Where the Annotation Can Be Used

`@Target` defines where an annotation is legally allowed to appear.

### Common ElementType values *(expanded — only 2 of many were listed)*

| ElementType | Can be applied to |
|---|---|
| `ElementType.METHOD` | Methods |
| `ElementType.TYPE` | Classes, interfaces, enums, records, and annotation interfaces |
| `ElementType.FIELD` | Fields/instance variables |
| `ElementType.PARAMETER` | Method parameters |
| `ElementType.CONSTRUCTOR` | Constructors |
| `ElementType.LOCAL_VARIABLE` | Local variables inside a method |
| `ElementType.ANNOTATION_TYPE` | Other annotations (i.e. meta-annotations) |
| `ElementType.PACKAGE` | Package declarations |

### You can allow multiple target types *(added — was missing)*
`@Target` accepts an array, so an annotation can be legal in more than one place:
```java
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Auditable {
}
```

---

## 3. @Retention — How Long the Annotation Is Preserved

`@Retention` defines how long annotation information is preserved.

| RetentionPolicy | Behavior |
|---|---|
| `RetentionPolicy.SOURCE` | The annotation exists only in the source code and is discarded during compilation |
| `RetentionPolicy.CLASS` | The annotation is stored in the compiled `.class` file but is **not** normally available through runtime reflection |
| `RetentionPolicy.RUNTIME` | The annotation remains available while the application is running |

### Why this matters for Spring AOP *(added — connects to earlier AOP notes)*
Remember `@annotation()` pointcut matching from the AOP notes? For Spring to detect your custom annotation **at runtime** (via reflection) and match it in a pointcut expression, the annotation **must** use `RetentionPolicy.RUNTIME`. If you used `SOURCE` or `CLASS` instead, Spring AOP simply wouldn't be able to see the annotation exists while the app is running, and the pointcut would never match.

This is why the standard pattern for a custom AOP annotation is always:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
}
```

---

## 4. @Documented

`@Documented` tells Java documentation tools to include the annotation in generated Javadoc — so anyone reading the generated docs for an annotated class/method can see that the annotation was applied, instead of it being invisible in the documentation.

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
}
```

---

## 5. @Inherited *(added — was missing entirely)*

`@Inherited` is another meta-annotation. It means: if a class is annotated, and another class **extends** it, the subclass is treated as having that annotation too — without needing to add it again.

```java
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Auditable {
}

@Auditable
public class BaseEntity { }

public class Student extends BaseEntity { 
    // Student is also treated as @Auditable, even without the annotation directly on it
}
```

**Important limitation**: `@Inherited` only works for **class-level** annotations (`ElementType.TYPE`) applied through class inheritance — it has no effect on interfaces or on method-level annotations.

---

## 6. Marker Annotation vs Annotation with Properties

### Marker Annotation
A marker annotation has **no properties** — its mere presence is the signal.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
    // no elements — just marks the method
}
```

### Annotation with Properties *(completed — was cut off in the original)*
An annotation can also carry values, called **elements**, declared like abstract methods:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditLog {
    String action();
    int priority() default 1; // default value — optional to specify when using
}
```

**Usage:**
```java
@AuditLog(action = "CREATE_STUDENT")            // priority defaults to 1
public void createStudent() { }

@AuditLog(action = "DELETE_STUDENT", priority = 5)  // overriding the default
public void deleteStudent() { }
```

### Reading these values at runtime *(added)*
Inside an AOP advice (or any reflection-based code), you can read these values back using `JoinPoint`/reflection:
```java
@Before("@annotation(auditLog)")
public void logAction(JoinPoint joinPoint, AuditLog auditLog) {
    System.out.println("Action: " + auditLog.action() + ", Priority: " + auditLog.priority());
}
```
