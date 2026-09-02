# First Unique Character in a String

## Pattern

HashMap + Queue

---

## Optimal Approach

### Code

```java
class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (!map.containsKey(ch)) {
                q.offer(i);
            }

            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int n = q.size();

        for (int i = 0; i < n; i++) {
            if (map.getOrDefault(s.charAt(q.peek()), 1) > 1) {
                q.poll();
            }
        }

        return q.isEmpty() ? -1 : q.peek();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The idea is to find the first character that occurs only once. I use a HashMap to store the frequency of every character and a Queue to maintain the indices of characters that appeared for the first time.
<br>
While traversing the string, whenever a character is seen for the first time, I add its index to the queue. Then I update its frequency in the HashMap.
<br>
After building the frequency map, I check the indices stored in the queue from left to right. If the character at the front of the queue has a frequency greater than 1, I remove its index because it is not unique. I continue this until the first unique character is found.
<br>
Since the queue stores indices in the same order in which the characters appeared, the first remaining index is the first non-repeating character.
<br>
If the queue becomes empty, it means there is no unique character, so I return -1.
<br>
Time Complexity: O(n), because I traverse the string once and each index is added to and removed from the queue at most once.
<br>
Space Complexity: O(n), because the HashMap and Queue can store up to n characters/indices.
