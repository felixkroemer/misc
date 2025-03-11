import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day18Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
                .map(line -> Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect().asList().map(incoming -> {
                    int w = 70;
                    int h = 70;
                    for (int j = 0; j < incoming.size(); j++) {
                        boolean[][] map = new boolean[h + 1][w + 1];
                        int i = 0;
                        for (var pos : incoming) {
                            if (i == j) break;
                            map[pos.get(1)][pos.get(0)] = true;
                            i++;
                        }

                        int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

                        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> parent = new HashMap<>();
                        Queue<Tuple2<Integer, Integer>> queue = new LinkedList<>();
                        queue.add(Tuple2.of(0, 0));
                        while (!queue.isEmpty()) {
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
                        if (!parent.containsKey(Tuple2.of(map.length - 1, map[0].length - 1))) {
                            return incoming.get(j - 1);
                        }
                    }
                    throw new RuntimeException();
                }).subscribe().with(System.out::println);
    }
}

