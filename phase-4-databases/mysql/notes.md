# Database & SQL Notes for Java Full-Stack Developers

These notes cover only what a Java Full-Stack Developer actually needs — not DBA-level depth. Every topic has: what it is, why it matters, an example, and how important it is for interviews and daily project work.

Each topic is marked with one of these labels so you know how much effort to put in:

- **MUST KNOW WELL** — you will use this often and interviewers expect solid understanding.
- **BASIC AWARENESS IS ENOUGH** — know what it is and why it exists, but you don't need deep mastery.

---

## 1. Database, Table, Row, Column — [MUST KNOW WELL]

A **database** is a place where all related tables live. Example: `company_db` holds all tables for one project.

A **table** stores actual data in rows and columns. Example: an `employees` table stores employee records.

- **Row** = one single record (one employee).
- **Column** = one piece of information about that record (name, salary, email).

**Why it matters:** Every backend application (including Spring Boot apps) stores its data in tables like this. Understanding rows/columns is the foundation for everything else — entity classes in your code map directly to tables, and object fields map to columns.

```sql
CREATE DATABASE company_db;
USE company_db;

CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100),
    salary INT
);

SHOW TABLES;
DESCRIBE employees;
```

---

## 2. Data Types — [MUST KNOW WELL]

Choosing the right data type keeps your data correct and your table efficient.

The ones you will actually use in almost every project:

| Type | Use for | Example |
|---|---|---|
| `INT` | whole numbers (id, age, quantity) | `age INT` |
| `BIGINT` | very large whole numbers (rarely needed unless huge IDs) | `views BIGINT` |
| `VARCHAR(n)` | text with a known max length (name, email) | `name VARCHAR(100)` |
| `TEXT` | large text (description, comments, blog content) | `bio TEXT` |
| `DECIMAL(p,s)` | money and exact numbers | `price DECIMAL(10,2)` |
| `DATE` | just a date | `dob DATE` |
| `DATETIME` / `TIMESTAMP` | date + time | `created_at TIMESTAMP` |
| `BOOLEAN` | true/false flags | `is_active BOOLEAN` |

**Why it matters:** Wrong data type = bugs or wasted storage. The most common interview question here is: **"Why use DECIMAL instead of FLOAT/DOUBLE for money?"**
Answer: FLOAT/DOUBLE store approximate values (rounding errors happen, like 0.1 + 0.2 not exactly equalling 0.3). DECIMAL stores the exact value, which is required for money calculations.

`TIMESTAMP` vs `DATETIME` — basic awareness is enough: `TIMESTAMP` is commonly used for `created_at` / `updated_at` fields because it can auto-update, and it stores in a timezone-aware way internally. `DATETIME` is used when you don't need that behavior.

---

## 3. Keys — [MUST KNOW WELL]

Keys are how tables uniquely identify rows and connect to each other. This is one of the most interview-relevant topics.

**Primary Key (PK):** uniquely identifies each row in a table. Cannot be NULL, must be unique.

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100)
);
```

**Foreign Key (FK):** a column in one table that points to the Primary Key of another table. This is how you connect two tables (relationships).

```sql
CREATE TABLE departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);

CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100),
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);
```

**Why it matters:** This is exactly how relationships between entities work in real applications (one department has many employees). Understanding PK/FK is required before understanding joins.

**Composite Key:** a Primary Key made of two or more columns together, used when no single column is unique on its own. Common in a "linking table" for many-to-many relationships (example: a student can join many courses, and a course can have many students).

```sql
CREATE TABLE student_courses (
    student_id INT,
    course_id INT,
    PRIMARY KEY (student_id, course_id)
);
```

**Candidate Key, Surrogate Key, Alternate Key — [BASIC AWARENESS IS ENOUGH]**
- Candidate Key = any column that COULD have been the primary key (unique + not null), like `email` or `emp_id`.
- Surrogate Key = an artificial id (like auto-increment `id`) with no real business meaning, used as PK because it's simple and never changes. This is why most tables use a plain `id` column instead of using email as PK.
- Alternate Key = a candidate key that was not chosen as the primary key.

**Interview note:** Explaining PK vs FK vs Composite Key with a simple example (like the department-employee one above) is a very common interview question.

---

## 4. Constraints — [MUST KNOW WELL]

Constraints are rules the database enforces automatically, so bad data can never get saved — even if the application code has a bug.

```sql
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(150) NOT NULL UNIQUE,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    CHECK (balance >= 0)
);
```

- `NOT NULL` — this column must always have a value.
- `UNIQUE` — no two rows can have the same value in this column.
- `DEFAULT` — auto-fills a value if none is given.
- `CHECK` — value must satisfy a condition (example: balance can never go negative).

**Why it matters:** In a Java application, you validate data in code too — but constraints act as a final safety net directly at the database level. Interviewers like asking "why not just validate everything in code?" — answer: multiple applications or even manual database access could bypass your code, but the database constraint always applies.

**Foreign Key actions — [BASIC AWARENESS IS ENOUGH]:** 
`ON DELETE CASCADE` (deleting parent auto-deletes children), `ON DELETE SET NULL` (child's FK becomes NULL), `ON DELETE RESTRICT` (blocks deletion if children exist, this is the default). You'll pick these when designing table relationships.

---

## 5. SQL Command Categories — [BASIC AWARENESS IS ENOUGH]

SQL commands are grouped into categories. You don't need to memorize the category names deeply, but knowing what falls where helps in interviews.

- **DDL (structure):** `CREATE`, `ALTER`, `DROP`, `TRUNCATE`
- **DML (data changes):** `INSERT`, `UPDATE`, `DELETE`
- **DQL (reading data):** `SELECT`
- **DCL (permissions):** `GRANT`, `REVOKE`
- **TCL (transactions):** `COMMIT`, `ROLLBACK`

**Interview note:** A common trick question is "Is TRUNCATE DDL or DML?" Answer: DDL — it removes all rows by rebuilding the table internally, which is why it's fast but cannot be filtered with WHERE and (in most cases) cannot be rolled back.

`DELETE` vs `TRUNCATE` vs `DROP` — this comparison comes up constantly:
- `DELETE FROM employees WHERE emp_id = 5;` — removes specific rows, can be rolled back, slower on large tables.
- `TRUNCATE TABLE employees;` — removes ALL rows at once, cannot filter, resets auto-increment.
- `DROP TABLE employees;` — removes the entire table (structure + data), gone completely.

---

## 6. Basic CRUD Queries — [MUST KNOW WELL]

These are the queries you will write every single day.

```sql
-- Insert
INSERT INTO employees (emp_name, salary, dept_id) VALUES ('Raj', 50000, 1);

-- Read
SELECT emp_name, salary FROM employees WHERE dept_id = 1;
SELECT * FROM employees ORDER BY salary DESC LIMIT 5;

-- Update
UPDATE employees SET salary = 55000 WHERE emp_id = 3;

-- Delete
DELETE FROM employees WHERE emp_id = 3;
```

**Why it matters:** Every Create/Read/Update/Delete feature in your application eventually turns into queries like these. Even when you use tools that generate SQL for you, understanding the actual query underneath is what interviewers test, and it's essential for debugging slow or wrong results.

---

## 7. Joins — [MUST KNOW WELL]

Joins combine data from two or more related tables into one result — this is used constantly, since real applications almost never have just one table.

```sql
-- INNER JOIN: only rows that match in both tables
SELECT e.emp_name, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id;

