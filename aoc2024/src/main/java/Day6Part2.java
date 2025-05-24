import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

public class Day6Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                 int[] startPos = null;
                 for (int i = 0; i < map.size(); i++) {
                     for (var j = 0; j < map.get(0).size(); j++) {
                         if (map.get(i).get(j).equals("^")) {
                             startPos = new int[]{i, j};
                             break;
                         }
                     }
                     if (startPos != null)
                         break;
                 }
                 int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                 int[] playerPos = new int[]{startPos[0], startPos[1]};
                 int dir = 0;
                 HashSet<Tuple2<Integer, Integer>> visited = new HashSet<>();

                 while (true) {
                     try {
                         var newPos = new int[]{playerPos[0] + dirs[dir][0], playerPos[1] + dirs[dir][1]};
                         while (map.get(newPos[0]).get(newPos[1]).equals("#")) {
                             dir = (dir + 1) % 4;
                             newPos = new int[]{playerPos[0] + dirs[dir][0], playerPos[1] + dirs[dir][1]};
                         }
                         playerPos = newPos;
                         visited.add(Tuple2.of(playerPos[0], playerPos[1]));
                     } catch (IndexOutOfBoundsException ignored) {
                         break;
                     }
                 }
                 visited.remove(Tuple2.of(startPos[0], startPos[1]));
                 var found = new HashSet<Tuple2<Integer, Integer>>();
                 for (var pos : visited) {
                     playerPos = new int[]{startPos[0], startPos[1]};
                     dir = 0;
                     map.get(pos.getItem1()).set(pos.getItem2(), "#");
                     HashSet<Tuple3<Integer, Integer, Integer>> v = new HashSet<>();

                     while (true) {
                         try {
                             var newPos = new int[]{playerPos[0] + dirs[dir][0], playerPos[1] + dirs[dir][1]};
                             while (map.get(newPos[0]).get(newPos[1]).equals("#")) {
                                 dir = (dir + 1) % 4;
                                 newPos = new int[]{playerPos[0] + dirs[dir][0], playerPos[1] + dirs[dir][1]};
                             }
                             playerPos = newPos;
                             if (v.contains(Tuple3.of(playerPos[0], playerPos[1], dir))) {
                                 found.add(Tuple2.of(pos.getItem1(), pos.getItem2()));
                                 break;
                             } else {
                                 v.add(Tuple3.of(playerPos[0], playerPos[1], dir));
                             }
                         } catch (IndexOutOfBoundsException ignored) {
                             break;
                         }
                     }

                     map.get(pos.getItem1()).set(pos.getItem2(), ".");
                 }
                 return found.size();
             }).subscribe()

             .with(System.out::println);
    }
}
