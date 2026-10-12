# Undirected Graph Cycle

## Pattern

Graph BFS + Visited Array + Parent Tracking

---

## Optimal Approach

### Code

```java
class Solution {

    public boolean dfs(int src, int par, List<Boolean> vis, List<List<Integer>> adj) {
        vis.set(src, true);


        for (int neigh : adj.get(src)) {
            if (!vis.get(neigh)) {
                if(dfs(neigh, src, vis, adj)) {
                   return true;
                };
            } else if (neigh != par) {
                return true;
            }
        }

        return false;
    }

    public boolean isCycle(int V, int[][] edges) {
        List<Boolean> vis = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        for (int i = 0; i < V; i++) {
            vis.add(false);
        }

        for (int i = 0; i < V; i++) {
            if (!vis.get(i)) {
                if (dfs(i, -1, vis, adj)) {
                    return true;
                }
            }
        }

        return false;
    }
}
```

### Time Complexity

- O(V+E)

### Space Complexity

- O(V+E)

---

### Explanation

I use BFS with a queue to detect a cycle in an undirected graph. First, I build an adjacency list and initialize a visited array.
<br>
Since the graph may be disconnected, I iterate through all vertices and start BFS from every unvisited vertex.
<br>
In BFS, I store each node along with its parent in the queue. For every neighbor, if it is unvisited, I mark it as visited and add it to the queue with the current node as its parent.
<br>
If a neighbor is already visited and is not the current node's parent, a cycle exists, so I return true. If no cycle is found in any component, I return false.
<br>
Time Complexity: O(V + E) — building the adjacency list and performing DFS.
<br>
Space Complexity: O(V + E) — adjacency list uses O(V + E); visited list and recursion stack use up to O(V).
