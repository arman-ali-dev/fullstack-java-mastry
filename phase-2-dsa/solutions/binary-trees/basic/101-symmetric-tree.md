# Symmetric Tree

## Pattern

Binary Tree + Recursion (DFS)

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return helper(root, root);
    }

    public boolean helper(TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) {
            return node1 == node2;
        }

        boolean lans = helper(node1.right, node2.left);
        boolean rans = helper(node1.left, node2.right);

        return lans && rans && node1.val == node2.val;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to check whether the binary tree is symmetric. I call the helper function with the root node twice. First, I check if either node is null. If both nodes are null, I return true. If only one node is null, I return false. Otherwise, I compare their values and recursively check the outer and inner children. The outer children are the right child of the first node and the left child of the second node. The inner children are the left child of the first node and the right child of the second node. If all these conditions are true, the tree is symmetric.
