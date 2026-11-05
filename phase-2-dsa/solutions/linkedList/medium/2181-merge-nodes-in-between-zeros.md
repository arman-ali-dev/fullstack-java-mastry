# Merge Nodes in Between Zeros

## Pattern

Linked List Traversal + Running Sum

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode mergeNodes(ListNode head) {
        ListNode newHead = null;
        ListNode prev = null;
        ListNode curr = head;
        int sum = 0;

        while (curr != null) {
            sum += curr.val;

            if (curr.val == 0 && sum != 0) {
                ListNode node = new ListNode(sum);

                if (newHead == null) {
                    newHead = node;
                    prev = node;
                } else {
                    prev.next = node;
                    prev = node;
                }

                sum = 0;
            }

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

I traverse the linked list using a current pointer and maintain a running sum. For every node, I add its value to the sum. Whenever I encounter a zero and the sum is not zero, it means I have completed one group of nodes. I create a new node with that sum and add it to the result linked list. I use newHead to maintain the beginning of the result and prev to connect the newly created nodes. After processing each group, I reset the sum to zero. Finally, I return the new linked list.
