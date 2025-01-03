import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
public class Main {
    public static void main(String[] args) throws IOException {
        int[] desiredData = ReadFile.getData("TrafficFlowDataset.csv");
        int[] ranges = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        new Process(ranges, desiredData);
    }
}