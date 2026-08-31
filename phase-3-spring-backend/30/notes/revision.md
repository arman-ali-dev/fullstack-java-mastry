# JPA Relationship Mapping and Cardinality - Complete Notes

## 1. Object References vs Foreign Keys — Recap

In Java, objects reference each other. In the database, Hibernate turns that into foreign keys/join tables.

A relational database cannot place a Java object inside a column. It represents the same relationship using a **foreign key**.

**Relationship mapping** is the configuration that tells JPA how an object reference should be represented in the database.

---

## 2. Relationship Cardinality

**Cardinality** — how many entities on one side can be associated with how many entities on the other side?

JPA supports four basic cardinalities:
1. **`@OneToOne`** — One entity is associated with one other entity
2. **`@OneToMany`** — One entity is associated with many entities
3. **`@ManyToOne`** — Many entities are associated with one entity
4. **`@ManyToMany`** — Many entities on both sides can be associated

### @OneToOne — Code Example *(added — was just named, no code)*
```java
@Entity
public class Student {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "profile_id")  // FK column lives in the Student table
    private StudentProfile profile;
}

@Entity
public class StudentProfile {
    @Id
    private Long id;
    private String bio;
}
```
The `@JoinColumn` side is the **owning side** — it holds the actual foreign key column (`profile_id`) in its table. A `@OneToOne` FK column is typically also given a `UNIQUE` constraint, since each `Student` maps to exactly one `StudentProfile`.

### @ManyToOne / @OneToMany — Code Example *(added — was just named, no code)*
```java
@Entity
public class Student {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")  // FK column lives in the Student table
    private Department department;
}

@Entity
public class Department {
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department")  // inverse side — no FK here
    private List<Student> students;
}
```

**Convention:** The **"many"** side is almost always the **owning side**, since it's the side that naturally holds the foreign key (each `Student` row has one `department_id` column). The "one" side (`Department`) is the **inverse side**, using `mappedBy`.

### mappedBy — Completing the Explanation *(added — was cut off)*
`mappedBy = "department"` refers to the **name of the Java field** in `Student` that owns this relationship:

```java
@OneToMany(mappedBy = "department")  // "department" = the field name in Student class
private List<Student> students;
```

This tells Hibernate: *"Don't create a separate foreign key for this side — go look at the `department` field inside `Student` to find out how this relationship is actually stored."* This avoids Hibernate creating a confusing extra join table when a simple foreign key on the "many" side already represents the relationship fully.

### @ManyToMany — Code Example *(added — was just named, no code)*
```java
@Entity
public class Student {
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}

@Entity
public class Course {
    @Id
    private Long id;

    @ManyToMany(mappedBy = "courses")  // inverse side
    private List<Student> students;
}
```

Since neither side can hold a single foreign key column (both sides can have many matches), `@ManyToMany` requires a separate **join table** (`student_course`), defined using `@JoinTable`:

| `@JoinTable` attribute | Meaning |
|---|---|
| `name` | Name of the join table itself |
| `joinColumns` | The FK column pointing back to the **owning** entity (Student) |
| `inverseJoinColumns` | The FK column pointing to the **other** entity (Course) |

---

## 3. Unidirectional vs Bidirectional (Recap)

- **Unidirectional Relationship**: Only one entity knows about the other. You can access it from one side only.
- **Bidirectional Relationship**: Both entities know about each other. You can access it from either side.

---

## 4. Owning Side vs Inverse Side (Recap)

- **Owning side**: The entity that actually controls the foreign key in the database. It's responsible for saving/updating the relationship.
- **Inverse side**: It does NOT control the foreign key. It uses `mappedBy` to say "the other side owns this."

### Quick reference — which side owns which relationship *(added)*

| Cardinality | Typical Owning Side | Why |
|---|---|---|
| `@OneToOne` | Side with `@JoinColumn` | Either side *could* own it — whichever has `@JoinColumn` does |
| `@OneToMany` / `@ManyToOne` | The `@ManyToOne` ("many") side | It's the side that naturally holds the FK column |
| `@ManyToMany` | The side with `@JoinTable` (the other uses `mappedBy`) | Neither side can hold a plain FK — one side must be designated to define the join table |
