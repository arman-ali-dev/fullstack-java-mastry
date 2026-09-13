# Search Insert Position

## Pattern

Binary Search

---

## Optimal Approach

### Code

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int n = nums.length;
        int start = 0;
        int end = n - 1;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return start;
    }
}
```

### Time Complexity

- O(log n)

### Space Complexity

- O(1)

### Explanation

I use binary search because the array is sorted.
<br>
If nums[mid] is equal to the target, I return mid.
<br>
If the target is greater than nums[mid], I move to the right side by updating start = mid + 1.
<br>
Otherwise, I move to the left side using end = mid - 1.
<br>
If the target is not found, the loop ends when start becomes the correct insertion position. Therefore, I return start.
<br>
For example, if the array is [1,3,5,6] and target is 2, the correct position is index 1, and after binary search start becomes 1.
<br>
Time Complexity: O(log n)
<br>
Space Complexity: O(1)
