# Search in 2D Array - II

## Pattern

Staircase Search / Top-Right Matrix Traversal

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int row = 0;
        int col = m - 1;

        while (col >= 0 && row < n) {
            if (matrix[row][col] == target) {
                return true;
            } else if (target < matrix[row][col]) {
                col--;
            } else {
                row++;
            }
        }

        return false;
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(1)

### Explanation

I start from the top-right corner of the matrix.
<br>
If the current element is equal to the target, I return true.
<br>
If the target is smaller than the current element, I move left because all elements below in that column are even larger.
<br>
If the target is greater than the current element, I move down because all elements to the left in that row are smaller.
<br>
In this way, every move eliminates either a complete row or a complete column from consideration.
<br>
I continue until I find the target or go outside the matrix boundaries.
<br>
Time Complexity: O(n + m) because I can move at most n times down and m times left.
<br>
Space Complexity: O(1) because I only use row and col variables.
