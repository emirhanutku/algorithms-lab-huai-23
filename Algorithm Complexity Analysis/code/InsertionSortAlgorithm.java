public class InsertionSortAlgorithm extends SortAlgorithm {

    public static int[] sort(int[] unSortedArray) {
        for (int j = 1; j < unSortedArray.length; j++) {
            int key = unSortedArray[j];
            int i = j - 1;
            while (i >= 0 && unSortedArray[i] > key) {
                unSortedArray[i + 1] = unSortedArray[i];
                i = i - 1;
            }
            unSortedArray[i + 1] = key;
        }
        return unSortedArray;
    }
}
