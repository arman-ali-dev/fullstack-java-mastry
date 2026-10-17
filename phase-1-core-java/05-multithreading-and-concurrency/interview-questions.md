## Must Prepare

1. What is multithreading, and why is it needed in real applications?
2. What is the difference between a process and a thread?
3. What are the ways to create a thread in Java, and why is `Runnable` preferred over extending `Thread`?
4. What is the difference between `start()` and `run()`?
5. Explain the thread lifecycle and its states.
6. What is the purpose of `join()`?
7. What is the difference between `sleep()` and `wait()`?
8. What is a daemon thread, and when would you use one?
9. What is the difference between `Runnable` and `Callable`, and what role does `Future` play?
10. Why use a thread pool instead of creating a new thread for every task, and what types of pools does `Executors` provide?
11. How does `ThreadPoolExecutor` work (core size, max size, queue), and why is `Executors.newFixedThreadPool()` risky in production?
12. What is the difference between `shutdown()` and `shutdownNow()`?
13. What is a race condition? Show it with a counter example.
14. How does the `synchronized` keyword work (monitor lock), and what is the difference between a synchronized method, a block, and a static synchronized method?
15. What is a deadlock, how can it occur, and how do you prevent it?
16. What does `volatile` guarantee, and why is it not enough for `count++`?
17. How do atomic classes like `AtomicInteger` achieve thread safety without locks (CAS)?
18. What is the difference between `synchronized` and `ReentrantLock`, and when would you use `ReentrantLock`?
19. What are the limitations of `Future`, and how does `CompletableFuture` solve them?
20. What is the difference between `thenApply()` and `thenCompose()`?
21. When would you use a `ReadWriteLock`?
22. How do you implement the producer-consumer pattern using `BlockingQueue`?
23. What is the difference between `exceptionally()` and `handle()` in `CompletableFuture`?
24. How would you call three independent services in parallel and combine their results using `CompletableFuture`?
25. How do `wait()`, `notify()`, and `notifyAll()` work, and why must they be called inside a synchronized block?
26. How do you stop a running thread safely using `interrupt()`?
27. What is `ThreadLocal`, and where is it used?
28. What are virtual threads (Java 21), and how do they differ from platform threads?
29. How do you run asynchronous tasks in Spring Boot using `@Async` and a `TaskExecutor`?
