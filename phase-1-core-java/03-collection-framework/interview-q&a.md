#### Q.1 What is the Collection Framework?
The Java Collections Framework is a set of classes and interfaces. It is used to efficiently store, retrieve and manage a group of objects. It provides us with ready-made data structures and algorithms. It is in the java.util package

##### Follow up Q.1 Why do we use the Collections Framework? Why not write our own data structures?

We can write our own data structures but it would very time consuming. and library's data structures are tested and optimized and let's suppose sir we have 2 team members and 1 create data structure and second one don't know about that structure that what is the insertion type of new data structure and which principal it follows but all members familiar with library's data structure. 

#### Q3. How does ArrayList work internally? What happens when it becomes full?

ArrayList is a dynamic array. internally an array is maintained. its default capacity is 10. when the arraylist becomes full then a new array is created by 1.5x capacity then old elements are copied into new array. and we can also define its capacity by its constructor.

##### follow up Q.1 - What is the time complexity of the add() method in ArrayList?
add() method is very fast because it adds element in the next empty index. there is no need to search for anything. but one add's call is very expensive when arraylist becomes full at that time there is need to copy all elements into new array.

##### follow up Q.2 - Suppose you already know that 10,000 elements are going to be added. How would you create an ArrayList for better performance, and why?
If I know the ArrayList is going to have 10,000 elements, I can set its capacity to 10,000 in the constructor. I do not want to create a new array again and again. When I add 10,000 elements one by one, many new arrays are created, so it is an expensive operation. That is why I pass the capacity in the constructor. Then the array is created only once, and no resizes happen.
