import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WeightedDirectedGraph {
    public static class Edge {
        int to, weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

    }

    private int n, k, numEdge;
    private List<List<Edge>> graphTable;

    public WeightedDirectedGraph(BufferedReader br) throws IOException {
        createGraph(br);
    }

    public void createGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        graphTable = new ArrayList<>(n);

        for (int i = 0; i < this.n; i++) {
            graphTable.add(new ArrayList<>());
        }
        k = Integer.parseInt(st.nextToken());

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            addEdge(from, to, weight);
            numEdge +=2;
        }
    }

    public void addEdge(int vertexFrom, int vertexTo, int weight) {
        if (vertexFrom < n && vertexFrom >= 0 && weight > 0 && vertexTo < n) {
            Edge edge1 = new Edge(vertexTo, weight);
            Edge edge2 = new Edge(vertexFrom, 0);
            graphTable.get(vertexFrom).add(edge1);
            graphTable.get(vertexTo).add(edge2);
        }
    }

    public boolean bfs(int start, int sink, int[] nodeList) {
        boolean[] visited = new boolean[n];
        LinkedList<Integer> queue = new LinkedList<>();
        nodeList[start] = -1;
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            start = queue.poll();

            for (Edge edge : graphTable.get(start)) {
                int val = edge.weight;
                int node = edge.to;

                if (!visited[edge.to] && val > 0) {
                    visited[edge.to] = true;
                    nodeList[node] = start;
                    queue.push(node);
                }
            }
        }
        return visited[sink];
    }

    public void edmondKarp(int start, int sink){
        int maxFlow = 0;
        int[] nodeList = new int[n];
        int[] printNodes = new int[n];
        for (int i = 0; i < n; i++) {
            printNodes[i] = -1;
        }
        int j;
        int flow = Integer.MAX_VALUE;
        System.out.println("Økning  : Flytøkende vei");
        while(bfs(start, sink, nodeList)){
            for(int i = sink; i != start; i = nodeList[i]){
                j = nodeList[i];
                for (Edge edge : graphTable.get(j)) {
                    if(edge.to == i && edge.weight != 0){
                        printNodes[i] = i;
                        flow = Math.min(flow, edge.weight);
                    }
                }
            }
            System.out.println(flow + "         " + printFlow(start, printNodes));

            for(int i = sink; i != start; i = nodeList[i]){
                j = nodeList[i];
                for (Edge edge : graphTable.get(j)) {
                    if(edge.to == i){
                        edge.weight -= flow;
                    }
                }
                for (Edge edge : graphTable.get(i)) {
                    if(edge.to == j){
                        edge.weight += flow;
                    }
                }
            }
            maxFlow += flow;
        }
        System.out.println("Max flow ble " + maxFlow);
    }

    public String printFlow(int start, int[] printNodes){
        StringBuilder print = new StringBuilder(start + " ");
        for (int i = 0; i < n; i++) {
            if (printNodes[i]>=0){
                print.append(i).append(" ");
            }
        }
        return print.toString();
    }

    public static void main(String[] args) throws IOException {
        WeightedDirectedGraph graph;
        FileReader file = new FileReader("./flytgraf1.txt");
        BufferedReader br = new BufferedReader(file);
        graph = new WeightedDirectedGraph(br);

        System.out.println("Maksimum flyt graf 3 fra start 0 til sluk 15 med Edmond Karp: ");
        graph.edmondKarp(0, 7);
    }
}