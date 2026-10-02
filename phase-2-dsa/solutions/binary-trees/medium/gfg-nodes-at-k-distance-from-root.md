# Nodes at k Distance from Root

## Pattern

Binary Tree BFS + Level Order Traversal

---

## Optimal Approach

### Code

```java
class Solution {
    public ArrayList<Integer> kdistance(Node root, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        Queue<Node> q = new LinkedList<>();

        if (root == null) {
            return ans;
        }

        int level = 0;
        q.add(root);

        while(q.size() > 0) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                Node curr = q.peek();
                q.poll();

                if (k == level) {
                    ans.add(curr.data);
                }

                if (curr.left != null) {
                    q.add(curr.left);
                }

                if (curr.right != null) {
                    q.add(curr.right);
                }
            }

            if (k == level) {
                return ans;
            }

            level++;
        }

        return ans;
    }
};
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use BFS traversal with a Queue because I need to find all nodes at a specific level.
<br>
I maintain a level variable starting from zero for the root. In every iteration, I process all nodes of the current level using the queue size.
<br>
When the current level becomes equal to k, I add all nodes of that level to the answer.
<br>
After processing that level, I return the answer because all required nodes have been found.
<br>
This allows me to directly collect all nodes that are exactly k distance from the root.
