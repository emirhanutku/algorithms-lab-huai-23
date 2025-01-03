public class LinearSearchAlgorithm extends SearchAlgorithm {
     static int search (int[] array, int desiredValue){
        int size = array.length;
        for (int i = 0; i < size; i++) {
            if (array[i] == desiredValue) {
                return i;
            }
        }
        return -1;
    }
}
