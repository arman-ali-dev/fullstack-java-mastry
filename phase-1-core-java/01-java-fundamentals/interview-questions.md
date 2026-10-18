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
12. What is the difference between `String`, `StringBuilder`, and `StringBuffer`?
13. What are wrapper classes, and what are autoboxing and unboxing?
14. Why does `Integer a = 127, b = 127; a == b` return true, but the same with 128 returns false?
15. Is Java pass-by-value or pass-by-reference? Explain with a primitive and an object example.
16. What is the difference between local, instance, and static variables in terms of scope, lifetime, and default values?
17. When can unboxing cause a `NullPointerException`?
18. How many objects are created by `String s = new String("hello");`?
19. Which data types does `switch` support, and how does a switch expression (Java 14+) differ from the classic switch statement?
20. What is the difference between an array and an `ArrayList`?
21. What are varargs, and what are their restrictions?
22. How do you write a generic class or method with a bounded type parameter (`T extends Number`)?
23. What is type erasure in Java generics?
24. What are common practical uses of bitwise operators (e.g., checking odd/even, multiply/divide by 2)?
