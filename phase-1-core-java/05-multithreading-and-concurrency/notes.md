# Java Multithreading and Concurrency - Complete Notes

---

## PART 1 - WHAT IS MULTITHREADING AND WHY IT EXISTS

Think about a restaurant. One waiter is taking orders, another is serving food, another is clearing tables. All of them are working at the same time. This is multithreading.

Without multithreading, your Java program does one thing at a time. It finishes one task completely before starting the next. This is fine for simple programs but horrible for real applications.

Imagine you are building a music app. You click play. The app starts downloading the next song. Without multithreading, the entire app freezes while downloading. You cannot pause, cannot change volume, cannot do anything. That is terrible.

With multithreading:

- One thread plays the current song
- Another thread downloads the next song in the background
- Another thread updates the UI

All of this happens at the same time and the app stays responsive.

A Thread is the smallest unit of work that a CPU can execute. Your Java program always starts with one thread called the main thread. main() runs in this thread. You can create more threads and they run alongside the main thread.

Important: Threads share the same memory (heap). This means they can read and write the same variables. This is powerful but also dangerous - we will see why later.

---

## PART 2 - CREATING A THREAD

Three ways to create a thread in Java.

### Way 1 - Extend Thread class

You create a class that extends Thread. You override the run() method. Whatever you write inside run() will execute in a new thread.

```java
class DownloadThread extends Thread {

    String fileName;

    DownloadThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        // this entire method runs in a NEW thread
        System.out.println("Starting download: " + fileName);
        try {
            Thread.sleep(2000); // simulate downloading - pause for 2 seconds
        } catch (InterruptedException e) {
            System.out.println("Download interrupted");
        }
        System.out.println("Download complete: " + fileName);
    }
}

public class Main {
    public static void main(String[] args) {

        DownloadThread d1 = new DownloadThread("song1.mp3");
        DownloadThread d2 = new DownloadThread("song2.mp3");

        d1.start(); // creates new thread and calls run() inside it
        d2.start(); // creates another new thread

        // THIS IS VERY IMPORTANT
        // d1.run() would NOT create a new thread
        // it would just call run() in the main thread like a normal method
        // always use start(), never run() directly

        System.out.println("Main thread continues while downloads happen");
    }
}

// Output will be something like:
// Main thread continues while downloads happen
// Starting download: song1.mp3
// Starting download: song2.mp3
// Download complete: song1.mp3
// Download complete: song2.mp3
// Order is NOT guaranteed - depends on CPU scheduling
```

Problem with this approach: your class is already extending Thread. Java does not allow extending two classes. So if DownloadThread needs to extend some other class also, you are stuck.

### Way 2 - Implement Runnable interface (preferred)

Runnable is just an interface with one method: run(). You implement this interface, write the task inside run(), and pass it to a Thread object.

```java
class DownloadTask implements Runnable {

    String fileName;

    DownloadTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("Downloading: " + fileName + " in " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("Done: " + fileName);
    }
}

public class Main {
    public static void main(String[] args) {

        // create the task
        DownloadTask task1 = new DownloadTask("song1.mp3");
        DownloadTask task2 = new DownloadTask("song2.mp3");

        // wrap task in Thread and start
        Thread t1 = new Thread(task1, "DownloadThread-1"); // second argument is thread name
        Thread t2 = new Thread(task2, "DownloadThread-2");

        t1.start();
        t2.start();
    }
}
```

This is better because DownloadTask can still extend another class if needed. The task and the thread are separate things.

### Way 3 - Lambda (shortest and most common in modern Java)

Since Runnable has only one method, you can use a lambda instead of writing a whole class.

```java
public class Main {
    public static void main(String[] args) {

        // lambda replaces the entire Runnable class
        Thread t1 = new Thread(() -> {
            System.out.println("Task 1 running in: " + Thread.currentThread().getName());
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("Task 1 done");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Task 2 running in: " + Thread.currentThread().getName());
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Task 2 done");
        });

        t1.start();
        t2.start();
    }
}
```

### Extend Thread vs Implement Runnable - which to use

Always use Runnable (or lambda). Here is why:

```
Extend Thread                         |  Implement Runnable
--------------------------------------|------------------------------------
Class cannot extend anything else     |  Class can still extend other class
Task and Thread are same object       |  Task and Thread are separate
Less flexible                         |  More flexible
Cannot reuse same task in             |  Same Runnable task can be given
multiple threads                      |  to multiple threads
Not recommended                       |  Always preferred
```

---

## PART 3 - THREAD LIFECYCLE

Every thread goes through specific states from birth to death. Understanding this is very important for interviews.

```
NEW  -->  RUNNABLE  -->  RUNNING  -->  TERMINATED
                            |
                            |---> BLOCKED
                            |---> WAITING
                            |---> TIMED_WAITING

(BLOCKED, WAITING, TIMED_WAITING all go back to RUNNABLE when condition is met)
```

### NEW

Thread object is created but start() has not been called yet. The thread exists in memory but is not doing anything.

```java
Thread t = new Thread(() -> System.out.println("hello"));
System.out.println(t.getState()); // NEW
```

### RUNNABLE

start() has been called. The thread is ready to run and is waiting for the CPU to pick it up. It may or may not be actually executing right now - the OS scheduler decides.

```java
t.start();
System.out.println(t.getState()); // RUNNABLE
```

### RUNNING

The thread is actually executing on the CPU right now. Java does not have a separate RUNNING state in the API - it shows as RUNNABLE. But conceptually this is when your run() code is actually executing.

### BLOCKED

The thread wants to enter a synchronized block but another thread is currently inside it holding the lock. So this thread is blocked and waiting for the lock to be released.

```java
// Thread A is inside synchronized block
// Thread B tries to enter the same synchronized block
// Thread B goes to BLOCKED state and waits
```

### WAITING

The thread is waiting indefinitely for another thread to do something specific. It will stay in WAITING until another thread explicitly wakes it up.

Caused by:

- t.join() with no timeout - waiting for thread t to finish
- object.wait() - waiting for notify()

```java
Thread t1 = new Thread(() -> {
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {}
});

t1.start();
t1.join(); // main thread goes to WAITING state until t1 finishes
```

### TIMED_WAITING

Same as WAITING but with a time limit. Thread wakes up automatically after the time expires even if no one wakes it up.

