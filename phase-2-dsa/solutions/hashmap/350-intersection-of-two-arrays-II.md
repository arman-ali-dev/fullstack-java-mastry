# Intersection of Two Arrays II

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num : nums2) {
            if (map.containsKey(num) && map.get(num) > 0) {
                list.add(num);
                map.put(num, map.get(num) - 1);
            }
        }

        int[] ans = new int[list.size()];
        int i = 0;
        for (int num : list) {
            ans[i++] = num;
        }

        return ans;
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(n + k)

### Explanation

The important difference from the normal intersection problem is that here duplicates matter. For example, if a number appears twice in both arrays, it should appear twice in the result.
<br>
So I use a HashMap to store the frequency of every element in nums1. Then I traverse nums2. If the current number exists in the map and its frequency is greater than zero, I add it to the result and decrease its frequency by one. Decreasing the frequency is important because it ensures that I don't use the same occurrence more times than it actually exists in nums1.
<br>
For example, if 2 appears three times in nums1, the map stores 2 -> 3, so it can be added to the result at most three times. Finally, I convert the result list into an array.
<br>
Time Complexity: O(n + m) on average, because I traverse both arrays once and HashMap operations are O(1) on average.
<br>
Space Complexity: O(n + k), where n is the number of distinct elements stored in the HashMap and k is the number of elements in the result
