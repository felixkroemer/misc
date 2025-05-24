import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Day13Part2 {


    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int separator = IntStream.range(0, lines.size())
                .filter(i -> lines.get(i).isEmpty())
                .boxed()
                .toList().getFirst();
        var points = new ArrayList<>(lines.subList(0, separator).stream().map(l -> Arrays.stream(l.split(",")).map(Integer::valueOf).toList()).toList());
        var h = points.stream().max(Comparator.comparingInt(List::getLast)).map(List::getLast).get() + 1;
        var w = points.stream().max(Comparator.comparingInt(List::getFirst)).map(List::getFirst).get() + 1;

        var folds = lines.subList(separator + 1, lines.size());

        var map = new ArrayList<List<Integer>>();
        for (int i = 0; i < h; i++) {
            var a = new ArrayList<Integer>();
            map.add(a);
            for (int j = 0; j < w; j++) {
                a.add(0);
            }
        }
        for (var point : points) {
            map.get(point.getLast()).set(point.getFirst(), 1);
        }

        for (var f : folds) {
            var fold = f.split("=");
            var foldLine = Integer.parseInt(fold[1]);
            var foldDir = "" + fold[0].charAt(fold[0].length() - 1);

            if (foldDir.equals("y")) {
                for (int i = foldLine + 1; i < map.size(); i++) {
                    for (int j = 0; j < map.getFirst().size(); j++) {
                        if (map.get(i).get(j) == 1) {
                            map.get(foldLine - (i - foldLine)).set(j, 1);
                            map.get(i).set(j, 0);
                        }
                    }
                }
            } else {
                for (int i = 0; i < map.size(); i++) {
                    for (int j = foldLine + 1; j < map.getFirst().size(); j++) {
                        if (map.get(i).get(j) == 1) {
                            map.get(i).set(foldLine - (j - foldLine), 1);
                            map.get(i).set(j, 0);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                System.out.print(map.get(i).get(j) == 1 ? "#" : " ");
            }
            System.out.println();
        }
    }
}
