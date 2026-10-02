# Path Sum

## Pattern

Binary Tree DFS — Recursion + Path Sum

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean helper(TreeNode root, int targetSum, int sum) {
        if (root == null) {
            return false;
        }

        sum += root.val;

        if (root.left == null && root.right == null) {
            return targetSum == sum;
        }

        boolean leftAns = helper(root.left, targetSum, sum);
        boolean rightAns = helper(root.right, targetSum, sum);

        return leftAns || rightAns;
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        return helper(root, targetSum, 0);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use DFS with recursion to check whether there is a root-to-leaf path whose sum is equal to the target sum.
<br>
At each node, I add its value to the current sum.
<br>
When I reach a leaf node, I compare the current sum with targetSum. If they are equal, I return true.
<br>
If it is not a leaf, I recursively check both the left and right subtrees. If either side returns true, I return true.
<br>
If no valid path is found, I return false.
<br>
Time Complexity: O(n) because every node can be visited once.
<br>
Space Complexity: O(n) in the worst case because of the recursion stack."
