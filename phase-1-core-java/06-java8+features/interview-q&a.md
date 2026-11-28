#### 1. What is a functional interface?

A functional interface in Java is an interface that contains exactly one abstract method. It is mainly used with lambda expressions.

---

#### 2. What is the purpose of `@FunctionalInterface`?

`@FunctionalInterface` is an annotation used to ensure that an interface has exactly one abstract method. If we add another abstract method, the compiler gives an error.

---

#### 3. What are `Predicate`, `Function`, `Consumer`, and `Supplier`, and when do you use each?

These are built-in functional interfaces in Java used with lambda expressions.

1. **Predicate:** Used to check a condition and returns a boolean value.
2. **Function:** Used to take an input and return a result.
3. **Consumer:** Used to take an input and perform an operation without returning anything.
4. **Supplier:** Used to provide a value without taking any input.

---

#### 4. What is a lambda expression?

A lambda expression is a short way to implement the abstract method of a functional interface without creating a separate class.

---

#### 5. Why must local variables used inside a lambda be effectively final?

Local variables used inside a lambda expression must be final or effectively final because lambda expressions capture their values. Java restricts modification of these variables to ensure predictable behavior.

---

#### 6. What are method references, and what are their four types?

Method references are a shorter way to write lambda expressions when we are simply calling an existing method.
<br>
There are four types:

1. **Static method reference:** `ClassName::staticMethod`
2. **Instance method reference of a particular object:** `object::instanceMethod`
3. **Instance method reference of an arbitrary object:** `ClassName::instanceMethod`
4. **Constructor reference:** `ClassName::new`

---

#### 7. Why were default methods introduced in interfaces in Java 8?

Default methods were introduced in Java 8 to allow adding new methods to existing interfaces without breaking the classes that already implement those interfaces.

---

#### 8. What is the Stream API?

The Stream API in Java is used to process collections of data in a clean and efficient way using operations like filtering, mapping, and sorting.

---

#### 9. What is the difference between intermediate and terminal operations?

Intermediate operations return a new Stream and allow us to perform operations like filter(), map(), and sorted().
<br>
Terminal operations produce a final result or perform an action, such as collect(), forEach(), and count(). They also trigger the execution of the Stream pipeline.

---

#### 10. What is lazy evaluation in streams?

Lazy evaluation in Streams means intermediate operations are not executed immediately. They are executed only when a terminal operation is called.

---

#### 11. What is the difference between `map()` and `flatMap()`?

map() is used to transform each element into another value, whereas flatMap() is used to transform each element into a stream and combine all those streams into a single stream.

---

#### 12. What is `reduce()`?

reduce() is a terminal operation used to combine all elements of a stream into a single result, such as calculating the sum,

---

#### 13. What is the difference between `Collectors.groupingBy()` and `Collectors.partitioningBy()`?

**`groupingBy()`** is used to group elements based on a property or condition and returns a Map with multiple groups.

<br>

**`partitioningBy()`** is used to divide elements into exactly two groups based on a condition: `true` and `false`.

---

#### 14. What are parallel streams, and when should you avoid them?

Parallel streams allow us to process multiple elements of a stream simultaneously using multiple threads. We should avoid them when working with small datasets.

---

#### 15. What is `Optional`?

`Optional` is a container class in Java 8 that may or may not contain a value. It helps us handle null values and avoid `NullPointerException`.

---

x

#### 16. What is the difference between `Optional.of()` and `Optional.ofNullable()`?

**`Optional.of()`** is used when the value is not null. If we pass null, it throws a `NullPointerException`.

<br>

**`Optional.ofNullable()`** is used when the value may be null. If we pass null, it returns an empty Optional.

---

#### 17. What is the difference between `orElse()` and `orElseGet()`?

**`orElse()`** returns the default value if the Optional is empty, but it evaluates the default value even if a value is present.

<br>

**`orElseGet()`** returns a value from a Supplier only when the Optional is empty.

---

#### 18. What are records, and when would you use them?

Records are special classes introduced in Java 16, mainly used to store immutable data. They automatically generate constructors, accessors, equals(), hashCode(), and toString() methods. We commonly use them for DTOs

---

#### 19. How do you handle checked exceptions inside a lambda expression?

Checked exceptions inside lambda expressions can be handled using a try-catch block

---

#### 20. What is the `var` keyword, and what are its restrictions?

var is used for local variables. It allows the compiler to automatically determine the variable's data type from its assigned value. It can only be used for local variables, and initialization is mandatory.

---

#### 21. What are sealed classes, and what problem do they solve?

Sealed classes were introduced in Java 17. They allow us to control which classes can extend a class or implement an interface.

---

#### 22. What are the major features added to Java after Java 8 (up to Java 17)?

Java 9 to Java 17 introduced several important features, such as the Module System, var keyword, new String methods, switch expressions, text blocks, records, pattern matching for instanceof, and sealed classes. These features improved code readability, reduced boilerplate code, and provided better control over application design.

---

#### 23. Can a functional interface have default and static methods?

Yes, a functional interface can have multiple default and static methods, but it must have exactly one abstract method. Default and static methods do not affect its functional interface status.

---

#### 24. What is the difference between a lambda expression and an anonymous inner class?

A lambda expression provides a shorter way to implement a functional interface, whereas an anonymous inner class creates an unnamed class to implement an interface or extend a class.

---

#### 25. What is the difference between a method reference and a lambda expression?

A lambda expression is used to provide the implementation of a functional interface, whereas a method reference is a shorter form of a lambda expression when we are simply calling an existing method.

---

#### 26. Can we reuse a Stream after performing a terminal operation?

No, we cannot reuse a Stream after performing a terminal operation because the Stream gets consumed. If we want to process the data again, we need to create a new Stream

---

#### 27. What is the difference between findFirst() and findAny()?

findFirst() returns the first element of a stream whereas findAny() returns any element from the stream.

---

#### 28. What is the difference between filter() and map()?

filter() is used to select elements based on a condition, whereas map() is used to transform each element into another value.

---

#### 29. What is the difference between forEach() and forEachOrdered()?

forEach() performs an action on each element but does not guarantee encounter order in parallel streams. Whereas, forEachOrdered() maintains the encounter order, even when using parallel streams.

---

#### 30. What is the difference between limit() and skip()?

`limit()` is used to get the first specified number of elements from a Stream, whereas `skip()` is used to skip the first specified number of elements from a stream.

---

#### 31. What is the difference between isPresent() and ifPresent() in `Optional`?

`isPresent()` is used to check whether an Optional contains a value and returns a boolean. Whereas, `ifPresent()` is used to perform an action only if a value is present.

---

#### 32. What are private methods in interfaces, introduced in Java 9?

Private methods were introduced in interfaces in Java 9 to avoid code duplication. They allow default and static methods to share common logic. These methods can only be accessed within the interface

---

#### 33. What are factory methods for creating immutable collections, introduced in Java 9?

Factory methods were introduced in Java 9 to create immutable collections easily using methods like List.of(), Set.of(), and Map.of(). These collections cannot be modified after creation.

---

### 34. What are text blocks, introduced in Java 15?

Text blocks were introduced in Java 15 to write multiline strings more easily. They improve code readability by removing the need for repeated quotation marks and escape characters like \n

---

#### 35. What are switch expressions, introduced in Java 14?

Switch expressions were introduced in Java 14. They allow us to use switch as an expression that returns a value. They make code shorter

---

#### 36. What is instanceof?

It allows us to check an object's type.
