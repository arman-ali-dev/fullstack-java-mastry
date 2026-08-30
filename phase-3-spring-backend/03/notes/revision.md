# JAR Files, Classpath, and Maven - Complete Notes

## 1. .java File vs .class File

- **.java file** = source code written by the developer
- **.class file** = compiled bytecode understood by the JVM

```
.java  →  javac  →  .class (bytecode)  →  JVM  →  output
```

### What is bytecode, and why does it matter? *(added)*
Bytecode is not machine code (0s and 1s specific to one OS) — it's a middle format that the JVM understands. This is why Java is called **"Write Once, Run Anywhere" (WORA)** — the same `.class` file can run on Windows, Linux, or Mac, as long as a JVM is installed there.

### JDK, JRE, and JVM *(added)*
These three terms are often confused:

| Term | What it is |
|---|---|
| **JVM** (Java Virtual Machine) | Actually runs the bytecode (`.class` files) |
| **JRE** (Java Runtime Environment) | JVM + libraries needed to *run* Java programs |
| **JDK** (Java Development Kit) | JRE + tools needed to *develop* Java programs (like `javac`, the compiler) |

**Simple analogy:** JDK = full toolkit (build + run), JRE = just enough to run a finished app, JVM = the actual engine that executes the code.

---

## 2. The Problem: Sharing Many .class Files

Now imagine sharing this project with another developer. Sending many separate `.class` files is not practical because:
- Files can get misplaced.
- Package/folder structure can break.
- Some files may be missed.
- Resource files may not be included.
- Running the project becomes confusing.

---

## 3. JAR Files

**JAR** stands for **Java Archive**.

A JAR file is basically a packaged bundle for Java projects. You can think of it like a ZIP file designed specifically for Java.

### A JAR file can contain:
- `.class` files
- folders/packages
- images
- properties files
- configuration files
- metadata

### Why Do We Need a JAR File?
1. **To Share Java Code Easily** — one file instead of hundreds of scattered `.class` files
2. **To Use External Libraries** — when we say "I am using Spring Core," practically our project is using Spring Core JAR files

### The MANIFEST.MF file *(added)*
Every JAR contains a special file called `META-INF/MANIFEST.MF`. It stores metadata about the JAR — most importantly, the **Main-Class**, which tells Java which class contains the `main()` method to run when the JAR is executed.

### Running a JAR *(added)*
If a JAR is executable (has a Main-Class defined), you can run it directly from the command line:
```
java -jar myapp.jar
```

---

## 4. Library vs Application

- **Library**: Code created for other developers to use inside their projects. It usually does not run independently.
- **Application**: Code created for the end user. It can run as a complete program, usually with a `main()` method or an application entry point.

---

## 5. Fat JAR / Executable JAR

- A normal library JAR usually contains **your compiled code only**, not all external dependency JARs inside it.
- A Spring Boot application is commonly packaged as an **executable JAR** or **fat JAR**.
- In that case, Spring Boot packages the application code **along with** all required dependencies — so the whole app can run with just one command, without needing anything else installed separately.

---

## 6. Classpath

The **classpath** is the place where the JVM searches for classes.

Classpath tells the JVM where to search for required classes.

Java searches for classes in:
1. Your own project classes
2. External JAR files
3. Other configured class locations

---

## 7. The Problem Before Maven

Once a project needs many external JARs, many problems appear:
- Where should we download these JARs from?
- Which version should we use?
- What if one JAR depends on another JAR?
- What if two teammates use different versions?
- What if we forget to add one required JAR?
- What if version 1 works, but version 2 breaks compatibility?

**This is exactly where Maven becomes useful.**

Maven says: *"Do not manually download and manage JAR files. Tell me what dependency your project needs, and I will download and manage the required JARs for you."*

---

## 8. What is Maven

Maven is a **project management and build automation tool** for Java projects.

Maven is independent of Spring. You can use Maven with:
- Core Java projects
- Spring projects
- Spring Boot projects
- Hibernate projects
- Many other Java-based projects

### Maven mainly helps with:
1. It gives a standard project structure.
2. It compiles Java code.
3. It runs tests.
4. It creates JAR/WAR files.
5. It downloads external JAR libraries.
6. It manages dependency versions.
7. It uses plugins to perform build-related tasks.

### Using Maven
- Maven can be used from the command line: `mvn clean install`
- Most modern IDEs also support Maven directly, such as: IntelliJ IDEA, Eclipse, VS Code

### Gradle — an alternative to Maven *(added)*
Maven is not the only build tool. **Gradle** is another popular build automation tool that does similar jobs (dependency management, compiling, packaging), but uses a script-based configuration (`build.gradle`) instead of XML (`pom.xml`), and is often considered faster for large projects.

---

## 9. Convention over Configuration

Maven follows a principle called: **Convention over Configuration**.

This means Maven already assumes a standard project structure. If we follow that structure, we do not need to manually configure everything. Because of this standard convention, Maven automatically knows where to find source code, test code, resources, and where to place generated output.

---

## 10. Maven Standard Project Structure

```
my-maven-project/
│
├── pom.xml
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │
│   └── test/
│       ├── java/
│       └── resources/
│
└── target/
```

| Folder/File | Purpose |
|---|---|
| `pom.xml` | Contains info about the complete Maven project (name, version, dependencies, plugins, build config, parent config) |
| `src/main/java` | Main Java source code of the application (e.g. `User.java`, `UserService.java`) |
| `src/main/resources` | Non-Java files needed by the main application (e.g. `application.properties`, `application.yml`, config files, static files, templates) |
| `src/test/java` | Test code |
| `target` | Created by Maven during the build process — contains compiled `.class` files, test reports, generated sources, the final JAR/WAR, and temporary build files |

