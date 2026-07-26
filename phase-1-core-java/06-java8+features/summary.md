# Java 8+ Features - Quick Recall Summary

One-line-per-concept summary. Goal: read it, remember what the topic does, not full syntax.

---

## Functional Interfaces

- An interface with exactly ONE abstract method (any number of default/static methods allowed) — this is what makes lambdas possible
- `@FunctionalInterface` — optional annotation, compiler error if a second abstract method sneaks in
- **Predicate\<T\>** — takes input, returns boolean (`test()`); combine with `.and()`, `.or()`, `.negate()`
- **Function\<T,R\>** — takes T, returns R (`apply()`); chain with `.andThen()`, `.compose()`
- **Consumer\<T\>** — takes input, returns nothing (`accept()`); chain with `.andThen()`
- **Supplier\<T\>** — takes nothing, returns a value (`get()`) — used for lazy/on-demand value generation
- Also exist: **BiFunction, BiPredicate, BiConsumer** (two inputs), **UnaryOperator/BinaryOperator** (input/output same type)

## Lambda Expressions

- Short way to implement a functional interface's single method — replaces verbose anonymous classes
- Syntax scales down: `(a, b) -> { }` → `(a, b) -> expr` → `a -> expr` (single param, no parens) → `() -> expr` (no params)
- Lambdas can use outer variables only if they're **effectively final** (never reassigned after being used)

## Method References

- Even shorter than a lambda when the lambda just calls an existing method: `ClassName::methodName` or `object::methodName`
- **Static method ref** — `Integer::parseInt` (same as `s -> Integer.parseInt(s)`)
- **Instance method on specific object** — `prefix::concat`
- **Instance method on the parameter itself** — `String::toUpperCase` (same as `s -> s.toUpperCase()`)
- **Constructor reference** — `ArrayList::new` (same as `() -> new ArrayList()`)

## Default and Static Methods in Interfaces

- **default method** — has a body inside the interface; implementing classes get it for free, can override; solves the problem of adding new methods without breaking existing implementers
- **Diamond problem** — if two interfaces both give a default method with the same name, the implementing class MUST override it (can call a specific one via `InterfaceName.super.method()`)
- **static method** in interface — belongs to the interface itself, called via interface name, NOT inherited, cannot be overridden — used for utility/factory methods

## Stream API

- A Stream processes data from a source (List/array) through a pipeline — it's NOT a data structure, doesn't store data, doesn't modify the original, and can only be used ONCE
- **Intermediate ops** (lazy, return a new Stream): `filter()` (keep matching), `map()` (transform), `flatMap()` (flatten nested collections into one stream), `sorted()`, `distinct()`, `limit()`/`skip()`, `peek()` (debug without changing)
- **Terminal ops** (trigger execution, close the stream): `forEach()`, `collect()`, `count()`, `min()`/`max()`, `findFirst()`/`findAny()`, `anyMatch()`/`allMatch()`/`noneMatch()`, `reduce()` (combine all into one value), `toArray()`
- **Collectors** — `toList()`, `toSet()`, `toMap()`, `joining()`, `groupingBy()` (like SQL GROUP BY), `partitioningBy()` (splits into true/false groups), `summarizingInt()` (min/max/avg/sum/count in one go)
- **mapToInt/mapToLong/mapToDouble** — convert to a primitive stream to get direct numeric aggregation like `.sum()`, `.average()`

## Optional Class

- A container that may or may not hold a value — forces the caller to explicitly handle "no value" instead of risking a silent `NullPointerException`
- **Optional.of(x)** — throws if x is null; **Optional.ofNullable(x)** — safe, becomes empty if x is null; **Optional.empty()** — explicitly empty
- Avoid `isPresent()` + `get()` — defeats the purpose; prefer:
  - **orElse(default)** — always evaluates the default even if not needed
  - **orElseGet(supplier)** — only computes default when actually empty (better for expensive defaults)
  - **orElseThrow()** — throws (custom or default exception) if empty
  - **ifPresent() / ifPresentOrElse()** — run code conditionally without unwrapping manually
  - **map()** — transform the value if present, stays empty otherwise (no NPE)
  - **filter()** — keep the value only if it passes a condition, else becomes empty
  - **flatMap()** — use when your mapping function itself returns an Optional (avoids nested Optional\<Optional\<T\>\>)
- Rule of thumb: use as a **return type**, never as a method parameter or a class field, and never store it inside a collection

## Date and Time API (java.time, Java 8)

- Replaced the old mutable, confusing `Date`/`Calendar` — everything here is **immutable**
- **LocalDate** — date only (year/month/day); **LocalTime** — time only; **LocalDateTime** — both, no timezone
- **ZonedDateTime** — date+time WITH timezone — needed for cross-country scheduling
- **Duration** — time-based amount (hours/minutes/seconds) — for `LocalTime`/`LocalDateTime`
- **Period** — calendar-based amount (years/months/days) — for `LocalDate`
- **DateTimeFormatter** — format dates to custom patterns (`dd/MM/yyyy`) and parse strings back to dates
- **Instant** — machine timestamp (epoch millis) — good for logging/measuring elapsed time

