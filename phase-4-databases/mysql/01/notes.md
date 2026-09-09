# Database Fundamentals

## Database

### What is it?
A place where data is stored in an organized way, so it can be saved, searched, and updated easily.

### Example
A "Students" database that stores names, ages, and marks of students.

---

## DBMS (Database Management System)

### What is it?
Software that manages the database. You never talk to the database directly — you talk to the DBMS, and it handles the data for you.

### Why do we use it?
It handles storing, retrieving, updating, and securing data, so you don't have to manage raw files yourself.

### Important Point
Examples: MySQL, PostgreSQL, Oracle, MongoDB. As a developer, your app connects to the DBMS, not the raw data files.

---

## Types of Databases

### What is it?
- **Relational (SQL)** → data stored in tables (rows & columns)
- **Non-Relational (NoSQL)** → data stored in flexible formats (documents, key-value pairs, etc.)

### Important Point
As a full-stack dev, you'll mostly use relational DBs (MySQL/PostgreSQL) with Spring Boot apps, but NoSQL (like MongoDB) is common too.

---

## Relational Database (with Example)

### What is it?
Stores data in tables that are connected to each other using keys. Has a fixed structure (schema) — every row in a table has the same columns.

### Example
A `Students` table connected to a `Courses` table using `student_id`.

### Important Point
Best when your data has clear relationships (e.g., a student has many orders, an order belongs to one student).

---

## Non-Relational Database (with Example)

### What is it?
Stores data without a fixed table structure. Data can be stored as documents (like JSON), key-value pairs, etc.

### Example
MongoDB stores a student like this:
```json
{ "name": "Arman", "course": "Cloud Computing" }
```

### Important Point
Good when your data structure keeps changing or doesn't fit neatly into rows/columns.

---

## SQL (Structured Query Language)

### What is it?
The language used to communicate with relational databases — to create, read, update, and delete data.

### Why do we use it?
Every relational DB (MySQL, PostgreSQL, Oracle) understands SQL, so learning it once lets you work with any of them.

### Important Point
Main categories:
- **DDL** – define structure (CREATE, ALTER, DROP)
- **DML** – manage data (INSERT, UPDATE, DELETE)
- **DQL** – query data (SELECT)

---

## MySQL

### What is it?
A popular open-source relational database that uses SQL. Works on a client-server model — the server stores the data, and clients (your app) send queries to it.

### Important Point
Very commonly paired with Java/Spring Boot backends in full-stack projects.

---

## Table

### What is it?
The basic structure that holds data in a relational database. Made of rows and columns. One table usually represents one "thing" (entity) — like `Students`, `Orders`, `Products`.

---

## Column

### What is it?
Represents one property/attribute of the data (e.g., `name`, `age`). Each column has a fixed datatype.

### Important Point
Also called a "field."

---

## Row

### What is it?
One single record of data in a table — all the column values for one entry.

### Important Point
Also called a "record" or "tuple."

---

## Creating Our First Database

### Example
```sql
CREATE DATABASE school;
USE school;
```

### Important Point
`CREATE DATABASE` makes a new database, `USE` tells MySQL which database you want to work in.

---

## Creating Our First Table

### Example
```sql
CREATE TABLE students (
    id INT,
    name VARCHAR(50),
    age INT
);
```

### Important Point
You define the table name, and then list each column with its datatype.

---

## SQL Datatypes

### What is it?
Defines what kind of value a column can hold.

### Important Point
Common ones you'll actually use:
- `INT` → whole numbers
- `VARCHAR(n)` → text (max n characters)
- `DATE` / `DATETIME` → dates and timestamps
- `DECIMAL` / `FLOAT` → numbers with decimals (e.g., price)
- `BOOLEAN` → true/false

---

## Database Related Queries

### Example
```sql
SHOW DATABASES;        -- list all databases
CREATE DATABASE db1;   -- create new database
USE db1;               -- switch to a database
DROP DATABASE db1;     -- delete a database
```

