# Square of a Sorted Array

## Pattern

Two Pointers

---

## Brute Force

### Code

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;

        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            ans[i] = nums[i] * nums[i];
        }

        Arrays.sort(ans);

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n log n)

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int start = 0;
        int end = n - 1;

        int[] ans = new int[n];
        int w = n - 1;

        while (start <= end) {

            if (nums[start] * nums[start] > nums[end] * nums[end]) {
                ans[w] = nums[start] * nums[start];
                start++;
            } else {
                ans[w] = nums[end] * nums[end];
                end--;
            }

            w--;
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

A straightforward approach is to first calculate the square of every element and then sort the array. Calculating the squares takes O(n) time, and sorting takes O(n log n) time, so the overall complexity becomes O(n log n).
<br>
To optimize it, I use the Two Pointers pattern. The important observation is that the input array is already sorted, but it may contain negative numbers. The largest square will always come from either the leftmost negative number or the rightmost positive number because both can have the largest absolute value. So I keep one pointer at the beginning and another at the end of the array. I compare the squares of both values, place the larger square at the end of the answer array, and move the corresponding pointer. I continue this process until the pointers meet. Since each element is processed exactly once, the solution runs in O(n) time with O(n) extra space for the output array.
