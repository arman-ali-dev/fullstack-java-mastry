# Longest Substring Without Repeating Characters

## Pattern

Sliding Window + HashMap

---

## Optimal Approach

### Code

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int res = 0;
        int start = 0;
        int end = 0;
        for (; end < s.length(); end++) {
            char ch = s.charAt(end);
            int idx = map.getOrDefault(ch, -1);

            if (idx != -1 && idx >= start) {
                res = Math.max(res, end - start);
                start = idx + 1;
            }

            map.put(ch, end);
        }

        return Math.max(res, end - start);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

A brute-force approach is to generate every possible substring and check whether it contains duplicate characters. Although this works, it is inefficient because checking every substring takes O(n²) or more.
<br>
To optimize it, I use the Sliding Window pattern with a HashMap. The HashMap stores the last index where each character was seen. I maintain a window using two pointers, start and end. As I move end forward, I check whether the current character already exists inside the current window. If it does, I move the start pointer to one position after the previous occurrence of that character. This removes the duplicate from the current window. Then I update the character's latest index in the HashMap and continue expanding the window. Throughout the traversal, I keep track of the maximum window size. Since every character is processed only once, the overall time complexity is O(n) with O(n) extra space.
