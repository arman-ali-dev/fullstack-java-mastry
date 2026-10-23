# Max Consecutive Ones

## Pattern

- Sliding Window + Two Pointers

---

## Optimal Force

### Code

```java
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int n = nums.length;
        int left = 0;
        int window = 0;
        int ans = 0;


        for (int right = 0; right < n; right++) {
            window += nums[right];

            while (window != right - left + 1) {
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

I use two pointers, left and right, to maintain a window containing only ones. I add the current element to the window sum. If the window sum is not equal to the window length, it means the window contains a zero, so I shrink the window from the left until it contains only ones. After that, I update the maximum window length. Finally, I return the maximum number of consecutive ones.
<br>
Time Complexity: O(n) — Each element is added and removed at most once.
<br>
Space Complexity: O(1) — Only a few variables are used.
