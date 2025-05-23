import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Day25Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var a = new char[lines.size()][lines.getFirst().length()];
        var b = new char[lines.size()][lines.getFirst().length()];
        char[][] last = null;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.getFirst().length(); j++) {
                if (lines.get(i).charAt(j) != '.') {
                    a[i][j] = lines.get(i).charAt(j);
                }
            }
        }
        int k = 1;
        while (true) {
            for (char[] chars : b) {
                Arrays.fill(chars, '\0');
            }
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[0].length; j++) {
                    if (a[i][j] != '>') {
                        continue;
                    }
                    if (a[i][(j + 1) % a[0].length] == '\0') {
                        b[i][(j + 1) % a[0].length] = '>';
                    } else {
                        b[i][j] = '>';
                    }
                }
            }
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[0].length; j++) {
                    if (a[i][j] != 'v') {
                        continue;
                    }
                    if (b[(i + 1) % a.length][j] == '\0' && a[(i + 1) % a.length][j] != 'v') {
                        b[(i + 1) % a.length][j] = 'v';
                    } else {
                        b[i][j] = 'v';
                    }
                }
            }
            if (Arrays.deepHashCode(last) == Arrays.deepHashCode(a)) {
                System.out.println(k);
                break;
            } else {
                last = a;
                var c = a;
                a = b;
                b = c;
            }
            k++;
        }
    }

}

