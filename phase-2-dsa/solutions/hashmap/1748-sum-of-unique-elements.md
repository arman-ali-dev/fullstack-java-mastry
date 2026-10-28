# Sum of Unique Elements

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public int sumOfUnique(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;

        for (int val : nums) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        for (int key : map.keySet()) {
            if (map.get(key) == 1) {
                sum += key;
            }
        }

        return sum;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a HashMap to count the frequency of each element in the array. First, I traverse the array and store the frequency of every number. Then, I iterate through the map's keys and check their frequencies. If a number appears exactly once, I add it to the sum. Finally, I return the total sum of all unique elements.
