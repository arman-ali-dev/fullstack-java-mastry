# Next Greater Element I

## Pattern

Monotonic Stack + HashMap

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                map.put(stack.peek(), num);
                stack.pop();
            }

            stack.push(num);
        }

        for (int x : stack) {
            map.put(x, -1);
        }

        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.get(nums1[i]);
        }

        return ans;
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(n)

### Explanation

The idea is to find the next greater element for every element of nums2. Instead of checking every element to the right, I use a stack to keep the elements for which I have not found a greater element yet.
<br>
I traverse nums2 from left to right. For every current number, I check the stack. While the current number is greater than the element at the top of the stack, the current number is the next greater element for that stack element. So I store this mapping in the HashMap and remove that element from the stack.
<br>
After this process, the elements still remaining in the stack do not have any greater element on their right, so I map them to -1. Finally, I traverse nums1 and use the HashMap to directly get the answer for each element.
<br>
The important idea is that each element is pushed into the stack once and popped at most once, so the stack operations are efficient.
<br>
Time Complexity: O(n + m), where n is the length of nums2 and m is the length of nums1. Each element of nums2 is pushed and popped at most once, and then I traverse nums1 once.
<br>
Space Complexity: O(n), because the HashMap and stack can store elements from nums2.
