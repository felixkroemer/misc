import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Day6Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var nums = lines.getFirst().split(",");
        long sum = 0;
        int k = 256;
        for (var num : nums) {
            var x = Integer.parseInt(num);
            HashMap<Tuple2<Integer, Integer>, Long> cache = new HashMap<>();
            long s = p(x, k, cache);
            System.out.println(s);
            sum += s;
        }
        System.out.println(sum);
    }

    static long p(int h, int l, HashMap<Tuple2<Integer, Integer>, Long> cache) {
        var id = Tuple2.of(h, l);
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        if (l < 0) {
            cache.put(id, 0L);
            return 0;
        }
        long sum = 1;
        l -= h;
        while (l >= 0) {
            sum += p(8, l - 1, cache);
            l -= 7;
        }
        cache.put(id, sum);
        return sum;
    }
}
