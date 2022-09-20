import java.util.Collections;
import java.util.Random;

public class AppDeloppgave2 {
    public static void main(String[] args) {
        int m = 12003391; //  13003399  //  12003391  //  11003099  //  130003733
        float degreeOfFilling = 0.8f;
        int[] table = new int[m];

        Random random = new Random();

        for (int i = 0; i < table.length; i++) {
            if (i == 0) {
                table[i] = random.nextInt(1000);
            } else {
                table[i] = table[i - 1] + random.nextInt(1000);
            }
        }
        Collections.shuffle(Collections.singletonList(table));

        Linear linear = new Linear(m);
        Square square = new Square(m);
        Dubble dubble = new Dubble(m);

        int collisionsLinear = 0;
        long startTimeLinear = System.nanoTime();
        for (int i = 0; i < table.length * degreeOfFilling; i++) {
            int pos = linear.hash(table[i]);
            int collisions = linear.insert(table[i], pos);

            if (collisions != 0) {
                collisionsLinear += collisions;
            }
        }
        long endTimeLinear = System.nanoTime();

        int collisionsSquare = 0;
        long startTimeSquare = System.nanoTime();
        for (int i = 0; i < table.length * degreeOfFilling; i++) {
            int pos = square.hash(table[i]);
            int collisions = square.insert(table[i], pos);

            if (collisions != 0) {
                collisionsSquare += collisions;
            }
        }
        long endTimeSquare = System.nanoTime();

        int collisionsDouble = 0;

        long startTimeDubble = System.nanoTime();
        for (int i = 0; i < table.length * degreeOfFilling; i++) {
            int pos = dubble.hash1(table[i]);
            int collisions = dubble.insert(table[i], pos);

            if (collisions != 0) {
                collisionsDouble += collisions;
            }
        }
        long endTimeDubble = System.nanoTime();

        long durationLinear = (endTimeLinear - startTimeLinear) / 1000000;
        long durationSquare = (endTimeSquare - startTimeSquare) / 1000000;
        long durationDouble = (endTimeDubble - startTimeDubble) / 1000000;

        float loadFactor = (table.length * degreeOfFilling) / m;

        System.out.println("\nLoad factor: " + loadFactor);

        System.out.println("\nDuration linear: " + durationLinear + "ms");
        System.out.println("Number of collisions in the linear probe: " + collisionsLinear);

        System.out.println("\nDuration square: " + durationSquare + "ms");
        System.out.println("Number of collisions in the square probe: " + collisionsSquare);

        System.out.println("\nDuration dubble: " + durationDouble + "ms");
        System.out.println("Number of collisions in the double probe: " + collisionsDouble);

    }
}
