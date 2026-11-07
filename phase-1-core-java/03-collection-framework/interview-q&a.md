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

---

#### 29. How does `HashSet` ensure uniqueness?

`HashSet` uses `hashCode()` and `equals()` methods to ensure uniqueness. First, it checks the hash code to find the bucket, then uses `equals()` to check whether an equal element already exists. If it exists, the duplicate is not added.

---

#### 30. What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?

`HashSet` does not maintain insertion order, `LinkedHashSet` maintains insertion order, and `TreeSet` stores elements in sorted order. `HashSet` and `LinkedHashSet` generally provide faster operations, while `TreeSet` operations are generally slower because it maintains sorted order.

---

#### 31. How does `HashMap` work internally for `put()` and `get()`?

`HashMap` internally uses an array of buckets to store key-value pairs. During `put()`, it calculates the key's hash code to find the bucket and uses `equals()` to check for an existing key. If the key exists, it updates the value; otherwise, it adds a new entry.
<br>
During `get()`, it calculates the hash code, finds the bucket, and uses `equals()` to find the matching key and return its value.

---

#### 32. What is a hash collision, and how does `HashMap` handle it, including treeification in Java 8?

A **hash collision** occurs when two different keys generate the same bucket index in a `HashMap`.
<br>
`HashMap` handles collisions using a linked list.
<br>
In Java 8, when a bucket contains 8 or more entries it converts the linked list into a Red-Black Tree. This process is called treeification.

---

#### 33. What must a custom class implement to be used safely as a `HashMap` key?

A custom class should override both equals() and hashCode() methods to work correctly as a HashMap key.

---

#### 34. What is the difference between `HashMap` and `Hashtable`?

HashMap is not synchronized, while Hashtable is synchronized and thread-safe. HashMap allows one null key and multiple null values, whereas Hashtable does not allow null keys or values.

---

#### 35. What is `ConcurrentHashMap`?

ConcurrentHashMap is a thread-safe implementation of the Map interface. It allows multiple threads to access and modify the map concurrently. It does not allow null keys or values.

---

#### 36. What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?

HashMap does not maintain insertion order, LinkedHashMap maintains insertion order, and TreeMap stores keys in sorted order. HashMap and LinkedHashMap is generally faster, while TreeMap is slower because it maintains sorted order.

---

#### 37. What is the difference between `Comparable` and `Comparator`?

`Comparable` is used to define the natural sorting order of objects by implementing the `compareTo()` method inside the class.
<br>
`Comparator` is used to define custom sorting logic separately from the class by implementing the `compare()` method.

---

#### 38. How do you sort a list of objects by multiple fields?

We can sort a list of objects by multiple fields using the Comparator interface and its thenComparing() method.

---

#### 39. How does `PriorityQueue` work, and how do you create a max-heap with it?

A `PriorityQueue` in Java is a queue that processes elements based on their priority rather than insertion order.
<br>
Internally, it uses a Min-Heap by default, so the smallest element has the highest priority and comes out first.
<br>
To create a Max-Heap, we can use `Comparator.reverseOrder()`, which makes the largest element come out first.

---

#### 40. What is Max-Heap and Min-Heap?

**Min-Heap:** A Min-Heap is a binary heap where the smallest element is always at the root. When we remove an element, the smallest element comes out first.
<br>
**Max-Heap:** A Max-Heap is a binary heap where the largest element is always at the root. When we remove an element, the largest element comes out first.

---

#### 41. What is `ArrayDeque`?

ArrayDeque is implementation of the Deque interface in Java. It allows us to insert and remove elements from both ends, meaning we can use it as both a queue and a stack.

---

#### 42. Which collections allow `null` elements or keys, and which do not?

- **ArrayList:** Allows multiple null elements.
- **LinkedList:** Allows multiple null elements.
- **HashSet:** Allows one null element.
- **LinkedHashSet:** Allows one null element.
- **TreeSet:** Does not allow null elements.
- **HashMap:** Allows one null key and multiple null values.
- **LinkedHashMap:** Allows one null key and multiple null values.
- **TreeMap:** Allows null values but does not allow null keys.
- **Hashtable:** Does not allow null keys or values.
- **ConcurrentHashMap:** Does not allow null keys or values.
- **ArrayDeque:** Does not allow null elements.
- **PriorityQueue:** Does not allow null elements.

