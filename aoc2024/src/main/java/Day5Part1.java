import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day5Part1 {

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
                            return false;
                        }
                    }
                }
                return true;
            }).mapToLong(l -> l.get(l.size() / 2)).sum();
        }).subscribe().with(System.out::println);
    }
}
