public class BinarySearchAlgorithm extends SearchAlgorithm {

    public static int binarySearch(int[] sortedArray, int desiredValue) {
        int low = 0;
        int high = sortedArray.length - 1;

        while (high - low > 1) {
            int mid = (high + low) / 2;
            if (sortedArray[mid] < desiredValue) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        if (sortedArray[low] == desiredValue) {
            return low;
        } else if (sortedArray[high] == desiredValue) {
            return high;
        } else {
            return -1;
        }
    }

}