Caused by:

- Thread.sleep(ms)
- t.join(ms) with timeout
- object.wait(ms)

```java
Thread t = new Thread(() -> {
    try {
        Thread.sleep(5000); // thread goes to TIMED_WAITING for 5 seconds
    } catch (InterruptedException e) {}
});

t.start();
Thread.sleep(500); // let t start and go to sleep
System.out.println(t.getState()); // TIMED_WAITING
```

### TERMINATED

Thread has finished executing. run() method has returned. Once terminated, you cannot start the same thread again. If you call start() on a terminated thread, it throws IllegalThreadStateException.

```java
Thread t = new Thread(() -> System.out.println("done"));
t.start();
t.join(); // wait for it to finish
System.out.println(t.getState()); // TERMINATED
// t.start(); // IllegalThreadStateException - cannot restart
```

### Complete lifecycle demo

```java
public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            try {
                System.out.println("Thread started");
                Thread.sleep(3000); // goes to TIMED_WAITING
                System.out.println("Thread finishing");
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
        });

        System.out.println("State 1: " + t.getState()); // NEW

        t.start();
        System.out.println("State 2: " + t.getState()); // RUNNABLE

        Thread.sleep(500); // give t time to start sleeping
        System.out.println("State 3: " + t.getState()); // TIMED_WAITING

        t.join(); // wait for t to completely finish
        System.out.println("State 4: " + t.getState()); // TERMINATED
    }
}
```

### Important Thread methods

```java
Thread t = new Thread(() -> {});

// naming
t.setName("WorkerThread");
t.getName(); // "WorkerThread"

// priority - 1 (MIN) to 10 (MAX), default is 5
t.setPriority(Thread.MAX_PRIORITY); // 10
t.setPriority(Thread.MIN_PRIORITY); // 1
t.setPriority(Thread.NORM_PRIORITY); // 5
t.getPriority();
// Note: priority is a hint to the OS, not a guarantee

// status checks
t.isAlive();     // true if thread has started and not yet terminated
t.isDaemon();    // true if it is a daemon thread
t.getState();    // returns Thread.State enum value

// daemon thread - background thread that dies when main thread dies
t.setDaemon(true); // must set BEFORE calling start()
t.start();

// static methods - called on Thread class
Thread.sleep(1000);               // pause current thread for 1 second
Thread.currentThread();           // get reference to current running thread
Thread.currentThread().getName(); // name of currently running thread

// join - make current thread wait for another thread to finish
t.join();        // wait indefinitely
t.join(2000);    // wait maximum 2 seconds, then continue anyway

// interrupt - signal a thread to stop
t.interrupt();
// this sets the interrupted flag on that thread
// if thread is sleeping or waiting, it throws InterruptedException immediately
// if thread is running normally, it just sets the flag - thread can check it with:
Thread.currentThread().isInterrupted(); // returns true if interrupted
```

### join() - very important

join() makes the calling thread wait until the target thread finishes. Very useful when one task depends on the result of another.

```java
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {

        int[] data = new int[5];

        Thread filler = new Thread(() -> {
            System.out.println("Filling data...");
            for (int i = 0; i < data.length; i++) {
                data[i] = i * 10;
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
            System.out.println("Data filled");
        });

        filler.start();
        filler.join(); // WAIT HERE until filler thread finishes

        // This only runs after filler is done
        // Without join(), data might not be filled yet when we print
        System.out.println("Processing data:");
        for (int d : data) {
            System.out.print(d + " "); // 0 10 20 30 40
        }
    }
}
```

### Daemon threads

Normal threads: JVM waits for all of them to finish before shutting down.

Daemon threads: JVM does NOT wait for them. When all normal threads finish, JVM exits and all daemon threads are killed automatically.

Use case: background tasks like garbage collection, auto-save, cleanup tasks.

```java
public class DaemonDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread autoSave = new Thread(() -> {
            while (true) { // runs forever
                System.out.println("Auto-saving...");
                try { Thread.sleep(2000); } catch (InterruptedException e) { break; }
            }
        });

        autoSave.setDaemon(true); // MUST be called before start()
        autoSave.start();

        // main thread does some work
        System.out.println("Main working...");
        Thread.sleep(5000);
        System.out.println("Main done");

        // main thread ends here
        // since autoSave is daemon, JVM exits and autoSave is killed
        // we do not have to manually stop it
    }
}
```

---

## PART 4 - RUNNABLE VS CALLABLE

Both define tasks that can run in threads. The key difference is the return value and exception handling.

```
Feature                  Runnable              Callable<T>
-------------------------|----------------------|------------------------
Method name              run()                 call()
Return type              void                  T (any type you specify)
Can throw checked        No                    Yes
exceptions               (must handle inside)  (caller handles it)
Result access            No result             Via Future<T>
Used with                Thread directly       ExecutorService
```

### Runnable - when you do not need a result

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableDemo {
    public static void main(String[] args) {

        Runnable sendEmail = () -> {
            System.out.println("Sending email... in " + Thread.currentThread().getName());
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Email sent");
            // returns nothing
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(sendEmail);
        executor.submit(sendEmail); // same task can be submitted multiple times
        executor.shutdown();
    }
}
```

### Callable - when you need a result back

```java
import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) throws Exception {

        // Callable<Integer> means this task will return an Integer
        Callable<Integer> sumTask = () -> {
            System.out.println("Calculating sum...");
            Thread.sleep(2000); // simulate long calculation
            int sum = 0;
            for (int i = 1; i <= 100; i++) sum += i;
            return sum; // 5050
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();

        // submit returns a Future - a promise that result will be available later
        Future<Integer> future = executor.submit(sumTask);

        // while task is running, main thread can do other work
        System.out.println("Sum task submitted. Doing other work...");
        System.out.println("Other work 1 done");
        System.out.println("Other work 2 done");

        // now get the result - this BLOCKS until task is complete
        Integer result = future.get();
        System.out.println("Sum result: " + result); // 5050

        executor.shutdown();
    }
}
```

### Future - the result holder

Future is a container that holds the result of an async task. The result is not available immediately - it will be available in the future (hence the name).

```java
Future<String> future = executor.submit(() -> {
    Thread.sleep(3000);
    return "Task completed";
});

// Check if done without blocking
System.out.println(future.isDone()); // false - still running

