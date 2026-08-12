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


--- 

Maven Standard Project Structure - 
<br>
<br>

my-maven-project/
│
├── pom.xml
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ └── resources/
│ │
│ └── test/
│ ├── java/
│ └── resources/
│
└── target/

<br>
pom.xml - This file contains information about the complete Maven project

1. Project name
2. Project version
3. Required dependencies
4. Plugins
5. Build configuration
6. Parent configuration, if any

- src/main/java This folder contains the main Java source code of the application.
Example:
- User.java
- UserService.java

- src/main/resources This folder contains non-Java files needed by the main application.

Example:
- application.properties
- application.yml
- configuration files
- static files
- templates

- src/test/java This folder contains test code
- target :- The target folder is created by Maven during the build process.

It can contain:
- compiled .class files
- test reports
- generated sources
- final JAR/WAR file
- temporary build files

---

POM stands for Project Object Model.
<br>
It tells Maven:
- What the project is
- Which version the project has
- What type of output should be created
- Which external libraries are needed
- Which plugins should be used
- Whether there is any parent configuration

---

pom.xml tags

```pom
<groupId>com.coderarmy</groupId>
<artifactId>calculator-app</artifactId>
<version>1.0.0</version>
```
Together, these 3 are called Maven coordinates

- <groupId> groupId usually represents the organization, company, or domain that owns the project.
- <artifactId> artifactId is the name of the project or module.
- <version> version tells which version of the project is being built.
- SNAPSHOT means the project is still under development.
- <packaging> Tag The <packaging> tag tells Maven what type of output should be created.
- <properties> Tag The <properties> tag is used to define reusable values in the POM
- <dependencies> Tag The <dependencies> section contains external libraries required by the project
- Each dependency is also identified using Maven coordinates: groupId + artifactId + version

When Maven reads this dependency, it downloads the required JAR file from a Maven repository.

---

- A starter dependency does not mean only one JAR. It usually brings a group of related dependencies needed for a particular feature. For example, spring-boot-starter-web brings dependencies related to web application development.


- In Maven, if you add dependency A, Maven can automatically download B and C also. These are called transitive dependencies.
- here B And C Called Transitive Dependencies
- This is one of the biggest reasons Maven is useful.


---

Maven Plugins are tools that Maven uses to perform tasks. Maven plugins can help with:
- Compiling code
- Running tests
- Creating JAR files
- Creating reports
- Running a Spring Boot application
- Packaging the application

---

A Maven repository is a storage place where Maven artifacts are kept. Artifacts can include:
1. JAR files
2. WAR files
3. POM files
4. plugins
5. other build artifacts

---

Types of Maven Repositories

1. Local repository - The local repository is a folder on your own computer C:\Users\<your-username>\.m2\repository It stores your own locally installed artifacts.
2. Maven Central repository - Maven Central is a huge public repository where many open-source Java libraries are published.
3. Remote repository - A remote repository is any Maven repository that is not on your local machine for example The internet, A company network, A private repository server


When we run: mvn install Maven builds the project and stores the final artifact in the local Maven repository. This allows other local Maven projects on the same machine to use that artifact.

---

How Maven Searches for a Dependency
1. Read pom.xml
2. See that a dependency is required
3. Check the local repository first
4. If found locally, use it
5. If not found locally, check remote repositories
6. Download the JAR and POM
7. Store them in the local repository
8. Use them in the project build

The first time, Maven downloads dependencies. After that, Maven uses the cached dependencies from the local .m2 repository.

---

Maven Lifecycle
- A Java project has a build journey.
- Check project → Compile code → Run tests → Create JAR → Store/Deploy artifact
- Maven calls this ordered build journey a lifecycle.
- A Maven lifecycle is a standard sequence of steps Maven follows to build a project.
- The heart of Maven lifecycle is this rule:
- When we run a Maven phase, Maven automatically runs all earlier phases of that lifecycle before it.
- mvn package
- validate → compile → test → package

---

The Three Main Maven Lifecycles

1. Clean Lifecycle - Cleans old build output
2. Default Lifecycle - Builds, tests, packages, installs, and deploys the project
3. Site Lifecycle - Generates project documentation and reports (mvn site) Output usually goes inside: target/site


Important Default Lifecycle Phases
1. validate - Checks whether the project is valid
2. compile Compiles .java files into .class files
3. test Runs test cases
4. package Creates JAR/WAR file
5. verify Performs additional checks on the packaged output
6. install Stores artifact in local .m2 repository
7. deploy Uploads artifact to a remote Maven repository

