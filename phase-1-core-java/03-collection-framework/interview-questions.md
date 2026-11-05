## Must Prepare

1. What is the Java Collections Framework?
2. What are the main interfaces in the Collections Framework?
3. What is the difference between List, Set, and Queue?
4. What is the difference between a collection and a data structure?
5. Why use it instead of arrays?
6. Can we change the size of an array after creation?
7. Which is faster: an array or an ArrayList?
8. Explain the Collection hierarchy.
9. Why does `Map` not extend `Collection`?
10. What is the difference between `Collection` and `Collections`?
11. How does the for-each loop work internally with `Iterable` and `Iterator`?
12. What is the difference between Iterable and Iterator?
13. Can we use a for-each loop with an array?
14. What is the difference between `Iterator` and `ListIterator`?
15. Why does modifying a collection during iteration cause this exception?
16. Why does `ConcurrentModificationException` occur?
17. how do you safely remove elements while iterating?
18. What is a fail-fast iterator?
19. What is a fail-safe iterator?
20. What is the difference between fail-fast and fail-safe iterators?
21. Which collections provide fail-fast iterators?
22. What is CopyOnWriteArrayList?
23. What is the difference between ArrayList and CopyOnWriteArrayList?
24. How does `ArrayList` work internally, and how does it resize?
25. What is the difference between `ArrayList` and `LinkedList`, and when would you use each?
26. Why are `Vector`, `Stack`, and `Hashtable` considered legacy, and what should replace them?
27. What is the difference between `list.remove(int index)` and `list.remove(Object o)`?
28. What does `Arrays.asList()` return, and why does `add()` on it throw `UnsupportedOperationException`?
29. How does `HashSet` ensure uniqueness?
30. What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?
31. How does `HashMap` work internally for `put()` and `get()`?
32. What is a hash collision, and how does `HashMap` handle it (including treeification in Java 8)?
33. What must a custom class implement to be used safely as a `HashMap` key?
34. What is the difference between `HashMap` and `Hashtable`?
35. How does `ConcurrentHashMap` achieve thread safety, and how is it different from `Collections.synchronizedMap()`?
36. What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?
37. How would you implement an LRU cache using `LinkedHashMap`?
38. What is the difference between `Comparable` and `Comparator`?
39. How do you sort a list of objects by multiple fields?
40. How does `PriorityQueue` work, and how do you create a max-heap with it?
41. Why is `ArrayDeque` preferred over `LinkedList` and `Stack` for stack and queue operations?
42. Which collections allow `null` elements or keys, and which do not?
43. What are the time complexities of common operations in `ArrayList`, `LinkedList`, `HashMap`, and `TreeMap`?
44. How do you make a `List` thread-safe? Compare `Collections.synchronizedList()` and `CopyOnWriteArrayList`.
45. Why do we use generics with collections, and what is wrong with raw types?
46. What is the difference between unmodifiable collections (`Collections.unmodifiableList`) and immutable collections (`List.of`)?
47. What do `? extends T` and `? super T` mean in generics, and when do you use each?
48. How does `TreeSet` detect duplicates, and how is that different from `HashSet`?
49. What is the difference between `add`/`remove`/`element` and `offer`/`poll`/`peek` in `Queue`?
50. Why is `HashMap` capacity a power of two, and how do load factor and initial capacity affect performance?
51. When would you use `WeakHashMap`, `IdentityHashMap`, or `EnumMap`?
52. How do `getOrDefault()`, `computeIfAbsent()`, and `merge()` simplify common `Map` operations?
53. What is a `BlockingQueue`, and where is it used (producer-consumer)?
54. How would you remove duplicates from a list while preserving insertion order?
