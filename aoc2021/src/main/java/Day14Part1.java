import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Day14Part1 {


    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var pattern = lines.getFirst();

        HashMap<String, String> rules = new HashMap<>();
        for (var rule : lines.subList(2, lines.size())) {
            rules.put(rule.split("->")[0].strip(), rule.split("->")[1].strip());
        }

        var counts = new HashMap<String, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            counts.put("" + pattern.charAt(i), counts.getOrDefault("" + pattern.charAt(i), 0) + 1);
        }

        for (int i = 0; i < pattern.length() - 1; i++) {
            if (rules.containsKey(pattern.substring(i, i + 2))) {
                v(pattern.substring(i, i + 1), pattern.substring(i + 1, i + 2), 10, rules, counts);
            }
        }
        var vals = new ArrayList<>(counts.values());
        Collections.sort(vals);
        System.out.println(vals.getLast() - vals.getFirst());
    }


    static void v(String a, String b, int rest, Map<String, String> rules, Map<String, Integer> counts) {
        if (rest == 0) {
            return;
        }
        counts.put(rules.get(a + b), counts.getOrDefault(rules.get(a + b), 0) + 1);
        v(a, rules.get(a + b), rest - 1, rules, counts);
        v(rules.get(a + b), b, rest - 1, rules, counts);
    }
}
