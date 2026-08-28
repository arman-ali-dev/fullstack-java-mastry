Spring AOP allows us to execute additional logic before, after, or around selected Spring bean methods without mixing that logic into the business code.

- A target bean is the ordinary Spring bean whose method we want to intercept
- @Aspect Declares that the class contains AOP configuration, such as pointcuts and advice.
- @Before Declares that the advice method must execute before every method matched by its pointcut expression.

---

"execution(String com.coderarmy.studentmanagement.service.StudentService.createStudent())"

- execution Match a method execution
- String The method must return String
- StudentService The method must belong to this class
- createStudent The method name must be createStudent
- () The method must take no arguments
- The complete string inside @Before is a pointcut expression.

The matched business method is the target method.

---

The StudentService class does not explicitly call LoggingAspect . Still, the advice executes before the service method. Spring makes this possible by placing a proxy object in front of the target object.
Conceptually, the proxy behaves like a wrapper:

```java
public String createStudent() {
 loggingAspect.logBeforeMethodExecution();
 return target.createStudent();
}
```

An advice tells additional logic should run before, after, or around a matched method execution.

- Spring provides five principal advice types:
1. @Before
2. @AfterReturning
3. @AfterThrowing
4. @After
5. @Around

- Before entering try :  @Before
- After a successful return : @AfterReturning
- Inside catch : @AfterThrowing
- Inside finally : @After
- The entire surrounding block : @Around


- @Before executes before the matched target method begins.
- @AfterReturning executes only when the matched method completes normally.
- @AfterThrowing runs when the matched target method exits by throwing an exception.
-  @After does not mean “after successful completion.” It behaves like a finally block It executes whether the target method:
    - Returns successfully
    - Throws an exception
- @Around surrounds the complete invocation.
