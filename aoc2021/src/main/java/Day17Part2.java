import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day17Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        var map = new HashMap<Integer, List<Integer>>();

        int startX = 185;
        int endX = 221;
        int startY = -122;
        int endY = -74;
        int initialXVel = 0;

        while (true) {
            if (initialXVel > endX) {
                break;
            }
            int xVel = initialXVel;
            int x = 0;
            int i = 0;
            while (i < 10000) {
                x += xVel;
                i++;
                if (x >= startX && x <= endX) {
                    if (!map.containsKey(initialXVel)) {
                        map.put(initialXVel, new ArrayList<>());
                    }
                    map.get(initialXVel).add(i);
                }
                if (xVel != 0) {
                    xVel--;
                }
            }
            initialXVel++;
        }

        HashSet<Tuple2<Integer, Integer>> solutions = new HashSet<>();

        for (var xEntry : map.entrySet()) {
            var initialXVelocity = xEntry.getKey();
            var steps = xEntry.getValue();

            var initialYVelocity = -1000;
            while (true) {
                int i = 0;
                var yVelocity = initialYVelocity;
                var y = 0;
                while (true) {
                    y += yVelocity;
                    i++;
                    if (y >= startY && y <= endY && steps.contains(i)) {
                        solutions.add(Tuple2.of(initialXVelocity, initialYVelocity));
                        //System.out.println(initialXVelocity + " " + initialYVelocity + " " + i);
                        break;
                    }
                    if (y < startY) {
                        break;
                    }
                    yVelocity--;
                }
                initialYVelocity++;
                if (initialYVelocity == 10000) {
                    break;
                }
            }
        }

        System.out.println(solutions.size());
    }
}
