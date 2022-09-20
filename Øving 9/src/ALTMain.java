
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ALTMain {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream(("noder.txt"));
        InputStream inputStream1 = new FileInputStream("kanter.txt");
        MapData mapData = new MapData();
        InputStream inputStream2 = new FileInputStream("interessepkt.txt");
        mapData.nodeInput(inputStream);
        System.out.println("Nodes read");
        mapData.edgeInput(inputStream1);
        System.out.println("Vertexes read");
        mapData.readInterestPoint(inputStream2);

        InputStream inputStream3 = new FileInputStream("PreFileFrom.txt");
        InputStream inputStream4 = new FileInputStream("PreFileTo.txt");
        mapData.readPreProcessedFileFromLandmark(inputStream3);
        mapData.readPreProcessedFileToLandmark(inputStream4);
        System.out.println(mapData.alt(1,1000000));
    }
}
