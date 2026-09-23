# Binary Tree Preorder Traversal

## Pattern

Recursion

---

## Optimal Approach

### Code

```java
class Solution {
    List<Integer> ans = new ArrayList<>();

    public List<Integer> preorderTraversal(TreeNode root) {

        if (root == null) {
            return ans;
        }


        ans.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to perform preorder traversal of the binary tree.
<br>
In preorder traversal, the order is Root → Left → Right.
<br>
So, first I add the current node's value to the answer list. Then I recursively traverse the left subtree, followed by the right subtree.
<br>
If the current node is null, I simply return because there is nothing to traverse.
<br>
This process continues until all nodes of the tree are visited.
<br>
Time Complexity: O(n) because every node is visited exactly once.
<br>
Space Complexity: O(n) because of the recursion call stack in the worst case, and the answer list also stores n elements.
