# Lowest Common Ancestor of a Binary Tree

## Pattern

Binary Tree DFS — Recursion + LCA

---

## Optimal Approach

### Code

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root == p) {
            return p;
        }

        if (root == q) {
            return q;
        }

        TreeNode leftLCA = lowestCommonAncestor(root.left, p, q);
        TreeNode rightLCA = lowestCommonAncestor(root.right, p, q);

        if (leftLCA != null && rightLCA != null) {
            return root;
        } else if (leftLCA != null) {
            return leftLCA;
        } else {
            return rightLCA;
        }
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use DFS with recursion to find the Lowest Common Ancestor of nodes p and q.
<br>
First, if the current node is null, I return null. If the current node is either p or q, I return that node.
<br>
Then I recursively search for p and q in the left and right subtrees.
<br>
If both leftLCA and rightLCA are not null, it means one node was found in the left subtree and the other in the right subtree. Therefore, the current root is their Lowest Common Ancestor.
<br>
If only one side returns a node, I return that node because both p and q are either found there or that node itself is one of p or q.
<br>
Time Complexity: O(n) because every node may be visited once.
<br>
Space Complexity: O(n) in the worst case because of the recursion stack.
