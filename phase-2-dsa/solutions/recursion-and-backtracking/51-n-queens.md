# N-Queens

## Pattern

Backtracking + Constraint Checking (N-Queens)

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

- O(N × N!)

### Space Complexity

- O(N²)

### Explanation

I use backtracking to place N queens on an N by N chessboard such that no two queens attack each other.
<br>
I place one queen in each row. For every row, I try each column and use the isSafe function to check whether placing a queen there would cause a conflict in the same row, column, or diagonals.
<br>
If the position is safe, I place the queen and recursively move to the next row. After the recursive call, I remove the queen to backtrack and try another position.
<br>
When all rows have a queen, I convert the board into strings and add the solution to the result. Finally, I return all valid arrangements.
<br>
Time Complexity: Commonly expressed as O(N!) for the backtracking search, with safety checks adding up to O(N) per attempted position. A conservative upper bound for this implementation is O(N × N!) for the search, excluding output construction.

<br>
Space Complexity:
- Auxiliary space: O(N) for the recursion stack, excluding the board.
- Board: O(N²).
- Output space: Depends on the number of valid solutions; storing each solution takes O(N²) space.
