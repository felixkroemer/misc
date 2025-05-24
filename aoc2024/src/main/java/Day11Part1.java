import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11Part1 {

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input")).get(0).split(" "))
                .map(arr -> new LinkedList<>(Arrays.stream(arr).map(Long::parseLong).collect(Collectors.toList()))).map(input -> {
                    int sum = 0;
                    HashMap<Long, Tuple2<Long, Long>> found = new HashMap<>();
                    for (var val : input) {
                        var l = new LinkedList<>(List.of(val));
                        for (int i = 0; i < 25; i++) {
                            int index = 0;
                            do {
                                if (l.get(index) == 0) {
                                    l.set(index, 1L);
                                    index++;
                                } else if ((l.get(index) == 0 ? 1 : (int) Math.log10(Math.abs(l.get(index))) + 1) % 2 == 0) {
                                    var b = l.get(index);
                                    long left, right;
                                    if (found.containsKey(b)) {
                                        var a = found.get(b);
                                        left = a.getItem1();
                                        right = a.getItem2();
                                    } else {
                                        var s = String.valueOf(l.get(index));
                                        right = Long.parseLong(s.substring(s.length() / 2));
                                        left = Long.parseLong(s.substring(0, s.length() / 2));
                                        found.put(b, Tuple2.of(left, right));
                                    }
                                    l.add(index + 1, right);
                                    l.set(index, left);
                                    index += 2;

                                } else {
                                    l.set(index, l.get(index) * 2024);
                                    index++;
                                }
                            } while (index != l.size());
                            System.out.println(i + " " + l.size());
                        }
                        sum += l.size();
                    }
                    return sum;
                }).subscribe().with(System.out::println);
    }

}
