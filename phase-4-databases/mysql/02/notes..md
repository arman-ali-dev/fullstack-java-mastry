# SQL Basics & CRUD

## SELECT Command

### What is it?

Used to read/fetch data from a table.

### Example

```sql
SELECT * FROM students;          -- get all columns
SELECT name, age FROM students;  -- get specific columns
```

### Important Point

`*` means "all columns" — fine for practice, but in real apps prefer selecting only the columns you need (better performance).

---

## INSERT Command

### What is it?

Used to add new rows (records) into a table.

### Example

```sql
INSERT INTO students (id, name, age)
VALUES (1, 'Arman', 22);
```

### Important Point

Column order in `VALUES` must match the column list. If you skip listing columns, you must give values for ALL columns in the exact table order.

---

## SELECT Command in Detail

### What is it?

SELECT can do more than just fetch raw data — you can filter, sort, limit, and combine it with other clauses (WHERE, ORDER BY, LIMIT, etc.) to control exactly what comes back.

### Important Point

Think of SELECT as the base command, and the other clauses below as add-ons that shape the result.

---

## WHERE Clause

### What is it?

Filters rows — only returns rows that match a condition.

### Example

```sql
SELECT * FROM students WHERE age > 20;
```

### Important Point

Without WHERE, SQL returns every row. WHERE is how you filter data — used constantly in real apps.

---

## Operators

### What is it?

Symbols/keywords used inside WHERE (or other clauses) to build conditions.

### Important Point

- Comparison: `=`, `!=` (or `<>`), `>`, `<`, `>=`, `<=`
- Logical: `AND`, `OR`, `NOT`
- Range: `BETWEEN ... AND ...`
- List match: `IN (val1, val2, ...)`
- Pattern match: `LIKE` (with `%` = any characters, `_` = one character)
- Null check: `IS NULL`, `IS NOT NULL`

### Example

```sql
SELECT * FROM students WHERE age BETWEEN 18 AND 25;
SELECT * FROM students WHERE name LIKE 'A%';
SELECT * FROM students WHERE course IN ('DevOps', 'Cloud');
```

---

## LIMIT Clause

### What is it?

Restricts how many rows are returned.

### Example

```sql
SELECT * FROM students LIMIT 5;
```

### Important Point

Very useful for pagination (showing data page-by-page in an app) or just previewing data.

---

## ORDER BY Clause

### What is it?

Sorts the result rows by one or more columns.

### Example

```sql
SELECT * FROM students ORDER BY age ASC;   -- smallest to largest
SELECT * FROM students ORDER BY age DESC;  -- largest to smallest
```

### Important Point

Default order is `ASC` (ascending) if you don't specify.

---

## Aggregate Functions

### What is it?

Functions that calculate a single summary value from many rows.

### Important Point

- `COUNT()` → number of rows
- `SUM()` → total of a numeric column
- `AVG()` → average
- `MAX()` / `MIN()` → highest / lowest value

### Example

```sql
SELECT COUNT(*) FROM students;
SELECT AVG(age) FROM students;
```

---

## GROUP BY Clause

### What is it?

Groups rows that share the same value in a column, so you can run aggregate functions per group instead of on the whole table.

### Example

```sql
SELECT course, COUNT(*)
FROM students
GROUP BY course;
```

This shows how many students are in each course.

### Important Point

Any column in SELECT that isn't inside an aggregate function must appear in GROUP BY.

---

## HAVING Clause

### What is it?

Filters groups after GROUP BY — like WHERE, but for grouped/aggregated data.

### Example

```sql
SELECT course, COUNT(*)
FROM students
GROUP BY course
HAVING COUNT(*) > 5;
```

This shows only courses with more than 5 students.

### Important Point

WHERE filters rows before grouping; HAVING filters groups after grouping. This difference is a common interview question.

---

## General Order of SQL Commands

### What is it?

The order in which you write a SELECT query's clauses:

```sql
SELECT columns
FROM table
WHERE condition
GROUP BY column
HAVING condition
ORDER BY column
LIMIT number;
```

### Important Point

SQL processes them in a slightly different logical order internally (FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY → LIMIT), but you always **write** them in the order shown above.

---

## UPDATE Command

### What is it?

Modifies existing rows in a table.

### Example

```sql
UPDATE students
SET age = 23
WHERE id = 1;
```

### Important Point

Always use WHERE with UPDATE — without it, ALL rows in the table get updated. This is a common and dangerous mistake.

---

## DELETE Command

### What is it?

Removes rows from a table.

### Example

```sql
DELETE FROM students WHERE id = 1;
```

### Important Point

Just like UPDATE, always use WHERE — without it, ALL rows get deleted.

---

## MUST REMEMBER

- SELECT = read data, INSERT = add data, UPDATE = modify data, DELETE = remove data (these four = CRUD).
- WHERE filters individual rows; HAVING filters groups (after GROUP BY).
- Common operators: `=`, `>`, `<`, `BETWEEN`, `IN`, `LIKE`, `IS NULL`.
- ORDER BY sorts results; LIMIT restricts how many rows come back.
- Aggregate functions (`COUNT`, `SUM`, `AVG`, `MAX`, `MIN`) summarize data — usually paired with GROUP BY.
- Query writing order: `SELECT → FROM → WHERE → GROUP BY → HAVING → ORDER BY → LIMIT`.
- Always use WHERE with UPDATE/DELETE, or you'll affect every row in the table.

## CAN LOOK UP LATER

- Exact wildcard behavior of LIKE (`%` vs `_`) — easy to check when needed.
- SQL's internal logical processing order — good to know exists, not something to memorize deeply right now.
