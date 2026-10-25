# Check if All Characters Have Equal Number of Occurrences

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean areOccurrencesEqual(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int occ = map.get(s.charAt(0));
        for (int val : map.values()) {
            if (val != occ) {
                return false;
            }
        }

        return true;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(k)

### Explanation

I use a HashMap to count the frequency of each character in the string. After counting, I take the frequency of the first character as the expected frequency. Then, I iterate through all the stored frequencies. If any frequency differs from the expected frequency, I return false. If all frequencies are equal, I return true."
<br>
Time Complexity: O(n) — We traverse the string once to count frequencies and then check the distinct character frequencies.
<br>
Space Complexity: O(k) — The HashMap stores each distinct character, where k is the number of distinct characters.
