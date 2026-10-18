## Must Prepare

1. Explain the structure of a Java program. Why is the main method `public static void main(String[] args)`?

A Java program generally contains a package declaration, import statements, a class and the main method.
<br>
The execution of a Java program starts from the main() method.
- The execution of a standalone Java application starts from the main() method.
- Public: It allows the JVM to access the main method from outside the class.
- Static: It allows the JVM to call the main method without creating an object of the class.
- Void: It means the method does not return any value.
- String[] args: It is used to receive command-line arguments as strings.

So, public static void main(String[] args) is the entry point of a java program.

2. What is the difference between JDK, JRE, and JVM?

JDK, JRE, and JVM are three important components of Java.

- JVM (Java Virtual Machine): It executes Java bytecode and makes Java platform-independent.
- JRE (Java Runtime Environment): It provides the JVM and the libraries required to run Java applications.
- JDK (Java Development Kit): It provides the tools required to develop Java applications, including the compiler and the runtime environment.

The relationship is: JDK contains the JRE, and the JRE contains the JVM in the traditional Java architecture.

3. What are the primitive data types in Java, and how do they differ from reference types?

Java has 8 primitive data types: byte, short, int, long, float, double, char, and boolean. Primitive types store simple values directly, such as numbers, characters, and true or false values.
<br>
Reference types refer to objects
<br>

The main difference is that primitive variables hold primitive values, whereas reference variables hold references to objects or arrays. Primitive types have fixed sizes, while size of reference types depends on object. 

4. What are the default values of instance variables, and why don't local variables get default values?

Instance variables get default values automatically when an object is created. For example, numeric types get 0, boolean gets false, and reference types get null.
<br>
Local variables do not get default values because Java ensures we to initialize them before using them. This helps prevent the use of uninitialized variables.

5. Why should you not use `double` or `float` for money calculations, and what should you use instead?

We should not use float or double for money calculations because they can produce small precision errors when representing decimal numbers.
<br>
Instead, we should use BigDecimal because it provides accurate decimal arithmetic, which is important for financial calculations.

6. What is the difference between widening and narrowing type casting? What does `double r = 5 / 2;` print and why?
7. What happens when an `int` overflows in Java?
8. What is the difference between pre-increment (`++x`) and post-increment (`x++`)?
9. What is the difference between `&&` and `&`, and what is short-circuit evaluation?
10. What is the difference between `==` and `equals()` for Strings, and what is the String pool?
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
