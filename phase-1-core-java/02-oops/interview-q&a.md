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

#### 3. What is encapsulation, and how do you achieve it in Java?

Encapsulation is an OOP principle that combines data and methods into a single unit and restricts direct access to the data.
<br>
In Java, we achieve encapsulation by declaring variables as private and providing public getters and setters to access or modify them.

---

#### 4. What is a constructor?

A constructor is a special member of a class that is used to initialize an object. It has the same name as the class and does not have a return type.

---

#### 5. When is a constructor called?

A constructor is called automatically when an object is created using the `new` keyword. It is used to initialize the object's state.

---

#### 6. What are the different types of constructors in Java?

There are two main types of constructors in Java: default constructors and parameterized constructors.
<br>
A default constructor is provided by the compiler when we do not define any constructor in a class. A parameterized constructor accepts parameters to initialize an object with specific values.

---

#### 7. Can a constructor be overloaded?

Yes, a constructor can be overloaded by defining multiple constructors with different parameter lists in the same class.

---

#### 8. Can a constructor be inherited or overridden?

No, constructors cannot be inherited or overridden in Java

---

#### 9. Can a constructor be private?

Yes, a constructor can be private. It is used to restrict object creation from outside the class.

---

#### 10. Can we call one constructor from another constructor?

Yes, we can call one constructor from another constructor using this().

---

#### 11. What is the difference between a constructor and a method?

A constructor is used to initialize an object and has the same name as the class. A method is used to perform a specific task and can have any valid name. A constructor has no return type, whereas a method can have a return type or `void`.

---

#### 12. what is `this` keyword?

In Java, the `this` keyword refers to the current object. It is used to access the current object's instance variables and methods, and to call another constructor of the same class using `this()`.

---

#### 13. Why doesn't Java support multiple inheritance with classes?

Java does not support multiple inheritance with classes to avoid ambiguity, especially the diamond problem.

---

#### 14. What is the diamond problem ?

In Java, the diamond problem with interfaces occurs when a class implements two interfaces that have the same default method. The class must override that method to resolve the ambiguity.

---

#### 15. What is the 'super' keyword?

In Java, the `super` keyword is used to refer to the immediate parent class object. It is used to access parent class variables and methods, and to call the parent class constructor using `super()`.

---

#### 16. What is the difference between method overloading and method overriding?

Method overloading means defining multiple methods with the same name but different parameter lists in the same class. It is also a compile-time polymorphism.
<br>
Method overriding occurs when a child class provides its own implementation of a method inherited from the parent class, using the same method signature. It is also called runtime polymorphism.

---

#### 17. What is the difference between Compile Time Polymorphism and Run Time Polymorphism?

Compile-time polymorphism is resolved by the compiler and is achieved through method overloading. Runtime polymorphism is resolved at runtime and is achieved through method overriding.

#### 18. What are the rules for method overriding?

The method must have the same name and parameters, and its return type must be the same or compatible. The child method cannot have more restrictive access than the parent method.

#### 19. Can you override `static`, `private`, or `final` methods?

We cannot override static methods because they are hidden, not overridden. We cannot override private methods because they are not inherited, and we cannot override final methods final keyword prevents methods to being overridden.

---

#### 20. What is the difference between an abstract class and an interface, and when would you choose each?

An abstract class can have both abstract and concrete methods, instance variables, and constructors, whereas an interface defines a contract that a class must follow and can also have default and static methods.
<br>
I would choose an abstract class when related classes share common state and behavior. I would choose an interface when different classes need to follow the same contract.

---

#### 21. What are `default` and `static` methods in interfaces?

A default method in an interface is a method that has a body and provides a default implementation.
<br>
static method in an interface also has a body, but it belongs to the interface itself and is called using the interface name.

---

#### 22. What are the four access modifiers in Java?

The four access modifiers in Java are `public`, `protected`, default, and `private`. They control the visibility and accessibility of classes, methods, variables, and constructors.

---

#### 23. what are the mean of each access modifier?

Public members can be accessed from anywhere, private members can be accessed only within the same class, and default members can be accessed only within the same package. Protected members can be accessed within the same package and also from subclasses in different packages.

---

#### 24. What does the `static` keyword mean, and why can't a static method access instance members directly?

The static keyword means that a variable or method belongs to the class rather than an object.

---

#### 25. How is the `final` keyword used with variables, methods, and classes?

The final keyword is used in three ways in Java. When we use final with a variable, we cannot reassign its value after initialization. When we use it with a method, the method cannot be overridden by a child class. When we use it with a class, the class cannot be inherited.

---

#### 26. What is the difference between `==` and `equals()`?

The equals() method is used to compare the content of objects, The == operator compares values for primitive data types, but for objects, it compares their references.

---

#### 27. What are upcasting and downcasting?

Upcasting is converting a subclass reference to a superclass reference. It happens automatically in Java.
<br>
Downcasting is converting a superclass reference to a subclass reference. It requires explicit casting

---

#### 28. What is the difference between composition and inheritance?

Inheritance represents an “is-a” relationship, where a child class inherits properties and methods from a parent class. Composition represents a “has-a” relationship, where a class contains an object of another class. For example, a Dog is an Animal, so we can use inheritance. A Car has an Engine, so we can use composition. Composition is often preferred because it provides more flexibility, reduces tight coupling between classes.

---

#### 29. What is a static block?

In Java, a static block is a block of code that executes only once when the class is loaded. It is mainly used to initialize static variables.

---

#### 30. How do you create an immutable class in Java?

To create an immutable class in Java, we declare the class as `final`, make all fields `private` and `final`, initialize them through a constructor, and provide no setter methods. We should also make defensive copies of mutable objects to prevent their state from being changed.

---

#### 31. What do you mean by defensive copies ?

Defensive copies are copies of mutable objects created to protect an object's internal state from external modifications.

32. What is an inner class in java?
33. What are the types of inner classes in Java?
34. What is an anonymous class?
35. What is the difference between `final`, `finally`, and `finalize`?
36. What is method hiding?
37. What is the relationship between equals() and hashCode()?
