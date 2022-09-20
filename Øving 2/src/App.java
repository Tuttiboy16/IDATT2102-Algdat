public class App {
    public static void main(String[] args) {

        int n = 0;
        int r = 10;

        // Time complexity o(n)
        long startTime = System.nanoTime();
        for (int i = 0; i < r; i++) {
            Exponential1.calcExponent(1.000001, n);
            n++;
        }
        long endTime = System.nanoTime();

        // Time complexity theta(log(n))
        long startTime2 = System.nanoTime();
        for (int k = 0; k < r; k++) {
            Exponential2.calcExponent(1.000001, n);
            n++;
        }
        long endTime2 = System.nanoTime();

        // Time complexity O(1)
        long startTime3 = System.nanoTime();
        for (int j = 0; j < r; j++) {
            Math.pow(1.000001, n);
            n++;
        }
        long endTime3 = System.nanoTime();

        long duration3 = endTime3 - startTime3;
        long duration2 = endTime2 - startTime2;
        long duration = endTime - startTime;

        System.out.println("Oppg 1 målt i nanosekunder: " + duration);
        System.out.println("Oppg 2 målt i nanosekunder: " + duration2);
        System.out.println("Pow målt i nanosekunder: " + duration3);


    }
}
