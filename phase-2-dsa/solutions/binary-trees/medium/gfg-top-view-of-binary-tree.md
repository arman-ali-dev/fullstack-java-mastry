# Binary Tree BFS + Horizontal Distance + TreeMap

## Pattern

Binary Tree BFS + Horizontal Distance + TreeMap

---

## Optimal Approach

### Code

```java

class Pair {
    Node node;
    int hd;

    public Pair(Node node, int hd) {
        this.node = node;
        this.hd = hd;
    }
}

class Solution {
    public ArrayList<Integer> topView(Node root) {
        Queue<Pair> q = new LinkedList<>();
        Map<Integer, Node> map = new TreeMap<>();

        q.add(new Pair(root, 0));

        while (q.size() > 0) {
            Pair curr = q.peek();
            q.poll();

            if (!map.containsKey(curr.hd)) {
                map.put(curr.hd, curr.node);
            }

            if (curr.node.left != null) {
                q.add(new Pair(curr.node.left, curr.hd - 1));
            }

            if (curr.node.right != null) {
                q.add(new Pair(curr.node.right, curr.hd + 1));
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();

        for (Node node : map.values()) {
            ans.add(node.data);
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

I use BFS with a Queue and maintain the horizontal distance of every node from the root.
<br>
I assign the root a horizontal distance of 0. For the left child, I use hd - 1, and for the right child, I use hd + 1.
<br>
I use a TreeMap where the horizontal distance is the key. Since BFS visits nodes level by level, the first node encountered at each horizontal distance is the top-view node, so I only add it if that horizontal distance is not already present in the map.
<br>
Finally, because TreeMap keeps the horizontal distances sorted, its values give me the nodes from left to right.
<br>
Time Complexity: O(n log n) because each node is inserted into the TreeMap.
<br>
Space Complexity: O(n) because the Queue and TreeMap can store up to n nodes.
