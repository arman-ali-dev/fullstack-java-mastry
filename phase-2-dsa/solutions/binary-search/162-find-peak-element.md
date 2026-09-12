# Find Peak Element

## Pattern

Binary Search

---

## Optimal Approach

### Code

```java
class Solution {
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        int st = 0;
        int end = n - 1;

        while (st <= end) {
            int mid = (st + end) / 2;

            if (mid != n - 1 && nums[mid + 1] > nums[mid]) {
                st = mid + 1;
            } else if (mid != 0 && nums[mid - 1] > nums[mid]) {
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(log n)

### Space Complexity

- O(1)

### Explanation

I use binary search to find a peak element.
<br>
First, I check the right side of mid. If nums[mid + 1] > nums[mid], it means the array is increasing at this point, so a peak must exist on the right side. Therefore, I move st to mid + 1.
<br>
Otherwise, I check the left side. If nums[mid - 1] > nums[mid], it means a peak exists on the left side, so I move end to mid - 1.
<br>
If neither neighbor is greater than nums[mid], then nums[mid] itself is a peak, so I return mid.
<br>
I also check the boundaries using mid != 0 and mid != n - 1 to avoid accessing an invalid index.
<br>
Time Complexity: O(log n) because I eliminate roughly half of the search space in every iteration.
<br>
Space Complexity: O(1) because I only use a few variables.
