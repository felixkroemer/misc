import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day15Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
                .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                    var splitIndex = map.indexOf(map.stream().filter(l -> l.size() == 1).findFirst().get());
                    var field = map.subList(0, splitIndex);
                    var movements = map.subList(splitIndex, map.size()).stream()
                            .flatMap(List::stream).filter(s -> !s.equals("\n") && !s.isEmpty())
                            .toList();

                    int[] head = new int[2];

                    for (int i = 0; i < field.size(); i++) {
                        for (int j = 0; j < field.get(0).size(); j++) {
                            if (field.get(i).get(j).equals("@")) {
                                head[0] = i;
                                head[1] = j;
                            }
                        }
                    }

                    var dirs = new HashMap<String, int[]>();
                    dirs.put("^", new int[]{-1, 0});
                    dirs.put(">", new int[]{0, 1});
                    dirs.put("<", new int[]{0, -1});
                    dirs.put("v", new int[]{1, 0});

                    for (var m : movements) {
                        int[] dir = dirs.get(m);
                        var next = new int[]{head[0], head[1]};
                        do {
                            next[0] += dir[0];
                            next[1] += dir[1];
                        } while (field.get(next[0]).get(next[1]).equals("O"));
                        if (field.get(next[0]).get(next[1]).equals(".")) {
                            if (next[0] - dir[0] == head[0] && next[1] - dir[1] == head[1]) {
                                field.get(head[0]).set(head[1], ".");
                                head[0] = next[0];
                                head[1] = next[1];
                                field.get(next[0]).set(next[1], "@");
                            } else {
                                field.get(head[0]).set(head[1], ".");
                                head[0] += dir[0];
                                head[1] += dir[1];
                                field.get(head[0]).set(head[1], "@");
                                field.get(next[0]).set(next[1], "O");
                            }
                        }
                    }
                    return field;
                }).map(field -> {
                    var sum = 0;
                    for (int i = 0; i < field.size(); i++) {
                        for (int j = 0; j < field.get(0).size(); j++) {
                            if (field.get(i).get(j).equals("O")) {
                                sum += 100 * i + j;
                            }
                        }
                    }
                    return sum;
                }).subscribe().with(System.out::println);
    }
}
