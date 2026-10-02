# Bottom View Of Binary Tree

## Pattern

Binary Tree BFS + Horizontal Distance + TreeMap

---

## Optimal Approach

### Code

```java
class Pair {
    int hd;
    Node node;

    Pair(int hd, Node node) {
        this.hd = hd;
        this.node = node;
    }
}

class Solution {
    public ArrayList<Integer> bottomView(Node root) {
        Queue<Pair> q = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        Map<Integer, Integer> map = new TreeMap<>();

        if (root == null) {
            return ans;
        }

        q.add(new Pair(0, root));

        while (q.size() > 0) {
            Pair curr = q.peek();
            q.poll();

            if (curr == null) {
                continue;
            }

            map.put(curr.hd, curr.node.data);

            if (curr.node.left != null) {
                q.add(new Pair(curr.hd - 1, curr.node.left));
            }

            if (curr.node.right != null) {
                q.add(new Pair(curr.hd + 1, curr.node.right));
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

I use BFS traversal with a Queue and maintain a horizontal distance for every node.
<br>
The root has horizontal distance zero. For the left child, I decrease the horizontal distance by one, and for the right child, I increase it by one.
<br>
For the bottom view, whenever I visit a node, I update the value for its horizontal distance in the TreeMap. Because BFS processes nodes level by level, deeper nodes overwrite the nodes above them.
<br>
Finally, the TreeMap keeps the horizontal distances sorted from left to right, so its values give the bottom view of the binary tree
