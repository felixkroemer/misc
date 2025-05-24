import io.smallrye.mutiny.Uni;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day19Part2 {

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            Map<String, Set<String>> map = new HashMap<>();
            var patterns = Arrays.stream(lines.getFirst().split(",")).map(String::trim).toList();
            long c = 0;
            Map<String, Long> h = new HashMap<>();
            for (int i = 2; i < lines.size(); i++) {
                c += test(lines.get(i), patterns, h);
            }
            return c;
        }).subscribe().with(System.out::println);
    }

    static long test(String s, List<String> patterns, Map<String, Long> h) {
        if (s.isEmpty()) {
            return 1;
        }
        if (h.containsKey(s)) {
            return h.get(s);
        }
        long sum = 0;
        for (int i = 0; i < s.length(); i++) {
            for (var pattern : patterns) {
                if (s.substring(0, i + 1).equals(pattern)) {
                    sum += test(s.substring(i + 1), patterns, h);
                }
            }
        }
        h.put(s, sum);
        return sum;
    }
}

