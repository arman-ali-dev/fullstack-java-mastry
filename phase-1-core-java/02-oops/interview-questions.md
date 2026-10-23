## Must Prepare

#### 1. What are the four pillars of OOP? Explain each with a real-world example.
1. Encapsulation means wrapping data and methods into a single unit and restricting direct access to data.
2. Inheritance allows one class to acquire the properties and methods of another class.
3. Polymorphism means one thing can take multiple forms. In Java, it is achieved through method overloading and method overriding.
4. Abstraction means hiding implementation details and showing only essential functionality.

---

#### 2. What is the difference between abstraction and encapsulation?
Abstraction focuses on hiding implementation details and showing only essential functionality, whereas encapsulation focuses on wrapping data and methods into a single unit and controlling access to the data.

---

#### 3. What is encapsulation, and how do you achieve it in Java? Why validate data inside setters?
Encapsulation is an OOP principle that combines data and methods into a single unit and restricts direct access to the data.
<br>
In Java, we achieve encapsulation by declaring variables as private and providing public getters and setters to access or modify them.
<br>
We validate data inside setters to prevent invalid values from being assigned to an object and to maintain data integrity.
<br>
For example, in a bank account, we can prevent the balance from being set to a negative value through a setter.

---

#### 4. What is a constructor, and what happens if you don't write one?
A constructor is a special member of a class that is used to initialize an object. It has the same name as the class and does not have a return type.
<br>
If we don't write any constructor in a class, the Java compiler automatically provides a default constructor. It initializes instance variables with their default values, such as 0 for int and null for reference types.
<br>
However, if we define any constructor ourselves, the compiler does not provide the default constructor automatically.

#### 5. What is constructor overloading, and what is `this()` used for?
Constructor overloading means defining multiple constructors in the same class with different parameter lists. They can differ in the number, types, or order of parameters.
<br>
this() is used to call another constructor of the same class. The this() call must be the first statement in a constructor.

---

6. What is inheritance, and why doesn't Java support multiple inheritance with classes?
7. In what order do constructors run in an inheritance chain, and what is the role of `super()`? What happens if the parent has no no-arg constructor?
8. What is the difference between method overloading and method overriding?
9. What are the rules for method overriding? Can you override `static`, `private`, or `final` methods?
10. How does runtime polymorphism work when a parent reference holds a child object?
11. What is the difference between an abstract class and an interface, and when would you choose each?
12. What are `default` and `static` methods in interfaces, and what happens when two interfaces provide the same default method?
13. What are the four access modifiers in Java, and how does `protected` differ from default access?
14. What does the `static` keyword mean, and why can't a static method access instance members directly?
15. How is the `final` keyword used with variables, methods, and classes? Does a `final` reference make the object immutable?
16. What is the difference between `==` and `equals()`?
17. What is the `equals()`/`hashCode()` contract, and what goes wrong in a `HashMap` if you override only one?
18. What are upcasting and downcasting, and how does `instanceof` help avoid `ClassCastException`?
19. What is the difference between composition and inheritance, and why is composition often preferred?
20. What does "program to an interface, not an implementation" mean? How does it relate to dependency injection in Spring?
21. In what order do static blocks, instance blocks, and constructors execute in a parent-child class hierarchy?
22. How do you create an immutable class in Java?
23. Is Java pass-by-value or pass-by-reference?
24. What are the types of inner classes in Java, and how does a static nested class differ from an inner class?
25. What is the difference between an anonymous class and a lambda expression?
26. What is the Liskov Substitution Principle? Give an example of a violation.
27. How would you design a payment or notification module so new types can be added without modifying existing code?
28. What is the difference between `final`, `finally`, and `finalize`?
