import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day11Part2 {

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input")).get(0).split(" "))
                .map(arr -> new LinkedList<>(Arrays.stream(arr).map(Long::parseLong).collect(Collectors.toList()))).map(input -> {
                    long sum = 0;
                    HashMap<Tuple2<Long, Integer>, Long> found = new HashMap<>();
                    for (var val : input) {
                        sum += visit(val, 0, found);
                    }
                    System.out.println(found.size());
                    return sum;

                }).subscribe().with(System.out::println);
    }

    static long visit(long num, int b, HashMap<Tuple2<Long, Integer>, Long> found) {
        if (b == 75) {
            return 1;
        } else if (num == 0) {
            return visit(1, b + 1, found);
        } else if (((int) Math.log10(Math.abs(num)) + 1) % 2 == 0) {
            if (found.containsKey(Tuple2.of(num, b))) {
                return found.get(Tuple2.of(num, b));
            } else {
                var sum2 = 0L;
                var s = String.valueOf(num);
                sum2 += visit(Long.parseLong(s.substring(s.length() / 2)), b + 1, found);
                sum2 += visit(Long.parseLong(s.substring(0, s.length() / 2)), b + 1, found);
                found.put(Tuple2.of(num, b), sum2);
                return sum2;
            }
        } else {
            return visit(num * 2024, b + 1, found);
        }
    }

}
