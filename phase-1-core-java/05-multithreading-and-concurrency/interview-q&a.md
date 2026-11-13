#### 1. What is multithreading?

Multithreading in Java is a feature that allows multiple threads to run concurrently within a single program.

---

#### 2. What is a thread?

A thread in Java is a small unit of execution within a program. It allows a program to perform a particular task independently.

---

#### 3. Why is it needed in real applications?

Multithreading is needed in real applications to perform multiple tasks concurrently. It improves application responsiveness and makes better use of CPU resources.
<br>
For example, in a web application, one thread can handle a user request while other threads handle different requests. This helps the application serve multiple users efficiently.

---

#### 4. What is the difference between a process and a thread?

A process is a program that is currently running. with own memory space. A thread is a smaller unit of execution within a process. A process can have multiple threads.
<br>
the main difference is that processes have own memory space while threads share the same memory withing a process. Threads are also lighter and generally faster to create than processes.

---

#### 5. What are the ways to create a thread in Java?

There are two main ways to create a thread in Java:

1. Extending the Thread class: Create a class that extends Thread and override its run() method.
2. Implementing the Runnable interface: Create a class that implements Runnable and override its run() method.

---

#### 6. Why is `Runnable` preferred over extending `Thread`?

Runnable is preferred over extending Thread because Java does not support multiple inheritance of classes.
By implementing Runnable, a class can extend another class if needed.

---

#### 7. What is the difference between `start()` and `run()`?

start() creates a new thread and internally calls the run() method. If we call run() directly, it behaves like a normal method and executes on the current thread instead of creating a new thread.

---

#### 8. Can we call run() directly without calling start()?

it will behave like a normal method and execute on the current thread. It will not create a new thread.

---

#### 9. Can we restart a thread after it has completed?

No, we cannot restart a thread after it has completed.
<br>
In Java, a thread can be started only once. If we call start() again on the same thread object, Java throws an IllegalThreadStateException.

---

#### 10. What happens if two threads access the same shared variable at the same time?

When two threads access the same shared variable at the same time, it can cause unexpected results due to a race condition.
<br>
For example, if both threads try to increment the same counter simultaneously, one update may be lost.
<br>
We can prevent this problem by using synchronization techniques like `synchronized`, `Lock`, or atomic classes.

---

#### 11. What is thread safety, and how do you achieve it?

Thread safety means that multiple threads can access shared data concurrently without causing unexpected results or data inconsistency.

We can achieve thread safety using synchronization techniques such as `synchronized`, `Lock`, or atomic classes.

---

#### 12. Explain the thread lifecycle and its states.

In Java, a thread passes through six states during its lifecycle:

1. **NEW:** A thread is created but `start()` has not been called yet.
2. **RUNNABLE:** The thread is ready to run or is currently running.
3. **BLOCKED:** The thread is waiting to acquire a lock to enter a synchronized block or method.
4. **WAITING:** The thread waits indefinitely until another thread performs a particular action, such as calling `notify()`.
5. **TIMED_WAITING:** The thread waits for a specific period of time, such as when using `sleep()`.
6. **TERMINATED:** The thread has completed its execution.

---

#### 13. What is the purpose of `join()`?

The join() method is used to wait for a thread to complete its execution before continuing with the next line of code.

---

#### 14. What is the difference between `sleep()` and `wait()`?

` sleep()` pauses a thread for a specific amount of time without releasing the lock.

`wait()` makes a thread wait until another thread notifies it, and it releases the lock while waiting.

---

#### 15. What is a daemon thread, and when would you use one?

A daemon thread is a background thread that supports other threads. It automatically stops when all user threads finish their execution.
<br>
We use daemon threads for background tasks like garbage collection.

---

#### 16. What is the Producer-Consumer problem?

The Producer-Consumer problem is a multithreading problem where one thread produces data and another thread consumes it.
<br>
The main problem is synchronization between the producer and consumer.
<br>
The problem occurs when both threads access the variable at the same time. For example, the producer may overwrite a value before the consumer reads it, or the consumer may try to read the value when it is null.

---

