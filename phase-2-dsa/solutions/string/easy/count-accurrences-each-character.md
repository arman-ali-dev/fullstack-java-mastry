# Count Occurrences of Each Character in String

## Pattern

- HashMap (Frequency Counting)

---

## Optimal Approach

### Code

```java
public class Solution {
    public static Map<Character, Integer> frequencyMap(String str) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int freq = map.getOrDefault(ch, 0);
            freq++;

            map.put(ch, freq);
        }

        return map;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

My approach uses a HashMap to store the frequency of each character. I traverse the string one character at a time. For every character, I check its current frequency in the HashMap using getOrDefault(). If the character is not already present, its frequency starts from 0. Then I increment the frequency by one and update it in the HashMap. By the end of the traversal, the HashMap contains every character as the key and its number of occurrences as the value. Since I process each character only once and HashMap operations take constant time on average, the overall time complexity is O(n) with O(n) extra space.
