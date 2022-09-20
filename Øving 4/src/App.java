import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {

        // Deloppgave 1

       int n = 1000000;
       int valueToDeleteAt = 4;

       Deloppgave_1 circularLinkedList = new Deloppgave_1();

       // fill list with n nodes
       for (int i = 1; i <= n; i++) {
           circularLinkedList.addNode(i);
       }

       System.out.println("Last man standing was number: " + circularLinkedList.deleteNodes(valueToDeleteAt));



        // Deloppgave 2
        String fileToCheck = "test.txt";

        String st = Deloppgave_2.removeCharAndSpaces(Deloppgave_2.readFile(fileToCheck));
        
        if (Deloppgave_2.parenthesesChecker(st)) {
            System.out.println("The file is OK.");
        } else {
            System.out.println("The file is NOT OK.");    
        }
    }
}
