# Reverse a Linked List

## Pattern

Linked List + Three Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;

            prev = curr;
            curr = next;
        }

        return prev;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

To reverse a linked list, I use three pointers: prev, curr, and next. Initially, curr points to the head and prev is null because the new last node should point to null.
<br>
For every node, I first save curr.next in next because after changing the link, I would otherwise lose access to the remaining list. Then I reverse the current node's pointer by making curr.next point to prev. After that, I move prev to the current node and curr to the next node that I saved earlier.
<br>
I continue this until curr becomes null. At that point, prev is pointing to the new head of the reversed linked list, so I return prev.
<br>
Time Complexity: O(n), because every node is visited exactly once.
<br>
Space Complexity: O(1), because I only use a few pointers and reverse the list in-place.
