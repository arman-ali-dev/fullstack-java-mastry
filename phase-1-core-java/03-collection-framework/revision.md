# Java Collection Framework

Quick-recall bullet notes — read a point, explain it in my own words.

---

## 1. Why Collection Framework Exists

- Before collections: separate variables (`student1`, `student2`...) — not scalable
- Arrays: fixed size at creation — can't grow. Need new array + copy everything to resize. Painful.
- Collection Framework = ready-made data structures — auto resizing, searching, sorting
- Package: `java.util` → `import java.util.*;`

---

## 2. Hierarchy — Key Facts

- `Iterable` (java.lang) → `Collection` (java.util) → `List` / `Set` / `Queue`
- `Map` is **separate** — does NOT extend `Collection`
- `LinkedList` implements **both** `List` and `Deque`
- `Stack` extends `Vector` (legacy)
- `LinkedHashSet` extends `HashSet` (not directly Set)
- `TreeSet` → `NavigableSet` → `SortedSet` → `Set`
- Map side: `HashMap` → `LinkedHashMap`; `TreeMap` via `NavigableMap`→`SortedMap`
- Legacy classes: `Vector`, `Stack`, `Hashtable`

---

## 3. Iterable vs Collection

- `Iterable` = root, in `java.lang`, one method: `iterator()`
- for-each loop = syntactic sugar over `Iterator` (compiler converts it internally)
- `Collection` extends `Iterable` — root for List/Set/Queue
- Core Collection methods: `add`, `addAll`, `remove`, `removeAll`, `retainAll`, `contains`, `containsAll`, `size`, `isEmpty`, `clear`, `toArray`, `iterator`

---

## 4. Iterator / ListIterator

- `Iterator`: `hasNext()`, `next()`, `remove()` — one-way traversal
- Removing during for-each → `ConcurrentModificationException`
- Fix: use `Iterator.remove()` instead — safe