-- LEFT JOIN: all rows from left table, matched data from right table (NULL if no match)
SELECT e.emp_name, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.dept_id;
```

**Why it matters:** When you fetch a list of employees along with their department names, this is exactly what happens behind the scenes. Understanding joins also helps you understand and debug slow queries related to fetching connected data.

- **INNER JOIN** — most common, only matching rows.
- **LEFT JOIN** — very common, use when you want ALL rows from one table even if there's no match in the other (example: all employees, even those with no department yet).
- **RIGHT JOIN** — same idea as LEFT JOIN but reversed; rarely used in practice since you can just rewrite it as a LEFT JOIN.
- **SELF JOIN** — a table joined with itself. Common example: employee-manager relationship, where manager is also stored in the same employees table.

```sql
SELECT emp.emp_name AS employee, mgr.emp_name AS manager
FROM employees emp
LEFT JOIN employees mgr ON emp.manager_id = mgr.emp_id;
```

**FULL OUTER JOIN and CROSS JOIN — [BASIC AWARENESS IS ENOUGH]:** MySQL doesn't support FULL OUTER JOIN directly (rare need anyway). CROSS JOIN combines every row with every row (used rarely, mainly for generating combinations).

**Interview note:** Be ready to explain INNER vs LEFT JOIN with a simple example, and to write a self-join for a manager-employee scenario — this is asked very often.

---

## 8. Aggregate Functions, GROUP BY, HAVING — [MUST KNOW WELL]

Used whenever you need summary data — counts, totals, averages — instead of individual rows.

```sql
SELECT COUNT(*) FROM employees;
SELECT AVG(salary) FROM employees;

-- Group by department
SELECT dept_id, COUNT(*) AS emp_count, AVG(salary) AS avg_salary
FROM employees
GROUP BY dept_id;

-- Filter groups (not rows) using HAVING
SELECT dept_id, COUNT(*) AS emp_count
FROM employees
GROUP BY dept_id
HAVING COUNT(*) > 5;
```

**Why it matters:** Dashboards, reports, and any "show total/average/count" feature use this. `COUNT`, `SUM`, `AVG`, `MIN`, `MAX` are the ones to know well.

**Interview note:** "WHERE vs HAVING" is asked almost every time. Answer: `WHERE` filters individual rows BEFORE grouping happens, and cannot use aggregate functions like COUNT(). `HAVING` filters AFTER grouping, and can use aggregate functions.

---

## 9. Subqueries — [BASIC AWARENESS IS ENOUGH, but know the pattern]

A subquery is a query written inside another query.

```sql
-- Find employees earning above the company average
SELECT emp_name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);
```

A **correlated subquery** refers to the outer query's row, so it effectively runs once per row:

```sql
-- Find employees earning above their OWN department's average
SELECT e.emp_name, e.salary
FROM employees e
WHERE e.salary > (
    SELECT AVG(e2.salary) FROM employees e2 WHERE e2.dept_id = e.dept_id
);
```

**Why it matters:** You'll run into this pattern for "compare against an average/max/min" type queries. You don't need to master deeply optimizing subqueries — just be able to read and write simple ones.

---

## 10. Set Operations (UNION) — [BASIC AWARENESS IS ENOUGH]

```sql
-- Combines results, removes duplicates
SELECT emp_name FROM employees WHERE dept_id = 1
UNION
SELECT emp_name FROM employees WHERE dept_id = 2;

-- Combines results, keeps duplicates (faster)
SELECT emp_name FROM employees WHERE dept_id = 1
UNION ALL
SELECT emp_name FROM employees WHERE dept_id = 2;
```

**Why it matters:** Not used every day, but occasionally useful for combining results from two separate queries. Just know `UNION` removes duplicates and `UNION ALL` doesn't (and is faster because it skips that extra step).

---

## 11. Normalization — [BASIC AWARENESS IS ENOUGH]

Normalization means designing tables so data isn't repeated unnecessarily, which avoids confusing/inconsistent data.

- **1NF:** each column holds a single value (no comma-separated lists in one cell).
- **2NF:** no partial dependency — relevant only when you have a composite key; every other column should depend on the WHOLE key.
- **3NF:** no column should depend on another non-key column (example: don't store `dept_name` inside the `employees` table if `dept_id` already tells you the department — that's a separate `departments` table's job).

**Why it matters:** This is exactly what you're doing whenever you design your entity/table relationships in a project — deciding what belongs in one table vs a separate linked table. You don't need to memorize BCNF or deep normal-form theory — just understand the general idea: don't repeat data, and split it into separate related tables.

**Interview note:** You may be asked to normalize a messy example table (like one with repeated department name in every employee row) — practice spotting and fixing that pattern.

---

## 12. Indexing — [MUST KNOW WELL, but only the practical part]

An index helps MySQL find rows faster, without scanning the entire table — same idea as an index at the back of a book.

```sql
CREATE INDEX idx_emp_name ON employees(emp_name);

