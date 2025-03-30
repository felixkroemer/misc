import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day7Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var crabs = Arrays.stream(lines.getFirst().split(",")).map(Integer::parseInt).toList();
        var max = crabs.stream().max(Integer::compareTo).get();
        long min = Long.MAX_VALUE;
        for (int i = 0; i <= max; i++) {
            long sum = 0;
            for (var c : crabs) {
                long diff = Math.abs(c - i);
                sum += diff * (diff + 1) / 2;
            }
            if (sum < min) {
                min = sum;
            }
        }
        System.out.println(min);
    }

}
