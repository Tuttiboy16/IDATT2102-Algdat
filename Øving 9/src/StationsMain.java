
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StationsMain {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream(("noder.txt"));
        InputStream inputStream1 = new FileInputStream("kanter.txt");
        InputStream inputStream2 = new FileInputStream("interessepkt.txt");
        MapData mapData = new MapData();
        mapData.nodeInput(inputStream);
        System.out.println("Nodes read");
        mapData.edgeInput(inputStream1);
        System.out.println("Vertexes read");
        mapData.readInterestPoint(inputStream2);
        System.out.println(mapData.dijkstraDistStations(2524262).toString());
        mapData.writeMapData(10, 30);
    }
}
