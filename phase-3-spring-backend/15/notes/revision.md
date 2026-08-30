# Spring MVC: DispatcherServlet and Core Annotations - Complete Notes

## 1. What Is Spring MVC

**Spring Web MVC**, commonly called **Spring MVC**, is Spring's web framework used to build:
1. Web applications
2. REST APIs
3. Backend services that handle HTTP requests

### What does "MVC" stand for? *(added)*
MVC = **Model-View-Controller** — a design pattern that separates an application into three parts:
| Part | Role |
|---|---|
| **Model** | The data/business logic |
| **View** | What gets shown to the user (e.g. an HTML page) |
| **Controller** | Receives requests, coordinates between Model and View |

For REST APIs (the most common case today), there's usually no "View" in the traditional page sense — the Controller returns data (JSON) directly instead.

---

## 2. Spring MVC Is Built on the Servlet API

Spring MVC is built on top of the **Servlet API** — the application still works through servlet-based request handling internally.

**Spring Boot does not replace Spring MVC. Spring Boot simply makes Spring MVC easier to use.**

But internally, someone still has to:
- Receive the HTTP request from Tomcat
- Understand the URL and HTTP method
- Call the correct controller method

**That internal web system is Spring MVC.**

---

## 3. DispatcherServlet — The Front Controller

The central servlet inside Spring MVC is called: **`DispatcherServlet`**

- `DispatcherServlet` is the **front controller** of Spring MVC.
- All requests first come to `DispatcherServlet`. `DispatcherServlet` then forwards the request to the correct controller method.
- It dispatches the request to the correct handler.

### DispatcherServlet Is Still a Servlet

Tomcat knows how to call servlets. So when a request comes, Tomcat sends the request to `DispatcherServlet` — just like any other Servlet, following the same lifecycle (`init()`, `service()`, etc.) discussed earlier.

### How does DispatcherServlet get registered in Spring Boot? *(added — was missing)*
In traditional Spring MVC (without Spring Boot), you had to manually register `DispatcherServlet` in `web.xml`. In Spring Boot, this is handled automatically by **auto-configuration** — as soon as `spring-boot-starter-web` is on the classpath, Spring Boot auto-configures and registers `DispatcherServlet` for you, mapped to handle all requests (`/`) by default. This is a good example of the `@EnableAutoConfiguration` mechanism discussed earlier.

---

## 4. DispatcherServlet Doesn't Work Alone

It does not do everything by itself. It takes help from multiple Spring MVC components:

1. **`DispatcherServlet`** — Central servlet that receives requests
2. **`HandlerMapping`** — Finds which controller method should handle the request
3. **`HandlerAdapter`** — Actually invokes the selected controller method
4. **Jackson** — Converts JSON to Java object and Java object to JSON

### ViewResolver *(added — missing component)*
For traditional `@Controller` classes that return web pages (not REST APIs), there's a fifth component: **`ViewResolver`**. It takes the logical view name a controller returns (e.g. `"home"`) and resolves it to an actual page/template (e.g. `home.html`) to render and send back. REST APIs (`@RestController`) skip this step entirely, since they return data directly.

### The full request flow, step by step *(added — components were listed but flow wasn't shown)*

```
1. Browser sends HTTP request
        ↓
2. Tomcat receives it, passes it to DispatcherServlet
        ↓
3. DispatcherServlet asks HandlerMapping:
   "Which controller method handles this URL?"
        ↓
4. HandlerMapping returns the matching controller method
        ↓
5. DispatcherServlet asks HandlerAdapter to actually call that method
        ↓
6. Controller method runs (business logic, calls Service/Repository)
        ↓
7. Controller returns data (or a view name)
        ↓
8a. REST API: Jackson converts the returned Java object → JSON
8b. Web page: ViewResolver resolves the view name → renders HTML
        ↓
9. DispatcherServlet sends the final response back through Tomcat to the browser
```

---

## 5. Core Stereotype Annotations

### @Repository
This class belongs to the **repository layer**. Please create and manage its object inside the IoC container. (Typically handles database access.)

### @Service
This class belongs to the **service layer**. Please create and manage its object inside the IoC container. The service layer contains **business logic**.

### @RestController
This class will handle REST API requests. The return value of its methods should be written directly in the HTTP response body.

`@RestController` = `@Controller` + `@ResponseBody`

### @RequestMapping
This defines the **base URL** for this controller.

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    // methods here handle URLs starting with /api/orders
}
```

### @Controller vs @RestController
- **`@Controller`** is traditionally used when we want to return web pages.
- **`@RestController`** is used for REST APIs.

---

## 6. Handling Request Data *(added — missing entirely)*

Inside a controller, you also need ways to read incoming data:

| Annotation | Purpose |
|---|---|
| `@PathVariable` | Reads a value from the URL path itself (e.g. `/orders/{id}`) |
| `@RequestParam` | Reads a value from query parameters (e.g. `?status=active`) |
| `@RequestBody` | Reads and converts the JSON request body into a Java object |
| `@ResponseBody` | Marks that a method's return value should go directly into the response body (already included automatically in `@RestController`) |

### Example — a complete controller *(added — was missing)*
```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable int id) {
        return orderService.findById(id);
    }

    @GetMapping
    public List<Order> getOrders(@RequestParam String status) {
        return orderService.findByStatus(status);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.save(order);
    }
}
```

Here you can see the typical layering in action: **Controller → Service → Repository**, each marked with its own stereotype annotation (`@RestController`, `@Service`, `@Repository`), and Spring wiring them together automatically via constructor injection.
