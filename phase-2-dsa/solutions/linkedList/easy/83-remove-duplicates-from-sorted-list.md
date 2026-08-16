# Remove Duplicates from Sorted List

## Pattern

Linked List Traversal + In-place Modification

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;

        while (curr != null && curr.next != null) {
            while (curr.next != null && curr.val == curr.next.val) {
                curr.next = curr.next.next;
            }

            curr = curr.next;
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

Because the linked list is already sorted, duplicate values will always be next to each other. So I don't need any extra data structure to detect duplicates.
<br>
I use a curr pointer to traverse the list. For each node, I check whether the next node has the same value. If it does, I skip that duplicate node by changing curr.next to curr.next.next. I keep doing this while consecutive nodes have the same value. Once the next node has a different value, I move curr forward and continue checking the rest of the list.
<br>
The important observation is that because the list is sorted, I only need to compare a node with its next node. This allows me to remove duplicates directly by changing links.
<br>
Time Complexity: O(n), because every node is processed at most a constant number of times.
<br>
Space Complexity: O(1), because I modify the linked list in-place and use only the curr pointer.
