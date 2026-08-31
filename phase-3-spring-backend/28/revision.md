# Object-Relational Mapping, Hibernate, and JPA - Complete Notes

## 1. Objects vs Relational Tables — The Mismatch

Consider this Java class:
```java
public class Student {
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
```

The equivalent relational representation might be:
```sql
CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    age INT
);
```

The object and row contain similar information, but they are **fundamentally different structures**.

### Writing data — the translation in code
```java
public int save(Student student) {
    String sql = """
        INSERT INTO students(name, email, age)
        VALUES (?, ?, ?)
        """;
    return jdbcTemplate.update(
        sql,
        student.getName(),
        student.getEmail(),
        student.getAge()
    );
}
```

This is much cleaner than raw JDBC, but the developer still performs the translation **manually**.

Spring JDBC manages much of the JDBC infrastructure, but it does not understand the **semantic relationship** between the `Student` class and the `students` table.

### Reading data — the opposite translation *(added — was mentioned but example was missing)*
When reading data, the opposite translation is needed — a `ResultSet` row must be manually converted back into a `Student` object:
```java
public Student findById(Long id) {
    String sql = "SELECT * FROM students WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
        new Student(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getInt("age")
        ), id);
}
```
Every field has to be manually pulled out of the `ResultSet` and placed into the object — this manual back-and-forth translation is exactly the problem ORM tools exist to solve.

---

## 2. Object-Relational Impedance Mismatch

Java ke Objects aur Relational Database ke Tables ka structure alag hota hai, aur in dono ko connect karne mein jo problems/differences aate hain, unhe **Object-Relational Impedance Mismatch** kehte hain.

### The specific kinds of mismatch *(added — commonly cited, was missing entirely)*

| Mismatch Type | The Problem |
|---|---|
| **Granularity mismatch** | An object can be made of smaller objects (e.g. `Address` inside `Student`), but a table is just flat rows/columns |
| **Inheritance mismatch** | Java supports class inheritance naturally; relational tables have no direct concept of "inheritance" |
| **Identity mismatch** | Two Java objects can be "equal" by value (`.equals()`) or by reference (`==`); a database row's identity is its primary key — these two notions of "sameness" don't automatically align |
| **Association mismatch** | Java objects reference each other directly (`student.getOrders()`); relational tables represent relationships using foreign keys and join tables |
| **Navigation mismatch** | In Java, you can freely "walk" from object to object (`student.getOrders().get(0).getItem()`); in SQL, you must explicitly write joins to get the same result |

This table of mismatches is exactly why a translation layer (an ORM) is needed between the two worlds.

---

## 3. What Is ORM

**Object-Relational Mapping**, or **ORM**, is the process of defining how:
- Java classes map to tables
- Java objects map to rows
- Java fields map to columns
- Java references map to foreign-key relationships
- Java collections map to rows

### Responsibilities of an ORM Framework

1. **Mapping metadata** — understands how classes and attributes correspond to tables and columns
2. **SQL generation** — can generate SQL based on entity operations
3. **Parameter binding** — extracts values from the entity and binds them to JDBC parameters
4. **Result-set mapping** — converts database rows back into entity objects
5. **Relationship management** — converts object references and collections into foreign-key and join-table operations
6. **Transaction integration** — coordinates entity operations with database transactions

### ORM is a concept, Hibernate is the implementation

ORM ek **technique/concept** hai jo batata hai ki kaise kaam hona chahiye, aur **Hibernate actual me wo kaam karta hai** — Hibernate is one concrete implementation of the ORM idea.

---

## 4. Hibernate

**Hibernate** is an ORM framework for Java and JVM applications. Hibernate operates **above** JDBC:

```
Application
    ↓
Hibernate
    ↓
JDBC
    ↓
JDBC Driver
    ↓
Database
```

This confirms what was covered earlier: Hibernate doesn't replace JDBC, it sits on top of it — internally, Hibernate still uses JDBC (Connection, PreparedStatement, ResultSet) to actually talk to the database, but it hides all of that from you.

---

## 5. JPA / Jakarta Persistence

**JPA** originally meant **Java Persistence API**. The current standard is named **Jakarta Persistence**.

### It defines contracts such as:
```
jakarta.persistence.Entity
jakarta.persistence.Id
jakarta.persistence.EntityManager
jakarta.persistence.EntityManagerFactory
jakarta.persistence.PersistenceContext
```

But **a specification does not perform the work by itself** — an implementation is required.

### What each contract actually represents *(added — was just listed, not explained)*

| Contract | What it represents |
|---|---|
| `@Entity` | Marks a class as mapped to a database table |
| `@Id` | Marks a field as the primary key |
| `EntityManager` | The main interface for performing operations (find, persist, remove — covered in earlier `@PersistenceContext` notes) |
| `EntityManagerFactory` | Creates `EntityManager` instances, similar to how a connection pool creates connections |
| `@PersistenceContext` | Requests Spring to inject a managed `EntityManager` automatically |

---

## 6. Hibernate Implements JPA

**Hibernate implements the Jakarta Persistence specification** — the interface is defined by Jakarta Persistence, but the runtime object may be implemented by Hibernate.

### Other JPA implementations besides Hibernate *(added — was missing)*
Hibernate is the most popular, but not the only JPA implementation:
- **EclipseLink** — the official reference implementation of Jakarta Persistence
- **OpenJPA** — an Apache project implementing JPA

In almost all Spring Boot projects today, **Hibernate** is the default and most commonly used implementation.

**Spring Boot's JPA starter includes Hibernate, Spring Data JPA, and Spring ORM.**

### What does Spring ORM actually do, separately from Spring Data JPA? *(added — was missing)*
- **Spring ORM** — a smaller, foundational module that integrates JPA/Hibernate with Spring's transaction management and exception translation (e.g. converting Hibernate's exceptions into Spring's consistent `DataAccessException` hierarchy).
- **Spring Data JPA** — built on top of Spring ORM, adds the repository abstraction, query derivation, and everything covered in the earlier Spring Data JPA notes.

So the layering is roughly:
```
Spring Data JPA (repository interfaces, query derivation)
        ↓ built on
Spring ORM (Spring integration: transactions, exception translation)
        ↓ built on
JPA (specification) + Hibernate (implementation)
        ↓ built on
JDBC
```

**Note:** The "JDBC → Spring JDBC → Hibernate evolution" section from the same document (covering boilerplate problems, Spring JDBC's fixes, and Hibernate's fixes) was already covered in detail in an earlier file — see your `jdbc-vs-spring-jdbc-vs-hibernate.md` notes for that part.
