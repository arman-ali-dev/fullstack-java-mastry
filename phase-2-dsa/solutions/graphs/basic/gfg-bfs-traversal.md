# BSF Traversal

## Pattern

Graph Traversal — Breadth-First Search (BFS) + Queue + Visited Array

---

## Optimal Approach

### Code

```java
class Solution {
    public ArrayList<Integer> bfs(ArrayList<ArrayList<Integer>> adj) {
        Queue<Integer> q = new LinkedList<>();
        ArrayList<Boolean> v = new ArrayList<>();
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < adj.size(); i++) {
            v.add(false);
        }

        q.add(0);
        v.set(0, true);

        while (!q.isEmpty()) {
            int u = q.poll();

            ans.add(u);

            for (int neigh : adj.get(u)) {
                if (!v.get(neigh)) {
                    v.set(neigh, true);
                    q.add(neigh);
                }
            }
        }

        return ans;
    }
}
```

### Time Complexity

- O(V+E)

### Space Complexity

- O(n)

---

### Explanation

I use BFS to traverse the graph level by level, starting from vertex 0.
<br>
First, I initialize a visited list with all values set to false. Then I add vertex 0 to the queue and mark it as visited.
<br>
While the queue is not empty, I remove a vertex, add it to the answer, and visit all its adjacent vertices.
<br>
If a neighbor has not been visited, I mark it as visited and add it to the queue.
<br>
I mark each vertex as visited when adding it to the queue to avoid processing the same vertex multiple times.
<br>
Finally, I return the BFS traversal order.
<br>
Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
<br>
Space Complexity: O(V) for the visited list, queue, and answer list.
