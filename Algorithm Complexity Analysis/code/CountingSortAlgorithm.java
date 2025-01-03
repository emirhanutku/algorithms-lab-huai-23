public class CountingSortAlgorithm extends SortAlgorithm{

    public static int[] sort(int[] unSortedArray) {
        int k = findMax(unSortedArray);

        int[] count = new int[k + 1];
        for (int i = 0; i < unSortedArray.length; i++) {
            count[unSortedArray[i]]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] output = new int[unSortedArray.length];

        for (int i = unSortedArray.length - 1; i >= 0; i--) {
            output[count[unSortedArray[i]] - 1] = unSortedArray[i];
            count[unSortedArray[i]]--;
        }
        return output;
    }

    private static int findMax(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
}
