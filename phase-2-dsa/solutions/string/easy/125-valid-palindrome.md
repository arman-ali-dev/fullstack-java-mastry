# Valid Palindrome

## Pattern

Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;

        while (start <= end) {
            // check for no alphanumeric
            if (!Character.isLetterOrDigit(s.charAt(start))) {
                start++;
                continue;
            }

            if (!Character.isLetterOrDigit(s.charAt(end))) {
                end--;
                continue;
            }

            if (Character.toLowerCase(s.charAt(start)) != Character.toLowerCase(s.charAt(end))) {
                return false;
            }

            start++;
            end--;
        }

        return true;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use the Two Pointers pattern directly on the original string. I place one pointer at the beginning and another at the end. If either character is not alphanumeric, I simply skip it by moving the corresponding pointer. When both characters are valid, I convert them to lowercase and compare them. If they are different, I immediately return false. Otherwise, I move both pointers toward the center and continue. If the entire traversal finishes without finding any mismatch, the string is a palindrome. This approach processes the string in one traversal with O(n) time complexity and O(1) extra space.
