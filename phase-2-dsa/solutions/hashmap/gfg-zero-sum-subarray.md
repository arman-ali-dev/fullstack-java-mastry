# Zero Sum Subarray

## Pattern

Prefix Sum + HashMap

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean subArrayExists(int arr[]) {
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == 0) {
                return true;
            }

            sum += arr[i];

            if (sum == 0) {
                return true;
            } else if (map.containsKey(sum)) {
                return true;
            }

            map.put(sum, i);
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

The brute-force approach would be to generate every possible subarray and calculate its sum. That would take O(n²) or O(n³) depending on how we calculate the sum.
<br>
To optimize it, I use the Prefix Sum pattern with a HashMap. I keep a running sum while traversing the array. If the running sum itself becomes zero, it means the subarray from the beginning has a zero sum. Otherwise, I store every prefix sum in the HashMap. The important observation is that if I see the same prefix sum again, the elements between those two positions must have a sum of zero. For example, if the prefix sum is 5 at one index and becomes 5 again later, the elements added between those two positions contributed exactly zero. So I can immediately return true. If no prefix sum repeats and the total sum never becomes zero, then no zero-sum subarray exists.
<br>
Time Complexity: O(n), because I traverse the array once and HashMap operations are O(1) on average.
<br>
Space Complexity: O(n), because in the worst case I may store all prefix sums in the HashMap.
