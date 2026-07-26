# Two Sum

## Pattern

- HashMap (Hashing)

The HashMap helps us store numbers we've already seen along with their indices, allowing us to find the required pair in constant time.

---

## Brute Force

### Code

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] ans = new int[2];
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && nums[j] + nums[i] == target) {
                    ans[0] = i;
                    ans[1] = j;
                    return ans;
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

## Optimal Approach

### Code

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] ans = new int[2];
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int first = nums[i];
            int second = target - first;

            if (map.containsKey(second)) {
                ans[0] = i;
                ans[1] = map.get(second);
                break;
            }

            map.put(first, i);
        }

        return ans;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

My first approach was the brute-force solution, where I used two nested loops to check every possible pair in the array. If the indices were different and the sum of the two elements matched the target, I returned those indices immediately. This approach is straightforward and guarantees the correct answer, but since it checks every pair, its time complexity is O(n²), which is not efficient for large inputs.

<br>
<br>

To optimize it, I realized that for every current element, I don't need to search the entire array. I only need one specific number that can complete the target. So, I store the previously visited numbers and their indices in a HashMap. In my code, first represents the current number, and second is the complement calculated as target - first. Before storing the current number, I check if second is already present in the map. If it is, I've found the required pair, so I return the current index along with the stored index. Otherwise, I store the current number and continue. This way, I process each element only once, reducing the time complexity to O(n) while using O(n) extra space.
