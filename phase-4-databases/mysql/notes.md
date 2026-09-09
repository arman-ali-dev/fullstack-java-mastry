# MySQL In Depth

This document covers MySQL from fundamentals to advanced topics used in real projects and asked in interviews. It is organized into 22 parts. Each part has the core concept, why it matters, and SQL examples. Read it top to bottom for a full course, or jump to a part for revision.

Parts:

1. Relational Model Fundamentals
2. Data Types and Storage
3. Constraints
4. Command Categories: DDL, DML, DQL, DCL, TCL
5. Joins
6. Subqueries and Correlated Subqueries
7. Aggregate Functions, GROUP BY, HAVING
8. Set Operations
9. Normalization
10. Storage Engines (InnoDB vs MyISAM)
11. Indexing
12. Query Execution Plan (EXPLAIN)
13. Transactions and ACID
14. Isolation Levels
15. Locking and Deadlocks
16. Stored Procedures, Functions, Triggers
17. Views and Materialized View Concepts
18. Window Functions
19. Partitioning
20. Replication
21. Connection Pooling (HikariCP with Spring Boot)
22. Database Migration Tools (Flyway, Liquibase)
23. Common Interview Questions Quick Reference

---

## 1. Relational Model Fundamentals

A relational database stores data in tables (also called relations). Each table has rows (records/tuples) and columns (fields/attributes).

Key terms:

- Table: a collection of related data organized in rows and columns. Example: `employees` table.
- Row: one single record. Example: one employee's data.
- Column: one attribute/field of the record. Example: `name`, `salary`.
- Primary Key (PK): a column (or set of columns) that uniquely identifies each row. Cannot be NULL, must be unique.
- Foreign Key (FK): a column that references the Primary Key of another table, used to create a relationship between two tables.
- Composite Key: a primary key made up of two or more columns.
- Candidate Key: any column (or set of columns) that could qualify as a primary key (unique + not null). One candidate key is chosen as the PK; others become alternate keys.
- Surrogate Key: an artificial key with no business meaning, usually auto-incremented (like `id`), used as PK instead of a natural key.

Example:

```sql
CREATE TABLE departments (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100) NOT NULL,
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);
```

Here `dept_id` in `employees` is a foreign key pointing to `dept_id` (primary key) in `departments`. This enforces referential integrity: you cannot insert an employee with a `dept_id` that does not exist in `departments`, and by default you cannot delete a department that still has employees pointing to it (unless you define `ON DELETE CASCADE` or similar).

Relationship types:

- One-to-One: one row in table A relates to exactly one row in table B. Example: `users` and `user_profile`.
- One-to-Many: one row in table A relates to many rows in table B. Example: one `department` has many `employees`.
- Many-to-Many: many rows in A relate to many rows in B, implemented using a junction/bridge table. Example: `students` and `courses`, linked via `student_courses`.

```sql
CREATE TABLE student_courses (
    student_id INT,
    course_id INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
```

Interview angle: be ready to explain PK vs FK vs unique key, and to design a small schema (like employees-departments) on a whiteboard.

---

## 2. Data Types and Storage

Choosing the right data type affects storage size, performance, and correctness.

Numeric types:

- `TINYINT` (1 byte, -128 to 127 or 0-255 unsigned) — good for flags/small counters.
- `SMALLINT` (2 bytes), `MEDIUMINT` (3 bytes), `INT` (4 bytes), `BIGINT` (8 bytes) — choose based on the max value you expect, not just "always use INT".
- `DECIMAL(p, s)` — exact fixed-point number, stored as string-like precision. Always use this for money, never FLOAT/DOUBLE.
- `FLOAT`, `DOUBLE` — approximate, faster, but has rounding errors. Do not use for currency.

String types:

- `CHAR(n)` — fixed length, padded with spaces. Faster for fixed-size data like country codes (`CHAR(2)`).
- `VARCHAR(n)` — variable length, stores actual length + data. Use for names, emails, etc.
- `TEXT`, `MEDIUMTEXT`, `LONGTEXT` — for large text blocks, stored differently (often off-page), cannot be fully indexed the same way as VARCHAR.

Date/Time types:

- `DATE` — YYYY-MM-DD.
- `DATETIME` — date + time, no timezone awareness, range up to year 9999.
- `TIMESTAMP` — date + time, stored in UTC internally, converted to session timezone, range limited to 1970-2038. Auto-updates possible with `ON UPDATE CURRENT_TIMESTAMP`.
- `TIME`, `YEAR` — for time-only or year-only values.

Other important types:

- `BOOLEAN` — actually stored as `TINYINT(1)` in MySQL, there is no real boolean type.
- `ENUM('a','b','c')` — stores one value from a predefined list, stored internally as integer, saves space but hard to alter later (schema change needed to add values).
- `JSON` — stores JSON documents, supports functions like `JSON_EXTRACT`, useful for semi-structured data, but should not replace proper relational design for core fields.
- `BLOB` — binary large object, for storing files/images (though storing files in DB is often discouraged in favor of storing file paths/URLs).

Storage considerations:

- Smaller data types = smaller rows = more rows fit in memory/page = faster scans and better cache usage.
- `VARCHAR` vs `TEXT`: use VARCHAR when you know a reasonable max length, since it works better with indexes.
- Avoid `NULL`able columns when not needed; NULLs add complexity to indexing and comparisons (`NULL != NULL` in SQL logic).
- `UTF8MB4` should be your default charset for full Unicode support (including emojis); plain `UTF8` in MySQL is actually a restricted 3-byte encoding.

Interview angle: "Why DECIMAL for money and not FLOAT" is a classic question. Answer: FLOAT/DOUBLE store approximate binary representations of decimal numbers, causing rounding errors (e.g. 0.1 + 0.2 != 0.3 exactly), which is unacceptable for financial calculations. DECIMAL stores exact digit-by-digit representation.

---

## 3. Constraints

Constraints enforce data integrity rules at the database level, not just application level.

- `NOT NULL` — column cannot store NULL.
- `UNIQUE` — all values in the column must be different (NULLs are allowed and not compared as duplicates).
- `PRIMARY KEY` — combination of NOT NULL + UNIQUE, one per table (can be composite).
- `FOREIGN KEY` — enforces that a value must exist in the referenced table's column.
- `CHECK (condition)` — value must satisfy a boolean expression. Example: `CHECK (salary > 0)`. Supported from MySQL 8.0.16 onward.
- `DEFAULT value` — auto-fills a column if no value is given during insert.

