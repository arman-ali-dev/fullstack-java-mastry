# Kids With the Greatest Number of Candies

## Pattern

- Array Traversal + Maximum Element

---

## Optimal Approach

### Code

```java
class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> ans = new ArrayList<>();
        int max = 0;

        for (int c : candies) {
            max = Math.max(max, c);
        }

        for (int c : candies) {
            if (c + extraCandies >= max) {
                ans.add(true);
            } else {
                ans.add(false);
            }
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

First, I traverse the array to find the maximum number of candies currently held by any kid.
<br>
Then I traverse the array again. For each kid, I add the extra candies to their current candies and check whether the result is greater than or equal to the maximum.
<br>
If it is, I add true to the answer; otherwise, I add false.
<br>
This tells us whether each kid can have the greatest number of candies after receiving all the extra candies.
<br>
Time Complexity: O(n) Two separate traversals, but O(n) + O(n) = O(n).
<br>
Space Complexity: O(n) For the result list.
