import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).getFirst().split(",");
        Map<Integer, List<Integer>> prev = new HashMap<>();
        int i = 1;
        int prevNumer = -1;
        for (var line : lines) {
            var num = Integer.parseInt(line);
            if (!prev.containsKey(num)) {
                prev.put(num, new ArrayList<>());
            }
            prev.get(num).add(i);
            prevNumer = num;
            i++;
        }

        while (i <= 30000000) {
            if (prev.get(prevNumer).size() > 1) {
                var diff = i - 1 - prev.get(prevNumer).get(prev.get(prevNumer).size() - 2);
                if (!prev.containsKey(diff)) {
                    prev.put(diff, new ArrayList<>());
                }
                prev.get(diff).add(i);
                prevNumer = diff;
            } else {
                prevNumer = 0;
                if (!prev.containsKey(0)) {
                    prev.put(0, new ArrayList<>());
                }
                prev.get(0).add(i);
            }
            i++;
        }
        System.out.println(prevNumer);
    }


}

