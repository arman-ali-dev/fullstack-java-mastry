# Find The Difference

## Pattern

1. HashMap + Frequency Counting
2. XOR Bit Manipulation

---

## Brute Force

### Code

```java
class Solution {
    public char findTheDifference(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        char ans = '\0';

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) - 1);

            if (map.get(ch) == -1) {
                ans = ch;
                break;
            }
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

---

## Optimal Approach

### Code

```java
class Solution {
    public char findTheDifference(String s, String t) {
        int ans = 0;

        for (int ch : s.toCharArray()) {
            ans ^= ch;
        }

        for (int ch : t.toCharArray()) {
            ans ^= ch;
        }

        return (char) ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

In the first approach, I use XOR to find the extra character. XOR of the same character becomes zero, so all matching characters cancel out. Since t contains one extra character, the remaining value is the answer. This approach takes O(n) time and O(1) auxiliary space.
<br>
In the second approach, I use a HashMap to count character frequencies in s. Then I traverse t and decrease the frequency of each character. When a character's frequency becomes negative, it is the extra character, so I return it. This approach also takes O(n) time but uses O(k) space, where k is the number of distinct characters.
