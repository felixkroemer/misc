import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day23Part2 {

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
            var mc = maxClique(g, new HashSet<>(), new HashSet<>(g.keySet()));
            var l = new ArrayList<>(mc);
            Collections.sort(l);
            return String.join(",", l);
        }).subscribe().with(System.out::println);
    }

    static Set<String> maxClique(Map<String, List<String>> g, Set<String> clique, Set<String> prospective) {
        if (prospective.isEmpty()) {
            return clique;
        }
        Set<String> maxClique = null;
        for (var v : new ArrayList<>(prospective)) {
            var n = g.get(v);
            var newClique = new HashSet<>(clique);
            newClique.add(v);
            var newProspective = new HashSet<>(prospective);
            newProspective.retainAll(n);
            var c = maxClique(g, newClique, newProspective);
            if (maxClique == null || c != null && c.size() > maxClique.size()) {
                maxClique = c;
            }
            prospective.remove(v);
        }
        return maxClique;
    }
}
