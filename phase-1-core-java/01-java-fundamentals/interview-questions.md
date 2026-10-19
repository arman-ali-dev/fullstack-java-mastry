## Must Prepare

#### 1. Explain the structure of a Java program. Why is the main method `public static void main(String[] args)`?

A Java program generally contains a package declaration, import statements, a class and the main method.
<br>
The execution of a Java program starts from the main() method.
- The execution of a standalone Java application starts from the main() method.
- Public: It allows the JVM to access the main method from outside the class.
- Static: It allows the JVM to call the main method without creating an object of the class.
- Void: It means the method does not return any value.
- String[] args: It is used to receive command-line arguments as strings.

So, public static void main(String[] args) is the entry point of a java program.

--- 

#### 2. What is the difference between JDK, JRE, and JVM?

JDK, JRE, and JVM are three important components of Java.

- JVM (Java Virtual Machine): It executes Java bytecode and makes Java platform-independent.
- JRE (Java Runtime Environment): It provides the JVM and the libraries required to run Java applications.
- JDK (Java Development Kit): It provides the tools required to develop Java applications, including the compiler and the runtime environment.

The relationship is: JDK contains the JRE, and the JRE contains the JVM in the traditional Java architecture.

---

#### 3. What are the primitive data types in Java, and how do they differ from reference types?

Java has 8 primitive data types: byte, short, int, long, float, double, char, and boolean. Primitive types store simple values directly, such as numbers, characters, and true or false values.
<br>
Reference types refer to objects
<br>

The main difference is that primitive variables hold primitive values, whereas reference variables hold references to objects or arrays. Primitive types have fixed sizes, while size of reference types depends on object. 

---

#### 4. What are the default values of instance variables, and why don't local variables get default values?

Instance variables get default values automatically when an object is created. For example, numeric types get 0, boolean gets false, and reference types get null.
<br>
Local variables do not get default values because Java ensures we to initialize them before using them. This helps prevent the use of uninitialized variables.

---

#### 5. Why should you not use `double` or `float` for money calculations, and what should you use instead?

We should not use float or double for money calculations because they can produce small precision errors when representing decimal numbers.
<br>
Instead, we should use BigDecimal because it provides accurate decimal arithmetic, which is important for financial calculations.

---

### 6. What is the difference between widening and narrowing type casting?
Widening type casting means converting a smaller data type into a larger data type. It happens automatically in Java when the conversion is safe.
<br>
Narrowing type casting means converting a larger data type into a smaller data type. It usually requires explicit casting because some data may be lost.

---

#### 7. What happens when an `int` overflows in Java?

When an int overflows in Java, its value wraps around because an int has a fixed size of 32 bits.
The maximum value of an int is already defined. If we add 1 to this value, it becomes minimum value of integer.
Java does not automatically throw an exception when an integer overflows.

---

#### 8. What is the difference between pre-increment (`++x`) and post-increment (`x++`)?
Pre-increment (++x) increments the value first and then uses the updated value.
<br>
Post-increment (x++) uses the current value first and then increments it.

---

#### 9. What is the difference between float and double?
Float and double are primitive data types in Java used to store decimal numbers.
- float: Uses 32 bits and provides approximately 6–7 decimal digits of precision.
- double: Uses 64 bits and provides approximately 15–16 decimal digits of precision.

---

#### 10. What is the difference between `==` and `equals()` for Strings, and what is the String pool?
== operator: It compares the references of two String objects to check whether they refer to the same object.
<br>
equals() method: It compares the actual content of two Strings.
<br>
String Pool: The String Pool is a special area of memory where Java reuses String literals to save memory. If the same literal already exists in the pool, Java can reuse that String object.

---

11. Why is `String` immutable in Java, and what are the benefits?
String is immutable in Java, which means its value cannot be changed after the object is created.
<br>
<br>
The main benefits of String immutability are:
1. Security: Strings are used in sensitive operations, such as file paths and database connections. Immutability prevents their values from being changed unexpectedly.
2. String Pool: Immutable Strings can be safely shared and reused from the String Pool.
3. Thread safety: Multiple threads can access the same String object without worrying about its value being modified.

