import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part2 {

    static long WIDTH = 101;
    static long HEIGHT = 103;

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream()).map(line -> {
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            return new long[]{Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3)), Long.parseLong(matcher.group(4))};
        }).collect().asList().invoke(r -> {
            long i = 0;

            while (i < WIDTH * HEIGHT) {
                System.out.println(i);
                Set<Tuple2<Integer, Integer>> positions = new HashSet<>();
                for (var pos : r) {
                    int x = (int) ((((pos[0] + i * pos[2]) % WIDTH) + WIDTH) % WIDTH);
                    int y = (int) ((((pos[1] + i * pos[3]) % HEIGHT) + HEIGHT) % HEIGHT);
                    positions.add(Tuple2.of(x, y));
                }
                StringBuilder sb = new StringBuilder();
                for (int w = 0; w < HEIGHT; w++) {
                    for (int h = 0; h < WIDTH; h++) {
                        if (positions.contains(Tuple2.of(w, h))) {
                            sb.append("#");
                        } else {
                            sb.append(".");
                        }
                    }
                    sb.append("\n");
                }
                String s = sb.toString();
                if(s.contains("#######")) {
                    System.out.println(s);
                }
                i += 1;
            }
        }).subscribe().with(System.out::println);
    }
}
