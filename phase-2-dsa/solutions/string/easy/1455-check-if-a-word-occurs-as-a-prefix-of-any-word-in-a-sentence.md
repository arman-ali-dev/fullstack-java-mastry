# Check If a Word Occurs As a Prefix of Any Word in a Sentence

## Pattern

- String Traversal + Prefix Matching

---

## Optimal Approach

### Code

```java
class Solution {
    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] arr = sentence.split(" ");

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].startsWith(searchWord)) {
                return i + 1;
            }
        }

        return -1;
    }
}
```

### Time Complexity

- O(n x m)

### Space Complexity

- O(n)

### Explanation

First, I split the sentence into individual words using the space delimiter.
<br>
Then I traverse the words from left to right and check whether each word starts with the given searchWord using startsWith.
<br>
As soon as I find a matching word, I return its 1-based index, which is why I return i + 1.
<br>
If no word starts with the search word, I return -1.
<br>
Time Complexity: O(n x m) in the general case, where n is the number of words and m is the length of searchWord.
<br>
Space Complexity: O(n) for the array created by split().
