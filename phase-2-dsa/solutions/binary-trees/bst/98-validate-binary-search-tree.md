# Validate Binary Search Tree

## Pattern

BST Validation + DFS Recursion + Range Constraints

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean helper(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }

        if (min != null && root.val <= min.val) {
            return false;
        }

        if (max != null && root.val >= max.val) {
            return false;
        }

        return helper(root.left, min, root) && helper(root.right, root, max);
    }

    public boolean isValidBST(TreeNode root) {
        return helper(root, null, null);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I validate the BST by maintaining a valid range for every node.
<br>
Initially, the root can have any value, so both minimum and maximum bounds are null.
<br>
For the left subtree, every value must be smaller than the current node, so I update the maximum bound to the current node. For the right subtree, every value must be greater than the current node, so I update the minimum bound to the current node.
<br>
If any node violates its allowed range, I return false. If all nodes satisfy their constraints, the tree is a valid BST.
