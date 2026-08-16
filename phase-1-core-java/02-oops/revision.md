Without OOP, everything is just variables and functions scattered around. There is no structure. With OOP, you group related data and behavior together into one unit called a class. This makes code organized, reusable, and easy to understand.

- Java is built on 4 OOP concepts:
1. Encapsulation
2. Inheritance
3. Polymorphism
4. Abstraction


---

- A class is a blueprint. An object is a real thing made from that blueprint.
- A constructor runs automatically when an object is created. It is used to set initial values. If you don't write one, Java gives you a default empty constructor
- Constructor Overloading - Multiple constructors with different parameters in the same class.
- this() - calling one constructor from another

---

- Encapsulation means keeping your data private and only allowing access through methods (getters and setters). This protects your data from invalid values.
- this refers to the current object. Use it when field name and parameter name are the same.
- Inheritance means one class gets all the fields and methods of another class. The child does not need to rewrite what the parent already has.
- super is used to access the parent class constructor or methods from the child class.
- Method Overriding - Child class provides its own version of a method that already exists in the parent.
- Use @Override annotation - it's not required but it's good practice
- // final class - cannot be extended
- // final method - cannot be overridden
- Polymorphism means one thing taking many forms. In Java, the same method name does different things based on the object or the arguments.
- Compile time polymorphism - Method Overloading
- Runtime polymorphism - Method Overriding
- Same method name, different parameters in the same class. Java decides which one to call based on what you pass.
- Runtime Polymorphism (Method Overriding) - Parent reference holds a child object. When you call a method, the child's version runs - not the parent's. This is decided at runtime.
- Abstraction means hiding the internal details and only showing what is necessary.
