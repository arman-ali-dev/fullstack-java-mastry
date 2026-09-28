# Vertical Tree Traversal

## Pattern

Binary Tree BFS + Horizontal Distance + TreeMap

---

## Optimal Approach

### Code

```java
class Pair {
    int col;
    Node node;

    Pair(int col, Node node) {
        this.col = col;
        this.node = node;
    }
}

class Solution {
    public ArrayList<ArrayList<Integer>> verticalOrder(Node root) {
        Map<Integer, ArrayList<Integer>> map = new TreeMap<>();
        Queue<Pair> q = new LinkedList<>();
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        q.add(new Pair(0, root));

        while (q.size() > 0) {
            Pair curr = q.peek();
            q.poll();

            if (!map.containsKey(curr.col)) {
                map.put(curr.col, new ArrayList<>());
            }

            map.get(curr.col).add(curr.node.data);

            if (curr.node.left != null) {
                q.add(new Pair(curr.col - 1, curr.node.left));
            }
            if (curr.node.right != null) {
                q.add(new Pair(curr.col + 1, curr.node.right));
            }
        }

        for (ArrayList<Integer> list : map.values()) {
            ans.add(list);
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

I use BFS traversal with a Queue and assign a horizontal distance to every node.
<br>
The root has column zero. For every left child, I decrease the column by one, and for every right child, I increase the column by one.
<br>
I use a TreeMap where each column stores a list of nodes belonging to that column. During BFS, I add every node to its corresponding column list.
<br>
Finally, since TreeMap automatically keeps the columns sorted from left to right, I add all the lists to the answer.
