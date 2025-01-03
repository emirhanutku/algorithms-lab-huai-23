import java.io.Serializable;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project project : projectList){
            int[] startTimes = project.getEarliestSchedule();
            project.printSchedule(startTimes);


        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        try{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filename);

            Element root = doc.getDocumentElement();
            NodeList projectNodes = root.getElementsByTagName("Project");
            for (int i = 0; i < projectNodes.getLength(); i++) {
                Element projectElement = (Element) projectNodes.item(i);
                String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();
                List<Task> tasks = new ArrayList<>();



                NodeList taskNodes = projectElement.getElementsByTagName("Task");
                for (int j = 0; j < taskNodes.getLength(); j++) {
                    Element taskElement = (Element) taskNodes.item(j);
                    String taskID = taskElement.getElementsByTagName("TaskID").item(0).getTextContent();
                    String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();
                    String duration = taskElement.getElementsByTagName("Duration").item(0).getTextContent();
                    List<Integer> dependencies = new ArrayList<>();



                    NodeList dependencyNodes = taskElement.getElementsByTagName("DependsOnTaskID");
                    if (dependencyNodes.getLength() > 0) {

                        for (int k = 0; k < dependencyNodes.getLength(); k++) {

                            dependencies.add(Integer.parseInt(dependencyNodes.item(k).getTextContent()));
                        }

                    }
                    Task newTask = new Task(Integer.parseInt(taskID),description,Integer.parseInt(duration),dependencies);
                    tasks.add(newTask);
                }
                Project newProject = new Project(projectName,tasks);
                projectList.add(newProject);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return projectList;
    }
}
