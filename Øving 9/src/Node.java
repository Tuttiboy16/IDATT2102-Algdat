
import java.util.ArrayList;
import java.util.List;

public class Node {
    int nr;
    String name;
    int value;
    int code;
    int cost = Integer.MAX_VALUE;
    int parent;
    boolean visited = false;

    double latitude;
    double longitude;

    int distanceToTarget = -1;
    int sumOfValues = 0;

    List<Edge> edges = new ArrayList<>();
    List<Edge> flippedEdges = new ArrayList<>();

    public Node(int nr) {
        this.nr = nr;
        this.value = 0;
    }

    public Node(int nr, int value) {
        this.nr = nr;
        this.value = value;
    }

    public Node(int nr, double longitude, double latitude) {
        this.nr = nr;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Node: " + name +
                ", nr " + nr +
                ", " + (float)cost/6000 + " minutes away " +
                ", code " + code +
                ", (latitude, longitude) " + latitude +
                ", " + longitude + "\n";
    }

}