import io.smallrye.mutiny.tuples.Tuple2;
import jdk.jshell.execution.Util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;


public class Day9Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var points = new ArrayList<Tuple2<Integer, Integer>>();

        var upperInner = Tuple2.of(94671, 50270);

        int i = 0;
        Tuple2<Integer, Integer> point;
        do {
            var parts = lines.get(i).split(",");
            point = Tuple2.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            points.add(point);
            i++;
        } while (!point.equals(upperInner));

        var maxH = 0;
        for (var p : points) {
            if (p.getItem1() < upperInner.getItem1()) {
                maxH = p.getItem2();
                break;
            }
        }
        long max = 0;
        var maxX = 0;
        Collections.reverse(points);
        points.removeFirst();
        for (var p : points) {
            if (p.getItem2() > maxH) {
                break;
            }
            if(p.getItem1() < maxX) {
                continue;
            } else {
                maxX = p.getItem1();
            }
            long size = (Math.abs(p.getItem1() - upperInner.getItem1()) + 1L) * (Math.abs(p.getItem2() - upperInner.getItem2()) + 1L);
            if (size > max) {
                max = size;
                System.out.println(p);
            }
            
        }
        System.out.println(max);

        points.clear();
        var lowerInner = Tuple2.of(94671, 48487);
        var end = Tuple2.of(98367, 50290);

        i = 250;
        do {
            var parts = lines.get(i).split(",");
            point = Tuple2.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            points.add(point);
            i++;
        } while (!point.equals(end));

        var minH = 0;
        Collections.reverse(points);
        for (var p : points) {
            if (p.getItem1() < lowerInner.getItem1()) {
                minH = p.getItem2();
                break;
            }
        }
        maxX = 0;
        for (var p : points) {
            if (p.getItem2() < minH) {
                break;
            }
            if(p.getItem1() < maxX) {
                continue;
            } else {
                maxX = p.getItem1();
            }
            long size = (Math.abs(p.getItem1() - lowerInner.getItem1()) + 1L) * (Math.abs(p.getItem2() - lowerInner.getItem2()) + 1L);
            if (size > max) {
                max = size;
                System.out.println(p);
            }
        }
        System.out.println(max);
    }
}
