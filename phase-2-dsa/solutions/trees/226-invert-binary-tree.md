# Invert Binary Tree

## Pattern

Binary Tree DFS — Recursion

---

## Optimal Approach

### Code

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        invertTree(root.left);
        invertTree(root.right);

        TreeNode temp = root.left;

        root.left = root.right;
        root.right = temp;

        return root;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to invert the binary tree.
<br>
If the root is null, I simply return null.
<br>
For every node, I first recursively invert its left and right subtrees. After that, I swap the left and right children of the current node using a temporary variable.
<br>
I repeat this for every node, so every node's left and right children are swapped and the complete tree gets inverted.
<br>
Time Complexity: O(n) because every node is visited exactly once.
<br>
Space Complexity: O(n) in the worst case because of the recursion stack.
