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

---
---

---

# JDBC → Spring JDBC → Hibernate: Evolution aur Problems

Java persistence ka evolution samajhna - kis dikkat ki wajah se agla layer aaya.

---

## 1. Plain JDBC ki Dikkatein

JDBC (Java Database Connectivity) sabse basic tarika hai database se baat karne ka. Lekin isme bahut saari **boilerplate code** likhni padti hai baar baar.

### Problem 1: Bahut zyada repetitive code
Har query ke liye manually ye sab karna padta hai:
- Connection banana
- Statement/PreparedStatement banana
- Query execute karna
- ResultSet se data nikalna (row by row)
- Connection, Statement, ResultSet close karna (try-finally me)

```java
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;
try {
    con = DriverManager.getConnection(url, user, pass);
    ps = con.prepareStatement("SELECT * FROM users WHERE id=?");
    ps.setInt(1, id);
    rs = ps.executeQuery();
    while(rs.next()) {
        // manually har column nikalo
    }
} catch(SQLException e) {
    // handle
} finally {
    // con, ps, rs teeno ko manually close karo - agar bhoola to connection leak!
}
```

**Analogy**: Ye aisa hai jaise har baar chai banane ke liye tumhe pehle bartan dhona pade, gas jalana pade, phir chai banake wapas sab kuch dhoke rakhna pade — koi shortcut nahi.

### Problem 2: Exception handling messy hai
JDBC checked exception (`SQLException`) throw karta hai, jo har method me handle/declare karni padti hai — chahe error recoverable ho ya na ho.

### Problem 3: Resource leak ka risk
Agar galti se Connection/Statement close karna bhool gaye (especially exception ke case me), to connection leak ho jata hai — production me bahut bada issue.

### Problem 4: Manual object mapping
ResultSet se data nikal ke manually Java object (POJO) me daalna padta hai — har field ke liye `rs.getString()`, `rs.getInt()` likhna padta hai.

---

## 2. Spring JDBC (JdbcTemplate) ne kya Fix kiya

Spring JDBC ne JDBC ke upar ek **thin wrapper/abstraction layer** banaya.

| Problem | Spring JDBC ka Solution |
|---|---|
| Boilerplate code | `JdbcTemplate` khud connection open/close, statement create, resource cleanup handle karta hai |
| Checked exceptions | `SQLException` ko `DataAccessException` (unchecked) me convert kar deta hai — handle karna optional |
| Manual mapping | `RowMapper` interface se mapping easy ho gayi |

```java
// Spring JDBC ke saath
List<User> users = jdbcTemplate.query(
    "SELECT * FROM users WHERE id=?", 
    new Object[]{id}, 
    new UserRowMapper()
);
```

Dekho kitna chhota ho gaya — connection open/close ka tension khatam.

### Spring JDBC me bhi kuch dikkatein reh gayi:

**Problem 1: SQL abhi bhi manually likhna padta hai**
Har query hardcoded string ke roop me likhni padti hai — koi automatic query generation nahi.

**Problem 2: Object-Relational mapping abhi bhi manual hai**
RowMapper helpful hai, lekin har entity ke liye khud likhna padta hai ki kaunsa column kaunse field me jayega. Complex relationships (jaise ek User ke multiple Orders) me joins, mapping sab manual karna padta hai.

**Problem 3: Database-specific SQL**
Database change karna ho (MySQL se PostgreSQL), to SQL syntax differences ki wajah se code me changes karne pad sakte hain.

**Problem 4: No caching, no automatic dirty checking**
Object fetch karke usme change kiya, to manually UPDATE query likh ke save karna padta hai — Spring JDBC ko pata nahi chalta ki object me kya badla.

---

## 3. Hibernate ne kya Fix kiya

Hibernate ek **full-fledged ORM (Object-Relational Mapping)** tool hai — ye SQL likhne ki zaroorat hi khatam kar deta hai (zyadatar cases me).

| Problem (Spring JDBC) | Hibernate ka Solution |
|---|---|
| Manual SQL likhna | Annotations (`@Entity`, `@Table`, `@Column`) se object-to-table mapping automatic |
| Manual object mapping | Hibernate khud object banata/populate karta hai |
| Database-specific SQL | HQL (Hibernate Query Language) likho, Hibernate khud target database ke hisaab se SQL generate karta hai (via Dialect) |
| No caching | Built-in **first-level aur second-level cache** — performance better |
| Manual dirty checking | Automatically track karta hai object me kya change hua, khud UPDATE query bana deta hai |
| Relationships (joins) manual | `@OneToMany`, `@ManyToOne` jaise annotations se relationships define karo, Hibernate khud joins handle karta hai |

```java
// Hibernate ke saath
User user = session.get(User.class, id);  // SQL likhne ki zaroorat nahi
user.setName("New Name");
session.update(user);  // Hibernate khud dirty checking karke UPDATE query banayega
```

**Analogy**:
- **JDBC** = khud khana banana, bartan dhona, sab kuch manually
- **Spring JDBC** = ek helper jo bartan dhone ka kaam kar deta hai, lekin recipe (SQL) khud likhni hai
- **Hibernate** = ek smart chef jo recipe khud decide karta hai (based on objects), aur zyada efficient tareeke se kaam karta hai (caching, batching)

---

## Quick Summary Table

| Layer | Kya Solve Kiya |
|---|---|
| **JDBC → Spring JDBC** | Boilerplate code, resource leaks, checked exceptions |
| **Spring JDBC → Hibernate** | Manual SQL writing, manual object mapping, no caching, database portability |

### Trade-off
Hibernate powerful hai lekin complex queries me kabhi kabhi fine-grained control kam milta hai jo raw SQL/JdbcTemplate me milta hai — isiliye modern projects (Spring Boot) me **Spring Data JPA** use hota hai jo dono ka best combine karta hai.
