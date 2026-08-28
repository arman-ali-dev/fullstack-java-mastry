A production application may also need to:
- Log when a method begins
- Log whether it succeeds or fails
- Measure execution time
- Check whether the caller has permission
- Record an audit event
- Start, commit, or roll back a transaction
- Collect application metrics
- Retry temporary failures
- Cache a result
- Mask sensitive information
These requirements are important, but they are not part of the core process of creating a student. If all of them are added directly to createStudent() , the method becomes difficult to read because most of its code no longer represents student-related business logic.

---

- Business logic represents the rules and operations of the application’s domain
- Supporting logic helps the application operate safely, reliably, securely, and observably. Examples:
1. Logging
2. Security checks
3. Transaction management
4. Auditing
5. Performance monitoring
6. Caching

---

- A concern is a responsibility or area of interest within an application. Examples:
    - Student management is one concern.
    - Payment processing is another concern.
    - Logging is another concern.
    - Security is another concern.
    - Transaction management is another concern.
 
Core business concerns normally belong to a particular feature or module.
- Student concern → StudentService
- Order concern → OrderService
- Payment concern → PaymentService

These concerns are organized vertically. Each feature has its own classes and methods.

- Cross-Cutting Concerns: Some concerns are required across many business modules

These concerns cut horizontally across otherwise unrelated features. They are therefore called cross-cutting concerns.

- A concern becomes cross-cutting when the same kind of behaviour must be applied in many different parts of the application.
