# Minimum Depth of Binary Tree

## Pattern

Binary Tree + Recursion (DFS)

---

## Optimal Approach

### Code

```java
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
           return 1;
        }

        int lans = root.left == null ? Integer.MAX_VALUE : minDepth(root.left);
        int rans = root.right == null ? Integer.MAX_VALUE : minDepth(root.right);

        return Math.min(lans, rans) + 1;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to find the minimum depth of the binary tree. First, I check if the root is null. If it is null, I return 0. If the current node is a leaf node, I return 1. Otherwise, I recursively calculate the minimum depth of the left and right subtrees. If one child is null, I assign Integer.MAX_VALUE to that side so that it does not affect the minimum depth. Finally, I return the smaller depth plus 1 for the current node.
