Jakarta Persistence, commonly called JPA, defines a standard contract for object-relational mapping in Java. It provides interfaces, annotations, and rules such as:
1. EntityManagerFactory
2. EntityManager
3. @Entity
4. @Id
5. @GeneratedValue

Hibernate is a JPA provider. It implements the JPA contract and performs the actual work:
- mapping entity objects to tables,
- generating SQL,
- interacting with JDBC,
- tracking entity state,
- performing dirty checking,
- managing the persistence context,
- coordinating synchronization with the database.

---

A persistence context is Hibernate’s in-memory workspace for managed entities

```java
Persistence Context
┌──────────────────────────────────┐
│ Student#1 → Student object       │
│ Student#2 → Student object       │
│ Student#7 → Student object       │
│                                  │
│ Tracks identity and state        │
└──────────────────────────────────┘
```
Suppose we execute:
```java
 Student student = entityManager.find(Student.class, 1L);
```
If the entity is not already available in the current persistence context, Hibernate may:
1. execute a SELECT ,
2. create a Student object from the returned row,
3. place that object in the persistence context,
4. begin managing its state.

---

EntityManager is not only a class that runs database queries. It is the main JPA interface through which we interact with a persistence context.

- @PersistenceContext is an annotation that tells Spring: "Please give me an EntityManager object automatically — I don't want to create it myself."
- And Spring hands you a ready-to-use EntityManager, and also manages its lifecycle (open/close) for you.

---

If the first statement succeeds and the second fails, money is deducted from one account but never added to the other.
- The region between beginning and completing the transaction is called the transaction boundary.

Spring normally applies @Transactional through an AOP proxy.

---

@Entity makes instances of the class eligible for persistence.
<br>
An entity instance can move through four main lifecycle states:
1. Transient : A new object created with new, but not yet connected to the database. Hibernate doesn't know it exists.
2. Managed : The object is now being tracked by Hibernate (inside a Session/EntityManager). Any change you make to it gets automatically saved to the database.
3. Detached : The object was Managed before, but the session is now closed (or detach() was called). It still exists as a Java object, but Hibernate is no longer tracking its changes.
4. Removed : The object is marked for deletion. It's still tracked in the current session, but will be deleted from the database once the transaction commits.


---

In Hibernate, dirty does not mean invalid or corrupted. An entity is dirty when its current managed state differs from the state Hibernate previously knew.

- JPA does not even define an EntityManager.update() method. The object returned by find() is managed, so Hibernate can automatically detect its changed state and generate the required UPDATE .

- Flushing means Hibernate takes whatever changes are pending in memory (in the Persistence Context) and actually sends them to the database as SQL statements (INSERT/UPDATE/DELETE) — but the transaction is not committed yet.
