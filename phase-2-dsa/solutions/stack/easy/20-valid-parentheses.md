# Longest Palindromic Substring

## Pattern

Stack (Last In Fifo)

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (stack.isEmpty()) {
                return false;
            } else if (stack.peek() == '(' && ch == ')' ||
                    stack.peek() == '[' && ch == ']' ||
                    stack.peek() == '{' && ch == '}') {
                stack.removeLast();
            } else {
                return false;
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

To optimize it, I use a Stack. As I traverse the string, every opening bracket is pushed onto the stack. When I encounter a closing bracket, I first check whether the stack is empty. If it is, the expression is invalid because there is no matching opening bracket. Otherwise, I compare the closing bracket with the opening bracket at the top of the stack. If they match, I remove the opening bracket from the stack because that pair is balanced. If they do not match, I immediately return false. After processing the entire string, if the stack is empty, it means every opening bracket had a matching closing bracket in the correct order, so the string is valid. Otherwise, it is invalid.
