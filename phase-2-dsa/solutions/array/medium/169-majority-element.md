# Majority Element

## Pattern

Boyer-Moore Voting Algorithm

---

## Optimal Approach

### Code

```java
class Solution {
    public int majorityElement(int[] nums) {
        int majorElem = nums[0];
        int freq = 0;

        for (int n : nums) {
            if (majorElem == n) {
                freq++;
            } else {
                freq--;
            }

            if (freq == 0) {
                majorElem = n;
                freq++;
            }
        }

        return majorElem;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

The idea is to find the element that appears more than n / 2 times. Since the majority element occurs more than half of the array, it will always survive the cancellation process.
<br>
I maintain two variables: majorElem stores the current candidate for the majority element, and freq represents its current count.
<br>
For every element, if it is equal to majorElem, I increase freq. Otherwise, I decrease freq, because one occurrence of the candidate can be cancelled with one different element.
<br>
Whenever freq becomes 0, it means the current candidate has been completely cancelled by different elements, so I choose the current element as the new candidate and set its frequency to 1.
<br>
At the end, majorElem is the majority element because the actual majority element has more occurrences than all other elements combined, so it cannot be completely cancelled.
<br>
Time Complexity: O(n), because I traverse the array only once.
<br>
Space Complexity: O(1), because I only use two variables.
