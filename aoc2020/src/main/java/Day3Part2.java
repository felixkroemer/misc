import java.nio.file.Files;
import java.nio.file.Path;

public class Day3Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        long prod = 1;
        for (var config : new int[][]{{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}}) {
            var y = 0;
            var x = 0;
            var sum = 0;
            while (y < lines.size()) {
                if (lines.get(y).charAt(x) == '#') {
                    sum++;
                }
                x = (x + config[0]) % lines.getFirst().length();
                y += config[1];
            }
            prod *= sum;
        }
        System.out.println(prod);
    }

}
