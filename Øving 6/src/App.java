import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        int startNodeBFS = 5;
        Graph graphBFS = new Graph("L7g1.txt");
        Graph graphTopo = new Graph("L7g5.txt");

        ArrayList<Node> sortedArray = new ArrayList<>();

        // BFS
        for (Object obj : graphBFS.bfs(graphBFS.getNodes()[startNodeBFS])) {
            if (obj != null) {
                sortedArray.add((Node) obj);
            }
        }

        // Topologic sort
        System.out.printf("Topologic sort%n");
        for (Node n: graphTopo.topologicSorting()) {
            System.out.println(n.getValue());
        }
        System.out.println();

        sortedArray = (ArrayList<Node>) sortedArray.stream()
                .sorted(Comparator.comparing(Node::getValue))
                .collect(Collectors.toList());

        System.out.println("BFS");
        System.out.printf("Node     Pred    Dist%n");
        for (Node n: sortedArray) {
            if (!Objects.isNull(n) && !Objects.isNull(n.getPredecessor().getPredecessor())) {
                System.out.printf("%s       %s      %s%n", n.getValue(), n.getPredecessor().getPredecessor().getValue(), n.getPredecessor().getDist());
            } else {
                System.out.printf("%s       %s      %s%n", n.getValue(), " ", n.getPredecessor().getDist());
            }

        }


    }
}
