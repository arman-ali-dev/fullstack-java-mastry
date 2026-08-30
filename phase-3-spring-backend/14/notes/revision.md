# Servlets and Tomcat - Complete Notes

## 1. What Happens When a Browser Sends a Request

Before understanding Servlets, first understand what happens when a browser sends a request.

- A browser does not understand Java classes, Java methods, or Java objects. It only understands **HTTP**.
- So the browser sends an HTTP request.

**Who receives this HTTP request?**
- A Java class does not automatically receive browser requests.
- We need a **server process** running on a port that can receive the request.

---

## 2. Java Can Listen on a Port

Java can do networking through the `java.net` package. Using classes like `ServerSocket`, Java can listen on a port — this means Java can open port 8080 and wait for incoming connections.

But the browser sends **raw HTTP text**, and if we use raw Java networking, we must handle everything manually — parsing the HTTP method, the URL, the headers, the body, and building a correctly formatted HTTP response text by hand.

Java can do networking, but building a full web application directly with low-level sockets is difficult, repetitive, and error-prone.

**This is the first major problem Servlets help solve.**

---

## 3. Web Applications Run Continuously

A normal Java program starts, executes some code, prints output, and usually finishes.

A web application is different:
- It should not start once and stop immediately.
- It should keep running continuously and wait for HTTP requests.

---

## 4. Let a Container Handle HTTP

Instead of every developer manually handling raw HTTP, let a **container** handle HTTP and call our Java class.

- **Tomcat** handles the low-level HTTP work.
- Our **Servlet** handles the application logic.

So instead of reading raw HTTP text manually, we get ready-made Java objects:
1. `HttpServletRequest request`
2. `HttpServletResponse response`

**A Servlet is a Java class used to handle web requests and generate web responses.**

- Tomcat listens on the port.
- Servlet lives inside Tomcat.
- When a matching request comes, Tomcat calls the Servlet.

### The Servlet API package *(added)*
Servlets are part of the standard **Servlet API**, found in the `jakarta.servlet` package (older versions used `javax.servlet`). `HttpServletRequest` and `HttpServletResponse` are interfaces from this API — Tomcat provides the actual implementations and hands them to your Servlet.

### A basic Servlet example *(added — was missing)*
```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.getWriter().write("Hello from Servlet!");
    }
}
```

### How different HTTP methods map to Servlet methods *(added)*
A Servlet doesn't have just one method — it has separate methods for each HTTP verb:

| HTTP Method | Servlet Method |
|---|---|
| GET | `doGet()` |
| POST | `doPost()` |
| PUT | `doPut()` |
| DELETE | `doDelete()` |

Tomcat automatically calls the correct one based on the incoming request's HTTP method.

### How does Tomcat know which URL maps to which Servlet? *(added — was missing)*
This mapping can be defined in two ways:
1. **`@WebServlet` annotation** on the Servlet class:
   ```java
   @WebServlet("/hello")
   public class HelloServlet extends HttpServlet { ... }
   ```
2. **`web.xml`** — an older, XML-based deployment descriptor that explicitly maps URLs to Servlet classes.

Either way, Tomcat uses this mapping to decide which Servlet should handle a given incoming URL.

---

## 5. Tomcat Is a Servlet Container

Tomcat is a Java-based server that can run Java web applications.

More specifically: **Tomcat is a Servlet Container.**

This means Tomcat provides the environment where Servlets can live, run, and handle HTTP requests.

- Servlet Container manages Servlets.
- Tomcat is called a Servlet Container because it manages Servlet objects.
- So we do not manually create Servlet objects — Tomcat does.

---

## 6. Servlet Lifecycle *(added — was missing entirely)*

A Servlet doesn't just have `doGet()`/`doPost()` — it goes through a defined lifecycle, managed entirely by the Servlet Container (Tomcat):

| Method | When it runs |
|---|---|
| `init()` | Called **once**, when the Servlet object is first created |
| `service()` | Called for **every request** — internally routes to `doGet()`, `doPost()`, etc. based on the HTTP method |
| `destroy()` | Called **once**, when the Servlet is being taken out of service (e.g. app shutdown) |

You rarely override `init()`/`destroy()` directly for simple apps — `doGet()`/`doPost()` are usually enough — but knowing this lifecycle exists explains *how* Tomcat reuses one Servlet object for many requests.

---

## 7. How Tomcat Runs as a Server

When Tomcat starts, it becomes a **long-running server process**.

- It does not start, run one method, and stop.
- It keeps waiting for HTTP requests.

### What Tomcat Does When a Request Comes
1. Which application? → `myapp`
2. Which URL? → `/hello`
3. Which Servlet? → `HelloServlet`
4. Which method? → `doGet()`

Usually, Tomcat creates the Servlet object when the **first matching request** comes. After that, the **same Servlet object** can handle future requests.

### Important: One Servlet instance, many requests, many threads *(added — was missing)*
Since the same Servlet object is reused for every request, and web servers handle many requests at the same time, Tomcat runs each request on a **separate thread**, but all of them call methods on the **same shared Servlet instance**.

This means: **instance fields on a Servlet are shared across all requests/threads** — writing to a shared field without care can cause bugs affecting other users' requests. This is why Servlets (and Spring beans in general) should generally avoid holding request-specific mutable state in instance fields.

### What is `myapp`? — Context Path *(added)*
`myapp` here is the **context path** — it's typically the name of the deployed application (often derived from the WAR file's name), used to distinguish between multiple applications running on the same Tomcat instance. A request URL looks like: `http://host:port/myapp/hello`.

---

## 8. Deploying to an External Tomcat

Traditional deployment steps:
1. Install Tomcat on your machine/server
2. Build the Java web application as a **WAR file**
3. Put the WAR file inside Tomcat
4. Start Tomcat
5. Tomcat runs the application

**WAR** means: **Web Application Archive**

- A WAR file is a packaged Java web application.
- Just like a normal Java project can be packaged as: `myapp.jar`
- A traditional Java web application is packaged as: `myapp.war`

**WAR = complete web application package**

---

## 9. One Tomcat Can Run Multiple Web Applications

External Tomcat can host multiple Java web applications at the same time — each with its own context path (`/app1`, `/app2`, etc.), all sharing the same running Tomcat process and port.

### External Tomcat vs Embedded Tomcat *(added — ties back to earlier Spring Boot notes)*
This whole flow (install Tomcat → build WAR → deploy → start) describes **external Tomcat** — the traditional way of running Java web apps.

Spring Boot takes a different approach: it uses an **embedded Tomcat**, bundled directly inside the executable JAR. There's nothing to install or deploy separately — running `java -jar myapp.jar` starts both your application *and* its own private Tomcat instance together. This is simpler, but means (by default) one JAR = one Tomcat = one application, rather than one external Tomcat hosting several apps.

| | External Tomcat | Embedded Tomcat (Spring Boot) |
|---|---|---|
| Setup | Install Tomcat separately | Bundled inside the JAR |
| Deployment | Build & deploy a WAR file | Just run the JAR |
| Multiple apps per server | Yes, common | No — one app per embedded instance |
