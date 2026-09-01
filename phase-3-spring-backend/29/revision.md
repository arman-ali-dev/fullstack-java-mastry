# JPA, Hibernate, EntityManager, Transactions, and Dirty Checking - Complete Notes

## 1. JPA and Hibernate's Role

**Jakarta Persistence**, commonly called **JPA**, defines a standard contract for object-relational mapping in Java. It provides interfaces, annotations, and rules such as:
1. `EntityManagerFactory`
2. `EntityManager`
3. `@Entity`
4. `@Id`
5. `@GeneratedValue`

**Hibernate is a JPA provider.** It implements the JPA contract and performs the actual work:
- mapping entity objects to tables,
- generating SQL,
- interacting with JDBC,
- tracking entity state,
- performing dirty checking,
- managing the persistence context,
- coordinating synchronization with the database.

---

## 2. Persistence Context

A **persistence context** is Hibernate's in-memory workspace for managed entities.

```
Persistence Context
┌──────────────────────────────────┐
│ Student#1 → Student object       │
│ Student#2 → Student object       │
│ Student#7 → Student object       │
│                                   │
│ Tracks identity and state        │
└──────────────────────────────────┘
```

Suppose we execute:
```java
Student student = entityManager.find(Student.class, 1L);
```

If the entity is **not already available** in the current persistence context, Hibernate may:
1. Execute a `SELECT`
2. Create a `Student` object from the returned row
3. Place that object in the persistence context
4. Begin managing its state

### What if the entity IS already in the persistence context? *(added — was missing)*
If you call `entityManager.find(Student.class, 1L)` a **second time** in the same persistence context, Hibernate does **not** run another `SELECT` — it simply returns the same already-managed object from its in-memory workspace. This is called the **first-level cache**, and it's automatic/built-in — every persistence context has one.

---

## 3. EntityManager

`EntityManager` is not only a class that runs database queries. It is the **main JPA interface** through which we interact with a persistence context.

### Key EntityManager methods *(added — was missing as a clean reference)*

| Method | Purpose |
|---|---|
| `persist(entity)` | Makes a new (Transient) entity Managed — schedules an INSERT |
| `find(Class, id)` | Retrieves a Managed entity by ID (uses first-level cache if already loaded) |
| `merge(entity)` | Takes a Detached entity and re-attaches its state as Managed |
| `remove(entity)` | Marks a Managed entity as Removed — schedules a DELETE |
| `flush()` | Sends pending changes to the database (without committing) |
| `detach(entity)` | Removes an entity from the persistence context — becomes Detached |

### @PersistenceContext

`@PersistenceContext` is an annotation that tells Spring: *"Please give me an `EntityManager` object automatically — I don't want to create it myself."*

Spring hands you a ready-to-use `EntityManager`, and also manages its lifecycle (open/close) for you.

---

## 4. Transactions and Transaction Boundaries

### Why transactions matter — the classic example *(completed — was cut off in original)*
```java
public void transferMoney(Long fromId, Long toId, double amount) {
    Account from = entityManager.find(Account.class, fromId);
    from.setBalance(from.getBalance() - amount);  // statement 1

    Account to = entityManager.find(Account.class, toId);
    to.setBalance(to.getBalance() + amount);       // statement 2
}
```

If the first statement succeeds and the second fails, **money is deducted from one account but never added to the other** — a serious data-consistency bug.

The region between beginning and completing the transaction is called the **transaction boundary**. A transaction ensures both statements succeed together, or neither does.

### ACID properties *(added — was missing)*
Transactions are built around four guarantees, commonly remembered as **ACID**:

| Letter | Property | Meaning |
|---|---|---|
| A | Atomicity | All operations in the transaction succeed together, or none do |
| C | Consistency | The database moves from one valid state to another |
| I | Isolation | Concurrent transactions don't interfere with each other |
| D | Durability | Once committed, changes survive even a crash |