Example:

```sql
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(150) NOT NULL UNIQUE,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    CHECK (balance >= 0)
);
```

Foreign key referential actions worth knowing for interviews:

- `ON DELETE CASCADE` — deleting parent row auto-deletes child rows.
- `ON DELETE SET NULL` — deleting parent row sets FK column in child to NULL.
- `ON DELETE RESTRICT` (default behavior) — blocks deletion of parent if child rows exist.
- `ON UPDATE CASCADE` — if parent PK changes, FK in child updates automatically.

Interview angle: explaining the difference between UNIQUE and PRIMARY KEY, and when you would use `ON DELETE CASCADE` vs `RESTRICT` in a real schema (e.g. cascade for order_items when order is deleted, restrict for departments with active employees), is a common practical question.

---

## 4. Command Categories: DDL, DML, DQL, DCL, TCL

SQL commands are grouped by what they do:

DDL (Data Definition Language) — defines/changes structure:

```sql
CREATE TABLE ...
ALTER TABLE employees ADD COLUMN phone VARCHAR(15);
DROP TABLE employees;
TRUNCATE TABLE employees;   -- removes all rows fast, resets AUTO_INCREMENT, cannot be rolled back in most engines
RENAME TABLE old_name TO new_name;
```

Note: `TRUNCATE` is technically DDL, not DML, because internally it drops and recreates the table structure rather than deleting rows one by one. This is why it is faster than `DELETE` but cannot be selectively filtered with `WHERE`.

DML (Data Manipulation Language) — changes data:

```sql
INSERT INTO employees (emp_name, dept_id) VALUES ('Raj', 1);
UPDATE employees SET dept_id = 2 WHERE emp_id = 5;
DELETE FROM employees WHERE emp_id = 5;
```

DQL (Data Query Language) — reads data:

```sql
SELECT emp_name, dept_id FROM employees WHERE dept_id = 1;
```

Some textbooks merge DQL into DML; both views are accepted in interviews, just be aware SELECT is sometimes classified separately.

DCL (Data Control Language) — permissions:

```sql
GRANT SELECT, INSERT ON company.employees TO 'app_user'@'%';
REVOKE INSERT ON company.employees FROM 'app_user'@'%';
```

TCL (Transaction Control Language) — manages transactions:

```sql
START TRANSACTION;
SAVEPOINT before_update;
UPDATE accounts SET balance = balance - 100 WHERE account_id = 1;
ROLLBACK TO before_update;
COMMIT;
```

Interview angle: a frequent trick question is "Is TRUNCATE DDL or DML" — answer DDL, and explain why (auto-commits, cannot rollback in most cases, resets identity counter, no WHERE clause).

---

## 5. Joins

Joins combine rows from two or more tables based on a related column.

Setup for examples:

```sql
-- employees: emp_id, emp_name, dept_id
-- departments: dept_id, dept_name
```

INNER JOIN — returns only rows that have matching values in both tables:

```sql
SELECT e.emp_name, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id;
```

LEFT JOIN (LEFT OUTER JOIN) — returns all rows from the left table, and matched rows from the right table; unmatched right-side columns are NULL:

```sql
SELECT e.emp_name, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.dept_id;
```

Use case: find employees who are not assigned to any department:

```sql
SELECT e.emp_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.dept_id
WHERE d.dept_id IS NULL;
```

RIGHT JOIN (RIGHT OUTER JOIN) — mirror of LEFT JOIN, returns all rows from the right table:

```sql
SELECT e.emp_name, d.dept_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.dept_id;
```

In practice, RIGHT JOIN is rarely used because you can always rewrite it as a LEFT JOIN by swapping table order; most teams standardize on LEFT JOIN for readability.

FULL OUTER JOIN — returns all rows from both tables, matched where possible, NULL where not. MySQL does not support FULL OUTER JOIN directly (unlike PostgreSQL/Oracle). Workaround using UNION:

```sql
SELECT e.emp_name, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.dept_id
UNION
SELECT e.emp_name, d.dept_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.dept_id;
```

SELF JOIN — a table joined with itself, useful for hierarchical data (like manager-employee relationships):

```sql
SELECT emp.emp_name AS employee, mgr.emp_name AS manager
FROM employees emp
LEFT JOIN employees mgr ON emp.manager_id = mgr.emp_id;
```

CROSS JOIN — cartesian product, every row of table A combined with every row of table B (no ON condition, or explicit CROSS JOIN keyword):

```sql
SELECT s.size, c.color
FROM sizes s
CROSS JOIN colors c;
```

Useful for generating combinations (like all size-color pairs for a product catalog). Dangerous if used accidentally on large tables — it multiplies row counts.

Interview angle: draw two overlapping circles (Venn-diagram style) mentally for INNER/LEFT/RIGHT/FULL. Also expect "how do you get rows that only exist in table A and not table B" (LEFT JOIN + WHERE right.col IS NULL — this is called an anti-join pattern).

---

## 6. Subqueries and Correlated Subqueries

A subquery is a query nested inside another query.

Non-correlated subquery — runs independently, does not reference the outer query:

```sql
SELECT emp_name
FROM employees
WHERE dept_id IN (SELECT dept_id FROM departments WHERE dept_name = 'Engineering');
```

Scalar subquery — returns exactly one value:

```sql
SELECT emp_name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);
```

Subquery in FROM clause (derived table):

```sql
SELECT dept_id, avg_salary
FROM (
    SELECT dept_id, AVG(salary) AS avg_salary
    FROM employees
    GROUP BY dept_id
) AS dept_avg
WHERE avg_salary > 50000;
```

Correlated subquery — references a column from the outer query, so it runs once per row of the outer query (conceptually):

```sql
SELECT e.emp_name, e.salary, e.dept_id
FROM employees e
WHERE e.salary > (
    SELECT AVG(e2.salary)
    FROM employees e2
    WHERE e2.dept_id = e.dept_id
);
```

This finds employees earning more than the average salary of their own department. The inner query depends on `e.dept_id` from the outer query — that dependency is what makes it "correlated."

EXISTS vs IN:

```sql
-- Using EXISTS (often more efficient for correlated existence checks)
SELECT d.dept_name
FROM departments d
WHERE EXISTS (
    SELECT 1 FROM employees e WHERE e.dept_id = d.dept_id
);

-- Using IN
SELECT dept_name
FROM departments
WHERE dept_id IN (SELECT dept_id FROM employees);
```

