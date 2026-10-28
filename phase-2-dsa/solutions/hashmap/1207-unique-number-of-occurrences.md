# Unique Number of Occurrences

## Pattern

HashMap + HashSet + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();

        for (int val : arr) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        for (int occ : map.values()) {
            if (set.contains(occ)) {
                return false;
            }

            set.add(occ);
        }

        return true;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a HashMap to count the frequency of each number in the array. Then, I use a HashSet to check whether these frequencies are unique. I iterate through the frequencies stored in the map. If a frequency already exists in the set, it means two different numbers have the same occurrence count, so I return false. Otherwise, I add the frequency to the set. If all frequencies are unique, I return true.
