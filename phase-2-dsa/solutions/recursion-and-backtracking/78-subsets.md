# Subsets

## Pattern

Backtracking + Recursion (Pick / Not Pick)

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
        helper(i + 1, ans, subset, nums);

        return ans;
    }
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        return helper(0, ans, new ArrayList<>(), nums);
    }
}
```

### Time Complexity

- O(n × 2ⁿ)

### Space Complexity

- O(n)

### Explanation

I use recursion and backtracking to generate all possible subsets of the given array.
<br>
For each element, I have two choices: either include it in the current subset or exclude it.
<br>
First, I include the current element and recursively explore the next index. Then, I backtrack by removing that element and make the second recursive call without including it.
<br>
When the index reaches the end of the array, I add a copy of the current subset to the result. I continue this process until all possible choices have been explored.
<br>
Since each element has two choices, the total number of subsets is \(2^n\).
<br>
Time Complexity: O(n × 2ⁿ) — there are 2ⁿ subsets, and copying each subset takes up to O(n) time.
<br>
Space Complexity: O(n) auxiliary space for the recursion stack and current subset, excluding the output. The output itself takes O(n × 2ⁿ) space.
