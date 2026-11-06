# Validate Stack Sequences

## Pattern

Stack + Simulation

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int j = 0;

        for (int i = 0; i < pushed.length; i++) {
            stack.push(pushed[i]);

            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }

        return stack.isEmpty();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a stack to simulate the push and pop operations. I iterate through the pushed array and push each element into the stack. After every push, I check whether the top element of the stack matches the current element in the popped array. If they match, I pop the element and move to the next element in the popped array. I repeat this process until the top element does not match. Finally, if the stack is empty, it means the given pop sequence is valid, so I return true.