`EXISTS` stops as soon as it finds one matching row (short-circuits), and handles NULLs more safely than `IN`, which can behave unexpectedly if the subquery returns NULL values.

Interview angle: be ready to convert a correlated subquery into an equivalent JOIN and explain performance trade-offs — MySQL's optimizer can sometimes rewrite correlated subqueries into joins internally, but it's not guaranteed, so understanding both forms matters.

---

## 7. Aggregate Functions, GROUP BY, HAVING

Aggregate functions operate on a set of rows and return a single summary value.

- `COUNT(*)` — counts all rows (including NULLs). `COUNT(column)` — counts non-NULL values only.
- `SUM(column)` — total.
- `AVG(column)` — average.
- `MIN(column)`, `MAX(column)` — smallest/largest value.

```sql
SELECT dept_id, COUNT(*) AS emp_count, AVG(salary) AS avg_salary
FROM employees
GROUP BY dept_id;
```

`GROUP BY` groups rows sharing the same value(s) in specified columns, so aggregate functions compute per group instead of over the whole table.

`HAVING` filters groups after aggregation (unlike `WHERE`, which filters rows before aggregation):

```sql
SELECT dept_id, COUNT(*) AS emp_count
FROM employees
GROUP BY dept_id
HAVING COUNT(*) > 5;
```

Order of logical execution matters for interviews:

```
FROM -> WHERE -> GROUP BY -> HAVING -> SELECT -> ORDER BY -> LIMIT
```

This is why you cannot use a column alias defined in SELECT inside a WHERE clause, but you sometimes can in HAVING or ORDER BY (MySQL allows alias in HAVING/ORDER BY as an extension, though standard SQL is stricter).

Common mistake: putting an aggregate condition in WHERE instead of HAVING:

```sql
-- Wrong: WHERE cannot use aggregate function directly
SELECT dept_id, COUNT(*) FROM employees WHERE COUNT(*) > 5 GROUP BY dept_id; -- ERROR

-- Correct
SELECT dept_id, COUNT(*) FROM employees GROUP BY dept_id HAVING COUNT(*) > 5;
```

Interview angle: "WHERE vs HAVING" is asked almost every interview. Answer: WHERE filters individual rows before grouping; HAVING filters groups after aggregation, and can use aggregate functions in its condition.

---

## 8. Set Operations

Set operations combine results of two or more SELECT queries.

`UNION` — combines results and removes duplicate rows:

```sql
SELECT emp_name FROM employees WHERE dept_id = 1
UNION
SELECT emp_name FROM employees WHERE dept_id = 2;
```

`UNION ALL` — combines results and keeps duplicates (faster, since no dedup step):

```sql
SELECT emp_name FROM employees WHERE dept_id = 1
UNION ALL
SELECT emp_name FROM employees WHERE dept_id = 2;
```

Rules: both SELECTs must have the same number of columns, and compatible data types in corresponding positions. Column names in the result come from the first SELECT.

`INTERSECT` — rows common to both queries. MySQL added native `INTERSECT` support from version 8.0.31. If on an older version, simulate with `IN` or `INNER JOIN`:

```sql
-- Native (MySQL 8.0.31+)
SELECT emp_id FROM team_a
INTERSECT
SELECT emp_id FROM team_b;

-- Workaround for older MySQL
SELECT DISTINCT a.emp_id
FROM team_a a
INNER JOIN team_b b ON a.emp_id = b.emp_id;
```

`EXCEPT` (also called MINUS in Oracle) — rows in the first query that are not in the second. Native support from MySQL 8.0.31. Workaround:

```sql
-- Native (MySQL 8.0.31+)
SELECT emp_id FROM team_a
EXCEPT
SELECT emp_id FROM team_b;

-- Workaround for older MySQL
SELECT a.emp_id
FROM team_a a
LEFT JOIN team_b b ON a.emp_id = b.emp_id
WHERE b.emp_id IS NULL;
```

Interview angle: know that UNION ALL is faster than UNION because it skips the duplicate-removal sort/hash step — use UNION ALL whenever you know duplicates cannot occur or don't matter.

---

## 9. Normalization

Normalization is the process of organizing columns and tables to reduce data redundancy and avoid update/insert/delete anomalies.

1NF (First Normal Form):

- Each column holds atomic (indivisible) values — no comma-separated lists in a single cell.
- Each row is unique (has a primary key).
- No repeating groups of columns (like `phone1`, `phone2`, `phone3`).

Bad (violates 1NF):

```
emp_id | emp_name | phones
1      | Raj      | 9876543210, 9123456780
```

Good (1NF):

```
emp_id | emp_name
1      | Raj

emp_id | phone
1      | 9876543210
1      | 9123456780
```

2NF (Second Normal Form):

- Must be in 1NF.
- No partial dependency — every non-key column must depend on the whole primary key, not just part of it. Only relevant when you have a composite primary key.

Example: table `order_items(order_id, product_id, product_name, quantity)` with composite PK `(order_id, product_id)`. Here `product_name` depends only on `product_id`, not on the full composite key — this is a partial dependency, violating 2NF. Fix by moving `product_name` to a separate `products` table.

3NF (Third Normal Form):

- Must be in 2NF.
- No transitive dependency — non-key columns must depend only on the primary key, not on other non-key columns.

Example: `employees(emp_id, dept_id, dept_name)`. Here `dept_name` depends on `dept_id`, which depends on `emp_id` — that's a transitive dependency (emp_id -> dept_id -> dept_name). Fix by moving `dept_name` into a separate `departments` table, keeping only `dept_id` as FK in `employees`.

BCNF (Boyce-Codd Normal Form):

- A stricter version of 3NF. For every functional dependency `X -> Y`, `X` must be a super key (a key that can uniquely identify a row, not necessarily minimal).
- 3NF allows a rare edge case where a non-key attribute determines part of a candidate key; BCNF eliminates that. This case is uncommon in real interview scenarios but good to mention: "BCNF handles overlapping composite candidate keys that 3NF doesn't fully address."

When to denormalize:

- Denormalization intentionally adds redundancy back for performance — fewer joins needed for read-heavy systems.
- Common in reporting/analytics tables, dashboards, or read replicas, where join cost outweighs storage cost.
- Example: storing `dept_name` directly in the `employees` table (duplicated from `departments`) to avoid a join on a very frequently hit query, accepting the trade-off of needing to update it in two places if department name changes.
- Rule of thumb: normalize for OLTP (transactional systems needing consistency), denormalize selectively for OLAP/reporting (read-heavy, less write frequency).

