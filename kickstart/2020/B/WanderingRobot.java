import java.util.Scanner;

class WanderingRobot {
    public static double solve(int w, int h, int l, int u, int r, int d) {
        double[] lf;
        if (w > h) {
            lf = new double[2 * w];
        } else {
            lf = new double[2 * h];
        }

        // fak(x) = 2^(log(fak(x))) = 2^(log(fak(x-1) * x)) = 2^(log(fak(x-1)) + log(x)))
        for (int i = 1; i < lf.length; i++) {
            lf[i] = Math.log(i) / Math.log(2) + lf[i - 1];
        }

        double res = 0;
        int n = u + r;
        for (int k = u - 1; k >= 0; k--) {
            if (n - k < w) {
                res += Math.pow(2, lf[n] - lf[k] - lf[n - k] - n);
                if (n - k == w - 1) {
                    int c = k - 1;
                    while (c >= 0) {
                        res += Math.pow(2, lf[n] - lf[c] - lf[n - c] - n);
                        c--;
                    }
                }
            } else {
                break;
            }
        }

        n = l + d;
        int offset;
        for (int i = d + 1; i < h; i++) {
            offset = i - d;
            if (l - offset >= 0) {
                res += Math.pow(2, lf[n] - lf[l - offset] - lf[n - (l - offset)] - n);
                if (i == h - 1) {
                    int c = l - offset - 1;
                    while (c >= 0) {
                        res += Math.pow(2, lf[n] - lf[c] - lf[n - c] - n);
                        c--;
                    }
                }
            } else {
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int w, h, l, u, r, d;
            for (int i = 0; i < t; i++) {
                w = s.nextInt();
                h = s.nextInt();
                l = s.nextInt();
                u = s.nextInt();
                r = s.nextInt();
                d = s.nextInt();
                double res = solve(w, h, l - 1, u - 1, r - 1, d - 1);
                System.out.println(String.format("Case #%d: %f", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}