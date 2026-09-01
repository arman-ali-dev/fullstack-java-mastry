# Peak Index in a Mountain Array

## Pattern

Binary Search on Mountain Array

---

## Optimal Approach

### Code

```java
class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int n = arr.length;
        int st = 0;
        int end = n - 1;

        while (st <= end) {
            int mid = (st + end) / 2;

            if (arr[mid + 1] > arr[mid]) {
                st = mid + 1;
            } else if (arr[mid - 1] > arr[mid]) {
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(log n)

### Space Complexity

- O(1)

---

### Explanation

The idea is to find the peak element of a mountain array using binary search. A mountain array first increases and then decreases, so I use the middle element to decide which side contains the peak.
<br>
If arr[mid + 1] > arr[mid], it means we are on the increasing side, so the peak must be on the right. Therefore, I move st to mid + 1.
<br>
If arr[mid - 1] > arr[mid], it means we are on the decreasing side, so the peak must be on the left. Therefore, I move end to mid - 1.
<br>
Otherwise, neither neighbor is greater than arr[mid], which means arr[mid] is the peak, so I return mid.
<br>
Because I eliminate roughly half of the search space in every iteration, binary search makes this much faster than checking every element.
<br>
Time Complexity: O(log n), because the search space is divided roughly in half in every iteration.
<br>
Space Complexity: O(1), because I only use a few variables.
