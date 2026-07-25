class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int n = arr.length;
        int st = 0;
        int end = n - 1;

        while (st <= end) {
            int mid = (st + end) / 2;

            if (arr[mid + 1] > arr[mid]) {
                st = mid + 1;
            } else if (arr[mid - 1] > arr[mid]) {
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}