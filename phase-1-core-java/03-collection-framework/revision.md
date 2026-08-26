Before collections existed in Java, storing multiple values was done like this: String student1 = "Arman"; String student2 = "Priya" Or with arrays

- Arrays have problems. Their size is fixed at creation time. If you need to add more elements than the array size, you are stuck. You have to create a new bigger array, copy everything, then add. This is painful.
- Collection Framework solves all of this. It gives you ready-made data structures that handle resizing, searching, sorting, and much more automatically.

- HIERARCHY

- LinkedList appears under both List and Deque. It implements both interfaces.
- Map is completely separate. It does not extend Collection.

---

- Iterable is the root. It is in java.lang, not java.util. Any class that implements Iterable can be used in a for-each loop.
- It has one method: iterator()
- Collection extends Iterable. It is the root interface for List, Set, and Queue.
- Iterator is an object that lets you traverse a collection one element at a time.
- If you try to remove from a collection while iterating with for-each, you get ConcurrentModificationException. Use Iterator to remove safely.
- Fail-Fast iterators throw ConcurrentModificationException immediately if the collection is modified while iterating (other than through the iterator's own remove() method). They detect modification by tracking a modification count internally.
- Most collections in java.util are fail-fast: ArrayList, HashMap, HashSet, LinkedList, etc.
- Fail-Safe iterators work on a copy of the collection. Modifications to the original do not affect the iterator. They do NOT throw ConcurrentModificationException.
- Collections in java.util.concurrent are fail-safe: CopyOnWriteArrayList, ConcurrentHashMap, etc.
- ListIterator is only for List. It extends Iterator and adds the ability to go backwards and to set or add elements.

---

- List is an ordered collection. Elements stay in insertion order. Duplicates are allowed. Every element has an index starting from 0.
- ArrayList - Internally a resizable array. When it fills up, a new array of 1.5x size is created and all elements are copied.
- Initial capacity is 10 by default. You can set it:
