# Kth Smallest Element in a BST

## Pattern

BST + Inorder Traversal + Counter

---

## Optimal Approach

### Code

```java
class Solution {
    int order = 0;
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }

        if (root.left != null) {
            int leftAns = kthSmallest(root.left, k);
            if (leftAns != -1) {
                return leftAns;
            }
        }

        if (order + 1 == k) {
            return root.val;
        }

        order++;

        if (root.right != null) {
            int rightAns = kthSmallest(root.right, k);
            if (rightAns != -1) {
                return rightAns;
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use inorder traversal because an inorder traversal of a BST gives the elements in sorted order.
<br>
I first recursively traverse the left subtree. After returning from the left subtree, I process the current node and increment the counter.
<br>
When the counter reaches k, the current node is the kth smallest element, so I return its value.
<br>
If the answer is not found in the left subtree or at the current node, I continue with the right subtree.
<br>
The order variable keeps track of how many nodes have been processed in sorted order.
