# Maximum Product Subarray

## Pattern

Prefix-Suffix Traversal

---

## Optimal Approach

### Code

```java
class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int prefix = 1;
        int suffix = 1;
        int ans = Integer.MIN_VALUE;

        int j = n - 1;

        for (int i = 0; i < n; i++) {

            if (prefix == 0) {
                prefix = 1;
            }

            if (suffix == 0) {
                suffix = 1;
            }

            prefix = prefix * nums[i];
            suffix = suffix * nums[j];

            ans = Math.max(ans, Math.max(prefix, suffix));
            j--;
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

The idea is to find the maximum product of any contiguous subarray. The main challenge is that negative numbers can change the sign of the product, and zero can break a subarray.
<br>
I use two running products: prefix and suffix. prefix calculates the product while traversing from left to right, and suffix calculates the product while effectively traversing from right to left.
<br>
Whenever either running product becomes 0, I reset it to 1. This allows the next part of the array after that zero to start a new product.
<br>
For every position, I calculate both the prefix and suffix products and update ans with the maximum value. Using both directions ensures that when a negative number causes the product to become negative, the maximum product can still be found by considering the product from the other side.
<br>
Time Complexity: O(n), because I traverse the array only once while maintaining both prefix and suffix products.
<br>
Space Complexity: O(1), because I only use a few variables.
