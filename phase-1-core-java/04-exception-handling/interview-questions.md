## Must Prepare

1. Explain the Java exception hierarchy (`Throwable`, `Error`, `Exception`, `RuntimeException`).
2. What is the difference between `Exception` and `Error`? Should you catch errors like `StackOverflowError` or `OutOfMemoryError`?
3. What is the difference between checked and unchecked exceptions, and why did Java design them this way?
4. How does the `try-catch-finally` flow work? Can you have `try` without `catch`?
5. Does the `finally` block always execute? In which cases does it not?
6. What does a method return if both the `try` block and the `finally` block contain a `return` statement?
7. Why must specific `catch` blocks come before general ones? What is multi-catch (Java 7+)?
8. What is the difference between `throw` and `throws`?
9. How do you create a custom exception, and how do you decide between making it checked or unchecked?
10. What is try-with-resources, how does it work with `AutoCloseable`, and in what order are resources closed?
11. What is exception chaining, and why should you pass the original exception as the cause when rethrowing?
12. How does an exception propagate up the call stack if it is not caught?
13. What are the best practices for exception handling (swallowing exceptions, catching generic `Exception`, logging)?
14. What are the common causes of `NullPointerException`, and how do you prevent it?
15. How do you handle exceptions globally in a Spring Boot REST application (`@ControllerAdvice`, `@ExceptionHandler`)?
16. What are the rules for exceptions when overriding a method (checked vs unchecked in the child method)?
17. What are suppressed exceptions in try-with-resources?
18. Why should exceptions not be used for normal flow control?
19. How would you design exception handling across the repository, service, and controller layers of an application?
20. In Spring, does `@Transactional` roll back on checked exceptions by default? How can you change that?
