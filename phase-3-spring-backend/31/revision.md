Cascade means: if you do an operation (like save/delete) on one entity, that same operation automatically happens on its related entity too — you don't have to do it manually.
- Suppose an Order contains multiple OrderItem objects
- The application does not need to call persist() separately for every OrderItem .

- CascadeType.PERSIST : when you save (persist) the parent entity, the related (child) entity automatically gets saved too — you don't need to call persist() on it separately.
- CascadeType.REMOVE : when you delete the parent entity, the related (child) entity automatically gets deleted too — you don't need to delete it separately.

---

FetchType.LAZY : Related data is NOT loaded immediately. It only loads when you actually access it (call the getter).
- Example:
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

FetchType.EAGER : Related data IS loaded immediately, along with the main entity

```java
User user = entityManager.find(User.class, 1); // User AND Orders both loaded together, right away
```
