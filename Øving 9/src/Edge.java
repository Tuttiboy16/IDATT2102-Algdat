
public class Edge {
    int weight;
    int from, to;

    public Edge(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.weight = cost;
    }

    public int getWeight() {
        return weight;
    }
}