---
  
12. What is the difference between `String`, `StringBuilder`, and `StringBuffer`?
String, StringBuilder, and StringBuffer are used to work with text in Java, but they differ in mutability and thread safety.
- String: Immutable. Once created, its content cannot be changed.
- StringBuilder: Mutable and not thread-safe. It is generally faster and preferred when modifying strings within a single thread.
- StringBuffer: Mutable and thread-safe because its methods are synchronized. It can be useful when the same buffer is shared across multiple threads.

---

13. What are wrapper classes, and what are autoboxing and unboxing?
Wrapper classes are classes that represent primitive data types as objects in Java. Java has eight wrapper classes corresponding to it's primitive types.
<br>
Autoboxing is the automatic conversion of a primitive value into its corresponding wrapper object.
<br>
Unboxing is the automatic conversion of a wrapper object into its corresponding primitive value.

---

14. Why does `Integer a = 127, b = 127; a == b` return true, but the same with 128 returns false?
Java caches Integer objects for values from -128 to 127 by default. Therefore, when we assign 127 to two Integer variables, both variables can refer to the same cached object, so a == b returns true.

For 128, which is outside the default cache range, Java typically creates separate objects, so a == b returns false.

15. Is Java pass-by-value or pass-by-reference? Explain with a primitive and an object example.
Java is always pass-by-value, not pass-by-reference.
<br>
When we pass an object to a method, Java passes a copy of the reference. Both references can point to the same object, so changes to the object's fields can be visible outside the method.

When we pass a primitive variable to a method, Java passes a copy of its value. Changes made inside the method do not affect the original variable.

---

16. What is the difference between local, instance, and static variables in terms of scope, lifetime, and default values?
In Java, local, instance, and static variables differ in scope, lifetime, and default values.
- Local variables are declared inside a method or block. Their scope is limited to that method or block, and they exist while that execution context is active. They do not receive default values; we must initialize them before use.
- Instance variables are declared inside a class but outside methods, without the static keyword. Each object has its own copy. They exist as part of the object and receive default values.
- Static variables are declared using the static keyword. They belong to the class and are shared among its instances. They are initialized when the class is initialized and receive default values.”

---

17. When can unboxing cause a `NullPointerException`?
Unboxing can cause a NullPointerException when we try to convert a null wrapper object into its corresponding primitive type. This happens because a primitive type cannot store null.
<br>
To avoid this, we should check whether the wrapper object is null before unboxing it

---

18. How many objects are created by `String s = new String("hello");`?

String s = new String("hello"); can create one or two String objects, depending on whether the literal "hello" already exists in the String Pool.
<br>
If the literal is not already in the pool, one String object is created in the pool for the literal, and another String object is created on the heap by new String().
<br>
If the literal already exists in the pool, only the new object created by new String() is needed.”

19. What is the difference between an array and an `ArrayList`?
An array has a fixed size, whereas an ArrayList can grow dynamically. An array can store both primitives and objects, while an ArrayList stores objects and uses wrapper classes for primitive values. Arrays use the length property, whereas ArrayList uses the size() method. We can access array elements using indexes, and we can also access ArrayList elements using indexes.

20. What are varargs, and what are their restrictions?
Varargs, or variable-length arguments, allow a method to accept zero or more arguments of the same type. We use three dots (...) to declare varargs in Java.
<br>
<br>
Restrictions:
1. A method can have only one varargs parameter.
2. The varargs parameter must be the last parameter in the method declaration.
3. Varargs are treated as an array inside the method.
4. We can pass zero, one, or multiple arguments to a varargs parameter.

21. How do you write a generic class or method with a bounded type parameter (`T extends Number`)?
22. What is type erasure in Java generics?
23. What are common practical uses of bitwise operators (e.g., checking odd/even, multiply/divide by 2)?
