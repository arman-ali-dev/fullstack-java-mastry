# First Letter to Appear Twice

## Pattern

HashSet — Detect Duplicate

---

## Optimal Approach

### Code

```java
class Solution {
    public char repeatedCharacter(String s) {
        Set<Character> set = new HashSet<>();
        char ans = '\0';

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (set.contains(ch)) {
                ans = ch;
                break;
            }

            set.add(ch);
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use a HashSet to keep track of the characters that I have already seen.
<br>
I traverse the string from left to right. For each character, I first check whether it is already present in the set.
<br>
If it is present, that means this is the first character to appear twice, so I store it and break the loop.
<br>
Otherwise, I add the character to the set.
<br>
Since we process the string from left to right, the first duplicate we find is the required answer."
