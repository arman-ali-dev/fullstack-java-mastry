# Longest Palindromic Substring

## Pattern

Expand Around Center (Two Pointers)

---

## Optimal Approach

### Code

```java
class Solution {
    public String longestPalindrome(String s) {
        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            int left = i;
            int right = i;

            while (left >= 0 && s.charAt(left) == s.charAt(i)) {
                left--;
            }

            while (right < s.length() && s.charAt(right) == s.charAt(i)) {
                right++;
            }

            while (right < s.length() && left >= 0) {
                if (s.charAt(left) != s.charAt(right)) {
                    break;
                }

                left--;
                right++;
            }

            left++;

            if (right - left > end - start) {
                start = left;
                end = right;
            }
        }

        return s.substring(start, end);
    }
}

```

### Time Complexity

- O(n²)

### Space Complexity

- O(1)

### Explanation

A brute-force approach is to generate every possible substring and check whether each substring is a palindrome. This works correctly, but generating all substrings and checking each one makes the solution inefficient with O(n³) time complexity.
<br>
To optimize it, I use the Expand Around Center technique. The main observation is that every palindrome has a center. So, I treat every character as the center of a possible palindrome. In my code, I first expand to include all consecutive duplicate characters because they form the middle of an even-length palindrome. Then I keep expanding on both sides as long as the characters are equal. When the characters no longer match, I stop expanding because the palindrome has ended. I compare the length of the current palindrome with the longest one found so far and update the answer if it is longer. After checking every possible center, I return the longest palindromic substring. This approach avoids checking every substring separately and runs in O(n²) time with O(1) extra space.
