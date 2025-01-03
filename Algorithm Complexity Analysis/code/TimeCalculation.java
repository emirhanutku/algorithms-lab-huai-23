public class TimeCalculation {
    static long calculateTime(Runnable function, boolean isSort) {
        long startTime = System.nanoTime();
        function.run();
        long endTime = System.nanoTime();

        return (isSort ? (endTime - startTime) / 1_000_000 : endTime - startTime);
    }
}
