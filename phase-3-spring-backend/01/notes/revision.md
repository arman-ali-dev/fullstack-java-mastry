# Client-Server Model, HTTP, and Spring Ecosystem - Notes

## 1. Client and Server

Your browser is running on your laptop or mobile phone, while Amazon's application is running on Amazon's server somewhere else.

**At the most basic level:**
```
Your Browser  ----  talks to  ----  Amazon Server
```

### Client
A **client** is the side that asks for something (sends a request).

**Examples of clients:**
1. Browser
2. Mobile app
3. Postman
4. Frontend React app
5. Android app
6. iOS app

When you open a website, your browser becomes the client.

### Server
A **server** is the side that receives requests, processes them, and sends back a response.

**Examples of servers:**
1. Amazon server
2. YouTube server
3. Bank server
4. Your Spring Boot application

---

## 2. HTTP: The Language of the Web

**HTTP** stands for: **HyperText Transfer Protocol**

HTTP is the rulebook for communication between a client and a server.

**HTTP defines:**
1. How a request should look
2. How a response should look
3. Which method is being used
4. Which URL is being called
5. What data is being sent
6. Which status code is returned

So the browser and server do not randomly exchange text — they follow a proper format.

### HTTP vs HTTPS *(added)*
- **HTTP** = data travels in plain text (not secure)
- **HTTPS** = HTTP + encryption (SSL/TLS) — data is secure and cannot be read if intercepted

Almost all real websites today use HTTPS.

### HTTP is Stateless *(added)*
Each HTTP request is independent — the server does not remember previous requests by default. Every request must carry all the information the server needs (like login tokens), because the server treats every request as brand new.

This is why things like **sessions**, **cookies**, and **JWT tokens** exist — to help the server "remember" a client across multiple requests.

---

## 3. HTTP Request - Structure

An HTTP request usually contains four main parts:
1. Method
2. URL or path
3. Headers
4. Body

### Method
The method tells the server what action the client wants to perform.

| Method | Purpose |
|---|---|
| GET | Read data |
| POST | Create data |
| PUT | Replace data completely |
| PATCH | Update data partially |
| DELETE | Remove data |

### Headers
Headers are key-value pairs that provide extra information about the request.

They tell the server things like:
1. What format the client can understand
2. What format the request body is in
3. Who the client is
4. Which host the client is trying to reach

**Common header examples** *(added)*:
| Header | Meaning |
|---|---|
| `Content-Type` | Format of the data being sent (e.g. `application/json`) |
| `Accept` | Format the client wants back in the response |
| `Authorization` | Login/auth token, used to identify the client |
| `Host` | Which domain/server the request is going to |

### Body
The body carries the actual data being sent by the client.

**Example** *(added)* — a POST request body sending a new user:
```json
{
  "name": "Jagir",
  "email": "jagir@example.com"
}
```

Note: GET and DELETE requests usually don't have a body — they mainly rely on the URL.

### Query Parameters and Path Variables *(added)*
Besides the body, data can also travel through the URL itself:

- **Path variable**: `/users/5` → `5` is the user's ID, part of the path
- **Query parameter**: `/users?age=25&city=Jaipur` → extra filters after `?`

---

## 4. HTTP Response - Structure

An HTTP response usually contains:
1. Status code
2. Headers
3. Body

### Status Codes *(added — was missing)*
Status codes tell the client what happened with their request. They're grouped into categories:

| Range | Meaning | Example |
|---|---|---|
| 1xx | Informational | 100 Continue |
| 2xx | Success | 200 OK, 201 Created |
| 3xx | Redirection | 301 Moved Permanently |
| 4xx | Client error (client's fault) | 400 Bad Request, 401 Unauthorized, 404 Not Found |
| 5xx | Server error (server's fault) | 500 Internal Server Error |

---

## 5. How a Web Server Program Behaves

A normal Java program usually follows this pattern:
```
Start
Run instructions
Finish
Exit
```

But a website or backend application follows a very different pattern:
```
Start
Keep running
Wait for requests
Process requests
```

A website is not like a program that runs once and exits. A **web server** is a program that stays alive continuously and keeps listening for incoming requests.

### Who actually "listens" for requests? *(added)*
In a Spring Boot app, an embedded **servlet container** (usually **Tomcat**) is what actually stays running and listens on a port (like `8080`) for incoming HTTP requests, and passes them to your Spring code to handle.

---

## 6. Spring is an Ecosystem

Spring is not just one small library. Spring is a large ecosystem of projects and frameworks. Different Spring projects solve different problems.

**Some important parts of the Spring ecosystem** *(completed — was cut off)*:

| Project | What it solves |
|---|---|
| **Spring Core** | The foundation — Dependency Injection, IoC container |
| **Spring MVC** | Building web applications and REST APIs |
| **Spring Boot** | Auto-configuration, easy setup, embedded server (no manual Tomcat setup) |
| **Spring Data (JPA)** | Simplifies database access, removes repository boilerplate |
| **Spring Security** | Authentication and authorization (login, roles, permissions) |
| **Spring Cloud** | Tools for microservices (service discovery, config management, etc.) |
| **Spring Batch** | Handling large batch/bulk data processing jobs |

**Simple analogy:** Spring is like a toolbox — Spring Boot is the easy-to-use handle, Spring MVC handles web requests, Spring Data handles the database, Spring Security handles login/permissions — each tool for a different job, all part of the same toolbox.
