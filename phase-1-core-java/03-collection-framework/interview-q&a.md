#### 1. What is the Java Collections Framework?

The Java Collections Framework is a set of interfaces and classes that provides ready-made data structures and methods to store, manage, and manipulate groups of objects efficiently.

---

#### 2. Why use it instead of arrays?

We use the Java Collections Framework instead of arrays because collections can grow or shrink dynamically. They also provide built-in methods for adding, removing, searching, and sorting elements, making data management easier.

---

#### 3. Explain the Collection hierarchy.

The Java Collection hierarchy starts with the Iterable interface, which is extended by the Collection interface. The Collection interface is extended by List, Set, and Queue. List is implemented by classes ArrayList, LinkedList, Vector, Stack (extends Vector). Set is implemented by HashSet, LinkedHashSet, and TreeSet. Queue is implemented by PriorityQueue and LinkedList, among others.

---

#### 4. Why does `Map` not extend `Collection`?

`Map` does not extend `Collection` because it stores data in key-value pairs, whereas `Collection` stores individual elements. Map has different operations for adding, removing, and retrieving data using keys.

---

#### 5. What is the difference between `Collection` and `Collections`?

**Collection:** It is an interface in the Java Collections Framework that represents a group of objects.

**Collections:** It is a utility class that provides static methods to perform operations on collections, such as sorting, searching, and reversing elements.

---

#### 6. How does the for-each loop work internally with `Iterable` and `Iterator`?

In Java, the enhanced for-each loop works internally using the `Iterable` and `Iterator` interfaces. The `Iterable` interface provides the `iterator()` method, which returns an `Iterator`. The iterator uses `hasNext()` to check whether more elements are available and `next()` to retrieve each element.

---

#### 7. What is the difference between `Iterator` and `ListIterator`?

Iterator is used to traverse elements in a collection in the forward direction only. It works with collections like ArrayList, HashSet, and others.
<br>
ListIterator is used to traverse elements of a List in both forward and backward directions. It also allows us to add, replace, and remove elements while iterating.

---

#### 8. Why does `ConcurrentModificationException` occur?

ConcurrentModificationException occurs when a collection is structurally modified while we are iterating over it using a fail-fast iterator

---

#### 9. How do you safely remove elements while iterating?

To safely remove elements while iterating, we should use the Iterator.remove() method instead of calling collection.remove() directly.

---

#### 10. What is the difference between fail-fast and fail-safe iterators?

Fail-fast iterators immediately throw a ConcurrentModificationException if a collection is structurally modified during iteration.
<br>
Fail-safe iterators work on a separate copy of the collection or use a mechanism that allows modifications without throwing this exception.

---

#### 10. How does `ArrayList` work internally, and how does it resize?

#### 10. What is the difference between `ArrayList` and `LinkedList`, and when would you use each?

#### 11. Why are `Vector`, `Stack`, and `Hashtable` considered legacy, and what should replace them?

#### 12. What is the difference between `list.remove(int index)` and `list.remove(Object o)`?

#### 13. What does `Arrays.asList()` return, and why does `add()` on it throw `UnsupportedOperationException`?

#### 14. How does `HashSet` ensure uniqueness?

#### 15. What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?

#### 16. How does `HashMap` work internally for `put()` and `get()`?

#### 17. What is a hash collision, and how does `HashMap` handle it (including treeification in Java 8)?

#### 18. What must a custom class implement to be used safely as a `HashMap` key?

#### 19. What is the difference between `HashMap` and `Hashtable`?

#### 20. How does `ConcurrentHashMap` achieve thread safety, and how is it different from `Collections.synchronizedMap()`?

#### 21. What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?

#### 22. How would you implement an LRU cache using `LinkedHashMap`?

#### 23. What is the difference between `Comparable` and `Comparator`?

#### 24. How do you sort a list of objects by multiple fields?

#### 25. How does `PriorityQueue` work, and how do you create a max-heap with it?

#### 26. Why is `ArrayDeque` preferred over `LinkedList` and `Stack` for stack and queue operations?

#### 27. Which collections allow `null` elements or keys, and which do not?

#### 28. What are the time complexities of common operations in `ArrayList`, `LinkedList`, `HashMap`, and `TreeMap`?

#### 29. How do you make a `List` thread-safe? Compare `Collections.synchronizedList()` and `CopyOnWriteArrayList`.

#### 30. Why do we use generics with collections, and what is wrong with raw types?

#### 31. What is the difference between unmodifiable collections (`Collections.unmodifiableList`) and immutable collections (`List.of`)?

#### 32. What do `? extends T` and `? super T` mean in generics, and when do you use each?

#### 33. How does `TreeSet` detect duplicates, and how is that different from `HashSet`?

#### 34. What is the difference between `add`/`remove`/`element` and `offer`/`poll`/`peek` in `Queue`?

#### 35. Why is `HashMap` capacity a power of two, and how do load factor and initial capacity affect performance?

#### 36. When would you use `WeakHashMap`, `IdentityHashMap`, or `EnumMap`?

#### 37. How do `getOrDefault()`, `computeIfAbsent()`, and `merge()` simplify common `Map` operations?

#### 38. What is a `BlockingQueue`, and where is it used (producer-consumer)?

#### 39. How would you remove duplicates from a list while preserving insertion order?
