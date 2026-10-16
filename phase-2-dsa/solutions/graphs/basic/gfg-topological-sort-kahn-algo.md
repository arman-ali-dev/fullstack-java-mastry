# Topological Sort

Kahn’s Algorithm is a BFS-based algorithm for topological sorting of a Directed Acyclic Graph (DAG). It repeatedly selects vertices with indegree 0, adds them to the result, and decreases the indegree of their neighboring vertices.

## Pattern

BFS + Indegree (Kahn’s Algorithm) + Topological Sorting

---

## Optimal Approach

### Code

```java
class Solution {
    public ArrayList<Integer> topoSort(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        List<Integer> indegree = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
            indegree.add(0);
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            indegree.set(v, indegree.get(v) + 1);
            adj.get(u).add(v);
        }

        for (int i = 0; i < V; i++) {
            if (indegree.get(i) == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int curr = q.poll();
            res.add(curr);

            for (int neigh : adj.get(curr)) {
                indegree.set(neigh, indegree.get(neigh) - 1);

                if (indegree.get(neigh) == 0) {
                    q.add(neigh);
                }
            }
        }

        return res;
    }
}
```

### Time Complexity

- O(V+E)

### Space Complexity

- O(V+E)

---

### Explanation

I use Kahn's Algorithm to perform topological sorting using BFS. First, I build an adjacency list and calculate the indegree of every vertex, which represents the number of incoming edges.
<br>
Then, I add all vertices with indegree zero to a queue because they have no prerequisites.
<br>
While the queue is not empty, I remove a vertex, add it to the result, and decrease the indegree of all its neighboring vertices. Whenever a neighbor's indegree becomes zero, I add it to the queue.
<br>
I continue until the queue becomes empty and return the resulting topological order.
<br>
Time Complexity: O(V + E) - each vertex and edge is processed once.
<br>
Space Complexity: O(V + E) - adjacency list takes O(V + E), while indegree array, queue, and result take O(V).
