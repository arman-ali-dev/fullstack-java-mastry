# Networking Basics, Spring Initializr, and Spring Boot Server - Notes

## 1. IP Address and Port Number

An **IP address** identifies the machine.
A **port number** identifies the application running inside that machine.

So:
- The IP address brings the data to the **correct machine**.
- The port number helps the operating system deliver that data to the **correct application** on that machine.

| | Tells us |
|---|---|
| IP address | Which machine to reach |
| Port number | Which application on that machine should receive the request |

**Simple analogy** *(added)*: The IP address is like a building's address, and the port number is like the specific apartment/flat number inside that building. The address gets you to the right building, the flat number gets you to the right door.

### Port Range *(added)*
Port numbers range from `0` to `65535`. Some ports are reserved for well-known purposes:
- Ports `0–1023` → reserved for well-known protocols (e.g. 80 for HTTP, 443 for HTTPS)
- Ports above `1023` → generally free to use for custom applications (e.g. Spring Boot's default `8080`)

---

## 2. localhost

`localhost` = your own machine.

`localhost` is equivalent to: `127.0.0.1`

This means when you type `localhost` in the browser, you're talking to your own computer, not some remote server.

---

## 3. How Does the Browser Know Which Port to Use?

Usually, we do not mention any port number in the URL. That is because browsers use **default ports**.

| Protocol | Default Port |
|---|---|
| http | 80 |
| https | 443 |

So `http://example.com` actually means `http://example.com:80` — the `:80` is just hidden because it's the default.

---

## 4. DNS (Domain Name System)

DNS gives the IP address of a domain.

**How it works** *(added)*: Computers only understand IP addresses, not names like `google.com`. DNS acts like a phonebook — you give it a domain name, and it looks up and returns the matching IP address, so your browser knows which machine to actually connect to.

```
You type: google.com
DNS converts it to: an IP address (e.g. 142.250.xxx.xxx)
Browser connects to: that IP address
```

---

## 5. Spring Initializr

Spring Initializr is a **project generator** for Spring applications.

It creates the basic project skeleton for us so that we can quickly start writing application logic.

**In simple words:** Spring Initializr helps us create a ready-to-use Spring Boot project.

It allows us to choose:
1. Project type
2. Programming language
3. Spring Boot version
4. Project metadata
5. Packaging type
6. Java version
7. Dependencies

### Packaging Type: JAR vs WAR *(added)*
- **JAR** — packages the app with an embedded server inside it (most common for Spring Boot; you just run it directly)
- **WAR** — packages the app to be deployed inside an external server (like a standalone Tomcat), common in older/enterprise setups

---

## 6. Dependencies and Libraries

When we build a Java project, we often need external libraries.

For example, if we want our Java application to connect with MySQL, we need a MySQL connector library.

- That reusable external code is called a **library**.
- When our project needs that library to work, it becomes a **dependency**.

**A dependency is an external library required by our project.**

### How dependencies are managed *(added)*
You don't manually download library files. Build tools like **Maven** (`pom.xml`) or **Gradle** (`build.gradle`) handle this — you just list what you need, and the tool downloads it automatically along with anything *that* library itself depends on (called a **transitive dependency**).

---

## 7. Version Types: SNAPSHOT, RC, Stable

| Version Type | Meaning |
|---|---|
| **SNAPSHOT** | A work-in-progress version. Still being developed, can change anytime. |
| **RC (Release Candidate)** | A version that's almost final, close to ready for release, but still being tested. |
| **Stable Release** | A tested, finalized version, safe for real use. |

**For beginners and real projects, we should prefer stable releases.** A stable release is tested and suitable for normal development.

**Order of maturity** *(added)*:
```
SNAPSHOT  →  RC (Release Candidate)  →  Stable Release
(in progress)   (almost ready)          (production-ready)
```

---

## 8. Controller and @RestController

A **Controller** is the entry point for incoming web requests.

`@RestController` is an annotation in Spring. It tells Spring: **"This class can handle HTTP requests and return data directly as a response."**

### What @RestController actually is *(added)*
`@RestController` = `@Controller` + `@ResponseBody` combined into one annotation.
- `@Controller` marks the class as a request-handler.
- `@ResponseBody` means whatever the method returns is sent directly as the response body (e.g. as JSON), instead of trying to load an HTML page/view.

### Mapping requests to methods *(added)*
Inside a controller, specific methods are mapped to specific URLs/HTTP methods using annotations like:

```java
@RestController
public class UserController {

    @GetMapping("/users")
    public List<User> getUsers() {
        // handles GET request to /users
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        // handles POST request to /users
    }
}
```

| Annotation | Maps to HTTP Method |
|---|---|
| `@GetMapping` | GET |
| `@PostMapping` | POST |
| `@PutMapping` | PUT |
| `@PatchMapping` | PATCH |
| `@DeleteMapping` | DELETE |

---

## 9. Embedded Tomcat in Spring Boot

When we run the Spring Boot application, we may see a message like:

```
Tomcat started on port 8080
```

This is very important:
- We did not install Tomcat separately.
- We did not write socket programming code.
- We did not manually create a server.

**Still, Tomcat started. Why?**

Because Spring Boot includes and configures an **embedded Tomcat server** for us when we use the web dependency (`spring-boot-starter-web`).

### Correct understanding of the flow:

1. **Spring Boot** starts and configures the application.
2. **Embedded Tomcat** listens for HTTP requests (on port 8080 by default).
3. **Spring MVC** maps the request to the correct controller method.

### Changing the default port *(added)*
You can change the port Tomcat runs on by adding this to `application.properties`:

```properties
server.port=9090
```
