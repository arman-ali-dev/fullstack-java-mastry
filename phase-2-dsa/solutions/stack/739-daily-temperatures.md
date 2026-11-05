# Daily Temperatures

## Pattern

Monotonic Stack (Next Greater Element) + Reverse Traversal

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int curr = temperatures[i];

            while (!stack.isEmpty() && curr >= temperatures[stack.peek()]) {
                stack.pop();
            }

            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }

        return res;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I traverse the temperatures array from right to left and use a stack to find the next warmer day. I store indices in the stack instead of temperature values because I need to calculate the number of days between two warmer temperatures. For each current temperature, I remove all indices whose temperatures are less than or equal to the current temperature because they cannot be the next warmer day. If the stack is empty, it means there is no warmer day, so I store zero. Otherwise, the top index gives the next warmer day, and I calculate the difference between the two indices. Finally, I push the current index onto the stack and return the result array.
