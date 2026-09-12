# Binary Search

## Pattern

Binary Search

---

## Optimal Approach

### Code

```java
class Solution {
    public int findMin(int[] nums) {
        int n = nums.length;
        int st = 0;
        int end = n - 1;

        int min = nums[0];

        while (st <= end) {
            int mid = (st + end) / 2;

            min = Math.min(min, nums[mid]);

            if (nums[mid] > nums[end]) {
                st = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return min;
    }
}
```

### Time Complexity

- O(log n)

### Space Complexity

- O(1)

### Explanation

I use binary search to find the minimum element in the rotated sorted array.
<br>
First, I compare nums[mid] with nums[end].
<br>
If nums[mid] > nums[end], it means the minimum element is definitely on the right side, so I move st to mid + 1.
<br>
Otherwise, the minimum can be at mid or on the left side, so I move end to mid - 1.
<br>
At every step, I also compare nums[mid] with my current min and keep the smaller value.
<br>
Finally, min contains the smallest element in the rotated sorted array.
<br>
Time Complexity: O(log n) because binary search reduces the search space roughly by half in every iteration.
<br>
Space Complexity: O(1) because I only use a few variables.
