# Subtree of Another Tree

## Pattern

Binary Tree DFS + Recursion — Subtree Matching

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isIdentical(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return p == q;
        }

        return p.val == q.val && isIdentical(p.left, q.left) && isIdentical(p.right, q.right);
    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null || subRoot == null) {
            return root == subRoot;
        }

        if (root.val == subRoot.val && isIdentical(root, subRoot)) {
            return true;
        }

        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }
}
```

### Time Complexity

- O(n x m)

### Space Complexity

- O(n)

### Explanation

I use recursion with two functions.
<br>
First, isIdentical() checks whether two trees are exactly the same. It compares their values and recursively checks both the left and right subtrees.
<br>
In isSubtree(), I traverse the main tree using DFS. Whenever I find a node whose value matches the root of subRoot, I call isIdentical() to check whether the complete subtree is identical.
<br>
If the trees are identical, I return true. Otherwise, I continue searching in the left and right subtrees of the main tree.
<br>
So basically, I traverse the main tree and whenever values match, I check whether the two trees are identical.
<br>
Time Complexity: O(n × m) in the worst case, where n is the number of nodes in root and m is the number of nodes in subRoot.
<br>
Space Complexity: O(n) in the worst case because of the recursion stack.
