import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;

public class Day3Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var y = 0;
        var x = 0;
        var sum = 0;
        while (y < lines.size()) {
            if (lines.get(y).charAt(x) == '#') {
                sum++;
            }
            x = (x + 3) % lines.getFirst().length();
            y++;
        }
        System.out.println(sum);
    }

}
