import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AppDeloppgave1 {
    public static void main(String[] args) throws IOException {
        String filename = "navn.txt";
        String searchWord = "Anders Tellefsen";

        ArrayList<String> file = ReadFile.read(filename);

        double loadFactor = (double) file.size() / 151;

        HashTable hashTable = new HashTable(151);
        ArrayList<String[]> totalCollisions = new ArrayList<String[]>();

        for (String st : file) {
            Node<String> newNode = new Node(st);
            int stringAsAscii = hashTable.stringToAscii(newNode.getValue());
            int pos = hashTable.divHash(stringAsAscii);

            Node[] collision = hashTable.insertNode(newNode, pos);

            if (!Objects.isNull(collision)) {
                String[] collided = {(String) collision[0].getValue(), (String) collision[1].getValue()};
                totalCollisions.add(collided);
            }
        }

        double meanCollisionPerPerson = (double) totalCollisions.size() / hashTable.getNodes().length;


        System.out.println(hashTable.search(searchWord).getValue());


        System.out.println("\nLoad factor: " + loadFactor);
        System.out.println("Mean collisions per person: " + meanCollisionPerPerson);
        System.out.println("Number of collisions during insertion: " + totalCollisions.size());

        System.out.println("Every collision:\n");

        for (String[] st: totalCollisions) {
            System.out.println(Arrays.toString(st));
        }
    }
}