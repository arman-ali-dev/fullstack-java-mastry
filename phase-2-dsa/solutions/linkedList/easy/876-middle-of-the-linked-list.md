# Middle of the Linked List

## Pattern

Two Pointers — Slow and Fast Pointer

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode middleNode(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

A simple approach would be to first count the total number of nodes and then traverse again until I reach the middle. But that requires two traversals. Instead, I use the slow and fast pointer approach.
<br>
Both pointers start from the head. In every iteration, the slow pointer moves one node at a time, while the fast pointer moves two nodes at a time. Because fast is moving twice as quickly, when fast reaches the end of the linked list, slow will be at the middle.
<br>
The loop condition fast != null && fast.next != null makes sure that the fast pointer can safely move two steps. For an even-sized list, slow ends up at the second middle node, which is what the problem requires.
<br>
Time Complexity: O(n), because the fast pointer travels through the list once.
<br>
Space Complexity: O(1), because I only use two pointers.
