import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    private  List<Molecule> selectedVitalesMolecule = new ArrayList<>();

    private  List<Molecule> selectedHumanMolecule = new ArrayList<>();

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        List<Molecule> weakestBondStrengthMolecules = new ArrayList<>();

        List<Bond> allBonds = new ArrayList<>();

        for (MolecularStructure humanMolecules : humanStructures){
            selectedHumanMolecule.add(humanMolecules.getMoleculeWithWeakestBondStrength());
            weakestBondStrengthMolecules.add(humanMolecules.getMoleculeWithWeakestBondStrength());

        }


        for (MolecularStructure vitalesMolecules : diffStructures){
            selectedVitalesMolecule.add(vitalesMolecules.getMoleculeWithWeakestBondStrength());
            weakestBondStrengthMolecules.add(vitalesMolecules.getMoleculeWithWeakestBondStrength());

        }


        for (int i = 0; i < weakestBondStrengthMolecules.size(); i++) {
            for (int j = i+1; j <weakestBondStrengthMolecules.size(); j++) {

                allBonds.add(
                        weakestBondStrengthMolecules.get(i).compareTo(weakestBondStrengthMolecules.get(j)) > 0 ?
                                new Bond(weakestBondStrengthMolecules.get(i), weakestBondStrengthMolecules.get(j), (weakestBondStrengthMolecules.get(i).getBondStrength() + weakestBondStrengthMolecules.get(j).getBondStrength()) / 2.0) :
                                new Bond(weakestBondStrengthMolecules.get(j), weakestBondStrengthMolecules.get(i), (weakestBondStrengthMolecules.get(i).getBondStrength() + weakestBondStrengthMolecules.get(j).getBondStrength()) / 2.0)
                );


            }
        }







        Comparator<Bond> bondComparator = Comparator.comparing(Bond::getWeight);
        Collections.sort(allBonds, bondComparator);



        for (Bond bond : allBonds){
            if (serum.size()==weakestBondStrengthMolecules.size()-1){break;}

            serum.add(bond);
            bond.getFrom().adjList.add(bond.getTo());
            bond.getTo().adjList.add(bond.getFrom());

            boolean hasCycle = hasCycle(serum);
            if (hasCycle){
                serum.remove(bond);
                bond.getTo().adjList.remove(bond.getFrom());
                bond.getFrom().adjList.remove(bond.getTo());
            }

        }






        return serum;
    }


    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: " + selectedHumanMolecule);
        System.out.println("Vitales molecules selected for synthesis: " + selectedVitalesMolecule);
        System.out.println("Synthesizing the serum...");

        selectedHumanMolecule.clear();
        selectedVitalesMolecule.clear();
        double totalWeight = 0 ;
        for (Bond bond : serum){
            totalWeight +=bond.getWeight();
            System.out.println("Forming a bond between "+bond.getFrom()+" - "+bond.getTo()+" with strength "+String.format("%.2f", bond.getWeight()));
        }
        System.out.println("The total serum bond strength is "+String.format("%.2f", totalWeight));


    }



    public boolean hasCycle(List<Bond> bonds) {
        Set<Molecule> visited = new HashSet<>();
        for (Bond bond : bonds) {
            if (!visited.contains(bond.getTo())) {
                if (hasCycleUtil(bond.getTo(), visited, null)) {
                    return true;
                }
            }
        }
        return false;
    }




    private static boolean hasCycleUtil(Molecule current, Set<Molecule> visited, Molecule parent) {
        visited.add(current);
        for (Molecule neighbor : current.adjList) {
            if (!visited.contains(neighbor)) {
                if (hasCycleUtil(neighbor, visited, current)) {
                    return true;
                }
            } else if (!neighbor.equals(parent)) {
                return true;
            }
        }
        return false;
    }




}

