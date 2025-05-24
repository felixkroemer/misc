import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day11Part2 {


    public static void main(String[] args) throws Exception {
        var lines = new ArrayList<>(Files.readAllLines(Path.of("input")).stream().map(l -> new ArrayList<>(Arrays.stream(l.split("")).map(Integer::valueOf).toList())).toList());
        var size = lines.size() * lines.getFirst().size();
        var r = 0;
        while (true) {
            Queue<Tuple2<Integer, Integer>> toFlash = new ArrayDeque<>();
            Set<Tuple2<Integer, Integer>> alreadyFlashed = new HashSet<>();
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.getFirst().size(); j++) {
                    if (lines.get(i).get(j) == 9) {
                        toFlash.add(Tuple2.of(i, j));
                    } else {
                        lines.get(i).set(j, lines.get(i).get(j) + 1);
                    }
                }
            }
            while (!toFlash.isEmpty()) {
                var item = toFlash.remove();
                for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, -1}, {1, 1}, {1, -1}, {-1, 1}}) {
                    try {
                        var y = item.getItem1() + dir[0];
                        var x = item.getItem2() + dir[1];
                        if (!alreadyFlashed.contains(Tuple2.of(y, x)) && !toFlash.contains(Tuple2.of(y, x))) {
                            if (lines.get(y).get(x) == 9) {
                                toFlash.add(Tuple2.of(y, x));
                            } else {
                                lines.get(y).set(x, lines.get(y).get(x) + 1);
                            }
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                lines.get(item.getItem1()).set(item.getItem2(), 0);
                alreadyFlashed.add(Tuple2.of(item.getItem1(), item.getItem2()));
            }
            r++;
            if (alreadyFlashed.size() == size) {
                System.out.println(r);
                break;
            }
        }
    }
}
