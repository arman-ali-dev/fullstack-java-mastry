# Delete Node in a BST

## Pattern

Binary Search Tree (BST) + Recursion + Inorder Successor

---

## Optimal Approach

### Code

```java
class Solution {
    public TreeNode getIS(TreeNode root) {
        while (root != null && root.left != null) {
            root = root.left;
        }

        return root;
    }
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // root.data == key
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode IS = getIS(root.right);
                root.val = IS.val;
                root.right = deleteNode(root.right, IS.val);
            }
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

I use the BST property to search for the node that needs to be deleted.
<br>
If the key is smaller than the current node, I search in the left subtree, and if it is greater, I search in the right subtree.
<br>
When I find the node, there are three cases. If it has no left child, I return its right child. If it has no right child, I return its left child.
<br>
If the node has two children, I find its inorder successor, which is the smallest node in its right subtree. I replace the current node's value with the successor's value, and then recursively delete that successor from the right subtree.
<br>
This maintains the BST property after deletion
