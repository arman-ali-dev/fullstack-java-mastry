# Binary Search

## Pattern

Binary Search

---

## Optimal Approach

### Code

```java
class Solution {
    public int search(int[] nums, int target) {
        int n = nums.length;
        int st = 0;
        int end = n - 1;

        while (st <= end) {
            int mid = (st + end) / 2;

            if (target == nums[mid]) {
                return mid;
            }

            if (nums[st] <= nums[mid]) {
                if (target >= nums[st] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    st = st + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[end]) {
                    st = mid + 1;
                } else {
                    end = mid - 1;
                }
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

I use binary search, but because the array is rotated, I first identify which half of the array is sorted.
<br>
If nums[st] <= nums[mid], the left half is sorted. I check whether the target lies within this sorted range. If it does, I search on the left side; otherwise, I search on the right side.
<br>
Otherwise, the right half is sorted. I check whether the target lies within that range and decide which side to search.
<br>
I repeat this process until I find the target or the search range becomes empty. If the target is not found, I return -1.
<br>
Time Complexity: O(log n) because I eliminate approximately half of the search space in every iteration.
<br>
Space Complexity: O(1) because I only use a few variables.
