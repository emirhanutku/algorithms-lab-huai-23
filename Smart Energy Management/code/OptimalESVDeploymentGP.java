import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        Collections.sort(maintenanceTaskEnergyDemands,Collections.reverseOrder());
        if (maintenanceTaskEnergyDemands.get(0)>maxESVCapacity){return -1;}

        ArrayList<Integer> remaining = new ArrayList<>();
        for (int j=0 ; j<maxNumberOfAvailableESVs;j++){remaining.add(maxESVCapacity);}



        for (int i = 0; i < maintenanceTaskEnergyDemands.size(); i++) {
            for (int k = 0; k <remaining.size(); k++) {
                if (remaining.get(k)>=maintenanceTaskEnergyDemands.get(i)){
                    if (remaining.get(k)==maxESVCapacity){maintenanceTasksAssignedToESVs.add(new ArrayList<>());}
                    remaining.set(k,remaining.get(k)-maintenanceTaskEnergyDemands.get(i));
                    maintenanceTasksAssignedToESVs.get(k).add(maintenanceTaskEnergyDemands.get(i));
                    break;
                }

            }
        }
        int totalTask = 0;
        for (ArrayList<Integer> innerList : maintenanceTasksAssignedToESVs) {
            totalTask += innerList.size();
        }

        if (totalTask<maintenanceTaskEnergyDemands.size()){return -1;}
        System.out.println("The minimum number of ESVs to deploy: " +maintenanceTasksAssignedToESVs.size() );

        for (int l = 0; l < maintenanceTasksAssignedToESVs.size(); l++) {
            System.out.println("ESV " + (l+1) +" tasks: " + Arrays.toString(maintenanceTasksAssignedToESVs.get(l).toArray()));

        }


        return maintenanceTasksAssignedToESVs.size();
    }

}
