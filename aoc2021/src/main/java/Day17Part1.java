import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day17Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        var map = new HashMap<Integer, List<Integer>>();

        int startX = 185;
        int endX = 221;
        int startY = -122;
        int endY = -74;
        int initialXVel = 1;

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

        var maxY = 0;

        for (var xEntry : map.entrySet()) {
            var initialXVelocity = xEntry.getKey();
            var steps = xEntry.getValue();

            //System.out.println("Processing " + xValue + ", values: " + String.join(",", steps.stream()
            //                                                                                 .map(Object::toString)
            //                                                                                 .toList()));

            var initialYVelocity = 0;
            while (true) {
                int i = 0;
                var yVelocity = initialYVelocity;
                var y = 0;
                var highestY = 0;
                while (true) {
                    y += yVelocity;
                    if(y>highestY) {
                        highestY = y;
                    }
                    i++;
                    if (y >= startY && y <= endY && steps.contains(i)) {
                        if(highestY > maxY) {
                            maxY = highestY;
                        }
                        System.out.println(initialXVelocity + " " + initialYVelocity + " " + i);
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

        System.out.println(maxY);
    }
}
