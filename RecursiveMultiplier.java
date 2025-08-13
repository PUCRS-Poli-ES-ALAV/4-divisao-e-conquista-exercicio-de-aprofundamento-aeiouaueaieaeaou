public class RecursiveMultiplier {
    public static long multiply(long x, long y, int n) {
        if (n == 1) {
            return x * y;
        } else {
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
    }
}
