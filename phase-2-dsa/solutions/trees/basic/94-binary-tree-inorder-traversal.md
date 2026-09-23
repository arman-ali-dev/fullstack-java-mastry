# Binary Tree Inorder Traversal

## Pattern

Recursion

---

## Optimal Approach

### Code

```java
class Solution {
    List<Integer> ans = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {

        if (root == null) {
            return ans;
        }

        inorderTraversal(root.left);
        ans.add(root.val);
        inorderTraversal(root.right);

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to perform inorder traversal of the binary tree.
<br>
In inorder traversal, the order is Left → Root → Right.
<br>
First, I recursively traverse the left subtree. Then I add the current node's value to the answer list. After that, I recursively traverse the right subtree.
<br>
If the current node is null, I return because there is nothing to traverse.
<br>
This process continues until all nodes are visited.
<br>
Time Complexity: O(n) because every node is visited exactly once.
<br>
Space Complexity: O(n) because of the recursion stack in the worst case, and the answer list stores n elements.
