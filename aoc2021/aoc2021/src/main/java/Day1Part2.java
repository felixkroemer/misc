import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day1Part2 {

    public static void main(String[] args) throws Exception {
        var lines = new ArrayList<>(Files.readAllLines(Path.of("input"))).stream().map(Integer::valueOf).toList();
        int gr = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (i > 1 && i < lines.size() - 1) {
                var val = lines.get(i - 1) + lines.get(i) + lines.get(i + 1);
                if (val > lines.get(i - 2) + lines.get(i - 1) + lines.get(i)) {
                    gr++;
                }
            }
        }
        System.out.println(gr);
    }
}
