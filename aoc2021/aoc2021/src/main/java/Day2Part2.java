import java.nio.file.Files;
import java.nio.file.Path;

public class Day2Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int f = 0;
        int h = 0;
        int aim = 0;
        for (var line : lines) {
            int val = Integer.parseInt(line.split(" ")[1]);
            if (line.startsWith("forward")) {
                f += val;
                h += aim * val;
            } else if (line.startsWith("down")) {
                aim += val;
            } else {
                aim -= val;
            }
        }
        System.out.println(f * h);
    }
}
