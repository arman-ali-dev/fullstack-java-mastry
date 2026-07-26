# Java Fundamentals - Quick Recall Summary

One-line-per-concept summary. Goal: read it, remember what the topic does, not full syntax.

---

## PART 1 - Java Program Structure

- **package** — folder for your class, avoids naming conflicts, lowercase convention (com.company.module)
- **import** — brings in classes from other packages; `java.lang` is auto-imported (String, Math, System)
- **class** — everything lives inside a class; class name must match file name exactly
- **main method** — JVM entry point: `public static void main(String[] args)`; args = command-line arguments
- **println/print/printf** — println adds newline, print doesn't, printf does formatted output (%s %d %f %n)

## PART 2 - Primitive Data Types

- 8 primitives, not objects, store value directly: `byte, short, int, long, float, double, char, boolean`
- **int** — default choice for integers; **long** — needs `L` suffix, use for big numbers (IDs, timestamps)
- **double** — default choice for decimals; **float** needs `f` suffix, rarely used
- Never use double/float for money — floating point rounding errors; use BigDecimal
- **char** — single quotes, secretly a number (Unicode); can do char arithmetic
- **boolean** — only true/false, no 0/1 tricks like C
- Instance variables get default values (0, false, null); local variables do NOT — must initialize manually
- Underscores allowed in number literals for readability: `1_000_000`

## PART 3 - Type Casting

- **Widening (implicit)** — small to big type, automatic, no data loss: byte→int→long→float→double
- **Narrowing (explicit)** — big to small type, manual `(type)` cast, risk of data loss
- Classic bug: `int/int = int` division even if result stored in double — must cast an operand to double BEFORE dividing

## PART 4 - Operators

- **Arithmetic** — `+ - * / %`; modulo `%` = remainder, used for even/odd checks and cycles
- **Increment/Decrement** — `x++` (use then increment) vs `++x` (increment then use) — matters in expressions
- **Relational** — `== != > < >= <=`; for objects/Strings, `==` compares memory address not content — use `.equals()`
- **Logical** — `&& ||` short-circuit (skip second condition if first decides result); `! ` negation
- **Bitwise** — `& | ^ ~ << >>`; used for flags, fast multiply/divide by 2, even/odd check (`n & 1`)
- **Ternary** — `condition ? valueIfTrue : valueIfFalse` — shorthand if-else for assignment
- **Precedence** — when unsure, use parentheses

## PART 5 - Control Flow

- **if/else if/else** — condition-based branching, works with ranges and complex boolean logic
- **switch** — compares ONE variable against multiple EXACT values; needs `break` or falls through to next case
- switch works with: int, byte, short, char, String, enum — NOT long/float/double/boolean
- **switch expression (Java 14+)** — arrow syntax `case x -> result`, no break needed, no fall-through
- Rule of thumb: switch for exact-value matches, if-else for ranges/conditions

## PART 6 - Loops

- **for loop** — use when iteration count is known; init; condition; update
- **while loop** — condition checked BEFORE body runs; use when iterations unknown
- **do-while loop** — condition checked AFTER body runs; body executes at least once (good for menus)
- **for-each (enhanced for)** — clean iteration over arrays/collections, no index access, no going backwards
- **break** — exits loop immediately; **continue** — skips current iteration, moves to next
- **labeled break** — breaks out of nested/outer loops directly

## PART 7 - Arrays

- Fixed-size container, same type, index starts at 0, size can't change after creation
- Declare vs create: `int[] arr;` (no memory) vs `arr = new int[5];` (memory allocated, defaults set)
- `.length` is a property (no parentheses), unlike String's `.length()`
- **Arrays class utilities** — `Arrays.sort()`, `Arrays.binarySearch()`, `Arrays.copyOf()`, `Arrays.fill()`, `Arrays.equals()`, `Arrays.toString()` (readable print)
- **2D arrays** — array of arrays, `grid[row][col]`, `.length` for rows, `.length` of a row for columns
- **Jagged arrays** — rows can have different lengths
- `Arrays.deepToString()` for readable 2D array printing

## PART 8 - Methods

