# Topological Sort

Definition: Topological Sort is a linear ordering of vertices in a Directed Acyclic Graph (DAG) such that for every directed edge u → v, vertex u appears before vertex v in the ordering.

## Pattern

Graph DFS + Stack (Topological Sorting)

---

## Optimal Approach

### Code

```java
class Solution {

    public void dfs(int curr, List<List<Integer>> adj, List<Boolean> vis, Stack<Integer> stack) {
        vis.set(curr, true);

        for (int neigh : adj.get(curr)) {
            if (!vis.get(neigh)) {
                dfs(neigh, adj, vis, stack);
            }
        }

        stack.push(curr);
    }

    public ArrayList<Integer> topoSort(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        List<Boolean> vis = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < V; i++){
            adj.add(new ArrayList<>());
            vis.add(false);
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            adj.get(u).add(v);
        }

        for (int i = 0; i < V; i++) {
            if (!vis.get(i)) {
                dfs(i, adj, vis, stack);
            }
        }

        while (!stack.isEmpty()) {
            ans.add(stack.pop());
        }

        return ans;
    }
}
```

### Time Complexity

- O(V+E)

### Space Complexity

- O(V+E)

---

### Explanation

I use DFS and a stack to perform topological sorting. First, I create an adjacency list from the directed edges and initialize all vertices as unvisited.
<br>
Then, I start DFS from each unvisited vertex. During DFS, I recursively visit all unvisited neighboring vertices. After exploring all neighbors of a vertex, I push that vertex onto the stack.
<br>
Finally, I pop all vertices from the stack to get the topological ordering. This ensures that a vertex appears before its dependent vertices in the final ordering.
<br>
Time Complexity: O(V + E) - every vertex and edge is processed once.
<br>
Space Complexity: O(V + E) - adjacency list takes O(V + E), while the visited list, stack, and recursion stack take O(V).
