# Minimum Consecutive Cards to Pick Up

## Pattern

HashMap + Last Seen Index

---

## Brute Force

### Code

```java
class Solution {
    public int minimumCardPickup(int[] cards) {
        int n = cards.length;
        Map<Integer, Integer> map = new HashMap<>();

        int right = 0;
        int ans = Integer.MAX_VALUE;

        int window = 0;
        for (; right < n; right++) {
            int curr = cards[right];
            int idx = map.getOrDefault(curr, -1);

            if (idx >= 0) {
                ans = Math.min(ans, right - idx + 1);
            }

            map.put(curr, right);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

---

### Explanation

I use a HashMap to store the most recent index of each card value. While traversing the array, I check whether the current card has appeared before. If it has, I calculate the length of the subarray between its previous occurrence and the current index, including both cards. I update the minimum length and then store the current index in the map. Finally, if no duplicate card was found, I return minus one; otherwise, I return the minimum length.
<br>
Time Complexity: O(n) — Each card is processed once, and HashMap operations take O(1) average time.
<br>
Space Complexity: O(n) — In the worst case, the HashMap stores all n distinct card values.
