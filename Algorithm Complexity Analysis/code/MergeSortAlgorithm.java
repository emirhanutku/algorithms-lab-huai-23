import java.util.Arrays;

public class MergeSortAlgorithm extends SortAlgorithm {

    public static int[] sort(int[] unSortedArray) {
        int n = unSortedArray.length;
        if (n<=1){return unSortedArray;}

        int mid = n / 2;
        int[] left = Arrays.copyOfRange(unSortedArray, 0, mid);
        int[] right = Arrays.copyOfRange(unSortedArray, mid, n);



        left= sort(left);
        right= sort(right);
        return Merge(left,right);
    }

    public static int [] Merge (int[] leftArray ,int[] rightArray){
        int [] result =new int[leftArray.length + rightArray.length];
        int i = 0, j = 0, k = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                result[k++] = leftArray[i++];
            } else {
                result[k++] = rightArray[j++];
            }
        }

        while (i < leftArray.length) {
            result[k++] = leftArray[i++];
        }

        while (j < rightArray.length) {
            result[k++] = rightArray[j++];
        }
        return result;
    }
}
