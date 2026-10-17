## Must Prepare

1. What is the Java Collections Framework, and why use it instead of arrays?
2. Explain the Collection hierarchy. Why does `Map` not extend `Collection`?
3. What is the difference between `Collection` and `Collections`?
4. How does the for-each loop work internally with `Iterable` and `Iterator`?
5. What is the difference between `Iterator` and `ListIterator`?
6. Why does `ConcurrentModificationException` occur, and how do you safely remove elements while iterating?
7. What is the difference between fail-fast and fail-safe iterators?
8. How does `ArrayList` work internally, and how does it resize?
9. What is the difference between `ArrayList` and `LinkedList`, and when would you use each?
10. Why are `Vector`, `Stack`, and `Hashtable` considered legacy, and what should replace them?
11. What is the difference between `list.remove(int index)` and `list.remove(Object o)`?
12. What does `Arrays.asList()` return, and why does `add()` on it throw `UnsupportedOperationException`?
13. How does `HashSet` ensure uniqueness?
14. What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?
15. How does `HashMap` work internally for `put()` and `get()`?
16. What is a hash collision, and how does `HashMap` handle it (including treeification in Java 8)?
17. What must a custom class implement to be used safely as a `HashMap` key?
18. What is the difference between `HashMap` and `Hashtable`?
19. How does `ConcurrentHashMap` achieve thread safety, and how is it different from `Collections.synchronizedMap()`?
20. What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?
21. How would you implement an LRU cache using `LinkedHashMap`?
22. What is the difference between `Comparable` and `Comparator`?
23. How do you sort a list of objects by multiple fields?
24. How does `PriorityQueue` work, and how do you create a max-heap with it?
25. Why is `ArrayDeque` preferred over `LinkedList` and `Stack` for stack and queue operations?
26. Which collections allow `null` elements or keys, and which do not?
27. What are the time complexities of common operations in `ArrayList`, `LinkedList`, `HashMap`, and `TreeMap`?
28. How do you make a `List` thread-safe? Compare `Collections.synchronizedList()` and `CopyOnWriteArrayList`.
29. Why do we use generics with collections, and what is wrong with raw types?
30. What is the difference between unmodifiable collections (`Collections.unmodifiableList`) and immutable collections (`List.of`)?
31. What do `? extends T` and `? super T` mean in generics, and when do you use each?
32. How does `TreeSet` detect duplicates, and how is that different from `HashSet`?
33. What is the difference between `add`/`remove`/`element` and `offer`/`poll`/`peek` in `Queue`?
34. Why is `HashMap` capacity a power of two, and how do load factor and initial capacity affect performance?
35. When would you use `WeakHashMap`, `IdentityHashMap`, or `EnumMap`?
36. How do `getOrDefault()`, `computeIfAbsent()`, and `merge()` simplify common `Map` operations?
37. What is a `BlockingQueue`, and where is it used (producer-consumer)?
38. How would you remove duplicates from a list while preserving insertion order?
