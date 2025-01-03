import java.util.*;

// Main class for running the program
public class Main {
    public static void main(String[] args) {


        Locale locale = new Locale("en_EN"); 
        Locale.setDefault(locale);


        System.out.println("### MISSION GENESIS START ###");
        

        MissionGenesis missionGenesis = new MissionGenesis();
        missionGenesis.readXML(args[0]);


        MolecularData humanData = missionGenesis.getMolecularDataHuman();
        MolecularData vitalesData = missionGenesis.getMolecularDataVitales();


        List<MolecularStructure> molecularStructuresHuman = humanData.identifyMolecularStructures();
        List<MolecularStructure> molecularStructuresVitales = vitalesData.identifyMolecularStructures();


        humanData.printMolecularStructures(molecularStructuresHuman, "typical humans");
        vitalesData.printMolecularStructures(molecularStructuresVitales, "Vitales individuals");


        ArrayList<MolecularStructure> anomalies = MolecularData.getVitalesAnomaly(molecularStructuresHuman, molecularStructuresVitales);

        humanData.printVitalesAnomaly(anomalies);



        System.out.println("### MISSION GENESIS END ###");


        // Start of Mission Synthesis
        System.out.println("### MISSION SYNTHESIS START ###");

        // Initialize MissionSynthesis with human molecular structures and anomalies

        MissionSynthesis missionSynthesis = new MissionSynthesis(molecularStructuresHuman, anomalies);
        
        // Synthesize bonds for the serum
        List<Bond> synthesis = missionSynthesis.synthesizeSerum();
        
        // Print the synthesized bonds
        missionSynthesis.printSynthesis(synthesis);
        
        // End of Mission Synthesis
        System.out.println("### MISSION SYNTHESIS END ###");
    }
}
