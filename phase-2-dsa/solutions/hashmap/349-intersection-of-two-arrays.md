# Intersection of Two Arrays

## Pattern

HashSet + Lookup

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> result = new HashSet<>();

        for (int num : nums1) {
            set.add(num);
        }

        for (int num : nums2) {
            if (set.contains(num)) {
                result.add(num);
            }
        }

        int[] ans = new int[result.size()];
        int i = 0;
        for (int num : result) {
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

The brute-force approach would be to compare every element of nums1 with every element of nums2, which would take O(n × m) time.
<br>
To optimize it, I use a HashSet. First, I put all elements of nums1 into the set. Then I traverse nums2 and check whether each element exists in the set. If it exists, that means the element is present in both arrays, so I add it to another result set. I use a second set because the intersection should contain each element only once, even if the same element appears multiple times in nums2. Finally, I convert the result set into an integer array and return it.
<br>
Time Complexity: O(n + m) on average, because I traverse both arrays once and HashSet lookup is O(1) on average.
<br>
Space Complexity: O(n + k), where n is the number of elements stored from nums1 and k is the number of unique elements in the result set.
