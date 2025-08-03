import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day17Part1 {

  static int[][] DIRS = {
    {1, 0, 0},
    {-1, 0, 0},
    {0, 1, 0},
    {0, -1, 0},
    {0, 0, 1},
    {0, 0, -1},
    {1, 1, 0},
    {1, -1, 0},
    {-1, 1, 0},
    {-1, -1, 0},
    {1, 0, 1},
    {1, 0, -1},
    {-1, 0, 1},
    {-1, 0, -1},
    {0, 1, 1},
    {0, 1, -1},
    {0, -1, 1},
    {0, -1, -1},
    {1, 1, 1},
    {1, 1, -1},
    {1, -1, 1},
    {1, -1, -1},
    {-1, 1, 1},
    {-1, 1, -1},
    {-1, -1, 1},
    {-1, -1, -1}
  };

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));
    int c = 100;
    boolean[][][] f = new boolean[c][][];
    boolean[][][] g = new boolean[c][][];
    for (int i = 0; i < c; i++) {
      f[i] = new boolean[c][];
      g[i] = new boolean[c][];
      for (int j = 0; j < c; j++) {
        f[i][j] = new boolean[c];
        g[i][j] = new boolean[c];
      }
    }

    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.getFirst().length(); j++) {
        if (lines.get(i).charAt(j) == '#') {
          f[c/2][i+(c/2)][j+(c/2)] = true;
        }
      }
    }

    for (int r = 0; r < 6; r++) {
      for (int i = 0; i < c; i++) {
        for (int j = 0; j < c; j++) {
          for (int k = 0; k < c; k++) {
            var n = 0;
            for (int[] dir : DIRS) {
              try {
                if (f[i + dir[0]][j + dir[1]][k + dir[2]]) {
                  n++;
                }
              } catch (IndexOutOfBoundsException ignored) {
              }
            }
            if (f[i][j][k]) {
              if (n == 2 || n == 3) {
                g[i][j][k] = true;
              }
            } else {
              if (n == 3) {
                g[i][j][k] = true;
              }
            }
          }
        }
      }
      var tmp = f;
      f = g;
      g = tmp;
      for (boolean[][] y : g) {
        for (boolean[] x : y) {
          Arrays.fill(x, false);
        }
      }
    }

    var sum = 0;
    for (int i = 0; i < c; i++) {
      for (int j = 0; j < c; j++) {
        for (int k = 0; k < c; k++) {
          if (f[i][j][k]) {
            sum++;
          }
        }
      }
    }
    System.out.println(sum);
  }
}
