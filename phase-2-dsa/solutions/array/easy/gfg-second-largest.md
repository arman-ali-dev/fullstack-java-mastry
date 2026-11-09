# Second Largest

Let me restate the problem. We are given an array of integers, and we need to find the second largest number.
<br>
Let me clarify a few things. Can the array be empty? Can the array have negative numbers or duplicates? for example in [10, 10, 5] is the second largest 10 or 5? If there is no second largest what should i return?

<br>

The brute force approach is to sort the array. after sorting the second last number is the second largest number.
<br>
Time complexity is O(n log n) because of sorting.Space complexity is O(1) because I use no data structures or auxilery space. But this does not satisfy the duplicates, so we need to optimize.

<br>

We can optimize this by traversing the array once and keeping track of two variables, largest and secondLargest. I initialize largest with the first element and secondLargest with Integer.MIN_VALUE.
<br>
For each next element, if it is greater than largest, I move the old largest into secondLargest and update largest with the current element. Else if it is smaller than largest but greater than secondLargest, I update secondLargest. If it is equal to largest, I ignore it, so duplicates are handled.
<br>
After the loop, if secondLargest is still Integer.MIN_VALUE, there is no second largest, so I return -1 or throw an exception, depending on what the interviewer wants. Otherwise I return secondLargest.
<br>
Time complexity is O(n) because I traverse the array once. Space complexity is O(1) because I use only two variables.

### Code

```java
class Solution {
    public int getSecondLargest(int[] arr) {

        int largest = arr[0];
        int secondLargest = Integer.MIN_VALUE;

        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num < largest && num > secondLargest) {
                secondLargest = num;
            }
        }

        return secondLargest == Integer.MIN_VALUE ? -1 : secondLargest;
    }
}
```

I initialize largest with the first value of the array and secondLargest with Integer.MIN_VALUE. I traverse the array and check for each element if it is greater than largest I move largest into secondLargest and update largest with current number. else if it is smaller than largest and greater than secondLargest I update secondLargest. After the loop if secondLargest is still Integer.MIN_VALUE means there is no second largest number so I return -1 otherwise secondLargest.
