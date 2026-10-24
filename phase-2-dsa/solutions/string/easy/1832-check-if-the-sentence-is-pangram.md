# Check if the Sentence Is Pangram

## Pattern

- HashSet (Approach 1) ans Frequency Array (Approach 2)

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean checkIfPangram(String sentence) {
        int n = sentence.length();
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < n; i++) {
            char ch = sentence.charAt(i);
            set.add(ch);
        }

        return set.size() == 26;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean checkIfPangram(String sentence) {
        int n = sentence.length();
        int[] freq = new int[26];

        for (int i = 0; i < n; i++) {
            char ch = sentence.charAt(i);
            freq[ch - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (freq[i] == 0) {
                return false;
            }
        }

        return true;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

A pangram is a sentence that contains every letter of the English alphabet at least once.
<br>
In the first approach, I use a frequency array of size 26 to count the occurrences of each letter. Then, I check whether any letter has a frequency of zero. If so, I return false; otherwise, I return true.
<br>
In the second approach, I use a HashSet to store unique characters. Since a set does not store duplicates, if its size is 26, the sentence contains all 26 letters, so I return true.
