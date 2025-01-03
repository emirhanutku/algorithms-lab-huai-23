// Class representing the mission of Genesis
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filename);


            NodeList humanMolecules = document.getElementsByTagName("HumanMolecularData").item(0).getChildNodes();
            List<Molecule> finalHumanMolecules = new ArrayList<>();
           
            for (int i = 0; i < humanMolecules.getLength(); i++) {
                Node node = humanMolecules.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element element = (Element) node;
                    
                    String id = element.getElementsByTagName("ID").item(0).getTextContent();
                    int bondStrength = Integer.parseInt(element.getElementsByTagName("BondStrength").item(0).getTextContent());
                   
                    NodeList humanBonds = element.getElementsByTagName("Bonds").item(0).getChildNodes();
                    List<String> humanBondsList = new ArrayList<>();
                    for (int j = 0; j < humanBonds.getLength(); j++) {
                        Node bondNode = humanBonds.item(j);
                        if (bondNode.getNodeType() == Node.ELEMENT_NODE) {
                            String moleculeID = bondNode.getTextContent();
                            humanBondsList.add(moleculeID);
                            
                        }
                    }
                    Molecule newHumanMolecule = new Molecule(id,bondStrength,humanBondsList);
                    finalHumanMolecules.add(newHumanMolecule);
                    

                }

            }
            molecularDataHuman = new MolecularData(finalHumanMolecules);



            List<Molecule> finalVitalesMolecules = new ArrayList<>();
            NodeList vitalesMolecules = document.getElementsByTagName("VitalesMolecularData").item(0).getChildNodes();

            for (int i = 0; i < vitalesMolecules.getLength(); i++) {
                Node vitalesNode = vitalesMolecules.item(i);
                if (vitalesNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) vitalesNode;

                    String id = element.getElementsByTagName("ID").item(0).getTextContent();
                    int bondStrength = Integer.parseInt(element.getElementsByTagName("BondStrength").item(0).getTextContent());


                    NodeList vitalesBonds = element.getElementsByTagName("Bonds").item(0).getChildNodes();
                    List<String> vitalesBondsList = new ArrayList<>();
                    for (int j = 0; j < vitalesBonds.getLength(); j++) {
                        Node bondNode = vitalesBonds.item(j);
                        if (bondNode.getNodeType() == Node.ELEMENT_NODE) {
                            String moleculeID = bondNode.getTextContent();
                            vitalesBondsList.add(moleculeID);

                        }
                    }
                    Molecule newMolecule = new Molecule(id,bondStrength,vitalesBondsList);
                    finalVitalesMolecules.add(newMolecule);
                }
            }
            molecularDataVitales=new MolecularData(finalVitalesMolecules);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        
    }

