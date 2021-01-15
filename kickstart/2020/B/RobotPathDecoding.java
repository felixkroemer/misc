import java.util.Scanner;

class RobotPathDecoding {
    public static final long B = 1000000000L;

    public static long[] solve(char[] ar) {
        long[] res = solveSubProblem(0, ar.length - 1, ar);
        for (int i = 0; i < res.length; i++) {
            if (res[i] < 0) {
                res[i] = res[i] + B;
            }
        }
        return res;
    }

    public static long[] solveSubProblem(int startPos, int endPos, char[] ar) {
        long rowOffset = 0;
        long colOffset = 0;
        for (int i = startPos; i <= endPos; i++) {
            switch (ar[i]) {
                case 'N':
                    rowOffset = (rowOffset - 1) % B;
                    break;
                case 'S':
                    rowOffset = (rowOffset + 1) % B;
                    break;
                case 'W':
                    colOffset = (colOffset - 1) % B;
                    break;
                case 'E':
                    colOffset = (colOffset + 1) % B;
                    break;
                default:
                    break;
            }
            if (ar[i] >= '0' && ar[i] <= '9') {
                int k = ar[i] - 48;
                int a = 0;
                int end = -1;
                for (int j = i + 1; j <= endPos; j++) {
                    if (ar[j] == '(') {
                        a++;
                    }
                    if (ar[j] == ')') {
                        a--;
                    }
                    if (a == 0) {
                        end = j;
                        break;
                    }
                }
                long[] res = solveSubProblem(i + 2, end - 1, ar);
                rowOffset = (rowOffset + k * res[0]) % B;
                colOffset = (colOffset + k * res[1]) % B;
                i = end;
            }
        }
        return new long[] { rowOffset, colOffset };
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            s.nextLine();
            String line;
            for (int i = 0; i < t; i++) {
                line = s.nextLine();
                long[] res = solve(line.toCharArray());
                System.out.println(String.format("Case #%d: %d %d", i + 1, res[1] + 1, res[0] + 1));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}