---

## Table Related Queries

### Example
```sql
SHOW TABLES;                  -- list tables in current database
DESCRIBE students;            -- see table structure
ALTER TABLE students ADD COLUMN email VARCHAR(100);  -- add a column
DROP TABLE students;          -- delete a table
```

---

## Keys — Primary Key and Foreign Key

### What is it?
- **Primary Key (PK)** → uniquely identifies each row in a table. Cannot be NULL or repeated.
- **Foreign Key (FK)** → a column in one table that points to the Primary Key of another table, creating a relationship between them.

### Why do we use it?
PK ensures every row is unique and identifiable. FK connects related tables together (e.g., linking an order to the student who placed it).

### Example
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

### Important Point
This is one of the most important concepts in relational databases — it's how tables relate to each other, which is exactly how JPA/Hibernate entity relationships work later.

---

## Constraints

### What is it?
Rules on a column that control what data is allowed.

### Important Point
- `NOT NULL` → value is required
- `UNIQUE` → no duplicate values
- `PRIMARY KEY` → unique + not null (identifier)
- `FOREIGN KEY` → links to another table
- `DEFAULT` → sets a default value
- `CHECK` → value must meet a condition

---

## Revisiting Foreign Keys

### What is it?
A foreign key keeps data consistent between two tables (called referential integrity).

### Important Point
- You can't insert a value in the FK column unless it already exists in the parent table.
- You normally can't delete a parent row if child rows are still linked to it (unless cascading rules are set — see next topic).

---

## Cascading Foreign Keys

### What is it?
Rules that decide what happens to child table rows when the related parent row is updated or deleted.

### Why do we use it?
Without cascading, deleting a parent row with linked child rows would give an error. Cascading automates what should happen instead.

### Example
```sql
FOREIGN KEY (student_id) REFERENCES students(id)
ON DELETE CASCADE
ON UPDATE CASCADE;
```

### Important Point
- `ON DELETE CASCADE` → deletes child rows automatically
- `ON DELETE SET NULL` → sets FK to NULL instead of deleting
- `ON DELETE RESTRICT` → blocks the delete if child rows exist (safer, default-like behavior)

---

## Normalization — 1NF, 2NF, 3NF

### What is it?
A way of organizing tables to avoid duplicate data and keep the database clean.

### Why do we use it?
Reduces repeated data and prevents issues when inserting, updating, or deleting records.

### Important Point
- **1NF** → each column holds only one value (no lists inside a cell), each row is unique
- **2NF** → 1NF + every non-key column depends on the WHOLE primary key (matters only when PK has multiple columns)
- **3NF** → 2NF + no non-key column depends on another non-key column (every column should depend only on the primary key)

---

## MUST REMEMBER
- DBMS manages the database; you interact through it (via SQL for relational DBs).
- Relational DB = tables + fixed schema; NoSQL = flexible structure (e.g., MongoDB documents).
- Table = rows (records) + columns (fields).
- Basic SQL commands: `CREATE`, `USE`, `SHOW`, `DESCRIBE`, `ALTER`, `DROP`.
- Primary Key = unique row identifier. Foreign Key = links two tables together.
- Constraints (`NOT NULL`, `UNIQUE`, `DEFAULT`, `CHECK`) control what data is valid.
- Cascading (`ON DELETE CASCADE`, etc.) controls what happens to related rows on delete/update.
- Normalization (1NF → 2NF → 3NF) removes duplicate data and keeps tables clean — important for good database design.

## CAN LOOK UP LATER
- Exact syntax of every SQL datatype (just remember the common ones: INT, VARCHAR, DATE, DECIMAL, BOOLEAN).
- Full list of cascading options — you'll rarely need more than CASCADE, SET NULL, RESTRICT.
- Deeper NoSQL database types (key-value, graph, column-based) — know they exist, don't need details now.
