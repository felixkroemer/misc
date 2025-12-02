import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day2Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var line = lines.get(0);
        var parts = Arrays.stream(line.split(",")).map(s -> Arrays.stream(s.split("-")).toList()).toList();
        long c = 0;
        for(var part : parts) {
            var a = Long.parseLong(part.get(0));
            var b = Long.parseLong(part.get(1));
            for(long i = a; i<=b; i++) {
                var s = String.valueOf(i);
                var len = s.length();
                if(len % 2 == 1) {
                    continue;
                }
                if(s.substring(0, (len/2)).equals(s.substring((len/2)))) {
                    c += i;
                }
            }
        }
        System.out.println(c);
    }
}
