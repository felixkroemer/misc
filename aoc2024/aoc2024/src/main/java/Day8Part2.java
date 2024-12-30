import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day8Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                 Map<String, List<Tuple2<Integer, Integer>>> positions = new HashMap<>();
                 for (int i = 0; i < map.size(); i++) {
                     for (int j = 0; j < map.get(0).size(); j++) {
                         if (!map.get(i).get(j).equals(".")) {
                             positions.computeIfAbsent(map.get(i).get(j), k -> new ArrayList<>()).add(Tuple2.of(i, j));
                         }
                     }
                 }
                 var found = new HashSet<Tuple2<Integer, Integer>>();
                 for (var k : positions.keySet()) {
                     for (var pos1 : positions.get(k)) {
                         found.add(Tuple2.of(pos1.getItem1(), pos1.getItem2()));
                         for (var pos2 : positions.get(k)) {
                             if (pos1 == pos2) {
                                 continue;
                             } else {
                                 int[] diff = new int[]{pos2.getItem1() - pos1.getItem1(), pos2.getItem2() - pos1.getItem2()};
                                 int mult = 1;
                                 int[] res1 = new int[]{pos2.getItem1() + diff[0] * mult, pos2.getItem2() + diff[1] * mult};
                                 while (res1[0] >= 0 && res1[0] < map.size() && res1[1] >= 0 && res1[1] < map.get(0)
                                                                                                             .size()) {
                                     found.add(Tuple2.of(res1[0], res1[1]));
                                     mult++;
                                     res1 = new int[]{pos2.getItem1() + diff[0] * mult, pos2.getItem2() + diff[1] * mult};
                                 }
                                 mult = 1;
                                 int[] res2 = new int[]{pos1.getItem1() - diff[0] * mult, pos1.getItem2() - diff[1] * mult};
                                 while (res2[0] >= 0 && res2[0] < map.size() && res2[1] >= 0 && res2[1] < map.get(0)
                                                                                                             .size()) {
                                     found.add(Tuple2.of(res2[0], res2[1]));
                                     mult++;
                                     res2 = new int[]{pos1.getItem1() - diff[0] * mult, pos1.getItem2() - diff[1] * mult};
                                 }
                             }
                         }
                     }
                 }
                 return found.size();
             }).subscribe().with(System.out::println);
    }
}
