import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        Set<Integer> visited = new HashSet<>();
        var total = 0;
        int i = 0;
        while (true) {
            if (visited.contains(i)) {
                System.out.println(total);
                return;
            }
            visited.add(i);
            var line = lines.get(i);
            var parts = line.split(" ");
            var cmd = parts[0];
            var num = Integer.parseInt(parts[1]);
            switch (cmd) {
                case "acc" -> {
                    total += num;
                    i++;
                }
                case "jmp" -> {
                    i += num;
                }
                default -> i += 1;

            }
        }
    }

}

