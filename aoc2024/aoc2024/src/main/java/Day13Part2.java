import io.smallrye.mutiny.Multi;

import java.nio.file.Files;
import java.nio.file.Path;

public class Day13Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).group().intoLists().of(4).map(lines -> {
            long a = Long.parseLong(lines.get(0).split("\\+")[1].split(",")[0]);
            long c = Long.parseLong(lines.get(0).split("\\+")[2].trim());
            long b = Long.parseLong(lines.get(1).split("\\+")[1].split(",")[0]);
            long d = Long.parseLong(lines.get(1).split("\\+")[2].trim());
            long e = Long.parseLong(lines.get(2).split("=")[1].split(",")[0]) + 10000000000000L;
            long f = Long.parseLong(lines.get(2).split("=")[2].trim()) + 10000000000000L;

            double determinant = a * d - b * c;
            double determinantA = e * d - b * f;
            double determinantB = a * f - e * c;
            double x = (determinantA / determinant);
            double y = (determinantB / determinant);
            if (x >= 0 && x == (long) x && y >= 0 && y == (long) y) {
                return ((long) x) * 3 + (long) y;
            } else {
                return 0L;
            }
        }).collect().asList().map(list -> list.stream().reduce(0L, Long::sum)).subscribe().with(System.out::println);
    }
}