Interview angle: be ready to normalize a messy sample table live (interviewers love giving you one flat table with redundant data and asking you to break it into 3NF).

---

## 10. Storage Engines (InnoDB vs MyISAM)

MySQL supports pluggable storage engines — the component responsible for how data is actually stored, indexed, and locked on disk.

InnoDB (default since MySQL 5.5):

- Supports transactions (ACID compliant).
- Row-level locking (better concurrency for write-heavy workloads).
- Supports foreign keys.
- Crash recovery via redo logs.
- Uses clustered index — the table data itself is physically stored in primary key order (the PK IS the data structure, not a separate lookup).

MyISAM (legacy, mostly historical now):

- No transaction support.
- Table-level locking (a write lock blocks all reads/writes on the whole table).
- No foreign key enforcement.
- Faster for read-heavy, simple workloads historically, but this advantage has mostly disappeared as InnoDB matured.
- Still occasionally seen in old/legacy systems or for full-text search in older MySQL versions (InnoDB gained full-text search support from 5.6 onward, reducing this reason too).

Practical takeaway: always use InnoDB for anything modern unless you have a very specific legacy reason not to. Almost all interview and real-world answers should default to InnoDB.

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY
) ENGINE=InnoDB;

SHOW TABLE STATUS WHERE Name = 'employees';  -- shows which engine a table uses
```

Interview angle: "Why is InnoDB preferred over MyISAM" — answer with transactions, row-level locking, foreign keys, crash recovery.

---

## 11. Indexing

An index is a separate data structure that lets MySQL find rows faster without scanning the whole table, similar to an index at the back of a book.

B-Tree Index (the default and most common type in InnoDB):

- Balanced tree structure, keeps data sorted, allows fast lookups, range queries (`<`, `>`, `BETWEEN`), and sorting (`ORDER BY`) in O(log n) time.
- Every InnoDB table has a clustered index on the primary key — the actual row data lives at the leaf nodes of this tree.
- Secondary indexes (non-primary) store the indexed column's value plus the primary key value at their leaf nodes — so a lookup via a secondary index does a second lookup (via PK) into the clustered index to fetch the full row. This is called a "bookmark lookup."

```sql
CREATE INDEX idx_emp_name ON employees(emp_name);
```

Composite Index (multi-column index):

```sql
CREATE INDEX idx_dept_salary ON employees(dept_id, salary);
```

- Order of columns matters. This index is useful for queries filtering on `dept_id` alone, or `dept_id AND salary` together, but NOT useful for filtering on `salary` alone — this is called the "leftmost prefix rule." The index can only be used starting from its leftmost column.

```sql
-- Uses the index (leftmost column present)
WHERE dept_id = 3
WHERE dept_id = 3 AND salary > 50000

-- Does NOT use this index efficiently
WHERE salary > 50000
```

Covering Index — an index that contains all the columns needed by a query, so MySQL never needs to go back to the actual table (no bookmark lookup):

```sql
CREATE INDEX idx_covering ON employees(dept_id, emp_name, salary);

SELECT emp_name, salary FROM employees WHERE dept_id = 3;
-- if idx_covering has dept_id, emp_name, salary, this is a covering index for this query
```

Covering indexes are one of the biggest real-world performance wins because the query is answered entirely from the index structure (visible in EXPLAIN as "Using index").

Index Selectivity — the ratio of distinct values to total rows. High selectivity (e.g. `email` column, almost all unique) means the index is very effective at narrowing down rows. Low selectivity (e.g. `gender` column with only 2-3 distinct values) means the index provides little benefit, since it still matches a huge fraction of rows.

```
Selectivity = COUNT(DISTINCT column) / COUNT(*)
```

Close to 1 = highly selective = good index candidate. Close to 0 = poor index candidate.

When NOT to index:

- Small tables (full scan is already fast).
- Columns with low selectivity used alone.
- Columns updated very frequently (every index adds write overhead, since the index must be updated on every INSERT/UPDATE/DELETE too).
- Too many indexes on one table slows down writes significantly.

Interview angle: expect to explain leftmost prefix rule, covering index benefit, and why indexing every column is a bad idea (write overhead + storage cost).

---

## 12. Query Execution Plan (EXPLAIN)

`EXPLAIN` shows how MySQL's optimizer plans to execute a query — which indexes it will use, in what order tables are joined, and how many rows it expects to examine.

```sql
EXPLAIN SELECT emp_name FROM employees WHERE dept_id = 3;
```

Key columns in EXPLAIN output:

- `type` — the join/access type, ordered roughly best to worst:
  - `system` / `const` — best, at most one matching row (e.g. lookup by PK).
  - `eq_ref` — one row matched per row from the previous table, typical for PK/unique joins.
  - `ref` — non-unique index lookup, multiple rows may match.
  - `range` — index used to retrieve rows within a range (`BETWEEN`, `>`, `<`).
  - `index` — full index scan (better than table scan, but still reads the whole index).
  - `ALL` — full table scan, worst case, means no usable index was found.
- `possible_keys` — indexes MySQL considered.
- `key` — the index it actually chose (NULL means no index used).
- `rows` — estimated number of rows MySQL expects to examine (lower is better).
- `Extra`:
  - `Using index` — covering index used, great.
  - `Using where` — filtering happens after fetching rows, normal.
  - `Using filesort` — MySQL has to do an extra sorting pass outside of index order, can be a performance concern on large data.
  - `Using temporary` — MySQL creates a temporary table, often for GROUP BY/DISTINCT/ORDER BY combos without a supporting index, can be expensive.

Identifying a full table scan: `type = ALL` and `key = NULL` in the EXPLAIN output. That is a signal to consider adding an appropriate index.

```sql
EXPLAIN ANALYZE SELECT emp_name FROM employees WHERE dept_id = 3;
```

`EXPLAIN ANALYZE` (available from MySQL 8.0.18+) actually runs the query and shows real timing/row counts, not just estimates — more accurate than plain EXPLAIN for tuning.

Interview angle: be ready to look at an EXPLAIN output and say "this is doing a full table scan because type is ALL and key is NULL, I would add an index on column X to fix it."

---

## 13. Transactions and ACID

A transaction is a sequence of one or more SQL operations executed as a single logical unit of work — either all of it succeeds, or none of it does.

```sql
START TRANSACTION;

