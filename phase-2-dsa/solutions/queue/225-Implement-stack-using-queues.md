# Implement Stack Using Queues

## Pattern

Queue + Two Queues

---

## Optimal Approach

### Code

```java
class MyStack {
    Queue<Integer> q;
    Queue<Integer> q2;

    public MyStack() {
        q = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(int x) {
        int n = q.size();

        for (int i = 0; i < n; i++) {
            q2.add(q.poll());
        }

        q.add(x);

        n = q2.size();
        for (int i = 0; i < n; i++) {
            q.add(q2.poll());
        }
    }

    public int pop() {
        return q.poll();
    }

    public int top() {
        return q.peek();
    }

    public boolean empty() {
        return q.isEmpty();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The idea is to implement a stack using queues. A stack follows LIFO, which means the last inserted element should come out first. But a queue follows FIFO, so I need to rearrange the elements to make the newest element come to the front.
<br>
I use two queues, q and q2. During push, I first move all existing elements from q to q2. Then I add the new element to q. After that, I move all elements from q2 back to q.
<br>
This makes the newest element always stay at the front of q. Therefore, pop() can simply remove the front element using poll(), and top() can get the front element using peek().
<br>
For example, if I push 1, then 2, the queue becomes:
<br>
2 → 1
<br>
So when I call pop(), 2 comes out first, just like a stack.
<br>
Time Complexity: push() is O(n) because existing elements are moved between the queues. pop(), top(), and empty() are O(1).
<br>
Space Complexity: O(n) because the queues store all the elements.
