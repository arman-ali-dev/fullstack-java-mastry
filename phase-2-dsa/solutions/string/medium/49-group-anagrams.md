# Longest Palindromic Substring

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List> ans = new HashMap<>();

        for (String s : strs) {
            int[] count = new int[26];

            for (int i = 0; i < s.length(); i++) {
                count[s.charAt(i) - 'a']++;
            }

            StringBuilder sb = new StringBuilder("");
            for (int i : count) {
                sb.append("#");
                sb.append(i);
            }

            String key = sb.toString();

            if (!ans.containsKey(key)) {
                ans.put(key, new ArrayList<>());
            }

            ans.get(key).add(s);
        }

        return new ArrayList(ans.values());
    }
}
```

### Time Complexity

- O(n x k)

### Space Complexity

- O(n x k)

### Explanation

I use a HashMap to group strings that have the same character frequency. For each string, I create a frequency array of size 26 to count the occurrences of each lowercase letter. Then I convert this frequency array into a unique key using a StringBuilder. If two strings are anagrams, they will produce the same frequency key. I use this key in the HashMap to store all strings belonging to the same group. If the key is already present, I simply add the current string to that group; otherwise, I create a new group. Finally, I return all the groups stored in the HashMap. This approach avoids comparing every pair of strings and efficiently groups all anagrams together.
