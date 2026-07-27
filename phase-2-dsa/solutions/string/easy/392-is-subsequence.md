# Is Subsequence

## Pattern

- Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isSubsequence(String s, String t) {

        if (s.isEmpty()) {
            return true;
        }

        int k = 0;

        for (int i = 0; i < t.length(); i++) {
            if (k < s.length() && t.charAt(i) == s.charAt(k)) {
                k++;
            }
        }

        return s.length() == k;
    }
}
```

### Time Complexity

- O(n+m)

### Space Complexity

- O(1)

### Explanation

My approach uses the Two Pointers pattern. I keep one pointer for the first string and traverse the second string using another pointer. Whenever the current characters of both strings match, I move the pointer of the first string forward because I have found the next required character in the correct order. Regardless of whether the characters match or not, I continue traversing the second string. If, by the end of the traversal, the pointer of the first string reaches its length, it means every character of the first string was found in order, so it is a subsequence. Otherwise, it is not. This approach scans both strings only once, resulting in O(n + m) time complexity and O(1) extra space.
