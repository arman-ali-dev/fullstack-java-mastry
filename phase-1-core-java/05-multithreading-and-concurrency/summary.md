# Java Multithreading & Concurrency - Quick Recall Summary

One-line-per-concept summary. Goal: read it, remember what the topic does, not full syntax.

---

## PART 1 - What is Multithreading and Why

- Multiple threads run at the "same time" so a program stays responsive instead of freezing on one long task
- A **Thread** is the smallest unit of CPU execution; every Java program starts with one **main thread**
- Threads share the same heap memory — powerful but dangerous (can read/write the same variables)

## PART 2 - Creating a Thread

- **Extend Thread** — override `run()`, call `.start()`; downside: class can't extend anything else, task and thread are the same object
- **Implement Runnable (preferred)** — separate task from thread, pass to `new Thread(task)`; class can still extend something else
- **Lambda** — shortest modern form, since Runnable has just one method
- **Critical rule:** always call `.start()`, never `.run()` directly — calling `.run()` just runs it like a normal method on the current thread, no new thread created

## PART 3 - Thread Lifecycle

- States: **NEW** → **RUNNABLE** → (RUNNING conceptually) → **BLOCKED / WAITING / TIMED_WAITING** → **TERMINATED**
- NEW = created, not started; RUNNABLE = ready, waiting for CPU; BLOCKED = waiting to enter a synchronized block someone else holds
- WAITING = waiting indefinitely (e.g. `join()` with no timeout, `wait()`); TIMED_WAITING = same but with a time limit (e.g. `sleep()`, `join(ms)`)
- TERMINATED = finished; can't be restarted — throws `IllegalThreadStateException`
- **join()** — calling thread waits for target thread to finish; essential when one task depends on another's result
- **Daemon threads** — background threads JVM does NOT wait for; killed automatically when all normal threads finish; set with `setDaemon(true)` BEFORE `start()`

## PART 4 - Runnable vs Callable

- **Runnable** — `run()`, returns nothing, can't throw checked exceptions, used directly with Thread
- **Callable\<T\>** — `call()`, returns a value of type T, CAN throw checked exceptions, used with ExecutorService
- **Future** — a placeholder for a result that will be available later; `future.get()` blocks until the task is done (or times out); `isDone()`, `cancel()`, `isCancelled()` also available
- Exceptions thrown inside a Callable are wrapped in `ExecutionException` when you call `future.get()`

## PART 5 - ExecutorService and Thread Pool

- Creating threads is expensive — a **thread pool** creates a fixed set of reusable threads instead of a new one per task
- **Fixed pool** — exactly n threads, extra tasks queue; **Single thread executor** — one thread, sequential; **Cached pool** — grows/shrinks dynamically, good for many short tasks; **Scheduled pool** — delayed/repeating tasks
- `execute()` — fire-and-forget Runnable; `submit(Runnable)` — returns Future (null result); `submit(Callable)` — returns Future with real result
- **shutdown()** — graceful, finishes submitted tasks, stops accepting new ones; **shutdownNow()** — forceful, interrupts running tasks; **awaitTermination()** — waits for shutdown to finish
- **ScheduledExecutorService** — `schedule()` runs once after a delay; `scheduleAtFixedRate()` runs repeatedly based on start time; `scheduleWithFixedDelay()` waits a gap after each run ends

## PART 6 - synchronized Keyword

- **Race condition** — two threads read-modify-write shared data at the same time, causing lost updates (e.g. `count++` is 3 separate steps, not atomic)
- **synchronized method** — only one thread can execute it at a time, using the object's built-in **monitor lock**
- **synchronized block** — locks only the critical section, letting non-critical code run freely for other threads
- **static synchronized** — locks on the Class object itself, shared across all instances
- **Deadlock** — two threads each hold a lock the other needs, both wait forever; avoid by always acquiring multiple locks in the SAME order

## PART 7 - volatile Keyword

- **Memory visibility problem** — a thread may read a stale cached value instead of the latest one written by another thread
- `volatile` forces every read/write to go straight to main memory — fixes visibility, but NOT atomicity
- Good for simple flags/status variables written by one thread and read by others; NOT enough for compound operations like `count++` (still needs synchronized/Atomic)

## PART 8 - Atomic Classes

