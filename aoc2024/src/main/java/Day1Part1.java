import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class Day1Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).onItem()
             .transform(line -> line.trim().split("\\s+")).collect().asList().map(lines -> {
                 List<Long> left = lines.stream().map(parts -> Long.parseLong(parts[0])).sorted().toList();
                 List<Long> right = lines.stream().map(parts -> Long.parseLong(parts[1])).sorted().toList();
                 return IntStream.range(0, left.size()).mapToLong(i -> Math.abs(left.get(i) - right.get(i))).sum();
             }).subscribe().with(System.out::println);
    }
}
