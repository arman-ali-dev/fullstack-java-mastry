# Number of Good Pairs

## Pattern

- Frequency Array

---

## Optimal Approach

### Code

```java
class Solution {
    public int numIdenticalPairs(int[] nums) {
        int[] arr = new int[101];
        int count = 0;

        for (int val : nums) {
            arr[val]++;
        }

        for (int val : arr) {
            count += val * (val - 1) / 2;
        }

        return count;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use a frequency array of size 101 because the numbers are between 1 and 100. First, I count the occurrences of each number. Then, I traverse the frequency array. If a number appears val times, it forms val \* (val - 1) / 2 good pairs. I add the pairs for every number and return the total count.
