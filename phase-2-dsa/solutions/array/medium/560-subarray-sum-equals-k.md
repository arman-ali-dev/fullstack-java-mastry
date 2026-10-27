# Subarray Sum Equals K

## Pattern

Prefix Sum + HashMap (Frequency Counting)

---

## Optimal Approach

### Code

```java
class Solution {
    public int subarraySum(int[] nums, int target) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;

        prefixSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }

        for (int i = 0; i < n; i++) {
            if (prefixSum[i] == target) {
                count++;
            }

            int val = prefixSum[i] - target;
            count += map.getOrDefault(val, 0);
            map.put(prefixSum[i], map.getOrDefault(prefixSum[i], 0) + 1);
        }

        return count;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

First, I calculate the prefix sum at every index. The prefix sum represents the sum of all elements from the beginning of the array to the current index.

While traversing the prefix sums, if the current prefix sum equals the target, I increment the count because the subarray starting from index zero has the required sum. Then, I calculate the difference between the current prefix sum and the target. If this difference exists in the HashMap, it means there are previous prefix sums that can form subarrays with the target sum, so I add their frequencies to the count. Finally, I store the frequency of the current prefix sum in the map and return the total count.
