# Inorder Successor in BST

## Pattern

Binary Search Tree (BST) + Iterative Search + Inorder Successor

---

## Optimal Approach

### Code

```java
class Solution {
    public int inOrderSuccessor(Node root, Node k) {
        int key = k.data;
        int succ = -1;

        while (root != null) {
            if (key > root.data) {
                root = root.right;
            } else if (key < root.data) {
                succ = root.data;
                root = root.left;
            } else {
                // key == root.data (key found)
                if (root.right != null) {
                    Node temp = root.right;

                    while (temp != null && temp.left != null) {
                        temp = temp.left;
                    }

                    succ = temp.data;
                }

                break;
            }
        }

        return succ;
    }
}
```

### Time Complexity

- O(h)

### Space Complexity

- O(1)

### Explanation

I use the BST property to find the inorder successor of the given node.
<br>
If the key is greater than the current node, I move to the right subtree. If the key is smaller, the current node can be a possible successor, so I store it and move to the left subtree to find a smaller valid successor.
<br>
When I find the key, if it has a right subtree, its successor is the leftmost node of that right subtree.
<br>
If there is no right subtree, the successor is the smallest ancestor that I stored while moving left.
<br>
Finally, I return the successor, or -1 if no successor exists.
