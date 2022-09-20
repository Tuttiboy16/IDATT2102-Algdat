public class StockProfit {

    public static String find(int[] lst) {

        int earning = 0;

        int currentHighest = 0;

        int buy = 0;
        int sell = 0;

        for (int i = 0; i < lst.length; i++) {

            earning = 0;
            for (int j = i; j < lst.length; j++) {

                earning += lst[j];

                if (earning > currentHighest) {
                    currentHighest = earning;
                    buy = i;
                    sell = j + 1;
                }

            }
        }

        return "Buy: " + buy + ", Sell: " + sell + ", Earned: " + Math.abs(earning);
    }
}