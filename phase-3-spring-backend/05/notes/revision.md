In a well-designed application, a class should focus on its own responsibility. For example, OrderService should focus on placing an order. It should not be responsible for creating the object of PaymentService.
Now OrderService does not create its dependency. It receives the dependency from outside. This is Dependency Injection

- At the most basic level, every application needs objects.
- Those objects may also need other objects.
- So the real questions are:
    - Who will create these objects?
    - Who will connect them together?
    - Who will manage their lifecycle?

- Without Spring, we usually do this manually.
    - main() creates objects.
    - main() connects objects.
    - main() behaves like a small manual container.

- With Spring, this responsibility is shifted to the Spring IoC container.
    - Spring creates objects.
    - Spring connects objects.
    - Spring manages their lifecycle.


---
To work with Spring Core using annotation-based configuration:
1. Create a Maven project.
2. Add the spring-context dependency.
- spring-context gives us important container features such as:
- ApplicationContext
- Annotation-based configuration
- Component scanning
- Bean creation and dependency injection

A Spring Bean is an object whose creation, dependency wiring, and lifecycle are managed by the Spring IoC container.

---

Spring can manage objects mainly through two configuration styles:
1. Annotation-based configuration
2. XML-based configuration


---

Reflection: Why Student.class Matters
- When we write something like: Student.class
- we are not creating a Student object.
- Instead, we are referring to a special object of type Class .
- Example: Class<Student> c = Student.class;
- It contains metadata about the Student class, such as:

```java
Class name -> Student
Fields -> name, age
Methods -> study()
Constructors -> Student()
Annotations -> @Component, @Service, etc.
```

---

Spring does not automatically manage every class in the project. We need to tell Spring which classes are eligible to become beans.
- One common way is by using @Component .
- But just writing @Component is not enough.
- Spring also needs to know where it should search for such classes. That is where
- @ComponentScan comes in.

---

ApplicationContext : The Spring IoC Container
- ApplicationContext represents the Spring IoC container.
- It is responsible for:
    - Reading configuration
    - Creating beans
    - Resolving dependencies
    - Managing bean lifecycle
    - Providing beans when requested
- ApplicationContext is an interface.
- For annotation-based configuration, we commonly use: AnnotationConfigApplicationContext
- AnnotationConfigApplicationContext is an implementation of ApplicationContext .
- It starts a Spring container using Java annotation-based configuration.
- ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
- This means:
    - Start the Spring container.
    - Read instructions from AppConfig.class.
    - Use annotation-based configuration.
    - Create and manage beans accordingly.

---

AppConfig is a configuration class.
- This class tells Spring:
    - This is a configuration class.
    - Scan the package in.coderarmy.
    - Find classes marked with annotations like @Component.
    - Create their beans.
    - Wire their dependencies.
- @Configuration tells Spring that a class contains Spring configuration instructions.
- When Spring sees this, it understands:
    - This is not just a normal class.
    - This class may contain Spring setup instructions.
    - This class can be a source of bean definitions.
- A configuration class may contain:
    - @ComponentScan
    - @Bean methods
    - Other configuration-related instructions
- When Spring starts, it needs to know where to search for classes marked with annotations like @Component.
- This tells Spring:
    - Start scanning from com.coderarmy.
    - Also scan its sub-packages.
    - Find classes marked with @Component, @Service, @Repository, @Controller, etc.\Register them as beans.


---

After Spring creates and stores beans inside the container, we can ask the container for a bea: ervice = context.getBean(OrderService.class);

- In constructor injection, dependencies are provided through the constructor.
- So if OrderService needs PaymentService , the cleanest time to provide PaymentService is while creating OrderService .
- That means the object is created in a complete and usable state.
- If OrderService cannot work without PaymentService , constructor injection makes that requirement clear.
- With constructor injection, we can manually create and test the class.
- We Can Use final This means once the dependency is assigned, it cannot be changed accidentally

---

In field injection, Spring directly injects the dependency into a field.
- This works because Spring can use reflection to set the field value.
- However, field injection is generally not preferred.
- Reasons:
    - The dependency is hidden.
    - The class cannot be easily tested without Spring.
    - The field cannot be marked as final.
    - The object can exist in an incomplete state before Spring injects the field.


In setter injection, Spring creates the object first and then calls a setter method to provide the dependency.
- Create OrderService object using no-argument constructor.
- Call setPaymentService().
- Pass PaymentService into the setter.
- Setter injection is useful when a dependency is optional or can be changed after object creation

---

Step 1: Spring Starts the Container
  - Spring creates an ApplicationContext .
  - This becomes the IoC container for our application.








