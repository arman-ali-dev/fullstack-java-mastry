# Linked List Cycle

## Pattern

Floyd’s Cycle Detection Algorithm (Slow & Fast Pointers)

---

## Optimal Approach

### Code

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

To detect whether a linked list contains a cycle, I use two pointers: slow and fast. Both start from the head. The slow pointer moves one node at a time, while the fast pointer moves two nodes at a time.
<br>
If there is no cycle, the fast pointer will eventually reach null. But if there is a cycle, the fast pointer will keep moving around the cycle and will eventually meet the slow pointer. So after moving both pointers, I check whether slow == fast. If they meet, I return true. If the fast pointer reaches the end, I return false.
<br>
The reason this works is similar to two runners on a circular track: the faster runner will eventually catch the slower runner if a cycle exists.
<br>
Time Complexity: O(n), because in the worst case the pointers traverse the list a constant number of times.
<br>
Space Complexity: O(1), because I only use two pointers.
