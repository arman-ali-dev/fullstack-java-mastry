# Maximum Number of Balloons

## Pattern

HashMap + Frequency Counting + Minimum Frequency

---

## Optimal Approach

### Code

```java
class Solution {
    public int maxNumberOfBalloons(String text) {
        Map<Character, Integer> map = new HashMap<>();

        for (char ch : text.toCharArray()) {
            if (ch == 'b' || ch == 'a' || ch == 'l' || ch == 'o' || ch == 'n') {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
        }

        int x = Math.min(map.getOrDefault('b', 0), Math.min( map.getOrDefault('a', 0), map.getOrDefault('n', 0)));
        int xx = Math.min(map.getOrDefault('l', 0), map.getOrDefault('o', 0));

        return Math.min(x, xx / 2);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use a HashMap to count the occurrences of the characters required to form the word balloon. Since only the characters b, a, l, o, and n are needed, I ignore all other characters. To form one balloon, we need one b, one a, two ls, two os, and one n. I find the minimum frequency among b, a, and n, then compare it with half the frequency of l and o. The smaller value determines the maximum number of balloons that can be formed."
<br>
Time Complexity: O(n) - We traverse the text once to count the required characters.
<br>
Space Complexity: O(1) - The map stores at most five distinct characters.
