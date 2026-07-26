# Reverse Array

## Pattern

Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public void reverseArray(int arr[]) {
        int n = arr.length;
        int i = 0, j = n - 1;

        while (i < j) {
            int temp = arr[i];
            arr[i++] = arr[j];
            arr[j--] = temp;
        }
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

My approach uses the Two Pointers pattern. Since I need to reverse the array in-place, I initialize one pointer at the beginning of the array and another at the end. In each iteration, I swap the elements at these two positions and then move the left pointer one step forward and the right pointer one step backward. I continue this process while the left pointer is less than the right pointer. Once both pointers meet or cross each other, the entire array has been reversed, so there's no need for further swaps. This approach is efficient because every element is swapped at most once, giving a time complexity of O(n) while using O(1) extra space.
