# Jewels and Stones

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < stones.length(); i++) {
            char ch = stones.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int count = 0;

        for (int i = 0; i < jewels.length(); i++) {
            char ch = jewels.charAt(i);
            count += map.getOrDefault(ch, 0);
        }

        return count;
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(k)

### Explanation

The idea is to count how many times each stone appears, and then check which of those stones are jewels. First, I store every character from stones in a HashMap along with its frequency. Then I traverse the jewels string. For each jewel character, I use getOrDefault() to get how many times that character appeared in the stones and add that count to the final answer.
<br>
For example, if jewels contains a and b, and stones contains a three times and b twice, the answer will be five. The HashMap makes it easy to get the frequency of each jewel without repeatedly scanning the stones.
<br>
Time Complexity: O(n + m), where n is the length of stones and m is the length of jewels, because I traverse both strings once.
<br>
Space Complexity: O(k), where n is the number of distinct characters present in stones. Since the character set is limited, this can be considered O(1) auxiliary space.
