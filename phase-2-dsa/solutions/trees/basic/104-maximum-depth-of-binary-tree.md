# Maximum Depth of Binary Tree

## Pattern

Binary Tree — Recursion / DFS

---

## Optimal Approach

### Code

```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepth(root.left) + 1;
        int rightDepth = maxDepth(root.right) + 1;

        return Math.max(leftDepth, rightDepth);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to find the maximum depth of the binary tree.
<br>
If the root is null, I return 0 because there is no node.
<br>
For every node, I recursively calculate the depth of its left subtree and right subtree. I add 1 for the current node.
<br>
Finally, I return the maximum of the left and right depths because the maximum depth is the longest path from the root to a leaf node.
<br>
Time Complexity: O(n) because every node is visited exactly once.
<br>
Space Complexity: O(n) in the worst case because of the recursion stack.
