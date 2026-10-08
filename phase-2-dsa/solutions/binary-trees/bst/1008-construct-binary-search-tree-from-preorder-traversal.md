# Construct Binary Search Tree from Preorder Traversal

## Pattern

BST + Preorder Traversal + Range Constraint + Recursion

---

## Optimal Approach

### Code

```java
class Solution {
    public TreeNode helper(int[] preorder, int[] idx, int range) {
        if (idx[0] >= preorder.length || preorder[idx[0]] >= range) {
            return null;
        }

        TreeNode node = new TreeNode(preorder[idx[0]++]);
        node.left = helper(preorder, idx, node.val);
        node.right = helper(preorder, idx, range);

        return node;
    }

    public TreeNode bstFromPreorder(int[] preorder) {
        int[] idx = {0};
        return helper(preorder, idx, Integer.MAX_VALUE);
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I construct the BST directly from the preorder traversal using a bound for each subtree.
<br>
The first value in preorder becomes the root, and I increment the index. For the left subtree, the new bound is the current node's value because all left subtree values must be smaller than the node.
<br>
For the right subtree, I keep the same bound because those values must be greater than the current node but still within the ancestor's allowed range.
<br>
If the current value is greater than or equal to the bound, I return null because that value belongs to another subtree.
<br>
This allows me to construct the BST in a single traversal without explicitly sorting the preorder array.
