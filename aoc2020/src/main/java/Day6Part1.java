import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day6Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Set<Character> answers = new HashSet<>();
        int sum = 0;

        for (var line : lines) {
            if (line.isEmpty()) {
                sum += answers.size();
                answers.clear();
                continue;
            }
            for (int i = 0; i < line.length(); i++) {
                answers.add(line.charAt(i));
            }
        }

        System.out.println(sum);

    }

}
