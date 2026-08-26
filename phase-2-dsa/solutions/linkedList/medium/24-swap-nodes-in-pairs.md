# Swap Nodes in Pairs

## Pattern

Linked List Manipulation + Pointer Rewiring

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode swapPairs(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode prev = null;
        ListNode first = head;
        ListNode sec = first.next;

        ListNode newHead = null;

        while (sec != null) {
            ListNode third = sec.next;

            sec.next = first;
            first.next = third;

            if (prev == null) {
                newHead = sec;
            } else {
                prev.next = sec;
            }

            prev = first;
            first = third;

            if (third != null) {
                sec = third.next;
            } else {
                sec = null;
            }
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

The idea is to swap the linked list nodes in pairs without changing their values. For example, 1 → 2 → 3 → 4 becomes 2 → 1 → 4 → 3.
<br>
I process two nodes at a time using first and sec. Before changing any links, I save the third node because it is the beginning of the remaining list. Then I reverse the connection between the first two nodes: sec points to first, and first points to third.
<br>
I also maintain a prev pointer so that the previously processed pair can be connected to the newly swapped pair. For the first pair, there is no previous node, so sec becomes the newHead. After swapping a pair, I move the pointers to the next pair and continue.
<br>
If there is only one node left at the end, it remains unchanged because it cannot form a pair.
<br>
Time Complexity: O(n), because every node is processed once.
<br>
Space Complexity: O(1), because I only use pointers and modify the links in-place.
