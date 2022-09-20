import java.util.Arrays;
import java.util.Random;

public class Oving_3 {

    public static int n = 1000000;

    public static void main(String[] args) {

        int[] t = generateTable(n);
        int[] copiedTable = t.clone();

        System.out.println("Sum of original table: " + summer(t));
        System.out.println("Sum of copied table: " + summer(copiedTable) + "\n");

        long startTimeWithInsertion = System.nanoTime();
        quickSortWithInsertion(t, 0, t.length-1);
        long endTimeWithInsertion = System.nanoTime();
        long timeWithInsertion = endTimeWithInsertion - startTimeWithInsertion;

        long startTime = System.nanoTime();
        quickSort(copiedTable, 0, copiedTable.length-1);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("Check sum of original table: " + summer(t));
        System.out.println("Sum of copied table: " + summer(copiedTable) + "\n");
        System.out.println(testSort(t));
        System.out.println(testSort(copiedTable));
        System.out.println("Quicksort with insertion as helping algorithm uses " + (timeWithInsertion/1000000) + " ms");
        System.out.println("Quicksort algorithm uses " + (time/1000000) + " ms");
    }

    public static void quickSortWithInsertion(int[] t, int v, int h) {
        while (v < h) {
            if (h - v < 32) {
                insertionSort(t, v, h);
                break;
            } else {
                int pivot = partition(t, v, h);

                if (pivot - v < pivot - h) {
                    quickSortWithInsertion(t, v, pivot - 1);
                    v = pivot +1;
                }else {
                    quickSortWithInsertion(t, pivot + 1, h);
                    h = pivot + 1;
                }
            }
        }

    }

    public static void quickSort(int[] t, int v, int h) {
        if (v < h) {
            int pivot = partition(t, v, h);
            quickSort(t, v, pivot - 1);
            quickSort(t, pivot + 1, h);
        }
    }

    private static int median3sort(int[] t, int v, int h) {
        int m = (v + h) / 2;
        if (t[v] > t[m]) swap(t,v,m);
        if (t[m] > t[h]) {
            swap(t,m,h);
            if(t[v] > t[m]) swap(t,v,m);
        }
        return m;
    }

    private static void insertionSort(int[] t, int fra, int til) {
        for (int j = fra; j < til; j++) {
            int bytt = t[j];

            // Place t[j] in correct place
            int i = j - 1;
            while (i >= 0 && t[j] > bytt) {
                t[i + 1] = t[i];
                i--;
            }

            t[i + 1] = bytt;
        }
    }

    private static int partition(int[] t, int v, int h) {
        int iv, ih;
        int m = median3sort(t, v, h);
        int dv = t[m];
        swap(t, m, h - 1);
        for (iv = v, ih = h - 1; ; ) {
            while (t[++iv] < dv) ;
            while (t[--ih] > dv) ;
            if (iv >= ih) break;
            swap(t, iv, ih);
        }
        swap(t, iv, h - 1);
        return iv;
    }

    private static void swap(int[] t, int a, int b) {
        int temp = t[a];
        t[a] = t[b];
        t[b] = temp;
    }

    public static int summer(int[] t){
        int sum = 0;
        for (int i = 0; i < t.length; i++){
            sum += t[i];
        }
        return sum;
    }

    private static int[] generateTable(int n) {
        int[] t = new int[n];
        Random random = new Random();
        for (int i = 0; i < t.length; i++) {
            t[i] = random.nextInt(100);
        }
        return t;
    }

    private static String testSort(int[] t) {
        for (int i = 0; i < t.length - 2; i++) {
            if (t[i] > t[i+1]) {
                return "The tabel is not sorted";
            }
        }
        return "The tabel is sorted";
    }

}
