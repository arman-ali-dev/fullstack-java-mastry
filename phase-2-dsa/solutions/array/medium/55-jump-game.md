# Jump Game

## Pattern

Greedy + Maximum Reach

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean canJump(int[] nums) {
        int n = nums.length;

        int maxJump = nums[0];

        if (n == 1) {
            return true;
        }

        for (int i = 0; i < n - 1; i++) {
            maxJump = Math.max(i + nums[i], maxJump);

            if (i >= maxJump) {
                return false;
            }

            if (maxJump >= n - 1) {
                return true;
            }
        }

        return false;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

The idea is to keep track of the farthest index I can reach from the positions I have visited so far. I store this in maxJump.
<br>
For every index i, I calculate how far I can reach from that index using i + nums[i]. I update maxJump with the maximum of the current reach and the previous maximum reach.
<br>
If at any point i >= maxJump, it means I have reached an index that I cannot actually reach, so I return false.
<br>
If maxJump becomes greater than or equal to the last index, it means I can reach the end, so I return true.
<br>
The important greedy idea is that I don't need to decide the exact jump at every index. I only need to remember the farthest position reachable so far.
<br>
Time Complexity: O(n), because I traverse the array only once.
<br>
Space Complexity: O(1), because I only use a few variables.
