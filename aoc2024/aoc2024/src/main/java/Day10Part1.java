import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
                .map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).collect(Collectors.toList())).collect().asList().map(map -> {
                    int count = 0;
                    for (int i = 0; i < map.size(); i++) {
                        for (var j = 0; j < map.get(0).size(); j++) {
                            if (map.get(i).get(j) == 0) {
                                Set<Tuple2<Integer, Integer>> positions = new HashSet<>();
                                search(map, new int[]{i, j}, 0, positions);
                                count += positions.size();
                            }
                        }
                    }
                    return count;
                }).subscribe().with(System.out::println);
    }

    static void search(List<List<Integer>> map, int[] pos, int num, Set<Tuple2<Integer, Integer>> positions) {
        if (num == 9) {
            positions.add(Tuple2.of(pos[0], pos[1]));
        }
        for (var d : new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}) {
            try {
                if (map.get(pos[0] + d[0]).get(pos[1] + d[1]) == num + 1) {
                    search(map, new int[]{pos[0] + d[0], pos[1] + d[1]}, num + 1, positions);
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }
}
