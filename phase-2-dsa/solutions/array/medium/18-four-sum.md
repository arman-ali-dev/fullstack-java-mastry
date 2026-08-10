# Four Sum

## Pattern

Sorting + Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < n;) {
                int p = j + 1;
                int q = n - 1;

                while (p < q) {
                    long res = (long) nums[i] + (long) nums[j] + (long) nums[p] + (long) nums[q];

                    if (res > target) {
                        q--;
                    } else if (res < target) {
                        p++;
                    } else {
                        ans.add(new ArrayList<>(List.of(nums[i], nums[j], nums[p], nums[q])));
                        q--;
                        p++;

                        while (p < q && nums[p] == nums[p - 1]) {
                            p++;
                        }
                    }
                }

                j++;
                while (j < n && nums[j] == nums[j - 1]) {
                    j++;
                }
            }
        }

        return ans;
    }
}
```

### Time Complexity

- O(n³)

### Space Complexity

- O(uniqueGroups)

### Explanation

The brute-force approach would be to use four nested loops and check every possible combination of four elements. That would take O(n⁴) time, so it is not efficient.
<br>
To optimize it, I first sort the array. After sorting, I fix the first two elements using i and j, and then use two pointers, p and q, to find the remaining two elements. If the total sum is smaller than the target, I move p forward because I need a larger value. If the sum is greater than the target, I move q backward because I need a smaller value. When the sum equals the target, I store the quadruplet and move both pointers.
<br>
Since the array is sorted, I can also skip duplicate values for i, j, and p, which prevents duplicate quadruplets in the answer. I use long for the sum to avoid integer overflow when adding four integers.
<br>
Time Complexity: O(n³). Sorting takes O(n log n), and after fixing the first two elements, the two-pointer search takes O(n²), giving O(n³) overall.
<br>
Space Complexity: O(1) auxiliary space apart from the output list, because I only use a few pointers and variables.
