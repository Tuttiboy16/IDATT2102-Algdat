import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    public boolean bfs(int source, int drain, Node[] lst) {
        Node sourceNode = this.nodes[source];
        CustomQueue customQueue = new CustomQueue(this.nodeCount);
        customQueue.addToQueue(sourceNode);
        sourceNode.setVisited(true);
        lst[source] = null;

        while(!customQueue.empty()) {
            Node start = (Node) customQueue.nextInQueue();

            for (Edge e = start.getEdge(); e != null; e = e.getNext()) {
                int value = e.getTotalCapacity();
                Node nextNode = e.getTo();

                if (!nextNode.isVisited() && value > 0) {
                    lst[nextNode.getValue()] = start;
                    nextNode.setVisited(true);
                    customQueue.addToQueue(nextNode);
                }
            }
        }

        return this.nodes[drain].isVisited();
    }

    public void edmundKarp(int source, int drain) {
        int maxFlow = 0;
        int flow = 100000000;
        Node node = null;
        Node[] nodesToPrint = new Node[nodeCount];
        Node[] nodeLst = new Node[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            nodesToPrint[i] = null;
        }

        System.out.println("Økning  :   Flytøkende vei");
        while(bfs(source, drain, nodeLst)) {
            for(int i = drain; i != source; i = nodeLst[i].getValue()) {
                node = this.nodes[i];
                for(Edge e = node.getEdge(); e != null; e = e.getNext()) {
                    if (e.getTotalCapacity() != 0) {  // e.getTo().getValue() == i &&
                        nodesToPrint[i] = this.nodes[i];
                        flow = Math.min(flow, e.getTotalCapacity());
                    }
                }
            }

            System.out.println(flow + "     " + printFlow(source, nodesToPrint));

            for(int i = drain; i != source; i = nodeLst[i].getValue()) {
                node = this.nodes[i];
                for(Edge e = node.getEdge(); e != null; e = e.getNext()) {
                    if (e.getTo().getValue() == i) {
                        e.updateUsedCapacity(flow, '-');
                    }
                }

                for(Edge e = node.getEdge(); e != null; e = e.getNext()) {
                    if (e.getTo().getValue() == node.getValue()) {
                        e.updateUsedCapacity(flow, '+');
                    }
                }
            }
            maxFlow += flow;
        }
        System.out.println("Max flow ble " + maxFlow);
    }

    public String printFlow(int source, Node[] printNodes){
        StringBuilder flow = new StringBuilder(source + " ");
        for (int i = 0; i < nodeCount; i++) {
            if (printNodes[i] != null){
                flow.append(this.nodes[i].getValue()).append(" ");
            }
        }
        return flow.toString();
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
                int capacity = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(this.nodes[to], this.nodes[from].getEdge(), capacity);

                if (edge.getOpposite() != null) {
                    Edge oppositeEdge = new Edge(this.nodes[from], this.nodes[to].getEdge(), 0);
                    edge.setOpposite(oppositeEdge);
                }

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
}
