import java.util.Scanner;

class BusRoutes {
    public static long solve(long[] ar, long d) {
        for (int i = ar.length - 1; i >= 0; i--) {
            d = (d / ar[i]) * ar[i];
        }
        return d;
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int n;
            long d;
            for (int i = 0; i < t; i++) {
                n = s.nextInt();
                d = s.nextLong();
                long[] ar = new long[n];
                for (int j = 0; j < n; j++) {
                    ar[j] = s.nextLong();
                }
                long res = solve(ar, d);
                System.out.println(String.format("Case #%d: %d", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
