# Get Equal Substrings Within Budget

## Pattern

Sliding Window + Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public int equalSubstring(String s, String t, int maxCost) {
        int n = s.length();
        int right = 0;
        int left = 0;
        int ans = Integer.MIN_VALUE;

        int window = 0;
        for (; right < n; right++) {
            window += Math.abs(s.charAt(right) - t.charAt(right));

            while (window > maxCost) {
                window -= Math.abs(s.charAt(left) - t.charAt(left));
                left++;
            }

            ans = Math.max(ans, right - left + 1);
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

I use two pointers to maintain a sliding window and a variable to track the total cost of converting the substring. For each position, I calculate the absolute difference between the corresponding characters of both strings and add it to the window cost. If the total cost exceeds maxCost, I shrink the window from the left until the cost becomes valid again. For every valid window, I update the maximum length. Finally, I return the longest substring length that can be converted within the given budget.
<br>
Time Complexity: O(n) — Each character is added to and removed from the window at most once.
<br>
Space Complexity: O(1) — Only a constant number of variables are used.
