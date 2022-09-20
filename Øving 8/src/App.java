import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class App {
    public static void main(String[] args) throws IOException {
        String path = "./diverse2.txt";
        Compression compression = new Compression();
        File file = new File("komprimert.txt");
        Files.write(file.toPath(), compression.LZ77(path));
        compression.decompress(path);
    }
}
