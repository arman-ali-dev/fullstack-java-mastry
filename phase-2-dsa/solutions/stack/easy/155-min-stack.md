# Min Stack

## Pattern

Stack + Auxiliary Stack (Min Stack)

---

## Optimal Approach

### Code

```java
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int value) {
        stack.push(value);

        if (minStack.isEmpty() || minStack.peek() >= value) {
            minStack.push(value);
        }
    }

    public void pop() {
        if (stack.peek().equals(minStack.peek())) {
            minStack.pop();
        }

        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

### Time Complexity

- O(1)

### Space Complexity

- O(n)

### Explanation

The problem is that we need to get the minimum element in O(1) time along with normal stack operations. If I use only one stack, finding the minimum would require traversing the stack, which takes O(n).
<br>
So I use two stacks. The first stack stores all the elements normally. The second minStack keeps track of the minimum elements. Whenever I push a value, I push it into minStack only if it is smaller than or equal to the current minimum. This means the top of minStack always represents the current minimum.
<br>
When I pop an element, I first check whether the element being removed is also the current minimum. If it is, I remove it from minStack as well. This is important because after removing the current minimum, the next element in minStack becomes the new minimum.
<br>
Therefore, push, pop, top, and getMin can all be performed in O(1) time.
<br>
Time Complexity: O(1) for each operation.
<br>
Space Complexity: O(n) because in the worst case, minStack can also contain n elements.
