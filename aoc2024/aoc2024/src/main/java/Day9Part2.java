import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.stream(line.split("")).map(Long::parseLong).collect(Collectors.toList())).map(arr -> {
                 List<Tuple2<Long, Integer>> parts = new ArrayList<>();
                 for (int i = 0; i < arr.size(); i++) {
                     parts.add(Tuple2.of(arr.get(i), i % 2 == 0 ? i / 2 : 0));
                 }
                 int i = parts.size() - 1;
                 while (i > 0) {
                     if (parts.get(i).getItem2() > 0) {
                         for (int j = 1; j < i; j++) {
                             if (parts.get(j).getItem2() == 0 && parts.get(j).getItem1() >= parts.get(i).getItem1()) {
                                 if (parts.get(j).getItem1().equals(parts.get(i).getItem1())) {
                                     parts.set(j, Tuple2.of(parts.get(j).getItem1(), parts.get(i).getItem2()));
                                     parts.set(i, Tuple2.of(parts.get(j).getItem1(), 0));
                                 } else {
                                     parts.add(j + 1, Tuple2.of(parts.get(j).getItem1() - parts.get(i).getItem1(), 0));
                                     i += 1;
                                     parts.set(j, Tuple2.of(parts.get(i).getItem1(), parts.get(i).getItem2()));
                                     parts.set(i, Tuple2.of(parts.get(i).getItem1(), 0));
                                 }
                                 break;
                             }
                         }
                     }
                     i--;
                 }
                 i = 0;
                 long sum = 0;
                 for (var part : parts) {
                     for (int j = i; j < i + part.getItem1(); j++) {
                         sum += (long) j * part.getItem2();
                     }
                     i += part.getItem1();
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }
}
