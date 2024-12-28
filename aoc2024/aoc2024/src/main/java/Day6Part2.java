import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day6Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                 int[] playerPos = null;
                 for (int i = 0; i < map.size(); i++) {
                     for (var j = 0; j < map.get(0).size(); j++) {
                         if (map.get(i).get(j).equals("^")) {
                             playerPos = new int[]{i, j};
                             break;
                         }
                     }
                     if (playerPos != null)
                         break;
                 }
                 int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                 int dir = 0;

                 try {
                     while (true) {
                         var newPos = new int[]{playerPos[0] + dirs[dir][0], playerPos[1] + dirs[dir][1]};
                         if (map.get(newPos[0]).get(newPos[1]).equals("#")) {
                             dir = (dir + 1) % 4;
                             newPos = new int[]{playerPos[0] + dirs[dir][0], playerPos[1] + dirs[dir][1]};
                         }
                         playerPos = newPos;
                         map.get(playerPos[0]).set(playerPos[1], "^");
                     }
                 } catch (IndexOutOfBoundsException ignored) {
                 }

                 int sum = 0;
                 for (var row : map) {
                     for (var pos : row) {
                         if (pos.equals("^")) {
                             sum++;
                         }
                     }
                 }
                 return sum;
             }).subscribe()

             .with(System.out::println);
    }
}
