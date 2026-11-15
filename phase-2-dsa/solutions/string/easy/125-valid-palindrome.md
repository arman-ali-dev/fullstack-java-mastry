# Valid Palindrome

Let me restate the problem. We are given a string, and we need to check whether it is a palindrome, which means it reads the same from the front and from the back. We only consider alphanumeric characters, and we ignore the case of letters.
<br>
Let me clarify a few things. Can the string be empty? Should I ignore non-alphanumeric characters? Should uppercase and lowercase letters be treated as the same? Should I return a boolean?

<br>

The brute force approach is to create a new string, called cleaned, and copy only the alphanumeric characters into it, converting each to lowercase. Then I create another string, called reversed, by reversing cleaned. Finally, I check whether cleaned and reversed are equal. If yes, it is a palindrome.
<br>
Time complexity is O(n) because cleaning, reversing and comparing are sequential traversals. Space complexity is O(n) because I create two extra strings of size n.
<br>
But this uses extra space, so we can optimize using two pointers.

<br>

We can optimize this using two pointers, left and right. I initialize left with 0 and right with n minus 1, and I run a while loop with the condition left less than right, so we continue until the pointers meet or cross.
<br>
In each iteration, if the character at left is not alphanumeric, I move left forward and skip the iteration. Else if the character at right is not alphanumeric, I move right backward and skip the iteration. Otherwise, I compare both characters after converting them to lowercase. If they are the same, I move left forward and right backward. If they are different, I return false immediately. After the loop, I return true, because no mismatch was found.
<br>
Time complexity is O(n) because left and right together traverse the string once. Space complexity is O(1) because I use only two pointers.

### Code

```java
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (!Character.isLetterOrDigit(s.charAt(left))) {
                left++;
                continue;
            }

            if (!Character.isLetterOrDigit(s.charAt(right))) {
                right--;
                continue;
            }

            if (Character.toLowerCase(s.charAt(left)) == Character.toLowerCase(s.charAt(right))) {
                left++;
                right--;
            } else {
                return false;
            }
        }

        return true;
    }
}
```

I create two pointers left and right. I initialize left with 0 and right with n minus 1, and I run a while loop with the condition left less than right, so we continue until the pointers meet or cross.
<br>
In each iteration, if the character at left is not alphanumeric, I move left forward and skip the iteration. Else if the character at right is not alphanumeric, I move right backward and skip the iteration. Otherwise, I compare both characters after converting them to lowercase. If they are the same, I move left forward and right backward. If they are different, I return false immediately. After the loop, I return true, because no mismatch was found.
