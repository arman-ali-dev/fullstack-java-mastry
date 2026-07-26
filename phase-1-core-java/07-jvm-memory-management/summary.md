# JVM & Memory Management - Quick Recall Summary

One-line-per-concept summary. Goal: read it, remember what the topic does, not full syntax.

---

## PART 1 - What is JVM and Why

- `.java` → `javac` compiles to platform-independent **bytecode** (.class) → JVM on each platform converts bytecode to machine code — this is "Write Once, Run Anywhere"
- JVM also manages memory, does garbage collection, provides security sandboxing, and optimizes code at runtime via JIT

## PART 2 - JVM Architecture

- Three main parts: **Class Loader Subsystem**, **Runtime Data Areas** (memory), **Execution Engine**
- **Class Loader** — loads .class files in 3 phases: Loading → Linking (verify/prepare/resolve) → Initialization
- **Method Area (Metaspace in Java 8+)** — class-level data (methods, static vars, constant pool), shared across threads, one per JVM
- **Heap** — where all `new` objects live, shared across threads, GC-managed, split into Young/Old Generation
- **Java Stack** — one per thread, stores method call frames + local variables, LIFO, `StackOverflowError` if full
- **PC Register** — one per thread, tracks the current instruction address
- **Native Method Stack** — for native (C/C++) method calls
- **Execution Engine** — Interpreter (reads bytecode line-by-line, slow) + JIT Compiler (compiles "hot" frequently-run code to native machine code for speed) + Garbage Collector

## PART 3 - ClassLoader Mechanism

- Three loaders in hierarchy: **Bootstrap** (core Java classes, native code, returns null as its own "loader") → **Extension** (jre/lib/ext) → **Application/System** (your classpath)
- **Delegation model** — a loader always asks its PARENT first before trying itself; ensures core classes (like `java.lang.String`) can never be shadowed by malicious versions — this is a security feature
- **Loading phases** — Loading (read .class, create Class object) → Linking (Verification of bytecode → Preparation, static vars set to DEFAULT values → Resolution, symbolic refs become real memory addresses) → Initialization (static vars get their real values, static blocks run, parent initialized before child)
- **Custom ClassLoader** — possible by extending `ClassLoader` and overriding `findClass()`, useful for loading from unusual sources (network, encrypted files)

## PART 4 - Heap vs Stack Memory

- **Stack** — per-thread, stores method frames + local primitives/references, LIFO, fast (pointer move), fixed size, thread-safe by nature; `StackOverflowError` on overflow (e.g. infinite recursion)
- **Heap** — shared by all threads, stores all objects (`new` keyword), GC-managed, slower, size controlled by `-Xmx`, needs synchronization for shared access; `OutOfMemoryError` when full
- Rule of thumb: primitives and object REFERENCES live on the stack; the actual OBJECTS (and arrays, Strings) live on the heap
- Static variables live in the **Method Area**, not heap or stack directly
- String literals go to the **String Pool** (part of heap); `new String()` creates a separate heap object outside the pool

## PART 5 - Heap Memory Structure

- Heap splits into **Young Generation** and **Old Generation (Tenured)**
- **Young Gen** — where new objects are born; has **Eden** (initial allocation) + two **Survivor spaces (S0, S1)**; Minor GC runs when Eden fills
- Objects that survive enough Minor GC cycles (age threshold, usually ~15) get **promoted** to Old Generation
- **Old Generation** — long-lived objects; Major/Full GC here is much more expensive (longer pauses)
- **Metaspace (Java 8+)** — replaced old PermGen; stores class metadata, lives in native memory outside the heap

## PART 6 - Garbage Collection

- An object becomes eligible for GC once NO references point to it anymore (including circular references between otherwise-unreferenced objects — "island of isolation")
- **Mark and Sweep** — Mark phase: traverse from GC Roots (local vars in active frames, static vars, active threads) and mark everything reachable as alive; Sweep phase: free everything unmarked; Compact phase (optional): defragment memory
- **Minor GC** — fast, cleans Young Generation, most objects die young; **Major/Full GC** — slow, cleans Old Generation, causes Stop-The-World pauses
- **GC algorithms** — Serial (single-threaded, small apps), Parallel (multi-threaded, throughput-focused, Java 8 default), G1 (region-based, predictable pauses, Java 9+ default, good for 4GB+ heaps), ZGC/Shenandoah (very low pause, Java 11+, real-time apps)
- **finalize()** — legacy, unreliable cleanup hook, deprecated in practice; use try-with-resources or explicit cleanup methods instead
- **Memory leaks still happen in Java** despite GC — common causes: static collections that grow forever, undereregistered listeners, inner class holding outer class reference, unclosed streams/connections, uncleaned ThreadLocals

## PART 7 - Java Memory Model (JMM)

- Defines the rules for when one thread's changes become visible to other threads — needed because each thread may cache variables in its own working memory (CPU cache) instead of always reading main memory
- **Working memory vs Main memory** — a thread's local cached view vs the actual shared RAM; a thread can update its working copy without another thread seeing it yet
- **Happens-before relationship** — the core JMM guarantee mechanism; key rules: actions within one thread happen in order; unlocking a monitor happens-before a later lock of the same monitor; a volatile write happens-before a subsequent volatile read; `Thread.start()` happens-before actions in the new thread; all actions happen-before `Thread.join()` returns
- **Three core problems**: **Visibility** (stale cached reads — fixed by volatile/synchronized), **Atomicity** (compound ops like `count++` getting interrupted mid-way — fixed by synchronized/AtomicInteger), **Ordering** (compiler/CPU instruction reordering breaking multithreaded assumptions — prevented by volatile/synchronized)
- **Safe publication** — making an object available to other threads only after it's fully constructed; achieved via static initializers, volatile fields, final fields, or synchronized access
- **Double-checked locking** — classic JMM pitfall: without `volatile` on the singleton instance, another thread can see a non-null reference to a not-yet-fully-constructed object due to instruction reordering; fixed by declaring the field `volatile`

## PART 8 - JVM Tuning Flags

- `-Xms` — initial heap size; `-Xmx` — max heap size (set equal to avoid resize overhead); `-Xmn` — young generation size; `-Xss` — per-thread stack size
- GC selection flags: `-XX:+UseG1GC`, `-XX:+UseZGC`, `-XX:+UseSerialGC`
- GC diagnostics: `-verbose:gc`, `-XX:+PrintGCDetails`, `-XX:+HeapDumpOnOutOfMemoryError`

---

## One-Glance Cheat Table

| Topic                         | What it does                                                              |
| ----------------------------- | ------------------------------------------------------------------------- |
| JVM pipeline                  | .java → bytecode (javac) → machine code (JVM) — platform independence     |
| ClassLoader hierarchy         | Bootstrap → Extension → Application, parent-first delegation for security |
| Stack                         | Per-thread, method frames + locals, fast, StackOverflowError on overflow  |
| Heap                          | Shared, all objects, GC-managed, OutOfMemoryError when full               |
| Young/Old Generation          | New objects in Young (Eden+Survivors); long-lived promoted to Old         |
| Garbage Collection            | Mark-and-sweep from GC Roots; Minor GC (fast) vs Major GC (slow, pauses)  |
| GC algorithms                 | Serial/Parallel (throughput) vs G1/ZGC (low pause, modern default)        |
| JMM                           | Rules for cross-thread visibility of memory changes                       |
| Visibility/Atomicity/Ordering | The three concurrency correctness problems JMM addresses                  |
| JVM flags                     | -Xms/-Xmx (heap), -Xss (stack), -Xmn (young gen), GC selection            |
