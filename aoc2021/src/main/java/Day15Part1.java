import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day15Part1 {


    public static void main(String[] args) throws Exception {
        var lines = new ArrayList<>(Files.readAllLines(Path.of("input")).stream().map(l -> new ArrayList<>(Arrays.stream(l.split("")).map(Integer::valueOf).toList())).toList());

        var dist = new ArrayList<List<Integer>>();
        for (int i = 0; i < lines.size(); i++) {
            var d = new ArrayList<Integer>();
            dist.add(d);
            for (int j = 0; j < lines.getFirst().size(); j++) {
                d.add(Integer.MAX_VALUE);
            }
        }

        dist.getFirst().set(0, 0);

        var queue = new PriorityQueue<Tuple2<Integer, Integer>>(Comparator.comparingInt(a -> dist.get(a.getItem1()).get(a.getItem2())));
        queue.add(Tuple2.of(0, 0));


        while (!queue.isEmpty()) {
            var pos = queue.remove();
            for (var dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                try {
                    var newDist = dist.get(pos.getItem1()).get(pos.getItem2()) + lines.get(pos.getItem1() + dir[0]).get(pos.getItem2() + dir[1]);
                    if (newDist < dist.get(pos.getItem1() + dir[0]).get(pos.getItem2() + dir[1])) {
                        dist.get(pos.getItem1() + dir[0]).set(pos.getItem2() + dir[1], newDist);
                        queue.add(Tuple2.of(pos.getItem1() + dir[0], pos.getItem2() + dir[1]));
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }

        System.out.println(dist.getLast().getLast());
    }


}
