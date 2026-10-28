# Binary Subarrays With Sum

## Pattern

Prefix Sum + HashMap (Frequency Counting)

---

## Optimal Approach

### Code

```java
class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        int ans = 0;

        Map<Integer, Integer> map = new HashMap<>();

        prefixSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }

        for (int i = 0; i < n; i++) {
            if (prefixSum[i] == goal) {
                ans++;
            }

            int val = prefixSum[i] - goal;
            ans += map.getOrDefault(val, 0);
            map.put(prefixSum[i], map.getOrDefault(prefixSum[i], 0) + 1);
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

---

### Explanation

First, I calculate the prefix sum at each index. Then, I traverse the prefix sums and check whether the current prefix sum equals the goal. If it does, I increment the answer because the subarray starting from index zero has the required sum.
<br>
Next, I calculate the difference between the current prefix sum and the goal. If this difference exists in the HashMap, its frequency tells me how many previous prefix sums can form subarrays with the required sum. I add that frequency to the answer and then store the frequency of the current prefix sum in the map. Finally, I return the total count.
