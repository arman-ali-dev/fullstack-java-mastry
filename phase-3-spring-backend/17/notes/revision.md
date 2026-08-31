# Exception Handling in REST APIs - Complete Notes

## 1. Why Exception Handling Matters

Exception handling is one of the most important parts of building professional REST APIs. A good API should not only return data — it should also clearly communicate what happened during the request.

A professional API response answers questions like:
1. Was the request successful?
2. Did the client send something wrong?
3. Was the requested resource missing?
4. Did something break inside the server?
5. Can the frontend understand the response and show the right message?

This is where **HTTP status codes**, **ResponseEntity**, **custom exceptions**, and **global exception handling** become important.

---

## 2. Parts of an API Response

An API response usually has three main parts:

1. **Status Code** — tells the meaning of the response at the HTTP level
2. **Headers** — not the main data, but give extra information about the response
3. **Body** — contains the actual data or error message

---

## 3. Common Status Codes for Scenarios

| Scenario | Status Code |
|---|---|
| New resource created | `201 Created` |
| Resource not found | `404 Not Found` |
| Validation failed | `400 Bad Request` |
| Duplicate data | `409 Conflict` |
| Unexpected backend issue | `500 Internal Server Error` |

### Status Code Categories

| Range | Meaning |
|---|---|
| 1xx | Informational |
| 2xx | Success |
| 3xx | Redirection |
| 4xx | Client error |
| 5xx | Server error |

---

## 4. Spring Boot's Default Error Handling

Spring Boot provides a default `/error` mapping. When an unhandled error occurs, Spring Boot internally forwards the request to `/error`.

- For **browser clients**, Spring Boot may show the **Whitelabel Error Page**.
- For **API clients** such as Postman, it usually returns a **JSON error response**.
- For learning or local testing, you can temporarily add this in `application.properties`:
```properties
spring.web.error.include-message=always
```

This default handling is generic and not very informative — this is exactly why we build our own exception handling on top of it.

---

## 5. ResponseEntity

With `ResponseEntity`, we control the response explicitly.

### Example *(added — was missing)*
```java
@GetMapping("/{id}")
public ResponseEntity<Student> getStudent(@PathVariable int id) {
    Student student = studentService.findById(id);
    return new ResponseEntity<>(student, HttpStatus.OK);
}
```

This means:
- Response body: `student`
- Status code: `200 OK`

**Without `ResponseEntity`**: Spring decides the response status (usually defaults to `200 OK` for any successful return).

**With `ResponseEntity`**: We explicitly decide the response status, headers, and body — giving full control instead of relying on Spring's default guess.

### Another common form — using a static builder *(added)*
```java
return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
// or
return ResponseEntity.ok(student);
```

---

## 6. Layered Responsibility for Errors

- **Controller**: Receive request and return success response
- **Service**: Run business logic and throw meaningful exceptions
- **Global Exception Handler**: Convert exceptions into proper HTTP responses

When an API fails, two things happen:
1. Some exception occurs inside backend code.
2. That exception must be converted into a proper HTTP response.

---

## 7. Global Exception Handling

Global exception handling means: instead of handling errors separately inside every controller, we create **one central place** that catches exceptions and converts them into proper API responses.

```
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

### @ControllerAdvice
A Spring annotation used to define common behavior for multiple controllers.

### @RestControllerAdvice
In REST APIs, we usually return JSON responses. For that, Spring provides: `@RestControllerAdvice`

`@RestControllerAdvice` is a shortcut for: `@ControllerAdvice` + `@ResponseBody`

### @ExceptionHandler
Used on a method to handle a **specific exception type**.

### Full working example *(added — was missing)*
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(StudentNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Something went wrong",
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### Why a generic `Exception.class` handler matters *(added)*
A `@RestControllerAdvice` class can have **multiple `@ExceptionHandler` methods**, each targeting a specific exception. It's good practice to also include one generic `Exception.class` handler as a catch-all — so any unexpected exception you didn't anticipate still returns a clean JSON error instead of leaking Spring Boot's default Whitelabel error page or raw stack trace to the client.

### A standard ErrorResponse DTO *(added — a common real-world pattern)*
Instead of returning just a plain string message, most real APIs return a structured error object:
```java
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    // constructor, getters, setters
}
```
This gives the frontend consistent, predictable fields to work with for every error, no matter which exception triggered it.

---

## 8. Custom Exceptions

### Why Create Custom Exceptions?

Java already has generic exceptions like:
1. `RuntimeException`
2. `IllegalArgumentException`
3. `NoSuchElementException`

But these do not clearly describe the **business meaning**. Custom exceptions make service-layer code more meaningful.

### Example *(added — was missing)*
```java
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
```

Usage in the Service layer:
```java
public Student findById(int id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
}
```

This reads far more clearly than throwing a generic `RuntimeException("error")` — anyone reading the Service code immediately understands *what* went wrong and *why*.

### Why extend RuntimeException, not Exception? *(added)*
`RuntimeException` is **unchecked** — it doesn't force every calling method to declare `throws` or wrap it in `try-catch`. This is preferred for custom business exceptions in Spring apps, since it keeps Service/Controller code clean, while the `GlobalExceptionHandler` still catches it centrally.

### @ResponseStatus shortcut *(added)*
For simple cases, you can skip writing a dedicated `@ExceptionHandler` and just annotate the custom exception itself:
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
```
Spring will automatically return that status code whenever this exception is thrown — useful for simple cases, though a `GlobalExceptionHandler` gives you more control over the exact response body shape.

---

## 9. Handling Validation Errors *(added — tied to "Validation failed: 400 Bad Request" mentioned earlier)*

When using `@Valid` on a request body to validate incoming data:
```java
@PostMapping
public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRequestDTO dto) {
    // ...
}
```

If validation fails, Spring throws `MethodArgumentNotValidException` — which you can also catch in your `GlobalExceptionHandler`:
```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldError().getDefaultMessage();
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
}
```
z
