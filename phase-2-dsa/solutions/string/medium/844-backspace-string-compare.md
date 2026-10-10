# Backspace String Compare

## Pattern

StringBuilder

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean backspaceCompare(String s, String t) {
        StringBuilder sbs = new StringBuilder();
        StringBuilder sbt = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '#' && sbs.length() != 0) {
                sbs.setLength(sbs.length() - 1);
                continue;
            }

            if (ch != '#') {
                sbs.append(ch);
            }
        }

        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);

            if (ch == '#' && sbt.length() != 0) {
                sbt.setLength(sbt.length() - 1);
                continue;
            }

            if (ch != '#') {
                sbt.append(ch);
            }
        }

        return sbs.toString().equals(sbt.toString());
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(n + m)

### Explanation

I use a StringBuilder to simulate the backspace operation for both strings.
<br>
I traverse each string character by character. If the character is not #, I append it to the StringBuilder.
<br>
If it is # and the StringBuilder is not empty, I remove the last character using setLength.
<br>
This produces the final string after applying all backspace operations.
<br>
Finally, I compare the two processed strings using equals. If they are equal, I return true; otherwise, I return false.
<br>
Time Complexity: O(n + m), where n and m are the lengths of the two strings.
<br>
Space Complexity: O(n + m) for the two StringBuilders.
