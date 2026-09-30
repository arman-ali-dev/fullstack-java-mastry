# Binary Tree Paths

## Pattern

Binary Tree DFS + Backtracking + StringBuilder

---

## Optimal Approach

### Code

```java
class Solution {

    public void helper(List<String> ans, StringBuilder sb, TreeNode root) {
        if (root == null) {
            return;
        }

        int length = sb.length();

        if (sb.length() > 0) {
            sb.append("->");
        }

        sb.append(root.val);

        if (root.left == null && root.right == null) {
            ans.add(sb.toString());
        }

        helper(ans, sb, root.left);
        helper(ans, sb, root.right);

        sb.setLength(length);
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ans = new ArrayList<>();
        helper(ans, new StringBuilder(), root);

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use DFS with recursion to explore every root-to-leaf path.
<br>
I use a StringBuilder to build the current path. For every node, I append its value, and if it is not the first node, I add -> before it.
<br>
When I reach a leaf node, I add the complete path to the answer.
<br>
After processing the current node and its subtrees, I restore the StringBuilder to its previous length using setLength. This is backtracking, which allows the same StringBuilder to be reused for other paths."
