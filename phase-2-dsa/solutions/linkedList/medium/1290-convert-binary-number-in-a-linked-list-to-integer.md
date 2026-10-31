# Convert Binary Number in a Linked List to Integer

## Pattern

Linked List Traversal + Binary to Decimal Conversion

---

## Optimal Approach

### Code

```java
class Solution {
    public int getDecimalValue(ListNode head) {
        int len = 0;
        ListNode curr = head;
        while (curr != null) {
            curr = curr.next;
            len++;
        }

        curr = head;
        int number = 0;
        while (curr != null) {
            number += curr.val * Math.pow(2, --len);
            curr = curr.next;
        }

        return number;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

First, I traverse the linked list to calculate its length. Then I traverse it again and use the length to calculate the positional value of each binary digit. For every node, I multiply its value by 2 raised to the power of its position and add the result to the decimal number. I decrease the power after processing each node. Finally, I return the decimal number.
