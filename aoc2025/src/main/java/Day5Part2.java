import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;


public class Day5Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        long sum = 0;
        Set<Tuple2<Long, Long>> intervals = new HashSet<>();
        for (var line : lines) {
            if (line.isBlank()) {
                break;
            }
            var parts = line.split("-");
            long min = Long.parseLong(parts[0]);
            long max = Long.parseLong(parts[1]);
            var it = intervals.iterator();
            boolean insert = true;
            while (it.hasNext()) {
                var interval = it.next();
                if (min <= interval.getItem1() && max >= interval.getItem2()) {
                    it.remove();
                } else if (min >= interval.getItem1() && max <= interval.getItem2()) {
                    insert = false;
                    break;
                } else if (min <= interval.getItem1() && max >= interval.getItem1()) {
                    max = interval.getItem2();
                    it.remove();
                } else if (min <= interval.getItem2() && max >= interval.getItem2()) {
                    min = interval.getItem1();
                    it.remove();
                }
            }
            if(insert) {
                intervals.add(Tuple2.of(min, max));
            }
        }
        for (var interval : intervals) {
            sum += interval.getItem2() - interval.getItem1() + 1;
        }
        System.out.println(sum);
    }
}
