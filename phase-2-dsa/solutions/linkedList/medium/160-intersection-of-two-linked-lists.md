# Intersection of Two Linked Lists

## Pattern

Two Pointers + Length Difference

---

## Optimal Approach

### Code

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;

        while (a != null && b != null) {
            a = a.next;
            b = b.next;
        }

        if (a == null) {
            int bExtraLen = 0;

            while (b != null) {
                bExtraLen++;
                b = b.next;
            }

            a = headA;
            b = headB;

            for (int i = 1; i <= bExtraLen; i++) {
                b = b.next;
            }

            while (a != null && b != null) {

                if (a == b) {
                    return a;
                }

                a = a.next;
                b = b.next;
            }
        } else {
            int aExtraLen = 0;

            while (a != null) {
                aExtraLen++;
                a = a.next;
            }

            a = headA;
            b = headB;

            for (int i = 1; i <= aExtraLen; i++) {
                a = a.next;
            }

            while (a != null && b != null) {

                if (a == b) {
                    return a;
                }

                a = a.next;
                b = b.next;
            }
        }

        return null;
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(1)

### Explanation

The main idea is that the two linked lists may have different lengths before they reach the common part. So first, I find which list has extra nodes at the beginning. I do this by moving both pointers together until one of them reaches the end. The pointer that is still not null belongs to the longer list.
<br>
Then I calculate how many extra nodes the longer list has. After that, I reset both pointers to their respective heads and move the pointer of the longer list forward by exactly that extra length. Now both pointers are at the same distance from the end of the lists.
<br>
From this point, I move both pointers one step at a time and compare the actual node references using a == b. If they point to the same node, that is the intersection point, so I return that node. If they reach null without meeting, there is no intersection.
<br>
The important thing is that I compare node references, not node values, because two different nodes can contain the same value.
<br>
Time Complexity: O(n + m), because I may traverse both lists multiple times, but each traversal is linear.
<br>
Space Complexity: O(1), because I only use a few pointers and counters.
