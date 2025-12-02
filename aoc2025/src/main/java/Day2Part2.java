import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var line = lines.get(0);
        var parts = Arrays.stream(line.split(",")).map(s -> Arrays.stream(s.split("-")).toList()).toList();
        long c = 0;
        Pattern p = Pattern.compile("^(\\d+)\\1+$");
        for(var part : parts) {
            var a = Long.parseLong(part.get(0));
            var b = Long.parseLong(part.get(1));
            for(long i = a; i<=b; i++) {
                var s = String.valueOf(i);
                if(p.matcher(s).find()) {
                    c += i;
                }
            }
        }
        System.out.println(c);
    }
}
