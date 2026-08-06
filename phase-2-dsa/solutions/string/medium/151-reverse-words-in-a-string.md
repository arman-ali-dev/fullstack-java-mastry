# Reverse Words in a String

## Pattern

- Brute Force: String Manipulation (split())
- Optimal: Reverse String + String Traversal

---

## Brute Force

### Code

```java
class Solution {
    public String reverseWords(String s) {
        String[] arr = s.trim().split("\\s+");
        StringBuilder reversedString = new StringBuilder();

        for (int i = arr.length - 1; i >= 0; i--) {
            reversedString.append(arr[i]);
            reversedString.append(" ");
        }

        return reversedString.toString().trim();
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
    public String reverseWords(String s) {
        int n = s.length();

        StringBuilder sb = new StringBuilder(s);
        sb.reverse();

        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < n; i++) {
            StringBuilder word = new StringBuilder();

            while (i < n && sb.charAt(i) != ' ') {
                word.append(sb.charAt(i));
                i++;
            }

            if (word.length() > 0) {
                word.reverse();
                ans.append(word);
                ans.append(" ");
            }
        }

        return ans.toString().trim();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

A straightforward approach is to split the string into words using spaces, then traverse the array from the end to the beginning and build the answer by appending each word. This approach is simple to understand, but it creates an extra array of words.
<br>
To optimize it, I first reverse the entire string. After reversing, the words appear in reverse order, but each individual word is also reversed. So, I traverse the reversed string and extract one word at a time. For every word, I reverse it again to restore its original character order and append it to the final answer. I ignore extra spaces by appending only non-empty words. This way, I get the words in reverse order without using split(). The solution processes the string linearly, giving O(n) time complexity with O(n) extra space for building the result.
