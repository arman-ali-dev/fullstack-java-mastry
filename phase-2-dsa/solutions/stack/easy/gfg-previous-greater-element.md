# Previous Greater Element I

## Pattern

Monotonic Stack

---

## Optimal Approach

### Code

```java
class Solution {
    public ArrayList<Integer> preGreaterEle(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> ans = new ArrayList<>();

        int n = arr.length;

        for (int num : arr) {
            while (!stack.isEmpty() && stack.peek() <= num) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                ans.add(stack.peek());
            } else {
                ans.add(-1);
            }

            stack.push(num);
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

The idea is to find the previous greater element for every element. I traverse the array from left to right and use a stack to keep possible greater elements from the left side.
<br>
For every current element, I remove all elements from the stack that are smaller than or equal to the current element, because they cannot be the previous greater element for the current element. After removing them, if the stack is not empty, the element at the top of the stack is greater than the current element, so it is the previous greater element. If the stack is empty, there is no greater element on the left, so I add -1.
<br>
Finally, I push the current element into the stack so that it can be a possible previous greater element for the upcoming elements.
<br>
Time Complexity: O(n), because every element is pushed into the stack once and popped at most once.
<br>
Space Complexity: O(n), because the stack can contain up to n elements in the worst case.
