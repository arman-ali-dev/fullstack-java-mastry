- package tells which package this file belongs to
- import - bring in classes from other packages
- class name must match file name exactly

---

- A package is just a folder for your Java files. It organizes your code and avoids naming conflicts.
- import tells Java where to find classes you are using. Without it, you would have to write the full name every single time.
- java.lang is automatically imported. That is why you can use String, System, Math, Integer without importing them - they are all in java.lang.

---

- public static void main(String[] args) is the entry point. When you run a Java program, JVM looks for this exact signature and starts execution from here.
- public - JVM needs to call it from outside, so it must be public
- static - JVM calls it without creating an object of the class
- void - returns nothing to JVM
- String[] args - command line arguments passed when running the program

---

All 8 primitives

```java
byte   b = 127;          // 1 byte,  range: -128 to 127
short  s = 32000;        // 2 bytes, range: -32,768 to 32,767
int    i = 2147483647;   // 4 bytes, range: -2 billion to 2 billion (most common integer type)
long   l = 9876543210L;  // 8 bytes, range: very large numbers - add L suffix
float  f = 3.14f;        // 4 bytes, ~7 decimal digits precision - add f suffix
double d = 3.14159265;   // 8 bytes, ~15 decimal digits precision (most common decimal type)
char   c = 'A';          // 2 bytes, a single character, uses single quotes
boolean flag = true;     // 1 bit in practice, only true or false
```

**Instance variables (fields in a class) get default values automatically. Local variables inside methods do NOT get defaults - you must initialize them.**

1. int - use this for all integers unless you have a reason not to. Do not overthink byte or short.
2. long - when your number can exceed 2 billion (IDs, file sizes, timestamps). Always add L at the end.
3. double - use this for all decimal numbers. float has less precision and is rarely used in modern Java.
4. char - single character, uses single quotes. Behind the scenes it is a number (Unicode value).
5. boolean - only true or false. No 0 or 1 trick like in C. Java is strict.

---

Casting = converting one type to another.

- Widening (Implicit) - automatic, safe Going from smaller type to larger type. No data is lost. Java does this automatically.
- Narrowing (Explicit) - manual, risky Going from larger type to smaller type. Data might be lost. You must tell Java you know the risk by putting the target type in parentheses.

---

- switch works with: int, byte, short, char, String (Java 7+), enum. switch does NOT work with: long, float, double, boolean.
- Use switch when comparing one variable against 3+ exact values. Use if-else for ranges, conditions, boolean checks.
- enhanced for loop (for-each) Clean way to iterate over arrays and collections. Cannot access index, cannot go backwards, cannot skip elements.

--- 

- An array is a fixed-size container that holds multiple values of the same type. Size cannot change after creation.
- A 2D array is an array of arrays. Think of it as a table with rows and columns.

---

- A method is a block of code that does one specific job. You define it once and call it whenever needed.
- Static methods belong to the class, not to any object. You can call them without creating an object
- Same method name, different parameter list. Java figures out which one to call based on the arguments you pass.
- Overloading is resolved at compile time based on parameter types. The return type alone cannot differentiate overloaded methods.
- Varargs - variable number of arguments
-  ... means "zero or more int arguments"

---

-  Pass by value Java always passes by value. For primitives, a copy of the value is passed. For objects, a copy of the reference is passed - meaning you can modify the object, but you cannot make the variable point to a different object.
-  modifies the actual array (reference was copied, object is same)

---

- Local variables Declared inside a method (or any block). Exist only while that block is executing. Destroyed when the block ends.
- Declared inside a class but outside any method. Belong to each object. Each object has its own copy.
- Declared with static keyword inside a class. Only ONE copy exists, shared by ALL objects of that class.
- Variable shadowing - Local variable with same name as instance variable hides the instance variable. Use this to access instance variable.

---

- String is not a primitive - it is an object. But it is used so often that Java gives it special treatment.
- String s1 = "Hello";           // string literal - stored in String pool
- String s2 = new String("Hello"); // new object in heap - avoid this
- // String pool: Java reuses string literals to save memory
- String is immutable Once created, a String cannot be changed. Every operation that "modifies" a String actually creates a new String.
- Mutable string. Use when you need to build a string step by step, especially in loops.

```ini
StringBuilder   |  StringBuffer
----------------|----------------
Not thread safe |  Thread safe (synchronized methods)
Faster          |  Slower (due to synchronization overhead)
Use in most cases|  Use only in multithreaded code
Java 5+         |  Java 1.0+
```

---

- Every primitive type has a corresponding Wrapper class - an object version of the primitive. They live in java.lang so no import needed.
- Collections like ArrayList cannot hold primitives - they hold objects. So you need Integer instead of int.
- Also, wrapper classes come with useful utility methods.

- Java automatically converts between primitive and wrapper - you do not have to do it manually.
- // Autoboxing - primitive to wrapper (automatic)
- Integer obj = x;  // Java automatically does: Integer obj = Integer.valueOf(x)
- // Unboxing - wrapper to primitive (automatic)
- int primitive = wrapped;  // Java automatically does: int primitive = wrapped.intValue()
- // In collections - autoboxing happens automatically

---

- Generics let you write code that works with any type, while still being type-safe. The type is specified when you use the class/method.
- The <String> tells Java: this list only holds Strings. Anything else is a compile error.
- You write the class with a type parameter <T>. When someone uses your class, they replace T with the actual type.
- ? is the wildcard - represents an unknown type. Used when you want to accept multiple different generic types.

---

- Scanner reads input from keyboard (or files, strings). It is in java.util.
