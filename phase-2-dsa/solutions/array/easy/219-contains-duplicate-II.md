# Contains Duplicate II

## Pattern

HashMap + Last Seen Index

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }

            map.put(nums[i], i);
        }

        return false;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a HashMap to store each number and its most recent index. I traverse the array and check whether the current number already exists in the map. If it exists and the difference between the current index and its previous index is less than or equal to k, I return true because a duplicate exists within the allowed distance. Otherwise, I update the number's index in the map. If no such pair is found after traversing the array, I return false.
