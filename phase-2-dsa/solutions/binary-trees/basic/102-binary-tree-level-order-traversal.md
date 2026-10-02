# Binary Tree Level Order Traversal

## Pattern

Binary Tree Traversal — Level Order Traversal (BFS)

---

## Optimal Approach

### Code

```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        List<List<Integer>> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        q.add(root);
        q.add(null);

        List<Integer> level = new ArrayList<>();

        while (q.size() > 0) {
            TreeNode curr = q.poll();

            if (curr == null && q.isEmpty()) {
                ans.add(level);
                break;
            }

            if (curr == null) {
                ans.add(level);
                q.add(null);

                level = new ArrayList<>();
                continue;
            }

            level.add(curr.val);

            if (curr.left != null) {
                q.add(curr.left);
            }

            if (curr.right != null) {
                q.add(curr.right);
            }
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use BFS with a Queue to traverse the binary tree level by level.
<br>
I first add the root to the Queue. Then I process each node from the Queue and add its value to the current level.
<br>
For every node, I add its left and right children to the Queue.
<br>
I use null as a marker to identify when one level ends. When I encounter null, I add the current level to the answer and start a new level.
<br>
This continues until all nodes of the tree are processed.
<br>
Time Complexity: O(n) because every node is visited exactly once.
<br>
Space Complexity: O(n) because the Queue and answer list can store up to n elements.