## var Keyword (Java 10)

- Lets the compiler infer a local variable's type — type is still fixed at compile time, not dynamic typing
- Must be initialized at declaration; cannot be used for method parameters, return types, or class fields — **local variables only**

## Immutable Collections (Java 9/10)

- **List.of(), Set.of(), Map.of()** — quick immutable collection literals; any add/remove/set throws `UnsupportedOperationException`; no nulls allowed; `Set.of` rejects duplicates
- **Map.ofEntries()** — needed when you have more than 10 key-value pairs (Map.of caps at 10)
- **List.copyOf()/Set.copyOf()/Map.copyOf()** (Java 10) — immutable snapshot of an existing (mutable) collection; original can still change independently afterward

## String Methods (Java 11)

- **isBlank()** — true for empty OR whitespace-only (unlike `isEmpty()` which only checks length 0)
- **strip()/stripLeading()/stripTrailing()** — like `trim()` but Unicode-aware
- **lines()** — splits a multi-line string into a Stream of lines
- **repeat(n)** — repeats the string n times

## Switch Expressions (Java 14)

- New arrow syntax `case x -> result` — no `break` needed, no fall-through, and it can directly RETURN a value into a variable
- Multiple labels in one case: `case 1, 2, 3 -> "Weekday"`
- Use `{ }` + `yield` when a case needs multiple statements before producing its value
- Works with int, String, and enum types

## Records (Java 16)

- One-line immutable data class: `record Person(String name, int age) {}` auto-generates constructor, accessor methods (`name()`, `age()` — no "get" prefix), `equals()`, `hashCode()`, `toString()`
- Fields are always final/immutable
- Can still add a **compact constructor** for validation, plus instance/static methods
- Best for: DTOs, value objects, API responses — NOT for classes needing inheritance or mutable state

## Sealed Classes (Java 17)

- Restrict exactly which classes are allowed to extend/implement yours: `sealed class Shape permits Circle, Rectangle, Triangle`
- Each permitted subclass must be `final`, `sealed` (further restricted), or `non-sealed` (opens it back up)
- Pairs well with switch expressions — compiler can verify all subclasses are handled, no default case needed
- Use for modeling a known fixed set of types (Result: Success/Failure, Shape: Circle/Rectangle/Triangle)

## Stream API Improvements (Java 9)

- **takeWhile()** — takes elements while condition is true, STOPS at the first failure (doesn't skip-and-continue)
- **dropWhile()** — drops elements while condition is true, then keeps everything after the first failure
- **Stream.iterate(seed, condition, next)** — bounded version of iterate, no `.limit()` needed
- **Optional.ifPresentOrElse()** — handles both present and empty cases in one call
- **Optional.or()** — supplies an alternative Optional (not just a plain value like `orElse`) if empty
- **Optional.stream()** — converts an Optional into a 0-or-1-element Stream, handy for flattening `List<Optional<T>>`

## Pattern Matching for instanceof (Java 16+)

- `if (obj instanceof String s)` automatically casts and binds `s` — no manual `(String) obj` needed
- The bound variable can even be used directly inside the same condition: `if (value instanceof Integer i && i > 10)`

## Text Blocks (Java 15)

- Multi-line strings with `"""..."""` — no need for `\n` and string concatenation for JSON/HTML/SQL snippets
- Indentation matching the closing `"""` position is automatically stripped
- `\` at line end suppresses the newline; `\s` forces a trailing space that would otherwise be stripped

---

## One-Glance Cheat Table

| Feature                           | What it does                                                 |
| --------------------------------- | ------------------------------------------------------------ |
| Functional Interface              | Interface with exactly one abstract method — enables lambdas |
| Lambda                            | Short inline implementation of a functional interface        |
| Method Reference                  | Even shorter lambda form pointing to an existing method      |
| default/static in interfaces      | Add behavior to interfaces without breaking implementers     |
| Stream API                        | Pipeline processing of data — filter/map/reduce style        |
| Optional                          | Explicit "may be empty" wrapper, avoids silent NPEs          |
| java.time (LocalDate etc.)        | Immutable, clear date/time API replacing Date/Calendar       |
| var                               | Compiler infers local variable type                          |
| List.of/Set.of/Map.of             | Quick, truly immutable collection literals                   |
| String.isBlank/strip/lines/repeat | Java 11 string quality-of-life methods                       |
| Switch expressions                | Arrow-syntax switch that returns a value directly            |
| Records                           | One-line immutable data classes                              |
| Sealed classes                    | Restrict which classes can extend a type                     |
| takeWhile/dropWhile               | Conditional stream slicing based on a predicate              |
| Pattern matching instanceof       | Auto-cast right inside the instanceof check                  |
| Text blocks                       | Clean multi-line string literals with `"""`                  |
