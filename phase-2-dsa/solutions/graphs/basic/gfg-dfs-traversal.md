# DSF Traversal

## Pattern

Graph Traversal — Depth-First Search (DFS) + Recursion + Visited Array

---

## Optimal Approach

### Code

```java
class Solution {

    public void dfsHelper(int u, ArrayList<Integer>ans, ArrayList<Boolean> vis, ArrayList<ArrayList<Integer>> adj) {
        ans.add(u);
        vis.set(u, true);

        for (int neigh : adj.get(u)) {
            if (!vis.get(neigh)) {
                dfsHelper(neigh, ans, vis, adj);
            }
        }
    }

    public ArrayList<Integer> dfs(ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> ans = new ArrayList<>();
        ArrayList<Boolean> vis = new ArrayList<>();

        for (int i = 0; i < adj.size(); i++){
            vis.add(false);
        }

        dfsHelper(0, ans, vis, adj);

        return ans;
    }
}
```

### Time Complexity

- O(V+E)

### Space Complexity

- O(V)

---

### Explanation

I use DFS to traverse the graph, starting from vertex 0.
<br>
First, I initialize a visited list with all values set to false. Then I call the recursive helper function with vertex 0.
<br>
In the helper function, I add the current vertex to the answer and mark it as visited. Then I traverse all its adjacent vertices.
<br>
If a neighbor has not been visited, I recursively call the helper function for that neighbor. This allows me to explore as deeply as possible along each path before backtracking.
<br>
Finally, I return the traversal order."
<br>
Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
<br>
Space Complexity: O(V) for the visited list, answer list, and recursion stack.
