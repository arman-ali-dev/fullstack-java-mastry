# Sqrt(x)

## Pattern

Binary Search

---

## Optimal Approach

### Code

```java
class Solution {
    public int mySqrt(int x) {

        if (x < 2) {
            return x;
        }

        int st = 1;
        int end = x;

        while (st <= end) {
            int mid = st + (end - st) / 2;

            long result = (long) mid * (long) mid;

            if (result == x) {
                return mid;
            } else if (result > x) {
                end = mid - 1;
            } else {
                st = mid + 1;
            }
        }

        return end;
    }
}
```

### Time Complexity

- O(log n)

### Space Complexity

- O(1)

### Explanation

I use binary search to find the integer square root of x.
<br>
I consider numbers from 1 to x as possible answers. For every mid, I calculate mid _ mid and compare it with x.
<br>
If mid _ mid is equal to x, then mid is the exact square root.
<br>
If it is greater than x, mid is too large, so I search on the left side.
<br>
If it is smaller than x, I search on the right side because there may be a larger value whose square is still less than or equal to x.
<br>
When the loop ends, end represents the largest integer whose square is less than or equal to x, so I return end.
<br>
I use long for result to avoid integer overflow while calculating mid \* mid.
<br>
Time Complexity: O(log x)
<br>
Space Complexity: O(1)
