import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12Part1 {


    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        Map<String, List<String>> paths = new HashMap<>();
        for (var line : lines) {
            var parts = line.split("-");
            if (!paths.containsKey(parts[0])) {
                paths.put(parts[0], new ArrayList<>());
            }
            paths.get(parts[0]).add(parts[1]);
            if (!paths.containsKey(parts[1])) {
                paths.put(parts[1], new ArrayList<>());
            }
            paths.get(parts[1]).add(parts[0]);
        }
        var visited = new HashSet<String>();
        visited.add("start");
        var sum = visit("start", visited, paths);
        System.out.println(sum);
    }

    static int visit(String loc, Set<String> visited, Map<String, List<String>> paths) {
        if (loc.equals("end")) {
            return 1;
        }
        var sum = 0;
        for (var n : paths.get(loc)) {
            if (!visited.contains(n)) {
                if (n.equals(n.toLowerCase())) {
                    visited.add(n);
                }
                sum += visit(n, visited, paths);
                visited.remove(n);
            }
        }
        return sum;
    }
}
