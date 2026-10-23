# Maximum Average Subarray I

## Pattern

- Sliding Window + Fixed-Size Window

---

## Optimal Force

### Code

```java
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int n = nums.length;
        double maxAvg = Integer.MIN_VALUE;

        int left = 0;
        int right = 0;

        double window = 0;
        for (; right < k; right++) {
            window += nums[right];
        }

        while (right < n) {
            double currAvg = window / k;
            maxAvg = Math.max(currAvg, maxAvg);

            window -= nums[left];
            window += nums[right];
            left++;
            right++;
        }

        double currAvg = window / k;
        return Math.max(currAvg, maxAvg);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

I use a fixed-size sliding window of size k. First, I calculate the sum of the first k elements. Then, I calculate the average of the current window and update the maximum average. To slide the window, I subtract the element leaving from the left and add the new element entering from the right. I repeat this process for all windows and return the maximum average.
<br>
Time Complexity: O(n) — Each element is processed a constant number of times.
<br>
Space Complexity: O(1) — Only a constant number of variables are used.