### 17. What is memory visibility problem?

Memory visibility problem occurs when one thread updates a shared variable, but another thread keeps reading its cached value instead of seeing the updated value.

---

#### 18. What is `volatile` keyword?

volatile ensures that changes made by one thread to a shared variable are visible to other threads.

---

#### 19. What is the critical section ?

A critical section is a part of code where shared resources are accessed or modified by multiple threads. We use synchronization to make sure only one thread accesses it at a time.

---

#### 20. What is context switching?

Context switching is the process of switching the CPU from one thread to another by saving the current thread's state and loading the next thread's state.

---

#### 21. What is monitor lock?

A monitor lock is an internal lock associated with every Java object. A thread acquires this lock when it enters a synchronized method or block and releases it when it exits. Only one thread can hold the same object's monitor lock at a time.

---

#### 22. What is wait()?

The `wait()` method makes a thread wait until another thread calls `notify()` or `notifyAll()`. It also releases the object's monitor lock while waiting.

---

#### 23. What is the difference between notify() and notifyAll()?

`notify()` wakes up one waiting thread, while `notifyAll()` wakes up all waiting threads on the same object.

---

#### 24. What is thread starvation?

Thread starvation is a problem which occurs when a thread waits for a long time because other threads keep getting CPU time or priority.

---

#### 25. What is Lock interface?

The `Lock` interface in Java is used to control access to shared resources by multiple threads. It allows only one thread at a time to access the locked section of code.

---

#### 26. What is ReentrantLock?

ReentrantLock is a class in Java that implements the Lock interface. It allows a thread to acquire the same lock multiple times

---

#### 27. What is the difference between `synchronized` and `ReentrantLock`, and when would you use `ReentrantLock`?

Both synchronized and ReentrantLock are used to control access to shared resources.
<br>
The main difference is that synchronized releases the lock automatically, while ReentrantLock gives us more control over locking and unlocking.
<br>
We use ReentrantLock when we need features like tryLock()

---

#### 28. What is `ReadWriteLock` ?

`ReadWriteLock` is a lock that allows multiple threads to read shared data at the same time, but only one thread can write at a time.

---

#### 29. When would you use a `ReadWriteLock`?

We use ReadWriteLock when multiple threads read shared data frequently, but write operations happen less often.

---

#### 30. What is a race condition?

A race condition occurs when multiple threads access and modify shared data at the same time, causing unexpected results

---

#### 31. What is Executor framework?

The Executor Framework is a Java framework used to manage and execute multiple tasks using a pool of threads instead of creating a new thread for every task.

---

#### 32. What is executor service?

ExecutorService is a Java interface used to manage a pool of threads and execute multiple tasks without creating a new thread for every task.

---

#### 33. What is thread pool?

A thread pool is a group of reusable threads.

---

#### 34. What is the difference between `Runnable` and `Callable`?

Both `Runnable` and `Callable` are used to define tasks that can be executed by a thread.
<br>
The main difference is that `Runnable` does not return a result and cannot throw checked exceptions, while `Callable` can return a result and throw checked exceptions.

---

#### 35. What is `Future`?

Future is an interface in Java that represents the result of an asynchronous task.

---

#### 36. What types of pools does `Executors` provide?

Executors provides several types of thread pools:

1. FixedThreadPool: Uses a fixed number of threads.
2. CachedThreadPool: Creates new threads when needed and reuses available threads.
3. SingleThreadExecutor: Uses only one thread to execute tasks sequentially.
4. ScheduledThreadPool: Executes tasks after a delay or at fixed intervals.
5. WorkStealingPool: Uses multiple threads and distributes tasks among them.

---

#### 37. What is the difference between `shutdown()` and `shutdownNow()`?

shutdown() stops accepting new tasks but allows already submitted tasks to complete. On the other hand, shutdownNow() attempts to stop running tasks by interrupting them and returns tasks that are waiting for execution.

---

#### 38. How does the `synchronized` keyword work?

The `synchronized` keyword in Java is used to control access to shared resources by multiple threads. It allows only one thread at a time to execute the synchronized method or block using the same object's lock. Other threads must wait until the lock is released.

