# Sort Colors

Let me restate the problem. We are given an array of integers containing only 0, 1 and 2. We need to sort it in place, without using the library’s sort method.
<br>
Let me clarify a few things. Can the array be empty? Can it have numbers other than 0, 1 and 2?
<br>

The brute force approach is to use bubble sort. I repeatedly compare adjacent elements and swap them if they are in the wrong order.

Time complexity is O(n²) because of the nested loops. Space complexity is O(1) because I use only loop variables and swap in place.

But this does not use the fact that the array has only three distinct values, so we can optimize.

<br>

We can optimize this using three pointers, low, mid and high. I initialize low and mid with index 0 and high with the last index. Everything before low is 0, everything after high is 2, the elements between low and mid are 1, and mid is the element I am checking.

I use a while loop with the condition mid less than or equal to high. If nums[mid] is 0, I swap it with nums[low] and move both low and mid forward. If it is 1, it is already in the correct region, so I only move mid forward. Otherwise, it is 2, so I swap it with nums[high] and move high backward. I do not move mid here, because the element that came from high is not checked yet. After the loop, I return nothing because the method is void.

Time complexity is O(n) I traverse the array once. Space complexity is O(1) because I use only three pointers, low, mid and high.

## Optimal Approach

### Code

```java
class Solution {
    public void sortColors(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low++] = nums[mid];
                nums[mid++] = temp;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high--] = temp;
            }
        }
    }
}
```

I create low, mid and high. I initialize low and mid with index 0 and high with the last index. I use a while loop with the condition mid less than or equal to high. If nums[mid] is 0, I swap it with nums[low] and move both low and mid forward. If it is 1, it is already in the correct region, so I only move mid forward. Otherwise, it is 2, so I swap it with nums[high] and move high backward. I do not move mid here, because the element that came from high is not checked yet. After the loop, I return nothing because the method is void.
