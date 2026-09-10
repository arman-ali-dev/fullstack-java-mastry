# SQL Advanced Querying

## ALTER Command

### What is it?

Used to change the structure of an existing table — add, remove, or modify columns.

### Example

```sql
ALTER TABLE students ADD COLUMN email VARCHAR(100);
ALTER TABLE students DROP COLUMN email;
```

### Important Point

ALTER changes the table structure, not the data inside it.

---

## CHANGE and MODIFY Commands

### What is it?

Both are used with `ALTER TABLE` to update an existing column's definition (like its datatype or name).

### Example

```sql
-- MODIFY: change datatype only, column name stays the same
ALTER TABLE students MODIFY name VARCHAR(100);

-- CHANGE: can rename the column AND change its datatype
ALTER TABLE students CHANGE name full_name VARCHAR(100);
```

### Important Point

Use `MODIFY` when only the datatype needs to change. Use `CHANGE` when you also want to rename the column.

---

## TRUNCATE Command

### What is it?

Removes all rows from a table instantly, but keeps the table structure.

### Example

```sql
TRUNCATE TABLE students;
```

### Important Point

- `TRUNCATE` = fast, removes all data, resets auto-increment, cannot use WHERE.
- `DELETE` = row-by-row, can use WHERE, slower on large tables.
- `DROP` = removes the whole table (structure + data).

---

## JOINS in SQL

### What is it?

Combines rows from two or more tables based on a related column (usually a foreign key).

### Why do we use it?

Real data is spread across multiple tables (e.g., `students` and `orders`). JOINS let you pull related data together in one query.

### Example

```sql
SELECT students.name, orders.item
FROM students
JOIN orders ON students.id = orders.student_id;
```

### Important Point

Types of joins:

- **INNER JOIN** → only matching rows from both tables (default JOIN)
- **LEFT JOIN** → all rows from left table + matched rows from right (unmatched = NULL)
- **RIGHT JOIN** → all rows from right table + matched rows from left (unmatched = NULL)
- **FULL JOIN** → all rows from both tables (MySQL doesn't support this directly — done using UNION of LEFT and RIGHT JOIN)

This is one of the most important SQL topics for a full-stack developer — used constantly in real apps.

---

## UNION in SQL

### What is it?

Combines the results of two SELECT queries into one result set (stacks rows, not columns).

### Example

```sql
SELECT name FROM students
UNION
SELECT name FROM teachers;
```

### Important Point

- Both SELECT queries must have the same number of columns and compatible datatypes.
- `UNION` removes duplicate rows automatically.
- `UNION ALL` keeps duplicates (and is faster since it skips the duplicate check).

---

## SQL Subqueries

### What is it?

A query written inside another query — used when you need the result of one query to run another.

### Example

```sql
SELECT name FROM students
WHERE id IN (SELECT student_id FROM orders WHERE item = 'Laptop');
```

This finds student names who ordered a 'Laptop', using the inner query first.

### Important Point

- Subquery runs first, its result is used by the outer query.
- Can be used inside `WHERE`, `SELECT`, or `FROM`.
- Often can be rewritten as a JOIN — JOINs are usually faster, but subqueries can be easier to read for complex logic.

---

## MySQL Views

### What is it?

A saved SQL query that acts like a virtual table. It doesn't store data itself — it just shows data from the underlying table(s) based on the query.

### Why do we use it?

Useful for reusing a complex query without rewriting it every time, and for showing only limited/selected data to certain users.

### Example

```sql
CREATE VIEW high_scorers AS
SELECT name, marks FROM students WHERE marks > 80;

SELECT * FROM high_scorers;
```

### Important Point

A view is always up-to-date since it runs the underlying query fresh each time it's used — it doesn't store its own copy of data.

---

## Composite Queries / Combining Multiple SQL Concepts

### What is it?

Real-world queries often combine several concepts together — JOIN + WHERE + GROUP BY + ORDER BY, etc., in one query.

### Example

```sql
SELECT students.course, COUNT(*) AS total_orders
FROM students
JOIN orders ON students.id = orders.student_id
WHERE orders.item = 'Laptop'
GROUP BY students.course
HAVING COUNT(*) > 1
ORDER BY total_orders DESC;
```

### Important Point

Build these step by step: start with the JOIN, then add WHERE, then GROUP BY, then HAVING/ORDER BY. Don't try to write it all at once — build and test in pieces.

---

## Practice Questions

Assume two tables:

```sql
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    course VARCHAR(50)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    student_id INT,
    item VARCHAR(50),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

## MUST REMEMBER

- `ALTER` changes table structure; `MODIFY` changes a column's datatype; `CHANGE` renames a column and can change its datatype.
- `TRUNCATE` = wipe all data fast (no WHERE); `DELETE` = row-by-row (can use WHERE); `DROP` = removes the whole table.
- JOINS combine tables: INNER (only matches), LEFT (all from left), RIGHT (all from right). This is a must-know topic.
- `UNION` stacks results of two SELECTs (removes duplicates); `UNION ALL` keeps duplicates.
- Subqueries = a query inside a query, often usable inside WHERE. Can often be rewritten as a JOIN.
- A View = a saved query that acts like a virtual table; always shows fresh/live data.
- Complex queries are just combinations of basic clauses — build step by step (JOIN → WHERE → GROUP BY → HAVING → ORDER BY).
