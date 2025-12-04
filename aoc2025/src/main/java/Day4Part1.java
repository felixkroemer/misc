import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import io.smallrye.mutiny.tuples.Tuple2;


public class Day4Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(l -> Arrays.asList(l.split(""))).toList();
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).size(); j++) {
                int c = 0;
                for (int[] dir : new int[][]{{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}}) {
                    try {
                        if (lines.get(i + dir[0]).get(j + dir[1]).equals("@")) {
                            c++;
                        }
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
                if (c < 4 && lines.get(i).get(j).equals("@")) {
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }
}
