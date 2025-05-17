import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day22Part2 {

    record E(boolean on, int fromZ, int toZ, int fromY, int toY, int fromX, int toX) {
    }

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        List<E> cubes = new ArrayList<>();

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
            var newCube = new E(on, fromZ, toZ, fromY, toY, fromX, toX);
            var it = cubes.iterator();
            var splitCubes = new HashSet<E>();
            while (it.hasNext()) {
                var cube = it.next();
                if (intersects(cube, newCube)) {
                    var s = split(cube, newCube);
                    it.remove();
                    splitCubes.addAll(s);
                }
            }
            cubes.add(newCube);
            cubes.addAll(splitCubes);
        }

        long sum = 0;
        for (var cube : cubes) {
            if (cube.on) {
                sum += (long) (cube.toX - cube.fromX + 1) *
                        (long) (cube.toY - cube.fromY + 1) *
                        (long) (cube.toZ - cube.fromZ + 1);
            }
        }
        System.out.println(sum);
    }


    static boolean intersects(E e, E n) {
        return e.fromZ <= n.toZ && n.fromZ <= e.toZ && e.fromY <= n.toY && n.fromY <= e.toY && e.fromX <= n.toX && n.fromX <= e.toX;
    }

    static Set<E> split(E e, E n) {
        Set<E> splitCubes = new HashSet<>();

        if (e.fromX < n.fromX) {
            splitCubes.add(new E(e.on, e.fromZ, e.toZ, e.fromY, e.toY, e.fromX, n.fromX - 1));
        }

        if (n.toX < e.toX) {
            splitCubes.add(new E(e.on, e.fromZ, e.toZ, e.fromY, e.toY, n.toX + 1, e.toX));
        }

        int x1 = Math.max(e.fromX, n.fromX);
        int x2 = Math.min(e.toX, n.toX);

        if (e.fromY < n.fromY) {
            splitCubes.add(new E(e.on, e.fromZ, e.toZ, e.fromY, n.fromY - 1, x1, x2));
        }
        if (n.toY < e.toY) {
            splitCubes.add(new E(e.on, e.fromZ, e.toZ, n.toY + 1, e.toY, x1, x2));
        }

        int y1 = Math.max(e.fromY, n.fromY);
        int y2 = Math.min(e.toY, n.toY);

        if (e.fromZ < n.fromZ) {
            splitCubes.add(new E(e.on, e.fromZ, n.fromZ - 1, y1, y2, x1, x2));
        }

        if (n.toZ < e.toZ) {
            splitCubes.add(new E(e.on, n.toZ + 1, e.toZ, y1, y2, x1, x2));
        }

        return splitCubes;
    }
}
