# Merge Two Sorted Lists

## Pattern

Recursion + Two Pointers / Merge of Sorted Lists

---

## Optimal Approach

### Code

```java
class Solution {
    public ListNode mergeTwoLists(ListNode h1, ListNode h2) {

        if (h1 == null) return h2;
        if (h2 == null) return h1;

        if (h1.val <= h2.val) {
            h1.next = mergeTwoLists(h1.next, h2);
            return h1;
        } else {
            h2.next = mergeTwoLists(h1, h2.next);
            return h2;
        }
    }
}
```

### Time Complexity

- O(n + m)

### Space Complexity

- O(n + m)

### Explanation

Since both linked lists are already sorted, I can merge them by always choosing the smaller value from the two current nodes. I use recursion for this. First, I compare the values of h1 and h2. If h1 is smaller or equal, I keep h1 as the current node and recursively merge the remaining part of h1 with h2. Otherwise, I keep h2 and recursively merge h1 with the remaining part of h2.
<br>
The important part is that after choosing the smaller node, I connect its next to the result of the remaining merge. When one list becomes null, I simply return the other list because the remaining nodes are already sorted.
<br>
So I don't create any new nodes; I just change the existing links.
<br>
Time Complexity: O(n + m), because every node from both lists is processed once.
<br>
Space Complexity: O(n + m) because of the recursion stack. The actual linked-list nodes are reused, so there is no extra node creation.
