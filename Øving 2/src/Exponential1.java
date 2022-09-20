public class Exponential1 {

    public static double calcExponent(double x, int n) {

        if (n == 0) {
            return 1;
        } else if (n >= 1) {
            return x * calcExponent(x, (n - 1));
        } else {
            return 0;
        }

        /*
        The time complexity here is supposed to be O(n) by following the rules of complexity by recursion.
        a = 1, b = n - 1 = -1, T(n) = n^^log(1) = O(n) 
         */
    }
}
