# Intersection of Multiple Arrays

## Pattern

HashMap + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public List<Integer> intersection(int[][] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] numArr : nums) {
            for (int num : numArr) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

        return map.keySet()
                    .stream()
                    .filter(n -> map.get(n) == nums.length)
                    .sorted()
                    .toList();
    }
}
```

### Time Complexity

- O(n + k log k)

### Space Complexity

- O(k)

### Explanation

The idea is to find the elements that are present in every array. I use a HashMap where the key is the number and the value is how many times that number has appeared while traversing all the arrays.
<br>
I first traverse every array and increase the frequency of each number. After that, I go through the keys of the HashMap and keep only those numbers whose frequency is equal to nums.length. This means that the number appeared in every array. Finally, I sort the result because the problem requires the answer in sorted order.
<br>
The important point is that every array contains unique elements, so if a number appears exactly nums.length times overall, it must have appeared once in every array.
<br>
Time Complexity: O(N + K log K), where N is the total number of elements across all arrays and K is the number of elements in the final/result candidate set. The traversal takes O(N), and sorting takes O(K log K).
<br>
Space Complexity: O(K) in terms of distinct numbers stored in the HashMap, where K can be up to the total number of distinct elements.
