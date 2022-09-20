import java.util.Random;

public class ListGenerator {
    public static int[] getList(int size) {
        int[] listOfNumbers = new int[size];

        Random randomNumber = new Random();

        for (int i = 0; i < size; i++) {
            int num = randomNumber.nextInt(21) - 10;
            listOfNumbers[i] = num;
        }

        return listOfNumbers;
    }
}
