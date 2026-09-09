# Database Fundamentals — Notes

## 1. Database

- Organized collection of data stored electronically
- Data is stored so it can be easily accessed, managed, updated

## 2. DBMS (Database Management System)

- Software that lets you create, manage, and interact with databases
- User → DBMS → Database (DBMS sits between user and actual data)
- Examples: MySQL, Oracle, MongoDB, PostgreSQL, SQL Server

## 3. Types of Databases

- **Relational (SQL)** → data in tables (rows & columns)
- **Non-Relational (NoSQL)** → data in flexible formats (documents, key-value, graphs)
- Others: Hierarchical, Network, Object-oriented (less common today)

## 4. Relational Database (with Example)

- Stores data in **tables**, tables are linked using **keys**
- Follows a fixed schema (structure defined beforehand)
- Example: `Students` table linked to `Courses` table via `student_id`
- Examples of RDBMS: MySQL, PostgreSQL, Oracle, SQL Server

## 5. Non-Relational Database (with Example)

- No fixed table structure → flexible/dynamic schema
- Types: Document-based, Key-Value, Column-based, Graph-based
- Example: MongoDB stores data as JSON-like documents

```json
{ "name": "Arman", "course": "Cloud Computing" }
```

- Good for unstructured/fast-changing data

## 6. SQL (Structured Query Language)

- Language used to talk to relational databases
- Used to: create, read, update, delete data (CRUD)
- Categories:
  - **DDL** (Data Definition) → CREATE, ALTER, DROP
  - **DML** (Data Manipulation) → INSERT, UPDATE, DELETE
  - **DQL** (Data Query) → SELECT
  - **DCL** (Data Control) → GRANT, REVOKE

## 7. MySQL

- Open-source RDBMS that uses SQL
- Widely used for websites/apps (works well with PHP, Node.js, etc.)
- Client-server model → MySQL server stores data, client sends queries

## 8. Table

- Basic unit of storage in relational DB
- Made up of **rows** and **columns**
- Each table represents one entity (e.g., `Students`, `Orders`)

## 9. Column

- Represents one attribute/field of data (e.g., `name`, `age`)
- Has a fixed data type (int, varchar, date, etc.)
- Also called a **field**

## 10. Row

- One single record/entry in a table
- Contains actual data values for each column
- Also called a **tuple** or **record**

## 11. Creating Our First Database

```sql
CREATE DATABASE school;
USE school;
```

- `CREATE DATABASE` → makes new database
- `USE` → selects which database to work in

## 12. Creating Our First Table

```sql
CREATE TABLE students (
    id INT,
    name VARCHAR(50),
    age INT
);
```

- Define table name + columns + datatypes inside `()`

## 13. SQL Datatypes

- **Numeric** → INT, FLOAT, DOUBLE, DECIMAL
- **String** → VARCHAR(n), CHAR(n), TEXT
- **Date/Time** → DATE, TIME, DATETIME, TIMESTAMP
- **Boolean** → BOOLEAN (stored as TINYINT in MySQL)
- Choose datatype based on kind + size of data expected

## 14. Database Related Queries

```sql
SHOW DATABASES;        -- list all databases
CREATE DATABASE db1;   -- create new
USE db1;               -- switch to db
DROP DATABASE db1;     -- delete database
```

## 15. Table Related Queries

```sql
SHOW TABLES;                  -- list tables in current db
DESCRIBE students;            -- show table structure
ALTER TABLE students ADD COLUMN email VARCHAR(100);  -- add column
DROP TABLE students;          -- delete table
```

## 16. Keys — Primary Key and Foreign Key

- **Primary Key (PK)**
  - Uniquely identifies each row in a table
  - Cannot be NULL, cannot repeat
  - Example: `id` in `students` table
- **Foreign Key (FK)**
  - Column that refers to Primary Key of another table
  - Used to create relationship between two tables
  - Example: `student_id` in `Orders` table refers to `id` in `Students` table

```sql
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    student_id INT,
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

## 17. Constraints

- Rules applied on columns to control what data can go in
- Common constraints:
  - `NOT NULL` → value must be given
  - `UNIQUE` → no duplicate values allowed
  - `PRIMARY KEY` → unique + not null (identifier)
  - `FOREIGN KEY` → links to another table
  - `DEFAULT` → sets default value if none given
  - `CHECK` → value must satisfy a condition

## 18. Revisiting Foreign Keys

- FK maintains **referential integrity** → child table data must match parent table
- Can't insert a value in FK column that doesn't exist in the parent's PK
- Can't delete a parent row if child rows still reference it (unless cascading is set)

## 19. Cascading Foreign Keys

- Defines what happens to child rows when parent row is updated/deleted
- Options:
  - `ON DELETE CASCADE` → delete child rows automatically when parent deleted
  - `ON UPDATE CASCADE` → update child rows automatically when parent key updated
  - `ON DELETE SET NULL` → set FK to NULL when parent deleted
  - `ON DELETE RESTRICT` → block deletion if child rows exist (default-like behavior)

```sql
FOREIGN KEY (student_id) REFERENCES students(id)
ON DELETE CASCADE
ON UPDATE CASCADE;
```

## 20. Normalization — 1NF, 2NF, 3NF

- Process of organizing data to reduce **redundancy** and avoid **anomalies**

**1NF (First Normal Form)**

- Each column has atomic (single) values → no multiple values in one cell
- Each row must be unique

**2NF (Second Normal Form)**

- Must satisfy 1NF
- No **partial dependency** → non-key column should depend on the WHOLE primary key (matters when PK is composite/multiple columns)

**3NF (Third Normal Form)**

- Must satisfy 2NF
- No **transitive dependency** → non-key column should not depend on another non-key column
- Every non-key column should depend only on the primary key

**Why normalize?**

- Avoids duplicate data
- Avoids update/insert/delete anomalies
- Keeps database clean and consistent
