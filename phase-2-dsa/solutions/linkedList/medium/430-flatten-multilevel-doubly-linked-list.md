# Flatten a Multilevel Doubly Linked List

## Pattern

Recursion + Linked List Traversal

---

## Optimal Approach

### Code

```java
class Solution {
    public Node flatten(Node head) {

        Node curr = head;

        while (curr != null) {

            if (curr.child != null) {
                Node next = curr.next;
                curr.next = flatten(curr.child);
                curr.next.prev = curr;
                curr.child = null;

                while (curr.next != null) {
                    curr = curr.next;
                }

                if (next != null) {
                    curr.next = next;
                    next.prev = curr;
                }
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

- O(d)

### Explanation

The idea is to flatten every child list and insert it between the current node and its original next node. I traverse the list using curr. Whenever a node has a child, I first save its original next node because I am going to change that link.
<br>
Then I recursively flatten the child list and connect it after the current node. I also update the prev pointer and remove the child link. After that, I move through the newly inserted child list until I reach its last node. This is important because I need to connect the original next node after the entire child list, not immediately after the current node.
<br>
Then I continue traversing the remaining list. Recursion handles nested child lists, so even if a child itself has another child, it gets flattened as well.
<br>
Time Complexity: O(n) in terms of the total number of nodes, because every node is processed during the flattening traversal.
<br>
Space Complexity: O(d) due to recursion, where d is the maximum depth of nested child lists. The list itself is modified in-place.
