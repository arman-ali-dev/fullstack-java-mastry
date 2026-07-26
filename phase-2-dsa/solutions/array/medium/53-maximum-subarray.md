# Maximum Subarray

## Pattern

Dynamic Programming (Kadane's Algorithm)

---

## Brute Force

### Code

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += nums[k];
                }

                maxSum = Math.max(sum, maxSum);
            }
        }

        return maxSum;
    }
}
```

### Time Complexity

- O(n³)

### Space Complexity

- O(1)

---

## Optimal Approach

### Code

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currSum = 0;

        for (int n : nums) {
            if (n > currSum + n) {
                currSum = n;
            } else {
                currSum += n;
            }

            maxSum = Math.max(currSum, maxSum);
        }


        return maxSum;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The brute-force approach is to check every possible subarray. I use three nested loops. The first loop selects the starting index, the second loop selects the ending index, and the third loop calculates the sum of that subarray. After calculating the sum, I update the maximum sum. This approach is correct because it checks every subarray, but its time complexity is O(n³).

<br>

To make it faster, I use Kadane's Algorithm. The idea is simple. For every element, I decide whether I should start a new subarray from the current element or continue the previous subarray by adding the current element. In my code, currSum stores the current subarray sum. If the current element alone is greater than currSum + current element, I start a new subarray. Otherwise, I add the current element to currSum. After that, I compare currSum with maxSum and update maxSum if needed. Since I go through the array only once, the time complexity becomes O(n) and the space complexity is O(1).
