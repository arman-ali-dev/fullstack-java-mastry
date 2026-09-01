# Implement Queue Using Stacks

## Pattern

Stack + Two Stack

---

## Optimal Approach

### Code

```java
class MyQueue {
    Stack<Integer> s1;
    Stack<Integer> s2;

    public MyQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    public void push(int x) {
        int n = s1.size();

        for (int i = 0; i < n; i++) {
            s2.push(s1.pop());
        }

        s1.add(x);

        n = s2.size();

        for (int i = 0; i < n; i++) {
            s1.push(s2.pop());
        }
    }

    public int pop() {
        return s1.pop();
    }

    public int peek() {
        return s1.peek();
    }

    public boolean empty() {
        return s1.isEmpty();
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

The idea is to implement a queue using two stacks. A queue follows FIFO, which means the first inserted element should come out first. But a stack follows LIFO, so I rearrange the elements to make the oldest element stay at the top of s1.
<br>
During push, I first move all elements from s1 to s2. Then I add the new element to s1. After that, I move all elements from s2 back to s1.
<br>
For example, if I push 1, then 2, s1 will look like:
<br>
1 ← 2
<br>
So 1 stays at the top and will be removed first, which gives us queue behavior.
<br>
Therefore, pop() simply does s1.pop(), peek() does s1.peek(), and empty() checks whether s1 is empty.
<br>
Time Complexity: push() is O(n) because I move all existing elements between the two stacks. pop(), peek(), and empty() are O(1).
<br>
Space Complexity: O(n) because the two stacks together store all the elements.
