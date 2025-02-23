import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part1 {

    static int WIDTH = 101;
    static int HEIGHT = 103;

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).map(line -> {
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            return new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))};
        }).map(r -> {
            int x = (((r[0] + 100 * r[2]) % WIDTH) + WIDTH) % WIDTH;
            int y = (((r[1] + 100 * r[3]) % HEIGHT) + HEIGHT) % HEIGHT;
            return Tuple2.of(x, y);
        }).collect().asList().map(positions -> {
            long tl = 0;
            long tr = 0;
            long bl = 0;
            long br = 0;
            for (var pos : positions) {
                if (pos.getItem1() < WIDTH / 2 && pos.getItem2() < HEIGHT / 2) {
                    tl += 1;
                } else if (pos.getItem1() > WIDTH / 2 && pos.getItem2() < HEIGHT / 2) {
                    tr += 1;
                } else if (pos.getItem1() < WIDTH / 2 && pos.getItem2() > HEIGHT / 2) {
                    bl += 1;
                } else if (pos.getItem1() > WIDTH / 2 && pos.getItem2() > HEIGHT / 2) {
                    br += 1;
                }
            }
            return tl * tr * bl * br;
        }).subscribe().with(System.out::println);
    }
}
