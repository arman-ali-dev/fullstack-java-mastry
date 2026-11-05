#### 1. What is the Java Collections Framework?

The Java Collections Framework is a set of interfaces and classes that provides ready-made data structures and methods to store, manage, and manipulate groups of objects efficiently.

---

#### 2. What are the main interfaces in the Collections Framework?

The main interfaces in the Java Collections Framework are List, Set, Queue, Deque, and Map.

---

#### 3. What is the difference between List, Set, and Queue?

`List` stores ordered elements and allows duplicates. `Set` stores only unique elements and does not allow duplicates. `Queue` is used to process elements, generally in FIFO order.

---

#### 4. Why use it instead of arrays?

We use the Java Collections Framework instead of arrays because collections can grow or shrink dynamically. They also provide built-in methods for adding, removing, searching, and sorting elements, making data management easier.

---

#### 5. What is the difference between a collection and a data structure?

A **collection** is a group of objects, while a **data structure** is a way of organizing and storing data.
For example, `ArrayList` is a collection that uses an array internally to store elements. while arrays, linked lists, stacks, and trees are data structures.

---

#### 6. Can we change the size of an array after creation?

No, we cannot change the size of an array after creation in Java. Its size is fixed. If we need a dynamic size, we can use collections like `ArrayList`.

---

#### 7. Which is faster: an array or an ArrayList?

An **array** is generally faster than an `ArrayList` because it has less overhead. An `ArrayList` provides additional methods and dynamic sizing, which can add some overhead.

---

#### 8. Explain the Collection hierarchy.

The Java Collection hierarchy starts with the Iterable interface, which is extended by the Collection interface. The Collection interface is extended by List, Set, and Queue. List is implemented by classes ArrayList, LinkedList, Vector, Stack (extends Vector). Set is implemented by HashSet, LinkedHashSet, and TreeSet. Queue is implemented by PriorityQueue and LinkedList, among others.

---

#### 9. Why does `Map` not extend `Collection`?

`Map` does not extend `Collection` because it stores data in key-value pairs, whereas `Collection` stores individual elements. Map has different operations for adding, removing, and retrieving data using keys.

---

#### 10. What is the difference between `Collection` and `Collections`?

**Collection:** It is an interface in the Java Collections Framework that represents a group of objects.

**Collections:** It is a utility class that provides static methods to perform operations on collections, such as sorting, searching, and reversing elements.

---

#### 11. How does the for-each loop work internally with `Iterable` and `Iterator`?

In Java, the enhanced for-each loop works internally using the `Iterable` and `Iterator` interfaces. The `Iterable` interface provides the `iterator()` method, which returns an `Iterator`. The iterator uses `hasNext()` to check whether more elements are available and `next()` to retrieve each element.

---

#### 12. What is the difference between Iterable and Iterator?

`Iterable` is an interface that provides the `iterator()` method to get an iterator. `Iterator` is used to traverse elements one by one using methods like `hasNext()` and `next()`.

---

#### 13. Can we use a for-each loop with an array?

Yes, we can use a for-each loop with an array in Java. It allows us to access each element one by one without using an index.

---

#### 14. What is the difference between `Iterator` and `ListIterator`?

Iterator is used to traverse elements in a collection in the forward direction only. It works with collections like ArrayList, HashSet, and others.
<br>
ListIterator is used to traverse elements of a List in both forward and backward directions. It also allows us to add, replace, and remove elements while iterating.

---

#### 15. Why does modifying a collection during iteration cause `ConcurrentModificationException` exception?

Because modifying a collection directly while iterating can change its structure unexpectedly. The iterator detects this modification and throws a `ConcurrentModificationException` to prevent unexpected behavior.

---

#### 16. Why does `ConcurrentModificationException` occur?

ConcurrentModificationException occurs when a collection is structurally modified while we are iterating over it using a fail-fast iterator

---

#### 17. How do you safely remove elements while iterating?

To safely remove elements while iterating, we should use the Iterator.remove() method instead of calling collection.remove() directly.

---

#### 18. What is a fail-fast iterator?

A fail-fast iterator detects structural modifications in a collection during iteration and throws a `ConcurrentModificationException` if the collection is modified directly.

#### 19. What is a fail-safe iterator?

A fail-safe iterator allows modifications and doesn't throw any exception during iteration because it works with a copy of collection.

#### 20. What is the difference between fail-fast and fail-safe iterators?

Fail-fast iterators immediately throw a ConcurrentModificationException if a collection is structurally modified during iteration.
<br>
Fail-safe iterators work on a separate copy of the collection or use a mechanism that allows modifications without throwing this exception.

---

#### 21. Which collections provide fail-fast iterators?

Collections like `ArrayList`, `HashSet`, and `HashMap` provide fail-fast iterators.

---

#### 22. What is CopyOnWriteArrayList?

CopyOnWriteArrayList is a thread-safe implementation of the List interface that creates a new copy of the underlying array whenever we modify the list.

---

#### 23. What is the difference between ArrayList and CopyOnWriteArrayList?

ArrayList is a resizable array implementation of the List interface. It is not thread-safe, so multiple threads accessing it.
<br>
CopyOnWriteArrayList is a thread-safe implementation of the List interface.

---

#### 24. How does `ArrayList` work internally, and how does it resize?

`ArrayList` internally uses a dynamic array to store elements. When the array becomes full, it creates a new array with approximately 1.5 times the old capacity, copies the existing elements into it, and adds the new element. This allows `ArrayList` to grow dynamically.

---

#### 25. What is the difference between `ArrayList` and `LinkedList`, and when would you use each?

`ArrayList` uses a dynamic array internally, while `LinkedList` uses a doubly linked list. `ArrayList` is faster for accessing elements by index, while `LinkedList` is more suitable for frequent insertions and deletions, especially at the ends.
<br>
I would use `ArrayList` when frequent access is required and `LinkedList` when frequent insertions and deletions are required.

---

#### 26. Why are `Vector`, `Stack`, and `Hashtable` considered legacy, and what should replace them?

`Vector`, `Stack`, and `Hashtable` are considered legacy because they are older classes with synchronized methods, which can cause unnecessary performance overhead.
<br>
We generally use `ArrayList` instead of `Vector`, `ArrayDeque` instead of `Stack`, and `HashMap` instead of `Hashtable` in modern Java applications.

---

#### 27. What is the difference between `list.remove(int index)` and `list.remove(Object o)`?

`list.remove(int index)` removes the element at the specified index, while `list.remove(Object o)` removes the first occurrence of the specified object from the list.

---

#### 28. What does `Arrays.asList()` return, and why does `add()` on it throw `UnsupportedOperationException`?

`Arrays.asList()` returns a fixed-size list backed by the original array. We cannot add or remove elements because its size cannot change, so calling `add()` throws `UnsupportedOperationException`.
