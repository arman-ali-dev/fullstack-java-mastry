# Best Buy and Sell Stock

## Pattern

Greedy + One Pass

---

## Brute Force

### Code

```java
class Solution {
    public int maxProfit(int[] prices) {
        int bestBuy = prices[0];
        int maxProfit = 0;

        for (int price : prices) {
            bestBuy = Math.min(bestBuy, price);
            int profit = price - bestBuy;
            maxProfit = Math.max(profit, maxProfit);
        }

        return maxProfit;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

---

## Optimal Approach

### Code

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currSum = 0;

        for (int n : nums) {
            if (n > currSum + n) {
                currSum = n;
            } else {
                currSum += n;
            }

            maxSum = Math.max(currSum, maxSum);
        }


        return maxSum;
    }
}
```

### Time Complexity

- O(n)

### Space Complexity

- O(1)

### Explanation

The idea is to find the maximum profit from buying the stock once and selling it once. I keep track of the lowest buying price seen so far using bestBuy.
<br>
For every price, I first check whether it is smaller than the current bestBuy. If it is, I update bestBuy. Then I calculate the profit I would get by selling the stock at the current price:
<br>
profit = current price - best buying price
<br>
I compare this profit with maxProfit and keep the maximum one.
<br>
This works because for every selling price, I always use the cheapest price that appeared before it as the buying price. If the prices are continuously decreasing, the profit remains 0, which means we don't make any transaction.
<br>
Time Complexity: O(n), because I traverse the array only once.
<br>
Space Complexity: O(1), because I use only a few variables.
