import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day6Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var nums = lines.getFirst().split(",");
        int sum = 0;
        int k = 80;
        for (var num : nums) {
            var x = Integer.parseInt(num);
            int s = p(x, k);
            System.out.println(s);
            sum += s;
        }
        System.out.println(sum);
    }

    static int p(int h, int l) {
        if(l< 0) {
            return 0;
        }
        int sum = 1;
        l -= h;
        while(l >= 0) {
            sum += p(8, l - 1);
            l -= 7;
        }
        return sum;
    }
}
