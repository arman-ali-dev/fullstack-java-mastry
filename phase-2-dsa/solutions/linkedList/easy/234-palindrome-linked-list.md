# Palindrome Linked List

## Pattern

Fast & Slow Pointers + Reverse Linked List

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isPalindrome(ListNode head) {

        // find middle
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode middle = slow;


        // reverse second half
        ListNode prev = null;
        ListNode curr = middle;

        while (curr != null) {
            ListNode next = curr.next;

            curr.next = prev;
            prev = curr;
            curr = next;
        }


        // check palindrome or not
        ListNode f1 = prev;
        ListNode s1 = head;

        while (f1 != null && s1 != null) {
            if (f1.val != s1.val) {
                return false;
            }

            f1 = f1.next;
            s1 = s1.next;
        }

        return true;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

To check whether a linked list is a palindrome, I divide the list into two halves and compare them. First, I use slow and fast pointers to find the middle of the linked list. The slow pointer moves one step while the fast pointer moves two steps.
<br>
Once I find the middle, I reverse the second half of the linked list. Now the second half is in reverse order, so I can compare it directly with the first half. I use two pointers, one starting from the head and the other starting from the reversed second half. If at any point their values are different, the list is not a palindrome, so I return false.
<br>
If all corresponding values match, I return true. The main idea is that a palindrome reads the same from both directions, so reversing the second half allows me to compare both halves directly.
<br>
Time Complexity: O(n), because finding the middle, reversing the second half, and comparing the halves each take O(n).
<br>
Space Complexity: O(1), because I reverse the linked list in-place and only use pointers.
