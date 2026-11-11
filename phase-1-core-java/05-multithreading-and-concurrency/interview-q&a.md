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
