# Add Two Numbers

## Pattern

Linked List Traversal + Carry

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode addTwoNumbers(ListNode h1, ListNode h2) {

        ListNode sumList = new ListNode();
        ListNode head = sumList;

        int carry = 0;
        while (h1 != null || h2 != null) {
            int h1Value = h1 != null ? h1.val : 0 ;
            int h2Value = h2 != null ? h2.val : 0;
            int sum = h1Value + h2Value + carry;
            carry = sum / 10;

            sumList.next = new ListNode(sum % 10);
            sumList = sumList.next;

            h1 = h1 != null ? h1.next : null;
            h2 = h2 != null ? h2.next : null;
        }

        if (carry != 0) {
            sumList.next = new ListNode(carry);
        }

        return head.next;
    }
}
```

### Time Complexity

- O(max(n, m))

### Space Complexity

- O(max(n, m))

### Explanation

The idea is similar to how we add two numbers normally, digit by digit from right to left. Since the linked lists store the digits in reverse order, the head already represents the least significant digit, so I can process both lists from the beginning.
<br>
I use a carry variable to store the extra value whenever the sum of two digits is 10 or more. In each iteration, I take the current values from both lists. If one list has already ended, I treat its value as zero. Then I calculate the sum including the carry, store sum % 10 as the current digit, and update the carry using sum / 10.
<br>
I use a dummy node, sumList, to easily build the result list without worrying about the first node. After both lists are processed, if a carry is still remaining, I add one final node. Finally, I return head.next because the first node was only a dummy node.
<br>
Time Complexity: O(max(n, m)), because I process each digit from both lists once.
<br>
Space Complexity: O(max(n, m)) for the new result linked list. The extra working space apart from the output is O(1).
