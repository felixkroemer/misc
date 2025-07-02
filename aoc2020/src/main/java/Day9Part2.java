import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

public class Day9Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(Long::parseLong).toList();
        var val = 10884537;

        var start = 0;
        var end = 0;
        while(true) {
            var sum = 0;
            for(int i = start; i<=end; i++) {
                sum += lines.get(i);
            }
            if(sum > val) {
                start++;
            } else if (sum < val) {
                end++;
            } else {
                var subList = lines.subList(start, end);
                System.out.println(Collections.min(subList) + Collections.max(subList));
                return;
            }
        }

    }


}

