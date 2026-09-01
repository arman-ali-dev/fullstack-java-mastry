# Majority Element II

## Pattern

Sorting + Frequency Counting

---

## Optimal Approach

### Code

```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        Arrays.sort(nums);

        List<Integer> ans = new ArrayList<>();
        int n = nums.length;

        int elem = nums[0];
        int freq = 0;

        for (int num : nums) {
            if (elem == num) {
                freq++;
            } else {
                if (freq > n / 3) {
                    ans.add(elem);
                }

                elem = num;
                freq = 1;
            }
        }

        if (freq > n / 3) {
            ans.add(elem);
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

The idea is to find all elements that appear more than n / 3 times. Since the array is sorted first, all occurrences of the same element become adjacent. This makes it easy to count the frequency of each element with a single traversal.
<br>
I maintain two variables: elem stores the current element whose frequency I'm counting, and freq stores its count. Whenever the current number is equal to elem, I increase freq. When a different number appears, I check whether the frequency of the previous element is greater than n / 3. If it is, I add that element to the answer and then start counting the new element.
<br>
After the loop, I separately check the last element because there is no different element after it to trigger the frequency check.
<br>
Time Complexity: O(n log n) because Arrays.sort() takes O(n log n), and the traversal takes O(n).
<br>
Space Complexity: O(1) auxiliary space apart from the output list, assuming the sorting implementation's extra space is not counted. In Java, Arrays.sort(int[]) uses an in-place primitive sort.
