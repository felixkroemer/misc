import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                 var sum = 0;
                 Set<Tuple2<Integer, Integer>> visited = new HashSet<>();
                 for (int i = 0; i < map.size(); i++) {
                     for (int j = 0; j < map.get(0).size(); j++) {
                         if (!visited.contains(Tuple2.of(i, j))) {
                             var res = visit(i, j, map, visited);
                             sum += res.getItem1() * res.getItem2();
                         }
                     }
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }

    static Tuple2<Integer, Integer> visit(int x, int y, List<List<String>> map, Set<Tuple2<Integer, Integer>> visited) {
        visited.add(Tuple2.of(x, y));
        var fields = 1;
        var fence = 0;
        for (int[] dir : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            try {
                if (map.get(x).get(y).equals(map.get(x + dir[0]).get(y + dir[1]))) {
                    if (!visited.contains(Tuple2.of(x + dir[0], y + dir[1]))) {
                        var res = visit(x + dir[0], y + dir[1], map, visited);
                        fields += res.getItem1();
                        fence += res.getItem2();
                    }
                } else {
                    fence += 1;
                }
            } catch (IndexOutOfBoundsException e) {
                fence += 1;
            }
        }
        return Tuple2.of(fields, fence);
    }
}
