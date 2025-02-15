import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                 var sum = 0;
                 Set<Tuple2<Integer, Integer>> visited = new HashSet<>();
                 for (int i = 0; i < map.size(); i++) {
                     for (int j = 0; j < map.get(0).size(); j++) {
                         if (!visited.contains(Tuple2.of(i, j))) {
                             HashMap<Tuple3<Integer, Integer, Integer>, Integer> bounds = new HashMap<>();
                             var sumBounds = 0;
                             var res = visit(i, j, map, visited, bounds);
                             while (!bounds.isEmpty()) {
                                 var bound = bounds.entrySet().stream().findFirst().get();
                                 boolean vert = false;
                                 var up = Tuple3.of(bound.getKey().getItem1() - 1, bound.getKey().getItem2(), bound.getKey()
                                                                                                                   .getItem3());
                                 while (bounds.containsKey(up)) {
                                     vert = true;
                                     bounds.computeIfPresent(up, (k, v) -> v == 1 ? null : v - 1);
                                     up = Tuple3.of(up.getItem1() - 1, up.getItem2(), up.getItem3());
                                 }
                                 var down = Tuple3.of(bound.getKey().getItem1() + 1, bound.getKey()
                                                                                          .getItem2(), bound.getKey()
                                                                                                            .getItem3());
                                 while (bounds.containsKey(down)) {
                                     vert = true;
                                     bounds.computeIfPresent(down, (k, v) -> v == 1 ? null : v - 1);
                                     down = Tuple3.of(down.getItem1() + 1, down.getItem2(), down.getItem3());
                                 }

                                 if (!vert) {
                                     var right = Tuple3.of(bound.getKey().getItem1(), bound.getKey()
                                                                                           .getItem2() + 1, bound.getKey()
                                                                                                                 .getItem3());
                                     while (bounds.containsKey(right)) {
                                         bounds.computeIfPresent(right, (k, v) -> v == 1 ? null : v - 1);
                                         right = Tuple3.of(right.getItem1(), right.getItem2() + 1, right.getItem3());
                                     }

                                     var left = Tuple3.of(bound.getKey().getItem1(), bound.getKey()
                                                                                          .getItem2() - 1, bound.getKey()
                                                                                                                .getItem3());
                                     while (bounds.containsKey(left)) {
                                         bounds.computeIfPresent(left, (k, v) -> v == 1 ? null : v - 1);
                                         left = Tuple3.of(left.getItem1(), left.getItem2() - 1, left.getItem3());
                                     }
                                 }

                                 bounds.computeIfPresent(bound.getKey(), (k, v) -> v == 1 ? null : v - 1);
                                 sumBounds += 1;
                             }
                             sum += res.getItem1() * sumBounds;
                         }
                     }
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }

    static Tuple2<Integer, Integer> visit(int x, int y, List<List<String>> map, Set<Tuple2<Integer, Integer>> visited,
                                          HashMap<Tuple3<Integer, Integer, Integer>, Integer> bounds) {
        visited.add(Tuple2.of(x, y));
        var fields = 1;
        var fence = 0;
        for (int[] dir : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            try {
                if (map.get(x).get(y).equals(map.get(x + dir[0]).get(y + dir[1]))) {
                    if (!visited.contains(Tuple2.of(x + dir[0], y + dir[1]))) {
                        var res = visit(x + dir[0], y + dir[1], map, visited, bounds);
                        fields += res.getItem1();
                        fence += res.getItem2();
                    }
                } else {
                    fence += 1;
                    bounds.merge(Tuple3.of(x + dir[0], y + dir[1], Arrays.hashCode(dir)), 1, Integer::sum);
                }
            } catch (IndexOutOfBoundsException e) {
                fence += 1;
                bounds.merge(Tuple3.of(x + dir[0], y + dir[1], Arrays.hashCode(dir)), 1, Integer::sum);
            }
        }
        return Tuple2.of(fields, fence);
    }
}
