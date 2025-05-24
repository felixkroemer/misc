import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;


public class Day19Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));


        List<List<Tuple3<Integer, Integer, Integer>>> scanners = new ArrayList<>();
        var l = new ArrayList<Tuple3<Integer, Integer, Integer>>();
        for (var line : lines) {
            if (line.isBlank()) {
                scanners.add(new ArrayList<>(l));
                l.clear();
                continue;
            }
            if (!line.startsWith("---")) {
                var items = Arrays.stream(line.split(",")).map(Integer::valueOf).toList();
                l.add(Tuple3.of(items.get(0), items.get(1), items.get(2)));
            }
        }

        List<List<Set<Integer>>> dist = new ArrayList<>();
        for (int i = 0; i < scanners.size(); i++) {
            var scanner = scanners.get(i);
            var k = new ArrayList<Set<Integer>>();
            dist.add(k);
            for (int j = 0; j < scanner.size(); j++) {
                var item = scanner.get(j);
                var m = new HashSet<Integer>();
                k.add(m);
                for (var other : scanner) {
                    var distance = Math.pow(item.getItem1() - other.getItem1(), 2) + Math.pow(item.getItem2() - other.getItem2(), 2) + Math.pow(item.getItem3() - other.getItem3(), 2);
                    m.add((int) distance);
                }
            }
        }

        var mappers = new HashMap<Tuple2<Integer, Integer>, Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>>();

        var paths = new HashSet<Tuple2<Integer, Integer>>();

        for (int i = 0; i < dist.size(); i++) {
            var scanner = dist.get(i);
            for (int j = 0; j < dist.size(); j++) {
                var otherScanner = dist.get(j);
                if (scanner == otherScanner) {
                    continue;
                }
                Map<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> mapped = new HashMap<>();
                for (int k = 0; k < scanner.size(); k++) {
                    var probe = scanner.get(k);
                    for (int m = 0; m < otherScanner.size(); m++) {
                        var otherProbe = otherScanner.get(m);
                        var overlap = new HashSet<>(probe);
                        overlap.retainAll(otherProbe);
                        if (overlap.size() == 12) {
                            mapped.put(scanners.get(j).get(m), scanners.get(i).get(k));
                        }
                    }
                }

                if (mapped.size() == 12) {
                    System.out.println(i + " " + j);
                    var transform = findTransform(mapped);
                    mappers.put(Tuple2.of(i, j), transform);
                    paths.add(Tuple2.of(i, j));
                }
            }
        }

        var beacons = new HashSet<Tuple3<Integer, Integer, Integer>>();
        var sensors = new HashSet<Tuple3<Integer, Integer, Integer>>();


        for (int i = 0; i < scanners.size(); i++) {
            var path = findPath(paths, 0, i);
            Tuple3<Integer, Integer, Integer> sensor = Tuple3.of(0, 0, 0);
            var elements = new ArrayList<>(scanners.get(i));
            for (var p : path.reversed()) {
                for (int j = 0; j < elements.size(); j++) {
                    elements.set(j, mappers.get(p).apply(elements.get(j)));
                }
                sensor = mappers.get(p).apply(sensor);
            }
            beacons.addAll(elements);
            sensors.add(sensor);
        }

        var max = 0;
        for(var sensor : sensors) {
            for(var o : sensors) {
                var val = Math.abs(sensor.getItem1() - o.getItem1()) + Math.abs(sensor.getItem2() - o.getItem2()) + Math.abs(sensor.getItem3() - o.getItem3());
                if(val > max) {
                    max = val;
                }
            }
        }

        System.out.println(max);
    }


    public static List<Tuple2<Integer, Integer>> findPath(
            Set<Tuple2<Integer, Integer>> edges,
            int start,
            int end
    ) {
        List<Tuple2<Integer, Integer>> path = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfs(edges, start, end, visited, path);
        return path;
    }

    private static boolean dfs(
            Set<Tuple2<Integer, Integer>> edges,
            int curr,
            int target,
            Set<Integer> visited,
            List<Tuple2<Integer, Integer>> path
    ) {
        if (curr == target) return true;
        visited.add(curr);

        for (Tuple2<Integer, Integer> edge : edges) {
            if (edge.getItem1() == curr && !visited.contains(edge.getItem2())) {
                path.add(edge);
                if (dfs(edges, edge.getItem2(), target, visited, path)) return true;
                path.removeLast();
            }
        }

        return false;
    }

    public static List<Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>> rots = List.of(
            p -> Tuple3.of(p.getItem1(), p.getItem2(), p.getItem3()),
            p -> Tuple3.of(p.getItem1(), -p.getItem3(), p.getItem2()),
            p -> Tuple3.of(p.getItem1(), -p.getItem2(), -p.getItem3()),
            p -> Tuple3.of(p.getItem1(), p.getItem3(), -p.getItem2()),
            p -> Tuple3.of(-p.getItem1(), -p.getItem2(), p.getItem3()),
            p -> Tuple3.of(-p.getItem1(), -p.getItem3(), -p.getItem2()),
            p -> Tuple3.of(-p.getItem1(), p.getItem2(), -p.getItem3()),
            p -> Tuple3.of(-p.getItem1(), p.getItem3(), p.getItem2()),

            p -> Tuple3.of(p.getItem2(), p.getItem3(), p.getItem1()),
            p -> Tuple3.of(p.getItem2(), -p.getItem1(), p.getItem3()),
            p -> Tuple3.of(p.getItem2(), -p.getItem3(), -p.getItem1()),
            p -> Tuple3.of(p.getItem2(), p.getItem1(), -p.getItem3()),
            p -> Tuple3.of(-p.getItem2(), -p.getItem3(), p.getItem1()),
            p -> Tuple3.of(-p.getItem2(), -p.getItem1(), -p.getItem3()),
            p -> Tuple3.of(-p.getItem2(), p.getItem3(), -p.getItem1()),
            p -> Tuple3.of(-p.getItem2(), p.getItem1(), p.getItem3()),

            p -> Tuple3.of(p.getItem3(), p.getItem1(), p.getItem2()),
            p -> Tuple3.of(p.getItem3(), -p.getItem2(), p.getItem1()),
            p -> Tuple3.of(p.getItem3(), -p.getItem1(), -p.getItem2()),
            p -> Tuple3.of(p.getItem3(), p.getItem2(), -p.getItem1()),
            p -> Tuple3.of(-p.getItem3(), -p.getItem1(), p.getItem2()),
            p -> Tuple3.of(-p.getItem3(), -p.getItem2(), -p.getItem1()),
            p -> Tuple3.of(-p.getItem3(), p.getItem1(), -p.getItem2()),
            p -> Tuple3.of(-p.getItem3(), p.getItem2(), p.getItem1())
    );

    public static Function<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>
    findTransform(Map<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>> mapped) {
        for (var rot : rots) {
            Set<Tuple3<Integer, Integer, Integer>> d = new HashSet<>();
            for (var e : mapped.entrySet()) {
                var rotated = rot.apply(e.getKey());
                var target = e.getValue();
                var dx = target.getItem1() - rotated.getItem1();
                var dy = target.getItem2() - rotated.getItem2();
                var dz = target.getItem3() - rotated.getItem3();
                d.add(Tuple3.of(dx, dy, dz));
            }
            if (d.size() == 1) {
                var delta = d.iterator().next();
                System.out.println(delta);
                return p -> {
                    var r = rot.apply(p);
                    return Tuple3.of(r.getItem1() + delta.getItem1(), r.getItem2() + delta.getItem2(), r.getItem3() + delta.getItem3());
                };
            }
        }
        throw new RuntimeException();
    }
}
