# Valid Anagram

## Pattern

- Better: Sorting
- Optimal: Frequency Array (Counting)

---

## Better

### Code

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        String newS = new String(arr1);
        String newT = new String(arr2);

        return newS.equals(newT);
    }
}
```

### Time Complexity

- O(n log n)

### Space Complexity

- O(n)

---

## Optimal Approach

### Code

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            freq[ch - 'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            freq[ch - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0 || freq[i] < 0) {
                return false;
            }
        }

        return true;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

One approach is to sort both strings. I first convert both strings into character arrays, sort them, and then compare the sorted strings. If they are equal, it means both strings contain the same characters with the same frequency, so they are anagrams. This approach is simple, but sorting both strings takes O(n log n) time.

<br>

To optimize it, I use a frequency array of size 26 because the strings contain only lowercase English letters. In the first traversal, I increase the count for every character in the first string. In the second traversal, I decrease the count for every character in the second string. If both strings are anagrams, every increment will be cancelled by a corresponding decrement, so all frequencies will become zero. Finally, I check the frequency array, and if any value is not zero, I return false; otherwise, I return true. This approach processes the strings in linear time with O(n) time complexity and O(1) extra space.
