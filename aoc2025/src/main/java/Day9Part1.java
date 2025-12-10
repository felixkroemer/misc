import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day9Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var points = new ArrayList<Tuple2<Integer, Integer>>();
        for(var line : lines) {
            var parts = line.split(",");
            points.add(Tuple2.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
        long max = 0;
        for(var a : points) {
            for(var b : points) {
                if(!a.equals(b)) {
                    long size = (Math.abs(a.getItem1() - b.getItem1()) + 1L) * (Math.abs(a.getItem2() - b.getItem2()) + 1L);
                    if(size > max) {
                        max = size;
                    }
                }
            }
        }
        System.out.println(max);
    }
}
