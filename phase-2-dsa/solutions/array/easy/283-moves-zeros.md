# Moves Zeros

Let me restate the problem. We are given an array of integers, and we need to move all zeros to the end in place, while keeping the relative order of the non-zero elements.

<br>

Let me clarify a few things. Can the array be empty? Should I maintain the order of non-zero elements?

<br>

The brute force approach is to create a new array and copy all non-zero elements into it, in the same order. Then I fill the remaining positions with zeros, and copy the new array back into the original.
Time complexity is O(n) because all these steps are sequential, and each is a single traversal. Space complexity is O(n) because I create a new array of the same size.
But this does not satisfy the in-place requirement, so we need to optimize.

<br>

We can optimize this using two pointers, i and j. The pointer j marks the position where the next non-zero element should be placed. I traverse the array with i, and whenever arr[i] is non-zero, I swap it with arr[j] and move j forward.
Because i moves left to right, the order of non-zero elements stays the same, and the zeros get pushed to the end by the swaps. After the loop, I return nothing because the method is void.
Time complexity is O(n) because I traverse the array once. Space complexity is O(1) because I use only two pointers, i and j.

### Code

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j++;
            }
        }
    }
}
```

I create j variable and initialize with 0. I loop through the array from 0 to n - 1 and check for each element if it is non zero element I swap it with arr[j] and move j forward. after the loop I return nothing because method's type is void.
