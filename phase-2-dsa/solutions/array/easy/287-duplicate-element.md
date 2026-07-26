# Find Duplicate

## Pattern

- Brute Force: Nested Loops (Array Traversal)
- Better: HashSet (Hashing)
- Optimal: Sorting

---

## Brute Force

### Code

```java
class Solution {
    public int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(n²)

### Space Complexity

- O(1)

---

## Better Approach

### Code

```java
class Solution {
    public int findDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int n : nums) {
            if (set.contains(n)) {
                return n;
            }

            set.add(n);
        }

        return -1;
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
public int findDuplicate(int[] nums) {
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return nums[i];
            }
        }

        return -1;
    }

}
```

### Time Complexity

- O(n log n)

### Space Complexity

- O(1)

### Explanation

The brute-force approach is to compare every element with all the elements after it using two nested loops. If I find two equal elements, I immediately return that duplicate. This approach is simple and works correctly, but it takes O(n²) time because of the nested loops.
<br>
To improve it, I use a HashSet. While traversing the array, I check whether the current element is already present in the set. If it is, then that element is the duplicate, so I return it immediately. Otherwise, I add the current element to the set and continue. This reduces the time complexity to O(n), but it requires O(n) extra space.
<br>
Another approach is sorting the array first. After sorting, duplicate elements become adjacent to each other. Then I simply traverse the array once and compare every element with the next one. If two adjacent elements are equal, I return that value. This approach takes O(n log n) time because of sorting and O(1) extra space if the sorting algorithm is in-place.
