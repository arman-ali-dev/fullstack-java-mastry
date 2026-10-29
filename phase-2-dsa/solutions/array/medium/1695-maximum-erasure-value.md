# Maximum Erasure Value

## Pattern

Sliding Window + HashMap (Last Seen Index) + Running Sum

---

## Optimal Approach

### Code

```java
class Solution {
    public int maximumUniqueSubarray(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        int res = 0;
        int left = 0;
        int right = 0;

        int window = 0;
        for (; right < n; right++) {
            window += nums[right];

            int idx = map.getOrDefault(nums[right], -1);

            if (idx != -1 && idx >= left) {
                for (int i = left; i <= idx; i++) {
                    window -= nums[i];
                    left++;
                }
            }

            res = Math.max(res, window);
            map.put(nums[right], right);
        }

        return res;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

---

### Explanation

I use a sliding window to maintain a subarray containing only unique elements. I keep a HashMap that stores the last seen index of each number and a variable to track the current window sum. For every element, I add it to the sum and check whether it has appeared before within the current window. If a duplicate exists, I remove elements from the left until the previous occurrence is excluded. I update the maximum sum after ensuring the window contains only unique elements. Finally, I return the maximum sum found
