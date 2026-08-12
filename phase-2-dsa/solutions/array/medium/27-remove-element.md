# Remove Element

## Pattern

Two Pointers / Read & Write Pointer

---

## Optimal Approach

### Code

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int start = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[start++] = nums[i];
            }
        }

        return start;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

The brute-force approach could be to create another array and copy only the elements that are different from val, but that would require extra space. To do it in-place, I use two pointers. The i pointer scans every element of the array, while start acts as the position where the next valid element should be written. Whenever nums[i] is not equal to val, I copy it to nums[start] and move start forward. If the value is equal to val, I simply skip it. At the end, start represents how many elements are left after removing the target value, so I return it.
<br>
The important point is that I'm not actually deleting elements from the array. I'm overwriting the unwanted elements with valid elements and returning the new valid length.
<br>
Time Complexity: O(n) because I traverse the array once.
<br>
Space Complexity: O(1) because I modify the array in-place and use only a pointer.
