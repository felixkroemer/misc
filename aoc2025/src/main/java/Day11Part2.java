import io.smallrye.mutiny.tuples.Tuple3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day11Part2 {

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
        System.out.println(visit("svr", g, false, false, new HashMap<>()));
    }

    static long visit(String node, Map<String, Set<String>> g, boolean aFound, boolean bFound, Map<Tuple3<Boolean, Boolean, String>, Long> memo) {
        aFound = aFound || node.equals("dac");
        bFound = bFound || node.equals("fft");
        var h = Tuple3.of(aFound, bFound, node);
        if (memo.containsKey(h)) {
            return memo.get(h);
        }
        long sum = 0;
        for (var n : g.get(node)) {
            if (n.equals("out")) {
                if (aFound && bFound) {
                    return 1L;
                } else {
                    return 0L;
                }
            }
            sum += visit(n, g, aFound, bFound, memo);
        }
        memo.put(h, sum);
        return sum;
    }
}
