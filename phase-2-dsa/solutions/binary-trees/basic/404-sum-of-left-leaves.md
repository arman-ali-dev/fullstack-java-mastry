# Sum of Left Leaves

## Pattern

Binary Tree + Recursion (DFS)

---

## Optimal Approach

### Code

```java
class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        return helper(root, false);
    }

    private int helper(TreeNode root, boolean isLeftNode) {
        if (root == null) {
            return 0;
        }

        int lans = helper(root.left, true);
        int rans = helper(root.right, false);
        int sum = isLeftNode && root.left == null && root.right == null ? root.val : 0;

        return lans + rans + sum;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use recursion to traverse the binary tree and calculate the sum of all left leaf nodes. I pass a boolean flag to identify whether the current node is a left child or not. For each node, I recursively calculate the sum of its left and right subtrees. If the current node is a left child and it is a leaf node, I add its value to the sum. Finally, I return the total sum of the left and right subtrees along with the current node's value if it is a left leaf.
