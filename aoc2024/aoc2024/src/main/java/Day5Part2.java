import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day5Part2 {

    public static List<Long> fix(Map<Long, List<Long>> map, List<Long> input) {
        input.sort((o1, o2) -> {
            if (map.containsKey(o1) && map.get(o1).contains(o2)) {
                return 1;
            } else if (map.containsKey(o2) && map.get(o2).contains(o1)) {
                return -1;
            } else {
                return 0;
            }
        });
        return input;
    }

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).collect().asList().map(lines -> {
            Map<Long, List<Long>> rules = new HashMap<>();
            for (var item : lines.subList(0, lines.indexOf(""))) {
                var elements = item.split("\\|");
                var key = Long.parseLong(elements[0]);
                var value = Long.parseLong(elements[1]);
                rules.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            }
            var sequences = lines.subList(lines.indexOf("") + 1, lines.size()).stream()
                                 .map(line -> Arrays.stream(line.split(",")).map(Long::parseLong)
                                                    .collect(Collectors.toList())).toList();

            return Tuple2.of(rules, sequences);
        }).map(tuple -> {
            Map<Long, List<Long>> rules = tuple.getItem1();
            List<List<Long>> sequences = tuple.getItem2();
            return sequences.stream().filter(f -> {
                for (int i = 0; i < f.size(); i++) {
                    for (int j = i + 1; j < f.size(); j++) {
                        if (!rules.containsKey(f.get(i)) || !rules.get(f.get(i)).contains(f.get(j))) {
                            return true;
                        }
                    }
                }
                return false;
            }).map(f -> fix(rules, f)).mapToLong(l -> l.get(l.size() / 2)).sum();
        }).subscribe().with(System.out::println);
    }
}
