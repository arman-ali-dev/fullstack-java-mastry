# Number of Provinces

## Pattern

DFS + Visited Array + Connected Components

---

## Optimal Approach

### Code

```java
class Solution {
    public void dfs(int i, int[][] adj, List<Boolean> vis) {
        vis.set(i, true);

        for (int j = 0; j < adj.length; j++) {
            if (adj[i][j] == 1 && !vis.get(j)) {
                dfs(j, adj, vis);
            }
        }
    }
    public int findCircleNum(int[][] adj) {
        List<Boolean> vis = new ArrayList<>();
        int provinces = 0;

        for (int i = 0; i < adj.length; i++) {
            vis.add(false);
        }

        for (int i = 0; i < adj.length; i++) {
            if (!vis.get(i)) {
                dfs(i, adj, vis);
                provinces++;
            }
        }

        return provinces;
    }
}
```

### Time Complexity

- O(V²) - for each of the V vertices, DFS may scan all V columns in the adjacency matrix.

### Space Complexity

- O(V) - the visited list and recursive DFS stack can each take up to O(V) space.

---

### Explanation

I use DFS to count the number of provinces in a graph. A province is a group of cities that are directly or indirectly connected to each other.
<br>
The given matrix represents the connections between cities. First, I initialize a visited list to track which cities have been explored.
<br>
Then, I iterate through all cities. Whenever I find an unvisited city, I start DFS from it and mark all cities reachable from it as visited. After completing that DFS traversal, I increment the province count because I have explored one complete connected component.
<br>
Finally, I return the total number of provinces.
