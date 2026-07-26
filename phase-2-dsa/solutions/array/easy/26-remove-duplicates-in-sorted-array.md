# Find All Duplicates

## Pattern

- Alternative: HashSet (Hashing)
- Optimal: Two Pointers

---

## Alternate approach

### Code

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int k = 0;

        for (int n : nums) {
            if (!set.contains(n)) {
                nums[k] = n;
                k++;
            }

            set.add(n);
        }

        return k;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

---

## Optimal Approach

### Code

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int k = 0;
        int n = nums.length;

        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[k]) {
                k++;
                nums[k] = nums[i];
            }
        }

        return k + 1;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

### Explanation

alternate approach is to use a HashSet. While traversing the array, I check whether the current element is already present in the set. If it is not present, I copy it into the array and add it to the set. This also works in O(n) time, but it requires O(n) extra space. Because the array is already sorted, using Two Pointers is a better and more space-efficient solution.
<br>
Since the array is already sorted, I don't need any extra data structure to detect duplicates. I use the Two Pointers pattern. One pointer, k, keeps track of the last unique element, while the other pointer traverses the array. Whenever I find an element different from nums[k], I move k forward and place the new unique element at that position. This way, all unique elements are stored at the beginning of the array, and I return k + 1 as the number of unique elements. Since I traverse the array only once and use no extra space, the time complexity is O(n) and the space complexity is O(1).
