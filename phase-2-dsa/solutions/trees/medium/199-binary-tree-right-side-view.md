# Binary Tree Right Side View

## Pattern

Binary Tree BFS + Level Order + Right-First Traversal

---

## Optimal Approach

### Code

```java
class Pair {
    int level;
    TreeNode node;

    Pair(int level, TreeNode node) {
        this.level = level;
        this.node = node;
    }
}

class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Queue<Pair> q = new LinkedList<>();
        Map<Integer, Integer> map = new TreeMap<>();


        q.add(new Pair(0, root));

        while (q.size() > 0) {
            Pair curr = q.peek();
            q.poll();

            if (curr.node == null) {
                continue;
            }

            if (!map.containsKey(curr.level)) {
                map.put(curr.level, curr.node.val);
            }

            if (curr.node.right != null) {
                q.add(new Pair(curr.level + 1, curr.node.right));
            }

            if (curr.node.left != null) {
                q.add(new Pair(curr.level + 1, curr.node.left));
            }
        }

        for (int val : map.values()) {
            ans.add(val);
        }

        return ans;
    }
}
```

### Time Complexity

- O(n log n)

### Space Complexity

- O(n)

### Explanation

I use BFS with a Queue and keep track of the level of each node.
<br>
I process the right child before the left child because I want the node that is visible from the right side.
<br>
For every level, I store the first node I encounter in the map. Since the right child is added to the Queue before the left child, the first node encountered at each level is the rightmost visible node.
<br>
Finally, I traverse the TreeMap values level by level and add them to the answer.
<br>
Time Complexity: O(n log n) because I store each level in a TreeMap.
<br>
Space Complexity: O(n) because the Queue and Map can store up to n elements.
