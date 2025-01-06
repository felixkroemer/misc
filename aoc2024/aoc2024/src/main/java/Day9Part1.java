import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day9Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.stream(line.split("")).map(Long::parseLong).collect(Collectors.toList())).map(arr -> {
                 int l = 1;
                 int r = arr.size() - 1;
                 long sum = 0;
                 long leftIndex = arr.get(0);
                 while (l < r) {
                     if (arr.get(l).equals(arr.get(r))) {
                         for (long i = leftIndex; i < leftIndex + arr.get(l); i++) {
                             sum += i * (r / 2);
                         }
                         r -= 2;
                         leftIndex += arr.get(l);
                         l += 1;
                         for (long i = leftIndex; i < leftIndex + arr.get(l); i++) {
                             sum += i * (l / 2);
                         }
                         leftIndex += arr.get(l);
                         l += 1;
                     } else if (arr.get(l) < arr.get(r)) {
                         for (long i = leftIndex; i < leftIndex + arr.get(l); i++) {
                             sum += i * (r / 2);
                         }
                         arr.set(r, arr.get(r) - arr.get(l));
                         leftIndex += arr.get(l);
                         l += 1;
                         for (long i = leftIndex; i < leftIndex + arr.get(l); i++) {
                             sum += i * (l / 2);
                         }
                         leftIndex += arr.get(l);
                         l += 1;
                     } else {
                         for (long i = leftIndex; i < leftIndex + arr.get(r); i++) {
                             sum += i * (r / 2);
                         }
                         leftIndex += arr.get(r);
                         arr.set(l, arr.get(l) - arr.get(r));
                         r -= 2;
                     }
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }
}
