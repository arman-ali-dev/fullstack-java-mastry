# Find Players With Zero or One Losses

## Pattern

HashMap + Frequency Counting + Sorting

---

## Optimal Approach

### Code

```java
class Solution {
    public List<List<Integer>> findWinners(int[][] matches) {
       List<Integer> zeroLoses = new ArrayList<>();
       List<Integer> oneLoses = new ArrayList<>();

       Map<Integer, Integer> map = new HashMap<>();

       for (int[] match : matches) {
            int winner = match[0];
            int loser = match[1];

            map.put(winner, map.getOrDefault(winner, 0) + 0);
            map.put(loser, map.getOrDefault(loser, 0) + 1);
       }

       for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getValue() == 0) {
                zeroLoses.add(e.getKey());
            } else if (e.getValue() == 1) {
                oneLoses.add(e.getKey());
            }
       }

       Collections.sort(zeroLoses);
       Collections.sort(oneLoses);

       return Arrays.asList(zeroLoses, oneLoses);
    }
}
```

### Time Complexity

- O(n + p log n)

### Space Complexity

- O(p)

### Explanation

I use a HashMap to track the number of losses for each player. For every match, I ensure the winner is present in the map with zero additional losses, and I increment the loser's loss count by one. After processing all matches, I traverse the map and put players with zero losses into one list and players with exactly one loss into another list. Finally, I sort both lists in ascending order and return them as the result.
<br>
Time Complexity: O(n + p log p), where n is the number of matches and p is the number of distinct players. Processing matches takes O(n) average time, and sorting the lists takes at most O(p log p).
<br>
Space Complexity: O(p) — The HashMap and result lists store information about the distinct players
