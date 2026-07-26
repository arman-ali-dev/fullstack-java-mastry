# Java Collections Framework - Quick Recall Summary

One-line-per-collection summary. Goal: read it, remember what each collection does and when to reach for it — not full syntax.

---

## ArrayList

- Resizable array-backed list; fast random access by index, slower inserts/removes in the middle
- Allows duplicates, maintains insertion order
- Common ops: `add`, `get(index)`, `remove`, `add(index, value)`, `size()`, `contains()`, `Collections.sort()`, `Collections.reverse()`, `Collections.max()/min()`
- Best when: you mostly read by index and append at the end

## LinkedList

- Doubly-linked list implementation of `List` (and `Deque`)
- Fast insert/remove at both ends (`addFirst`, `addLast`, `removeFirst`, `removeLast`), slower random access than ArrayList
- Best when: frequent additions/removals from front/back, not much random indexing

## Vector

- Legacy version of ArrayList, synchronized (thread-safe) — slower than ArrayList because of that
- Uses old-style methods too: `elementAt()`, `firstElement()`, `lastElement()`, `addElement()`, `removeElement()`
- Rarely used in modern code; ArrayList (or `Collections.synchronizedList`) is preferred

## Stack

- LIFO (last in, first out) structure, extends Vector
- Core ops: `push()`, `pop()`, `peek()` (see top without removing), `empty()`, `search()`
- Best when: you need classic stack behavior (undo, backtracking, expression evaluation)

## ArrayDeque (as Queue)

- Double-ended queue; used as FIFO queue with `offerLast()` to add and `pollFirst()` to remove
- `peekFirst()` looks at the front without removing
- Faster than LinkedList for queue operations in most cases

## ArrayDeque (as Stack)

- Same ArrayDeque class, used as LIFO stack instead: `push()`/`pop()`/`peek()` operate on the front
- Preferred over the old `Stack` class for stack behavior — not synchronized, more efficient

## HashSet

- Unordered collection of unique elements — no duplicates, no guaranteed order
- Core ops: `add`, `contains`, `remove`
- Set operations: `addAll()` = union, `retainAll()` = intersection, `removeAll()` = difference, `Collections.disjoint()` checks for no overlap
- Best when: you only care about uniqueness, not order

## LinkedHashSet

- Like HashSet but maintains insertion order
- Common use: remove duplicates from a List while preserving original order (convert List → LinkedHashSet → back to List)

## TreeSet

- Unique elements, automatically sorted (natural order or custom comparator)
- Navigation methods: `first()`, `last()`, `floor(x)` (≤x), `ceiling(x)` (≥x), `headSet(x)` (< x), `tailSet(x)` (≥ x), `subSet(a,b)` (range)
- `pollFirst()`/`pollLast()` remove and return smallest/largest
- Best when: you need a sorted set with fast range queries

## HashMap

- Key-value pairs, no duplicate keys, no guaranteed order
- Core ops: `put`, `get`, `getOrDefault`, `containsKey`, `containsValue`, `remove`, `putIfAbsent` (only sets if key absent)
- Iterate via `entrySet()` (key+value), `keySet()` (keys only), `values()` (values only), or `forEach(lambda)`
- Best when: fast key-based lookup, order doesn't matter

## HashMap for Frequency Counting

- Classic pattern: `map.getOrDefault(key, 0) + 1` or the shorter `map.merge(key, 1, Integer::sum)` to count occurrences
- Loop `entrySet()` to find the max-count entry manually
- Dump into a `TreeMap` when you want the counted results sorted by key

## HashMap for Grouping

- Pattern: key = category (e.g. grade), value = `List` of items belonging to that category
- Use `putIfAbsent(key, new ArrayList<>())` before adding, so the list exists before you add to it
- Effectively a manual version of what `Collectors.groupingBy` does in streams

## LinkedHashMap

- Like HashMap but preserves insertion order when iterating — regular HashMap does not guarantee this
- Same API as HashMap (`put`, `get`, `entrySet`, etc.), just predictable order

## TreeMap

- Key-value pairs automatically sorted by key
- Navigation methods: `firstKey()`, `lastKey()`, `lowerKey(x)`, `higherKey(x)`, `headMap(x)`, `tailMap(x)`
- Best when: you need key-based lookup AND sorted iteration

## PriorityQueue

- Queue that always returns the smallest (or highest-priority) element first via `poll()`, regardless of insertion order
- `peek()` shows the head without removing; for-each/Iterator does NOT return elements in sorted order — only repeated `poll()` does
- Pass a custom comparator (e.g. `(a, b) -> b - a`) to make it a max-heap instead of min-heap
- Best when: you repeatedly need the min/max element from a changing set (scheduling, Dijkstra, top-K problems)

## Iterator

- Safe way to loop AND modify a collection at the same time using `hasNext()`, `next()`, `it.remove()`
- Removing elements during a regular for-each loop throws `ConcurrentModificationException` — Iterator's `remove()` avoids this
- `ListIterator` (for List types) can additionally move backward, not just forward

## Sorting with Comparator

- `list.sort(Comparator)` or `Collections.sort(list, comparator)` — sort custom objects by any field
- Lambda form: `(a, b) -> a.getMarks() - b.getMarks()` for ascending, reverse the subtraction for descending
- Chain conditions for tie-breaking: sort by one field, then by another when the first is equal (`thenComparing`)

## Nested Collections

- Common real-world shape: `Map<String, List<Integer>>` — one key maps to a whole list of values (e.g. student → their marks)
- Loop the outer map's `entrySet()`, then loop the inner list for each entry
- Useful for grouped/aggregated data (compute an average per key, find the max group, etc.)

## Mixed / Choosing the Right Collection

- Order matters + duplicates allowed → **List** (ArrayList/LinkedList)
- Uniqueness only, order doesn't matter → **HashSet**
- Uniqueness + insertion order → **LinkedHashSet**
- Uniqueness + sorted order → **TreeSet**
- Key-value lookup, order doesn't matter → **HashMap**
- Key-value lookup + insertion order → **LinkedHashMap**
- Key-value lookup + sorted by key → **TreeMap**
- Always-need-min/max from a changing set → **PriorityQueue**

---

## One-Glance Cheat Table

| Collection    | Duplicates? | Order                  | Best For                        |
| ------------- | ----------- | ---------------------- | ------------------------------- |
| ArrayList     | Yes         | Insertion order        | Fast index access, append-heavy |
| LinkedList    | Yes         | Insertion order        | Fast add/remove at both ends    |
| Vector        | Yes         | Insertion order        | Legacy, thread-safe list        |
| Stack         | Yes         | LIFO                   | Undo, backtracking              |
| ArrayDeque    | Yes         | FIFO or LIFO           | Modern queue/stack replacement  |
| HashSet       | No          | Unordered              | Fast uniqueness checks          |
| LinkedHashSet | No          | Insertion order        | Unique + order preserved        |
| TreeSet       | No          | Sorted                 | Unique + sorted + range queries |
| HashMap       | No (keys)   | Unordered              | Fast key-value lookup           |
| LinkedHashMap | No (keys)   | Insertion order        | Lookup + order preserved        |
| TreeMap       | No (keys)   | Sorted by key          | Lookup + sorted iteration       |
| PriorityQueue | Yes         | Priority order on poll | Always get min/max next         |
