# Ransom Note

## Pattern

HashMap + Freq Count

---

## Brute Force

### Code

```java
class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < ransomNote.length(); i++) {
            char ch = ransomNote.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < magazine.length(); i++) {
            char ch = magazine.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) - 1);
        }

        for (int val : map.values()) {
            if (val > 0) {
                return false;
            }
        }

        return true;
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(n)

---

### Explanation

I use a HashMap to track character frequencies. First, I count the frequency of each character in the ransom note. Then, I traverse the magazine and decrease the frequency of each character. Finally, I check the map. If any character has a positive frequency, it means the magazine does not contain enough copies of that character, so I return false. Otherwise, I return true because the ransom note can be constructed using the magazine.
<br>
Time Complexity: O(n + m), where n is the length of ransomNote and m is the length of magazine.
<br>
Space Complexity: O(k), where k is the number of distinct characters stored in the map.
