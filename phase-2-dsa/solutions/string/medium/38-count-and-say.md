# Count and Say

## Pattern

- Recursion + String Traversal / Run-Length Encoding

## Optimal Approach

### Code

```java
class Solution {
    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        String res = countAndSay(n - 1);
        char ch = res.charAt(0);
        int count = 1;

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < res.length(); i++) {
            if (ch == res.charAt(i)) {
                count++;
            } else {
                sb.append(count);
                sb.append(ch);

                count = 1;
                ch = res.charAt(i);
            }
        }

        sb.append(count);
        sb.append(ch);

        return sb.toString();
    }
}
```

### Time Complexity

- O(n × L)

### Space Complexity

- O(L)

---

### Explanation

The idea of Count and Say is that each term describes the previous term. So I use recursion to first generate the result for n - 1. The base case is n = 1, where the result is simply 1.
<br>
Once I have the previous string, I traverse it from left to right and count consecutive occurrences of the same character. Whenever the character changes, I append the count followed by that character to the result. After the loop, I also append the last group because there is no character change after it. For example, 21 is described as one 2 and one 1, so the next term becomes 1211.
<br>
The important part is that I'm not counting the total frequency of a character; I'm counting consecutive occurrences. I repeat this process recursively until I reach the required n.
<br>
Time Complexity: roughly O(n × L), where L is the length of the generated sequence, because each level processes the previous string.
<br>
Space Complexity: O(L) for the generated strings and StringBuilder used during the recursion.
