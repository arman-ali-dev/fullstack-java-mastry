Exception handling is one of the most important parts of building professional REST APIs. A good API should not only return data; it should also clearly communicate what happened during the request.

<br>
<br>

A professional API response answers questions like:
1. Was the request successful?
2. Did the client send something wrong?
3. Was the requested resource missing?
4. Did something break inside the server?
5. Can the frontend understand the response and show the right message?

This is where HTTP status codes, ResponseEntity, custom exceptions, and global exception handling become important

- An API response usually has three main parts:
1. Status Code - The status code tells the meaning of the response at the HTTP level
2. Headers - Headers are not the main data, but they give extra information about the response.
3. Body - The body contains the actual data or error message.

---

- New resource created:  201 Created
- Resource not found:  404 Not Found
- Validation failed: 400 Bad Request
- Duplicate data:  409 Conflict
- Unexpected backend issue:  500 Internal Server Error

- 1xx: Informational
- 2xx: Success
- 3xx: Redirection
- 4xx: Client error
- 5xx: Server error

Spring Boot provides a default /error mapping. When an unhandled error occurs, Spring Boot internally forwards the request to /error .
- For browser clients, Spring Boot may show the Whitelabel Error Page.
- For API clients such as Postman, it usually returns a JSON error response.

- For learning or local testing, you can temporarily add this in application.properties : spring.web.error.include-message=always

---

With ResponseEntity , we control the response explicitly: This means:
- Response body: student
- Status code: 200 OK

Without ResponseEntity : Spring decides the response status.
<br>
<br>
With ResponseEntity : We explicitly decide the response status, headers, and body.

---

- Controller Receive request and return success response
- Service Run business logic and throw meaningful exceptions
- Global Exception Handler Convert exceptions into proper HTTP responses

When an API fails, two things happen:
1. Some exception occurs inside backend code.
2. That exception must be converted into a proper HTTP response.

---

Global exception handling means:
- Instead of handling errors separately inside every controller, we create one central place that catches exceptions and converts them into proper API resonses.

```java
Client
↓
Controller
↓
Service
↓
Exception thrown
↓
GlobalExceptionHandler catches exception
↓
HTTP response returned
```

@ControllerAdvice is a Spring annotation used to define common behavior for multiple controllers.

- In REST APIs, we usually return JSON responses. For that, Spring provides: @RestControllerAdvice
- @RestControllerAdvice is a shortcut for: @ControllerAdvice + @ResponseBody
- @ExceptionHandler is used on a method to handle a specific exception type.

---

Why Create Custom Exceptions?
- Java already has generic exceptions like:
1. RuntimeException
2. IllegalArgumentException
3. NoSuchElementException

But these do not clearly describe the business meaning.

- Custom exceptions make service-layer code more meaningful.
