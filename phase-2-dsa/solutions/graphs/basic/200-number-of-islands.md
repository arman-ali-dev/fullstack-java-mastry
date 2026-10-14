# Number of Islands

## Pattern

Graph DFS + Visited Matrix + Connected Components

---

## Optimal Approach

### Code

```java
class Solution {
    public void dfs(char[][] grid, List<List<Boolean>> vis, int i, int j, int n, int m) {
        if (i < 0 || j < 0 || i >= n || j >= m || vis.get(i).get(j) || grid[i][j] != '1') {
            return;
        }

        vis.get(i).set(j, true);

        dfs(grid, vis, i - 1, j, n, m); // top
        dfs(grid, vis, i + 1, j, n, m); // bottom
        dfs(grid, vis, i, j + 1, n, m); // right
        dfs(grid, vis, i, j - 1, n, m); // left
    }

    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        List<List<Boolean>> vis = new ArrayList<>();
        int islands = 0;

        for (int i = 0; i < n; i++) {
            List<Boolean> list = new ArrayList<>();

            for(int j = 0; j < m; j++) {
                list.add(false);
            }

            vis.add(list);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!vis.get(i).get(j) && grid[i][j] == '1') {
                    dfs(grid, vis, i, j, n, m);
                    islands++;
                }
            }
        }

        return islands;
    }
}
```

### Time Complexity

- O(n x m)

### Space Complexity

- O(n x m)

---

### Explanation

I use DFS to count the number of islands in a grid. First, I create a visited matrix to keep track of the cells I have already explored.
<br>
Then, I traverse every cell in the grid. Whenever I find an unvisited land cell, represented by '1', I start DFS from that cell and increment the island count.
<br>
During DFS, I mark the current cell as visited and explore its four adjacent directions: up, down, left, and right. I stop when the cell is out of bounds, already visited, or represents water.
<br>
After traversing the entire grid, I return the total number of islands.
<br>
Time Complexity: O(n × m) — each cell is visited at most once.
<br>
Space Complexity: O(n × m) — the visited matrix takes O(n × m) space, and the recursive DFS stack can also take up to O(n × m) space in the worst case.
