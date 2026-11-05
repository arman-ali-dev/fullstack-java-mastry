# Final Prices With a Special Discount in a Shop

## Pattern

Monotonic Stack (Next Smaller or Equal Element)

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int curr = prices[i];

            while (!stack.isEmpty() && stack.peek() > curr) {
                stack.pop();
            }

            res[i] = stack.isEmpty() ? prices[i] : prices[i] - stack.peek();
            stack.push(curr);
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

I traverse the prices array from right to left and use a stack to find the next smaller or equal element for each price. For every current price, I remove elements from the stack that are greater than the current price because they cannot be its discount. If the stack is empty, it means no valid discount exists, so I keep the original price. Otherwise, the top element of the stack is the next smaller or equal price, so I subtract it from the current price. Finally, I push the current price onto the stack and return the result array.
