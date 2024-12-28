import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).filter(line -> !line.isEmpty())
             .collect().asList().map(list -> String.join("", list)).map(line -> {
                 var sum = 0;
                 Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
                 Matcher matcher = pattern.matcher(line);
                 while (matcher.find()) {
                     int l = Integer.parseInt(matcher.group(1));
                     int r = Integer.parseInt(matcher.group(2));
                     sum += l * r;
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }
}