### Fail-Fast vs Fail-Safe
- **Fail-Fast**: throws `ConcurrentModificationException` if collection modified during iteration (except via iterator's own remove). Tracks internal mod-count.
  - Examples: `ArrayList`, `HashMap`, `HashSet`, `LinkedList`
- **Fail-Safe**: works on a copy/snapshot — no exception, original changes don't affect ongoing iteration
  - Examples: `CopyOnWriteArrayList`, `ConcurrentHashMap` (java.util.concurrent package)

### ListIterator (List only)
- Extends Iterator, adds: `hasPrevious()`, `previous()`, `nextIndex()`, `previousIndex()`, `set(e)`, `add(e)`
- Can go **both directions**, can modify while iterating

---

## 5. List — Ordered, Duplicates Allowed, Indexed

### ArrayList
- Resizable array internally. Fills up → new array 1.5x size → copy over
- Default initial capacity = 10 (can set custom: `new ArrayList<>(50)`)
- **Use when**: frequent `get`/`set` by index (O(1)), adding mostly at end
- **Avoid when**: frequent insert/delete in middle (O(n) — shifting)
- `remove(int index)` vs `remove(Object value)` — classic confusion:
  - `list.remove(1)` → removes index 1
  - `list.remove(Integer.valueOf(1))` → removes value 1
- Binary search: list must be sorted first → `Collections.binarySearch(list, val)`
  - not found → returns negative number

### LinkedList
- Doubly linked list — each node has value + prev + next pointers
- Implements **both** List and Deque → usable as Queue or Stack too
- More memory per element (extra 2 references) vs ArrayList
- **Use when**: frequent insert/delete at start/end/middle (O(1)), need Queue/Deque behavior
- **Avoid when**: frequent index access (O(n) — must traverse)
- Extra methods: `addFirst/addLast`, `getFirst/getLast`, `peekFirst/peekLast`, `pollFirst/pollLast`
- As Queue (FIFO): `offer()` add to end, `poll()` remove from front
- As Stack (LIFO): `push()` add to front, `pop()` remove from front

### ArrayList vs LinkedList — memorize this table
| Op | ArrayList | LinkedList |
|---|---|---|
| get(index) | O(1) | O(n) |
| add at end | O(1) | O(1) |
| add at beginning | O(n) | O(1) |
| add/remove middle | O(n) | O(1)* if node ref known |
| Can be Queue/Stack | No | Yes |

### Vector
- Old synchronized ArrayList (Java 1.0, pre-Collections Framework)
- Every method synchronized → slow. Exists only for backward compatibility.
- Modern alternative: `ArrayList` (no threads) / `Collections.synchronizedList()` / `CopyOnWriteArrayList` (concurrent read-heavy)

### Stack
- Extends `Vector` — considered bad design (Stack shouldn't be a List, breaks encapsulation)
- `push()`, `pop()`, `peek()`, `empty()`, `search()` (1-based from top, -1 if not found)
- **Modern recommendation: use `ArrayDeque` instead of `Stack`**
  - `ArrayDeque` not synchronized → faster, cleaner API (no inherited List clutter)
- Classic use case: Balanced brackets problem (push open brackets, pop+match on close)

---

## 6. Set — No Duplicates, No Index

- `add()` returns `false` if duplicate — no error, just ignored
- Duplicate detection uses `equals()` + `hashCode()`
- No `get(index)` — sets aren't indexed

### HashSet
- Uses HashMap internally (element = key, dummy value)
- Order NOT guaranteed
- O(1) for add/remove/contains
- Custom objects need `equals()` + `hashCode()` overridden, else duplicates not detected correctly
- Set operations via existing methods:
  - Union → `addAll()`
  - Intersection → `retainAll()`
  - Difference → `removeAll()`
  - Subset check → `containsAll()`
  - No common elements → `Collections.disjoint(set1, set2)`

### LinkedHashSet
- Extends HashSet, maintains **insertion order** via internal doubly linked list
- Slightly slower than HashSet (extra bookkeeping)
- Common use: removing duplicates from a list while keeping order

### TreeSet
- Implements NavigableSet → SortedSet — elements sorted ascending
- Internally Red-Black tree
- **No null allowed** (needs to compare for sorting)
- O(log n) for add/remove/contains — slower than HashSet
- Key methods: `first()`, `last()`, `floor(x)`, `ceiling(x)`, `lower(x)`, `higher(x)`
- `headSet(x)` (< x), `tailSet(x)` (>= x), `subSet(from, to)`
- `descendingSet()`, `pollFirst()`, `pollLast()`
- Custom objects: need `Comparable` OR pass a `Comparator`

---

## 7. Queue — FIFO (mostly)

### Two method styles — always prefer the safe ones
| Operation | Throws Exception | Safe (returns special value) |
|---|---|---|
| Insert | add() | offer() — false if full |
| Remove | remove() | poll() — null if empty |
| Examine | element() | peek() — null if empty |

### LinkedList as Queue
- `offer()` add to end, `poll()` remove from front, `peek()` look at front

### PriorityQueue
- NOT FIFO — always serves **highest priority** first
- Default = min-heap (smallest first)
- Internally: binary heap (array-based)
- Null NOT allowed
- Max-heap: pass comparator `(a, b) -> b - a`
- Custom priority: pass any comparator (e.g., sort strings by length)

### Deque (Double-Ended Queue)
- Extends Queue — insert/remove from **both ends**
- Can act as Queue (FIFO) AND Stack (LIFO)
- Front ops: `addFirst/offerFirst`, `removeFirst/pollFirst`, `getFirst/peekFirst`
- Back ops: `addLast/offerLast`, `removeLast/pollLast`, `getLast/peekLast`
- Stack-style (maps to front): `push()`=addFirst, `pop()`=removeFirst, `peek()`=peekFirst

### ArrayDeque
- Implements Deque via resizable **circular array**
- **Preferred over LinkedList** for Queue/Deque — no node overhead, better cache performance, faster
- Null NOT allowed
- Can be used as Queue or Stack (see above)

---

## 8. Map — Key-Value Pairs

- Keys unique, values can repeat, each key → exactly one value
- Putting same key again → replaces old value
- Map does NOT extend Collection

### HashMap
- Internally: array of buckets (default 16), `hashCode()` decides bucket
- Collisions handled via LinkedList/Tree in same bucket
- Load factor 0.75 → resize to 2x when 75% full
- Order not guaranteed
- Key methods:
  - `put`, `get`, `getOrDefault`, `containsKey`, `containsValue`
  - `putIfAbsent` — only adds if key missing
  - `replace` — only updates if key exists
  - `remove(key)` / `remove(key, value)` — second removes only if value also matches
  - `compute`, `computeIfAbsent`, `computeIfPresent`, `merge` — functional update patterns
- 4 ways to loop: `entrySet()`, `keySet()`, `values()`, `forEach(lambda)`
- **Frequency counting pattern**: `map.merge(word, 1, Integer::sum)` or `getOrDefault(word,0)+1`
- **Grouping pattern**: `map.putIfAbsent(key, new ArrayList<>()); map.get(key).add(value);`

### LinkedHashMap
- Maintains insertion order (or **access order** if `true` passed in constructor)
- Access-order + `removeEldestEntry()` override = classic **LRU Cache** implementation

### TreeMap
- Implements NavigableMap — sorted by key ascending, Red-Black tree
- Null key NOT allowed (needs comparison), null values OK
- O(log n) ops
- Methods mirror TreeSet: `firstKey/lastKey`, `lowerKey/higherKey/floorKey/ceilingKey`, `headMap/tailMap/subMap`, `descendingMap`, `pollFirstEntry/pollLastEntry`

### Hashtable
- Legacy synchronized HashMap — all methods synchronized (slow)
- No null key or null value allowed at all
- Use `HashMap` (single-thread) or `ConcurrentHashMap` (thread-safe) instead

### WeakHashMap
- Keys held via **weak references** — entry auto-removed once key has no other strong references (GC eligible)
- Use case: caches that should self-clean

### IdentityHashMap
- Uses `==` (reference equality) instead of `.equals()` for keys
- Two different objects with same content = different keys here

### EnumMap
- Keys = enum constants only. Faster/more memory-efficient than HashMap for enum keys. Iterates in enum declaration order.

---

## 9. Generics

- `List<String>` — compiler enforces type, avoids `ClassCastException` at runtime
- Raw type (no generics) = old, dangerous, avoid
- Multiple type params: `Map<String, List<Integer>>`
- Wildcards:
  - `? extends Number` — any subtype of Number (read-only safe)
  - `? super Integer` — Integer or any supertype

---

## 10. Null Handling — Memorize This

| Collection | Null allowed? |
|---|---|
| ArrayList / LinkedList / Vector / Stack | multiple nulls OK |
| HashSet / LinkedHashSet | ONE null OK |
| TreeSet | **no null** (needs comparison) |
| HashMap / LinkedHashMap | ONE null key, multiple null values |
| TreeMap | **no null key** |
| Hashtable | **no null key or value** |
| WeakHashMap | null key/values OK |
| PriorityQueue / ArrayDeque | **no null** |

---

## 11. Arrays vs Collections

- Array: fixed size, primitives allowed, `arr[0]`, `arr.length`, less memory, no built-in methods
- ArrayList: dynamic size, objects only, `list.get(0)`, `list.size()`, rich methods
- Array → List: `new ArrayList<>(Arrays.asList(arr))`
- List → Array: `list.toArray(new String[0])`
- **Trap**: `Arrays.asList()` result is FIXED SIZE — can't add/remove — wrap in `new ArrayList<>(...)` if you need to modify

---

## 12. Unmodifiable / Immutable Collections

- `Collections.unmodifiableList(original)` — wrapper can't be modified, but **original still can be** (and changes reflect in wrapper)
- Java 9+: `List.of(...)`, `Set.of(...)`, `Map.of(...)` — fully immutable, can't modify at all
- Java 10+: `List.copyOf(original)` — independent immutable copy

---

## 13. Which Collection to Use — Decision Guide

- Need key-value? → **Map**
  - sorted keys → TreeMap | insertion order → LinkedHashMap | LRU → LinkedHashMap(access-order) | enum keys → EnumMap | thread-safe → ConcurrentHashMap | just fast → HashMap
- Need unique elements only? → **Set**
  - sorted → TreeSet | insertion order → LinkedHashSet | just fast → HashSet
- Need FIFO/priority? → **Queue**
  - priority → PriorityQueue | both ends → ArrayDeque | plain FIFO → ArrayDeque/LinkedList
- Need LIFO (stack)? → **ArrayDeque** (push/pop/peek) — not `Stack`
- Otherwise → **List**
  - lots of index access → ArrayList | lots of insert/delete → LinkedList | thread-safe → CopyOnWriteArrayList

### Big-O cheat sheet
| Op | ArrayList | LinkedList | HashSet | HashMap | TreeSet/Map |
|---|---|---|---|---|---|
| get by index/key | O(1)/N/A | O(n)/N/A | N/A | O(1) | O(log n) |
| add at end | O(1) | O(1) | O(1) | O(1) | O(log n) |
| add at start | O(n) | O(1) | — | — | — |
| contains | O(n) | O(n) | O(1) | O(1) | O(log n) |

---

## 14. Thread Safety

- Most `java.util` collections are **NOT thread-safe** by default
- Old-style wrappers (avoid in new code): `Collections.synchronizedList/Set/Map(...)` — still need manual sync block for iteration
- **Preferred (java.util.concurrent)**:
  - `CopyOnWriteArrayList` — every write makes a new copy, good for read-heavy/write-light
  - `ConcurrentHashMap` — segmented locking, fast
  - `ConcurrentLinkedQueue` — thread-safe queue
  - `BlockingQueue` (e.g. `LinkedBlockingQueue`) — waits if empty/full
- Legacy thread-safe (avoid, backward-compat only): `Vector`, `Hashtable`, `Stack`

---

## 15. Sorting

### Comparable — natural ordering
- Implemented **inside** the class: `class Student implements Comparable<Student>`
- One `compareTo()` method → only ONE natural order per class
- `Collections.sort(list)` uses it automatically

### Comparator — custom ordering
- **External** to the class — can have many comparators for different orders
- Lambda: `(a, b) -> a.marks - b.marks`
- Clean builder style: `Comparator.comparing(s -> s.name)`, `Comparator.comparingInt(s -> s.marks)`
- `.reversed()` — flip order
- `.thenComparing(...)` — tie-breaker / secondary sort
- `list.sort(comparator)` or `Collections.sort(list, comparator)`

### Binary Search
- List MUST be sorted first
- `Collections.binarySearch(list, value)` — returns index, or negative if not found
- With custom objects: pass the same comparator used to sort
