# Merge Sorted Array

Let me restate the problem. We are given two sorted arrays of integers, nums1 and nums2, and two integers m and n. nums1 has a length of m plus n. Its first m elements are valid, and the last n positions are empty, filled with zeros. We need to merge nums2 into nums1 in place, so that nums1 becomes one sorted array. We don’t return anything.
<br>
Let me clarify a few things. Can m or n be zero? Can the arrays have duplicates or negative numbers?
<br>

The brute force approach is to use two pointers i and j. i for the traversal and j is the position where next element should be placed from nums2. I initialize j with m and traverse the array from 0 to n and in each iteration I copy nums2[i] with nums1[j] and move j forward. after the loop there is no need to return anything.
time complexity is O(n log n) because of sorting. space complexity is O(1) because i use only two pointers

<br>

We can optimize this by using three pointers, p1, p2 and k. I initialize p1 with m minus 1, p2 with n minus 1, and k with m plus n minus 1. I fill nums1 from the back, because if I start from the front, I will overwrite the valid elements of nums1.

I use a while loop with the condition p2 greater than or equal to 0, because once nums2 is finished, the remaining elements of nums1 are already in place. In each iteration, I check if p1 is greater than or equal to 0 and nums1[p1] is greater than nums2[p2]. If yes, I copy nums1[p1] to nums1[k] and move p1 backward. Otherwise, I copy nums2[p2] to nums1[k] and move p2 backward. In both cases, I move k backward.

The loop runs at most m + n times so the time complexity is O(n + m). Space complexity is O(1) because I use only three pointers, p1, p2 and k.

### Code

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int k = m + n - 1;

        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[k] = nums1[p1];
                p1--;
            } else {
                nums1[k] = nums2[p2];
                p2--;
            }
            k--;
        }
    }
}
```

I create three pointers p1, p2 and k. I initialize p1 with m - 1 and p2 with n - 1 and k with m + n - 1. and I use a while loop with condition p2 is greater than or equal to 0 because once nums2 is finished. remaining elements of nums1 are already in place. In each iteration I check if p1 is greater than or equal to 0 because I don't want to go out of bounds. and nums1[p1] is greater than or equal to nums2[p2] if yes I copy nums1[p1] with nums1[k] and move p1 backward. else I copy nums2[p2] with nums1[k] and move p2 backward. In both cases I move k backward. after the loop there is no need to return anything because method's return type is void.
