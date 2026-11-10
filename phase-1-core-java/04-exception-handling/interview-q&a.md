#### 1. What are Exceptions in Java?

An exception is an unwanted event that occurs during program execution and interrupts the normal flow of the program.

---

#### 2. What do you mean by exception handling?

Exception handling in Java is a way to handle runtime problems so that the program does not stop unexpectedly.

#### 3. Explain the Java exception hierarchy.

In Java, Throwable is the root class of the exception hierarchy. It has two main subclasses: Error and Exception. The Exception class is further divided into checked exceptions and unchecked exceptions.

---

#### 4. What is the difference between `Exception` and `Error`?

`Exception` represents problems that an application can handle, while `Error` represents serious problems that are generally beyond the application's control.

For example, `NullPointerException` is an Exception, while `StackOverflowError` is an Error.

---

#### 5. Should you catch errors like `StackOverflowError` or `OutOfMemoryError`?

Generally, no. We should not catch errors like StackOverflowError or OutOfMemoryError because they represent serious problems, and they are beyond the application's control.

---

#### 6. What is the difference between checked and unchecked exceptions?

Checked exceptions are checked at compile time. We must handle them using try-catch or declare them using throws.

Unchecked exceptions are not checked at compile time. They usually occur because of programming mistakes and do not require mandatory handling.

---

#### 7. How does the `try-catch-finally` flow work?

First, the code inside the `try` block executes. If an exception occurs, Java looks for a matching `catch` block to handle it. After that, the `finally` block executes whether an exception occurs or not.

---

#### 8. Can you have `try` without `catch`?

Yes, we can use try without catch if we provide a finally block

---

#### 9. Does the `finally` block always execute? In which cases does it not?

The finally block generally executes whether an exception occurs or not. However, it will not execute if the `System.exit()` is used.

---

#### 10. What does a method return if both the `try` block and the `finally` block contain a `return` statement?

If both the `try` block and the `finally` block contain a `return` statement, the method returns the value from the `finally` block because it overrides the return from the `try` block.

---

#### 11. Why must specific `catch` blocks come before general ones?

If a general catch block comes first, it will catch the exception, making the specific catch block unreachable and causing a compile-time error.

---

#### 12. What is multi-catch (Java 7+)?

Multi-catch allows us to handle multiple types of exceptions in a single catch block using the `|` operator.

#### 13. What is the difference between `throw` and `throws`?

`throw` is used to explicitly throw an exception from a method or block of code.
<br>
`throws` is used in a method declaration to declare that the method may throw one or more exceptions, so the caller can handle them.

---

#### 14. How do you create a custom exception?

We can create a custom exception by extending the Exception class for a checked exception or the RuntimeException class for an unchecked exception.

---

#### 15. What is try-with-resources, how does it work with `AutoCloseable`.

In Java, try-with-resources closes resources automatically after the try block finishes. It works with the `AutoCloseable` interface, which provides the `close()` method. Java calls this method automatically, even if an exception occurs. This helps prevent resource leaks.

---

#### 16. In what order are resources closed?

resources are closed in the reverse order of their declaration.

#### 17. How does an exception propagate up the call stack if it is not caught?

If an exception is not caught in a method, it is passed to the method that called it. If no method catches it, the program terminates abnormally.

---

#### 18. What are the common causes of `NullPointerException`, and how do you prevent it?

A NullPointerException occurs when we try to use an object that has a null value. Common causes are Calling a method on a null object andAccessing a variable of a null object. We can prevent it by checking for null before using an object.

---

#### 19. What is InterruptedException, and how should we handle it properly?

InterruptedException occurs when a thread is interrupted while it is waiting, sleeping, or blocked.
We should handle it using a try-catch block.
