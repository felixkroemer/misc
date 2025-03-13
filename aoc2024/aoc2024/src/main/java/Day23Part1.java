import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day23Part1 {

    static HashMap<String, Tuple2<Integer, Integer>> coordsB = new HashMap<>();
    static long MOD = 16777216;

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            Map<String, List<String>> g = new HashMap<>();
            for (var line : lines) {
                var items = line.split("-");
                var l = items[0];
                var r = items[1];

                g.putIfAbsent(l, new ArrayList<>());
                g.putIfAbsent(r, new ArrayList<>());
                g.get(l).add(r);
                g.get(r).add(l);
            }
            var sum = 0;
            HashSet<String> visited = new HashSet<>();
            for (var e : g.entrySet()) {
                if (e.getKey().startsWith("t")) {
                    visited.add(e.getKey());
                    var n = e.getValue();
                    for (int i = 0; i < n.size(); i++) {
                        for (int j = i; j < n.size(); j++) {
                            if (i == j) {
                                continue;
                            }
                            if (visited.contains(n.get(i)) || visited.contains(n.get(j))) {
                                continue;
                            }
                            if (g.get(n.get(i)).contains(n.get(j))) {
                                sum++;
                            }
                        }
                    }
                }
            }
            return sum;
        }).subscribe().with(System.out::println);
    }
}
