import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {


    static int[] getData(String csvFile) {


        String line = "";
        String csvSplitBy = ",";

        ArrayList<Integer> columnData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                int data = Integer.parseInt(row[6]);
                columnData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] dataArray = new int[columnData.size()];
        for (int i = 0; i < columnData.size(); i++) {
            dataArray[i] = columnData.get(i);
        }
        return dataArray;
    }
}

