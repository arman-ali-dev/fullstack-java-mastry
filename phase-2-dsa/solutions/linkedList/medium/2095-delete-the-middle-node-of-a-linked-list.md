# Delete the Middle Node of a Linked List

## Pattern

Slow and Fast Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode target = null;

        while (fast != null && fast.next != null) {
            target = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        target.next = target.next.next;

        return head;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

I use two pointers, slow and fast, to find the middle node of the linked list. I also maintain a target pointer to keep track of the node just before the middle node. The slow pointer moves one step at a time, while the fast pointer moves two steps at a time. When the fast pointer reaches the end, the slow pointer points to the middle node and the target pointer points to the previous node. I delete the middle node by updating the target's next pointer to skip the middle node. If the list contains only one node, I return null.
