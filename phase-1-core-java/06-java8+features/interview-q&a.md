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

Parallel streams allow us to process multiple elements of a stream simultaneously using multiple threads. We should avoid them when working with small datasets

---

#### 15. What is `Optional`?

`Optional` is a container class in Java 8 that may or may not contain a value. It helps us handle null values and avoid `NullPointerException`.

---

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
