# Implement Stack using Arrays

## Pattern

Array + Top Pointer

---

## Optimal Approach

### Code

```java
class myStack {
    int[] arr;
    int i;
    int size;

    public myStack(int n) {
        arr = new int[n];
        i = -1;
        size = n;
    }

    public boolean isEmpty() {
        if (i == -1) {
            return true;
        }

        return false;
    }

    public boolean isFull() {
        if (i == size - 1) {
            return true;
        }

        return false;
    }

    public void push(int x) {
        if(i < size) {
            arr[++i] = x;
        }
    }

    public void pop() {
        if (i == -1) {
            return;
        }

        i--;
    }

    public int peek() {
        if (i == -1) {
            return -1;
        }

        return arr[i];
    }
}
```

### Time Complexity

- O(1)

### Space Complexity

- O(n)

### Explanation

The idea is to implement a stack using a normal integer array. I use the variable i to keep track of the top of the stack. Initially, i is -1, which means the stack is empty.
<br>
In the push operation, I first check that the stack has space. Then I increase i and store the new element at that position.
<br>
In the pop operation, if the stack is empty, I simply return. Otherwise, I decrease i, which effectively removes the top element.
<br>
In the peek operation, if the stack is empty, I return -1. Otherwise, I return arr[i], which is the current top element.
<br>
isEmpty() checks whether i == -1, and isFull() checks whether i == size - 1.
<br>
Time Complexity: O(1) for push, pop, peek, isEmpty, and isFull.
<br>
Space Complexity: O(n), because the array can store up to n elements.
