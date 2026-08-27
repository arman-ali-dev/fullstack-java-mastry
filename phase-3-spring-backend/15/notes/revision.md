Spring Web MVC, commonly called Spring MVC, is Spring’s web framework used to build:
1. Web applications
2. REST APIs
3. Backend services that handle HTTP requests


Spring MVC is built on top of the Servlet API 
- the application still works through servlet-based request handling internally

Spring Boot does not replace Spring MVC. Spring Boot simply makes Spring MVC easier to use.

- But internally, someone still has to receive the HTTP request from Tomcat, understand the URL and HTTP method, and then call the correct controller method.
- That internal web system is Spring MVC.
- The central servlet inside Spring MVC is called: DispatcherServlet

- DispatcherServlet is the front controller of Spring MVC.
- All requests first come to DispatcherServlet. DispatcherServlet then forwards the request to the correct controller method.
- It dispatches the request to the correct handler.
- DispatcherServlet is Still a Servlet
- Tomcat knows how to call servlets. So when a request comes, Tomcat sends the request to DispatcherServlet
- It does not do everything by itself. It takes help from multiple Spring MVC components

1. DispatcherServlet - Central servlet that receives requests
2. HandlerMapping - Finds which controller method should handle the request
3. HandlerAdapter - Actually invokes the selected controller method
4. Jackson - Converts JSON to Java object and Java object to JSON

---

1. @Repository - This class belongs to the repository layer. Please create and manage its object inside the IoC container.
2. @Service - This class belongs to the service layer. Please create and manage its object inside the IoC container. The service layer contains business logic.
3. @RestController - This class will handle REST API requests. The return value of its methods should be written directly in the HTTP response body. @Controller + @ResponseBody
4. @RequestMapping - This defines the base URL for this controller.
5. @Controller is traditionally used when we want to return web pages.
6. @RestController is used for REST APIs.
