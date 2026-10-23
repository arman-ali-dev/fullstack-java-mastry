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

#### 6. What is inheritance, and why doesn't Java support multiple inheritance with classes?
Inheritance is an OOP concept in which one class acquires the properties and methods of another class. It promotes code reusability. In Java, we use the extends keyword to inherit a class.
<br>
Java does not support multiple inheritance with classes because it can create ambiguity when two parent classes have methods with the same signature. This is known as the Diamond Problem. The compiler would need to determine which implementation the child class should inherit.

---

#### 7. In what order do constructors run in an inheritance chain, and what is the role of `super()`? What happens if the parent has no no-arg constructor?
In Java, constructors execute from the parent class to the child class. First, the parent constructor runs, then the child constructor runs.
<br>
The super() keyword is used to call the parent class constructor. If we don't explicitly write a constructor call, Java implicitly inserts super() as the first statement, provided the parent has an accessible no-argument constructor.
<br>
If the parent class has no accessible no-argument constructor, the child must explicitly call one of the parent's available constructors using super(arguments). Otherwise, the code produces a compile-time error.

---

#### 8. What is the difference between method overloading and method overriding?
Method overloading means defining multiple methods with the same name but different parameter lists in the same class. It is an example of compile-time polymorphism because the compiler determines which method to call based on the arguments.
<br>
Method overriding occurs when a child class provides its own implementation of a method inherited from the parent class, using the same method signature. It supports runtime polymorphism because the method implementation is determined by the actual object at runtime.

---

#### 9. What are the rules for method overriding? Can you override `static`, `private`, or `final` methods?
Method overriding means when a child class provides its own implementation of a method inherited from the parent class. The method must have the same name and parameters, and its return type must be the same or compatible. The child method cannot have more restrictive access than the parent method.
<br>
We cannot override static methods because they are hidden, not overridden. We cannot override private methods because they are not inherited, and we cannot override final methods because they cannot be changed in a child class.

---

#### 10. How does runtime polymorphism work when a parent reference holds a child object?
Runtime polymorphism occurs when a parent class reference holds a child class object, and an overridden method is called. In this case, Java decides which method to execute based on the actual object at runtime, not the reference type. For example, if an Animal reference holds a Dog object and we call the sound() method, Java executes the Dog class's overridden sound() method. This is called runtime polymorphism.

---

#### 11. What is the difference between an abstract class and an interface, and when would you choose each?
An abstract class can have both abstract methods and concrete methods. It can also have instance variables and constructors. An interface is mainly used to define a contract that a class must follow. In an interface, methods are generally abstract by default, but it can also have default and static methods with implementations. A class can extend only one abstract class, but it can implement multiple interfaces. I would choose an abstract class when related classes share common code and state, and I would choose an interface when different classes need to follow the same contract, even if they are unrelated.

---

#### 12. What are `default` and `static` methods in interfaces?
A default method in an interface is a method that has a body and provides a default implementation.
<br>
static method in an interface also has a body, but it belongs to the interface itself and is called using the interface name.

---

#### 13. What happens when two interfaces provide the same default method?

When two interfaces provide the same default method, the class that implements both interfaces must override that method to resolve the conflict. Inside the overridden method, we can choose which interface's implementation to call using InterfaceName.super.methodName(). This helps Java avoid ambiguity about which default method should be executed.

---

#### 14. What are the four access modifiers in Java, and how does `protected` differ from default access?
Java has four access modifiers: public, protected, default, and private. Public members can be accessed from anywhere, private members can be accessed only within the same class, and default members can be accessed only within the same package. Protected members can be accessed within the same package and also from subclasses in different packages.

#### 15. What does the `static` keyword mean, and why can't a static method access instance members directly?

The static keyword means that a variable or method belongs to the class rather than an object. A static method cannot directly access instance members because instance members belong to a particular object.

---

16. How is the `final` keyword used with variables, methods, and classes? Does a `final` reference make the object immutable?
17. What is the difference between `==` and `equals()`?
18. What is the `equals()`/`hashCode()` contract, and what goes wrong in a `HashMap` if you override only one?
19. What are upcasting and downcasting, and how does `instanceof` help avoid `ClassCastException`?
20. What is the difference between composition and inheritance, and why is composition often preferred?
21. What does "program to an interface, not an implementation" mean? How does it relate to dependency injection in Spring?
22. In what order do static blocks, instance blocks, and constructors execute in a parent-child class hierarchy?
23. How do you create an immutable class in Java?
24. Is Java pass-by-value or pass-by-reference?
25. What are the types of inner classes in Java, and how does a static nested class differ from an inner class?
26. What is the difference between an anonymous class and a lambda expression?
27. What is the Liskov Substitution Principle? Give an example of a violation.
28. How would you design a payment or notification module so new types can be added without modifying existing code?
29. What is the difference between `final`, `finally`, and `finalize`?
