import java.util.Scanner;

class Plates {
    public static int solve(int[][] ar, int p) {
        int[][] f = new int[ar.length + 1][p + 1];
        int max, sum, val;
        for (int i = 1; i <= ar.length; i++) {
            for (int j = 0; j <= p; j++) {
                max = 0;
                for (int k = 0; k <= Math.min(j, ar[i - 1].length); k++) {
                    val = 0;
                    sum = 0;
                    for (int l = 0; l < k; l++) {
                        sum += ar[i - 1][l];
                    }
                    val = sum + f[i - 1][j - k];
                    if (val > max) {
                        max = val;
                    }
                }
                f[i][j] = max;
            }
        }
        return f[ar.length][p];
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int n, k, p;
            for (int i = 0; i < t; i++) {
                n = s.nextInt();
                k = s.nextInt();
                p = s.nextInt();
                int[][] ar = new int[n][];
                for (int m = 0; m < n; m++) {
                    ar[m] = new int[k];
                    for (int j = 0; j < k; j++) {
                        ar[m][j] = s.nextInt();
                    }
                }
                int res = solve(ar, p);
                System.out.println(String.format("Case #%d: %d", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}