# Sort Characters By Frequency

## Pattern

HashMap + Bucket Sort

---

## Optimal Force

### Code

```java
class Solution {
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        List<List<Character>> list = new ArrayList<>();
        int n = s.length();

        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }

        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        for (char ch : map.keySet()) {
            list.get(map.get(ch)).add(ch);
        }

        for (int i = n; i > 0; i--) {
            for (char ch : list.get(i)) {
                for (int j = 0; j < i; j++) {
                    sb.append(ch);
                }
            }
        }

        return sb.toString();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

---

### Explanation

First, I use a HashMap to count the frequency of each character. Then, I create a bucket list where each index represents a frequency, and each bucket stores the characters that appear that many times. I place every character into the bucket corresponding to its frequency. Finally, I traverse the buckets from the highest frequency to the lowest and append each character the required number of times to a StringBuilder. This produces the string sorted by character frequency.
<br>
Time Complexity: O(n) - Counting frequencies takes O(n), and filling the buckets and building the result take O(n) overall.
<br>
Space Complexity: O(n) - The HashMap, buckets, and StringBuilder require space proportional to the input length.