// Wait max 2 seconds for result
// throws TimeoutException if not done in 2 seconds
try {
    String result = future.get(2, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    System.out.println("Task took too long");
}

// Cancel the task
future.cancel(true); // true = interrupt if running
System.out.println(future.isCancelled()); // true

// After cancel, get() throws CancellationException
```

### Callable with checked exception

```java
Callable<String> fileReader = () -> {
    // Callable can throw checked exceptions - no need to catch inside
    FileReader fr = new FileReader("data.txt"); // throws IOException - no problem
    // read file...
    return "file content";
};

Future<String> future = executor.submit(fileReader);

try {
    String content = future.get(); // if task threw exception, get() wraps it in ExecutionException
} catch (ExecutionException e) {
    System.out.println("Task failed: " + e.getCause().getMessage());
}
```

---

## PART 5 - EXECUTORSERVICE AND THREADPOOL

### Why not just create new Thread() every time?

Creating a thread is expensive. It takes time, memory, and OS resources. If you have 1000 tasks and you create 1000 threads, your program will crash or become very slow.

ThreadPool is the solution. You create a fixed number of threads upfront. These threads are reused for all tasks.

Think of it like a taxi company. Instead of buying a new car for every customer, you have 10 cars. When a customer needs a ride, an available car picks them up. When done, that car waits for the next customer. Cars are reused.

ExecutorService is the interface that manages this pool.

### Types of thread pools

```java
import java.util.concurrent.*;

// 1. Fixed Thread Pool
// Always exactly n threads running
// If you submit more tasks than threads, extra tasks wait in a queue
// Best for: known number of parallel tasks
ExecutorService fixed = Executors.newFixedThreadPool(4);

// 2. Single Thread Executor
// Only 1 thread. Tasks run one after another in order.
// Useful when tasks must not run concurrently but you still want async execution
ExecutorService single = Executors.newSingleThreadExecutor();

// 3. Cached Thread Pool
// Creates new threads as needed
// Reuses idle threads
// Idle threads die after 60 seconds
// Best for: many short-lived tasks
// Danger: can create too many threads if tasks keep coming
ExecutorService cached = Executors.newCachedThreadPool();

// 4. Scheduled Thread Pool
// For tasks that need to run after a delay or repeatedly
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
```

### Submitting tasks

Three methods to submit tasks:

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

// execute() - submit Runnable, no return value, no way to check if done
executor.execute(() -> System.out.println("Fire and forget task"));

// submit(Runnable) - submit Runnable, returns Future<?> but get() returns null
Future<?> f1 = executor.submit(() -> System.out.println("Runnable task"));
f1.get(); // returns null, but useful to know when task is done

// submit(Callable) - submit Callable, returns Future<T> with actual result
Future<Integer> f2 = executor.submit(() -> {
    return 42;
});
System.out.println(f2.get()); // 42
```

### Full working example - processing multiple tasks

```java
import java.util.*;
import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) throws Exception {

        // 3 threads in pool
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future<String>> futures = new ArrayList<>();

        // Submit 6 tasks - only 3 threads available
        // Tasks 4, 5, 6 wait in queue until a thread is free
        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            Future<String> future = executor.submit(() -> {
                System.out.println("Task " + taskId + " started in " + Thread.currentThread().getName());
                Thread.sleep(2000); // simulate work
                System.out.println("Task " + taskId + " finished");
                return "Result of task " + taskId;
            });
            futures.add(future);
        }

        // Collect all results
        // get() blocks until that specific task is done
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        // Always shutdown the executor when done
        // Without shutdown, JVM will not exit because pool threads are still alive
        executor.shutdown();

        // Optional: wait for all tasks to complete after shutdown
        boolean allDone = executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("All tasks done: " + allDone);
    }
}
```

### Shutdown - very important

```java
// shutdown() - graceful
executor.shutdown();
// Stops accepting new tasks
// Already submitted tasks complete normally
// Non-blocking - does not wait

// shutdownNow() - forceful
List<Runnable> notStarted = executor.shutdownNow();
// Stops accepting new tasks
// Sends interrupt signal to all running tasks
// Returns list of tasks that were waiting but never started
// Running tasks may or may not stop depending on how they handle interrupts

// awaitTermination() - wait for shutdown to complete
executor.shutdown();
try {
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow(); // force if not done in 60 seconds
    }
} catch (InterruptedException e) {
    executor.shutdownNow();
}

// Check if shutdown
executor.isShutdown();    // true after shutdown() or shutdownNow() called
executor.isTerminated();  // true after all tasks have completed after shutdown
```

### ScheduledExecutorService

```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// Run ONCE after a delay
scheduler.schedule(() -> {
    System.out.println("Runs once after 3 seconds");
}, 3, TimeUnit.SECONDS);

// Run REPEATEDLY - fixed rate
// starts after 1 second, then runs every 2 seconds
// next run starts 2 seconds after PREVIOUS START (not end)
// if task takes longer than period, next run starts immediately after
ScheduledFuture<?> sf = scheduler.scheduleAtFixedRate(() -> {
    System.out.println("Fixed rate task at: " + System.currentTimeMillis());
}, 1, 2, TimeUnit.SECONDS);

// Run REPEATEDLY - fixed delay
// starts after 1 second, then waits 2 seconds after PREVIOUS END before next run
scheduler.scheduleWithFixedDelay(() -> {
    System.out.println("Fixed delay task");
}, 1, 2, TimeUnit.SECONDS);

// Cancel a scheduled task
sf.cancel(false); // false = don't interrupt if running, just don't run again

// Shutdown
scheduler.shutdown();
```

---

## PART 6 - SYNCHRONIZED KEYWORD

### The Problem - Race Condition

Threads share the same memory. When two threads read and write the same variable at the same time, things go wrong.

count++ looks like one operation but it is actually three separate steps:

1. Read current value of count from memory
2. Add 1 to it
3. Write the new value back to memory

If two threads both read count = 5 at the same time, both add 1, both write 6 back - you lose one increment. This is a race condition.

```java
class Counter {
    int count = 0;

    void increment() {
        count++; // NOT thread safe - 3 separate steps
    }
}

public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) counter.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Expected: 20000");
        System.out.println("Actual: " + counter.count); // some random number less than 20000
    }
}
```

### synchronized method

Adding synchronized to a method means only one thread can execute it at a time. Other threads wait.

```java
class Counter {
    int count = 0;

    // only ONE thread can be inside this method at any time
    synchronized void increment() {
        count++; // now thread safe
    }

    synchronized int getCount() {
        return count;
    }
}

// Now count will always be exactly 20000
```

### How synchronized works internally - Monitor Lock

Every object in Java has an invisible lock called a monitor lock or intrinsic lock.

When a thread calls a synchronized method:

1. Thread tries to acquire the lock of that object
2. If lock is available, thread takes it and enters the method
3. Other threads that try to enter ANY synchronized method of the SAME object are blocked
4. When thread finishes and exits the synchronized method, lock is released
5. One of the waiting threads gets the lock and enters

This is why synchronized methods on the same object cannot run at the same time, but synchronized methods on DIFFERENT objects can run at the same time.

```java
class Printer {
    synchronized void printDocument(String name) {
        System.out.println("Printing: " + name);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println("Done printing: " + name);
    }
}

public class Main {
    public static void main(String[] args) {

        Printer printer = new Printer();

        Thread t1 = new Thread(() -> printer.printDocument("Report.pdf"));
        Thread t2 = new Thread(() -> printer.printDocument("Invoice.pdf"));

        t1.start();
        t2.start();

        // t1 and t2 use SAME printer object
        // so they take turns - one prints, then the other
        // output will be:
        // Printing: Report.pdf
        // Done printing: Report.pdf
        // Printing: Invoice.pdf
        // Done printing: Invoice.pdf
        // (or in reverse order - first one to get lock goes first)
    }
}
```

### synchronized block - more control

Instead of locking the entire method, you can lock only the part that needs protection. This way other threads can run the non-critical parts simultaneously.

```java
class BankAccount {
    private double balance;
    private String accountName;
    private Object balanceLock = new Object(); // dedicated lock object

    void processTransaction(double amount, String description) {
        // This part does not modify shared data - no lock needed
        // Multiple threads can run this simultaneously
        System.out.println("Processing: " + description);
        validateTransaction(amount);

        // This part modifies balance - must be protected
        synchronized (balanceLock) {
            if (amount < 0 && Math.abs(amount) > balance) {
                System.out.println("Insufficient balance");
                return;
            }
            balance += amount;
            System.out.println("New balance: " + balance);
        }
        // Lock is released here, others can enter

        // This part also does not need lock
        logTransaction(description, amount);
    }

    private void validateTransaction(double amount) { /* validation */ }
    private void logTransaction(String desc, double amount) { /* logging */ }
}
```

### synchronized on static method

Static synchronized method locks on the CLASS object, not an instance. All instances share this lock.

```java
class IdGenerator {
    private static int nextId = 0;

    // locks on IdGenerator.class - all objects share this lock
    public static synchronized int generateId() {
        return ++nextId;
    }
}

// Two different IdGenerator objects, but generateId is static synchronized
// So calls to generateId from any object are synchronized
IdGenerator g1 = new IdGenerator();
IdGenerator g2 = new IdGenerator();
// IdGenerator.generateId() and g1.generateId() and g2.generateId() all compete for same lock
```

### Deadlock

Deadlock happens when two threads are each waiting for a lock that the other thread is holding. Both are stuck forever.

```java
public class DeadlockDemo {
    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lock1) { // t1 takes lock1
                System.out.println("T1: holding lock1, waiting for lock2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock2) { // t1 wants lock2 - but t2 has it
                    System.out.println("T1: got both locks");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) { // t2 takes lock2
                System.out.println("T2: holding lock2, waiting for lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock1) { // t2 wants lock1 - but t1 has it
                    System.out.println("T2: got both locks");
                }
            }
        });

        t1.start();
        t2.start();
        // T1 holds lock1, waits for lock2
        // T2 holds lock2, waits for lock1
        // Both wait forever - DEADLOCK
    }
}
```

How to avoid deadlock: always acquire locks in the same order. If both threads always take lock1 first then lock2, deadlock cannot happen.

```java
// FIXED - both threads acquire locks in same order: lock1 then lock2
Thread t1 = new Thread(() -> {
    synchronized (lock1) {
        synchronized (lock2) {
            System.out.println("T1 done");
        }
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lock1) { // same order as t1 - lock1 first
        synchronized (lock2) {
            System.out.println("T2 done");
        }
    }
});
```

---

## PART 7 - VOLATILE KEYWORD

### The Problem - Memory Visibility

Modern CPUs have caches (L1, L2, L3). For performance, each thread may work with its own cached copy of a variable instead of reading from main memory every time.

Problem: Thread 1 changes a variable. Thread 2 reads it. But Thread 2 is reading from its cache which still has the old value. Thread 2 never sees the update.

This is the memory visibility problem.

```java
class SharedFlag {
    boolean running = true; // NOT volatile

    void stop() {
        running = false;
        System.out.println("Flag set to false");
    }
}

public class VisibilityProblem {
    public static void main(String[] args) throws InterruptedException {

        SharedFlag flag = new SharedFlag();

        Thread worker = new Thread(() -> {
            int count = 0;
            while (flag.running) { // may read from cache and never see the change!
                count++;
            }
            System.out.println("Worker stopped. Count: " + count);
        });

        worker.start();
        Thread.sleep(1000);
        flag.stop(); // sets running = false in main thread's memory

        // Worker thread may NEVER stop because it reads running from its own cache
        // This is a real bug - program hangs forever
    }
}
```

### volatile fixes visibility

volatile tells the JVM: always read this variable from main memory, always write directly to main memory, never use cached value.

```java
class SharedFlag {
    volatile boolean running = true; // NOW volatile

    void stop() {
        running = false; // written directly to main memory
    }
}

// Now worker thread always reads from main memory
// When stop() sets running = false, worker sees it immediately
```

### When to use volatile

Use volatile when:

- One thread writes, other threads only read
- The operation on the variable is a single read or write (not compound)
- You need visibility but not full synchronization

```java
class Config {
    volatile int maxConnections = 10; // updated by admin thread, read by many worker threads
    volatile String serverAddress = "localhost"; // same pattern
    volatile boolean maintenanceMode = false;
}

// Admin thread updates these
// Worker threads read these
// volatile ensures workers always see latest values
```

### volatile is NOT enough for compound operations

count++ is three operations: read, add, write. Even if count is volatile, two threads can still race on count++. For this you need synchronized or AtomicInteger.

```java
class WrongCounter {
    volatile int count = 0;

    void increment() {
        count++; // STILL NOT THREAD SAFE even with volatile
                 // volatile only helps with single read/write
                 // count++ is read-modify-write - still a race condition
    }
}

class RightCounter {
    int count = 0;

    synchronized void increment() {
        count++; // thread safe
    }
}
```

### volatile vs synchronized

```
volatile                              |  synchronized
--------------------------------------|--------------------------------------
Guarantees visibility only            |  Guarantees visibility + atomicity
No locking - very fast                |  Uses lock - slower
Only for single read/write            |  For compound operations too
Cannot prevent race condition         |  Prevents race condition
Good for flags and status variables   |  Good for shared mutable state
Does not block other threads          |  Blocks other threads while locked
```

---

## PART 8 - ATOMIC CLASSES

volatile gives visibility but not atomicity. synchronized gives both but uses locks which can cause contention and slow things down.

Atomic classes give you both visibility and atomicity WITHOUT using locks. They use a low-level CPU instruction called CAS (Compare And Swap) which is much faster than locks.

They are in the package java.util.concurrent.atomic.

### AtomicInteger

Most commonly used. Thread-safe integer operations without synchronized.

```java
import java.util.concurrent.atomic.AtomicInteger;

class SafeCounter {
    AtomicInteger count = new AtomicInteger(0);

    void increment() {
        count.incrementAndGet(); // atomic - no race condition, no lock
    }

    void decrement() {
        count.decrementAndGet();
    }

    int getCount() {
        return count.get();
    }
}

public class AtomicDemo {
    public static void main(String[] args) throws InterruptedException {

        SafeCounter counter = new SafeCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) counter.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Count: " + counter.getCount()); // always 20000
    }
}
```

### All AtomicInteger methods

```java
AtomicInteger num = new AtomicInteger(10);

num.get();                    // read current value -> 10
num.set(20);                  // set value to 20
num.getAndSet(30);            // return 20, then set to 30

num.incrementAndGet();        // ++num - add 1, return NEW value
num.getAndIncrement();        // num++ - return OLD value, then add 1
num.decrementAndGet();        // --num - subtract 1, return NEW value
num.getAndDecrement();        // num-- - return OLD value, then subtract 1

num.addAndGet(5);             // add 5, return NEW value
num.getAndAdd(5);             // return OLD value, then add 5

// Compare and Set - this is the key operation used internally
// "if current value is 30, change it to 50"
boolean changed = num.compareAndSet(30, 50);
// returns true if the swap happened (current was 30)
// returns false if current was NOT 30 (no change made)

System.out.println(num.get()); // 50 if changed, or 30 if it was already different
```

### compareAndSet is very powerful

```java
AtomicInteger stock = new AtomicInteger(5);

// Safe "check and update" without synchronized
// Scenario: many threads trying to buy last item
public boolean buyItem() {
    while (true) {
        int current = stock.get();
        if (current <= 0) return false; // out of stock

        // "if stock is still current, reduce it by 1"
        // if another thread already changed it, this returns false and we retry
        if (stock.compareAndSet(current, current - 1)) {
            return true; // successfully bought
        }
        // if we get here, another thread changed stock between our get() and compareAndSet()
        // loop again and retry with new value
    }
}
```

### Other Atomic classes

```java
import java.util.concurrent.atomic.*;

AtomicLong longVal = new AtomicLong(0L);
longVal.incrementAndGet();
longVal.addAndGet(1000L);

AtomicBoolean flag = new AtomicBoolean(false);
flag.get();          // read
flag.set(true);      // write
flag.getAndSet(false); // read old, write new
flag.compareAndSet(false, true); // if false, set to true

AtomicReference<String> ref = new AtomicReference<>("initial");
ref.get();
ref.set("updated");
ref.compareAndSet("updated", "final"); // if "updated", change to "final"
```

---

## PART 9 - REENTRANTLOCK AND READWRITELOCK

### Why ReentrantLock when we have synchronized?

synchronized works well but has limitations:

- You cannot try to acquire lock without waiting (tryLock)
- You cannot give up waiting after some time
- You cannot interrupt a thread that is waiting for a lock
- You cannot check how many threads are waiting

ReentrantLock gives all of these abilities.

Reentrant means: if a thread already holds the lock and tries to acquire it again, it succeeds (instead of deadlocking with itself). Same behavior as synchronized.

### Basic ReentrantLock usage

```java
import java.util.concurrent.locks.ReentrantLock;

class SafeCounter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    void increment() {
        lock.lock(); // acquire lock - blocks if another thread has it
        try {
            count++; // critical section
        } finally {
            lock.unlock(); // ALWAYS unlock in finally
                           // if exception happens, lock is still released
        }
    }

    int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
```

Always put unlock() in finally. If you put it after the critical section and an exception occurs, the lock will never be released and other threads will be blocked forever.

### tryLock - try without waiting

```java
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

class ResourceManager {
    private ReentrantLock lock = new ReentrantLock();

    void tryToProcess() {
        // tryLock() returns immediately
        // returns true if lock acquired, false if someone else has it
        if (lock.tryLock()) {
            try {
                System.out.println("Got lock, processing");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        } else {
            // did not get lock - do something else instead of waiting
            System.out.println("Lock busy, doing something else");
        }
    }

    void tryToProcessWithTimeout() throws InterruptedException {
        // wait up to 3 seconds to get the lock
        if (lock.tryLock(3, TimeUnit.SECONDS)) {
            try {
                System.out.println("Got lock within 3 seconds");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not get lock in 3 seconds, giving up");
        }
    }
}
```

### Fair lock - prevent starvation

By default ReentrantLock is unfair. Any waiting thread can get the lock next - no order. A thread might wait forever if others keep getting lucky.

Fair lock gives the lock to the thread that has been waiting the longest.

```java
// fair = false (default) - faster but can cause starvation
ReentrantLock unfairLock = new ReentrantLock();

// fair = true - slower but guaranteed no starvation
ReentrantLock fairLock = new ReentrantLock(true);
```

### ReentrantLock extra methods

```java
ReentrantLock lock = new ReentrantLock();

lock.isLocked();              // true if any thread holds the lock
lock.isHeldByCurrentThread(); // true if THIS thread holds the lock
lock.getHoldCount();          // how many times THIS thread has locked (reentrant count)
lock.getQueueLength();        // how many threads are waiting for this lock
lock.hasQueuedThreads();      // true if any threads are waiting
```

### ReadWriteLock - for read-heavy scenarios

Regular synchronized or ReentrantLock allows only ONE thread at a time. This is unnecessarily restrictive for read operations. Multiple threads reading simultaneously is perfectly safe - only writing causes problems.

ReadWriteLock has two separate locks:

- Read lock: multiple threads can hold it simultaneously (shared)
- Write lock: only one thread can hold it, and only when no reader is active (exclusive)

```java
import java.util.concurrent.locks.*;
import java.util.*;

class ThreadSafeCache {
    private Map<String, String> cache = new HashMap<>();
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // Multiple threads can read at the same time
    public String get(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading");
            Thread.sleep(100); // simulate read time
            return cache.get(key);
        } catch (InterruptedException e) {
            return null;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    // Only one thread can write, and only when no readers active
    public void put(String key, String value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing");
            Thread.sleep(500); // simulate write time
            cache.put(key, value);
        } catch (InterruptedException e) {
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {

        ThreadSafeCache cache = new ThreadSafeCache();
        cache.put("name", "Arman");

        // 5 reader threads - all can read simultaneously
        for (int i = 1; i <= 5; i++) {
            final int id = i;
            new Thread(() -> {
                System.out.println("Reader " + id + " got: " + cache.get("name"));
            }, "Reader-" + i).start();
        }

        // 1 writer thread
        new Thread(() -> cache.put("name", "Priya"), "Writer").start();

        // Readers run simultaneously with each other
        // But writer blocks all readers and vice versa
    }
}
```

### synchronized vs ReentrantLock vs ReadWriteLock

```
synchronized              |  ReentrantLock           |  ReadWriteLock
--------------------------|--------------------------|---------------------------
Simple to use             |  More code needed        |  More code needed
Auto release on exception |  Must unlock in finally  |  Must unlock in finally
Cannot tryLock            |  Can tryLock             |  Can tryLock
Cannot timeout            |  Can set timeout         |  Can set timeout
Unfair only               |  Fair or unfair          |  Fair or unfair
One at a time             |  One at a time           |  Multiple readers OR one writer
Best for most cases       |  When you need tryLock   |  Read-heavy scenarios
                          |  or timeout              |
```

---

## PART 10 - CONCURRENT COLLECTIONS

Regular collections like ArrayList, HashMap are NOT thread safe. If two threads modify them at the same time, you get ConcurrentModificationException or corrupted data.

Do not use Collections.synchronizedList() or Collections.synchronizedMap() in new code - they lock the entire collection for every operation, very slow.

Use these purpose-built concurrent collections instead.

### ConcurrentHashMap

Thread-safe HashMap. Instead of locking the entire map, it divides the map into segments and locks only the segment being modified. This allows multiple threads to read and write different parts of the map simultaneously.

```java
import java.util.concurrent.ConcurrentHashMap;

ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();

// All standard Map operations work and are thread-safe
scores.put("Arman", 100);
scores.get("Arman");
scores.remove("Arman");
scores.containsKey("Arman");

// Extra atomic operations (very useful)
scores.putIfAbsent("Priya", 90);     // add only if key does not exist
scores.replace("Priya", 95);         // update only if key exists

// Atomic compute
scores.compute("Arman", (k, v) -> v == null ? 1 : v + 1);
scores.computeIfAbsent("Raj", k -> 0);
scores.computeIfPresent("Arman", (k, v) -> v + 10);

// Atomic merge - add to existing or create new
scores.merge("Arman", 5, Integer::sum);
// if Arman exists: Arman's score = old score + 5
// if Arman does not exist: Arman's score = 5

// Safe iteration - does NOT throw ConcurrentModificationException
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

### CopyOnWriteArrayList

Thread-safe ArrayList. Every time you write (add, set, remove), it creates a completely new copy of the internal array. Readers always work on a snapshot so they are never blocked.

```java
import java.util.concurrent.CopyOnWriteArrayList;

CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

list.add("Arman");
list.add("Priya");
list.add("Raj");

// Safe iteration even while another thread is modifying
for (String name : list) {
    System.out.println(name); // no ConcurrentModificationException
    list.add("new item"); // this creates a new copy, iterator uses old copy
}

// When to use: many readers, few writers
// When NOT to use: many writes - copying the array every time is expensive
```

### BlockingQueue

A queue that blocks. If you try to take from an empty queue, the thread waits until something is added. If you try to put into a full queue, the thread waits until space is available.

Perfect for producer-consumer pattern.

```java
import java.util.concurrent.*;

public class BlockingQueueDemo {
    public static void main(String[] args) {

        // Queue with max capacity 5
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            String[] tasks = {"Task1", "Task2", "Task3", "Task4", "Task5", "Task6", "Task7"};
            for (String task : tasks) {
                try {
                    queue.put(task); // BLOCKS if queue is full (size 5)
                    System.out.println("Produced: " + task + " | Queue size: " + queue.size());
                    Thread.sleep(200);
                } catch (InterruptedException e) { break; }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    String task = queue.take(); // BLOCKS if queue is empty
                    System.out.println("Consumed: " + task);
                    Thread.sleep(800); // consumer is slower than producer
                } catch (InterruptedException e) { break; }
            }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}

// Producer fills queue fast
// When queue hits 5, producer blocks
// Consumer slowly takes items
// When consumer takes one, producer unblocks and adds one more
```

### BlockingQueue methods

```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

// Adding
queue.put("item");                        // BLOCKS if full
queue.offer("item");                      // returns false immediately if full (no block)
queue.offer("item", 2, TimeUnit.SECONDS); // waits max 2 seconds, returns false if still full

// Removing
String item = queue.take();                        // BLOCKS if empty
String item2 = queue.poll();                       // returns null immediately if empty
String item3 = queue.poll(2, TimeUnit.SECONDS);    // waits max 2 seconds, returns null if still empty

// Peeking
queue.peek();  // returns head without removing, null if empty

// Info
queue.size();            // current number of elements
queue.remainingCapacity(); // how much space left
queue.isEmpty();
```

---

## PART 11 - COMPLETABLEFUTURE

### Why CompletableFuture?

Future was introduced in Java 5 but it has problems:

Problem 1: future.get() blocks your thread. You submit an async task but then you block waiting for it. What is the point of async?

Problem 2: Cannot chain tasks. After task A completes, you cannot automatically start task B with A's result.

Problem 3: Cannot combine results. If you have two parallel tasks, there is no clean way to get both results and combine them.

Problem 4: No exception handling. You cannot attach a callback for when an exception occurs.

CompletableFuture (Java 8) solves all of this. It lets you write async code that chains, combines, and handles errors without blocking.

### Creating a CompletableFuture

```java
import java.util.concurrent.CompletableFuture;

// runAsync - for tasks that do NOT return a value
CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
    System.out.println("Async task with no result in: " + Thread.currentThread().getName());
    // heavy work here
});

