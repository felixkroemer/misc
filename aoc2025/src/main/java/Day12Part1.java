import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

record S(List<Integer> shapes, int width, int height) {
}

public class Day12Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        lines = lines.subList(lines.indexOf(lines.stream().filter(l -> l.length() > 6).findFirst().get()), lines.size());

        var sum = 0;

        var c = new ArrayList<S>();
        c.add(new S(List.of(1, 0, 1, 0, 1, 0), 6, 3));
        c.add(new S(List.of(0, 0, 2, 0, 0, 0), 5, 3));
        c.add(new S(List.of(2, 0, 0, 0, 2, 0), 8, 3));

        for (var line : lines) {
            var parts = List.of(line.split(" "));
            var size = parts.getFirst().substring(0, parts.getFirst().length() - 1).split("x");
            var height = Integer.parseInt(size[0]);
            var width = Integer.parseInt(size[1]);
            var counters = new ArrayList<>(parts.subList(1, parts.size()).stream().map(Integer::parseInt).toList());

/*            boolean found;
            do {
                found = false;
                for (var shape : c) {
                    
                    boolean modifyHeight = false;
                    boolean modifyWidth = false;
                    int w = 0;
                    int h = 0;
                    
                    if(height % shape.height() == 0 && height >= shape.height()) {
                        modifyWidth = true;
                        w = shape.width();
                        h = shape.height();
                    } else if(height % shape.width() == 0 && height >= shape.width()) {
                        modifyWidth = true;
                        w = shape.height();
                        h = shape.width();
                    } else if(width % shape.width() == 0 && width >= shape.width()) {
                        modifyHeight = true;
                        w = shape.width();
                        h = shape.height();
                    } else if(width % shape.height() == 0 && width >= shape.height()) {
                        modifyHeight = true;
                        w = shape.height();
                        h = shape.width();
                    }
                    
                    if (modifyHeight || modifyWidth) {
                        var m = (modifyHeight ? width : height) / (modifyHeight ? w : h);
                        boolean allMatch = true;
                        for (int i = 0; i < 6; i++) {
                            if (counters.get(i) < shape.shapes().get(i) * m) {
                                allMatch = false;
                                break;
                            }
                        }
                        if (allMatch) {
                            found = true;
                            for (int i = 0; i < 6; i++) {
                                counters.set(i, counters.get(i) - shape.shapes().get(i) * m);
                            }
                            if (modifyHeight) {
                                height -= h;
                            }
                            if (modifyWidth) {
                                width -= w;
                            }
                        }
                    }
                }
            } while (found);*/

            int max3TileSquares = (height / 3) * (width / 3);
            int min3TileSquares = 0;
            for (var counter : counters) {
                min3TileSquares += counter;
            }
            if (max3TileSquares >= min3TileSquares) {
                sum++;
                continue;
            }

            var h = new HashMap<Integer, Integer>();
            h.put(0, 5);
            h.put(1, 7);
            h.put(2, 6);
            h.put(3, 7);
            h.put(4, 7);
            h.put(5, 7);
            var squareSum = 0;
            for (int i = 0; i < counters.size(); i++) {
                squareSum += counters.get(i) * h.get(i);
            }
            int maxSquares = height * width;
            if (squareSum > maxSquares) {
                continue;
            }
            System.out.println();
        }
        System.out.println(sum);
    }

}
