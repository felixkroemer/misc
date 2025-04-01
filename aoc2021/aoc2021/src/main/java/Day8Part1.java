import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day8Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int sum = 0;
        for (var line : lines) {
            var inputs = Arrays.stream(line.split("\\|")[0].strip().split(" ")).toList();
            var outputs = Arrays.stream(line.split("\\|")[1].strip().split(" ")).toList();
            List<String> items = List.of("a", "b", "c", "d", "e", "f", "g");
            var possible = new HashMap<String, Set<String>>();
            for (var item : items) {
                possible.put(item, new HashSet<>(items));
            }
            for (var output : outputs) {
                var out = Arrays.stream(output.split("")).toList();
                switch (out.size()) {
                    case 2, 3, 4, 7:
                        sum++;
                    default:
                }
            }
        }
        System.out.println(sum);
    }
}
