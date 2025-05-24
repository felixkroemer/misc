import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).filter(line -> !line.isEmpty())
             .collect().asList().map(list -> String.join("", list)).map(line -> {
                 var sum = 0;
                 Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)|(do\\(\\))|(don't\\(\\))");
                 Matcher matcher = pattern.matcher(line);
                 boolean enabled = true;
                 while (matcher.find()) {
                     if (matcher.group(1) != null) {
                         int l = Integer.parseInt(matcher.group(1));
                         int r = Integer.parseInt(matcher.group(2));
                         if(enabled) {
                             sum += l * r;
                         }
                     } else if (matcher.group(3) != null) {
                         enabled = true;
                     } else if (matcher.group(4) != null) {
                         enabled = false;
                     }
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }
}
