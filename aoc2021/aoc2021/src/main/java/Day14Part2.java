import io.smallrye.mutiny.tuples.Tuple3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Day14Part2 {


    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var pattern = lines.getFirst();

        HashMap<String, String> rules = new HashMap<>();
        for (var rule : lines.subList(2, lines.size())) {
            rules.put(rule.split("->")[0].strip(), rule.split("->")[1].strip());
        }

        var counts = new HashMap<String, Long>();
        for (int i = 0; i < pattern.length(); i++) {
            counts.put("" + pattern.charAt(i), counts.getOrDefault("" + pattern.charAt(i), 0L) + 1);
        }

        var cache = new HashMap<Tuple3<String, String, Integer>, Map<String, Long>>();

        for (int i = 0; i < pattern.length() - 1; i++) {
            if (rules.containsKey(pattern.substring(i, i + 2))) {
                var res = v(pattern.substring(i, i + 1), pattern.substring(i + 1, i + 2), 40, rules, cache);
                for (var r : res.entrySet()) {
                    counts.put(r.getKey(), counts.getOrDefault(r.getKey(), 0L) + r.getValue());
                }
            }
        }
        var vals = new ArrayList<>(counts.values());
        Collections.sort(vals);
        System.out.println(vals.getLast() - vals.getFirst());
    }


    static Map<String, Long> v(String a, String b, int rest, Map<String, String> rules, Map<Tuple3<String, String, Integer>, Map<String, Long>> cache) {
        if (rest == 0) {
            return Map.of();
        }
        var key = Tuple3.of(a, b, rest);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        var ret = new HashMap<String, Long>();
        ret.put(rules.get(a + b), 1L);
        var res = v(a, rules.get(a + b), rest - 1, rules, cache);
        for (var r : res.entrySet()) {
            ret.put(r.getKey(), ret.getOrDefault(r.getKey(), 0L) + r.getValue());
        }
        res = v(rules.get(a + b), b, rest - 1, rules, cache);
        for (var r : res.entrySet()) {
            ret.put(r.getKey(), ret.getOrDefault(r.getKey(), 0L) + r.getValue());
        }
        cache.put(Tuple3.of(a, b, rest), ret);
        return ret;
    }
}
