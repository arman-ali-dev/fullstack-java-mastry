# Insert Into a Binary Search Tree

## Pattern

Binary Search Tree (BST) + Recursion

---

## Optimal Approach

### Code

```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }
}
```

### Time Complexity

- O(height)

### Space Complexity

- O(height)

### Explanation

I use the BST property to find the correct position for the new value.
<br>
If the current root is null, I create a new node and return it.
<br>
If the value is smaller than the current node, I recursively insert it into the left subtree. Otherwise, I insert it into the right subtree.
<br>
Finally, I return the current root so that the existing tree structure is maintained.
