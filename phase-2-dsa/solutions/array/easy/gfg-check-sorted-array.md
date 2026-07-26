# Two Sum

## Pattern

Array Traversal (Linear Scan)

---

## Optimal Approach

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

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

My approach is based on a single traversal of the array. Since a sorted array means every element should be less than or equal to its next element, I compare each element with its adjacent element while traversing the array. If I find any element that is greater than the next one, I can immediately conclude that the array is not sorted and return false. If I complete the entire traversal without finding any such violation, it means the array is sorted, so I return true. This approach is efficient because it checks each adjacent pair only once, resulting in O(n) time complexity and O(1) extra space.
