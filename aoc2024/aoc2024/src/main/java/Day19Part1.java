import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day19Part1 {

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            Map<String, Set<String>> map = new HashMap<>();
            var patterns = Arrays.stream(lines.getFirst().split(",")).map(String::trim).toList();
            int c = 0;
            Map<String, Boolean> h = new HashMap<>();
            for (int i = 2; i < lines.size(); i++) {
                if (test(lines.get(i), patterns, h)) {
                    c++;
                }
            }
            return c;
        }).subscribe().with(System.out::println);
    }

    static boolean test(String s, List<String> patterns, Map<String, Boolean> h) {
        if (s.isEmpty()) {
            return true;
        }
        if (h.containsKey(s)) {
            return h.get(s);
        }
        for (int i = 0; i < s.length(); i++) {
            for (var pattern : patterns) {
                if (s.substring(0, i + 1).equals(pattern)) {
                    if (test(s.substring(i + 1), patterns, h)) {
                        h.put(s, true);
                        return true;
                    }
                }
            }
        }
        h.put(s, false);
        return false;
    }
}

