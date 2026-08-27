- Controller: Handles HTTP requests and sends HTTP responses
- Service: Contains business logic
- Repository: Communicates with the database
- Entity Represents a database table

A clean backend application should keep these responsibilities separate.

---

Because of the @Entity annotation, JPA/Hibernate understands that this class should be mapped to a database table.

- In a beginner CRUD project, we often pass the Student entity everywhere: Controller → Service → Repository → Database
- Entity Has a Specific Purpose
- An Entity is created mainly for database mapping.

---

- Problem 1: Client Can Send Fields They Should Not Send
- Problem 2: Client Can Modify Sensitive/Internal Data
- Problem 3: Response May Leak Unwanted Data
- Problem 4: Request and Response Usually Need Different Structures
- Problem 5: Database Changes Can Break the API Contract

A class should ideally have one clear responsibility But when we pass Entity everywhere, the Entity becomes responsible for too many things:

1. Database mapping
2. Request handling
3. Response handling
4. Business data transfer
5. External API contract

---

DTO stands for: Data Transfer Object

- A DTO is a simple object used to transfer data between layers or between client and server. DTO is used to carry only the data required for a particular request or response.
