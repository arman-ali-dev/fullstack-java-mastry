# First recurring character in string

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    String firstRepChar(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            int freq = map.getOrDefault(ch, 0);
            freq++;

            if (freq > 1) {
                return String.valueOf(ch);
            }

            map.put(ch, freq);
        }


        return "-1";
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The idea is to find the first character that appears more than once while traversing the string from left to right. I use a HashMap to store the frequency of each character. For every character, I get its current frequency and increase it by one. If the frequency becomes greater than one, that means I have seen this character before, so I immediately return that character. Because I traverse the string from left to right and return at the first repeated character, this gives me the first recurring character. If I finish the complete string without finding any repeated character, I return -1.
<br>
Time Complexity: O(n) because I traverse the string only once, and HashMap operations are O(1) on average.
<br>
Space Complexity: O(k) where k is the number of distinct characters stored in the HashMap. In the worst case, this can be O(n).
