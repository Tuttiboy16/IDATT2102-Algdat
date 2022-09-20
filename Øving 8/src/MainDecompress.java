import java.io.File;
import java.io.IOException;

public class MainDecompress {
    public static void main(String[] args) throws IOException {
        Compression compression = new Compression();
        File file = new File("./diverse.txtcompressed");
        compression.decompress(file.toPath().toString());
    }
}
