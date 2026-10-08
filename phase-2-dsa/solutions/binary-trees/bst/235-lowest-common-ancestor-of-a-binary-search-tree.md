# Lowest Common Ancestor of a BST

## Pattern

BST + Recursion + Lowest Common Ancestor (LCA)

---

## Optimal Approach

### Code

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root.val == p.val || root.val == q.val) {
            return root;
        }

        TreeNode leftLca = null;
        TreeNode rightLca = null;

        if (p.val < root.val && q.val < root.val) {
            leftLca = lowestCommonAncestor(root.left, p, q);
        } else if (p.val > root.val && q.val > root.val) {
            rightLca = lowestCommonAncestor(root.right, p, q);
        } else {
            leftLca = lowestCommonAncestor(root.left, p, q);
            rightLca = lowestCommonAncestor(root.right, p, q);
        }

        if (leftLca != null && rightLca != null) {
            return root;
        } else if (leftLca != null) {
            return leftLca;
        } else {
            return rightLca;
        }
    }
}
```

### Time Complexity

- O(height)

### Space Complexity

- O(height)

### Explanation

I use the BST property to find the lowest common ancestor of the two nodes.
<br>
If the current node is equal to either p or q, I return it.
<br>
If both p and q are smaller than the current node, both nodes must be in the left subtree, so I recursively search there.
<br>
If both are greater, I search in the right subtree.
<br>
Otherwise, the two nodes are on different sides of the current node, so the current node is their lowest common ancestor.
<br>
In my code, I recursively check both sides in this case and return the current root when both results are non-null.
