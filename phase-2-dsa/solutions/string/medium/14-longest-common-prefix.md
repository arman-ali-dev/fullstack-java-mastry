# Longest Common Prefix

## Pattern

- Horizontal Scanning (String Comparison)

## Optimal Approach

### Code

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {

        StringBuilder longestStr = new StringBuilder(strs[0]);

        for (int i = 1; i < strs.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < longestStr.length(); j++) {
                if (j < strs[i].length() && longestStr.charAt(j) == strs[i].charAt(j)) {
                    sb.append(strs[i].charAt(j));
                } else {
                    break;
                }
            }

            if (sb.length() == 0) {
                return "";
            }

            longestStr = sb;
        }

        return longestStr.toString();
    }
}
```

### Time Complexity

- O(n x m)

### Space Complexity

- O(m)

---

### Explanation

A straightforward approach is to compare every pair of strings character by character, but that leads to unnecessary comparisons. Instead, I use the first string as the initial common prefix. Then I compare this prefix with every other string one by one. For each comparison, I keep matching characters from the beginning until I find a mismatch or one of the strings ends. The matched part becomes the new common prefix. As I continue comparing with the remaining strings, the common prefix can only become shorter, never longer. If it ever becomes empty, I immediately return an empty string because no common prefix exists. After comparing all the strings, the remaining prefix is the longest common prefix.
<br>
Time Complexity: O(n × m), where n is the number of strings and m is the length of the shortest common prefix (or the average string length in the worst case). Each character is compared at most once across all strings.
<br>
Space Complexity: O(m) because I use a StringBuilder to store the current common prefix.
