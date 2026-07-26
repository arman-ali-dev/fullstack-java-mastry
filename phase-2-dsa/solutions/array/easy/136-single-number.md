# Moves Zeros

## Pattern

Bit Manipulation (XOR)

---

## Optimal Approach

### Code

```java
class Solution {
    public int singleNumber(int[] nums) {
        int ans = 0;

        for (int num : nums) {
            ans ^= num;
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

To optimize it, I use the XOR operation. The key property of XOR is that a number XOR itself becomes 0, and any number XOR 0 remains the same. Since every element except one appears exactly twice, all duplicate elements cancel each other when I XOR all the elements in the array. In the end, only the element that appears once remains. This allows me to solve the problem in a single traversal with O(n) time complexity and O(1) extra space.
