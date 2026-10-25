# Max Sum of a Pair With Equal Sum of Digits

## Pattern

HashMap + Digit Sum Grouping

---

## Brute Force

### Code

```java
class Solution {
    public int getDigitSum(int num) {
        int sum = 0;

        while (num > 0) {
            int r = num % 10;
            sum += r;
            num /= 10;
        }

        return sum;
    }

    public int maximumSum(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        int ans = -1;

        for (int val : nums) {
            int digitSum = getDigitSum(val);

            if (map.containsKey(digitSum)) {
                ans = Math.max(ans, map.get(digitSum) + val);

                if (val > map.get(digitSum)) {
                    map.put(digitSum, val);
                }

                continue;
            }

            map.put(digitSum, val);
        }

        return ans;
    }
}
```

### Time Complexity

- O(n x d)

### Space Complexity

- O(n)

---

### Explanation

I use a HashMap to store the maximum number encountered so far for each digit sum. For every number, I calculate its digit sum using the helper method. If the map already contains that digit sum, I can form a valid pair with the current number and the stored maximum number. I update the answer with their sum. Then, if the current number is larger than the stored number, I update the map to keep the maximum value for that digit sum. If no valid pair exists, I return minus one.
<br>
Time Complexity: O(n × d), where n is the number of elements and d is the maximum number of digits in an element. Each number's digit sum takes O(d) time, and HashMap operations take O(1) average time.
<br>
Space Complexity: O(n) — In the worst case, the map stores a separate entry for each distinct digit sum.
