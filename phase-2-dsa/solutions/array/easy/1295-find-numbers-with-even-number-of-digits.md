# Find Numbers with Even Number od Digits

## Pattern

Array Traversal + StringBuilder + Digit Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public int findNumbers(int[] nums) {
        int count = 0;

        StringBuilder sb = new StringBuilder();
        for (int i : nums) {
            sb.append(i);

            if (sb.length() % 2 == 0) {
                count++;
            }

            sb.setLength(0);
        }

        return count;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(d)

### Explanation

I traverse through each number in the array and convert it into a string using a StringBuilder.
<br>
Then I check the length of the StringBuilder. If the length is even, it means the current number has an even number of digits, so I increment the count.
<br>
After processing each number, I clear the StringBuilder and reuse it for the next number.
<br>
Finally, I return the total count of numbers having an even number of digits.
<br>
Space Complexity: O(d) where d is number of digits in a number.
