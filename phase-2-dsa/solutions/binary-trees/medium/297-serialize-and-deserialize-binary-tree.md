# Serialize and Deserialize Binary Tree

## Pattern

Binary Tree DFS — Preorder Traversal + Serialization/Deserialization

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

I use DFS with preorder traversal to serialize and deserialize the binary tree.
<br>
In serialize(), I traverse the tree in Root → Left → Right order. I store each node's value in a StringBuilder, and for a null node, I store "null". This is important because it preserves the structure of the tree.
<br>
In deserialize(), I first split the string into values. Then I use recursion to rebuild the tree in the same Root → Left → Right order.
<br>
If the current value is "null", I return null. Otherwise, I create a node and recursively build its left and right children.
<br>
The idx array is used so that the same index can be updated across recursive calls.
<br>
Time Complexity: O(n) because every node is processed once during serialization and deserialization.
<br>
Space Complexity: O(n) because the serialized string, array of values, and recursion stack can require O(n) space.
