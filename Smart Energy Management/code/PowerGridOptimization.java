import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        ArrayList<OptimalPowerGridSolution> solutions=new ArrayList<>();
        solutions.add(new OptimalPowerGridSolution(0,new ArrayList<Integer>()));

        for (int j = 1; j < amountOfEnergyDemandsArrivingPerHour.size()+1; j++) {

            int solution = 0;
            int index=0;
            ArrayList<Integer> hours = new ArrayList<>();

            for (int i = 0; i < j; i++) {
                int temporarySolution = solutions.get(i).getmaxNumberOfSatisfiedDemands() + Math.min(amountOfEnergyDemandsArrivingPerHour.get(j-1),(j-i)*(j-i));
                if (temporarySolution>solution){
                    solution=temporarySolution;
                    index = i;
                }
            }
            hours.addAll(solutions.get(index).getHoursToDischargeBatteriesForMaxEfficiency());
            hours.add(j);
            solutions.add(new OptimalPowerGridSolution(solution,hours));
        }
        return solutions.get(findMaxIndex(solutions));

    }
    private static int findMaxIndex(ArrayList<OptimalPowerGridSolution> solutions) {
        int maxIndex = -1;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < solutions.size(); i++) {
            OptimalPowerGridSolution solution = solutions.get(i);
            if (solution.getmaxNumberOfSatisfiedDemands() > max) {
                max = solution.getmaxNumberOfSatisfiedDemands();
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
