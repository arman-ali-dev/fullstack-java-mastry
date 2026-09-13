# Two Sum II - Input Array Is Sorted

## Pattern

Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        int st = 0;
        int end = n - 1;

        int[] ans = new int[2];

        while (st < end) {
            int result = nums[st] + nums[end];

            if (result == target) {
                ans[0] = st + 1;
                ans[1] = end + 1;
                break;
            } else if (result > target) {
                end--;
            } else {
                st++;
            }
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

I use the two-pointer approach because the array is already sorted.
<br>
I keep one pointer at the beginning and one pointer at the end. I calculate their sum.
<br>
If the sum is equal to the target, I return their indices.
<br>
If the sum is greater than the target, I move the end pointer left because I need a smaller value.
<br>
If the sum is smaller than the target, I move the st pointer right because I need a larger value.
<br>
I continue this until I find the required pair.
<br>
Since the problem uses 1-based indexing, I return st + 1 and end + 1.
<br>
Time Complexity: O(n) because each pointer moves only in one direction.
<br>
Space Complexity: O(1) because I use only a few variables.
