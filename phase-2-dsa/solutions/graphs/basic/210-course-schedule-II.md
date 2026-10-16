# Course Schedule II

## Pattern

Directed Graph + DFS + Cycle Detection + Topological Sorting (Stack)

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isCycleDFS(int src, List<List<Integer>> adj, List<Boolean> vis, List<Boolean> recPath, Stack<Integer> stack) {
        vis.set(src, true);
        recPath.set(src, true);

        for (int neigh : adj.get(src)) {
            if (!vis.get(neigh)) {
                if (isCycleDFS(neigh, adj, vis, recPath, stack)) {
                    return true;
                }
            } else if (recPath.get(neigh)) {
                return true;
            }
        }

        stack.push(src);
        recPath.set(src, false);
        return false;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        List<Boolean> vis = new ArrayList<>();
        List<Boolean> recPath = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[0];

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
                if (isCycleDFS(i, adj, vis, recPath, stack)) {
                    return ans;
                }
            }
        }

        ans = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            ans[i] = stack.pop();
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

I use DFS-based topological sorting to find a valid order for completing all courses. First, I build an adjacency list where each prerequisite course points to the course that depends on it.
<br>
I maintain a visited list and a recursion-path list to detect cycles. During DFS, I mark the current course as visited and add it to the recursion path. If I encounter a course that is already in the current recursion path, a cycle exists, so I return an empty array because completing all courses is impossible.
<br>
After visiting all neighboring courses, I push the current course onto the stack and remove it from the recursion path.
<br>
Finally, I pop the courses from the stack to obtain the topological ordering and return it as the answer.
<br>
Time Complexity: O(V + E) — every course and prerequisite edge is processed at most a constant number of times.
<br>
Space Complexity: O(V + E) — the adjacency list takes O(V + E) space, while the visited list, recursion path, stack, and answer array take O(V) additional space.
