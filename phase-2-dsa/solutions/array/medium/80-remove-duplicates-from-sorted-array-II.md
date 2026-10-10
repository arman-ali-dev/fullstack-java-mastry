# Remove Duplicates from Sorted Array II

## Pattern

Two Pointers + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int lastVis = nums[0];
        int freq = 0;
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == lastVis) {
                freq++;
            } else {
                lastVis = nums[i];
                freq = 1;
            }

            if (freq <= 2) {
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

Since the array is sorted, duplicate elements appear consecutively.
<br>
I maintain lastVis to track the previous distinct value and freq to count its occurrences. Whenever the current value changes, I update lastVis and reset the frequency to one.
<br>
If the frequency is at most two, I copy the current element to index k and increment k. This ensures that each element appears at most twice in the modified array.
<br>
Finally, I return k, which represents the length of the array after removing the extra duplicates.
