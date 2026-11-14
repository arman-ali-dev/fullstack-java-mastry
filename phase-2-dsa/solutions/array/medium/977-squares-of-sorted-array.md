# Square of a Sorted Array

Let me restate the problem. We are given an array of integers sorted in non-decreasing order. We need to return a new array containing the square of each number, also sorted in non-decreasing order.
<br>
Let me clarify a few things. Can the array have negative numbers or duplicates? Can it be empty? Should I return a new array, or modify in place?
<br>

The brute force approach is to traverse the array, calculate the square of each number, and copy it with arr[i]. After the loop, I sort the array in ascending order and return it.

<br>

Time complexity is O(n log n) because of sorting. The loop runs n times, but sorting dominates. Space complexity is O(1) because I use only a loop variable.

<br>

We can optimize this using three pointers, left, right and k. I initialize left with index 0, right with the last index, and k with the last index of the result array, because k is the position where the next largest square should be placed. I also create a new array, result, of the same size.

I run a while loop with the condition left less than or equal to right. In each iteration, I calculate leftS and rightS. If leftS is greater than or equal to rightS, I copy leftS to result[k] and move left forward. Otherwise, I copy rightS to result[k] and move right backward. In both cases, I move k backward. After the loop, I return result.

Time complexity is O(n) because left and right together traverse the array once. Space complexity is O(n) because I create a new result array of size n.

### Code

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        int k = n - 1;

        int[] result = new int[n];

        while (left <= right) {
            int leftS = nums[left] * nums[left];
            int rightS = nums[right] * nums[right];

            if (leftS >= rightS) {
                result[k] = leftS;
                left++;
            } else {
                result[k] = rightS;
                right--;
            }

            k--;
        }

        return result;
    }
}
```

I create three pointers left, right and k. I initialize left with index 0 and right with last index of the array and k with last index of the result array. because K is the position where next largest square should be placed. I run a while loop with the condition left less than or equal to right. In each iteration, I calculate leftS and rightS. If leftS is greater than or equal to rightS, I copy leftS to result[k] and move left forward. Otherwise, I copy rightS to result[k] and move right backward. In both cases, I move k backward. After the loop, I return result.
