import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Day24Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Map<Tuple2<Double, Double>, Boolean> tiles = new HashMap<>();

        for (var line : lines) {
            int idx = 0;
            double xPos = 0;
            double yPos = 0;
            while(idx < line.length() ) {
                var s = line.substring(idx);
                if (s.startsWith("sw")) {
                    idx += 2;
                    yPos -= 0.5;
                    xPos -= 0.5;
                } else if (s.startsWith("se")) {
                    idx += 2;
                    yPos -= 0.5;
                    xPos += 0.5;
                } else if (s.startsWith("nw")) {
                    idx += 2;
                    yPos += 0.5;
                    xPos -= 0.5;
                } else if (s.startsWith("ne")) {
                    idx += 2;
                    yPos += 0.5;
                    xPos += 0.5;
                } else if (s.startsWith("e")) {
                    idx += 1;
                    xPos += 1;
                } else if (s.startsWith("w")) {
                    idx += 1;
                    xPos -= 1;
                }
            }
            tiles.compute(Tuple2.of(yPos, xPos), (k, v) -> v == null || !v);
        }
        var res = tiles.entrySet().stream().filter(Map.Entry::getValue).count();
        System.out.println(res);
    }
}
