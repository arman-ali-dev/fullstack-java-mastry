# Two Sum II - Input Array Is Sorted

Let me restate the problem. We are given a sorted array of integers in ascending order and a target value. We need to find two numbers that add up to the target, and return their indices, 1-indexed.
<br>
Let me clarify a few things. Can the array be empty? Can it have duplicates or negative numbers? Can I use the same element twice? Is exactly one solution guaranteed? Can I use only constant extra space?

<br>

The brute force approach is to use two nested loops. For each element at index i, the inner loop starts from i plus 1 and checks whether any element makes the sum equal to the target. When I find such a pair, I return their indices plus 1, because the problem is 1-indexed.
Time complexity is O(n²) because of the two nested loops. Space complexity is O(1) because I use only loop variables.
But this does not use the sorted property of the array, so we can optimize.

<br>

We can optimize this using two pointers, left and right, because the array is sorted. I initialize left at index 0 and right at the last index. I run a while loop with the condition left less than right, so we continue until the pointers meet or cross.
In each iteration, I calculate the sum of numbers[left] and numbers[right]. If it is equal to the target, I return left plus 1 and right plus 1. If the sum is smaller than the target, I need a larger value, so I move left forward, because the array is sorted and the next element is larger. If the sum is greater, I need a smaller value, so I move right backward.
Time complexity is O(n) because left and right together traverse the array at most once. Space complexity is O(1) because I use only two pointers.

### Code

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum > target) {
                right--;
            } else {
                left++;
            }
        }

        return new int[]{};
    }
}
```

I create two pointers left and right. I initialize left with index 0 and right with last index. I use a while loop with condition left less than right so we continue until the pointers meet or cross each other. In each iteration I calculate the sum of numbers[left] and numbers[right] and check If it equals to target If yes i return left + 1 and right + 1 because the problem is 1-indexed else if it is smaller than sum so I move right backward because I need a smaller value else i need a larger value so I move left forward. after the loop I return an empty array.
