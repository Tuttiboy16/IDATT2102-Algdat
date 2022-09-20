public class Exponential2 {

    public static double calcExponent(double x, int n) {

        if (n == 0) {
            return 1;
        } else if (n % 2 == 0) {
            return calcExponent((x * x), n / 2);
        } else if (n % 2 == 1) {
            return x * calcExponent((x * x), (n - 1) / 2);
        } else {
            return 0;
        }

        /*
        Time complexity for this code is theta(n^^k * log(n)) because by using the recursion complexity, we can calculate
        a = 2 and b = 2. Which means b = a = T(n) = theta(n^^k * log(n)) = theta(log(n)).
         */
    }
}
