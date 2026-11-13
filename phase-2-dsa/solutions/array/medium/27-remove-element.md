# Remove Element

Let me restate the problem. We are given a sorted array of integers. We need to remove duplicates in place, so that each unique element appears only once, and keep the relative order the same. Then we return k, the count of unique elements.
<br>

Let me clarify a few things. Can the array be empty? Can it have negative numbers? Do the elements after the first k positions matter?

<br>

The brute force approach is to add all elements to a LinkedHashSet, which removes duplicates and keeps the insertion order. Then I copy the elements of the set back into the original array. The size of the set is k, and I return it.

Time complexity is O(n) because adding to the set and copying back are two sequential traversals. Space complexity is O(n) because I use a set.

But this uses extra space and does not use the sorted property of the array, so we can optimize.

<br>

We can optimize this using two pointers, i and j. The pointer j points to the last unique element found so far, and i traverses the array starting from index 1. If nums[i] is not equal to nums[j], I move j forward and copy nums[i] to nums[j]. After the loop, I return j plus 1, because j is an index and k is the count of unique elements.

Time complexity is O(n) because I traverse the array once. Space complexity is O(1) because I use only two pointers.”

### Code

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int j = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[j]) {
                j++;
                nums[j] = nums[i];
            }
        }
        return j + 1;
    }
}
```

I create j variable and initialize with 0. I traverse the array by for loop and check for each element if it is different from nums[j]. if yes I move j forward and copy nums[i] with nums[j]. after the loop i return j + 1.