- Block of reusable code that does one job; `returnType name(params) { return value; }`
- **static methods** — belong to the class, callable without creating an object (like `main` itself)
- **Method overloading** — same name, different parameter list; resolved at compile time; return type alone can't differentiate
- **Varargs** — `int... numbers` accepts zero or more arguments, treated as an array inside method
- **Pass by value** — primitives copy the value; objects copy the reference (can modify object contents, can't reassign the original reference)

## PART 9 - Scope and Lifetime of Variables

- **Local variables** — inside a method/block only, no default value, destroyed when block ends
- **Instance variables (fields)** — belong to each object, get default values, separate copy per object
- **Static variables** — ONE copy shared across ALL objects of the class (e.g. a counter)
- **Variable shadowing** — local variable/parameter with same name hides instance variable; use `this.name` to access the instance version

## PART 10 - String Basics

- String is an object, not primitive; **immutable** — every "modification" creates a new String
- String pool — literals are reused (`==` may be true); `new String()` creates separate heap object (`==` false)
- Always use `.equals()` for content comparison, never `==`
- Key methods: `trim/strip`, `length()`, `isEmpty()` vs `isBlank()`, `substring()`, `indexOf()`, `split()`, `replace()`, `contains()`, case conversions
- **Concatenation with `+` in a loop is slow** — creates many intermediate objects
- **StringBuilder** — mutable string, use for building strings step by step / in loops (`append`, `insert`, `delete`, `reverse`)
- StringBuilder = not thread-safe, faster; StringBuffer = thread-safe, slower — use StringBuilder by default

## PART 11 - Wrapper Classes

- Object version of each primitive (int→Integer, char→Character, etc.) — needed because Collections only hold objects
- **Autoboxing** — primitive auto-converts to wrapper; **Unboxing** — wrapper auto-converts to primitive
- Gotcha: unboxing a `null` wrapper throws NullPointerException
- Gotcha: Integer caching — values -128 to 127 are cached so `==` works; outside that range `==` fails, always use `.equals()`
- Useful statics: `Integer.parseInt()`, `Integer.MAX_VALUE`, `Character.isDigit()`, `Character.isLetter()`, `Boolean.parseBoolean()`

## PART 12 - Generics Basics

- Write type-safe reusable code without knowing the exact type in advance — catches type errors at compile time instead of runtime
- **Generic class** — `class Box<T> { T content; }` — T is replaced with real type when used
- **Generic method** — `<T> void method(T[] arr)` — type parameter scoped to just that method
- **Multiple type parameters** — e.g. `Pair<K, V>` for key-value style pairs
- **Bounded type** — `<T extends Number>` restricts T to Number or its subclasses
- **Wildcard `?`** — represents an unknown type, e.g. `List<?>` accepts a list of any type; `List<? extends Number>` accepts Number or subtypes

## PART 13 - Scanner Class

- Reads input from keyboard/file/string; part of `java.util`
- `nextInt()`, `nextDouble()`, `next()` (one word), `nextLine()` (full line including spaces), `nextBoolean()`
- **Classic bug** — `nextInt()`/`nextDouble()` leaves a leftover newline in buffer; call an extra `sc.nextLine()` before reading a real line
- `hasNextInt()` / `hasNext()` / `hasNextLine()` — check before reading, useful for input validation loops or reading until end of input

---

## One-Glance Cheat Table

| Topic           | What it does                                                             |
| --------------- | ------------------------------------------------------------------------ |
| package/import  | Organize and reuse code across files                                     |
| main method     | Entry point where JVM starts running the program                         |
| Primitives      | 8 raw value types, no object overhead                                    |
| Type Casting    | Convert between types — implicit (safe) or explicit (risky)              |
| Operators       | Arithmetic, comparison, logical, bitwise, ternary manipulation of values |
| Control Flow    | Decide which code runs (if/else, switch)                                 |
| Loops           | Repeat code (for, while, do-while, for-each)                             |
| Arrays          | Fixed-size same-type collection accessed by index                        |
| Methods         | Reusable named blocks of logic, can overload by parameters               |
| Scope           | Where a variable lives and how long it survives (local/instance/static)  |
| Strings         | Immutable text objects with rich built-in methods                        |
| Wrapper Classes | Object form of primitives, needed for Collections, auto-converted        |
| Generics        | Type-safe reusable classes/methods without hardcoding a type             |
| Scanner         | Reads user input from console                                            |
