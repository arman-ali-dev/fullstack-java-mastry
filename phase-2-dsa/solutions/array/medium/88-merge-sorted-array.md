# Merge Sorted Array

## Pattern

Two Pointers + Merge from Right to Left

---

## Optimal Approach

### Code

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int r1 = m - 1;
        int r2 = n - 1;
        int w = m + n - 1;


        while (w >= 0) {
            if (r1 >= 0 && r2 >= 0) {
                nums1[w] = nums1[r1] > nums2[r2] ? nums1[r1--] : nums2[r2--];
            } else if (r1 >= 0) {
                nums1[w] = nums1[r1--];
            } else {
                nums1[w] = nums2[r2--];
            }

            w--;
        }
    }
}
```

### Time Complexity

- O(m + n)

### Space Complexity

- O(1)

---

### Explanation

The idea is to merge the two sorted arrays directly into nums1. Since nums1 already has enough empty space at the end, I fill it from the back so that I don't overwrite the elements that still need to be compared.
<br>
I use three pointers: r1 points to the last valid element of nums1, r2 points to the last element of nums2, and w points to the last position of the final merged array.
<br>
I compare nums1[r1] and nums2[r2]. The larger element is placed at nums1[w], and its pointer is moved backward.
<br>
If one array becomes empty, I copy the remaining elements of the other array. I continue until w reaches the beginning.
<br>
The important part is that I merge from right to left, because writing from the beginning could overwrite the unprocessed elements of nums1.
<br>
Time Complexity: O(m + n), because every element from both arrays is processed at most once.
<br>
Space Complexity: O(1), because the merging is done in-place and I only use a few pointers.
