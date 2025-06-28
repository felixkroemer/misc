import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("nop") || lines.get(i).startsWith("jmp")) {
                solve(lines, i);
            }
        }
    }

    static void solve(List<String> lines, int patch) {
        var total = 0;
        int i = 0;
        Set<Integer> visited = new HashSet<>();
        while (true) {
            if(visited.contains(i)) {
                return;
            }
            visited.add(i);
            if (i == lines.size()) {
                System.out.println(total);
                return;
            }
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
                    if (i != patch) {
                        i += num;

                    } else {
                        i += 1;
                    }
                }
                case "nop" -> {
                    if (i != patch) {
                        i += 1;
                    } else {
                        i += num;
                    }
                }

            }
        }
    }

}

