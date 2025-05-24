import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day1Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).onItem()
             .transform(line -> line.trim().split("\\s+")).collect().asList().map(lines -> {
                 List<Long> left = lines.stream().map(parts -> Long.parseLong(parts[0])).sorted().toList();
                 List<Long> right = lines.stream().map(parts -> Long.parseLong(parts[1])).sorted().toList();
                 var count = right.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
                 return left.stream().mapToLong(x -> x * count.getOrDefault(x, 0L)).sum();
             }).subscribe().with(System.out::println);
    }
}
