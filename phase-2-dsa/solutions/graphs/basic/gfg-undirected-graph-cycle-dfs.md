# Undirected Graph Cycle

## Pattern

Graph DFS + Visited Array + Parent Tracking

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

I use DFS to detect a cycle in an undirected graph. First, I convert the edge list into an adjacency list and initialize all vertices as unvisited.
<br>
I check every vertex because the graph may contain multiple disconnected components. If a vertex is unvisited, I start DFS from that vertex with its parent set to -1.
<br>
During DFS, I mark the current vertex as visited and explore its neighbors. If a neighbor is unvisited, I recursively visit it while passing the current vertex as its parent.
<br>
If a neighbor is already visited and is not the parent of the current vertex, it means there is a cycle, so I return true. If no cycle is found in any component, I return false.
<br>
Time Complexity: O(V + E) — building the adjacency list and performing DFS.
<br>
Space Complexity: O(V + E) — adjacency list uses O(V + E); visited list and recursion stack use up to O(V).
