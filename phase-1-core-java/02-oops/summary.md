# Java OOP - Quick Recall Summary

One-line-per-concept summary. Goal: read it, remember what the topic does, not full syntax.

---

## PART 1 - What is OOP and Why

- OOP groups related data + behavior into one unit (a class) instead of scattered variables/functions
- 4 pillars: **Encapsulation, Inheritance, Polymorphism, Abstraction**

## PART 2 - Class and Object

- **Class** — blueprint; **Object** — actual instance created from that blueprint using `new`
- Each object has its own separate copy of instance fields

## PART 3 - Constructor

- Runs automatically on object creation; same name as class, no return type
- If none is written, Java provides a default empty constructor
- **Constructor overloading** — multiple constructors, different parameter lists
- **`this()`** — calls another constructor of the same class (must be the first line)

## PART 4 - Encapsulation

- Keep fields `private`, expose access only via public getters/setters — protects data from invalid values (like an ATM controlling access to your account)
- Setters can validate input before assigning (e.g. reject negative age)
- **`this`** keyword — refers to current object, resolves naming clash between field and parameter

## PART 5 - Inheritance

- Child class reuses parent's fields/methods via `extends`; avoids rewriting shared code
- **`super`** — calls parent constructor (must be first line in child constructor) or parent's method
- **Method Overriding** — child redefines a parent method: same name, same parameters; use `@Override`
- Types: **Single** (A→B), **Multilevel** (A→B→C), **Hierarchical** (A→B, A→C); **Multiple inheritance with classes is NOT allowed** in Java (use interfaces instead)
- `final class` — cannot be extended; `final method` — cannot be overridden

## PART 6 - Polymorphism

- One method behaves differently depending on object or arguments — "many forms"
- **Compile-time (Method Overloading)** — same method name, different parameter list, resolved at compile time
- **Runtime (Method Overriding)** — parent reference holds child object; the child's overridden method runs — decided at runtime
- Classic pattern: array/list of parent-type references, each calling its own overridden behavior in a loop

## PART 7 - Abstraction

- Hides internal details, shows only what's necessary — two ways: **abstract class** and **interface**
- **Abstract class** — cannot be instantiated directly; can mix abstract methods (no body, must be implemented) with concrete methods (shared code); use when related classes share some common code
- **Interface** — pure contract, only declares WHAT not HOW; methods are `public abstract` by default; a class can implement **multiple** interfaces; use when unrelated classes need the same contract
- **Default methods** (Java 8+) — interface methods with a body, optional to override
- **Static methods in interface** — called via interface name, not through an object
- Abstract class: one parent only, can have constructor/fields/any access modifier. Interface: many can be implemented, no constructor, only constants

## PART 8 - Access Modifiers

- **private** — same class only
- **default** (no modifier) — same package only
- **protected** — same package + subclasses (even in different packages)
- **public** — accessible everywhere

## PART 9 - Static Keyword

- `static` belongs to the class, shared across ALL objects — not tied to any single instance
- Static methods have no access to `this` or instance fields directly
- **Static block** — runs once when the class is loaded, before any object is created (good for one-time setup)

## PART 10 - Final Keyword

- **final variable** — value can't be reassigned after initialization
- **final method** — can't be overridden by a subclass
- **final class** — can't be extended at all

## PART 11 - instanceof Operator

- Checks whether an object is an instance of a given class/interface — returns boolean
- Commonly used before downcasting to avoid `ClassCastException`

## PART 12 - Upcasting and Downcasting

- **Upcasting** — child object assigned to parent-type reference; automatic and safe
- **Downcasting** — parent reference cast back to child type; must be done manually with `(ChildType)`, risks `ClassCastException` if the object isn't actually that child type — check with `instanceof` first

## PART 13 - Object Class

- Every class implicitly extends `Object`, inheriting methods like `toString()`, `equals()`, `hashCode()`
- **toString()** — override to control what prints when you print an object directly
- **equals()** — override to compare objects by content instead of default reference (`==`) comparison
- **hashCode()** — always override together with `equals()` for consistency (used by hash-based collections)

## PART 14 - Inner Classes

- **Inner class** — defined inside another class, has access to the outer class's fields
- **Static nested class** — inner class marked `static`, no access to outer instance fields, created without needing an outer object
- **Anonymous class** — nameless one-off class, typically used to implement an interface/abstract class inline at the point of use

## PART 15 - Complete Example

- Real combined usage: `abstract class Person` (shared fields/behavior) + `interface Workable` (contract) implemented by `FullTimeEmployee` and `FreelanceEmployee`, each overriding `doWork()` and `calculatePay()` differently — demonstrates encapsulation (private fields + getters), inheritance (`extends Person`), abstraction (abstract class + interface), and polymorphism (looping over `Person` list, calling overridden behavior per object)

---

## One-Glance Cheat Table

| Topic                   | What it does                                                                              |
| ----------------------- | ----------------------------------------------------------------------------------------- |
| Class/Object            | Blueprint vs actual instance created from it                                              |
| Constructor             | Auto-runs on creation to set initial state; can be overloaded                             |
| Encapsulation           | Private fields + public getters/setters to protect data                                   |
| Inheritance             | Child reuses parent's code via `extends`; `super` accesses parent                         |
| Method Overriding       | Child redefines parent's method — same signature                                          |
| Polymorphism            | Same method name, different behavior — overloading (compile-time) vs overriding (runtime) |
| Abstraction             | Hide details — abstract class (partial implementation) or interface (pure contract)       |
| Access Modifiers        | private → default → protected → public (increasing visibility)                            |
| static                  | Belongs to class, shared by all objects, no `this` access                                 |
| final                   | Locks a variable's value, a method from override, or a class from extension               |
| instanceof              | Checks an object's actual type before downcasting                                         |
| Upcasting/Downcasting   | Child→Parent (auto/safe) vs Parent→Child (manual/risky)                                   |
| Object class methods    | `toString()`, `equals()`, `hashCode()` — override for meaningful comparisons/printing     |
| Inner/Anonymous classes | Class nested inside another, or a nameless one-off implementation                         |
