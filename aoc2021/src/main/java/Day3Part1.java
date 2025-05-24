import java.nio.file.Files;
import java.nio.file.Path;

public class Day3Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int a = 0;
        int b = 0;
        for (int i = 0; i < lines.getFirst().length(); i++) {
            int n = 0;
            for (var line : lines) {
                if (line.charAt(i) == '1') {
                    n++;
                }
            }
            if (n > lines.size() / 2) {
                a += 1 << lines.getFirst().length() - i - 1;
            } else {
                b += 1 << lines.getFirst().length() - i - 1;
            }
        }
        System.out.println(a * b);
    }
}
