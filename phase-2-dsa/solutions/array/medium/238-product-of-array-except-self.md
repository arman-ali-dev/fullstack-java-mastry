# Product of Array except self

## Pattern

Prefix Product + Suffix Product

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            prefix[i] = 1;
            suffix[i] = 1;
            ans[i] = 1;
        }

        // prefix
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }

        // suffix
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }

        // ans
        for (int i = 0; i < n; i++) {
            ans[i] = prefix[i] * suffix[i];
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The idea is to calculate the product of all elements before and after each index. Since the current element should be excluded, I maintain two arrays: prefix and suffix.
<br>
prefix[i] stores the product of all elements to the left of index i, while suffix[i] stores the product of all elements to the right of index i.
<br>
First, I build the prefix array from left to right. For example, prefix[i] is calculated using the previous prefix value multiplied by nums[i - 1].
<br>
Then I build the suffix array from right to left. Similarly, suffix[i] contains the product of all elements after i.
<br>
Finally, for every index, I multiply prefix[i] and suffix[i] to get the product of every element except nums[i].
<br>
This approach also handles zeroes correctly and doesn't require division.
<br>
Time Complexity: O(n), because I make three linear traversals of the array.
<br>
Space Complexity: O(n), because I use prefix, suffix, and ans arrays, each of size n.
