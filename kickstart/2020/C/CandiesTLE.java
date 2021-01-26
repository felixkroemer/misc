import java.util.Scanner;

class CandiesTLE {
    public static long solve(int[] ar, int[][] ops) {
        long sum = 0;
        long prefix[] = new long[ar.length + 1];
        long multPrefix[] = new long[ar.length + 1];
        updatePrefix(ar, multPrefix, true);
        updatePrefix(ar, prefix, false);

        for (int i = 0; i < ops.length; i++) {
            if (ops[i][0] == 0) {
                long s = (multPrefix[ops[i][2]] - multPrefix[ops[i][1] - 1]);
                s -= (ops[i][1] - 1) * (prefix[ops[i][2]] - prefix[ops[i][1] - 1]);
                sum += ops[i][1] % 2 == 1 ? s : -s;
            } else {
                ar[ops[i][1] - 1] = ops[i][2];
                updatePrefix(ar, multPrefix, true);
                updatePrefix(ar, prefix, false);
            }
        }
        return sum;
    }

    public static void updatePrefix(int[] ar, long[] prefix, boolean mult) {
        for (int i = 1; i <= ar.length; i++) {
            prefix[i] = prefix[i - 1] + ar[i - 1] * (i % 2 == 0 ? -1 : 1) * (mult ? i : 1);
        }
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int n, q;
            for (int i = 0; i < t; i++) {
                n = s.nextInt();
                q = s.nextInt();
                int[] ar = new int[n];
                for (int j = 0; j < n; j++) {
                    ar[j] = s.nextInt();
                }
                int[][] ops = new int[q][3];
                for (int k = 0; k < q; k++) {
                    ops[k][0] = s.next().charAt(0) == 'Q' ? 0 : 1;
                    ops[k][1] = s.nextInt();
                    ops[k][2] = s.nextInt();
                }
                long res = solve(ar, ops);
                System.out.println(String.format("Case #%d: %d", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
