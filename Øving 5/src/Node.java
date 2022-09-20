public class Node<T> {
    private T value;
    private Node nextNode;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}
