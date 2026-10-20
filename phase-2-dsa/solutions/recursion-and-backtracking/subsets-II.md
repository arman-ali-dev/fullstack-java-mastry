# Subsets II

## Pattern

Backtracking + Sorting + Duplicate Skipping (Pick / Not Pick)

---

## Optimal Approach

### Code

```java
class Solution {
    public List<List<Integer>> helper(int i, List<List<Integer>> ans, List<Integer> subset, int[] nums) {
        if (i == nums.length) {
            ans.add(new ArrayList<>(subset));
            return ans;
        }

        subset.add(nums[i]);
        helper(i + 1, ans, subset, nums);

        subset.remove(subset.size() - 1);

        i++;
        while (i < nums.length && nums[i - 1] == nums[i]) {
            i++;
        }

        helper(i, ans, subset, nums);

        return ans;
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        return helper(0, new ArrayList<>(), new ArrayList<>(), nums);
    }
}
```

### Time Complexity

- O(n × 2ⁿ)

### Space Complexity

- O(n)

### Explanation

I use backtracking to generate all unique subsets, even when the array contains duplicate elements.
<br>
First, I sort the array so that duplicate elements appear next to each other.
<br>
For each element, I have two choices. First, I include the current element in the subset and recursively explore the next index. Then, I backtrack by removing that element.
<br>
For the second choice, I skip the current element and all its consecutive duplicates before making the recursive call. This prevents generating duplicate subsets.
<br>
Whenever I reach the end of the array, I add a copy of the current subset to the result. Finally, I return all unique subsets.
<br>
Time Complexity: O(n × 2ⁿ) — in the worst case, when all elements are distinct, there are \(2^n\) subsets, and copying each subset takes up to O(n) time. Sorting adds O(n log n), which is dominated by the subset generation cost.

<br>

Space Complexity:

- Auxiliary space: O(n) for the recursion stack and current subset, excluding the output.
- Output space: O(n × 2ⁿ) in the worst case.
