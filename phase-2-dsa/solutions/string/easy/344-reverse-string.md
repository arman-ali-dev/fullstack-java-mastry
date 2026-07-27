# Reverse String

## Pattern

Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public void reverseString(char[] s) {
        int start = 0;
        int end = s.length - 1;

        while (start < end) {
            char temp = s[start];
            s[start++] = s[end];
            s[end--] = temp;
        }
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

The straightforward approach would be to create a new character array and copy the characters from the end of the original array to the beginning of the new array. Although this works, it requires O(n) extra space.
<br>
To optimize it, I use the Two Pointers pattern. I place one pointer at the beginning of the array and another at the end. In each iteration, I swap the characters at these two positions, then move the left pointer forward and the right pointer backward. I continue this process until both pointers meet or cross each other. This reverses the string in-place without using any extra array. Since I traverse only half of the array, the overall time complexity is O(n) and the space complexity is O(1).