-- Composite index on two columns
CREATE INDEX idx_dept_salary ON employees(dept_id, salary);
```

**Why it matters:** Slow queries in real applications are very often caused by a missing index. This is one of the most practical performance topics for a full-stack developer — you don't need deep internal index theory, but you should always add an index to columns you frequently search or filter by (like a `email` column used in login lookups, or a foreign key column used in joins).

Key practical rule to remember: for a composite index on `(dept_id, salary)`, MySQL can use it for searches on `dept_id` alone, or `dept_id + salary` together — but NOT for searching `salary` alone. This is called the "leftmost rule."

**When NOT to add an index:** on columns that are rarely searched, or that change very frequently (every index adds a small write cost too), or on very small tables (index isn't needed).

---

## 13. Transactions and ACID — [MUST KNOW WELL]

A transaction groups multiple SQL operations into one all-or-nothing unit.

```sql
START TRANSACTION;

UPDATE accounts SET balance = balance - 500 WHERE account_id = 1;
UPDATE accounts SET balance = balance + 500 WHERE account_id = 2;

COMMIT;   -- saves changes permanently
-- or
ROLLBACK; -- undoes everything since START TRANSACTION
```

**Why it matters:** Think of a money transfer between two accounts. If the first update (debit) succeeds but the second (credit) fails, you do NOT want the money to just disappear. A transaction makes sure either both updates happen, or neither does. This exact scenario is the standard example used to explain transactions in every interview.

**ACID — know what each word means, at a basic level:**
- **Atomicity** — all steps succeed together or fail together.
- **Consistency** — the database always ends up in a valid state (no broken rules).
- **Isolation** — one transaction shouldn't be disturbed by another happening at the same time.
- **Durability** — once committed, the change is permanently saved, even if the server crashes right after.

---

## 14. Isolation Levels and Locking — [BASIC AWARENESS IS ENOUGH]

When multiple transactions run at the same time, isolation levels control how much they can "see" of each other's in-progress changes.

You don't need to memorize deep internals — just know these exist, in increasing strictness:
`Read Uncommitted` → `Read Committed` → `Repeatable Read` (MySQL's default) → `Serializable`.

Stricter isolation = safer, but slower (more locking = less concurrency).

**Deadlock — basic idea:** happens when two transactions each hold a lock the other one needs, so both wait forever. MySQL automatically detects this and cancels (rolls back) one of them. In real projects, the fix is usually: keep transactions short, and always update tables/rows in the same consistent order across your application.

**Why basic awareness is enough:** As a full-stack developer, you rarely configure isolation levels manually — but interviewers do ask "what is MySQL's default isolation level" (answer: Repeatable Read) and "what is a deadlock" as standard questions.

---

## 15. Views — [BASIC AWARENESS IS ENOUGH]

A view is a saved SELECT query that behaves like a virtual table.

```sql
CREATE VIEW high_earners AS
SELECT emp_id, emp_name, salary FROM employees WHERE salary > 80000;

SELECT * FROM high_earners;
```

**Why it matters:** Useful for simplifying a complex/repeated query, or restricting what data a certain query exposes. Not something you'll create often, but you should know what it is and be able to read one if you see it in an existing project.

---

## 16. Window Functions — [MUST KNOW WELL for interviews, moderate use in projects]

Window functions calculate something across a group of rows, without collapsing rows the way GROUP BY does — every row stays visible, plus you get an extra calculated column.

```sql
-- Rank employees by salary within each department
SELECT emp_name, dept_id, salary,
       ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rank_in_dept
