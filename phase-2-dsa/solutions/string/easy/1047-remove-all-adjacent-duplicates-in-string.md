# Remove All Adjacent Duplicates In String

## Pattern

- Stack

---

## Optimal Approach

### Code

```java
class Solution {
    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == ch) {
                stack.pop();
            } else {
                stack.add(ch);
            }
        }

        for (char ch : stack) {
            sb.append(ch);
        }

        return sb.toString();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a stack to remove adjacent duplicate characters. I traverse the string character by character. If the stack is not empty and its top character matches the current character, I pop the top character. Otherwise, I push the current character onto the stack. This process also handles new adjacent duplicates formed after removing a pair. Finally, I iterate through the stack, append the remaining characters to a StringBuilder, and return the resulting string.
