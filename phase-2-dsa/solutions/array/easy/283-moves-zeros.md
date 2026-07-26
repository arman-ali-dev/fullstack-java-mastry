# Moves Zeros

## Pattern

Two Pointers (Read Pointer & Write Pointer)

---

## Optimal Approach

### Code

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int start = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[start++] = nums[i];
            }
        }

        for (int i = start; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

My approach uses the Two Pointers pattern. I maintain a write pointer called start, which always points to the position where the next non-zero element should be placed. Then I traverse the array using another pointer. Whenever I encounter a non-zero element, I copy it to the start position and increment start. This effectively shifts all non-zero elements to the front while preserving their original order. After processing all the elements, every position before start contains the required non-zero values. Finally, I fill the remaining positions from start to the end of the array with zeros. This approach performs the operation in-place without using any extra array, resulting in O(n) time complexity and O(1) extra space.
