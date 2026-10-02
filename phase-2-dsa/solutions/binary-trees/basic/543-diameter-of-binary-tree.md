# Diameter of Binary Tree

## Pattern

Binary Tree DFS — Recursion + Height Calculation

---

## Optimal Approach

### Code

```java
class Solution {
    int ans = 0;

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = height(root.left);
        int right = height(root.right);

        ans = Math.max(ans, left + right);

        return Math.max(left, right) + 1;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to calculate the height of every node and find the diameter at the same time.
<br>
For each node, I first calculate the height of its left and right subtrees.
<br>
The diameter passing through the current node is left + right, because the longest path can go from a node in the left subtree through the current node to a node in the right subtree.
<br>
I update ans with the maximum diameter found so far.
<br>
Then I return the height of the current node as max(left, right) + 1, so that the parent node can use this height.
<br>
In this way, I calculate the height and diameter together in a single traversal.
<br>
Time Complexity: O(n) because every node is visited exactly once.
<br>
Space Complexity: O(n) in the worst case because of the recursion stack.
