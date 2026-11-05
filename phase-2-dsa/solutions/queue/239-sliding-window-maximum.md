# Sliding Window Maximum

## Pattern

Sliding Window + Monotonic Deque (Decreasing Order)

---

## Optimal Approach

### Code

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.add(i);
        }

        int idx = 0;
        for (int i = k; i < n; i++) {
            res[idx++] = nums[deque.peek()];

            while (!deque.isEmpty() && deque.peek() <= i - k) {
                deque.poll();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.add(i);
        }

        res[idx] = nums[deque.peek()];

        return res;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a Deque to maintain the indices of elements in decreasing order of their values. First, I process the first window of size k. Before adding an index, I remove all smaller elements from the back because they cannot become the maximum while the current element is in the window. Then I add the current index to the deque.

For each next window, I first store the maximum element from the front of the deque in the result array. Then I remove indices that are outside the current window. After that, I remove smaller elements from the back and add the current index. Finally, I store the maximum of the last window and return the result.
