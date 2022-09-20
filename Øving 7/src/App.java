import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph("./src/flytgraf1.txt");
        graph.edmundKarp(0, 7);
    }
}
