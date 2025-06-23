import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Set<Character> answers = new HashSet<>();
        for (char c = 'a'; c <= 'z'; c++) {
            answers.add(c);
        }
        int sum = 0;

        for (var line : lines) {
            if (line.isEmpty()) {
                sum += answers.size();
                answers.clear();
                for (char c = 'a'; c <= 'z'; c++) {
                    answers.add(c);
                }
                continue;
            }
            answers = answers.stream().filter(v -> line.contains("" + v)).collect(Collectors.toSet());
        }

        System.out.println(sum);

    }

}