UPDATE accounts SET balance = balance - 500 WHERE account_id = 1;
UPDATE accounts SET balance = balance + 500 WHERE account_id = 2;

COMMIT;   -- makes changes permanent
-- or
ROLLBACK; -- undoes all changes since START TRANSACTION
```

`SAVEPOINT` allows partial rollback within a transaction:

```sql
START TRANSACTION;
UPDATE accounts SET balance = balance - 500 WHERE account_id = 1;
SAVEPOINT sp1;
UPDATE accounts SET balance = balance + 500 WHERE account_id = 2;
ROLLBACK TO sp1;  -- undoes only the second update, first update still pending
COMMIT;
```

ACID properties:

- Atomicity — all operations in a transaction succeed together, or all fail together (the money transfer example: if the debit succeeds but the credit fails, the whole transaction rolls back, no money disappears).
- Consistency — a transaction takes the database from one valid state to another valid state, respecting all constraints (foreign keys, checks, unique constraints).
- Isolation — concurrent transactions should not interfere with each other's intermediate (uncommitted) state; the level of this guarantee is tunable (see Isolation Levels below).
- Durability — once a transaction is committed, the change survives even a crash/power failure immediately after (achieved via write-ahead redo logs in InnoDB).

Interview angle: the money transfer between two accounts is the textbook example to explain Atomicity — always keep it ready. Also expect "what happens if the server crashes right after COMMIT but before the response reaches the client" — durability guarantees the data itself is safe on disk, but the application must still handle the fact that it doesn't know if the client saw the success message (idempotency concern).

---

## 14. Isolation Levels

Isolation levels control how much one transaction can see of another transaction's uncommitted or concurrently changing data. There's a trade-off: stricter isolation = more consistency, but more locking = less concurrency/performance.

Read Uncommitted:

- Lowest isolation. A transaction can read data that another transaction has modified but not yet committed.
- Problem: Dirty Read — you might read data that later gets rolled back, meaning you read something that "never really happened."

Read Committed:

- A transaction only sees data that has been committed by other transactions at the moment of each read.
- Problem: Non-Repeatable Read — if you run the same SELECT twice within the same transaction, you might get different results because another transaction committed a change in between.

Repeatable Read (MySQL's default isolation level for InnoDB):

- Guarantees that if you read a row once in a transaction, you'll see the same data if you read it again later in the same transaction, regardless of other transactions committing changes.
- Achieved in InnoDB using MVCC (Multi-Version Concurrency Control) — instead of locking, each transaction sees a consistent "snapshot" of the data taken at the start of the transaction (roughly).
- Problem it can still have: Phantom Read — a new row inserted by another transaction can appear if you re-run a range query (`WHERE salary > 1000`), though InnoDB's specific implementation of Repeatable Read actually prevents most phantom reads too, using gap locks, making it stronger than the SQL standard strictly requires.

Serializable:

- Highest isolation level. Transactions are executed as if they ran one after another (serially), not concurrently.
- Achieved via heavy locking (essentially every plain SELECT becomes a locking read). Prevents dirty reads, non-repeatable reads, and phantom reads completely.
- Trade-off: significantly reduced concurrency, more chance of lock waits/timeouts/deadlocks.

Summary table (interview favorite):

```
Level             Dirty Read   Non-Repeatable Read   Phantom Read
Read Uncommitted  Possible     Possible              Possible
Read Committed    Prevented    Possible              Possible
Repeatable Read   Prevented    Prevented             Possible (mostly prevented in InnoDB via gap locks)
Serializable      Prevented    Prevented             Prevented
```

Setting isolation level:

```sql
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
SELECT @@transaction_isolation;  -- check current level
```

Interview angle: memorize the table above exactly, and know that MySQL's default is Repeatable Read (unlike Oracle/PostgreSQL, which default to Read Committed) — this is a very commonly asked distinguishing fact.

---

## 15. Locking and Deadlocks

Row-level locking vs table-level locking:

- Row-level lock (InnoDB default) — only the specific rows involved in a transaction's write are locked, other rows in the same table remain freely accessible. Much better concurrency for write-heavy systems.
- Table-level lock (MyISAM, or explicit `LOCK TABLES` in InnoDB) — locks the entire table, blocking all other reads/writes to it until released. Simple but kills concurrency.

Types of locks in InnoDB:

- Shared lock (S) — allows the transaction to read a row, other transactions can also acquire shared locks on the same row (multiple readers allowed), but no one can write to it while shared locks are held.
- Exclusive lock (X) — allows the transaction to update/delete a row, blocks all other locks (read or write) on that row until released.

```sql
SELECT * FROM accounts WHERE account_id = 1 FOR SHARE;    -- shared lock (was LOCK IN SHARE MODE in older syntax)
SELECT * FROM accounts WHERE account_id = 1 FOR UPDATE;   -- exclusive lock
```

Gap locks and Next-Key locks (InnoDB specific, important for Repeatable Read):

- Gap lock — locks the "gap" between index records, preventing other transactions from inserting new rows into that range (this is how InnoDB mostly prevents phantom reads in Repeatable Read).
- Next-key lock — combination of a row lock plus the gap lock before it, InnoDB's default locking strategy for range scans under Repeatable Read.

Deadlocks — occur when two (or more) transactions each hold a lock the other one needs, and each is waiting on the other, creating a circular wait with no way forward.

Classic deadlock example:

```
Transaction A: locks row 1, then tries to lock row 2
Transaction B: locks row 2, then tries to lock row 1
-> both wait forever on each other
```

```sql
-- Transaction A
START TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE account_id = 1;
-- (pause)
UPDATE accounts SET balance = balance + 100 WHERE account_id = 2;
COMMIT;

