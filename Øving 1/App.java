public class App {
    public static void main(String[] args) {

        long startTime = System.nanoTime();


        System.out.println(StockProfit.find(ListGenerator.getList(100000)));

        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        System.out.println(duration);
    }
}
