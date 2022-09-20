public class Node {
    private int value;
    private Edge edge;
    private boolean visited;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Edge getEdge() {
        return edge;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}


class Edge {
    private Edge next;
    private Edge opposite;
    private Node to;
    private int totalCapacity;
    private int usedCapacity = 0;

    public Edge(Node to, Edge next, int totalCapacity) {
        this.next = next;
        this.to = to;
        this.totalCapacity = totalCapacity;
    }

    public Edge getNext() {
        return next;
    }

    public Node getTo() {
        return to;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getUsedCapacity() {
        return usedCapacity;
    }

    public Edge getOpposite() {
        return opposite;
    }

    public void updateUsedCapacity(int usedCapacity, char operation) {
        switch (operation) {
            case '+' -> this.usedCapacity += usedCapacity;
            case '-' -> this.usedCapacity -= usedCapacity;
        }

    }

    public boolean isFull() {
        return getUsedCapacity() == getTotalCapacity();
    }

    public void setOpposite(Edge opposite) {
        this.opposite = opposite;
    }
}
