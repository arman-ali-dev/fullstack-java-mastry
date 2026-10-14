#### 1. What is Collection in Java?
A Collection in Java is a group of objects treated as a single unit. The Java Collection Framework (JCF) provides a set of interfaces and classes to efficiently store, retrieve, manipulate, and process groups of objects.

- Part of the java.util package.
- Provides reusable data structures and algorithms.

#### 2. What is a Framework in Java?
A Java framework is a pre-built collection of classes and interfaces that provides a ready-made structure to develop applications. It defines a reusable architecture where developers can extend or use existing components instead of writing everything from scratch.
<br>
<br>
Some Popular Java Frameworks:
1. Spring Framework: Used for building enterprise-level applications
2. Hibernate: Used for object-relational mapping between Java objects and databases

#### 3. What is the difference between Array and Collection in Java?
Arrays and Collections are both used to store multiple elements, but they differ in terms of size, flexibility, performance, and functionality. Arrays have a fixed size, whereas Collections are dynamic and provide many utility methods.

#### 4. What are the various interfaces used in Java Collections Framework?
The Java Collection Framework provides several interfaces that define different ways to store and manage data. These interfaces act as the foundation for various collection classes.
<br>
<br>
Main Collection Interfaces

1. Collection: Root interface for all collections.
2. List: Ordered collection that allows duplicate elements.
3. Set: Collection that stores only unique elements.
4. Queue: Stores elements for processing, generally in FIFO order.
5. Deque: Double-ended queue supporting insertion and deletion from both ends.
6. Map: Stores data as key-value pairs (not part of the Collection hierarchy).

#### 5. Explain the hierarchy of the Collection framework in Java.
The Java Collection Framework is a set of classes and interfaces in the java.util package that provides a standardized way to store and manipulate groups of objects. 
<br>
At the top, we have the Iterable interface, which is extended by the Collection interface. The Collection interface has three main interfaces: List, Set, and Queue. List is implemented by classes like ArrayList, LinkedList and vector. Set is implemented by HashSet and SortedSet. Queue has implementations like PriorityQueue, LinkedList and Deque.
