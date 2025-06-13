import java.nio.file.Files;
import java.nio.file.Path;

public class Day2Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int valid = 0;
        for (var line : lines) {
            var parts = line.split(" ");
            var fromTo = parts[0].split("-");
            var from = Integer.parseInt(fromTo[0]);
            var to = Integer.parseInt(fromTo[1]);
            var c = parts[1].substring(0, 1).toCharArray()[0];
            var s = parts[2];
            if (s.charAt(from - 1) == c ^ s.charAt(to - 1) == c) {
                valid++;
            }
        }
        System.out.println(valid);
    }
}
