# Longest Consecutive Sequence

## Pattern

HashSet + Sequence Detection

---

## Optimal Approach

### Code

```java
class Solution {
    public int longestConsecutive(int[] nums) {

        if (nums.length == 0) return 0;

        Set<Integer> set = new HashSet<>();

        int maxLength = 1;

        for (int num : nums) {
            set.add(num);
        }

        for (int num : set) {
            int length = 1;

            if (!set.contains(num - 1)) {
                while (set.contains(num + length)) {
                    length++;
                }
            }

            maxLength = Math.max(length, maxLength);
        }

        return maxLength;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The brute-force approach would be to sort the array and then find the longest sequence of consecutive numbers. That would take O(n log n) because of sorting.
<br>
To optimize it, I use a HashSet so I can check whether a number exists in O(1) average time. First, I put all numbers into the set. Then for each number, I check whether num - 1 exists. If it exists, this number is not the beginning of a sequence, so I skip it. If num - 1 does not exist, then this is the starting point of a consecutive sequence. I then keep checking num + 1, num + 2, and so on, and calculate the length of that sequence. Finally, I keep track of the maximum length found.
<br>
The important optimization is that I only start counting from the beginning of a sequence, which prevents unnecessary work for numbers in the middle of a sequence.
<br>
Time Complexity: O(n) average. Although there is a while loop inside the loop, every consecutive sequence is started only from its first element, so the elements are processed efficiently overall.
<br>
Space Complexity: O(n) because I store all the numbers in the HashSet.
