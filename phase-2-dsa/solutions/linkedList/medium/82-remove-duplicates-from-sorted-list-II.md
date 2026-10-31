# Remove Duplicates from Sorted List II

## Pattern

Two Pointers + Linked List Traversal

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;

        while (curr != null) {
            if (curr.next != null && curr.val == curr.next.val) {
                int val = curr.val;
                while (curr != null && val == curr.val) {
                    curr = curr.next;
                }

                if (prev == null) {
                    head = curr;
                } else {
                    prev.next = curr;
                }
            } else {
                prev = curr;
                curr = curr.next;
            }
        }

        return head;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

Since the linked list is sorted, duplicate values appear next to each other. I use two pointers, curr and prev, to traverse the list. The curr pointer checks whether the current value is repeated. If a duplicate is found, I store that value and skip all nodes containing the same value. Then I update the previous node's next pointer to connect it to the next distinct node. If the duplicate starts from the head, I update the head pointer. If the current value is not repeated, I move both pointers forward. Finally, I return the updated head.
