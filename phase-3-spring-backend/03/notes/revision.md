# Quick Revision

.java file = source code written by the developer
.class file = compiled bytecode understood by the JVM
<br/>
<br/>

.java -> javac -> .class (convert to bytecode) -> jvm -> output
--- 
Now imagine sharing this project with another developer. Sending many separate .class files is not practical because:
- Files can get misplaced.
- Package/folder structure can break.
- Some files may be missed.
- Resource files may not be included.
- Running the project becomes confusing.

--- 

JAR stands for Java Archive
- A JAR file is basically a packaged bundle for Java projects. You can think of it like a ZIP file designed specifically for Java.

A JAR file can contain:
- .class files
- folders/packages
- images
- properties files
- configuration files
- metadata

---

Why Do We Need a JAR File?
- Reason 1: To Share Java Code Easily
- Reason 2: To Use External Libraries

When we say, “I am using Spring Core”, practically our project is using Spring Core JAR files.


--- 

- Library :- Code created for other developers to use inside their projects. It usually does not run independently.
- Application :- Code created for the end user. It can run as a complete program, usually with a main() method or an application entry point

---

- A normal library JAR Usually contains your compiled code, not all external dependency JARs inside it.
- a Spring Boot application is commonly packaged as an **executable JAR or fat JAR**.
- In that case, Spring Boot can package the application code along with required dependencies

---

- The classpath is the place where jvm searches for classes.
- Classpath tells jvm where to search for required classes.


Java searches for classes in:
- Your own project classes
- External JAR files
- Other configured class locations

---
Then many problems appear:
- Where should we download these JARs from?
- Which version should we use?
- What if one JAR depends on another JAR?
- What if two teammates use different versions?
- What if we forget to add one required JAR?
- What if version 1 works, but version 2 breaks compatibility?

This is exactly where Maven becomes useful.
<br>
<br>
Maven says:
- Do not manually download and manage JAR files. Tell me what dependency your project needs, and I will download and manage the required JARs for you.

---

Maven is a project management and build automation tool for Java projects.
<br>
<br>
Maven is independent of Spring. You can use Maven with:
- Core Java projects
- Spring projects
- Spring Boot projects
- Hibernate projects
- Many other Java-based projects

Maven mainly helps with these things:
1. It gives a standard project structure.
2. It compiles Java code.
3. It runs tests.
4. It creates JAR/WAR files.
5. It downloads external JAR libraries.
6. It manages dependency versions.
7. It uses plugins to perform build-related tasks.

---

- Maven can be used from the command line.
- mvn clean install
- But most modern IDEs also support Maven directly, such as:
- IntelliJ IDEA
- Eclipse
- VS Code

--- 

Maven follows a principle called: Convention over Configuration This means Maven already assumes a standard project structure. If we follow that structure, we do not need to manually configure everything. Because of this standard convention, Maven automatically knows where to find source code, test code, resources, and where to place generated output
