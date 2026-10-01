# Maximum Width of Binary Tree

## Pattern

Binary Tree BFS + Complete Binary Tree Indexing

---

## Optimal Approach

### Code

```java
class Pair {
    int idx;
    TreeNode node;

    public Pair(int idx, TreeNode node) {
        this.idx = idx;
        this.node = node;
    }
}

class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        Deque<Pair> deque = new ArrayDeque<>();
        int maxWidth = 0;

        deque.add(new Pair(0, root));

        while (deque.size() > 0) {
            int firstIndex = deque.peek().idx;
            int lastIndex = deque.peekLast().idx;

            maxWidth = Math.max(maxWidth, lastIndex - firstIndex + 1);

            int size = deque.size();

            for (int i = 0; i < size; i++) {
                Pair curr = deque.pop();

                if (curr.node.left != null) {
                    deque.add(new Pair(curr.idx * 2 + 1, curr.node.left));
                }

                if (curr.node.right != null) {
                    deque.add(new Pair(curr.idx * 2 + 2, curr.node.right));
                }
            }
        }

        return maxWidth;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use BFS with a Deque to process the tree level by level.
<br>
I assign an index to every node as if the tree were a complete binary tree. For a node at index i, its left child gets 2i + 1 and its right child gets 2i + 2.
<br>
At each level, I take the first and last node indices. The width of that level is lastIndex - firstIndex + 1. This automatically includes the positions of the missing nodes between them.
<br>
I keep updating the maximum width while traversing all levels.
