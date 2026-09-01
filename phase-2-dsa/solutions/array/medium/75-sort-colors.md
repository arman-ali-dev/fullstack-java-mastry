# Sort Colors

## Pattern

Dutch National Flag Algorithm + Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public void sortColors(int[] nums) {
        int n = nums.length;
        int low = 0;
        int mid = 0;
        int high = n - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[mid];
                nums[mid++] = nums[low];
                nums[low++] = temp;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high--] = temp;
            }
        }
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

The idea is to sort the array containing only 0, 1, and 2 without using an extra array. I maintain three pointers: low, mid, and high.
<br>
low represents the position where the next 0 should go, mid is the current element I am processing, and high represents the position where the next 2 should go.
<br>
If nums[mid] is 0, I swap it with nums[low] and move both low and mid forward because the 0 is now in its correct position.
<br>
If nums[mid] is 1, it is already in the correct middle region, so I only move mid forward.
<br>
If nums[mid] is 2, I swap it with nums[high] and decrease high. I don't increment mid here because the element coming from high has not been processed yet.
<br>
I continue until mid crosses high. This puts all 0s on the left, 1s in the middle, and 2s on the right.
<br>
Time Complexity: O(n), because every element is processed at most a constant number of times.
<br>
Space Complexity: O(1), because the sorting is done in-place using only three pointers and a temporary variable.