---

#### 43. Why do we use generics with collections, and what is wrong with raw types?

We use generics with collections to provide type safety. They allow us to store only a specific type of object. without specifying a generic type. They can store different types of objects, which may cause ClassCastException at runtime.

---

#### 44. What do `? extends T` and `? super T` mean in generics, and when do you use each?

? extends T means the generic type can be T or any subclass of T. We use it when we want to read data from a collection.
<br>
? super T means the generic type can be T or any superclass of T. We use it when we want to add data to a collection.

---

#### 45. How does `TreeSet` detect duplicates, and how is that different from `HashSet`?

TreeSet: It uses compareTo() or Comparator to compare elements. If the comparison returns 0, it considers the elements duplicates and does not add the new element.

HashSet: It uses hashCode() to find the bucket and equals() to check whether an equal element already exists.

---

#### 46. What is the difference between `add`/`remove`/`element` and `offer`/`poll`/`peek` in `Queue`?

In Java Queue, both sets of methods perform similar operations, but they handle failure differently.

- `add()`: Inserts an element. Throws an exception if insertion fails.
- `offer()`: Inserts an element. Returns `false` if insertion fails.
- `remove()`: Removes and returns the head element. Throws an exception if the queue is empty.
- `poll()`: Removes and returns the head element. Returns `null` if the queue is empty.
- `element()`: Returns the head element without removing it. Throws an exception if the queue is empty.
- `peek()`: Returns the head element without removing it. Returns `null` if the queue is empty.

**Main Difference:** The first set of methods throws exceptions on failure, while the second set returns special values like `false` or `null`.

---

#### 47. How would you remove duplicates from a list while preserving insertion order?

We can use `LinkedHashSet` to remove duplicates from a list while preserving insertion order.
`LinkedHashSet` stores only unique elements and maintains the order in which elements were inserted.
We can convert the set back into a list using the `ArrayList` constructor.

---

#### 48. What is the contract between equals() and hashCode()?

The contract between equals() and hashCode() states that if two objects are equal according to the equals() method, they must have the same hash code.
<br>
However, if two objects have the same hash code, they are not necessarily equal.
<br>
Whenever we override equals(), we should also override hashCode() to maintain this contract.

---

#### 49. What happens if we override equals() but not hashCode()?

If we override `equals()` but not `hashCode()`, two objects that are logically equal may have different hash codes.
<br>
Because of this, hash-based collections like `HashMap` and `HashSet` may fail to identify duplicate or equal objects correctly.
<br>
That's why whenever we override `equals()`, we should also override `hashCode()`.

---

#### 50. What are the initial capacity and load factor of a HashMap?

The default initial capacity of a HashMap is 16, which means its internal array can initially hold 16 buckets.
<br>
The default load factor is 0.75.

---

#### 51. What is load factor?

Load factor is the value that decides when a HashMap should increase its capacity.

---

#### 52. When does HashMap resize its internal array?

HashMap resizes its internal array when the number of entries reaches the calculated limit.

`Capacity × Load Factor`

By default, the initial capacity is 16 and the load factor is 0.75, so HashMap resizes when the number of entries reaches 12. Its capacity typically doubles from 16 to 32.

---

#### 53. Can we change load factor?

Yes, we can change the load factor by passing it to the HashMap constructor.

---

#### 54. What is the difference between ArrayDeque and Stack?

Stack is a legacy class that follows the LIFO (Last In, First Out) principle. Its methods are synchronized, which adds overhead.
<bbr>
ArrayDeque is a resizable array-based implementation of the Deque interface. It is generally faster than Stack and is not synchronized.

---

#### 55. How can we implement an LRU cache using LinkedHashMap?

We can implement an LRU (Least Recently Used) cache using `LinkedHashMap` by enabling access order and overriding the `removeEldestEntry()` method.
<br>
When the cache exceeds its capacity, it automatically removes the least recently used entry.

---

#### 56. How can we sort a Map by keys and values?

We can sort a Map by keys or values using TreeMap and Stream API.

#### 57. How does LinkedHashMap maintain insertion order?

`LinkedHashMap` maintains insertion order by using a doubly linked list along with the HashMap structure.
<br>
Each entry is connected to the previous and next entry, which helps maintain the order in which elements were inserted.
