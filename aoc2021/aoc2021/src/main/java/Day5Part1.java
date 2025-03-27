import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day5Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        Set<Tuple2<Integer, Integer>> found = new HashSet<>();
        Set<Tuple2<Integer, Integer>> duplicate = new HashSet<>();
        for (var line : lines) {
            var l = line.split("->")[0].strip();
            var x1 = Integer.parseInt(l.split(",")[0]);
            var y1 = Integer.parseInt(l.split(",")[1]);
            var r = line.split("->")[1].strip();
            var x2 = Integer.parseInt(r.split(",")[0]);
            var y2 = Integer.parseInt(r.split(",")[1]);
            if (x1 != x2 && y1 != y2) {
                continue;
            }
            var xLow = Math.min(x1, x2);
            var xHigh = Math.max(x1, x2);
            var yLow = Math.min(y1, y2);
            var yHigh = Math.max(y1, y2);
            for (int i = yLow; i < yHigh + 1; i++) {
                for (int j = xLow; j < xHigh + 1; j++) {
                    var t = Tuple2.of(i, j);
                    if (found.contains(t)) {
                        duplicate.add(t);
                    } else {
                        found.add(Tuple2.of(i, j));
                    }
                }
            }
        }
        System.out.println(duplicate.size());
    }
}
