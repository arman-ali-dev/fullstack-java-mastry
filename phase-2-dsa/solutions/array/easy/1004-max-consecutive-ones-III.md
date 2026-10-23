# Max Consecutive Ones III

## Pattern

- Sliding Window + Two Pointers

---

## Optimal Force

### Code

```java
class Solution {
    public int longestOnes(int[] nums, int k) {
        int n = nums.length;
        int left = 0;
        int ans = 0;

        int window = 0;

        for (int right = 0; right < n; right++) {
            window += nums[right];

            while (window + k < right - left + 1) {
                window -= nums[left];
                left++;
            }

            ans = Math.max(ans, right - left + 1);
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

I use two pointers to maintain a sliding window. The variable window stores the number of ones in the current window. The remaining elements are zeros, which we can flip using k. If the number of ones plus k is less than the window length, it means we need more flips than allowed, so I shrink the window from the left. For every valid window, I update the maximum length. Finally, I return the longest length.
<br>
Time Complexity: O(n) — Each element is added and removed at most once.
<br>
Space Complexity: O(1) — Only a constant number of variables are used.
