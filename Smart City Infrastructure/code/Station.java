import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Station implements Serializable {
    static final long serialVersionUID = 55L;

    public Point coordinates;
    public String description;

    public double totalDuration ;
    public List<Station> beforeStations;




    public Station(Point coordinates, String description) {
        this.coordinates = coordinates;
        this.description = description;
    }

    public String toString() {
        return this.description;
    }
}