### @Transactional — how it's actually used *(added — was mentioned but no code shown)*
```java
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void transferMoney(Long fromId, Long toId, double amount) {
        Account from = accountRepository.findById(fromId).get();
        from.setBalance(from.getBalance() - amount);
        accountRepository.save(from);

        Account to = accountRepository.findById(toId).get();
        to.setBalance(to.getBalance() + amount);
        accountRepository.save(to);
        // if anything throws here, BOTH changes are rolled back automatically
    }
}
```

Spring normally applies `@Transactional` through an **AOP proxy** — meaning it works using the exact same proxy mechanism covered in the Spring AOP notes.

### The self-invocation problem applies here too *(added — connects to earlier AOP notes)*
Just like with AOP advice in general, calling a `@Transactional` method via `this.someMethod()` from inside the same class **bypasses the proxy**, so the transaction behavior won't apply. This is one of the most common real-world `@Transactional` bugs.

### What triggers a rollback? *(added — important gotcha, was missing)*
By default, Spring's `@Transactional` only rolls back automatically on **unchecked exceptions** (`RuntimeException` and its subclasses) — not on checked exceptions. If you need a checked exception to also trigger a rollback, you must specify it explicitly:
```java
@Transactional(rollbackFor = Exception.class)
```

### Brief note on propagation and isolation *(added)*
`@Transactional` also supports fine-tuning behavior with attributes like:
- **Propagation** — how this transaction relates to an existing one (e.g. join it, or always start a new one)
- **Isolation** — how strictly this transaction is isolated from other concurrent transactions

These are more advanced settings — the defaults work fine for most everyday applications.

---

## 5. Entity Lifecycle States (Recap)

`@Entity` makes instances of the class eligible for persistence.

An entity instance can move through four main lifecycle states:

1. **Transient**: A new object created with `new`, but not yet connected to the database. Hibernate doesn't know it exists.
2. **Managed**: The object is now being tracked by Hibernate (inside a Session/EntityManager). Any change you make to it gets automatically saved to the database.
3. **Detached**: The object was Managed before, but the session is now closed (or `detach()` was called). It still exists as a Java object, but Hibernate is no longer tracking its changes.
4. **Removed**: The object is marked for deletion. It's still tracked in the current session, but will be deleted from the database once the transaction commits.

### Full lifecycle diagram with the methods that trigger each transition *(added — was missing)*
```
new Student()
     ↓                          
  Transient
     ↓  persist()
  Managed  ←──────┐
     ↓  remove()  │  merge()
  Removed         │
                   │
  Detached ────────┘
     ↑
     └── detach() / close() / commit()
```

---

## 6. Dirty Checking

In Hibernate, **dirty** does not mean invalid or corrupted. An entity is **dirty** when its current managed state differs from the state Hibernate previously knew.

`JPA` does not even define an `EntityManager.update()` method. The object returned by `find()` is **managed**, so Hibernate can automatically detect its changed state and generate the required `UPDATE`.

### How does this actually work internally? *(added — explains the "how" that was missing)*
When Hibernate first loads a managed entity, it internally keeps a snapshot of the original state. When the persistence context is flushed (at commit time, or manually), Hibernate compares the entity's current field values against that original snapshot. If they differ, Hibernate generates and sends an `UPDATE` statement automatically — you never called anything like `.update()` yourself.

### Example *(added — was missing)*
```java
@Transactional
public void updateStudentName(Long id, String newName) {
    Student student = entityManager.find(Student.class, id); // now Managed
    student.setName(newName); // just a plain setter call — no save/update needed

    // when the transaction commits, Hibernate compares old vs new state,
    // detects the change, and automatically runs:
    // UPDATE students SET name = ? WHERE id = ?
}
```

Recall from earlier notes: **flushing** means Hibernate takes whatever changes are pending in memory (in the Persistence Context) and actually sends them to the database as SQL statements (INSERT/UPDATE/DELETE) — but the transaction is not committed yet.
