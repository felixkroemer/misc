import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Map<Tuple2<Double, Double>, Boolean> tiles = new HashMap<>();
        
        for (var line : lines) {
            int idx = 0;
            double xPos = 0;
            double yPos = 0;
            while(idx < line.length() ) {
                var s = line.substring(idx);
                if (s.startsWith("sw")) {
                    idx += 2;
                    yPos -= 0.5;
                    xPos -= 0.5;
                } else if (s.startsWith("se")) {
                    idx += 2;
                    yPos -= 0.5;
                    xPos += 0.5;
                } else if (s.startsWith("nw")) {
                    idx += 2;
                    yPos += 0.5;
                    xPos -= 0.5;
                } else if (s.startsWith("ne")) {
                    idx += 2;
                    yPos += 0.5;
                    xPos += 0.5;
                } else if (s.startsWith("e")) {
                    idx += 1;
                    xPos += 1;
                } else if (s.startsWith("w")) {
                    idx += 1;
                    xPos -= 1;
                }
            }
            tiles.compute(Tuple2.of(yPos, xPos), (k, v) -> v == null || !v);
        }
        var dirs = new double[][] {{0.5, 0.5}, {-0.5, 0.5}, {0.5, -0.5}, {-0.5, -0.5}, {0, 1}, {0, -1}};
        var tileSet = tiles.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toSet());
        System.out.println(tileSet.size());
        for(int i = 0; i<100; i++){
            Set<Tuple2<Double, Double>> blacks = new HashSet<>();
            Map<Tuple2<Double, Double>, Integer> whites = new HashMap<>();
            for(var pos : tileSet) {
                var count = 0;
                for(var dir : dirs) {
                    var y = pos.getItem1() + dir[0];
                    var x = pos.getItem2() + dir[1];
                    if(!tileSet.contains(Tuple2.of(y, x))) {
                        whites.compute(Tuple2.of(y,x), (k,v) -> v == null ? 1 : (v + 1));
                    } else {
                        count++;
                    }
                }
                if(count == 1 || count == 2) {
                    blacks.add(pos);
                }
            }
            blacks.addAll(whites.entrySet().stream().filter(e -> e.getValue() == 2).map(Map.Entry::getKey).toList());
            tileSet = blacks;
            System.out.println(tileSet.size());
        }
    }
}
