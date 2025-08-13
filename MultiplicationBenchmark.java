import java.util.Random;
import java.util.Arrays;

public class MultiplicationBenchmark {
    // Contadores globais de iterações
    static long mergeSortIters = 0;
    static long maxVal1Iters = 0;
    static long maxVal2Iters = 0;
    static long multiplyIters = 0;

    // Merge Sort com contagem de iterações
    public static void mergeSort(long[] arr) {
        mergeSortIters = 0;
        mergeSortRec(arr, 0, arr.length - 1, new long[arr.length]);
    }
    private static void mergeSortRec(long[] arr, int left, int right, long[] aux) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSortRec(arr, left, mid, aux);
        mergeSortRec(arr, mid + 1, right, aux);
        merge(arr, left, mid, right, aux);
    }
    private static void merge(long[] arr, int left, int mid, int right, long[] aux) {
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            mergeSortIters++;
            if (arr[i] <= arr[j]) aux[k++] = arr[i++];
            else aux[k++] = arr[j++];
        }
        while (i <= mid) { mergeSortIters++; aux[k++] = arr[i++]; }
        while (j <= right) { mergeSortIters++; aux[k++] = arr[j++]; }
        for (int l = left; l <= right; l++) arr[l] = aux[l];
    }

    // maxVal1: linear
    public static long maxVal1(long[] A, int n) {
        maxVal1Iters = 0;
        long max = A[0];
        for (int i = 1; i < n; i++) {
            maxVal1Iters++;
            if (A[i] > max) max = A[i];
        }
        return max;
    }

    // maxVal2: divisão e conquista
    public static long maxVal2(long[] A, int init, int end) {
        maxVal2Iters++;
        if (end - init <= 1)
            return Math.max(A[init], A[end]);
        else {
            int m = (init + end) / 2;
            long v1 = maxVal2(A, init, m);
            long v2 = maxVal2(A, m + 1, end);
            return Math.max(v1, v2);
        }
    }

    // MULTIPLY: divisão e conquista
    public static long multiply(long x, long y, int n) {
        multiplyIters++;
        if (n == 1) return x * y;
        int m = (int) Math.ceil(n / 2.0);
        long pow = 1L << m;
        long a = x / pow;
        long b = x % pow;
        long c = y / pow;
        long d = y % pow;
        long e = multiply(a, c, m);
        long f = multiply(b, d, m);
        long g = multiply(b, c, m);
        long h = multiply(a, d, m);
        return (1L << (2 * m)) * e + (1L << m) * (g + h) + f;
    }

    // Geração de vetor randômico
    private static long[] randomArray(int size, Random rand) {
        long[] arr = new long[size];
        for (int i = 0; i < size; i++) arr[i] = rand.nextLong();
        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = {32, 2048, 1048576};
        Random rand = new Random(42);

        System.out.printf("%-20s | %10s | %10s | %10s\n", "Algoritmo", "Tamanho", "Iterações", "Tempo(ms)");
        System.out.println("---------------------------------------------------------------------");

        // Merge Sort
        for (int sz : sizes) {
            long[] arr = randomArray(sz, rand);
            long[] arrCopy = Arrays.copyOf(arr, arr.length);
            long start = System.currentTimeMillis();
            mergeSort(arrCopy);
            long end = System.currentTimeMillis();
            System.out.printf("%-20s | %10d | %10d | %10d\n", "MergeSort", sz, mergeSortIters, (end - start));
        }

        // maxVal1
        for (int sz : sizes) {
            long[] arr = randomArray(sz, rand);
            long start = System.currentTimeMillis();
            maxVal1(arr, arr.length);
            long end = System.currentTimeMillis();
            System.out.printf("%-20s | %10d | %10d | %10d\n", "maxVal1", sz, maxVal1Iters, (end - start));
        }

        // maxVal2
        for (int sz : sizes) {
            long[] arr = randomArray(sz, rand);
            long start = System.currentTimeMillis();
            maxVal2(arr, 0, arr.length - 1);
            long end = System.currentTimeMillis();
            System.out.printf("%-20s | %10d | %10d | %10d\n", "maxVal2", sz, maxVal2Iters, (end - start));
        }

        // MULTIPLY
        int[] bitSizes = {4, 16, 64};
        for (int bits : bitSizes) {
            long x = rand.nextLong() & ((1L << bits) - 1);
            long y = rand.nextLong() & ((1L << bits) - 1);
            int n = bits;
            multiplyIters = 0;
            long start = System.currentTimeMillis();
            multiply(x, y, n);
            long end = System.currentTimeMillis();
            System.out.printf("%-20s | %10d | %10d | %10d\n", "MULTIPLY", bits, multiplyIters, (end - start));
        }
    }
}
