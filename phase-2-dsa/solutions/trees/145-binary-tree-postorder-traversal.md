# Binary Tree Postorder Traversal

## Pattern

Recursion

---

## Optimal Approach

### Code

```java
class Solution {
    List<Integer> ans = new ArrayList<>();

    public List<Integer> postorderTraversal(TreeNode root) {

        if (root == null) {
            return ans;
        }

        postorderTraversal(root.left);
        postorderTraversal(root.right);
        ans.add(root.val);

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to perform postorder traversal of the binary tree.
<br>
In postorder traversal, the order is Left → Right → Root.
<br>
First, I recursively traverse the left subtree. Then I recursively traverse the right subtree. After both subtrees are processed, I add the current node's value to the answer list.
<br>
If the current node is null, I return because there is nothing to traverse.
<br>
This process continues until all nodes are visited.
<br>
Time Complexity: O(n) because every node is visited exactly once.
<br>
Space Complexity: O(n) because of the recursion stack in the worst case, and the answer list stores n elements.