---

## 11. POM (Project Object Model)

**POM** stands for **Project Object Model**.

It tells Maven:
- What the project is
- Which version the project has
- What type of output should be created
- Which external libraries are needed
- Which plugins should be used
- Whether there is any parent configuration

---

## 12. pom.xml Tags

```xml
<groupId>com.coderarmy</groupId>
<artifactId>calculator-app</artifactId>
<version>1.0.0</version>
```

Together, these 3 are called **Maven coordinates**.

| Tag | Meaning |
|---|---|
| `<groupId>` | Usually represents the organization, company, or domain that owns the project |
| `<artifactId>` | The name of the project or module |
| `<version>` | Tells which version of the project is being built. `SNAPSHOT` means the project is still under development |
| `<packaging>` | Tells Maven what type of output should be created (e.g. `jar`, `war`) |
| `<properties>` | Used to define reusable values in the POM (e.g. Java version) |
| `<dependencies>` | Contains external libraries required by the project |

Each dependency is also identified using Maven coordinates: `groupId` + `artifactId` + `version`. When Maven reads this dependency, it downloads the required JAR file from a Maven repository.

### Parent POM *(added)*
A project's `pom.xml` can inherit settings from a **parent POM**, so it doesn't need to repeat common configuration. Spring Boot projects commonly use `spring-boot-starter-parent` as their parent — it pre-configures sensible defaults (like Java version and dependency versions), so you don't have to specify a version for every single Spring dependency yourself.

---

## 13. Starter Dependencies

A **starter dependency** does not mean only one JAR. It usually brings a **group of related dependencies** needed for a particular feature.

For example, `spring-boot-starter-web` brings dependencies related to web application development.

---

## 14. Transitive Dependencies

In Maven, if you add dependency A, Maven can automatically download B and C also — these are called **transitive dependencies**.

Here, B and C are called transitive dependencies. This is one of the biggest reasons Maven is useful — you don't need to manually chase down every dependency's own dependencies.

### Dependency Scope *(added)*
Not every dependency is needed at every stage. Maven lets you define a **scope** for each dependency:

| Scope | Meaning |
|---|---|
| `compile` (default) | Needed everywhere — compiling, testing, and running |
| `test` | Needed only for running tests (e.g. JUnit) |
| `provided` | Needed to compile, but provided by the environment at runtime (e.g. servlet API in some setups) |
| `runtime` | Not needed to compile, but needed when running the app |

### Excluding a Transitive Dependency *(added)*
Sometimes a transitive dependency causes a conflict (e.g. two different versions of the same library being pulled in). Maven allows you to **exclude** a specific transitive dependency from a given dependency using `<exclusions>` in the `pom.xml`.

---

## 15. Maven Plugins

Maven Plugins are tools that Maven uses to perform tasks. Maven plugins can help with:
- Compiling code
- Running tests
- Creating JAR files
- Creating reports
- Running a Spring Boot application
- Packaging the application

---

## 16. Maven Repository

A **Maven repository** is a storage place where Maven artifacts are kept. Artifacts can include:
1. JAR files
2. WAR files
3. POM files
4. plugins
5. other build artifacts

### Types of Maven Repositories

| Type | Description |
|---|---|
| **Local repository** | A folder on your own computer: `C:\Users\<your-username>\.m2\repository`. Stores your own locally installed artifacts. |
| **Maven Central repository** | A huge public repository where many open-source Java libraries are published. |
| **Remote repository** | Any Maven repository that is not on your local machine — e.g. the internet, a company network, a private repository server. |

When we run `mvn install`, Maven builds the project and stores the final artifact in the local Maven repository. This allows other local Maven projects on the same machine to use that artifact.

---

## 17. How Maven Searches for a Dependency

1. Read `pom.xml`
2. See that a dependency is required
3. Check the local repository first
4. If found locally, use it
5. If not found locally, check remote repositories
6. Download the JAR and POM
7. Store them in the local repository
8. Use them in the project build

The first time, Maven downloads dependencies. After that, Maven uses the cached dependencies from the local `.m2` repository.

### Maven Wrapper (mvnw) *(added)*
Many projects include a **Maven Wrapper** (`mvnw` / `mvnw.cmd` files). This lets someone run Maven commands (`./mvnw clean install`) **without having Maven installed** on their machine — the wrapper downloads the correct Maven version automatically. This avoids "works on my machine" issues caused by different Maven versions.

---

## 18. Maven Lifecycle

A Java project has a build journey:
```
Check project → Compile code → Run tests → Create JAR → Store/Deploy artifact
```

Maven calls this ordered build journey a **lifecycle**.

**A Maven lifecycle is a standard sequence of steps Maven follows to build a project.**

The heart of the Maven lifecycle is this rule: **when we run a Maven phase, Maven automatically runs all earlier phases of that lifecycle before it.**

Example: `mvn package` actually runs:
```
validate → compile → test → package
```

### The Three Main Maven Lifecycles

| Lifecycle | Purpose |
|---|---|
| **Clean Lifecycle** | Cleans old build output |
| **Default Lifecycle** | Builds, tests, packages, installs, and deploys the project |
| **Site Lifecycle** | Generates project documentation and reports (`mvn site`). Output usually goes inside `target/site` |

### Important Default Lifecycle Phases

| Phase | What it does |
|---|---|
| `validate` | Checks whether the project is valid |
| `compile` | Compiles `.java` files into `.class` files |
| `test` | Runs test cases |
| `package` | Creates JAR/WAR file |
| `verify` | Performs additional checks on the packaged output |
| `install` | Stores artifact in local `.m2` repository |
| `deploy` | Uploads artifact to a remote Maven repository |
