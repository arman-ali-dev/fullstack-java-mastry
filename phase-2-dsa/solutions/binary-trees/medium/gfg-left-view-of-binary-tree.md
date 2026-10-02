# Left View Of Binary Tree

## Pattern

Binary Tree BFS + Level Order + Left-First Traversal

---

## Optimal Approach

### Code

```java
class Pair {
    int level;
    Node node;

    Pair(int level, Node node) {
        this.level = level;
        this.node = node;
    }
}

class Solution {
    public ArrayList<Integer> leftView(Node root) {
        ArrayList<Integer> ans = new ArrayList<>();
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
                map.put(curr.level, curr.node.data);
            }


            if (curr.node.left != null) {
               q.add(new Pair(curr.level + 1, curr.node.left));
            }

            if (curr.node.right != null) {
                q.add(new Pair(curr.level + 1, curr.node.right));
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
I process the left child before the right child, so at every level, the leftmost node is encountered first.
<br>
For each level, I store only the first node I encounter in the map. Since I add the left child before the right child, the first node at each level is the node visible from the left side.
<br>
Finally, I take the values from the TreeMap in level order and add them to the answer.
<br>
Time Complexity: O(n log n) because I use a TreeMap to store the levels.
<br>
Space Complexity: O(n) because the Queue and Map can store up to n elements.
