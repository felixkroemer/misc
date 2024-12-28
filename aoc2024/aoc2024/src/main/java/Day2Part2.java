import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day2Part2 {

    public static Optional<Integer> test(long[] line) {
        var inc = line[0] < line[1];
        for (int i = 0; i < line.length - 1; i++) {
            if (line[i] == line[i + 1] || Math.abs(line[i] - line[i + 1]) > 3) {
                return Optional.of(i);
            }
            if (inc && line[i] > line[i + 1] || !inc && line[i] < line[i + 1]) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static long[] removeElement(long[] arr, int index) {
        long[] result = new long[arr.length - 1];
        System.arraycopy(arr, 0, result, 0, index);
        System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
        return result;
    }

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).onItem()
             .transform(line -> line.trim().split("\\s+")).filter(line -> line.length > 0)
             .map(line -> Arrays.stream(line).mapToLong(Long::parseLong).toArray()).filter(line -> {
                 var res = test(line);
                 if (res.isEmpty()) {
                     return true;
                 } else {
                     int index = res.get();
                     for (var i : new int[]{-1, 0, 1}) {
                         if (index == 0 && i == -1)
                             continue;
                         var e = removeElement(line, index + i);
                         if (test(e).isEmpty()) {
                             return true;
                         }
                     }
                     return false;
                 }
             }).collect().asList().map(List::size).subscribe().with(System.out::println);
    }
}
