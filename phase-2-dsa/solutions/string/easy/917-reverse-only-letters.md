# Reverse Only Letters

## Pattern

- Two Pointers + In-Place Swapping

---

## Optimal Approach

### Code

```java
class Solution {
    public String reverseOnlyLetters(String s) {
        int st = 0;
        int end = s.length() - 1;
        StringBuilder sb = new StringBuilder(s);

        while (st <= end) {
            if (!Character.isLetter(sb.charAt(st))) {
                st++;
                continue;
            }

            if (!Character.isLetter(sb.charAt(end))) {
                end--;
                continue;
            }

            char tempCh = sb.charAt(st);
            sb.setCharAt(st, sb.charAt(end));
            sb.setCharAt(end, tempCh);

            st++;
            end--;
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

I use two pointers, one at the beginning and one at the end of the string. I create a StringBuilder so I can modify the characters. If the character at either pointer is not a letter, I move that pointer inward. If both characters are letters, I swap them and move both pointers inward. I repeat this process until the pointers cross. This reverses only the letters while keeping all non-letter characters in their original positions.
<br>
Time Complexity: O(n) — Each character is visited at most a constant number of times.
<br>
Space Complexity: O(n) — StringBuilder stores a mutable copy of the input string.
