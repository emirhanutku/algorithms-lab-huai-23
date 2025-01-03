import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();

        ArrayList<Molecule> moleculeStructure =new ArrayList<>();



        for(Molecule molecule : molecules){
            boolean control = false;
            if (moleculeStructure.isEmpty()){
                moleculeStructure.add(molecule);
                continue;}

            for (Molecule molecule1 : moleculeStructure){

                if (  molecule1.getBonds().contains(molecule.getId()) || molecule.getBonds().contains(molecule1.getId()) || !Collections.disjoint(molecule1.getBonds(), molecule.getBonds())) {
                    control = true;
                    break;
                }
            }
            if (control ){
                moleculeStructure.add(molecule);}
            else {
                MolecularStructure molecularStructure = new MolecularStructure();
                for (Molecule molecule1 : moleculeStructure){
                    molecularStructure.addMolecule(molecule1);
                }
                structures.add(molecularStructure);
                moleculeStructure.clear();
                moleculeStructure.add(molecule);
            }

        }
        if (!moleculeStructure.isEmpty()){
            MolecularStructure molecularStructure = new MolecularStructure();
            for (Molecule molecule1 : moleculeStructure){
                molecularStructure.addMolecule(molecule1);
            }
            structures.add(molecularStructure);
        }

        return structures;
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {

        System.out.println(molecularStructures.size()+" molecular structures have been discovered in " + species +"." );
        for (int i = 0; i < molecularStructures.size(); i++) {
            System.out.print("Molecules in Molecular Structure " + (i+1) + ": " + molecularStructures.get(i).toString());
            System.out.println();
        }

        
        /* YOUR CODE HERE */ 

    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure  targetMolecularStructures: targeStructures){
            boolean control = false;
            for (MolecularStructure sourceMolecularStructures : sourceStructures){
                if (targetMolecularStructures.equals(sourceMolecularStructures)) {
                    control = true;
                    break;
                }
            }
            if (!control){
                anomalyList.add(targetMolecularStructures);
                System.out.println(targetMolecularStructures.toString());
            }
        }
        
        /* YOUR CODE HERE */ 

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {

        /* YOUR CODE HERE */ 

    }
}
