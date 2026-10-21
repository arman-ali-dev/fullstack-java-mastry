# Permutations

## Pattern

Backtracking + In-Place Swapping

---

## Optimal Approach

### Code

```java
class Solution {
    public void helper(int idx, int[] nums, List<List<Integer>> ans) {
        if (idx == nums.length) {
            List<Integer> list = new ArrayList<>();
            for (int val : nums) {
                list.add(val);
            }
            ans.add(list);
            return;
        }

        for (int i = idx; i < nums.length; i++) {
            int temp = nums[i];
            nums[i] = nums[idx];
            nums[idx] = temp;

            helper(idx + 1, nums, ans);

            temp = nums[i];
            nums[i] = nums[idx];
            nums[idx] = temp;
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        helper(0, nums, ans);
        return ans;
    }
}
```

### Time Complexity

- O(n × 2ⁿ)

### Space Complexity

- O(n)

### Explanation

I use backtracking with swapping to generate all permutations of the given array.
<br>
At each index, I try every element from the current index to the end of the array by swapping it with the element at the current index.
<br>
After swapping, I recursively process the next index. When the index reaches the end of the array, I create a copy of the current array and add it to the result.
<br>
After the recursive call returns, I swap the elements back to restore the original arrangement. This is called backtracking, and it allows me to explore all possible permutations without using an extra array.
<br>
Time Complexity: O(n × n!) — there are n! permutations, and copying each permutation takes O(n) time.
<br>
Space Complexity:

- Auxiliary space: O(n) for the recursion stack.
- Output space: O(n × n!) to store all permutations.
