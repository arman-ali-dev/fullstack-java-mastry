# Balanced Binary Tree

## Pattern

Binary Tree DFS — Recursion + Height Calculation

---

## Optimal Approach

### Code

```java
class Solution {
    public int difference(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = difference(root.left);
        int rightDepth = difference(root.right);

        if (leftDepth == -1 || rightDepth == -1) {
            return -1;
        }

        int diff = leftDepth > rightDepth ? leftDepth - rightDepth : rightDepth - leftDepth;

        if (diff > 1) {
            return -1;
        }

        return Math.max(leftDepth, rightDepth) + 1;
    }

    public boolean isBalanced( TreeNode root) {
        int result = difference(root);

        if (result == -1) {
            return false;
        }

        return true;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to check whether the binary tree is balanced.
<br>
For every node, I calculate the height of its left and right subtrees.
<br>
Then I calculate the difference between these two heights. If the difference is greater than 1, the tree is unbalanced, so I return -1.
<br>
If any subtree has already returned -1, I immediately return -1 without doing further calculations.
<br>
Otherwise, I return the height of the current node using max(leftDepth, rightDepth) + 1.
<br>
Finally, if difference(root) returns -1, the tree is not balanced; otherwise, it is balanced.
<br>
Time Complexity: O(n) because every node is visited once.
<br>
Space Complexity: O(n) in the worst case because of the recursion stack.
