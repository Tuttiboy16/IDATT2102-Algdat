import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class MainCompress {
    public static void main(String[] args) throws IOException {
        String path = "./diverse.txt";
        Compression compression = new Compression();
        byte[] input = compression.LZ77(path);
        File file = new File(path);
        Files.write(file.toPath(), input);


        String path1 = "./diverse.txtcompressed";
        File file1 = new File(path1);

//        ArrayList<Byte> compressed = compression.decompress(file1.toPath().toString());
//        byte[] compressedArr = new byte[compressed.size()];

//        for (int i = 0; i<compressed.size();i++){
//            if(i>=input.length)break;
//            compressedArr[i] = input[i];
//        }
//
//        System.out.println(Arrays.toString(input));
//        System.out.println(Arrays.toString(compressed.toArray()));
//        for (int i = 0; i < 300; i++){
//            System.out.printf("%nArray: %s, Value: %s, Index: %s%n", "compressed", compressed.get(i), i);
//            System.out.printf("Array: %s, Value: %s, Index: %s%n", "input", input[i], i);
//        }





    }
}
