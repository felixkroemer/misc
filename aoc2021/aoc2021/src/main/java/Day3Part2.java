import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Day3Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var a = new HashSet<>(lines);
        var b = new HashSet<>(lines);
        for (int i = 0; i < lines.getFirst().length(); i++) {
            int nA = 0;
            int nB = 0;
            for (var line : a) {
                if (line.charAt(i) == '1') {
                    nA++;
                }
            }
            for (var line : b) {
                if (line.charAt(i) == '1') {
                    nB++;
                }
            }
            var aIt = a.iterator();
            var aSize = a.size();
            while (aIt.hasNext()) {
                if (a.size() == 1) {
                    break;
                }
                var val = aIt.next();
                if (val.charAt(i) == (nA >= aSize / 2.0 ? '0' : '1')) {
                    aIt.remove();
                }
            }
            var bIt = b.iterator();
            var bSize = b.size();
            while (bIt.hasNext()) {
                if (b.size() == 1) {
                    break;
                }
                var val = bIt.next();
                if (val.charAt(i) == (nB >= bSize / 2.0 ? '1' : '0')) {
                    bIt.remove();
                }
            }
        }
        System.out.println(Integer.parseInt(a.iterator().next(), 2) * Integer.parseInt(b.iterator().next(), 2));
    }
}
