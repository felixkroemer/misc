import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day18Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
                .map(line -> Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect().asList().map(incoming -> {
                    int w = 70;
                    int h = 70;
                    int number = 1024;
                    boolean[][] map = new boolean[h + 1][w + 1];
                    int i = 0;
                    for (var pos : incoming) {
                        map[pos.get(1)][pos.get(0)] = true;
                        i++;
                        if (i == number) break;
                    }

                    int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

                    Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> parent = new HashMap<>();
                    Queue<Tuple2<Integer, Integer>> queue = new LinkedList<>();
                    queue.add(Tuple2.of(0, 0));
                    while (true) {
                        var pos = queue.remove();
                        if (pos.getItem1() == map.length - 1 && pos.getItem2() == map[0].length - 1) {
                            break;
                        }
                        for (var dir : dirs) {
                            try {
                                var y = pos.getItem1() + dir[0];
                                var x = pos.getItem2() + dir[1];
                                if (!map[y][x]) {
                                    if (!parent.containsKey(Tuple2.of(y, x))) {
                                        queue.add(Tuple2.of(y, x));
                                        parent.put(Tuple2.of(y, x), pos);
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException ignored) {
                            }
                        }
                    }
                    Set<Tuple2<Integer, Integer>> path = new HashSet<>();
                    var pos = Tuple2.of(map.length - 1, map[0].length - 1);
                    path.add(pos);
                    int c = 0;
                    while (pos.getItem1() != 0 || pos.getItem2() != 0) {
                        pos = parent.get(Tuple2.of(pos.getItem1(), pos.getItem2()));
                        path.add(pos);
                        c++;
                    }

                    for (int y = 0; y < map.length; y++) {
                        for (int x = 0; x < map[0].length; x++) {
                            if (path.contains(Tuple2.of(y, x))) {
                                System.out.print("O");
                            } else if (map[y][x]) {
                                System.out.print("#");
                            } else {
                                System.out.print(".");
                            }
                        }
                        System.out.println();
                    }

                    return c;
                }).subscribe().with(System.out::println);
    }
}

