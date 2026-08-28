Spring MVC interceptors allow us to execute common logic around controller execution without placing the same code inside every controller method.
- Suppose every request needs some common operations:
  - Log the requested URL
  - Record execution time
  - Check authentication or authorization
  - Read common request information
  - Identify the controller method being executed
  - Add request attributes
 
Adding this logic directly to every controller method creates several problems

<br>
<br>

**Definition: A Spring MVC interceptor is a component that executes before a handler, after a handler, and after the complete MVC request-processing cycle.**

```java
Request
↓
Interceptor
↓
Controller
↓
Interceptor
↓
Response
```
