public class Deloppgave_1 {
    private Node head = null;
    private Node tail = null;

    public void addNode(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            tail.setNextNode(newNode);
            newNode.setPrevNode(tail);
        }

        tail = newNode;
        tail.setNextNode(head);
        head.setPrevNode(tail);
    }

    public int deleteNodes(int valueToDeleteAt) {
        Node currentNode = head;

        if (head == null) {
            return -1;
        }

        int counter = 1;
        do {
            Node nextNode = currentNode.getNextNode();
            Node prevNode = currentNode.getPrevNode();
            if (counter % valueToDeleteAt == 0) {
                nextNode.setPrevNode(prevNode);
                prevNode.setNextNode(nextNode);
                if (head == currentNode) {
                    head = nextNode;
                    nextNode.setPrevNode(tail);
                }

                if (tail == currentNode) {
                    tail = prevNode;
                    prevNode.setNextNode(head);
                }
            }
            currentNode = nextNode;
            counter++;

        } while (head.getNextNode() != head);
        return head.getValue();
    }
}

class Node {
    private int value;
    private Node nextNode;
    private Node prevNode;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }
}
