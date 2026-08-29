An advice defines what additional behaviour should run, such as logging, security, auditing or performance tracking.
- A pointcut expression defines where that advice should run.

- execution() is the most commonly used pointcut designator in Spring AOP. It selects methods from their signatures.
- execution() focuses on a method signature. within() focuses on the class or package where the method is declared

- execution() can filter by modifier, return type, method name and parameters.
- within() only restricts the declaring class or package.

---

- @annotation() matches methods that carry a specified annotation. It means:
- Match a method execution when the method has the specified annotation.
- @annotation() is used for method-level annotations. Class-level annotation
- matching is handled by @within() and @target() .
- bean() matches methods using the Spring bean name
- AND: && Both expressions must match.
- OR: || At least one expression must match.
- NOT: ! The negated expression must not match.

---

Long expressions are often reused across multiple advice methods. Repeating them creates duplication and makes maintenance harder.

- within() - Matches methods declared inside types carrying the specified annotation.
