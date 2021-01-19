import java.util.Scanner;

class StableWall {
    public static String solve(int[][] ar) {
        int[] status = new int[26];
        boolean[][] incidence = new boolean[26][26];
        for (int i = 0; i < ar[0].length; i++) {
            for (int j = 0; j < ar.length; j++) {
                status[ar[j][i]] = 1;
                if (j < ar.length - 1 && ar[j][i] != ar[j + 1][i]) {
                    incidence[ar[j][i]][ar[j + 1][i]] = true;
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < status.length; i++) {
            if (status[i] == 1) {
                if (!visit(i, status, incidence, sb)) {
                    return "-1";
                }
            }
        }

        return sb.toString();
    }

    public static boolean visit(int i, int[] status, boolean[][] incidence, StringBuffer sb) {
        status[i] = 2;
        for (int j = 0; j < 26; j++) {
            if (incidence[i][j] && status[j] != 0) {
                if (status[j] == 2) {
                    return false;
                }
                if (!visit(j, status, incidence, sb)) {
                    return false;
                }
            }
        }
        status[i] = 0;
        sb.append((char) (i + 65));
        return true;
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int r, c;
            for (int i = 0; i < t; i++) {
                r = s.nextInt();
                c = s.nextInt();
                int[][] ar = new int[r][];
                s.nextLine();
                for (int j = 0; j < r; j++) {
                    ar[j] = new int[c];
                    String p = s.nextLine();
                    for (int k = 0; k < p.length(); k++) {
                        ar[j][k] = p.charAt(k) - 65;
                    }
                }
                String res = solve(ar);
                System.out.println(String.format("Case #%d: %s", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
