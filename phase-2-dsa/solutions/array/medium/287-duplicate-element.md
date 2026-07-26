# Find Duplicate

## Pattern

- Brute Force: Nested Loops (Array Traversal)
- Better: HashSet (Hashing)
- Optimal: Sorting

---

## Brute Force

### Code

```java
class Solution {
    public int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(n²)

### Space Complexity

- O(1)

---

## Better Approach

### Code

```java
class Solution {
    public int findDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int n : nums) {
            if (set.contains(n)) {
                return n;
            }

            set.add(n);
        }

        return -1;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

---

## Optimal Approach

### Code

```java
class Solution {
public int findDuplicate(int[] nums) {
Arrays.sort(nums);

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return nums[i];
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

### Explanation

The brute-force approach is to check every possible subarray. I use three nested loops. The first loop selects the starting index, the second loop selects the ending index, and the third loop calculates the sum of that subarray. After calculating the sum, I update the maximum sum. This approach is correct because it checks every subarray, but its time complexity is O(n³).

<br>

To make it faster, I use Kadane's Algorithm. The idea is simple. For every element, I decide whether I should start a new subarray from the current element or continue the previous subarray by adding the current element. In my code, currSum stores the current subarray sum. If the current element alone is greater than currSum + current element, I start a new subarray. Otherwise, I add the current element to currSum. After that, I compare currSum with maxSum and update maxSum if needed. Since I go through the array only once, the time complexity becomes O(n) and the space complexity is O(1).
