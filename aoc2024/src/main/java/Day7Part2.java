import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day7Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).filter(line -> !line.isEmpty())
             .filter(line -> {
                 var result = Long.parseLong(line.split(":")[0]);
                 var parts = Arrays.stream(line.split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
                 var possibilities = Math.pow(3, parts.size() - 1);
                 for (int i = 0; i < possibilities; i++) {
                     int temp = i;
                     var res = parts.get(0);
                     for (int j = 0; j < parts.size() - 1; j++) {
                         int op = temp % 3;
                         temp /= 3;
                         if (op == 0) {
                             res *= parts.get(j + 1);
                         } else if (op == 1) {
                             res += parts.get(j + 1);
                         } else {
                             res = Long.parseLong("" + res + parts.get(j + 1));
                         }
                     }
                     if (res == result) {
                         return true;
                     }
                 }
                 return false;
             }).map(line -> Long.parseLong(line.split(":")[0])).collect().with(Collectors.summingLong(l -> l))
             .subscribe().with(System.out::println);
    }
}
