# Find Lucky Integer in an Array

## Pattern

Frequency Array

---

## Optimal Approach

### Code

```java
class Solution {
    public int findLucky(int[] arr) {
        int[] freq = new int[501];
        int largestLuckey = -1;

        for (int val : arr) {
            freq[val]++;
        }

        for (int val : arr) {
            if (freq[val] == val) {
                largestLuckey = Math.max(largestLuckey, val);
            }
        }

        return largestLuckey;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use a frequency array to count how many times each number appears. Then, I traverse the input array and check whether a number's frequency is equal to its value. If they are equal, that number is a lucky integer. I keep updating the maximum lucky integer found so far. If no lucky integer exists, the initial value remains minus one, which I return.
