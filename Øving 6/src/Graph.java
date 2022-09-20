import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;
import java.util.StringTokenizer;

public class Graph {
    private int nodeCount;
    private int edgeCount;
    private Node[] nodes;

    public Graph(String filename) throws IOException {
        int[] arr = readFile(filename);
        this.nodeCount = arr[0];
        this.edgeCount = arr[1];
    }

    public Object[] bfs(Node start) {
        initPredecessor(start);
        CustomQueue customQueue = new CustomQueue(this.nodeCount);
        customQueue.addToQueue(start);

        while(!customQueue.empty() && !customQueue.full()) {
            Node node = (Node) customQueue.nextInQueue();
            for (Edge e = node.getEdge(); e != null; e = e.getNext()) {
                Predecessor pre = e.getTo().getPredecessor();
                if (pre.getDist() == Predecessor.getInfinite()) {
                    pre.setDist(node.getPredecessor().getDist() + 1);
                    pre.setPredecessor(node);
                    if (!Objects.isNull(e.getTo())) {
                        customQueue.addToQueue(e.getTo());
                    }
                }
            }
        }

        return customQueue.getTab();
    }

    public void initTopo() {
        for (Node n: this.nodes) {
            n.setInfo(new TopoLst());
        }
    }

    public void dfs(Node startNode, Stack<Node> stack) {

        startNode.getInfo().setFound(true);

        for (Edge edge = startNode.getEdge(); edge != null; edge = edge.getNext()) {
            if(!edge.getTo().getInfo().isFound())dfs(edge.getTo(), stack);
        }
        stack.push(startNode);
    }

    public Stack<Node> topologicSorting() {
        initTopo();
        Stack<Node> nodeStack = new Stack<Node>();
        for (int i = 0; i < nodes.length; i++){
            if(!nodes[i].getInfo().isFound()) {
                dfs(nodes[i], nodeStack);
            }
        }
        return nodeStack;
    }

    public void initPredecessor(Node node) {
        for (int i = 0; i < this.nodeCount; i++) {
            this.nodes[i].setPredecessor(new Predecessor());
        }
        node.setPredecessor(new Predecessor());
        node.getPredecessor().setDist(0);
    }

    public int[] readFile(String filename) throws IOException {
        BufferedReader br = null;

        int[] count = new int[2];
        try {
            File file = new File(filename);
            br = new BufferedReader(new FileReader(file));
            StringTokenizer st = new StringTokenizer(br.readLine());
            int numberOfNodes = Integer.parseInt(st.nextToken());
            count[0] = numberOfNodes;

            this.nodes = new Node[numberOfNodes];

            for (int i = 0; i < numberOfNodes; i++) {
                this.nodes[i] = new Node(i);
            }

            int numberOfEdges = Integer.parseInt(st.nextToken());
            count[1] = numberOfEdges;

            for (int i = 0; i < numberOfEdges; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(this.nodes[to], this.nodes[from].getEdge());
                this.nodes[from].setEdge(edge);
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            assert br != null;
            br.close();
        }
        return count;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