// supplyAsync - for tasks that DO return a value
CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
    System.out.println("Fetching user data...");
    // simulate DB call
    try { Thread.sleep(2000); } catch (InterruptedException e) {}
    return "Arman"; // this will be the result
});

// By default uses ForkJoinPool.commonPool()
// Provide your own pool if needed
ExecutorService executor = Executors.newFixedThreadPool(4);
CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "result", executor);
```

### thenApply - transform the result

thenApply runs after the previous task completes. It takes the result, transforms it, and returns a new result. Like map() in streams.

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        System.out.println("Step 1: fetching name");
        return "arman";
    })
    .thenApply(name -> {
        System.out.println("Step 2: converting to uppercase");
        return name.toUpperCase(); // "ARMAN"
    })
    .thenApply(name -> {
        System.out.println("Step 3: adding greeting");
        return "Hello, " + name; // "Hello, ARMAN"
    });

System.out.println(future.get()); // Hello, ARMAN

// Each step runs after the previous one completes
// The result flows through each transformation
```

### thenAccept - use the result without transforming

thenAccept is like thenApply but does not return anything. Use it for the last step where you just consume the result.

```java
CompletableFuture.supplyAsync(() -> fetchUserFromDB("Arman"))
    .thenApply(user -> formatUserDetails(user))
    .thenAccept(formattedUser -> {
        // last step - just use the result
        System.out.println("Displaying: " + formattedUser);
        updateUI(formattedUser);
        // returns nothing
    });
```

