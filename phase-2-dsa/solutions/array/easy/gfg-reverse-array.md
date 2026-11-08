# Reverse Array

Let me restate the problem. We are given an array of integers, and we need to reverse it in place, which means without using extra space.
<br>
Let me clarify a few things. Can the array be empty? Can it have only one element? Should I modify the original array instead of returning a new one?

<br>

The brute force approach is to traverse the array backward. I create a new array of the same size and copy the elements into it in reverse order. After the loop, I return the new array.
<br>
Time complexity is O(n) because I traverse the array once. Space complexity is O(n) because I copy every element into a new array. But this does not satisfy the in-place requirement, so we need to optimize.

<br>

We can optimize this using two pointers. I keep a left pointer at index 0 and a right pointer at index n minus 1. I use a while loop with the condition left less than right, which means we continue until the pointers meet or cross. In each iteration, I swap the elements at left and right, then I move left forward and right backward.
Time complexity is O(n) because the loop runs n by 2 times, which is still O(n). Space complexity is O(1) because I use only one temporary variable for swapping.

### Code

```java
class Solution {
    public void reverseArray(int arr[]) {
        int n = arr.length;
        int left = 0, right = n - 1;

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }
}
```

I initialize left at index 0 and right at the last index. Inside the while loop, I swap the elements at left and right using a temp variable. I need temp because if I directly assign arr[right] to arr[left], the original value at left is overwritten and lost, and both positions end up with the same value. After the swap, I move left forward and right backward. The method is void because the array is modified in place, so there is no need to return anything.
