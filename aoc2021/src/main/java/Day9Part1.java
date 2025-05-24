import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day9Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(l -> Arrays.stream(l.split("")).map(Integer::valueOf).toList()).toList();
        var sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.getFirst().size(); j++) {
                var val = lines.get(i).get(j);
                boolean miss = false;
                var dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
                for (var dir : dirs) {
                    try {
                        if (val >= lines.get(i + dir[0]).get(j + dir[1])) {
                            miss = true;
                            break;
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                if (!miss)
                    sum += lines.get(i).get(j) + 1;
            }
        }
        System.out.println(sum);
    }
}
