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
        int start = 0;
        int end = n - 1;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (target > nums[mid]) {
                start = mid + 1;
            } else if (target < nums[mid]) {
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

The idea is to search for the target in a sorted array by repeatedly dividing the search range into half. I use two pointers, start and end, which represent the current search range.
<br>
I calculate the middle index and compare nums[mid] with the target. If the target is greater than nums[mid], I search in the right half by moving start to mid + 1. If the target is smaller, I search in the left half by moving end to mid - 1. If nums[mid] is equal to the target, I return mid.
<br>
I use start <= end so that even the last remaining element is checked.
<br>
Time Complexity: O(log n), because the search space becomes half after every iteration.
<br>
Space Complexity: O(1), because only a few variables are used.
