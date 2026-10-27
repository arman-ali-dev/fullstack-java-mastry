# Destination City

## Pattern

HashMap

---

## Optimal Approach

### Code

```java
class Solution {
    public String destCity(List<List<String>> paths) {
        Map<String, String> map = new HashMap<>();

        for (List<String> cities : paths) {
            map.put(cities.get(0), cities.get(1));
        }

        for (String val : map.values()) {
            if (!map.containsKey(val)) {
                return val;
            }
        }

        return null;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(n)

### Explanation

I use a HashMap to store each starting city as the key and its destination city as the value. After building the map, I traverse its values. If a destination city does not exist as a key in the map, it means there is no outgoing path from that city, so it must be the destination city. I store that city and return it.
