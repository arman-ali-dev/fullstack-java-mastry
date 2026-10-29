# Contains Duplicate

## Pattern

HashSet

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int val : nums) {
            if (set.contains(val)) {
                return true;
            }
            set.add(val);
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

I use a HashSet to keep track of the elements I have already visited. I traverse the array and check whether the current element is already present in the set. If it is present, it means the array contains a duplicate, so I return true. Otherwise, I add the element to the set and continue. If I finish traversing the array without finding any duplicate, I return false.
