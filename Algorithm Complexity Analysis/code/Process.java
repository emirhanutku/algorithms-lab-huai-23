import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Process {
    Process(int[] ranges,int[] desiredData) throws IOException {


        int [] randomInsertionTimes = new int[ranges.length]; int[] randomMergeTimes = new int[ranges.length];int[] randomCountingTimes = new int[ranges.length];
        int [] sortedInsertionTimes = new int[ranges.length]; int [] sortedMergeTimes = new int[ranges.length]; int[] sortedCountingTimes = new int[ranges.length];
        int [] reverseSortedInsertionTimes = new int[ranges.length]; int [] reverseSortedMergeTimes=new int[ranges.length]; int[] reverseSortedCountingTimes=new int[ranges.length];

        int [] randomSortedLinearTimes = new int[ranges.length];int [] sortedLinearTimes = new int[ranges.length]; int []sortedBinaryTimes = new int[ranges.length];

        for (int i = 0; i <ranges.length ; i++) {

            int [ ] rangeOfArray = getArray(ranges[i], desiredData );
            int[]sortedArray = rangeOfArray.clone();
            Arrays.sort(sortedArray);
            Random random = new Random();

            workFunctionSortedAlgorithm(i,randomInsertionTimes,randomCountingTimes,randomMergeTimes,() -> InsertionSortAlgorithm.sort(rangeOfArray.clone()),() -> CountingSortAlgorithm.sort(rangeOfArray),() ->MergeSortAlgorithm.sort(rangeOfArray));
            workFunctionSearchAlgorithm(i,randomSortedLinearTimes,sortedLinearTimes,sortedBinaryTimes,() ->LinearSearchAlgorithm.search(rangeOfArray,rangeOfArray[random.nextInt(rangeOfArray.length)]),() ->LinearSearchAlgorithm.search(sortedArray,rangeOfArray[random.nextInt(rangeOfArray.length)]),() -> BinarySearchAlgorithm.binarySearch(sortedArray,rangeOfArray[random.nextInt(rangeOfArray.length)]));


            Arrays.sort(rangeOfArray);

            workFunctionSortedAlgorithm(i,sortedInsertionTimes,sortedCountingTimes,sortedMergeTimes,() -> InsertionSortAlgorithm.sort(rangeOfArray.clone()),() ->CountingSortAlgorithm.sort(rangeOfArray),() ->MergeSortAlgorithm.sort(rangeOfArray));


            reverseSort(rangeOfArray);

            workFunctionSortedAlgorithm(i,reverseSortedInsertionTimes,reverseSortedCountingTimes,reverseSortedMergeTimes,() -> InsertionSortAlgorithm.sort(rangeOfArray.clone()),() ->CountingSortAlgorithm.sort(rangeOfArray),() ->MergeSortAlgorithm.sort(rangeOfArray));

        }
        System.out.println("Random input data Insertion sort timing results "+Arrays.toString(randomInsertionTimes));
        System.out.println("Random input data Merge sort timing results"+Arrays.toString(randomMergeTimes));
        System.out.println("Random input data Counting sort timing results" + Arrays.toString(randomCountingTimes));
        System.out.println();

        System.out.println("Sorted input data Insertion sort timing results " + Arrays.toString(sortedInsertionTimes));
        System.out.println("Sorted input data Merge sort timing results " + Arrays.toString(sortedMergeTimes));
        System.out.println("Sorted input data Counting sort timing results " +Arrays.toString(sortedCountingTimes));
        System.out.println();

        System.out.println("Reverse sorted input data Insertion sort timing results " + Arrays.toString(reverseSortedInsertionTimes));
        System.out.println("Reverse sorted input data Merge sort timing results " +Arrays.toString(reverseSortedMergeTimes));
        System.out.println("Reverse sorted input data Counting sort timing results " + Arrays.toString(reverseSortedCountingTimes));
        System.out.println();

        System.out.println("Random input data Linear search timing results " + Arrays.toString(randomSortedLinearTimes));
        System.out.println("Sorted input data Linear search timing results " + Arrays.toString(sortedLinearTimes));
        System.out.println("Sorted input data Binary search timing results" + Arrays.toString(sortedBinaryTimes));
        showAndSaveChart("Random Input Data Timing Results",ranges,randomInsertionTimes,randomMergeTimes,randomCountingTimes,true);
        showAndSaveChart("Sorted Input Data timing Results",ranges,sortedInsertionTimes,sortedMergeTimes,sortedCountingTimes,true);
        showAndSaveChart("Reverse Sorted Input Data Timing Results",ranges,reverseSortedInsertionTimes,reverseSortedMergeTimes,reverseSortedCountingTimes,true);
        showAndSaveChart("Searches Timing Results",ranges,randomSortedLinearTimes,sortedLinearTimes,sortedBinaryTimes,false);


    }
    private static void workFunctionSortedAlgorithm (int  index , int [] insertionSort ,int [] countingSort,int[] mergeSort, Runnable insertionFunction ,Runnable countingFunction,Runnable mergeFunction ) {
        int avgInsertionTime = 0;
        int avgCountingTime = 0 ;
        int avgMergeTime = 0;


        for (int i = 0; i < 10; i++) {
            avgInsertionTime +=TimeCalculation.calculateTime(insertionFunction,true);
            avgCountingTime+=TimeCalculation.calculateTime(countingFunction,true);
            avgMergeTime+=TimeCalculation.calculateTime(mergeFunction,true);
        }
        insertionSort[index]= avgInsertionTime / 10;
        countingSort[index]=avgCountingTime/10;
        mergeSort[index]=avgMergeTime/10;

    }
    private static void workFunctionSearchAlgorithm(int index,int[] linearSearchRandom,int[] linearSearchSorted,int[]binarySearchSorted,Runnable linearRandomSearchFunction,Runnable linearSortedSearchFunction, Runnable binarySearchFunction){
        int avgLinearSearchRandomTime = 0 ;
        int avgLinearSearchSortedTime = 0 ;
        int avgBinarySearchSortedTime= 0 ;
        for (int j = 0 ; j<1000 ; j++){
            avgLinearSearchRandomTime +=TimeCalculation.calculateTime(linearRandomSearchFunction,false);
            avgLinearSearchSortedTime +=TimeCalculation.calculateTime(linearSortedSearchFunction,false);
            avgBinarySearchSortedTime +=TimeCalculation.calculateTime(binarySearchFunction,false);
        }
        linearSearchRandom[index] =avgLinearSearchRandomTime/1000;
        linearSearchSorted[index]=avgLinearSearchSortedTime/1000;
        binarySearchSorted[index]=avgBinarySearchSortedTime/1000;
    }

    private static  int[] getArray (int range , int[] dataArray){
        int [] sampledData= new int[range];
        Random random = new Random();
        int startIndex = random.nextInt(dataArray.length - range + 1);
        int endIndex = startIndex + range - 1;
        int j=0;

        for (int i = startIndex; i <= endIndex; i++) {
            sampledData[j]=(dataArray[i]);
            j++;
        }

        return sampledData;
    }
    private static void reverseSort(int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;

            left++;
            right--;
        }
    }
    public static void showAndSaveChart(String title, int[] xAxis, int[] yAxis1,int[] yAxis2,int[] yAxis3,boolean isSort) throws IOException {
        String time = isSort ? "Time in Milliseconds" : "Time in Nanoseconds";
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle(time).xAxisTitle("Input Size").build();


        double[] doubleX= Arrays.stream(xAxis).asDoubleStream().toArray();
        double[] doubleY1 = Arrays.stream(yAxis1).asDoubleStream().toArray();
        double[] doubleY2   = Arrays.stream(yAxis2).asDoubleStream().toArray();
        double[] doubleY3 = Arrays.stream(yAxis3).asDoubleStream().toArray();


        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        if (isSort){
            chart.addSeries("Insertion Sort", doubleX, doubleY1);
            chart.addSeries("Merge Sort", doubleX, doubleY2);
            chart.addSeries("Counting Sort", doubleX, doubleY3);
        }
        else {
            chart.addSeries("Linear Search (Random Input Data) ", doubleX, doubleY1);
            chart.addSeries("Linear Search (Sorted Input Data) ", doubleX, doubleY2);
            chart.addSeries("Binary Search (Sorted Input Data) ", doubleX, doubleY3);
        }



        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);


        new SwingWrapper(chart).displayChart();
    }
}



