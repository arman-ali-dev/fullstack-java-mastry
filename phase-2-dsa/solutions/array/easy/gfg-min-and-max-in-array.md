# Min and Max in Array

Let me restate the problem. We are given an array of integers, and we need to find the maximum and minimum element in it.

<br>

Let me clarify a few things. Can the array have negative numbers or duplicates? Can it be empty?

<br>

The brute force approach is to sort the array. After sorting, the first element is the minimum and the last element is the maximum. Time complexity is O(n log n) because sorting algorithms like merge sort take n log n time.

<br>

The optimal approach is we do not need to sort. I can traverse the array only once. I keep track of two variables, min and max, and initialize both with the first element. For each next element, if it is greater than max, I update max. If it is smaller than min, I update min. After the loop, I return both values. Time complexity is O(n) because I traverse the array only once. Space complexity is O(1) because I use only two variables.

### Code

```java
class Solution {
    public ArrayList<Integer> getMinMax(int[] arr) {
        int min = arr[0];
        int max = arr[0];
        ArrayList<Integer> ans = new ArrayList<>();

        for (int num : arr) {
            if (num > max) {
                max = num;
            }

            if (num < min) {
                min = num;
            }
        }


        ans.add(min);
        ans.add(max);

        return ans;
    }
}

```

For the code, I initialize min and max with the first element. I loop from index 1 to the end, because index 0 is already stored in min and max. Inside the loop, I compare and update. Finally, I return both values.
