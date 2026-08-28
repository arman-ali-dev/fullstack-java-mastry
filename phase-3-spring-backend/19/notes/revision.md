Filters allow us to execute common HTTP-level logic before a request reaches the Spring MVC controller and after the request has been processed.

- In a real application, the request does not directly reach the controller. Before executing the controller, the application may need to perform common tasks such as:
- Logging the request URL
- Checking an authentication token
- Blocking suspicious requests
- Measuring request execution time
- Adding common response headers
- Applying character encoding
- Creating a request or trace ID

Writing this logic inside every controller method leads to repeated code. 
- A Filter provides a common place for request-level logic that must apply to multiple endpoints.

---

Tasks that apply across many parts of an application are known as cross-cutting concerns.
- Request logging
- Authentication and authorization checks
- Security checks
- Character encoding
- Request timing
- Audit tracking
- Rate limiting
- Correlation or trace IDs

These concerns are not part of the business logic of a particular controller, service, or repository.
- Filters belong to the Servlet layer. They execute before the request reaches Spring MVC’s DispatcherServlet.
- Because a Filter executes before DispatcherServlet , it can reject a request before Spring MVC performs controller mapping.
- The call to chain.doFilter(...) passes control to the next component in the request-processing chain.

A Filter can:
- Inspect request headers, cookies, query parameters, and other metadata
- Add response headers
- Log request and response details
- Measure total request duration
- Validate authentication information
- Wrap the request or response
- Allow the request to continue
- Stop the request immediately

Filters are not originally a Spring feature. They are defined by the Servlet specification.

---

The Filter lifecycle contains three methods:
1. init() Called when the Filter is initialized
2. doFilter() Called when a matching request passes through the Filter
3. destroy() Called when the Filter is removed or the application shuts down

The doFilter contains three parameters
1. ServletRequest request: The request object provides general request information.
2. ServletResponse response: The response object represents the outgoing response.
3. FilterChain represents everything that remains after the current Filter