- Give both visibility AND atomicity WITHOUT locks, using a fast CPU-level **CAS (Compare-And-Swap)** operation
- **AtomicInteger/Long/Boolean/Reference** — thread-safe operations: `incrementAndGet()`, `getAndIncrement()`, `addAndGet()`, `compareAndSet(expected, new)`
- `compareAndSet` is the key building block: "if value is still what I expect, update it — otherwise fail and retry" (used in retry loops like limited-stock purchases)

## PART 9 - ReentrantLock and ReadWriteLock

- **ReentrantLock** — more flexible than synchronized: supports `tryLock()` (non-blocking attempt), timeouts, interruptible waiting, and fairness
- Always call `unlock()` in a `finally` block — otherwise an exception can leave the lock held forever
- **Fair lock** (`new ReentrantLock(true)`) — gives the lock to the longest-waiting thread, prevents starvation, but is slower
- **ReadWriteLock** — separate read lock (shared, many readers at once) and write lock (exclusive, blocks everyone); best for read-heavy shared data

## PART 10 - Concurrent Collections

- Regular ArrayList/HashMap are NOT thread-safe — concurrent modification breaks them; avoid the old `Collections.synchronizedX()` wrappers (lock the whole collection, slow)
- **ConcurrentHashMap** — thread-safe map with segment-level locking, plus atomic helpers: `putIfAbsent`, `compute`, `computeIfAbsent`, `merge`
- **CopyOnWriteArrayList** — every write creates a new internal array copy; readers never block; good for many-reads/few-writes scenarios
- **BlockingQueue** — `put()`/`take()` block when full/empty; ideal for producer-consumer patterns; `offer()`/`poll()` have non-blocking or timeout variants

## PART 11 - CompletableFuture

- Solves Future's limitations: no forced blocking, supports chaining, combining, and exception callbacks
- **supplyAsync()** — async task with a return value; **runAsync()** — async task with no return value
- **thenApply()** — transform the result (like `map`); **thenAccept()** — consume the result, return nothing; **thenRun()** — run after, ignore the result entirely
- **thenCompose()** — chain another ASYNC step (flattens nested futures, like `flatMap`) — use instead of `thenApply` when the next step also returns a CompletableFuture
- **thenCombine()** — merge results of two independent parallel tasks once both finish
- **allOf()** — wait for all given futures to complete; **anyOf()** — get the result of whichever finishes first
- **exceptionally()** — runs only on exception, supplies a fallback value; **handle()** — runs always, receives both result and exception (whichever is non-null)

## PART 12 - Common Problems and How to Avoid Them

- **Race condition** — shared mutable data modified by multiple threads without protection → fix with synchronized/Atomic/Lock
- **Deadlock** — circular lock-waiting between threads → fix by always acquiring locks in a consistent order
- **Memory visibility** — stale cached reads → fix with `volatile` or synchronized
- **Starvation** — a thread never gets the lock because others keep winning it → fix with a fair lock
- **Livelock** — threads keep reacting to each other without making real progress → fix by adding randomness/backoff

---

## One-Glance Cheat Table

| Topic                                             | What it does                                                  |
| ------------------------------------------------- | ------------------------------------------------------------- |
| Thread creation                                   | Runnable/lambda preferred over extending Thread directly      |
| Thread lifecycle                                  | NEW → RUNNABLE → (BLOCKED/WAITING/TIMED_WAITING) → TERMINATED |
| Runnable vs Callable                              | No return value vs returns a result via Future                |
| ExecutorService                                   | Reusable thread pool instead of creating threads manually     |
| synchronized                                      | One thread at a time per lock — visibility + atomicity        |
| volatile                                          | Visibility only, no atomicity — for simple flags              |
| Atomic classes                                    | Lock-free thread-safe operations via CAS                      |
| ReentrantLock                                     | Flexible locking — tryLock, timeout, fairness                 |
| ReadWriteLock                                     | Many concurrent readers OR one exclusive writer               |
| Concurrent collections                            | Thread-safe Map/List/Queue built for concurrency              |
| CompletableFuture                                 | Non-blocking async chaining, combining, error handling        |
| Race condition / Deadlock / Starvation / Livelock | The four classic concurrency bugs and their standard fixes    |
