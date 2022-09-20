public class HashTable {
    private Node[] nodes;

    public HashTable(int m) {
        this.nodes = new Node[m];
    }

    public Node[] insertNode(Node node, int index) {
        Node[] collision = new Node[2];

        if (this.nodes[index] != null) {
            collision[0] = this.nodes[index];
            collision[1] = node;
            collisionHandler(this.nodes[index], node);
        } else {
            collision = null;
            this.nodes[index] = node;
        }

        return collision;
    }

    public Node<String> search(String st) {
        int pos = divHash(stringToAscii(st));

        Node nodeToReturn = null;

        if (!nodes[pos].getValue().equals(st)) {
            Node currentNode = nodes[pos];

            while (currentNode.getNextNode() != null) {
                currentNode = currentNode.getNextNode();
                if (currentNode.getValue().equals(st)) {
                    nodeToReturn = currentNode;
                }
            }
        } else if (nodes[pos].getValue().equals(st)){
            nodeToReturn = nodes[pos];
        }

        return nodeToReturn;
    }

    public int divHash(int k) {
        return k % nodes.length;
    }

    public int stringToAscii(String st) {
        int stringAscii = 0;

        for (int i = 0; i < st.length(); i++) {
            stringAscii += ((int) st.charAt(i)) * (i + 1);
        }

        return stringAscii;
    }

    public void collisionHandler(Node nodeAlreadyInList, Node collidingNode) {
        Node currentNode = nodeAlreadyInList;
        while(currentNode.getNextNode() != null) {
            currentNode = currentNode.getNextNode();
        }
        currentNode.setNextNode(collidingNode);
    }

    public Node[] getNodes() {
        return nodes;
    }
}
