import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;


public class Day6Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(s -> s.trim().split("\\s+")).toList();
        long sum = 0;
        for (int i = 0; i < lines.get(0).length; i++) {
            boolean mult = lines.get(lines.size() - 1)[i].equals("*");
            long res = mult ? 1 : 0;
            for (int j = 0; j < lines.size() - 1; j++) {
                if (mult) {
                    res *= Integer.parseInt(lines.get(j)[i]);
                } else {
                    res += Integer.parseInt(lines.get(j)[i]);
                }
            }
            sum += res;
        }

        System.out.println(sum);
    }
}