### thenRun - run something after, ignore result

thenRun runs a task after completion but does not receive the result and does not return anything.

```java
CompletableFuture.supplyAsync(() -> processOrder())
    .thenRun(() -> {
        // order is done, send confirmation, but we don't need the order details here
        System.out.println("Order completed. Sending email confirmation.");
    });
```

### thenCompose - chain async tasks (flatMap)

Use thenApply when the next step is a regular operation.
Use thenCompose when the next step is ALSO an async operation returning a CompletableFuture.

```java
// WRONG - using thenApply with async next step
// returns CompletableFuture<CompletableFuture<String>> - nested and messy
CompletableFuture<CompletableFuture<String>> wrong = CompletableFuture
    .supplyAsync(() -> "Arman")
    .thenApply(name -> fetchUserData(name)); // fetchUserData returns CompletableFuture<String>

// CORRECT - use thenCompose to flatten
CompletableFuture<String> correct = CompletableFuture
    .supplyAsync(() -> "Arman")
    .thenCompose(name -> fetchUserData(name)); // automatically flattened

// Real example
CompletableFuture<String> result = CompletableFuture
    .supplyAsync(() -> getUserId("Arman"))          // returns String (user ID)
    .thenCompose(userId -> fetchProfile(userId))     // takes ID, returns CF<Profile>
    .thenCompose(profile -> fetchOrders(profile));   // takes Profile, returns CF<Orders>

String orders = result.get();

// Helper methods that return CompletableFuture
static CompletableFuture<String> fetchProfile(String userId) {
    return CompletableFuture.supplyAsync(() -> {
        // simulate DB call
        return "Profile of " + userId;
    });
}
```

