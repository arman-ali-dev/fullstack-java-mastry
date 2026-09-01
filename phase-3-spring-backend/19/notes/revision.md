# Filters and Interceptors - Complete Notes

## 1. Why Filters Exist

Filters allow us to execute common HTTP-level logic **before** a request reaches the Spring MVC controller and **after** the request has been processed.

In a real application, the request does not directly reach the controller. Before executing the controller, the application may need to perform common tasks such as:
- Logging the request URL
- Checking an authentication token
- Blocking suspicious requests
- Measuring request execution time
- Adding common response headers
- Applying character encoding
- Creating a request or trace ID

Writing this logic inside every controller method leads to repeated code.

**A Filter provides a common place for request-level logic that must apply to multiple endpoints.**

---

## 2. Cross-Cutting Concerns

Tasks that apply across many parts of an application are known as **cross-cutting concerns**.

Examples:
- Request logging
- Authentication and authorization checks
- Security checks
- Character encoding
- Request timing
- Audit tracking
- Rate limiting
- Correlation or trace IDs

These concerns are not part of the business logic of a particular controller, service, or repository.

---

## 3. Where Filters Sit

Filters belong to the **Servlet layer**. They execute **before** the request reaches Spring MVC's `DispatcherServlet`.

Because a Filter executes before `DispatcherServlet`, it can **reject a request before Spring MVC even performs controller mapping** — meaning a bad request can be stopped early, without Spring MVC's machinery even getting involved.

The call to `chain.doFilter(...)` passes control to the next component in the request-processing chain.

### What a Filter Can Do
- Inspect request headers, cookies, query parameters, and other metadata
- Add response headers
- Log request and response details
- Measure total request duration
- Validate authentication information
- Wrap the request or response
- Allow the request to continue
- Stop the request immediately

### Filters Are Not a Spring Feature

Filters are not originally a Spring feature. They are defined by the **Servlet specification** — meaning Filters work in any Java web app, with or without Spring, because they're part of the underlying Servlet API (same family as `HttpServletRequest`/`HttpServletResponse` from earlier notes).

---

## 4. Filter Lifecycle

The Filter lifecycle contains three methods:

| Method | When it's called |
|---|---|
| `init()` | Called when the Filter is initialized |
| `doFilter()` | Called when a matching request passes through the Filter |
| `destroy()` | Called when the Filter is removed or the application shuts down |

### doFilter() Parameters

1. **`ServletRequest request`** — the request object, provides general request information
2. **`ServletResponse response`** — the response object, represents the outgoing response
3. **`FilterChain chain`** — represents everything that remains after the current Filter

### Why ServletRequest instead of HttpServletRequest? *(added — was missing)*
The Servlet spec is designed to be protocol-generic — `ServletRequest`/`ServletResponse` aren't HTTP-specific by default. Since almost every real application is HTTP-based, you typically cast them inside `doFilter()`:
```java
HttpServletRequest httpRequest = (HttpServletRequest) request;
HttpServletResponse httpResponse = (HttpServletResponse) response;
```
This gives you access to HTTP-specific methods like `getHeader()`, `getMethod()`, `setStatus()`, etc.

### A complete Filter example *(added — was missing)*
```java
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        long start = System.currentTimeMillis();

        System.out.println("Incoming request: " + httpRequest.getRequestURI());

        chain.doFilter(request, response); // pass control to the next filter/servlet

        long duration = System.currentTimeMillis() - start;
        System.out.println("Request took: " + duration + "ms");
    }
}
```

### Registering a Filter in Spring Boot *(added — was missing)*
Simply marking the class `@Component` is often enough for Spring Boot to auto-register it for all URLs. For more control (specific URL patterns, custom ordering), use `FilterRegistrationBean`:
```java
@Bean
public FilterRegistrationBean<LoggingFilter> loggingFilter() {
    FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new LoggingFilter());
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1);
    return registrationBean;
}
```

### Multiple Filters — the Filter Chain *(added — was missing)*
An application can have **multiple filters**, each handling a different concern, chained together:
```
Request → Filter 1 (logging) → Filter 2 (auth check) → Filter 3 (encoding) → DispatcherServlet → Controller
```
Each filter calls `chain.doFilter()` to pass control onward. If any filter doesn't call it (e.g. because it rejected the request), the chain stops there and the request never reaches the controller. `@Order` (or the `order` field in `FilterRegistrationBean`) controls the sequence.

---

## 5. Interceptors *(added — full topic was missing, as requested)*

While Filters are a **Servlet-level** concept, **Interceptors** are a **Spring MVC-level** concept — they sit *inside* Spring MVC's own request handling, not before it.

### Where Interceptors Sit in the Flow

```
Request → Filters → DispatcherServlet → HandlerMapping 
       → Interceptor (preHandle) → Controller method 
       → Interceptor (postHandle) → View/Response rendering 
       → Interceptor (afterCompletion) → Response sent
```

Because Interceptors run *after* `DispatcherServlet` has already figured out which controller method will handle the request, they have access to Spring-specific context that Filters don't — like which exact `HandlerMethod` is about to run.

### The HandlerInterceptor Interface

An Interceptor is created by implementing `HandlerInterceptor`, which has three methods:

| Method | When it runs | Typical use |
|---|---|---|
| `preHandle()` | Before the controller method runs | Auth checks, logging, can stop the request by returning `false` |
| `postHandle()` | After the controller method runs, before the view is rendered | Modifying the model/response before rendering (mostly relevant for `@Controller`, less so for `@RestController`) |
| `afterCompletion()` | After the complete request has finished (including view rendering) | Cleanup, final logging, even runs if an exception occurred |

### Example Interceptor

```java
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false; // stops the request here — controller is never called
        }
        return true; // allow the request to continue
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                  Object handler, Exception ex) {
        System.out.println("Request completed for: " + request.getRequestURI());
    }
}
```

### Registering an Interceptor

Interceptors must be registered explicitly using `WebMvcConfigurer`:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/public/**");
    }
}
```

Notice interceptors support fine-grained path inclusion/exclusion directly through this registration — no need for a separate filter just to skip certain endpoints.

---

## 6. Filter vs Interceptor — Key Differences *(added — the most important comparison, entirely missing)*

| | Filter | Interceptor |
|---|---|---|
| **Defined by** | Servlet specification | Spring MVC |
| **Works without Spring?** | Yes | No — Spring-only |
| **Runs relative to DispatcherServlet** | Before (and after) DispatcherServlet | Inside DispatcherServlet's processing, around the controller call |
| **Access to handler method info** | No — generic request/response only | Yes — knows exactly which controller method will run |
| **Best for** | Generic, protocol-level concerns (encoding, CORS, raw request logging) | Spring-aware concerns (auth tied to a specific controller/annotation, model manipulation) |
| **Registration** | `FilterRegistrationBean` or `@Component` (auto) | `WebMvcConfigurer.addInterceptors()` |
| **Key methods** | `init()`, `doFilter()`, `destroy()` | `preHandle()`, `postHandle()`, `afterCompletion()` |

### Simple rule of thumb *(added)*
- Use a **Filter** for something that should apply broadly and doesn't need to know anything about Spring MVC or which controller is being called (e.g. logging every raw request, setting character encoding, basic CORS headers).
- Use an **Interceptor** when the logic needs Spring MVC context — like checking annotations on the target controller method, or when you specifically want fine-grained path include/exclude control tied to Spring's own routing.
