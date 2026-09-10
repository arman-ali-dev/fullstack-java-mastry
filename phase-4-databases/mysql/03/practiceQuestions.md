
### Questions

1. Rename the `course` column in `students` to `program`.
2. Change the datatype of `item` in `orders` to `VARCHAR(100)`.
3. Delete all data from `orders` but keep the table structure.
4. Get each student's name along with the items they ordered (only students who have orders).
5. Get all students' names along with their orders, including students with no orders.
6. Combine all student names and all order items into a single list (no duplicates).
7. Find names of students who ordered a `'Laptop'` (using a subquery).
8. Create a view that shows only students enrolled in `'DevOps'`.
9. Find each course's total number of orders placed, but only show courses with more than 1 order.

### Answers

1. `ALTER TABLE students CHANGE course program VARCHAR(50);`
2. `ALTER TABLE orders MODIFY item VARCHAR(100);`
3. `TRUNCATE TABLE orders;`
4.

```sql
SELECT students.name, orders.item
FROM students
JOIN orders ON students.id = orders.student_id;
```

5.

```sql
SELECT students.name, orders.item
FROM students
LEFT JOIN orders ON students.id = orders.student_id;
```

6.

```sql
SELECT name FROM students
UNION
SELECT item FROM orders;
```

7.

```sql
SELECT name FROM students
WHERE id IN (SELECT student_id FROM orders WHERE item = 'Laptop');
```

8.

```sql
CREATE VIEW devops_students AS
SELECT * FROM students WHERE course = 'DevOps';
```

9.

```sql
SELECT students.course, COUNT(*) AS total_orders
FROM students
JOIN orders ON students.id = orders.student_id
GROUP BY students.course
HAVING COUNT(*) > 1;
```

---
