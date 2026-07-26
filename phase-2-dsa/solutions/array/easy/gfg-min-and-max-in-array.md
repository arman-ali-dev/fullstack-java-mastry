# Min and Max in Array

## Pattern

Array Traversal (Single Pass)

---

## Optimal Approach

### Code

```java
class Solution {
    public ArrayList<Integer> getMinMax(int[] arr) {
        int min = arr[0];
        int max = arr[0];
        ArrayList<Integer> ans = new ArrayList<>();

        for (int num : arr) {

            if (num > max) {
                max = num;
            }

            if (num < min) {
                min = num;
            }
        }


        ans.add(min);
        ans.add(max);

        return ans;
    }
}

```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

My first thought was that since I need both the minimum and maximum values, I can traverse the array once while keeping track of both. I initialize min and max with the first element because it's the only value I've seen initially. Then, for every element in the array, I compare it with the current maximum and update max if it's larger. Similarly, I compare it with the current minimum and update min if it's smaller. By maintaining these two variables throughout a single traversal, I can find both values without making multiple passes over the array. Finally, I store the minimum and maximum in an ArrayList and return the result. This approach is efficient because it processes each element only once, giving a time complexity of O(n) with O(1) extra space, excluding the output list.
