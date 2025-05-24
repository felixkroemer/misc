import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class Day1Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int gr = 0;
        int prev = -1;
        for (var line : lines) {
            var val = Integer.valueOf(line);
            if (prev != -1 && val > prev) {
                gr++;
            }
            prev = val;
        }
        System.out.println(gr);
    }
}
