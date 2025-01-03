import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;
    ;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     *
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     *
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]+)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     *
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+(\\.[0-9]+)?)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     *
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        String regex = "\\s*" + varName + "\\s*=\\s*\\(\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\)\\s*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fileContent);
        m.find();
        int x = Integer.parseInt(m.group(1));
        int y = Integer.parseInt(m.group(2));
        return new Point(x, y);
    }

    /**
     * Function to extract the train lines from the fileContent by reading train line names and their
     * respective stations.
     *
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();


        Pattern pattern = Pattern.compile("train_line_name\\s*=\\s*\"(.*?)\"\\s*train_line_stations\\s*=\\s*(\\(.*?\\))((?=\\s*train_line_name)|\\s*$)");
        Matcher matcher = pattern.matcher(fileContent);


        while (matcher.find()) {
            String trainLineName = matcher.group(1);
            String trainLineStation = matcher.group(2);


            List<Station> trainLineStations = new ArrayList<>();


            Pattern pattern1 = Pattern.compile("\\((.*?)\\)");
            Matcher matcher1 = pattern1.matcher(trainLineStation);
            int stationNumber = 1;

            while (matcher1.find()) {
                String data = "each point=" + "(" + matcher1.group(1) + ")";
                Point point = getPointVar("each point", data);
                trainLineStations.add(new Station(point, trainLineName + " Line Station " + stationNumber++));
            }
            trainLines.add(new TrainLine(trainLineName, trainLineStations));
        }

        return trainLines;
    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String trainLine = "";

            while ((line = br.readLine()) != null) {
                if (line.contains("num_train_lines")) {
                    numTrainLines = getIntVar("num_train_lines", line);
                } else if (line.contains("starting_point")) {
                    startPoint = new Station(getPointVar("starting_point", line), "Starting Point");
                } else if (line.contains("destination_point")) {
                    destinationPoint = new Station(getPointVar("destination_point", line), "Final Destination");
                } else if (line.contains("average_train_speed")) {
                    double trainSpeed = getDoubleVar("average_train_speed", line);
                    averageTrainSpeed = (trainSpeed * 1000) / 60;
                } else {
                    trainLine = trainLine.concat(line);
                }

            }
            lines = getTrainLines(trainLine);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // TODO: Your code goes here

}