### thenCombine - combine two independent parallel tasks

When you have two tasks that can run in parallel and you need both results to proceed.

```java
// Both tasks run at the same time
CompletableFuture<String> userTask = CompletableFuture.supplyAsync(() -> {
    Thread.sleep(1000); // simulate delay
    return "User: Arman";
});

CompletableFuture<Integer> scoreTask = CompletableFuture.supplyAsync(() -> {
    Thread.sleep(1500); // different delay
    return 950;
});

// When BOTH are done, combine their results
CompletableFuture<String> combined = userTask.thenCombine(scoreTask, (user, score) -> {
    return user + " has score: " + score;
});

System.out.println(combined.get()); // User: Arman has score: 950
// Total time = 1500ms (max of both), not 2500ms (sum)
```

### allOf - wait for multiple tasks to complete

```java
CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
    sleep(1000); return "Result 1";
});
CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
    sleep(2000); return "Result 2";
});
CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
    sleep(1500); return "Result 3";
});

// allOf returns CompletableFuture<Void> - just waits, does not collect results
CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);

allTasks.get(); // blocks until ALL three are done - waits 2000ms (slowest)

// Now get individual results
System.out.println(task1.get()); // Result 1 - available immediately (already done)
System.out.println(task2.get()); // Result 2
System.out.println(task3.get()); // Result 3
```