---

#### 39. What is the difference between a synchronized method, a block, and a static synchronized method?

1. Synchronized Method: It locks the current object (this). Only one thread at a time can execute synchronized methods on the same object.

2. Synchronized Block: It allows us to synchronize only a specific section of code instead of the entire method. We can also specify which object's lock to use.

3. Static Synchronized Method: It acquires the class-level lock (ClassName.class) instead of an object-level lock. Only one thread at a time can execute static synchronized methods of the same class.

---

#### 40. Why must wait(), notify(), and notifyAll() be called inside a synchronized context?

We must call `wait()`, `notify()`, and `notifyAll()` inside a synchronized block because a thread must have the object's lock before using these methods. Otherwise, Java throws an `IllegalMonitorStateException`.

---

#### 41. What is the difference between execute() and submit()?

Both `execute()` and `submit()` are used to execute tasks using an `ExecutorService`.

- `execute()` accepts a `Runnable` task and does not return a result.
- `submit()` accepts a `Runnable` or `Callable` task and returns a `Future` that we can use to get the result or check the task's status.

---

#### 42. What happens when you submit a task to an ExecutorService after calling shutdown()?

If we submit a new task after calling `shutdown()`, the `ExecutorService` rejects the task and throws a `RejectedExecutionException`.

---

#### 43. How do you stop a running thread safely using `interrupt()`?

We can stop a running thread safely by calling its interrupt() method. It sends an interruption request to the thread.

---

#### 44. What happens if you forget to call unlock() in ReentrantLock?

If we forget to call `unlock()`, the lock remains held, and other threads may wait indefinitely to acquire it. This can cause deadlock.

---

#### 45. What is atomic classes?

Atomic classes in Java provide thread-safe operations on shared variables without using explicit synchronization. For example, `AtomicInteger` allows multiple threads to safely update an integer value.

---

#### 46. What is a deadlock, how can it occur, and how do you prevent it?

A deadlock occurs when two or more threads wait for each other to release locks, and none of them can continue.
<br>
It happens when threads acquire locks in different orders.
<br>
We can prevent deadlocks by Acquiring locks in the same order, avoiding unnecessary locks and using tryLock() with a timeout.

---

#### 47. What is the difference between visibility and atomicity?

Visibility means changes made by one thread are visible to other threads. Atomicity means an operation completes fully without being interrupted by another thread.

---

#### 48. What is the `CompletableFuture`?

`CompletableFuture` is used to execute tasks asynchronously and handle their results when they are completed.

---

#### 49. What are the limitations of `Future`, and how does `CompletableFuture` solve them?

The main limitation of `Future` is that its `get()` method blocks the calling thread until the task completes. It also does not support chaining multiple tasks easily.

`CompletableFuture` solves these limitations by allowing us to execute tasks without blocking the calling thread and chain multiple tasks using methods like `thenApply()` and `thenAccept()`.

---

#### 50. What is the difference between `thenApply()` and `thenAccept()`?

- **`thenApply()`** is used to transform the result of a task.
- **`thenAccept()`** is used to consume the result of a task without returning a new result.

---

#### 51. What is the difference between `supplyAsync()` and `runAsync()`?

- **`supplyAsync()`** is used to execute an asynchronous task that returns a result.

- **`runAsync()`** is used to execute an asynchronous task that does not return a result.

---

#### 52. What is `ThreadLocal`, and where is it used?

`ThreadLocal` is used to create a separate copy of a variable for each thread. It is commonly used to store user-specific data, such as user details, in web applications.

---

#### 53. What are virtual threads (Java 21), and how do they differ from platform threads?

Virtual threads are lightweight threads introduced in Java 21.
<br>
The main difference is:
<br>
Platform threads are directly mapped to operating system threads and are more expensive to create.
<br>
Virtual threads are managed by the JVM and are much cheaper to create.

---

#### 54. What is the difference between concurrency and parallelism?

Concurrency means handling multiple tasks at the same time by switching between them, while parallelism means actually executing multiple tasks simultaneously, usually using multiple CPU cores.
