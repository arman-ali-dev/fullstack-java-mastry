# Minimum Size Subarray Sum

## Pattern

Sliding Window + Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = 0;
        int ans = Integer.MAX_VALUE;

        int sum = 0;
        for (; right < n; right++) {
            sum += nums[right];

            while (sum >= target) {
                ans = Math.min(ans, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

I use two pointers to maintain a sliding window and a variable to store its sum. I expand the window by adding elements from the right. Whenever the sum becomes greater than or equal to the target, I update the minimum length and shrink the window from the left by removing elements. I continue this process to find the smallest valid subarray. If no valid subarray exists, I return zero.
<br>
Time Complexity: O(n) — Each element is added to and removed from the window at most once.
<br>
Space Complexity: O(1) — Only a constant number of variables are used.
