# JDBC and Spring JDBC — Introduction

## 1. Java and MySQL Are Separate Programs

- A Java application normally runs inside the **JVM**.
- **MySQL** runs as a **separate server process**.

These are two completely independent programs running independently — they need a way to talk to each other over a connection (typically over the network, even if both are on the same machine).

### How they actually connect *(added)*
```
Java Application (inside JVM)
        ↓  (uses)
   JDBC API
        ↓  (implemented by)
   JDBC Driver
        ↓  (network connection)
   MySQL Server Process (separate program)
```

---

## 2. What Is JDBC

**JDBC** stands for **Java Database Connectivity**. It is a standard Java API used to communicate with relational databases.

### JDBC API
The JDBC API defines the **contracts** (interfaces) used by Java code — things like `Connection`, `Statement`, `ResultSet`. These are just rules; they don't do anything by themselves.

### JDBC Driver
The JDBC driver provides the **database-specific implementation** of the JDBC contracts — the actual working code that knows how to talk to a specific database.

| Database | Official Driver |
|---|---|
| MySQL | **MySQL Connector/J** |
| PostgreSQL | **PostgreSQL JDBC Driver** |

*(This mirrors the earlier "JDBC vs JDBC Driver" distinction — JDBC = the standard/rulebook, the driver = the actual translator for a specific database.)*

### The JDBC URL *(added — was missing)*
To actually connect, Java needs a **JDBC URL** telling it which database, host, and port to talk to:
```
jdbc:mysql://localhost:3306/mydatabase
jdbc:postgresql://localhost:5432/mydatabase
```
Format: `jdbc:<database-type>://<host>:<port>/<database-name>`

### DriverManager and DataSource *(added — was missing)*
- **`DriverManager`** — the classic, low-level way to get a `Connection`, by passing the JDBC URL, username, and password directly (`DriverManager.getConnection(url, user, pass)`).
- **`DataSource`** — a more modern, production-friendly interface for getting connections. Spring Boot uses a `DataSource` internally, which typically wraps a **connection pool**.

### Connection Pooling *(added — was missing)*
Opening a new database connection for every single request is slow and expensive. Instead, a **connection pool** keeps a set of ready-to-use connections open in advance, and hands them out/returns them as needed. Spring Boot's default connection pool is **HikariCP**, configured automatically as soon as a JDBC driver and `spring-boot-starter-data-jpa` or `spring-boot-starter-jdbc` is on the classpath — you don't have to set it up manually.

---

## 3. Why Spring JDBC Exists

**Spring JDBC reduces the repetitive infrastructure code required by raw JDBC while keeping SQL under the developer's control.**

### What "infrastructure code" means here *(added — ties back to earlier JDBC notes)*
Recall from the earlier JDBC notes: manually opening a `Connection`, creating a `PreparedStatement`, executing it, looping through a `ResultSet`, and closing everything in a `finally` block — every single time. Spring JDBC removes all of that repeated setup/cleanup, while you still write the actual SQL yourself (unlike Hibernate, which can generate SQL for you).

### The Spring JDBC dependency *(added — was missing)*
```xml
spring-boot-starter-jdbc
```
This brings in Spring JDBC support (and a default connection pool like HikariCP) so you can start using it right away.

### JdbcTemplate — Spring JDBC's core class *(added — was missing entirely, the doc ends right before introducing it)*
The main class Spring JDBC provides is `JdbcTemplate`. It's the "template" that handles all the repetitive infrastructure (opening/closing connections, exception handling) so you only provide the SQL and how to map the results:

```java
@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> findAll() {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new Student(rs.getInt("id"), rs.getString("name"))
        );
    }
}
```

Notice: no manual `Connection`, no manual `try-finally` cleanup — `JdbcTemplate` handles that internally, you just supply the SQL and how each row maps to a `Student` object.
