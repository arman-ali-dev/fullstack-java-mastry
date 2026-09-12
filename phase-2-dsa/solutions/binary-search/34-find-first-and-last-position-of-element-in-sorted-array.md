# Find First and Last Position of Element in Sorted Array

## Pattern

Binary Search

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int st = 0;
        int end = n - 1;

        int[] ans = new int[2];

        ans[0] = -1;
        ans[1] = -1;

        while (st <= end) {
            int mid = (st + end) / 2;

            if (target == nums[mid]) {
                ans[0] = mid;
                end = mid - 1;
            } else if (target < nums[mid]) {
                end = mid - 1;
            } else {
                st = mid + 1;
            }
        }

        st = 0;
        end = n - 1;

        while (st <= end) {
            int mid = (st + end) / 2;

            if (target == nums[mid]) {
                ans[1] = mid;
                st = mid + 1;
            } else if (target < nums[mid]) {
                end = mid - 1;
            } else {
                st = mid + 1;
            }
        }

        return ans;
    }
}
```

### Time Complexity

- O(log n)

### Space Complexity

- O(n)

### Explanation

I use binary search twice because the array is sorted.
<br>
In the first binary search, whenever I find the target, I store its index as the first position and continue searching on the left side using end = mid - 1.
<br>
In the second binary search, whenever I find the target, I store its index as the last position and continue searching on the right side using st = mid + 1.
<br>
Finally, I return both positions. If the target is not present, both values remain -1.
<br>
Time Complexity: O(log n) because both searches take O(log n).
<br>
Space Complexity: O(1) because I use only constant extra variables.
