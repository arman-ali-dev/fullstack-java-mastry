# Cascade Types and Fetch Types - Complete Notes

## 1. Cascade — Recap and Example

**Cascade** means: if you do an operation (like save/delete) on one entity, that same operation automatically happens on its related entity too — you don't have to do it manually.

Suppose an `Order` contains multiple `OrderItem` objects. The application does not need to call `persist()` separately for every `OrderItem`.

### Code example *(added — was mentioned but no code shown)*
```java
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> items;
}

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String productName;
}
```

```java
Order order = new Order();
OrderItem item1 = new OrderItem();
item1.setOrder(order);
OrderItem item2 = new OrderItem();
item2.setOrder(order);

order.setItems(List.of(item1, item2));

entityManager.persist(order); 
// Because of CascadeType.PERSIST, item1 and item2 are automatically persisted too —
// no need to call entityManager.persist(item1) and persist(item2) separately
```

### CascadeType.PERSIST
When you save (persist) the parent entity, the related (child) entity automatically gets saved too — you don't need to call `persist()` on it separately.

### CascadeType.REMOVE
When you delete the parent entity, the related (child) entity automatically gets deleted too — you don't need to delete it separately.

### Other CascadeType values *(added — only PERSIST and REMOVE were covered)*

| CascadeType | What it propagates |
|---|---|
| `PERSIST` | Saving the parent also saves the child |
| `REMOVE` | Deleting the parent also deletes the child |
| `MERGE` | Merging (re-attaching) the parent also merges the child |
| `REFRESH` | Refreshing the parent's data from the DB also refreshes the child |
| `DETACH` | Detaching the parent from the persistence context also detaches the child |
| `ALL` | Combines all five of the above — commonly used when the child truly "belongs" to the parent (like `OrderItem` belongs to `Order`) |

**Caution (from earlier notes)**: Use cascade — especially `REMOVE`/`ALL` — only when the child truly "belongs" to the parent. If a child entity is shared with other parents too, cascading delete could accidentally wipe out data still needed elsewhere.

---

## 2. FetchType — Recap and Example

### FetchType.LAZY
Related data is **NOT** loaded immediately. It only loads when you actually access it (call the getter).

```java
@Entity
public class User {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders; // Orders load only when you call getOrders()
}
```

```java
User user = entityManager.find(User.class, 1); // only User is loaded, Orders NOT loaded yet
user.getOrders(); // NOW Orders get loaded (extra DB query happens here)
```

### FetchType.EAGER
Related data **IS** loaded immediately, along with the main entity.

```java
User user = entityManager.find(User.class, 1); // User AND Orders both loaded together, right away
```

---

## 3. Default Fetch Types Per Relationship *(added — was missing here)*

Each relationship annotation has its own default fetch behavior:

| Annotation | Default Fetch Type |
|---|---|
| `@OneToMany` | LAZY |
| `@ManyToMany` | LAZY |
| `@ManyToOne` | EAGER |
| `@OneToOne` | EAGER |

**Why the difference?** Collections (`@OneToMany`, `@ManyToMany`) can potentially hold a large number of related rows, so Hibernate defaults to lazy-loading them to avoid pulling in unexpectedly large amounts of data. Single-object relationships (`@ManyToOne`, `@OneToOne`) are cheap to load immediately, so they default to eager.

---

## 4. LazyInitializationException — A Common Real-World Gotcha *(added — entirely missing, very important)*

If a `LAZY` field is accessed **after** the persistence context/session has already closed, Hibernate cannot go fetch the data anymore — since it's no longer connected/managing that entity. This throws:

```
org.hibernate.LazyInitializationException: 
could not initialize proxy - no Session
```

### Example of when this happens
```java
@Transactional
public User getUser(Long id) {
    return entityManager.find(User.class, id); // returns User, persistence context still open here
}

// ... later, outside the transaction/method ...
User user = userService.getUser(1L);
user.getOrders(); // BOOM — LazyInitializationException! Session/transaction already closed
```

### Common fixes *(added)*
- Access the lazy field **while still inside the transaction/service method**, before it returns.
- Use `FetchType.EAGER` for that specific relationship (not always ideal — see below).
- Use a JOIN FETCH query or `@EntityGraph` (covered in earlier Spring Data JPA notes) to explicitly load exactly what's needed, in one query, while still inside the transaction.

---

## 5. Why Not Just Use EAGER Everywhere? *(added)*

It might seem simpler to just make everything `EAGER` to avoid `LazyInitializationException` — but this creates its own problems:
- **Unnecessary data loading**: You load related data even when you never actually need it, wasting memory and time.
- **The N+1 problem** (from earlier Spring Data JPA notes): Eagerly loading collections across many rows can trigger many extra queries.
- **Loss of control**: With LAZY + explicit fetch (via `JOIN FETCH` or `@EntityGraph`) when needed, you decide exactly what gets loaded and when — generally the better long-term practice for real applications.

**Rule of thumb**: Prefer `LAZY` by default, and fetch related data explicitly (via query design) only when you actually need it.
