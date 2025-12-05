import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Day5Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int sum = 0;
        Set<Tuple2<Long, Long>> intervals =  new HashSet<>();
        for(var line : lines) {
            if(line.contains("-")) {
                var parts = line.split("-");
                intervals.add(Tuple2.of(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            } else {
                if(line.isBlank()) continue;
                var val = Long.parseLong(line);
                for(var interval : intervals) {
                    if(val >= interval.getItem1() && val <= interval.getItem2()) {
                        sum++;
                        break;
                    }
                }
            }
        }
        System.out.println(sum);
    }
}
