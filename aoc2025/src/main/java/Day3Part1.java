import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day3Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(l -> Arrays.stream(l.split("")).map(Integer::parseInt).toList()).toList();
        int sum = 0;
        for(var line : lines) {
            int a = line.size() - 2;
            int b = line.size() - 1;
            for(int i = line.size() - 2; i>=0; i--) {
                if(line.get(i) >= line.get(a)) {
                    a = i;
                }
            }
            for(int i = line.size() - 1; i > a; i--) {
                if(line.get(i) >= line.get(b)) {
                    b = i;
                }
            }
            sum += (line.get(a) * 10 + line.get(b));
        }
        System.out.println(sum);
    }
}
