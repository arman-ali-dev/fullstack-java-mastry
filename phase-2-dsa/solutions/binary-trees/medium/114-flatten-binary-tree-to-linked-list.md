# Flatten Binary Tree to Linked List

## Pattern

Binary Tree DFS + Reverse Preorder + Previous/Next Pointer

---

## Optimal Approach

### Code

````java
class Solution {
    TreeNode nextRight = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        flatten(root.right);
        flatten(root.left);

        root.left = null;
        root.right = nextRight;
        nextRight = root;
    }
}```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a reverse preorder traversal, which means I visit the right subtree first, then the left subtree, and finally the current node.
<br>
I maintain a nextRight pointer that stores the previously processed node. For every current node, I set its left pointer to null and its right pointer to nextRight.
<br>
Then I update nextRight to the current node.
<br>
This effectively converts the tree into a linked list in preorder: Root → Left → Right, without using extra data structures.
````
