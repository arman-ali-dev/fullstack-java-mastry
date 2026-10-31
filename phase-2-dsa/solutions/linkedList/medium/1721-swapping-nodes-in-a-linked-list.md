# Swapping Nodes in a Linked List

## Pattern

Linked List Traversal + Two Pointers

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode swapNodes(ListNode head, int k) {
        int len = 0;
        ListNode curr = head;
        while (curr != null) {
            len++;
            curr = curr.next;
        }

        ListNode firstNode = head;
        ListNode secondNode = head;

        for (int i = 1; i < k; i++) {
            firstNode = firstNode.next;
        }

        for (int i = 1; i < len - k + 1; i++) {
            secondNode = secondNode.next;
        }

        int temp = firstNode.val;
        firstNode.val = secondNode.val;
        secondNode.val = temp;

        return head;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

First, I calculate the length of the linked list by traversing it once. Then I use two pointers, firstNode and secondNode, both starting from the head. I move the first pointer to the kth node from the beginning and the second pointer to the kth node from the end. Since the position from the end is len - k + 1, I use that to find the second node. Finally, I swap the values of these two nodes using a temporary variable and return the head.
