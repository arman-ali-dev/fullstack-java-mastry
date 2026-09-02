# Java Exception Handling

Quick-recall bullet notes — read a point, explain it in my own words.

---

## 1. What Is an Exception & Why It Exists

- Analogy: car tyre punctures → you get a signal, pull over, handle it, continue — doesn't just explode
- Exception = an object Java creates describing what went wrong, where, and why
- Without handling: program crashes immediately on error
- With handling: catch problem, show proper message, keep running
- Common triggers: divide by zero, array index out of bounds, null method call, missing file, DB down

---

## 2. Exception Hierarchy

```
Throwable
   |--- Error (JVM-level, don't catch)
   |       |--- StackOverflowError (infinite recursion)
   |       |--- OutOfMemoryError (heap exhausted)
   |
   |--- Exception
           |--- Checked (IOException, SQLException, FileNotFoundException...)
           |--- RuntimeException → Unchecked (NPE, ArrayIndexOOB, Arithmetic, NumberFormat, ClassCast, IllegalArgument...)
```

- `Throwable` = root of everything. Technically catchable but never should be.
- **Error** = serious JVM problems, NOT meant to be caught/handled — let it crash, fix root cause
- **Exception** = what you actually work with — splits into checked/unchecked

---

## 3. Checked vs Unchecked — Core Interview Topic

- **Checked**: extends `Exception` (not `RuntimeException`) — compiler FORCES you to handle (catch or `throws`), else won't compile
  - Used for: external/predictable failures (file missing, network fail, DB down)
  - Examples: `IOException`, `SQLException`, `FileNotFoundException`
- **Unchecked**: extends `RuntimeException` — compiler does NOT check, compiles fine, crashes at runtime if it happens
  - Used for: programming mistakes/bugs — fix the code, don't just catch
  - Examples: `NullPointerException`, `ArrayIndexOutOfBoundsException`, `ArithmeticException`, `NumberFormatException`, `ClassCastException`

| Feature | Checked | Unchecked |
|---|---|---|
| Extends | Exception | RuntimeException |
| Compiler checks | YES | NO |
| Cause | External failures | Programming bugs |
| Handling | Must try-catch or throws | Optional — fix the bug |

---

## 4. try / catch / finally

- `try` block = code that might throw. Once exception thrown, remaining try lines SKIPPED, jumps to catch.
- Multiple catch blocks allowed — each for different exception type
- **Rule**: specific exceptions BEFORE general ones (else compile error — unreachable code)
  ```java
  catch (ArithmeticException e) { }  // specific first
  catch (Exception e) { }             // general last
  ```
- **Multi-catch (Java 7+)**: `catch (NumberFormatException | ArithmeticException e)` — combine same-handling exceptions with `|`

### finally
- ALWAYS runs — exception or not, caught or not, return hit or not
- Only exception: `System.exit()` kills JVM, finally is skipped entirely
- Use for: closing resources, releasing locks, mandatory cleanup
- **Tricky edge case**: `return` inside `try` — `finally` runs BEFORE the method actually returns
  - If `finally` also has a `return`, it OVERRIDES try's return — confusing, avoid doing this
- **Nested try-catch**: inner exception handled locally doesn't propagate to outer catch; outer finally still always runs

---

## 5. throws Keyword

- Used in method **signature** — declares "this might throw X, I'm not handling it, caller must"
- Passes responsibility up the call chain
- Can declare multiple: `throws IOException, SQLException`
- Can be used on unchecked exceptions too — optional, just documentation

```java
public static String readFile(String path) throws IOException { ... }
```

---

## 6. throw Keyword

- Used INSIDE method body — actually creates and fires an exception NOW
- Use when: validation fails, "should never happen" condition occurs, converting low-level exception to meaningful one

```java
if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
```

### throw vs throws
| throw | throws |
|---|---|
| Inside method body | In method signature |
| Throws exception NOW | Declares what might be thrown later |
| One exception object | Can declare multiple classes |

---

## 7. Custom Exceptions

- Create when built-in exceptions don't clearly describe YOUR app's problem
- `UserNotFoundException` >> generic `RuntimeException` — much more readable

### Custom Unchecked (extends RuntimeException)
- Use for: programming mistakes / invalid data that should've been validated earlier
- Caller NOT forced to catch it
```java
class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String msg) { super(msg); }
}
```

### Custom Checked (extends Exception)
- Use for: situations caller MUST be forced to handle
```java
class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg) { super(msg); }
}
```

### Professional pattern — error codes
- Base `AppException` with `errorCode` + `errorType` fields
- Specific exceptions (`StudentNotFoundException`, `InvalidInputException`) extend the base, pass fixed code/type
- Useful for logging + consistent API error responses

### Naming rule
- Custom exception class names must end with `Exception` — `UserNotFoundException` good, `UserNotFound` bad

---

## 8. Try-With-Resources (Java 7+)

- **Old problem**: manual `close()` in `finally`, must null-check, must catch exceptions from `close()` itself — ugly, error-prone
- **New way**: declare resources inside `try(...)` — Java auto-calls `close()` when try block ends (normally or via exception)
- Resource class must implement `AutoCloseable` (one method: `close()`)

```java
try (FileReader fr = new FileReader("data.txt");
     BufferedReader br = new BufferedReader(fr)) {
    // use br
} // both auto-closed here
```

- **Multiple resources close in REVERSE order** of declaration (last opened → first closed)
- Custom class: `implements AutoCloseable`, override `close()` — usable in try-with-resources
- Can still combine with `catch`/`finally` — order: resource closes → catch (if exception) → finally

---

## 9. Exception Chaining

- Wrapping one exception inside another — preserves original ("cause") while throwing a more meaningful higher-level exception
- **Why**: DB layer throws `SQLException` → service shouldn't leak that detail to controller → wrap in `DataAccessException`, but keep original as `cause` for debugging

```java
class DataAccessException extends RuntimeException {
    public DataAccessException(String msg, Throwable cause) {
        super(msg, cause); // cause = original exception
    }
}
```

- Chain can go multiple levels — `e.getCause()`, `e.getCause().getCause()`, etc.

### Key methods
| Method | Purpose |
|---|---|
| `getMessage()` | error message of THIS exception |
| `getCause()` | the exception that caused this one (null if no chain) |
| `toString()` | class name + message |
| `printStackTrace()` | prints full chain to console — for debugging |

---

## 10. Best Practices (interview-important)

- **Catch specific, not generic `Exception`** — generic hides real cause, catches bugs you should actually fix
- **Never swallow exceptions silently** — empty catch block = worst practice, problems vanish with no trace
  - Minimum: log it. Better: log + rethrow if you can't truly handle it.
- **Use try-with-resources for anything closeable** — avoids leaks if exception happens mid-operation before manual `close()`
- **Don't use exceptions for normal flow control** — e.g. don't use try/catch just to check if a string is a number; exceptions are slow + confusing for that
- **Custom exception names end with `Exception`**
- **Throw early, catch late**:
  - Throw as soon as problem detected (e.g. input validation)
  - Catch at the level with enough context to actually handle it meaningfully (e.g. HTTP request boundary → send proper response)
- **Always chain exceptions when rethrowing** — pass original as `cause`, never lose it
  ```java
  throw new ServiceException("Database error", e); // e preserved
  ```
