# Check Sorted Array

Let me restate the problem. We are given an array of integers, and we need to check whether it is sorted in ascending order.
<br>
Let me clarify a few things. Can the array be empty? Can it have duplicates? Does sorted mean ascending order?

<br>

The brute force approach is to create a copy of the array and sort it. Then I compare each element of the sorted copy with the original array. If all elements match, the array is sorted. Otherwise, it is not.
<br>
Time complexity is O(n log n) because of sorting. The comparison loop is O(n), but sorting dominates. Space complexity is O(n) because I create a copy of the array.

<br>

We can optimize this by traversing the array once and comparing each element with the next one. If the current element is greater than the next one, the array is not sorted, so I return false immediately. If the loop finishes without finding such a pair, the array is sorted, so I return true.
Time complexity is O(n) in the worst case, because I traverse the array once. In the best case it is O(1), because I return at the first violation. Space complexity is O(1) because I use only a loop variable.

### Code

```java
class Solution {
    public boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
```

I use a for loop with i starting from 0, and the condition is i less than arr.length minus 1. I use length minus 1 because I access arr[i + 1] inside the loop, so at the last index it would go out of bounds.

Inside the loop, I check if arr[i] is greater than arr[i + 1]. If yes, the array is not sorted, so I return false immediately.

After the loop, I return true, because no violation was found. For an empty or single element array, the loop does not run at all, so it returns true, which is correct.
