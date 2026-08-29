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

The object and row contain similar information, but they are fundamentally different structures.

```sql
ublic int save(Student student) {
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
This is much cleaner than raw JDBC, but the developer still performs the translation manually.
- Spring JDBC manages much of the JDBC infrastructure, but it does not understand the semantic relationship between the Student class and the students table.

**When reading data, the opposite translation is needed**

---

Java ke Objects aur Relational Database ke Tables ka structure alag hota hai, aur in dono ko connect karne mein jo problems/differences aate hain, unhe Object-Relational Impedance Mismatch kehte hain.


- Object-Relational Mapping, or ORM, is the process of defining how:
  - Java classes map to tables
  - Java objects map to rows
  - Java fields map to columns
  - Java references map to -key relationships
  - Java collections map to rows

Responsibilities of an ORM framework
1. Mapping metadata : It understands how classes and attributes correspond to tables and columns.
2. SQL generation : It can generate SQL based on entity operations.
3. Parameter binding : It extracts values from the entity and binds them to JDBC parameters.
4. Result-set mapping : It converts database rows back into entity objects.
5. Relationship management : It converts object references and collections into foreign-key and join-table operations
6. Transaction integration : It coordinates entity operations with database transactions.

orm ek technique hai ya ek concept hai jo ki btata hai ki kaise kaam hona chahiye aur hibernate actual me wo kaam karta hai 

---

Hibernate is an ORM framework for Java and JVM applications. Hibernate operates above JDBC.
```java
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

JPA originally meant Java Persistence API.
- The current standard is named Jakarta Persistence

It defines contracts such as:

```java
jakarta.persistence.Entity
jakarta.persistence.Id
jakarta.persistence.EntityManager
jakarta.persistence.EntityManagerFactory
jakarta.persistence.PersistenceContext
```

But a specification does not perform the work by itself.
- An implementation is required.

---

Hibernate implements the Jakarta Persistence specification.
- the interface is defined by Jakarta Persistence, but the runtime object may b implemented by Hibernate


**Spring Boot’s JPA starter includes Hibernate, Spring Data JPA and Spring ORM.**
