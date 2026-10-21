# Sudoku Solver

## Pattern

Backtracking + Constraint Checking (Sudoku Solver)

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isValid(char ch, int row, int col, int n, char[][] board) {
        // row
        for (int i = 0; i < n; i++) {
            if (board[i][col] == ch) {
                return false;
            }
        }

        // column
        for (int i = 0; i < n; i++) {
            if (board[row][i] == ch) {
                return false;
            }
        }

        // grid
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == ch) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean helper(int row, int col, int n, char[][] board) {
        if (col == n) {
            col = 0;
            row++;
        }

        if (row == n) {
            return true;
        }

        if (board[row][col] != '.') {
            return helper(row, col + 1, n, board);
        }

        for (char ch = '1'; ch <= '9'; ch++) {
            if (isValid(ch, row, col, n, board)) {
                board[row][col] = ch;

                boolean isSolve = helper(row, col + 1, n, board);

                if (isSolve) {
                    return true;
                }

                board[row][col] = '.';
            }
        }

        return false;
    }

    public void solveSudoku(char[][] board) {
        helper(0, 0, 9, board);
    }
}
```

### Time Complexity

- O(9^E)

### Space Complexity

- O(E)

### Explanation

I use backtracking to solve the Sudoku puzzle. I traverse the board cell by cell, moving to the next row when I reach the end of a column.
<br>
If a cell is already filled, I skip it and move to the next cell. If it is empty, I try digits from 1 to 9 and use the isValid function to check whether the digit can be placed without violating the Sudoku rules for its row, column, or 3×3 grid.
<br>
If the digit is valid, I place it and recursively solve the remaining cells. If the recursive call fails, I reset the cell to empty and try the next digit. This is backtracking.
<br>
When I reach beyond the last row, it means all cells have been filled successfully, so I return true.
<br>
Time Complexity: \(O(9^E)\) as a loose upper bound, where \(E\) is the number of empty cells. Each empty cell can try up to 9 digits, and each validity check takes constant time for a fixed 9×9 board.
<br>
Space Complexity: \(O(E)\) for the recursion stack, where \(E\) is the number of empty cells.
