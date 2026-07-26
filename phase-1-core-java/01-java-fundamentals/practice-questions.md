# Java Fundamentals - Coding Revision Questions

25 small coding problems to revise syntax and core concepts. Write actual code for each — don't just read and move on.

---

## Variables & Data Types

1. Declare one variable of each primitive type (`byte, short, int, long, float, double, char, boolean`), print all of them, then print each one's default value when declared as an instance variable (not initialized).
2. Write a program that stores your weight in `kg` (double), converts and prints it in `pounds` (1 kg = 2.20462 lbs).
3. Take two `int` variables and swap their values **without using a third variable**.

## Operators

4. Take a 3-digit number from the user and print the sum of its digits.
5. Check whether a given year is a leap year using logical operators (`&&`, `||`).
6. Take a number and print whether it is even or odd using the bitwise `&` operator (not `%`).
7. Write a program that swaps two numbers using the `+` and `-` operators only (no third variable, no XOR).

## Loops

8. Print the first `n` numbers of the Fibonacci series using a `for` loop.
9. Check whether a given number is a palindrome (e.g. `121`) using a `while` loop (no string conversion).
10. Print all prime numbers between 1 and 100 using nested loops.
11. Print the following pattern for `n = 5` rows using nested loops:
    ```
    1
    1 2
    1 2 3
    1 2 3 4
    1 2 3 4 5
    ```

## Methods

12. Write a method `int gcd(int a, int b)` that returns the GCD of two numbers using recursion.
13. Write a method `boolean isPrime(int n)` and use it inside a loop to print all primes below 50.
14. Write two overloaded methods named `multiply` — one that takes two `int`s, another that takes two `double`s — call both from `main`.
15. Write a method `int[] reverseArray(int[] arr)` that returns a new array with elements in reverse order.

## Arrays

16. Given an array of integers, find the second largest element without sorting the array.
17. Given an array, count how many even and odd numbers it contains.
18. Merge two sorted arrays into a single sorted array (without using `Arrays.sort()` on the merged result).
19. Given a 2D array (matrix), calculate the sum of its diagonal elements.
20. Given an array of integers, shift all elements one position to the right (last element wraps to the front).

## Strings

21. Check if a given string is a palindrome (e.g. `"madam"`), ignoring case.
22. Count the number of vowels and consonants in a given string.
23. Given a string, print the frequency of each character (e.g. `"aabbc"` → `a:2, b:2, c:1`).
24. Write a program that checks if two strings are anagrams of each other (e.g. `"listen"` and `"silent"`).

## Wrapper Classes & Basic OOP

25. Create a class `Book` with fields `title` (String), `price` (double), and `quantity` (int). Add a constructor, a method `totalValue()` that returns `price * quantity`, and create 2-3 `Book` objects in `main` to print their total values. Then take one price as a `String` input (e.g. `"499.99"`) and convert it to `double` using wrapper class parsing before creating the object.

---

**Revision method:** Write the code from scratch first, then compare with your original notes only if stuck. If a question takes more than a couple of tries, that topic needs another pass.
