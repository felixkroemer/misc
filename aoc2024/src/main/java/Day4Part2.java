import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day4Part2 {

    public static boolean find(List<List<String>> map, int y, int x) {
        int[][] configs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (var config : configs) {
            try {
                if (map.get(y + (config[0] == 0 ? -1 : config[0])).get(x + (config[1] == 0 ? -1 : config[1]))
                       .equals("M") && map.get(y + (config[0] == 0 ? 1 : config[0]))
                                          .get(x + (config[1] == 0 ? 1 : config[1]))
                                          .equals("M") && map.get(y - (config[0] == 0 ? -1 : config[0]))
                                                             .get(x - (config[1] == 0 ? -1 : config[1]))
                                                             .equals("S") && map.get(y - (config[0] == 0 ? 1 : config[0]))
                                                                                .get(x - (config[1] == 0 ? 1 : config[1]))
                                                                                .equals("S")) {
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).filter(line -> !line.isEmpty())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(line -> {
                 var sum = 0;
                 for (int i = 0; i < line.size(); i++) {
                     for (int j = 0; j < line.get(0).size(); j++) {
                         if (line.get(i).get(j).equals("A")) {
                             sum += find(line, i, j) ? 1 : 0;
                         }
                     }
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }
}
