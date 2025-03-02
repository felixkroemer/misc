import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day20Part1 {

    record Point(Tuple2<Integer, Integer> loc, int dist) {

    }

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                 int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                 Tuple2<Integer, Integer> start = null;
                 Tuple2<Integer, Integer> end = null;
                 PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.dist));
                 Map<Tuple2<Integer, Integer>, Integer> dist = new HashMap<>();
                 Set<Tuple2<Integer, Integer>> done = new HashSet<>();
                 for (int i = 0; i < map.size(); i++) {
                     for (int j = 0; j < map.getFirst().size(); j++) {
                         if (map.get(i).get(j).equals("S")) {
                             map.get(i).set(j, ".");
                             start = Tuple2.of(i, j);
                         }
                         if (map.get(i).get(j).equals("E")) {
                             map.get(i).set(j, ".");
                             end = Tuple2.of(i, j);
                         }
                     }
                 }
                 for (int i = 0; i < map.size(); i++) {
                     for (int j = 0; j < map.getFirst().size(); j++) {
                         if (map.get(i).get(j).equals(".")) {
                             pq.add(new Point(Tuple2.of(i, j), Integer.MAX_VALUE));
                             dist.put(Tuple2.of(i, j), Integer.MAX_VALUE);
                         }
                     }
                 }
                 pq.add(new Point(end, 0));
                 dist.put(end, 0);
                 while (!pq.isEmpty()) {
                     Point point = pq.remove();
                     if (done.contains(point.loc)) {
                         continue;
                     }
                     done.add(point.loc);
                     for (var dir : dirs) {
                         var newPoint = Tuple2.of(point.loc.getItem1() + dir[0], point.loc.getItem2() + dir[1]);
                         if (map.get(newPoint.getItem1()).get(newPoint.getItem2()).equals("#") || done.contains(newPoint)) {
                             continue;
                         }
                         var cost = dist.get(newPoint);
                         var newCost = point.dist + 1;
                         if (newCost < cost) {
                             pq.add(new Point(newPoint, newCost));
                             dist.put(newPoint, newCost);
                         }
                     }
                 }
                 HashMap<Integer, Integer> counter = new HashMap<>();
                 for (int i = 0; i < map.size(); i++) {
                     for (int j = 0; j < map.getFirst().size(); j++) {
                         if (map.get(i).get(j).equals(".")) {
                             for (var dir : dirs) {
                                 try {
                                     if (map.get(i + dir[0]).get(j + dir[1]).equals("#")) {
                                         if (map.get(i + dir[0] * 2).get(j + dir[1] * 2).equals(".")) {
                                             var diff = dist.get(Tuple2.of(i, j)) - dist.get(Tuple2.of(i + dir[0] * 2, j + dir[1] * 2));
                                             diff -= 2;
                                             if (diff >= 100) {
                                                 int val = counter.getOrDefault(diff, 0);
                                                 counter.put(diff, val + 1);
                                             }
                                         }
                                     }
                                 } catch (IndexOutOfBoundsException ignored) {
                                 }
                             }
                         }
                     }
                 }
                 var sum = 0;
                 for (var n : counter.values()) {
                     sum += n;
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }
}
