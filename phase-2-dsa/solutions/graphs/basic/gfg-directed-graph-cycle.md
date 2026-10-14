# Directed Graph Cycle

## Pattern

Graph DFS + Visited Array + Recursion Path

---

## Optimal Approach

### Code

```java
class Solution {

    public boolean dfs(int curr, List<List<Integer>> adj, List<Boolean> vis, List<Boolean> recPath) {
        vis.set(curr, true);
        recPath.set(curr, true);

        for (int neigh : adj.get(curr)) {
            if (!vis.get(neigh)) {
                if (dfs(neigh, adj, vis, recPath)) {
                    return true;
                }
            } else if (recPath.get(neigh)) {
                return true;
            }
        }

        recPath.set(curr, false);
        return false;
    }

    public boolean isCyclic(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        List<Boolean> vis = new ArrayList<>();
        List<Boolean> recPath = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
            vis.add(false);
            recPath.add(false);
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            adj.get(u).add(v);
        }

        for (int i = 0; i < V; i++) {
            if (!vis.get(i)) {
                if (dfs(i, adj, vis, recPath)) {
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

I use DFS to detect a cycle in a directed graph. I maintain two boolean lists: vis to track visited nodes and recPath to track nodes currently in the recursion path.
<br>
For each unvisited vertex, I start DFS. During DFS, I mark the current node as visited and add it to the recursion path.
<br>
For every neighbor, if it is unvisited, I recursively explore it. If the neighbor is already present in the current recursion path, it means there is a back edge, so a cycle exists.
<br>
After exploring all neighbors, I remove the current node from the recursion path. I repeat this for every disconnected component and return false if no cycle is found.
