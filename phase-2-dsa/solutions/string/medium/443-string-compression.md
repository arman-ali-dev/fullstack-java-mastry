# String Compression

## Pattern

Two Pointers (Read & Write Pointers)

---

## Optimal Approach

### Code

```java
class Solution {
    public int compress(char[] chars) {
        int n = chars.length;

        int idx = 0;
        for (int i = 0; i < n; i++) {
            char ch = chars[i];

            int count = 0;
            while (i < n && ch == chars[i]) {
                count++;
                i++;
            }

            if (count == 1) {
                chars[idx++] = ch;
            } else {
                chars[idx++] = ch;
                String digits = Integer.toString(count);
                for (int j = 0; j < digits.length(); j++) {
                    char digit = digits.charAt(j);
                    chars[idx++] = digit;
                }
            }

            i--;
        }

        return idx;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

A straightforward approach is to create a new string or a new character array and store the compressed result in it. Although this is easy to implement, it requires extra space.
<br>
To optimize it, I use the Two Pointers pattern. One pointer reads the original array and counts the frequency of consecutive identical characters, while another pointer writes the compressed result back into the same array. For each group of consecutive characters, I first write the character. If its count is greater than one, I convert the count into a string and write each digit one by one into the array. After processing all the groups, the write pointer represents the length of the compressed string, which I return. Since every character is visited only once, the time complexity is O(n) and the space complexity is O(1) because the compression is performed in-place.