-- Transaction B (running concurrently)
START TRANSACTION;
UPDATE accounts SET balance = balance - 50 WHERE account_id = 2;
-- (pause)
UPDATE accounts SET balance = balance + 50 WHERE account_id = 1;
COMMIT;
```

If both reach their second UPDATE at the same time, each is waiting for a lock the other holds — MySQL's deadlock detector will pick one transaction as the "victim," roll it back automatically, and return an error (`Deadlock found when trying to get lock`) to that transaction's client, letting the other proceed.

How to avoid deadlocks:

- Always access/lock tables and rows in the same consistent order across all transactions in your application (e.g. always update the lower account_id first).
- Keep transactions short — commit as soon as the necessary work is done, don't hold locks while doing unrelated slow work (like calling an external API mid-transaction).
- Use appropriate indexes — poor indexing means more rows/gaps get locked than necessary, increasing deadlock chances.
- Catch deadlock exceptions in application code and retry the transaction (deadlocks are often considered a normal, expected condition to handle gracefully, not a bug).

Interview angle: explain a deadlock scenario with two transactions, and describe at least two prevention strategies (consistent lock ordering + short transactions are the two most expected answers).

---

## 16. Stored Procedures, Functions, Triggers

Stored Procedure — a saved, reusable block of SQL logic that can accept input parameters, run multiple statements, and does not necessarily return a value (though it can return output params or result sets):

```sql
DELIMITER //
CREATE PROCEDURE GetEmployeesByDept(IN deptId INT)
BEGIN
    SELECT emp_name, salary
    FROM employees
    WHERE dept_id = deptId;
END //
DELIMITER ;

CALL GetEmployeesByDept(3);
```

Function — similar to a procedure, but must return exactly one value and can be used directly inside SQL expressions (like `SELECT`, `WHERE`):

```sql
DELIMITER //
CREATE FUNCTION GetAnnualSalary(monthlySalary DECIMAL(10,2))
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN monthlySalary * 12;
END //
DELIMITER ;

SELECT emp_name, GetAnnualSalary(salary) AS annual_salary FROM employees;
```

Trigger — a block of logic that automatically executes in response to an INSERT, UPDATE, or DELETE event on a specific table:

```sql
DELIMITER //
CREATE TRIGGER before_salary_update
BEFORE UPDATE ON employees
FOR EACH ROW
BEGIN
    IF NEW.salary < OLD.salary THEN
        INSERT INTO salary_audit(emp_id, old_salary, new_salary, changed_at)
        VALUES (OLD.emp_id, OLD.salary, NEW.salary, NOW());
    END IF;
END //
DELIMITER ;
```

`OLD` refers to the row's values before the change, `NEW` refers to the row's values after the change. Triggers can run `BEFORE` or `AFTER` the event.

When to use what:

- Stored procedure: complex multi-step business logic you want to run on demand from the application, or batch operations.
- Function: reusable calculation you want embedded directly inside a query.
- Trigger: automatic side-effects (auditing, denormalized field updates, enforcing complex business rules) that must happen no matter which client makes the change.

Trade-offs to mention in interviews: stored procedures/triggers move logic into the database, which can make it harder to version control, test, and debug compared to application-layer code (like Spring Boot service classes) — many modern teams intentionally keep business logic in the application layer and use the database mainly for storage and simple constraints, reserving triggers mostly for cross-cutting concerns like auditing.

---

## 17. Views and Materialized View Concepts

View — a virtual table defined by a stored SELECT query. It does not store data itself (in standard MySQL views); every time you query the view, MySQL runs the underlying query fresh.

```sql
CREATE VIEW high_earners AS
SELECT emp_id, emp_name, salary, dept_id
FROM employees
WHERE salary > 80000;

SELECT * FROM high_earners WHERE dept_id = 3;
```

Benefits: simplifies complex/repeated queries, can restrict which columns/rows a user is allowed to see (a security/abstraction layer), keeps queries DRY.

Updatable views: simple views (based on one table, no aggregation/GROUP BY/DISTINCT/UNION) can support INSERT/UPDATE through them; complex views generally cannot.

Materialized View — a view whose result is physically stored (materialized) on disk, not recomputed on every query, and needs to be refreshed periodically. MySQL does NOT support materialized views natively (unlike Oracle/PostgreSQL). Common workarounds:

- Create a real summary table and populate it with a scheduled job (`EVENT` scheduler, cron job, or triggers) that refreshes it periodically.

```sql
CREATE TABLE dept_summary (
    dept_id INT PRIMARY KEY,
    avg_salary DECIMAL(10,2),
    emp_count INT,
    last_refreshed TIMESTAMP
);

-- Refresh logic (could run via scheduled event or app job)
REPLACE INTO dept_summary (dept_id, avg_salary, emp_count, last_refreshed)
SELECT dept_id, AVG(salary), COUNT(*), NOW()
FROM employees
GROUP BY dept_id;
```

- Use MySQL's `EVENT` scheduler to automate that refresh on a timer.

Interview angle: "Does MySQL support materialized views" — answer clearly: no, not natively; explain the summary-table-plus-scheduled-refresh workaround, since interviewers want to see you know the limitation and the practical fix.

---

## 18. Window Functions

Window functions perform calculations across a set of rows related to the current row, without collapsing rows into groups the way GROUP BY does — every original row stays in the output, with an extra calculated column.

Syntax pattern:

```sql
function_name() OVER (
    PARTITION BY column
    ORDER BY column
)
```

- `PARTITION BY` — divides rows into groups (windows), similar concept to GROUP BY but without collapsing rows.
- `ORDER BY` (inside OVER) — defines the order used for ranking/running calculations within each partition.

`ROW_NUMBER()` — assigns a unique sequential number to each row within its partition, no ties:

```sql
SELECT emp_name, dept_id, salary,
       ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS row_num
FROM employees;
```

Common use: get the top N rows per group (e.g. top 3 highest paid employees per department):

```sql
SELECT * FROM (
    SELECT emp_name, dept_id, salary,
           ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rn
    FROM employees
) ranked
WHERE rn <= 3;
```

`RANK()` — assigns the same rank to tied values, but skips the next rank number(s) (e.g. 1, 2, 2, 4):

```sql
SELECT emp_name, salary,
       RANK() OVER (ORDER BY salary DESC) AS salary_rank
FROM employees;
```

`DENSE_RANK()` — same as RANK but does not skip numbers after a tie (e.g. 1, 2, 2, 3):

```sql
SELECT emp_name, salary,
       DENSE_RANK() OVER (ORDER BY salary DESC) AS salary_dense_rank
FROM employees;
```

`LEAD()` — accesses a value from a following row within the same partition, without a self-join:

```sql
SELECT emp_name, salary,
       LEAD(salary) OVER (ORDER BY salary DESC) AS next_lower_salary
FROM employees;
```

`LAG()` — accesses a value from a preceding row:

```sql
SELECT emp_name, hire_date,
       LAG(hire_date) OVER (ORDER BY hire_date) AS previous_hire_date
