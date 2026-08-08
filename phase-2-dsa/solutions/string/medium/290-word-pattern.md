# Word Pattern

## Pattern

- HashMap / One-to-One Mapping

## Optimal Approach

### Code

```java
class Solution {
    public boolean wordPattern(String pattern, String s) {
        Map<Character, String> map = new HashMap<>();
        String[] arr = s.split(" ");

        if (arr.length != pattern.length()) {
            return false;
        }

        for (int i = 0; i < pattern.length(); i++) {
            String word = arr[i];
            char ch = pattern.charAt(i);

            if ((map.containsKey(ch) && !map.get(ch).equals(word)) ||
                !map.containsKey(ch) && map.values().contains(word)) {
                return false;
            }

            map.put(ch, word);
        }

        return true;
    }
}
```

### Time Complexity

- O(n²)

### Space Complexity

- O(n)

---

### Explanation

The idea is to check whether each character in the pattern consistently maps to exactly one word in the string. First, I split the string into words and make sure the number of words is equal to the number of characters in the pattern. Then I use a HashMap where the pattern character is the key and the corresponding word is the value.
<br>
While traversing, if the character was already mapped, I check whether it maps to the same word as before. If it maps to a different word, I return false. If the character is new, I also check whether that word is already mapped to another character using map.values().contains(word). This ensures that two different pattern characters cannot map to the same word.
<br>
If all characters follow this one-to-one mapping, I return true.
<br>
Time Complexity: For your current code, O(n²) in the worst case because map.values().contains(word) can take O(n), and this check happens for each character.
<br>
Space Complexity: O(n) because the HashMap can store mappings for the characters, and split() also creates an array of words.
