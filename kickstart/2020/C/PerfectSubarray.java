import java.util.Scanner;

class PerfectSubarray {
    public static long solve(int[] ar) {
        int[] squares = new int[(int) Math.sqrt(ar.length * 100) + 1];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = i * i;
        }
        int[] res = new int[ar.length];
        int offset = 0;
        int sum = 0;
        for (int i = 0; i < ar.length; i++) {
            sum += ar[i];
            if (sum < offset) {
                offset = sum;
            }
        }
        offset = offset * (-1);
        int[] p = new int[ar.length * 100 + offset];
        sum = 0;
        p[0 + offset] = 1;
        for (int i = 0; i < ar.length; i++) {
            sum += ar[i];
            for (int j = 0; j < squares.length; j++) {
                if (sum - squares[j] + offset >= 0) {
                    res[i] += p[sum - squares[j] + offset];
                } else {
                    break;
                }
            }
            p[sum + offset]++;
        }
        long result = 0;
        for (int i : res) {
            result += i;
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            for (int i = 0; i < t; i++) {
                int n = s.nextInt();
                int[] ar = new int[n];
                for (int j = 0; j < n; j++) {
                    ar[j] = s.nextInt();
                }
                long res = solve(ar);
                System.out.println(String.format("Case #%d: %d", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
