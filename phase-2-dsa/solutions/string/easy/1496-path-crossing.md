# Path Crossing

## Pattern

- HashSet + Coordinate Tracking

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isPathCrossing(String path) {
        Set<String> set = new HashSet<>();
        int x = 0;
        int y = 0;

        set.add("0,0");
        for (char ch : path.toCharArray()) {
            switch (ch) {
                case 'N' : y++;
                    break;
                case 'S' : y--;
                    break;
                case 'E' : x++;
                    break;
                case 'W' : x--;
            }

            if (set.contains(x + "," + y)) {
                return true;
            }

            set.add(x + "," + y);
        }

        return false;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use two variables, x and y, to track the current position, starting from the origin (0, 0). I store each visited position in a HashSet. For every direction in the path, I update the coordinates accordingly. Then, I check whether the new position already exists in the set. If it does, the path has crossed a previously visited location, so I return true. Otherwise, I add the new position to the set and continue. If no position is revisited, I return false.
