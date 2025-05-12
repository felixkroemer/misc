import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day22Part1 {

    record E(boolean on, int fromZ, int toZ, int fromY, int toY, int fromX, int toX) {
    }

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        List<E> steps = new ArrayList<>();
        for (var line : lines) {
            var p = line.split(" ");
            var on = p[0].equals("on");
            var rest = p[1];
            var coords = rest.split(",");
            var fromZ = Integer.parseInt(coords[0].substring(2, coords[0].indexOf("..")));
            var toZ = Integer.parseInt(coords[0].substring(coords[0].indexOf("..") + 2));
            var fromY = Integer.parseInt(coords[1].substring(2, coords[1].indexOf("..")));
            var toY = Integer.parseInt(coords[1].substring(coords[1].indexOf("..") + 2));
            var fromX = Integer.parseInt(coords[2].substring(2, coords[2].indexOf("..")));
            var toX = Integer.parseInt(coords[2].substring(coords[2].indexOf("..") + 2));
            steps.add(new E(on, fromZ, toZ, fromY, toY, fromX, toX));
        }
        
        var map = new ArrayList<ArrayList<ArrayList<Boolean>>>();
        for (int i = 0; i < 101; i++) {
            var a = new ArrayList<ArrayList<Boolean>>();
            for (int j = 0; j < 101; j++) {
                var b = new ArrayList<Boolean>();
                for (int k = 0; k < 101; k++) {
                    b.add(false);
                }
                a.add(b);
            }
            map.add(a);
        }


        for (var step : steps) {

            if (step.fromZ > 50 || step.toZ < -50 || step.fromY > 50 || step.toY < -50 || step.fromX > 50 || step.toX < -50) {
                continue;
            }

            for (int i = step.fromZ; i <= step.toZ; i++) {
                for (int j = step.fromY; j <= step.toY; j++) {
                    for (int k = step.fromX; k <= step.toX; k++) {
                        try {
                            map.get(i + 50).get(j + 50).set(k + 50, step.on);
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }

        int on = 0;
        for (int i = -50; i <= 50; i++) {
            for (int j = -50; j <= 50; j++) {
                for (int k = -50; k <= 50; k++) {
                    if (map.get(i + 50).get(j + 50).get(k + 50)) {
                        on++;
                    }
                }
            }
        }
        System.out.println(on);


    }
}
