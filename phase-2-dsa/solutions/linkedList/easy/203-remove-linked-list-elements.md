# Remove Linked List Elements

## Pattern

Two Pointers (Prev and Current) + Linked List Traversal

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        ListNode curr = head;
        ListNode prev = null;

        while (curr != null && curr.val == val) {
            curr = curr.next;
        }

        if (curr == null) {
            return null;
        }

        ListNode newHead = curr;
        prev = curr;
        curr = curr.next;

        while (curr != null) {
            while (curr != null && curr.val == val) {
                curr = curr.next;
            }

            prev.next = curr;

            if (curr == null) {
                return newHead;
            }

            prev = curr;
            curr = curr.next;
        }

        return newHead;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use two pointers, prev and curr, to remove all nodes containing the given value. First, I skip all matching nodes from the beginning of the linked list and store the first valid node as the new head. Then I traverse the remaining list. If the current node contains the target value, I skip it by moving the curr pointer forward. I connect the previous valid node to the current node using prev.next. If the current node is valid, I move both pointers forward. Finally, I return the updated head.
