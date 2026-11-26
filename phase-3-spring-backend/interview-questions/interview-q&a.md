#### 1. What is the difference between a client and a server?

A client is an application or device that sends requests to a server and receives responses. A server is a system or application that receives client requests, processes them, and sends back responses.

---

#### 2. What is HTTP? What is the difference between HTTP and HTTPS?

HTTP stands for HyperText Transfer Protocol. It is a communication protocol used to transfer data between a client and a server.
<br>
HTTPS stands for HyperText Transfer Protocol Secure. It is the secure version of HTTP that uses TLS to encrypt communication between the client and the server.

---

#### 3. What does "HTTP is stateless" mean?

HTTP is a stateless protocol, which means the server does not automatically remember previous requests from a client. Each HTTP request is treated independently.

---

#### 4. What are the main parts of an HTTP request and an HTTP response?

An HTTP request mainly consists of three parts: Request Line, Headers, and Body. The Request Line contains the HTTP method, request URL or path, and HTTP version. Headers provide additional information, such as content type, authorization, and accepted response formats. The Body contains the actual data sent by the client,

---

#### 5. PUT vs PATCH? (And when do you use GET, POST and DELETE?)

GET is used to retrieve data from the server. POST is used to create a new resource or perform an operation on the server. PUT is used to completely replace an existing resource. PATCH is used to partially update an existing resource. DELETE is used to remove an existing resource from the server.

---

#### 6. Path variable vs query parameter vs request body?

@PathVariable extracts values directly from the URL path. @RequestParam extracts query parameters from the URL. @RequestBody reads data from the HTTP request body.

---

#### 7. What are HTTP status codes?

HTTP status codes are three-digit numbers returned by the server to indicate the result of a client's request.

---

#### 8. What is REST API?

REST API stands for Representational State Transfer Application Programming Interface. used for communication between clients and servers over HTTP.

---

#### 9. What is an API?

API stands for Application Programming Interface. It is a way for communication between client and server.

---

#### 10. What is JSON? Why is it used?

JSON stands for JavaScript Object Notation. It is a lightweight, text-based data format used to store and exchange data between different applications.

---

#### 11. What is the difference between an IP address and a port number?

An IP address identifies a device. whereas a port number identifies a specific application or service running on that device.

---

#### 12. What is DNS and how does it work?

DNS stands for Domain Name System. It is used to translate human-readable domain names into IP addresses because computers use IP addresses to communicate over a network.
<br>
When a user enters a domain name in the browser, the browser first checks its cache. If the IP address is not available, the request goes to a DNS resolver.

---

#### 13. What is Spring Initializr?

Spring Initializr is a web-based tool used to quickly generate Spring Boot projects. It allows developers to select the project type, programming language, Spring Boot version, Java version, and required dependencies. Based on these selections, it generates a ready-to-use project structure with the necessary configuration files.

---

#### 14. What is the difference between JAR and WAR packaging?

JAR stands for Java Archive, and WAR stands for Web Application Archive. Both are packaging formats used to distribute Java applications.
<br>
A JAR file is commonly used for standalone Java and Spring Boot applications. It can include an embedded server like Tomcat, so we can run it directly using the java -jar command.
<br>
A WAR file is mainly used for web applications that are deployed to an external application server like Tomcat.

---

#### 15. What is a dependency? How are dependencies managed in a Spring Boot project?

A dependency is an external library or module that our project needs to use specific functionality without implementing everything from scratch. Dependencies are managed using build tools like Maven or Gradle.

---

#### 16. What is embedded Tomcat?

Embedded Tomcat is a web server that is included inside a Spring Boot application. Normally, we need to install Tomcat separately and deploy our web application to it. But with embedded Tomcat, we don't need to install or configure an external server. Spring Boot automatically starts Tomcat when we run the application.

---

#### 17. What is a fat JAR, and why does Spring Boot use it?

A Fat JAR, also known as executable JAR file that contains the application's compiled classes, resources, and required dependencies. Spring Boot uses Fat JARs to make application deployment easier.

---

#### 18. What is the classpath?

Classpath is the path or set of locations where the Java compiler and JVM search for required classes, resources, and libraries.

---

#### 19. What is Maven and why do we need it?

Maven is a build automation and dependency management tool mainly used for Java projects. We use Maven to manage project dependencies, compile source code, run tests, and package applications into JAR or WAR files.
<br>
Maven uses a configuration file called pom.xml, where we define project information, dependencies, plugins, and build configurations.

---

#### 20. Maven vs Gradle?

Maven and Gradle are both build automation and dependency management tools used in Java projects. The main difference is their configuration Maven uses XML-based configuration through the pom.xml file, whereas Gradle uses Script-Based configuration through build.gradle file.

---

#### 21. What is pom.xml?

POM stands for Project Object Model. The pom.xml file is the main configuration file of a Maven project. It contains project information, dependencies, plugins, and build configurations.

---

#### 22. What is a starter dependency?

A starter dependency is a group of dependencies for a specific functionality. Instead of adding multiple related dependencies manually, we can add a single starter dependency, and It brings the required libraries.

---

#### 23. What are transitive dependencies?

Transitive dependencies are the required dependencies of a particular dependency that are automatically included in our project.

---

#### 24. Where does Maven download dependencies from?

Maven downloads dependencies from repositories.
<br>
There are three main types of Maven repositories:

1. Local Repository: It is located on our computer.
2. Central Repository: It is the default public repository.
3. Remote Repository: These are additional repositories, such as company-specific or third-party repositories.

---

#### 25. What is tight coupling?

Tight coupling occurs when one class is highly dependent on another specific class. For example, if a UserService directly creates an EmailService object, then UserService becomes tightly coupled to EmailService.
