# SQL Basics & CRUD — Practice Questions

Assume this table:

```sql
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT,
    course VARCHAR(50),
    marks INT
);
```

## Questions

1. Get all columns of all students.
2. Get only `name` and `course` of all students.
3. Get all students older than 21.
4. Get all students whose course is `'DevOps'`.
5. Get all students whose age is between 20 and 25.
6. Get all students whose name starts with `'A'`.
7. Get all students enrolled in either `'DevOps'` or `'Cloud'`.
8. Get all students sorted by marks, highest first.
9. Get only the top 3 students by marks.
10. Count how many students are in the table.
11. Find the average marks of all students.
12. Find the number of students in each course.
13. Find courses that have more than 2 students enrolled.
14. Insert a new student: id=10, name='Neha', age=21, course='Cloud', marks=85.
15. Update the marks of the student with id=10 to 90.
16. Delete the student with id=10.

## Answers

1. `SELECT * FROM students;`
2. `SELECT name, course FROM students;`
3. `SELECT * FROM students WHERE age > 21;`
4. `SELECT * FROM students WHERE course = 'DevOps';`
5. `SELECT * FROM students WHERE age BETWEEN 20 AND 25;`
6. `SELECT * FROM students WHERE name LIKE 'A%';`
7. `SELECT * FROM students WHERE course IN ('DevOps', 'Cloud');`
8. `SELECT * FROM students ORDER BY marks DESC;`
9. `SELECT * FROM students ORDER BY marks DESC LIMIT 3;`
10. `SELECT COUNT(*) FROM students;`
11. `SELECT AVG(marks) FROM students;`
12. `SELECT course, COUNT(*) FROM students GROUP BY course;`
13. `SELECT course, COUNT(*) FROM students GROUP BY course HAVING COUNT(*) > 2;`
14. `INSERT INTO students (id, name, age, course, marks) VALUES (10, 'Neha', 21, 'Cloud', 85);`
15. `UPDATE students SET marks = 90 WHERE id = 10;`
16. `DELETE FROM students WHERE id = 10;`
