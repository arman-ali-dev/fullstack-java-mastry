# Find Duplicate

## Pattern

- Better: Sorting
- Optimal: Mathematics (Sum Formula)

---

## Better Approach

### Code

```java
class Solution {
    public int missingNumber(int[] nums) {
        Arrays.sort(nums);

        int i = 0;
        for (i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) {
                return nums[i+1] + 1;
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(n log n)

### Space Complexity

- O(1)

---

## Optimal Approach

### Code

```java
class Solution {
    public int missingNumber(int[] nums) {

        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }

        int n = nums.length;
        int expectedSum = n * (n + 1) / 2;

        return expectedSum - actualSum;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

One approach is to sort the array first. After sorting, the value at every index should be equal to its index because the array contains numbers from 0 to n with one number missing. I traverse the sorted array, and if I find that nums[i] is not equal to i, then i is the missing number. If all indices match, then the missing number is n. This approach works correctly, but sorting takes O(n log n) time.
<br>
To optimize it, I use the sum formula. Since the array should contain all numbers from 0 to n, I first calculate the expected sum using the formula n × (n + 1) / 2. Then I calculate the actual sum of the given array. The difference between the expected sum and the actual sum is exactly the missing number. This approach requires only one traversal of the array, giving O(n) time complexity and O(1) extra space.