### anyOf - return result of first completed task

```java
CompletableFuture<String> server1 = CompletableFuture.supplyAsync(() -> {
    sleep(3000); return "Response from Server 1";
});
CompletableFuture<String> server2 = CompletableFuture.supplyAsync(() -> {
    sleep(1000); return "Response from Server 2";
});
CompletableFuture<String> server3 = CompletableFuture.supplyAsync(() -> {
    sleep(2000); return "Response from Server 3";
});

// Returns result of whichever completes first
CompletableFuture<Object> fastest = CompletableFuture.anyOf(server1, server2, server3);
System.out.println(fastest.get()); // Response from Server 2 (1000ms is fastest)
```

### Exception handling - exceptionally

exceptionally runs only when an exception occurs. You get the exception and return a fallback value.

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        System.out.println("Fetching data...");
        if (true) throw new RuntimeException("Database connection failed");
        return "data";
    })
    .exceptionally(ex -> {
        // runs only when exception occurs
        System.out.println("Error: " + ex.getMessage());
        return "default data"; // fallback value returned
    });

System.out.println(future.get()); // default data
// Program does not crash - exception was handled
```

### Exception handling - handle

handle runs whether there is an exception or not. You receive both the result (null if exception) and the exception (null if success).

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> fetchFromServer()) // may throw exception
    .handle((result, exception) -> {
        if (exception != null) {
            // something went wrong
            System.out.println("Error: " + exception.getMessage());
            return "fallback value";
        }
        // everything fine
        return result.toUpperCase();
    });

// handle always runs regardless of exception
// exceptionally only runs when exception occurs
```