FROM employees;
```

Common ones:
- `ROW_NUMBER()` — gives each row a unique number (1,2,3,4...), even for ties.
- `RANK()` — same rank for ties, but skips the next number (1,2,2,4).
- `DENSE_RANK()` — same rank for ties, no skipping (1,2,2,3).
- `LAG()` / `LEAD()` — get the value from the previous/next row (useful for comparing a row to the one before/after it).

**Why it matters:** Very useful for things like "top 3 highest paid employees per department" or "show change from previous month." This is one of the most commonly asked SQL questions in interviews today, so it's worth practicing even though you may not use it daily.

---

## 17. Stored Procedures, Functions, Triggers — [BASIC AWARENESS IS ENOUGH]

- **Stored Procedure** — a saved block of SQL logic you can call, optionally with parameters.
- **Function** — similar, but must return one value and can be used inside a query.
- **Trigger** — code that runs automatically when a row is inserted/updated/deleted.

```sql
DELIMITER //
CREATE PROCEDURE GetEmployeesByDept(IN deptId INT)
BEGIN
    SELECT emp_name FROM employees WHERE dept_id = deptId;
END //
DELIMITER ;

CALL GetEmployeesByDept(3);
```

**Why basic awareness is enough:** In most modern full-stack projects, business logic is written in the application layer, not inside the database — so you won't write these often. But you should be able to explain what each one is, since interviewers ask about them, and you may encounter them in an existing/legacy project.

---

## 18. Query Performance Basics (EXPLAIN) — [BASIC AWARENESS IS ENOUGH]

`EXPLAIN` shows how MySQL plans to run your query — helpful for figuring out why a query is slow.

```sql
EXPLAIN SELECT emp_name FROM employees WHERE dept_id = 3;
```

The one thing worth remembering: if the output shows `type = ALL` and `key = NULL`, it means MySQL is scanning the entire table with no index — a common sign you need to add an index on that column.

**Why it matters:** When a feature in your app becomes slow, this is the first tool you reach for to understand why — very practical for real project debugging.

---

## 19. Connection Handling — [BASIC AWARENESS IS ENOUGH]

Opening a brand-new database connection for every single query is slow and wasteful, because setting up a connection has overhead (handshake, authentication). To avoid this, applications keep a small pool of ready-to-use, reusable connections open, instead of creating and closing one every time.

**Why it matters:** This is why real applications can handle many simultaneous database requests efficiently without constant connection overhead. You don't need deep tuning knowledge here — just understand the concept and why it exists.

---

## 20. Database Migrations (Schema Versioning) — [BASIC AWARENESS IS ENOUGH, but important concept]

As a project grows, your table structure changes over time (new columns, new tables). A migration tool tracks these schema changes as ordered, version-controlled files, so every environment (your laptop, a teammate's laptop, production) ends up with the exact same database structure, applied in the same order.

Example of how a migration file might look (plain SQL, versioned):
```sql
-- V1__create_employees_table.sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100)
);
```

**Why it matters:** Without this, schema changes get applied manually and inconsistently, causing "works on my machine" database problems. This is a very common real-project concept, and interviewers in full-stack roles often ask if you've used a migration tool.

---

## Topics intentionally left out (DBA-level, not needed for you)

To keep this focused, the following are NOT covered in depth here because they belong to database administration work, not full-stack development: replication setup (master-slave/master-master server configuration), partitioning strategies for huge tables, storage engine internals, and advanced lock-level tuning. If an interviewer asks, a one-line answer is enough: know that they exist and what problem they solve in general — deep configuration knowledge is a DBA's job, not a full-stack developer's.

---

## Quick Interview Revision List

- PK vs FK vs Composite Key — explain with a simple example.
- DECIMAL vs FLOAT for money — DECIMAL is exact, FLOAT/DOUBLE are approximate.
- DELETE vs TRUNCATE vs DROP.
- WHERE vs HAVING.
- INNER JOIN vs LEFT JOIN, with an example.
- What is normalization, in simple words (avoid repeating data).
- What is an index, and why it speeds up queries.
- ACID properties, with the money-transfer example for Atomicity.
- MySQL's default isolation level: Repeatable Read.
- What is a deadlock, and how to avoid one.
- ROW_NUMBER vs RANK vs DENSE_RANK, with a tie example.
- Why connection pooling and migration tools matter in real projects.
