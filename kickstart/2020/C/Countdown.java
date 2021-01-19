import java.util.Scanner;

class Countdown {
    public static int solve(int[] ar, int k) {
        int sum = 0;
        for (int i = ar.length - 1; i >= k - 1; i--) {
            if (ar[i] == 1) {
                for (int j = 1; j < k; j++) {
                    if (ar[i - j] != j + 1) {
                        break;
                    }
                    if (j == k - 1) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int n, k;
            for (int i = 0; i < t; i++) {
                n = s.nextInt();
                k = s.nextInt();
                int[] ar = new int[n];
                for (int j = 0; j < n; j++) {
                    ar[j] = s.nextInt();
                }
                int res = solve(ar, k);
                System.out.println(String.format("Case #%d: %d", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
