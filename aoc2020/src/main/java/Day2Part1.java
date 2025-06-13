import java.nio.file.Files;
import java.nio.file.Path;

public class Day2Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int valid = 0;
        for (var line : lines) {
            var parts = line.split(" ");
            var fromTo = parts[0].split("-");
            var from = Integer.parseInt(fromTo[0]);
            var to = Integer.parseInt(fromTo[1]);
            var c = parts[1].substring(0, 1);
            var s = parts[2];
            var occurrences = s.chars().filter(v -> v == c.toCharArray()[0]).count();
            if (occurrences >= from && occurrences <= to) {
                valid++;
            }
        }
        System.out.println(valid);
    }
}
