# Predecessor and Successor in BST

## Pattern

Binary Search Tree (BST) + Iterative Search + Inorder Predecessor & Successor

---

## Optimal Approach

### Code

```java
class Solution {
    public ArrayList<Node> findPreSuc(Node root, int key) {
        Node pred = null;
        Node succ = null;

        while (root != null) {
            if (key > root.data) {
                pred = root;
                root = root.right;
            } else if (key < root.data) {
                succ = root;
                root = root.left;
            } else {

                if (root.left != null) {
                    Node temp = root.left;
                    while (temp != null && temp.right != null) {
                        temp = temp.right;
                    }

                    pred = temp;
                }

                if (root.right != null) {
                    Node temp = root.right;
                    while (temp != null && temp.left != null) {
                        temp = temp.left;
                    }

                    succ = temp;
                }


                break;
            }
        }

        ArrayList<Node> ans = new ArrayList<>();

        ans.add(pred);
        ans.add(succ);

        return ans;
    }
}
```

### Time Complexity

- O(h)

### Space Complexity

- O(1)

### Explanation

I use the BST property to find both the inorder predecessor and successor of the given key.
<br>
While searching for the key, if the key is greater than the current node, that node becomes a possible predecessor, so I store it and move right. If the key is smaller, the current node becomes a possible successor, so I store it and move left.
<br>
When I find the key, I check both subtrees. If the left subtree exists, the predecessor is the rightmost node of the left subtree. Similarly, if the right subtree exists, the successor is the leftmost node of the right subtree.
<br>
Finally, I return both the predecessor and successor.
