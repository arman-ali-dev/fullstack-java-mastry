# Java Exception Handling - Quick Recall Summary

One-line-per-concept summary. Goal: read it, remember what the topic does, not full syntax.

---

## PART 1 - What is an Exception and Why

- An exception is an object Java creates describing what went wrong, where, and why — instead of the program just crashing silently
- Lets you catch problems, handle them gracefully, and keep the program running

## PART 2 - Exception Hierarchy

- Everything extends **Throwable** → splits into **Error** and **Exception**
- **Error** — serious JVM-level problems (StackOverflowError, OutOfMemoryError) — NOT meant to be caught/handled, let it crash
- **Exception** — splits into **checked** (IOException, SQLException) and **unchecked/RuntimeException** (NullPointerException, ArithmeticException, ClassCastException, etc.)

## PART 3 - Checked vs Unchecked Exceptions

- **Checked** — extends Exception (not RuntimeException); compiler forces you to `try-catch` or `throws` it; used for predictable external failures (file missing, network down, DB down)
- **Unchecked** — extends RuntimeException; compiler doesn't force handling; represents programming bugs (forgot null check, bad index, divide by zero) — fix the code, don't just catch it
- Design intent: checked = "plan for this," unchecked = "this shouldn't happen at all"

## PART 4 - Try Catch Finally

- **try** — code that might fail; **catch** — runs only if the matching exception is thrown; once thrown, remaining try code is skipped
- **Multiple catch blocks** — order matters: specific exceptions BEFORE general ones, or compile error (unreachable code)
- **Multi-catch (Java 7+)** — `catch (TypeA | TypeB e)` handles multiple exception types the same way in one block
- **finally** — always runs, whether exception thrown/caught or not; only skipped by `System.exit()`; used for cleanup (closing resources)
- **finally with return** — finally's code runs BEFORE the method actually returns; a `return` inside finally overrides the try's return (avoid doing this)
- **Nested try-catch** — inner exceptions can be handled locally without reaching the outer catch; outer `finally` still always runs

## PART 5 - throws Keyword

- Used in a method **signature** to declare "this might throw X, caller must handle it" — passes responsibility upward instead of handling it here
- Can declare multiple exceptions: `throws IOException, SQLException`
- Can technically be used for unchecked exceptions too (optional, just documentation)

## PART 6 - throw Keyword

- Used **inside method body** to actually create and fire an exception right now
- Common uses: input validation failure, a condition that should never happen, converting a low-level exception into a meaningful one
- **throw vs throws** — `throw` fires one exception object now (inside body); `throws` just declares possible exception types (in signature)

## PART 7 - Custom Exceptions

- Create your own exception class when built-in ones don't clearly describe your app's error (e.g. `UserNotFoundException` is clearer than generic `RuntimeException`)
- **Custom unchecked** — `extends RuntimeException`; caller isn't forced to catch it; use for validation/programming-type errors
- **Custom checked** — `extends Exception`; caller IS forced to catch it; use when the situation absolutely must be handled
- Can carry extra data (fields + getters) like requested amount, user ID, error code — useful for meaningful error messages and API responses
- Naming convention: custom exception class names should end with `Exception`

## PART 8 - Try-With-Resources

- Java 7+ feature — declare resources inside `try(...)`, Java auto-calls `close()` when the block ends, whether it succeeded or threw
- Removes the need for manual null-checks and nested try-finally just to close things
- Resource class must implement `AutoCloseable` (just needs a `close()` method)
- **Multiple resources close in REVERSE order** of how they were opened
- Can still combine with `catch`/`finally` — order is: resource closes → catch (if exception) → finally

## PART 9 - Exception Chaining

- Wrap a low-level exception inside a higher-level, more meaningful one while preserving the original as the **cause** — nothing is lost
- Pattern: `throw new HigherLevelException("message", originalException)`
- **getCause()** — retrieves the wrapped original exception; can chain multiple levels deep and unwrap with repeated `.getCause()`
- `printStackTrace()` prints the full chain for debugging

## PART 10 - Best Practices

- Catch **specific** exceptions, not a broad generic `Exception` — broad catches hide what actually failed
- **Never** leave a catch block empty (silently swallowing errors) — at minimum log it, ideally rethrow if you can't truly handle it
- Use **try-with-resources** for anything that needs closing — avoids resource leaks if an exception occurs mid-operation
- Don't use exceptions for normal control flow (e.g. don't use try-catch just to check if a string is a number) — exceptions are for the unexpected, and they're slower than a proper check
- **Throw early, catch late** — validate/throw as soon as a problem is detected; catch only at the level where you have enough context to actually handle it (e.g. at an API boundary)
- When catching and rethrowing, always pass the original exception as the cause to preserve context for debugging

---

## One-Glance Cheat Table

| Topic               | What it does                                                      |
| ------------------- | ----------------------------------------------------------------- |
| Throwable hierarchy | Error (don't catch, JVM-level) vs Exception (checked/unchecked)   |
| Checked exception   | Compiler-forced handling — predictable external failures          |
| Unchecked exception | Not compiler-forced — represents a bug, fix the code              |
| try/catch/finally   | Isolate risky code, handle specific failures, always run cleanup  |
| Multi-catch         | One catch block for several exception types via `\|`              |
| throws              | Declares a method might throw X, passes handling to the caller    |
| throw               | Actually fires an exception object right now                      |
| Custom exceptions   | App-specific, meaningful exception classes (checked or unchecked) |
| try-with-resources  | Auto-closes AutoCloseable resources, even on exception            |
| Exception chaining  | Wrap + preserve original exception as `cause` for full context    |
| Best practices      | Specific catches, never swallow silently, throw early/catch late  |
