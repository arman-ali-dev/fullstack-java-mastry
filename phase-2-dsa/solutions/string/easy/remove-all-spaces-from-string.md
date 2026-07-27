# Remove All Spaces From String

## Pattern

String Traversal (Linear Scan)

---

## Optimal Approach

### Code

```java
public class Solution {
    public static String removeSpaces(String str) {
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                sb.append(str.charAt(i));
            }
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

My approach is based on a single traversal of the string. I use a StringBuilder to efficiently build the result because strings in Java are immutable, and modifying them repeatedly would be inefficient. I traverse each character of the input string, and whenever the current character is not a space, I append it to the StringBuilder. If the character is a space, I simply skip it. After processing all the characters, I convert the StringBuilder back to a string and return it. Since I traverse the string only once, the time complexity is O(n) and the extra space complexity is O(n) for storing the resulting string.
