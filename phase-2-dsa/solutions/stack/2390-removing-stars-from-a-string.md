# Removing Stars From a String

## Pattern

Stack + String Processing

---

## Optimal Approach

### Code

```java
class Solution {
    public String removeStars(String s) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (ch != '*') {
                stack.push(ch);
            } else if (!stack.isEmpty()) {
                stack.pop();
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

I use a stack to remove characters based on the star operations. I traverse the string character by character. If the current character is not a star, I push it onto the stack. If it is a star, I remove the top character from the stack because the star represents deleting the closest non-star character to its left. Finally, I iterate through the stack, append the remaining characters to a StringBuilder, and return the resulting string.
