import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day4Part1 {

    public static int find(List<List<String>> map, int y, int x) {
        var xmas = new String[]{"X", "M", "A", "S"};
        int found = 0;
        for (var i : new int[][]{{-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}}) {
            boolean err = false;
            for (int j = 1; j<4; j++) {
                try {
                    if (!map.get(y + i[0] * j).get(x + i[1] * j).equals(xmas[j])) {
                        err = true;
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    err = true;
                    break;
                }
            }
            if(!err) {
                found += 1;
            }
        }
        return found;
    }

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).filter(line -> !line.isEmpty())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(line -> {
                 var sum = 0;
                 for (int i = 0; i < line.size(); i++) {
                     for (int j = 0; j < line.get(0).size(); j++) {
                         if (line.get(i).get(j).equals("X")) {
                             sum += find(line, i, j);
                         }
                     }
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }
}
