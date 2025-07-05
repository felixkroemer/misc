import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day10Part2 {

    public static void main(String[] args) throws Exception {
        var nums = new ArrayList<>(Files.readAllLines(Path.of("input")).stream().map(Long::parseLong).toList());
        Map<Integer, Long> cache = new HashMap<>();
        Collections.sort(nums);
        nums.addFirst(0L);
        nums.add(nums.getLast() + 3);
        System.out.println(solve(nums, 0, cache));
    }


    static long solve(List<Long> nums, int idx, Map<Integer, Long> cache) {
        if(cache.containsKey(idx)) {
            return cache.get(idx);
        }
        if(idx == nums.size() - 1) {
            return 1;
        }
        long x = 0;
        for(int i = idx + 1; i< nums.size(); i++) {
            if(nums.get(i) - nums.get(idx) <= 3) {
                x += solve(nums, i, cache);
            } else {
                break;
            }
        }
        cache.put(idx, x);
        return x;
    }
}

