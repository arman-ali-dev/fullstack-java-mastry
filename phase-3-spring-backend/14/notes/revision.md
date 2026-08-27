Before understanding Servlets, first understand what happens when a browser sends a request.

- A browser does not understand Java classes, Java methods, or Java objects. It only understands HTTP.
- So the browser sends an HTTP request

Who receives this HTTP request?
- A Java class does not automatically receive browser requests.
- server process running on a port that can receive the request.

---

ava Can Listen on a Port. 
- Java can do networking through the java.net package
- Using classes like ServerSocket , Java can listen on a port
- This means Java can open port 8080 and wait for incoming connections.
- But the browser sends HTTP text, and if we use raw Java networking, we must handle everything manually.

Java can do networking, but building a full web application directly with low-level sockets is difficult, repetitive, and error-prone.

<br>

**This is the first major problem Servlets help solve**

---

The normal java program starts, executes some code, prints output, and usually finishes.

- A web application is different.
- A web application should not start once and stop immediately.
- It should keep running continuously and wait for HTTP requests.

---

Instead of every developer manually handling raw HTTP, let a container handle HTTP and call our Java class

- Tomcat handles the low-level HTTP work.
- Our Servlet handles the application logic.
- So instead of reading raw HTTP text manually, we get ready-made Java objects:
1. HttpServletRequest request
2. HttpServletResponse response

A Servlet is a Java class used to handle web requests and generate web responses.

- Tomcat listens on the port.
- Servlet lives inside Tomcat.
- When a matching request comes, Tomcat calls the Servlet.

---

Tomcat is a Java-based server that can run Java web applications.
- More specifically: Tomcat is a Servlet Container.
- This means Tomcat provides the environment where Servlets can live, run, an handle HTTP requests.
- Servlet Container manages Servlets.
- Tomcat is called a Servlet Container because it manages Servlet objects.
- So we do not manually create Servlet objects.

---

- When Tomcat starts, it becomes a long-running server process.
- It does not start, run one method, and stop.
- It keeps waiting for HTTP requests

What Tomcat Does When a Request Comes

1. Which application? → myapp
2. Which URL? → /hello
3. Which Servlet? → HelloServlet
4. Which method? → doGet()

Usually, Tomcat creates the Servlet object when the first matching request comes. After that, the same Servlet object can handle future requests.

---

1. Install Tomcat on your machine/server
2. Build the Java web application as a WAR file
3. Put the WAR file inside Tomcat
4. Start Tomcat
5. Tomcat runs the application

WAR means: Web Application Archive
- A WAR file is a packaged Java web application.


- Just like a normal Java project can be packaged as: myapp.jar

- a traditional Java web application is packaged as: myapp.war

WAR = complete web application package

---

One Tomcat Can Run Multiple Web Applications
- External Tomcat can host multiple Java web applications at the same time.







