# Path Sum II

## Pattern

Binary Tree DFS — Backtracking + Path Sum

---

## Optimal Approach

### Code

```java
class Solution {
    public void helper(TreeNode root, int targetSum, List<List<Integer>> result, List<Integer> current) {
        if (root == null) {
            return;
        }

        current.add(root.val);

        if (root.left == null && root.right == null && targetSum - root.val == 0) {
            result.add(new ArrayList<>(current));
        }


        helper(root.left, targetSum - root.val, result, current);
        helper(root.right, targetSum - root.val, result, current);

        current.remove(current.size() - 1);
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        helper(root, targetSum, result, current);

        return result;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(h)

### Explanation

I use DFS with recursion and backtracking to find all root-to-leaf paths whose sum is equal to the target sum.
<br>
I maintain a current list to store the nodes of the current path. Whenever I visit a node, I add its value to current and reduce the remaining targetSum by that value.
<br>
When I reach a leaf node, if the remaining target sum becomes 0, it means the current path has the required sum, so I add a copy of the path to the result.
<br>
After processing both left and right subtrees, I remove the current node from current. This is backtracking, which allows me to reuse the same list for other paths.
<br>
Time Complexity: O(n) for traversing the tree, excluding the cost of copying paths into the result.
<br>
Space Complexity: O(h) auxiliary space for the recursion stack and current path, where h is the tree height. The result list itself can take additional space for storing all valid paths.
