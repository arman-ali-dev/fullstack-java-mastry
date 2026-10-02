# Search In a Binary Search Tree

## Pattern

Binary Search Tree (BST) + Recursion

---

## Optimal Approach

### Code

```java
class Solution {
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        if (root.val == val) {
            return root;
        }

        if (val < root.val) {
            return searchBST(root.left, val);
        }

        return searchBST(root.right, val);
    }
}
```

### Time Complexity

- O(height)

### Space Complexity

- O(height)

### Explanation

I use the BST property to decide which subtree can contain the target value.
<br>
If the current node is null, the value is not present, so I return null. If the current node's value matches the target, I return that node.
<br>
If the target is smaller than the current value, I search in the left subtree. Otherwise, I search in the right subtree.
<br>
This allows me to eliminate half of the possible search space at each step in a balanced BST
