# First non-repeating character in Stream

## Pattern

HashMap + Queue

---

## Optimal Approach

### Code

```java
class Solution {
    public String firstNonRepeating(String s) {
        int n = s.length();
        Map<Character, Integer> map = new HashMap<>();
        Queue<Character> q = new LinkedList<>();
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (!map.containsKey(ch)) {
                q.add(ch);
            }

            map.put(ch, map.getOrDefault(ch, 0) + 1);


            while (map.getOrDefault(q.peek(), 0) > 1) {
                q.remove();
            }

            if (q.isEmpty()) {
                ans.append("#");
            } else {
                ans.append(q.peek());
            }
        }

        return ans.toString();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a HashMap and a Queue. The HashMap stores the frequency of each character, and the Queue stores characters in the order in which they appear for the first time.
<br>
For every character, if it is appearing for the first time, I add it to the Queue. Then I update its frequency in the HashMap.
<br>
After that, I check the front of the Queue. If its frequency becomes greater than 1, I remove it because it is repeating. I keep doing this until the front character is non-repeating.
<br>
If the Queue is not empty, its front character is the first non-repeating character, so I add it to the answer. Otherwise, I add #.
<br>
Time Complexity: O(n) because every character is added to and removed from the Queue at most once.
<br>
Space Complexity: O(n) because the HashMap and Queue can store up to n characters in the worst case.
