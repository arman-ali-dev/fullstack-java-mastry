# Maximum Width of Binary Tree

## Pattern

Binary Tree BFS + Complete Binary Tree Indexing

---

## Optimal Approach

### Code

```java
public class Codec {

    public void encode(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null");
            sb.append(",");
            return;
        }

        sb.append(root.val);
        sb.append(",");

        encode(root.left, sb);
        encode(root.right, sb);
    }

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        encode(root, sb);

        return sb.toString();
    }

    public TreeNode deserialize(String data) {
        String[] values = data.split(",");
        int[] idx = {0};
        return build(values, idx);
    }

    public TreeNode build(String[] values, int[] idx) {
        String value = values[idx[0]];
        idx[0]++;

        if (value.equals("null")) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(value));

        node.left = build(values, idx);
        node.right = build(values, idx);

        return node;
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