FROM employees;
```

Common use: month-over-month comparison, calculating the difference between the current row's value and the previous row's value (e.g. `salary - LAG(salary) OVER (...)`).

Other useful window functions worth knowing: `SUM()`, `AVG()`, `COUNT()` can also be used as window functions (with `OVER`) to get running totals without collapsing rows:

```sql
SELECT emp_name, salary,
       SUM(salary) OVER (ORDER BY emp_id) AS running_total
FROM employees;
```

Interview angle: "ROW_NUMBER vs RANK vs DENSE_RANK, with ties" is asked constantly — memorize the 1,2,2,4 vs 1,2,2,3 difference exactly, and be ready to write the "top N per group" query pattern from memory, it comes up very often in real interviews.

---

## 19. Partitioning Strategies for Large Tables

Partitioning splits a very large table into smaller physical pieces internally, while MySQL still presents it as a single logical table to queries. Goal: improve query performance and manageability (like archiving old data) on huge tables.

Range Partitioning — split by a range of values, commonly used for date-based data:

```sql
CREATE TABLE orders (
    order_id INT NOT NULL,
    order_date DATE NOT NULL,
    amount DECIMAL(10,2)
)
PARTITION BY RANGE (YEAR(order_date)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION pmax VALUES LESS THAN MAXVALUE
);
```

Benefit: a query filtering `WHERE order_date >= '2025-01-01'` only scans the relevant partition(s) — this is called "partition pruning." Also makes archiving easy: you can drop an entire old partition instantly instead of running a slow DELETE.

List Partitioning — split by a predefined list of discrete values:

```sql
PARTITION BY LIST (store_region) (
    PARTITION p_north VALUES IN ('DELHI', 'PUNJAB'),
    PARTITION p_south VALUES IN ('KARNATAKA', 'TAMIL_NADU')
);
```

Hash Partitioning — MySQL applies a hash function to a column to distribute rows evenly across a fixed number of partitions, useful when there's no natural range/list to split by, mainly for spreading load evenly:

```sql
PARTITION BY HASH(emp_id)
PARTITIONS 4;
```

Key Partitioning — similar to Hash, but MySQL uses its own internal hashing function on the primary key (or a specified column), simpler to set up.

Important constraint: any column used in the partitioning expression must be part of every unique key (including the primary key) on the table. This trips people up — you cannot partition by a column that isn't included in the PK if the table has one.

When partitioning helps:

- Very large tables (hundreds of millions of rows) where queries usually filter on the partition key.
- Data lifecycle management — easy bulk deletion of old data via `DROP PARTITION` instead of slow row-by-row DELETE.

When it doesn't help / can hurt:

- Small-to-medium tables — overhead isn't worth it.
- Queries that don't filter on the partition key at all — MySQL then has to scan every partition anyway.
- Too many partitions increases metadata overhead.

Interview angle: know partition pruning as the core performance benefit, and the "drop old partition instead of DELETE" trick for data retention/archiving use cases — this is a very practical, frequently asked benefit.

---

## 20. Replication Basics

Replication copies data from one MySQL server (source) to one or more other servers (replicas), used for read scaling, high availability, and backups.

Master-Slave (Source-Replica) Replication:

- One master (source) server handles all writes.
- One or more slave (replica) servers receive a copy of the data and typically handle read-only queries.
- How it works: the master logs every data change to its binary log (binlog). Each replica runs an I/O thread that copies these binlog events to its own relay log, then a SQL thread that replays those events to apply the same changes locally.
- Benefits: read scalability (spread SELECT load across replicas), a replica can be promoted to master if the original master fails (basic high availability), replicas can be used for backups without affecting the live master's performance.
- Limitation: by default this is asynchronous — there can be a small replication lag, meaning a replica might briefly serve slightly stale data compared to the master.

```sql
-- On master
SHOW MASTER STATUS;   -- shows current binlog file and position

-- On replica (conceptual setup)
CHANGE REPLICATION SOURCE TO
    SOURCE_HOST='master_host',
    SOURCE_USER='repl_user',
    SOURCE_PASSWORD='repl_pass',
    SOURCE_LOG_FILE='mysql-bin.000001',
    SOURCE_LOG_POS=  154;

START REPLICA;
SHOW REPLICA STATUS \G
```

(Older MySQL versions used `CHANGE MASTER TO` / `SLAVE` terminology; MySQL 8.0.23+ renamed these to `CHANGE REPLICATION SOURCE TO` / `REPLICA` for more inclusive terminology, both may appear in different environments/interview contexts.)

Master-Master (Multi-Source / Circular) Replication:

- Two servers each act as both master and replica to each other — writes on either server get replicated to the other.
- Benefit: both servers can accept writes, useful for certain HA/geo-distributed setups.
- Major risk: write conflicts — if the same row is updated differently on both servers around the same time, resolving the conflict is complex and error-prone. Because of this, master-master is used carefully and less commonly than master-slave; many real systems prefer master-slave with a clear single write path plus manual/automated failover instead.

Semi-synchronous replication — a middle ground: the master waits for at least one replica to acknowledge it has received (not necessarily applied) the change before considering the transaction committed, reducing (but not eliminating) the risk of data loss on failover compared to fully asynchronous replication.

Interview angle: explain binlog-based replication flow (I/O thread + SQL thread) and the read-scaling use case; also be ready to explain replication lag as a real-world consistency trade-off application developers need to be aware of (e.g. "read your own write" problems right after an insert, if the read immediately goes to a lagging replica).

---

## 21. Connection Pooling (HikariCP with Spring Boot)

Opening a new database connection for every single query is expensive (TCP handshake, authentication, session setup). Connection pooling keeps a pool of already-open, reusable connections ready to hand out, avoiding this repeated cost.

HikariCP is the default connection pool in Spring Boot (since Spring Boot 2.x), known for being lightweight and fast.

Basic configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/company
spring.datasource.username=app_user
spring.datasource.password=app_pass

spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.max-lifetime=1800000
```

Key parameters explained:

- `maximum-pool-size` — the maximum number of connections HikariCP will keep open at once, including both idle and in-use connections. Set based on expected concurrent load and what the MySQL server's `max_connections` setting can handle across all app instances combined.
- `minimum-idle` — minimum number of idle connections HikariCP tries to maintain, ready for immediate use.
- `connection-timeout` — how long (ms) a thread will wait for a connection from the pool before throwing an exception, if the pool is exhausted.
- `idle-timeout` — how long (ms) a connection can sit idle in the pool before being closed (only applies if the pool has more than `minimum-idle` connections).
- `max-lifetime` — maximum lifetime (ms) of a connection before it's retired and replaced, even if it's healthy — this prevents issues with stale connections and works well with typical MySQL `wait_timeout` settings.

