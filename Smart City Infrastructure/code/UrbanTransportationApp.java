import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;

    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function calculate the fastest route from the user's desired starting point to
     * the desired destination point, taking into consideration the hyperloop train
     * network.
     *
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {

        String pattern = "(.* Line) .*";
        List<RouteDirection> routeDirections = new ArrayList<>();
        List<Station> allStations = new ArrayList<>();

        for (TrainLine trainLine : network.lines) {
            allStations.addAll(trainLine.trainLineStations);
        }
        allStations.sort(Comparator.comparingDouble(station -> calculateEuclideanDistance(station.coordinates, network.startPoint.coordinates)));
        allStations.add(network.destinationPoint);

        for (Station station : allStations) {

            station.beforeStations = new ArrayList<>();
            station.beforeStations.add(network.startPoint);
            station.totalDuration = calculateEuclideanDistance(station.coordinates, network.startPoint.coordinates) / network.averageWalkingSpeed;

        }


        for (int i = 1; i < allStations.size(); i++) {

            for (int j = 0; j < i; j++) {

                boolean control;

                if (Objects.equals(allStations.get(i).description, "Final Destination") || Objects.equals(allStations.get(j).description, "Final Destination")) {
                    control = false;
                } else {
                    String string1 = (extractLine(allStations.get(i).description, pattern));

                    String string2 = (extractLine(allStations.get(j).description, pattern));
                    control = Objects.equals(string1, string2);

                }


                String stationNumberStr1 = allStations.get(i).description.substring(allStations.get(i).description.lastIndexOf(" ") + 1);
                String stationNumberStr2 = allStations.get(j).description.substring(allStations.get(j).description.lastIndexOf(" ") + 1);


                if (control && Math.abs(Integer.parseInt(stationNumberStr1) - Integer.parseInt(stationNumberStr2)) != 1) {
                    List<Station> desiredStations = new ArrayList<>();
                    String trainLineName = (extractLine(allStations.get(i).description, pattern));



                    int bigStationNumber = Math.max(Integer.parseInt(stationNumberStr1), Integer.parseInt(stationNumberStr2));
                    int smallStationNumber = Math.min(Integer.parseInt(stationNumberStr1), Integer.parseInt(stationNumberStr2));
                    double duration = 0;

                    for (int currentStationNumber = smallStationNumber; currentStationNumber != bigStationNumber; currentStationNumber++) {

                        Station desiredStation = findStationByDescription(network.lines, extractLine(trainLineName, "(.*?)\\s{0,1}Line"), currentStationNumber);

                        Station nextStation = findStationByDescription(network.lines, extractLine(trainLineName, "(.*?)\\s{0,1}Line"), currentStationNumber + 1);
                        if (smallStationNumber == Integer.parseInt(stationNumberStr1)){desiredStations.add(nextStation);}
                        else {desiredStations.add(desiredStation);}

                        double distance = calculateEuclideanDistance(desiredStation.coordinates, nextStation.coordinates);
                        duration += (distance / network.averageTrainSpeed);
                    }
                    if (smallStationNumber == Integer.parseInt(stationNumberStr1)) {
                        Collections.reverse(desiredStations);
                    }
                    double totalDuration = duration + allStations.get(j).totalDuration;
                    if (totalDuration <= allStations.get(i).totalDuration) {
                        allStations.get(i).totalDuration = totalDuration;
                        allStations.get(i).beforeStations.clear();
                        allStations.get(i).beforeStations.addAll(allStations.get(j).beforeStations);
                        allStations.get(i).beforeStations.addAll(desiredStations);

                    }


                } else {
                    double distance = calculateEuclideanDistance(allStations.get(i).coordinates, allStations.get(j).coordinates);
                    double duration = control ? distance / network.averageTrainSpeed : distance / network.averageWalkingSpeed;

                    double totalDuration = duration + allStations.get(j).totalDuration;

                    if (totalDuration <= allStations.get(i).totalDuration) {
                        allStations.get(i).totalDuration = totalDuration;
                        allStations.get(i).beforeStations.clear();
                        allStations.get(i).beforeStations.addAll(allStations.get(j).beforeStations);
                        allStations.get(i).beforeStations.add(allStations.get(j));
                    }
                }


            }
            if (Objects.equals(allStations.get(i).description, "Final Destination")) break;

        }


        for (int i = 1; i < network.destinationPoint.beforeStations.size(); i++) {
            Station startStation = network.destinationPoint.beforeStations.get(i - 1);
            Station endStation = network.destinationPoint.beforeStations.get(i);
            String string1 = (extractLine(network.destinationPoint.beforeStations.get(i).description, pattern));
            String string2 = (extractLine(network.destinationPoint.beforeStations.get(i - 1).description, pattern));
            boolean control = Objects.equals(string1, string2);
            double distance = calculateEuclideanDistance(network.destinationPoint.beforeStations.get(i).coordinates, network.destinationPoint.beforeStations.get(i - 1).coordinates);
            double duration = control ? distance / network.averageTrainSpeed : distance / network.averageWalkingSpeed;
            routeDirections.add(new RouteDirection(startStation.description, endStation.description, duration, control));
        }
        routeDirections.add(new RouteDirection(network.destinationPoint.beforeStations.get(network.destinationPoint.beforeStations.size() - 1).description, network.destinationPoint.description, calculateEuclideanDistance(network.destinationPoint.beforeStations.get(network.destinationPoint.beforeStations.size() - 1).coordinates, network.destinationPoint.coordinates) / network.averageWalkingSpeed, false));

        // TODO: Your code goes here

        return routeDirections;
    }

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        int totalDuration = calculateTotalRoundedDuration(directions);
        System.out.println("The fastest route takes " + totalDuration + " minute(s).\n" +
                "Directions\n" +
                "----------");
        int i = 1;
        for (RouteDirection routeDirection : directions) {
            String walkOrTrain = routeDirection.trainRide ? "Get on the train from" : "Walk from";
            System.out.println(i++ + ". " + walkOrTrain + " \"" + routeDirection.startStationName + "\" to \"" + routeDirection.endStationName + "\" for " + String.format("%.2f", routeDirection.duration) + " minutes.");
        }

        // TODO: Your code goes here

    }


    public static double calculateEuclideanDistance(Point p1, Point p2) {
        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static String extractLine(String str, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return m.group(1);
        }
        return null;
    }

    public static int calculateTotalRoundedDuration(List<RouteDirection> directions) {
        return (int) Math.round(directions.stream().mapToDouble(d -> d.duration).sum());
    }

    public static Station findStationByDescription(List<TrainLine> lines, String TrainLineName, int stationDescriptionNumber) {
        for (TrainLine trainLine : lines) {
            if (trainLine.trainLineName.equals(TrainLineName)) {
                return trainLine.trainLineStations.get(stationDescriptionNumber - 1);
            }
        }
        return null;
    }


}