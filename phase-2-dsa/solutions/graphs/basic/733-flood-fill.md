# Flood Fill

## Pattern

DFS + Grid Traversal + In-Place Modification

---

## Optimal Approach

### Code

```java
class Solution {
    public void helper(int[][] image, int sr, int sc, int color, int target) {
        if (sc < 0 || sr < 0 || sr >= image.length || sc >= image[0].length || image[sr][sc] == color || image[sr][sc] != target) {
            return;
        }

        image[sr][sc] = color;

        helper(image, sr - 1, sc, color, target);
        helper(image, sr + 1, sc, color, target);
        helper(image, sr, sc + 1, color, target);
        helper(image, sr, sc - 1, color, target);
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        helper(image, sr, sc, color, image[sr][sc]);
        return image;
    }
}
```

### Time Complexity

- O(n x m)

### Space Complexity

- O(n x m)

---

### Explanation

I use Depth-First Search to perform flood fill on the image. First, I store the original color of the starting pixel as the target color.
<br>
Then, I check whether the current pixel is out of bounds, already has the new color, or does not match the target color. If any of these conditions is true, I stop the recursion.
<br>
Otherwise, I change the current pixel to the new color and recursively explore its four adjacent pixels: up, down, left, and right.
<br>
Since I modify the image directly, I don't need a separate visited matrix. Finally, I return the updated image.
<br>
Time Complexity: O(m × n) - in the worst case, every pixel is visited.
<br>
Space Complexity: O(m × n) - due to the recursive DFS stack in the worst case.
