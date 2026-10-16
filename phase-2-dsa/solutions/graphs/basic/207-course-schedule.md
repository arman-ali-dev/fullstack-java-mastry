# Course Schedule

## Pattern

Directed Graph + DFS + Cycle Detection using Recursion Path

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isCycleDFS(int src, List<List<Integer>> adj, List<Boolean> vis, List<Boolean> recPath) {
        vis.set(src, true);
        recPath.set(src, true);

        for (int neigh : adj.get(src)) {
            if (!vis.get(neigh)) {
                if (isCycleDFS(neigh, adj, vis, recPath)) {
                    return true;
                }
            } else if (recPath.get(neigh)) {
                return true;
            }
        }

        recPath.set(src, false);
        return false;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        List<Boolean> vis = new ArrayList<>();
        List<Boolean> recPath = new ArrayList<>();


        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
            vis.add(false);
            recPath.add(false);
        }

        for (int[] edge : prerequisites) {
            int u = edge[1];
            int v = edge[0];

            adj.get(u).add(v);
        }


        for (int i = 0; i < numCourses; i++) {
            if (!vis.get(i)) {
                if (isCycleDFS(i, adj, vis, recPath)) {
                    return false;
                }
            }
        }

        return true;
    }
}
```

### Time Complexity

- O(V+E)

### Space Complexity

- O(V)

---

### Explanation

I use DFS to detect a cycle in the course dependency graph. First, I build an adjacency list where an edge from course u to course v means course u must be completed before course v.
<br>
I maintain two boolean lists: vis to track visited courses and recPath to track courses currently in the recursion path.
<br>
For every unvisited course, I run DFS. If I encounter a course that is already in the current recursion path, it means there is a cycle in the prerequisites, so completing all courses is impossible, and I return false.
<br>
If no cycle exists in any component, I return true because all courses can be completed.
<br>
Time Complexity: O(V + E) — where V is the number of courses and E is the number of prerequisites.
<br>
Space Complexity: O(V + E) — adjacency list takes O(V + E), while the visited list, recursion-path list, and DFS stack take O(V).
<br>
Important: Your code is correct. The edge direction prerequisites[i][1] → prerequisites[i][0] represents the prerequisite course pointing to the course that depends on it.
