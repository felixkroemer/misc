import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day10Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
                .map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).collect(Collectors.toList())).collect().asList().map(map -> {
                    int count = 0;
                    for (int i = 0; i < map.size(); i++) {
                        for (var j = 0; j < map.get(0).size(); j++) {
                            if (map.get(i).get(j) == 0) {
                                count += p(map, new int[]{i, j}, 0);
                            }
                        }
                    }
                    return count;
                }).subscribe().with(System.out::println);
    }

    static int p(List<List<Integer>> map, int[] pos, int num) {
        if (num == 9) {
            return 1;
        }
        int count = 0;
        for (var d : new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}) {
            try {
                if (map.get(pos[0] + d[0]).get(pos[1] + d[1]) == num + 1) {
                    count += p(map, new int[]{pos[0] + d[0], pos[1] + d[1]}, num + 1);
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        return count;
    }
}
