import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class Day2Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int f = 0;
        int h = 0;
        for (var line : lines) {
            int val = Integer.parseInt(line.split(" ")[1]);
            if (line.startsWith("forward")) {
                f += val;
            } else if (line.startsWith("down")) {
                h += val;
            } else {
                h -= val;
            }
        }
        System.out.println(f * h);
    }
}
