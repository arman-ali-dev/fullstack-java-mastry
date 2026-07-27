# First Unique Character in String

## Pattern

- Brute Force: Nested Loops
- HashMap (Frequency Counting)

---

## Brute Force

### Code

```java
class Solution {
    public int firstUniqChar(String s) {
        for (int i = 0; i < s.length(); i++) {
            boolean flag = false;

            for (int j = 0; j < s.length(); j++) {
                if (i != j && s.charAt(i) == s.charAt(j)) {
                    flag = true;
                    break;
                }
            }


            if (!flag) {
                return i;
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(n²)

### Space Complexity

- O(1)

---

## Optimal Approach

### Code

```java
class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int freq = map.getOrDefault(ch, 0);
            freq++;
            map.put(ch, freq);
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.get(ch) == 1) {
                return i;
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The brute-force approach is to check every character one by one. For each character, I compare it with every other character in the string using a second loop. If I find the same character at another position, I mark it as repeated. If I finish the inner loop without finding any duplicate, then that character is the first unique character, so I return its index. This approach works correctly, but because of the nested loops, its time complexity is O(n²).

<br>

To optimize it, I use a HashMap to store the frequency of every character. In the first traversal, I count how many times each character appears in the string. Then I traverse the string again in its original order. The first character whose frequency is 1 is the first unique character, so I return its index. Using two linear traversals and constant-time HashMap operations, this approach achieves O(n) time complexity with O(n) extra space.
