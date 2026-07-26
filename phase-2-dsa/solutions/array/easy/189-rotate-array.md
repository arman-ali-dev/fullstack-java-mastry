# Rotate Array

## Pattern

Array Reversal (Three Reversals)

---

## Optimal Approach

### Code

```java
class Solution {
    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }
    }

    public void rotate(int[] nums, int k) {
        int n = nums.length;

        if (n < 2) return;

        k = k % n;

        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

My approach uses the Array Reversal pattern. First, I calculate k % n so that unnecessary rotations are avoided when k is greater than the array size. Then I reverse the entire array. After that, I reverse the first k elements and finally reverse the remaining elements. These three reversal operations place every element in its correct rotated position without using any extra array. Since each reversal is a linear operation, the overall time complexity is O(n) and the space complexity is O(1).
