import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day2Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).onItem()
             .transform(line -> line.trim().split("\\s+")).filter(line -> line.length > 0)
             .map(line -> Arrays.stream(line).mapToLong(Long::parseLong).toArray()).filter(line -> {
                 var inc = line[0] < line[1];
                 for (int i = 0; i < line.length - 1; i++) {
                     if(line[i] == line[i+1] || Math.abs(line[i] - line[i + 1]) > 3) {
                        return false;
                     }
                     if (inc && line[i] > line[i + 1] || !inc && line[i] < line[i + 1]) {
                         return false;
                     }
                 }
                 return true;
             }).collect().asList().map(List::size).subscribe().with(System.out::println);
    }
}
