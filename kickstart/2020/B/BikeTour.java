import java.util.Scanner;

class BikeTour {
    public static int solve(int[] ar) {
        int sum = 0;
        for (int i = 1; i < ar.length - 1; i++) {
            if (ar[i - 1] < ar[i] && ar[i] > ar[i + 1]) {
                sum++;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int n;
            for (int i = 0; i < t; i++) {
                n = s.nextInt();
                int[] ar = new int[n];
                for (int j = 0; j < n; j++) {
                    ar[j] = s.nextInt();
                }
                int res = solve(ar);
                System.out.println(String.format("Case #%d: %d", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
