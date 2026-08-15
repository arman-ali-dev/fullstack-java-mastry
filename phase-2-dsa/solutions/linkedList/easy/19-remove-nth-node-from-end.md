# Remove Nth Node From End of List

## Pattern

Two Pointers — Fast & Slow Pointer

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slow = head;
        ListNode fast = head;

        for (int i = 1; i <= n; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            return head.next;
        }

        while (fast != null && fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        if (slow != null && slow.next != null) {
            slow.next = slow.next.next;
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

The goal is to remove the Nth node from the end without first calculating the length of the linked list. I use two pointers, fast and slow. First, I move fast exactly n positions ahead. This creates a gap of n nodes between the two pointers.
<br>
Then I move both pointers together until fast reaches the end. Because the gap remains n, slow will be positioned just before the node that needs to be removed. I then skip that node by changing slow.next to slow.next.next.
<br>
There is one special case: if fast becomes null immediately after moving it n steps, it means the node to remove is the head, so I return head.next.
<br>
This allows me to solve the problem in one traversal without calculating the length separately.
<br>
Time Complexity: O(n), because the list is traversed at most once.
<br>
Space Complexity: O(1), because I only use two pointers.
