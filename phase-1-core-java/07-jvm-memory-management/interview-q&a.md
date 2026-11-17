#### 1. What is memory management in Java?

Memory management in Java is the process of allocating and freeing memory during program execution. Java handles memory management automatically through the JVM.

---

#### 2. What are the JVM memory areas?

The JVM divides memory into five main areas: Heap, Stack, Method Area, PC Register, and Native Method Stack.

---

#### 3. What is a class loader?

A Class Loader is a part of the JVM responsible for loading Java classes into memory at runtime.

---

#### 4. What are the types of class loaders?

Java provides three main built-in class loaders: Bootstrap, Platform, and Application Class Loader. Bootstrap loads core Java classes, Platform loads platform libraries, and Application loads application classes from the classpath or module path.

---

#### 5. What is heap memory?

Heap memory is a memory area in the JVM where objects are stored. It is shared among all threads and managed by the Garbage Collector.

---

#### 6. How does heap memory work?

When an object is created using the new keyword, memory is allocated for it in the heap. References are used to access these objects. When an object is no longer reachable through any reference, it becomes eligible for garbage collection, and the Garbage Collector can reclaim its memory.

---

#### 7. What is the young generation?

Young Generation is a part of heap memory where newly created objects are initially allocated.

---

#### 8. What is the old generation?

Old Generation is a part of heap memory where long-lived objects are stored.

---

#### 9. What is stack memory?

Stack memory is a JVM memory area used to store method call frames, local variables, and method parameters. Each thread has its own stack.

---

#### 10. How does stack memory work?

Whenever a method is called, a new stack frame is created, and it is removed when the method execution is completed.

---

#### 11. What is the difference between stack and heap memory?

Stack memory stores method call frames, local variables, and method parameters, whereas heap memory stores objects and arrays. Each thread has its own stack, while heap memory is shared among all threads. Stack frames are automatically removed when methods finish, whereas heap memory is managed by the Garbage Collector.

---

#### 12. What is Metaspace?

Metaspace is a memory area introduced in Java 8 to store class metadata. It stores information about what a class contains, such as its variables and methods.

---

#### 13. What is the String pool?

String Pool is a special area in the JVM heap where String literals are stored and reused. When two String variables have the same literal value, they can refer to the same String object.

---

#### 14. What is garbage collection?

Garbage Collection is an automatic memory management process in Java. The JVM uses the Garbage Collector to identify objects that are no longer reachable and reclaim their memory.

---

#### 15. How does garbage collection work?

Garbage Collection is the process in which Garbage Collector identify objects that are no longer reachable and reclaim their memory.

---

#### 16. What is a memory leak in Java?

A memory leak in Java occurs when objects are no longer needed by the application but their references are still maintained.

---

#### 17. What is OutOfMemoryError?

OutOfMemoryError is an Error in Java that occurs when the JVM cannot allocate the required memory for an operation. It can happen when heap memory, Metaspace, or other required memory resources are exhausted.

---

#### 18. What is StackOverflowError?

StackOverflowError is an error that occurs when a thread's stack memory becomes full. It usually happens due to infinite recursion

---

#### 19. Can we force garbage collection?

No, we cannot force garbage collection in Java. However, we can request the JVM to perform garbage collection using System.gc()

---

#### 20. What is the `finalize()` method?

finalize() was a method that the Garbage Collector could call before reclaiming an object's memory.

---

#### 21. What is Method Area?

Method Area is a shared memory area of the JVM that stores class-level information, such as class metadata, method information, field information, and the runtime constant pool. It is shared among all threads.

---
