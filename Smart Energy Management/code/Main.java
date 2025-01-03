import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class
 */

public class Main {

    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/
        System.out.println("##MISSION POWER GRID OPTIMIZATION##");

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        ArrayList<Integer> data = new ArrayList<>();
        for (String str : br.readLine().split(" ")) {
            data.add(Integer.parseInt(str));
        }


        PowerGridOptimization powerGridOptimization=new PowerGridOptimization(data);
        OptimalPowerGridSolution optimalPowerGridSolution=powerGridOptimization.getOptimalPowerGridSolutionDP();




        int sum = 0;
        for (int num : data) sum += num;

        System.out.println("The total number of demanded gigawatts: "+sum);
        System.out.println("Maximum number of satisfied gigawatts: "+optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");
        for (int i = 0; i < optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().size(); i++) {
            System.out.print(optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().get(i));
            if (i < optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().size() - 1) {System.out.print(", ");}
        }System.out.println();
        System.out.println("The number of unsatisfied gigawatts: "+ (sum-optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands()));

        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");













        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        BufferedReader br2 = new BufferedReader(new FileReader(args[1]));
        String[] firstRow=br2.readLine().split(" ");
        int eSVsNumber = Integer.parseInt(firstRow[0]);
        int esvCapacity= Integer.parseInt(firstRow[1]);


        ArrayList<Integer> maintenanceTaskEnergyDemands=new ArrayList<>();
        String[] secondRow= br2.readLine().split(" ");
        for (String value : secondRow){maintenanceTaskEnergyDemands.add(Integer.parseInt(value));}

        OptimalESVDeploymentGP optimalESVDeploymentGP = new OptimalESVDeploymentGP(maintenanceTaskEnergyDemands);
        int control = optimalESVDeploymentGP.getMinNumESVsToDeploy(eSVsNumber,esvCapacity);
        if (control==-1){System.out.println("Warning: Mission Eco-Maintenance Failed.");}
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }





}
