# Fibonacci Number

## Pattern

Recursion — Fibonacci

---

## Optimal Approach

### Code

```java
class Solution {
    public int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }
}
```

### Time Complexity

- O(2^n)

### Space Complexity

- O(n)

### Explanation

I use recursion to calculate the Fibonacci number.
<br>
The base cases are fib(0) = 0 and fib(1) = 1. For any other value of n, I calculate the current Fibonacci number by adding the previous two Fibonacci numbers: fib(n - 1) + fib(n - 2).
<br>
So the function keeps breaking the problem into smaller subproblems until it reaches the base cases.
<br>
Time Complexity: O(2^n) because the same Fibonacci values are calculated multiple times.
<br>
Space Complexity: O(n) because the maximum recursion depth can be n
