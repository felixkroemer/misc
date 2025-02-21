import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day16Part1 {

    enum DIR {
        NORTH, EAST, SOUTH, WEST
    }

    record Point(Tuple2<Integer, Integer> loc, DIR dir) {

    }

    record PointDist(Point loc, int dist) {

    }

    record Edge(Point loc, int cost) {

    }

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                 int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                 Tuple2<Integer, Integer> start = null;
                 Tuple2<Integer, Integer> end = null;
                 PriorityQueue<PointDist> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.dist));
                 Map<Point, Integer> dist = new HashMap<>();
                 Map<Point, Set<Edge>> edges = new HashMap<>();
                 Set<Point> done = new HashSet<>();
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
                             for (var dir : DIR.values()) {
                                 var point = new Point(Tuple2.of(i, j), dir);
                                 pq.add(new PointDist(point, Integer.MAX_VALUE));
                                 dist.put(point, Integer.MAX_VALUE);
                                 edges.computeIfAbsent(point, k -> new HashSet<>());
                                 edges.get(point)
                                      .add(new Edge(new Point(Tuple2.of(i, j), DIR.values()[(dir.ordinal() + 1) % 4]), 1000));
                                 edges.get(point)
                                      .add(new Edge(new Point(Tuple2.of(i, j), DIR.values()[(((dir.ordinal() - 1) % 4) + 4) % 4]), 1000));
                                 if (map.get(i + dirs[dir.ordinal()][0]).get(j + dirs[dir.ordinal()][1]).equals(".")) {
                                     edges.get(point)
                                          .add(new Edge(new Point(Tuple2.of(i + dirs[dir.ordinal()][0], j + dirs[dir.ordinal()][1]), dir), 1));
                                 }
                             }
                         }
                     }
                 }
                 pq.add(new PointDist(new Point(start, DIR.EAST), 0));
                 while (!pq.isEmpty()) {
                     PointDist pointDist = pq.remove();
                     if (done.contains(pointDist.loc)) {
                         continue;
                     }
                     done.add(pointDist.loc);
                     for (var edge : edges.get(pointDist.loc)) {
                         if (done.contains(edge.loc)) {
                             continue;
                         }
                         var cost = dist.get(edge.loc);
                         var newCost = pointDist.dist + edge.cost;
                         if (newCost < cost) {
                             pq.add(new PointDist(edge.loc, newCost));
                             dist.put(edge.loc, newCost);
                         }
                     }
                 }
                 var min = Integer.MAX_VALUE;
                 for (var dir : DIR.values()) {
                     var val = dist.get(new Point(end, dir));
                     if (val < min) {
                         min = val;
                     }
                 }
                 return min;
             }).subscribe().with(System.out::println);
    }
}
