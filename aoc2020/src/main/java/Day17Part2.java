import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day17Part2 {

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));
    int co = 50;
    boolean[][][][] f = new boolean[co][][][];
    boolean[][][][] g = new boolean[co][][][];
    for (int i = 0; i < co; i++) {
      f[i] = new boolean[co][][];
      g[i] = new boolean[co][][];
      for (int j = 0; j < co; j++) {
        f[i][j] = new boolean[co][];
        g[i][j] = new boolean[co][];
        for (int k = 0; k < co; k++) {
          f[i][j][k] = new boolean[co];
          g[i][j][k] = new boolean[co];
        }
      }
    }

    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.getFirst().length(); j++) {
        if (lines.get(i).charAt(j) == '#') {
          f[co / 2][co / 2][i + (co / 2)][j + (co / 2)] = true;
        }
      }
    }

    for (int r = 0; r < 6; r++) {
      for (int i = 0; i < co; i++) {
        for (int j = 0; j < co; j++) {
          for (int k = 0; k < co; k++) {
            for (int l = 0; l < co; l++) {
              var n = 0;
              for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                  for (int c = -1; c <= 1; c++) {
                    for (int d = -1; d <= 1; d++) {
                      if (a == 0 && b == 0 && c == 0 && d == 0) {
                        continue;
                      }
                      try {
                        if (f[i + a][j + b][k + c][l + d]) {
                          n++;
                        }
                      } catch (IndexOutOfBoundsException ignored) {
                      }
                    }
                  }
                }
              }
              if (f[i][j][k][l]) {
                if (n == 2 || n == 3) {
                  g[i][j][k][l] = true;
                }
              } else {
                if (n == 3) {
                  g[i][j][k][l] = true;
                }
              }
            }
          }
        }
      }
      var tmp = f;
      f = g;
      g = tmp;
      for (boolean[][][] y : g) {
        for (boolean[][] x : y) {
          for (boolean[] d : x) {
            Arrays.fill(d, false);
          }
        }
      }
    }

    var sum = 0;
    for (int i = 0; i < co; i++) {
      for (int j = 0; j < co; j++) {
        for (int k = 0; k < co; k++) {
          for (int l = 0; l < co; l++) {
            if (f[i][j][k][l]) {
              sum++;
            }
          }
        }
      }
    }
    System.out.println(sum);
  }
}
