import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    public static ArrayList<String> read(String filename) throws IOException {
        ArrayList<String> fileAsStrings = new ArrayList<String>();

        try {
            File file = new File(filename);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String input;
            while((input = br.readLine()) != null) {
                fileAsStrings.add(input);
            }

            br.close();
        } catch (IOException e) {
            throw new IOException(e);
        }

        return fileAsStrings;
    }
}
