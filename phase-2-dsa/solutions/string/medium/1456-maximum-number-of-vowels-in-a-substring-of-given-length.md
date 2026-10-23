# Maximum Number of Vowels in a Substring of Given Length

## Pattern

Sliding Window + Fixed-Size Window

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    public int maxVowels(String s, int k) {
        int n = s.length();
        int left = 0;
        int maxVowels = 0;
        int vowels = 0;
        for (int right = 0; right < n; right++) {
            char ch = s.charAt(right);

            if (k == right - left) {
                maxVowels = Math.max(maxVowels, vowels);

                if (isVowel(s.charAt(left))) {
                    vowels--;
                }
                left++;
            }

            if (isVowel(ch)) {
                vowels++;
            }
        }

        return Math.max(maxVowels, vowels);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use a sliding window of size k to find the maximum number of vowels in any substring. I maintain a count of vowels in the current window. When the window reaches size k, I update the maximum vowel count. Then, I remove the contribution of the leftmost character if it is a vowel and move the left pointer forward. After processing all characters, I return the maximum vowel count.
<br>
Time Complexity: O(n) — Each character is processed at most a constant number of times.
<br>
Space Complexity: O(1) — Only a few variables are used.
