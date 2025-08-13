import java.util.Random;

public class MultiplicationBenchmark {
    public static void main(String[] args) {
        int[] bitSizes = {8, 16, 24, 32, 40, 48, 56, 63};
        System.out.printf("%-25s", "Algoritmo");
        for (int bits : bitSizes) {
            System.out.printf("%10d", bits);
        }
        System.out.println();

        benchmark("Multiplicação Recursiva", bitSizes, true);
        benchmark("Multiplicação Padrão", bitSizes, false);
    }

    private static void benchmark(String name, int[] bitSizes, boolean recursive) {
        System.out.printf("%-25s", name);
        Random rand = new Random(42);
        for (int bits : bitSizes) {
            long x = rand.nextLong() & ((1L << bits) - 1);
            long y = rand.nextLong() & ((1L << bits) - 1);
            int n = bits;

            long start = System.nanoTime();
            long result = 0;
            for (int i = 0; i < 1000; i++) {
                if (recursive) {
                    result = RecursiveMultiplier.multiply(x, y, n);
                } else {
                    result = x * y;
                }
            }
            long end = System.nanoTime();
            System.out.printf("%10d", (end - start) / 1000); // tempo médio por execução (ns)
        }
        System.out.println();
    }
}
