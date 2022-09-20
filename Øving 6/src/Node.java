public class Node {
    private int value;
    private Edge edge;
    private Predecessor predecessor;
    private TopoLst info;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Edge getEdge() {
        return edge;
    }

    public Predecessor getPredecessor() {
        return predecessor;
    }

    public TopoLst getInfo() {
        return info;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public void setPredecessor(Predecessor predecessor) {
        this.predecessor = predecessor;
    }

    public void setInfo(TopoLst info) {
        this.info = info;
    }
}


class Edge {
    private Edge next;
    private Node to;

    public Edge(Node to, Edge next) {
        this.next = next;
        this.to = to;
    }

    public Edge getNext() {
        return next;
    }

    public Node getTo() {
        return to;
    }
}


class Predecessor {
    private int dist;
    private Node predecessor;
    private static int infinite = 1000000000;

    public Predecessor() {
        dist = infinite;
    }

    public int getDist() {
        return dist;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public static int getInfinite() {
        return infinite;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }
}


class TopoLst {
    private boolean found = false;
    private Node[] next;

    public boolean isFound() {
        return found;
    }

    public Node[] getNext() {
        return next;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public void setNext(Node[] next) {
        this.next = next;
    }
}
