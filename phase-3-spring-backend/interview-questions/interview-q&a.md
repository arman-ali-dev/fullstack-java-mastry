#### 1. What is HTTP? What is HTTPS?

HTTP is a communication protocol used to transfer data between a client and a server over the internet.
<br>
HTTPS is the secure version of HTTP. It uses TLS encryption to protect data transferred between the client and the server.

---

#### 2. What is a client-server architecture?

Client-Server Architecture is a software architecture in which an application is divided into two main components: client and server. The client sends requests to the server, and the server processes those requests and sends responses back to the client.

---

#### 3. What is a request and a response? What does each contain?

An HTTP request is a message sent by the client to the server to access a resource or perform an operation. It contains a request line, headers, and an optional body.
<br>
An HTTP response is a message sent by the server to the client after processing the request. It contains a status line, headers, and an optional body

---

#### 4. what are HTTP methods. When do you use each?

HTTP methods define the type of operation a client wants to perform on a server. GET is used to retrieve data, POST to create resources, PUT to replace a resource, PATCH to partially update it, and DELETE to remove a resource.

---

#### 5. GET vs POST?

GET is used to retrieve data from the server, whereas POST is used to send data and create a new resource on the server.

---

#### 6. What is idempotency? Which methods are idempotent?

Idempotency means if we send the same request multiple times, the result on the server remains the same as sending it once. GET, PUT, DELETE, are idempotent methods. POST is generally not idempotent.

---

#### 7. What are HTTP status codes?

HTTP status codes are three-digit numbers returned by the server to indicate whether an HTTP request was successful or failed. They are divided into five categories: 1xx for informational, 2xx for success, 3xx for redirection, 4xx for client errors, and 5xx for server errors.

---

#### 8. 401 vs 403?

401 means the user is not properly authenticated, for example, when the token is missing or invalid. 403 means the user is authenticated but does not have permission to access the requested resource.

---

#### 9. What is REST API?

REST stands for Representational State Transfer API. It allows a client to communicate with a server over HTTP using standard methods like GET, POST, PUT, and DELETE.

---

#### 10. What is an API?

An API is a way that allows two applications to communicate with each other.
