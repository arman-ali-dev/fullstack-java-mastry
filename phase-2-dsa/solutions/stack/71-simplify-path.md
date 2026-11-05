# Simplify Path

## Pattern

Stack + String Processing

---

## Optimal Approach

### Code

```java
class Solution {
    public String simplifyPath(String path) {
        String[] arr = path.split("/");
        Stack<String> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        for (String i : arr) {
            if (!stack.isEmpty() && i.equals("..")) {
                stack.pop();
            } else if (!i.equals("")  && !i.equals("..") && !i.equals(".")) {
                stack.push(i);
            }
        }

        for (String i : stack) {
            sb.append("/");
            sb.append(i);
        }

        return sb.isEmpty() ? "/" : sb.toString();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

First, I split the given path using / to get individual directory names. Then I use a stack to process each part. If the current part is .., I remove the last directory from the stack if it is not empty. If the current part is empty or ., I ignore it because it represents an extra slash or the current directory. Otherwise, I push the directory name onto the stack. Finally, I iterate through the stack and build the simplified absolute path using StringBuilder. If the stack is empty, I return /.