### Real world example - parallel data loading

```java
import java.util.concurrent.*;

public class DashboardLoader {

    static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        // All three load in parallel - each simulates a DB/API call
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Arman Singh";
        }, executor);

        CompletableFuture<Integer> ordersFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1500);
            return 42;
        }, executor);

        CompletableFuture<Double> balanceFuture = CompletableFuture.supplyAsync(() -> {
            sleep(800);
            return 12500.50;
        }, executor);

        // Wait for all
        CompletableFuture.allOf(userFuture, ordersFuture, balanceFuture).get();

        long end = System.currentTimeMillis();

        System.out.println("User: " + userFuture.get());
        System.out.println("Orders: " + ordersFuture.get());
        System.out.println("Balance: " + balanceFuture.get());
        System.out.println("Total time: " + (end - start) + "ms"); // ~1500ms, not 3300ms

        executor.shutdown();
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}
```

---

## PART 12 - COMMON PROBLEMS AND HOW TO AVOID THEM

### Race Condition

What it is: Two or more threads read and write shared data at the same time. The final result depends on the order of execution which is unpredictable.

Example: Two threads both do count++. Both read 5, both add 1, both write 6. You lost one increment.

Fix: Use synchronized, ReentrantLock, or AtomicInteger for shared mutable data.

```java
// Problem
int count = 0;
void increment() { count++; } // not thread safe

// Fix option 1
synchronized void increment() { count++; }

// Fix option 2
AtomicInteger count = new AtomicInteger(0);
void increment() { count.incrementAndGet(); }
```

### Deadlock

What it is: Thread A holds lock1 and wants lock2. Thread B holds lock2 and wants lock1. Both wait forever.

Fix: Always acquire multiple locks in the same order. If every thread acquires lock1 before lock2, deadlock cannot happen.

```java
// Deadlock prone
Thread A: lock(account1) then lock(account2)
Thread B: lock(account2) then lock(account1) // opposite order!

// Fixed - always lock in same order (smaller account number first)
void transfer(Account from, Account to) {
    Account first = from.id < to.id ? from : to;
    Account second = from.id < to.id ? to : from;
    synchronized(first) {
        synchronized(second) {
            // transfer
        }
    }
}
```

### Memory Visibility Problem

What it is: Thread A updates a variable. Thread B reads it but gets the old cached value because Thread B's CPU cache is not updated.

Fix: Use volatile for variables that are written by one thread and read by others. Or use synchronized which also gives visibility.

```java
// Problem - worker may never stop
boolean running = true;

// Fix
volatile boolean running = true;
```

### Starvation

What it is: A thread is always waiting and never gets to run because other threads keep acquiring the lock first.

Fix: Use a fair ReentrantLock which gives the lock to the thread that has been waiting the longest.

```java
ReentrantLock fairLock = new ReentrantLock(true); // true = fair
```

### Livelock

What it is: Two threads keep responding to each other and keep changing state but neither makes actual progress. Like two people in a hallway both stepping aside at the same time in the same direction, repeatedly.

Fix: Add randomness or a backoff strategy so threads do not always respond the same way.

---

## PART 13 - QUICK REFERENCE

```
CREATING THREADS:
  extends Thread      -> override run() -> call start()
  implements Runnable -> override run() -> new Thread(r).start()
  Lambda              -> new Thread(() -> { }).start()
  Callable            -> executor.submit(callable) -> future.get()

THREAD STATES:
  NEW -> RUNNABLE -> (RUNNING) -> BLOCKED/WAITING/TIMED_WAITING -> TERMINATED

KEY THREAD METHODS:
  start()             -> create new thread and run
  join()              -> wait for thread to finish
  sleep(ms)           -> pause current thread
  interrupt()         -> signal thread to stop
  isAlive()           -> is thread still running
  getState()          -> current state

EXECUTORSERVICE:
  newFixedThreadPool(n)       -> exactly n threads
  newSingleThreadExecutor()   -> 1 thread, sequential
  newCachedThreadPool()       -> dynamic, grows and shrinks
  newScheduledThreadPool(n)   -> delayed or repeated tasks
  submit(Runnable/Callable)   -> submit task, get Future
  shutdown()                  -> graceful stop
  shutdownNow()               -> forceful stop

THREAD SAFETY TOOLS:
  synchronized method/block   -> one thread at a time, visibility + atomicity
  volatile variable           -> visibility only, for flags and simple variables
  AtomicInteger/Long/Boolean  -> atomic ops without lock, fast
  ReentrantLock               -> flexible lock with tryLock and timeout
  ReadWriteLock               -> multiple readers OR one writer

CONCURRENT COLLECTIONS:
  ConcurrentHashMap           -> thread-safe map, segment locking
  CopyOnWriteArrayList        -> thread-safe list, good for read-heavy
  BlockingQueue               -> blocks on empty take or full put

COMPLETABLEFUTURE:
  supplyAsync(supplier)       -> async task with return value
  runAsync(runnable)          -> async task with no return value
  thenApply(function)         -> transform result (sync next step)
  thenAccept(consumer)        -> consume result, return nothing
  thenRun(runnable)           -> run after, ignore result
  thenCompose(function)       -> chain async tasks (async next step)
  thenCombine(cf, bifunction) -> combine two parallel tasks
  allOf(cf1, cf2, ...)        -> wait for all to complete
  anyOf(cf1, cf2, ...)        -> get first completed result
  exceptionally(function)     -> handle exception, return fallback
  handle(bifunction)          -> handle result or exception

COMMON PROBLEMS:
  Race condition    -> use synchronized / AtomicInteger
  Deadlock          -> always acquire locks in same order
  Visibility        -> use volatile or synchronized
  Starvation        -> use fair ReentrantLock
```
