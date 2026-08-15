# Linked List Cycle II

## Pattern

Floyd’s Cycle Detection Algorithm (Slow & Fast Pointers)

---

## Optimal Approach

### Code

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }

                return slow;
            }
        }

        return null;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

The goal is not only to detect a cycle, but also to find the node where the cycle begins. I use Floyd's slow and fast pointer approach. First, both pointers start from the head. The slow pointer moves one step and the fast pointer moves two steps. If there is no cycle, fast eventually reaches null and I return null.
<br>
If a cycle exists, slow and fast will eventually meet inside the cycle. Once they meet, I reset the slow pointer back to the head, while keeping fast at the meeting point. Then I move both pointers one step at a time. The point where they meet again is the starting node of the cycle, so I return that node.
<br>
The important observation is that after the first meeting, moving one pointer from the head and the other from the meeting point at the same speed makes them meet exactly at the cycle's starting node.
<br>
Time Complexity: O(n), because the pointers traverse the linked list a constant number of times.
<br>
Space Complexity: O(1), because I only use two pointers.