Sizing guidance (commonly asked): pool size should not simply be "as large as possible." A common starting formula (from HikariCP's own guidance, based on the Oracle/PostgreSQL "connections = ((core_count \* 2) + effective_spindle_count)" idea) is to keep pool size relatively small (often in the 10-20 range per app instance) — too large a pool can actually reduce throughput due to context-switching and lock contention on the database side; the bottleneck is usually CPU/IO on the DB, not the number of open connections.

Interview angle (especially relevant given Spring Boot background): explain why connection pooling matters (avoiding repeated handshake/auth cost), name HikariCP as Spring Boot's default, and mention that pool size is deliberately kept moderate rather than maximized, since database throughput — not connection count — is usually the real bottleneck.

---

## 22. Database Migration Tools (Flyway, Liquibase)

Migration tools version-control your database schema changes the same way Git version-controls your code, so schema changes are tracked, repeatable, and applied consistently across dev/test/prod environments.

Flyway:

- Migrations are plain versioned SQL files (or Java classes for complex logic), named with a strict convention:

```
V1__create_employees_table.sql
V2__add_phone_column_to_employees.sql
V3__create_departments_table.sql
```

- Flyway tracks which migrations have already run in a metadata table (`flyway_schema_history`), so it only applies new ones on each run.

```sql
-- V1__create_employees_table.sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100) NOT NULL
);
```

Spring Boot integration: just add the `flyway-mysql` dependency, drop SQL files in `src/main/resources/db/migration`, and Flyway runs them automatically on application startup by default.

Liquibase:

- Migrations (called "changesets") can be written in XML, YAML, JSON, or SQL — more format flexibility than Flyway.
- Tracks applied changes in `DATABASECHANGELOG` table, and also maintains a `DATABASECHANGELOGLOCK` table to prevent concurrent migration runs from colliding.

```xml
<changeSet id="1" author="jagir">
    <createTable tableName="employees">
        <column name="emp_id" type="int" autoIncrement="true">
            <constraints primaryKey="true"/>
        </column>
        <column name="emp_name" type="varchar(100)">
            <constraints nullable="false"/>
        </column>
    </createTable>
</changeSet>
```

- Liquibase also supports rollback definitions per changeset, giving more structured rollback capability than Flyway's default community edition (Flyway's free/community rollback support is more limited; paid Flyway Teams edition adds undo migrations).

Flyway vs Liquibase — quick comparison for interviews:

```
Aspect              Flyway                          Liquibase
Format              Mainly plain SQL                SQL, XML, YAML, JSON
Learning curve      Simpler, closer to raw SQL       More abstraction, steeper learning curve
Rollback            Limited in free version          Built-in structured rollback support
Spring Boot default Very commonly used, simple setup Also well supported, slightly more config
```

Why migration tools matter in real teams: without them, schema changes get applied manually and inconsistently across environments, leading to "works on my machine" database drift, and no clear history of who changed what schema and when — migrations solve this the same way source control solves uncoordinated code changes.

Interview angle: know that both tools solve the same core problem (versioned, repeatable schema changes) and be ready to name at least one concrete difference (SQL-first vs multi-format, or rollback support) — this is commonly asked in Spring Boot / full-stack interviews specifically because of how naturally it pairs with your JPA/Hibernate experience.

---

## 23. Common Interview Questions Quick Reference

Short-answer style questions that come up repeatedly, with one-line answers to anchor a fuller explanation:

- Difference between PRIMARY KEY and UNIQUE KEY: PK cannot be NULL and only one per table; UNIQUE allows one NULL (in most DBs) and multiple per table.
- Difference between DELETE, TRUNCATE, DROP: DELETE removes rows (can filter, can rollback, is DML), TRUNCATE removes all rows fast (cannot filter, resets identity, is DDL), DROP removes the entire table structure and data.
- Difference between CHAR and VARCHAR: CHAR is fixed length, padded with spaces; VARCHAR is variable length, stores actual length used.
- Why use DECIMAL over FLOAT for money: DECIMAL is exact, FLOAT/DOUBLE are approximate binary representations causing rounding errors.
- WHERE vs HAVING: WHERE filters rows before grouping, cannot use aggregates; HAVING filters groups after aggregation, can use aggregates.
- What is a clustered index: the index that determines the physical storage order of table data; in InnoDB this is always the primary key.
- Clustered vs non-clustered (secondary) index: clustered index leaf nodes ARE the actual row data; secondary index leaf nodes store the indexed value plus a pointer (the PK value) back to the actual row.
- What is normalization vs denormalization: normalization reduces redundancy for data integrity (good for OLTP writes); denormalization adds redundancy back for read performance (good for OLAP/reporting).
- ACID properties: Atomicity, Consistency, Isolation, Durability.
- Default isolation level in MySQL: Repeatable Read (InnoDB).
- What causes a deadlock and how to prevent it: circular wait between transactions holding locks the other needs; prevent via consistent lock ordering and short transactions.
- Difference between stored procedure and function: function must return a single value and can be used inline in SQL expressions; procedure can return zero or many outputs/result sets and is called standalone via CALL.
- Can MySQL do FULL OUTER JOIN natively: no, must simulate with LEFT JOIN UNION RIGHT JOIN.
- Does MySQL support materialized views natively: no, must simulate with a summary table plus scheduled refresh.
- ROW_NUMBER vs RANK vs DENSE_RANK with ties (values 90, 90, 80): ROW_NUMBER gives 1,2,3; RANK gives 1,1,3; DENSE_RANK gives 1,1,2.
- InnoDB vs MyISAM: InnoDB supports transactions, row-level locking, foreign keys; MyISAM does not support transactions, uses table-level locking, no foreign keys.
- What is the N+1 query problem (common with JPA/Hibernate): fetching a list of parent entities (1 query) then lazily fetching related child entities separately for each parent (N additional queries), instead of a single JOIN FETCH query; fixed using JOIN FETCH, `@EntityGraph`, or batch fetching.
- Leftmost prefix rule for composite indexes: a composite index on (A, B, C) can be used for queries filtering on A, or A+B, or A+B+C, but not on B or C alone.
- Covering index: an index containing all columns a query needs, so MySQL never has to look up the actual table row.

This section is meant as rapid-fire revision the night before an interview — if any line doesn't fully make sense, go back to the matching numbered part above for the full explanation.
