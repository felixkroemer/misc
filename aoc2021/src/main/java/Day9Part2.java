import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day9Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(l -> Arrays.stream(l.split("")).map(Integer::valueOf).toList()).toList();
        List<Integer> basins = new ArrayList<>();
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
                if (!miss) {
                    Set<Tuple2<Integer, Integer>> found = new HashSet<>();
                    Queue<Tuple2<Integer, Integer>> open = new LinkedList<>();
                    found.add(Tuple2.of(i, j));
                    open.add(Tuple2.of(i, j));
                    while (!open.isEmpty()) {
                        var pos = open.remove();
                        for (var dir : dirs) {
                            var n = Tuple2.of(pos.getItem1() + dir[0], pos.getItem2() + dir[1]);
                            try {
                                var nVal = lines.get(pos.getItem1() + dir[0]).get(pos.getItem2() + dir[1]);
                                if (nVal != 9 && !found.contains(n)) {
                                    found.add(n);
                                    open.add(n);
                                }
                            } catch (IndexOutOfBoundsException ignored) {
                            }
                        }
                    }
                    basins.add(found.size());
                }

            }
        }
        Collections.sort(basins);
        Collections.reverse(basins);
        System.out.println(basins.get(0) * basins.get(1) * basins.get(2));
    }
}
