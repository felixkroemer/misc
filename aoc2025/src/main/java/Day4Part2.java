import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day4Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(l -> Arrays.asList(l.split(""))).toList();
        int sum = 0;
        while(true) {
            int s = 0;
            Set<Tuple2<Integer, Integer>> found = new HashSet<>();
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
                        found.add(Tuple2.of(i,j));
                    }
                }
            }
            for(var x : found) {
                lines.get(x.getItem1()).set(x.getItem2(), ".");
            }
            sum += found.size();
            if(found.isEmpty()) {
                break;
            }
        }
        System.out.println(sum);
    }
}
