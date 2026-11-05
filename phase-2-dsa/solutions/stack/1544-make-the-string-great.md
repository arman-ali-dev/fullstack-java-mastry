# Make The String Great

## Pattern

Stack + String Processing

---

## Optimal Approach

### Code

```java
class Solution {
    public String makeGood(String s) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (!stack.isEmpty() && Character.isLowerCase(stack.peek()) && Character.isUpperCase(ch) && Character.toLowerCase(stack.peek()) == Character.toLowerCase(ch)) {
                stack.pop();
            } else if (!stack.isEmpty() && Character.isUpperCase(stack.peek()) && Character.isLowerCase(ch) && Character.toLowerCase(stack.peek()) == Character.toLowerCase(ch)) {
                stack.pop();
            } else {
                stack.push(ch);
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

I use a stack to remove adjacent characters that have the same letter but different cases. I traverse the string character by character. If the stack is not empty, I check whether the top character and the current character represent the same letter but have different cases. If they match this condition, I remove the top character from the stack. Otherwise, I push the current character onto the stack. This process also handles new adjacent pairs formed after removing characters. Finally, I build the resulting string using StringBuilder and return it.
