# Binary Tree Zigzag Level Order Traversal

## Pattern

Binary Tree BFS + Level Order Traversal + Direction Toggle

---

## Optimal Approach

### Code

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();

        if (root == null) {
            return ans;
        }

        boolean leftToRight = true;

        q.add(root);

        while (q.size() > 0) {
            int size = q.size();
            int[] level = new int[size];

            for (int i = 0; i < size; i++) {
                TreeNode curr = q.poll();
                int idx = leftToRight ? i : size - i - 1;
                level[idx] = curr.val;

                if (curr.left != null) {
                    q.add(curr.left);
                }

                if (curr.right != null) {
                    q.add(curr.right);
                }
            }

            leftToRight = !leftToRight;
            List<Integer> list = new ArrayList<>();

            for (int val : level) {
                list.add(val);
            }

            ans.add(list);
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

I use BFS with a Queue to process the binary tree level by level.
<br>
For each level, I store the number of nodes in size and use an array to place the nodes in the required order.
<br>
When leftToRight is true, I store the node at index i. When it is false, I store it at size - i - 1, which reverses the order of that level.
<br>
After processing each level, I toggle leftToRight so the next level is traversed in the opposite direction.
<br>
Finally, I convert the array into a list and add it to the answer.
