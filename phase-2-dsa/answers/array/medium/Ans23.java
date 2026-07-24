class Solution {
    public int trap(int[] height) {
        int leftMax = 0;
        int rightMax = 0;
        int n = height.length;
        int left = 0, right = n - 1;
        int ans = 0;

        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            if (leftMax < rightMax) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right--;
            }
        }

        return ans;
    }
}