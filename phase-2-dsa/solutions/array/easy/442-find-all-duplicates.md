# Find All Duplicates

## Pattern

- Brute Force: Nested Loops (Array Traversal)
- Better: Sorting
- Optimal: HashSet (Hashing)

---

## Brute Force

### Code

```java
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    ans.add(nums[i]);
                }
            }
        }

        return ans;
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
    public List<Integer> findDuplicates(int[] nums) {
        Arrays.sort(nums);

        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                ans.add(nums[i]);
            }
        }

        return ans;
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
    public List<Integer> findDuplicates(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        List<Integer> ans = new ArrayList<>();

        for (int n : nums) {
            if (set.contains(n)) {
                ans.add(n);
            }

            set.add(n);
        }


        return ans;
    }
}
```

### Time Complexity

- O(n log n)

### Space Complexity

- O(1)

### Explanation

The brute-force approach is to compare every element with all the elements after it using two nested loops. If I find two equal elements, I add that element to the answer list. This approach is simple and checks every possible pair, but because of the nested loops, its time complexity is O(n²).
<br>
A better approach is to sort the array first. After sorting, duplicate elements become adjacent to each other. Then I traverse the array once and compare each element with the next one. If they are equal, I add that value to the answer list. This approach reduces the time complexity to O(n log n) because of sorting.
<br>
The optimal approach is to use a HashSet. While traversing the array, I check whether the current element is already present in the set. If it is, that means I have seen this element before, so it is a duplicate and I add it to the answer list. Otherwise, I simply add the current element to the set. Since HashSet provides O(1) average lookup and insertion, I process each element only once. This gives an overall time complexity of O(n) with O(n) extra space.
