# Search a 2D Matrix

## Pattern

Binary Search on Rows + Binary Search within a Row

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean searchElement(int[][] matrix, int row, int target) {
        int n = matrix[0].length;
        int start = 0;
        int end = n - 1;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (target == matrix[row][mid]) {
                return true;
            } else if (target > matrix[row][mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return false;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int startRow = 0;
        int endRow = n - 1;

        while (startRow <= endRow) {
            int midRow = (startRow + endRow) / 2;

            if (target >= matrix[midRow][0] && target <= matrix[midRow][m - 1]) {
                return searchElement(matrix, midRow, target);
            } else if (target >= matrix[midRow][m - 1]) {
                startRow = midRow + 1;
            } else {
                endRow = midRow - 1;
            }
        }

        return false;
    }
}
```

### Time Complexity

- O(log n + log m)

### Space Complexity

- O(1)

### Explanation

I use binary search twice.
<br>
First, I apply binary search on the rows. For the current middle row, I check whether the target lies between the first and last element of that row.
<br>
If the target lies in that range, I know it can only be present in that row, so I call searchElement() and perform another binary search on that row.
<br>
If the target is greater than the last element of the middle row, I move to the lower rows. Otherwise, I move to the upper rows.
<br>
The searchElement() method performs normal binary search on the selected row.
<br>
If the target is found, I return true; otherwise, I return false.
<br>
Time Complexity: O(log n + log m) where n is the number of rows and m is the number of columns.
<br>
Space Complexity: O(1) because I only use a few variables and no extra data structure.
