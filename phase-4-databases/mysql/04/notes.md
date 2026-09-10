# Database Performance & Important Concepts

## Indexes — Basic Concept

### What is it?

A special structure that helps the database find rows faster, without scanning the whole table. Like an index at the back of a book — instead of reading every page, you jump straight to what you need.

### Why do we use it?

Without an index, the database checks every single row to find a match (slow on large tables). With an index, it can locate rows much faster.

### Example

```sql
CREATE INDEX idx_name ON students(name);
```

### Important Point

- Indexes speed up `SELECT`/`WHERE`/`ORDER BY` queries.
- They slow down `INSERT`/`UPDATE`/`DELETE` a little, because the index also needs updating.
- Primary keys are automatically indexed.
- Don't add indexes on every column — only on columns you search/filter/sort by often.

---

## Composite Indexes

### What is it?

An index built on more than one column together, instead of just one.

### Why do we use it?

Useful when you often filter/search using multiple columns together (e.g., `course` + `age`).

### Example

```sql
CREATE INDEX idx_course_age ON students(course, age);
```

### Important Point

Column order matters — this index works best when your query filters by `course` first, or `course` + `age` together. It won't help much if you filter by `age` alone.

---

## EXPLAIN — Basic Query Execution Plan

### What is it?

A command that shows HOW MySQL will actually run your query — whether it uses an index, how many rows it scans, etc.

### Why do we use it?

Helps you check if your query is efficient or if it's doing a slow full-table scan.

### Example

```sql
EXPLAIN SELECT * FROM students WHERE name = 'Arman';
```

### Important Point

You don't need to master this deeply now — just know it exists and is used to debug slow queries.

---

## Transactions

### What is it?

A group of one or more SQL operations that are executed together as a single unit — either all of them succeed, or none of them do.

### Why do we use it?

Some operations need to happen together. Example: transferring money — deduct from one account AND add to another. If only one part happens, data becomes wrong/inconsistent.

### Example

```sql
START TRANSACTION;

UPDATE accounts SET balance = balance - 500 WHERE id = 1;
UPDATE accounts SET balance = balance + 500 WHERE id = 2;

COMMIT;
```

### Important Point

This is a core concept — used constantly in real backend applications (Spring Boot's `@Transactional` is built on this exact idea).

---

## ACID Properties

### What is it?

Four properties that guarantee transactions are safe and reliable.

### Important Point

- **Atomicity** → all steps happen, or none do (no half-done transactions)
- **Consistency** → data stays valid/correct before and after the transaction
- **Isolation** → transactions running at the same time don't mess with each other
- **Durability** → once committed, data is saved permanently, even if the system crashes right after

This is a very common interview topic — know all 4 by name and in one line each.

---

## COMMIT

### What is it?

Saves all changes made during the current transaction permanently.

### Example

```sql
COMMIT;
```

### Important Point

Once you COMMIT, the changes can't be undone with ROLLBACK anymore.

---

## ROLLBACK

### What is it?

Undoes all changes made in the current transaction (since the last COMMIT).

### Example

```sql
ROLLBACK;
```

### Important Point

Useful if something goes wrong mid-transaction (e.g., an error occurs) — you can cancel everything and go back to the last saved state.

---

## SAVEPOINT

### What is it?

A marker inside a transaction that lets you roll back to a specific point, instead of undoing the whole transaction.

### Example

```sql
START TRANSACTION;

UPDATE students SET marks = 90 WHERE id = 1;
SAVEPOINT sp1;

UPDATE students SET marks = 100 WHERE id = 2;
ROLLBACK TO sp1;  -- undoes only the second update

COMMIT;
```

### Important Point

Gives you more control — useful in long transactions with multiple steps.

---

## Isolation Levels — Basic Understanding

### What is it?

Rules that control how much one transaction can "see" of another transaction's changes while both are running at the same time.

### Important Point

From least to most strict:

- **Read Uncommitted** → can see uncommitted changes from other transactions (risky, rarely used)
- **Read Committed** → only sees changes that are already committed
- **Repeatable Read** → same query gives the same result throughout the transaction (MySQL's default)
- **Serializable** → strictest, transactions run as if one at a time (safest but slowest)

Higher isolation = safer, but slower (less concurrency). You just need to know these levels exist and the basic trade-off.

---

## Row-Level vs Table-Level Locking — Basic Understanding

### What is it?

When a transaction is running, the database locks data so other transactions don't interfere.

### Important Point

- **Row-level locking** → only locks the specific row(s) being changed. Other rows in the table stay available. (More concurrency, used by InnoDB — MySQL's default engine)
- **Table-level locking** → locks the entire table, so no one else can touch any row until it's released. (Simpler, but blocks more)

Row-level locking is what you'll deal with in real Java/Spring Boot apps using MySQL's default engine.

---

## Deadlocks — Basic Understanding

### What is it?

A situation where two transactions are each waiting for the other to release a lock — so neither can proceed, and both get stuck.

### Example

- Transaction A locks Row 1, waits for Row 2.
- Transaction B locks Row 2, waits for Row 1.
- Both wait forever → deadlock.

### Important Point

MySQL usually detects deadlocks automatically and cancels (rolls back) one of the transactions so the other can continue. As a developer, just know deadlocks can happen when multiple transactions touch the same rows in different orders.

---

## MUST REMEMBER

- Indexes speed up searching/sorting but slightly slow down writes — use them on columns you query often.
- `EXPLAIN` shows how a query runs — use it to check if a query is slow.
- A Transaction = group of operations that succeed or fail together.
- ACID = Atomicity, Consistency, Isolation, Durability — know all 4 (common interview question).
- `COMMIT` saves changes permanently; `ROLLBACK` undoes them; `SAVEPOINT` lets you undo partially.
- Isolation levels control how transactions see each other's changes — MySQL default is Repeatable Read.
- Row-level locking (MySQL default) locks only affected rows; table-level locks the whole table.
- A deadlock happens when two transactions wait on each other forever; MySQL auto-resolves it by rolling one back.

## CAN LOOK UP LATER

- Composite index column-order rules in detail — just remember order matters.
- Exact behavior differences between all 4 isolation levels — know they exist and the basic trade-off (safety vs speed), deep-dive only if needed.
- How MySQL's deadlock detection algorithm works internally — not needed at this level.
