import java.nio.file.Files;
import java.nio.file.Path;

public class Day1Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(Integer::valueOf).toList();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                for (int k = j + 1; k < lines.size(); k++) {
                    if (lines.get(i) + lines.get(j) + lines.get(k) == 2020) {
                        System.out.println(lines.get(i) * lines.get(j) * lines.get(k));
                    }
                }
            }
        }

    }
}
