Application security means protecting the application’s valuable resources and operations from unauthorized access, modification, destruction, disclosure, or misuse.

- Security is a collection of controls.
1. Authentication
2. Authorization
3. Input validation
4. Session protection
5. Password protection
6. CSRF protection
7. CORS policies
8. Secure communication
9. Rate limiting
10. Audit logging
11. Error handling
12. Secret management

Spring Security primarily helps with:
1. Authentication
2. Authorization
3. Security-context management
4. Session security
5. Protection against several common web attacks
6. Integration with protocols

---

Before deciding how to secure an application, we need to understand three concepts:
1. Assets - An asset is anything valuable that should be protected.
2. Threats - A threat is something that could harm an asset
  - Vulnerability : A vulnerability is a weakness that makes an attack possible.
  - Threat + Vulnerability → Possible Attack
3. Trust boundaries - A trust boundary is a point where information moves from one trust level to another. 

```java
Consider:
Browser
 ↓
Internet
 ↓
Spring Boot application
 ↓
Database
```
