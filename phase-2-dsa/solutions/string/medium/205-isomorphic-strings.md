# Isomorphic Strings

## Pattern

- HashMap / Character Mapping

## Optimal Approach

### Code

```java
class Solution {
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ss = s.charAt(i);
            char tt = t.charAt(i);


            if ((map.containsKey(ss) && map.get(ss) != tt) ||
                !map.containsKey(ss) && map.values().contains(tt)) {
                return false;
            }

            map.put(ss, tt);
        }

        return true;
    }
}
```

### Time Complexity

- O(n²)

### Space Complexity

- O(k)

---

### Explanation

The idea is to check whether characters from string s can be consistently mapped to characters in string t. I use a HashMap where the character from s is the key and its corresponding character from t is the value.
<br>
While traversing both strings, there are two cases I need to handle. First, if a character from s was already mapped, then it must map to the same character as before. If it maps to a different character, I return false. Second, if this is a new character from s, I make sure that the target character from t is not already being used by another character. This is why I check map.values().contains(tt). This prevents two different characters from s from mapping to the same character in t.
<br>
If all characters follow these rules, the strings are isomorphic, so I return true.
<br>
Time Complexity: Strictly for your current code, O(n²) in the worst case because map.values().contains(tt) can take O(n), and this check happens for each character.
<br>
Space Complexity: O(k), where k is the number of distinct characters stored in the map.
