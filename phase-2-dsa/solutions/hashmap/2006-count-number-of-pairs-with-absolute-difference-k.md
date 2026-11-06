# Count Number of Pairs With Absolute Difference K

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public int countKDifference(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;

        for (int val : nums) {
            count += map.getOrDefault(val - k, 0);
            count += map.getOrDefault(val + k, 0);
            map.put(val, map.getOrDefault(val, 0) + 1);
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

I use a HashMap to store the frequency of each number. I iterate through the array and check whether val - k or val + k already exists in the map. If they exist, I add their frequencies to the count because they form valid pairs with the current number. After checking, I update the frequency of the current number in the map. Finally, I return the total count.
