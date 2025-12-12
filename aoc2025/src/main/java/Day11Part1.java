import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day11Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Map<String, Set<String>> g = new HashMap<>();

        for (var line : lines) {
            var parts = line.split(" ");
            var from = parts[0].substring(0, parts[0].length() - 1);
            var h = new HashSet<String>();
            g.put(from, h);
            h.addAll(Arrays.asList(parts).subList(1, parts.length));
        }
        System.out.println(visit("you", g, new HashSet<>()));
    }

    static int visit(String node, Map<String, Set<String>> g, Set<String> visited) {
        visited.add(node);
        var sum = 0;
        for (var n : g.get(node)) {
            if (n.equals("out")) {
                visited.remove(node);
                return 1;
            }
            if (!visited.contains(n)) {
                sum += visit(n, g, visited);
            }
        }
        visited.remove(node);
        return sum;
    }
}
