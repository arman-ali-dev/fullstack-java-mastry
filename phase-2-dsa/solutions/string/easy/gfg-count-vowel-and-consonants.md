# Valid Palindrome

## Pattern

String Traversal (Linear Scan)

---

## Optimal Approach

### Code

```java
public class Solution {
    public static int[] countVowelsAndConsonants(String str) {
        int[] ans = new int[2];
        int vowelCount = 0;
        int consCount = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = Character.toLowerCase(str.charAt(i));

            if (Character.isLetter(ch)) {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowelCount++;
                } else {
                    consCount++;
                }
            }
        }

        ans[0] = vowelCount;
        ans[1] = consCount;

        return ans;
    }
}

problem - Count vowels and consonants
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

My approach is based on a single traversal of the string. I iterate through each character and first convert it to lowercase so that I don't have to handle uppercase and lowercase letters separately. Then I check whether the current character is an alphabet using Character.isLetter(). If it is a letter, I check whether it is one of the five vowels (a, e, i, o, or u). If yes, I increment the vowel count; otherwise, I increment the consonant count. Characters such as digits, spaces, and special symbols are ignored. After traversing the entire string, I return both counts. Since the string is scanned only once, the time complexity is O(n) and the space complexity is O(1).
