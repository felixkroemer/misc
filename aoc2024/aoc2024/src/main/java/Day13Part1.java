import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Part1 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).group().intoLists().of(4).map(lines -> {
            int a = Integer.parseInt(lines.get(0).split("\\+")[1].split(",")[0]);
            int c = Integer.parseInt(lines.get(0).split("\\+")[2].trim());
            int b = Integer.parseInt(lines.get(1).split("\\+")[1].split(",")[0]);
            int d = Integer.parseInt(lines.get(1).split("\\+")[2].trim());
            int e = Integer.parseInt(lines.get(2).split("=")[1].split(",")[0]);
            int f = Integer.parseInt(lines.get(2).split("=")[2].trim());

            double determinant = a * d - b * c;
            double determinantA = e * d - b * f;
            double determinantB = a * f - e * c;
            var x = (determinantA / determinant);
            var y = (determinantB / determinant);
            if (x >= 0 && x == (int) x && y >= 0 && y == (int) y) {
                return ((int) x) * 3 + (int) y;
            } else {
                return 0;
            }
        }).collect().asList().map(list -> list.stream().reduce(0, Integer::sum)).subscribe().with(System.out::println);
    }
}
