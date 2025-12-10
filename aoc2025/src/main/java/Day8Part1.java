import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day8Part1 {

    record R(long x, long y, long z) {
    }

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        Set<R> coords = new HashSet<>();
        for (var line : lines) {
            var parts = line.split(",");
            var coord = new R(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
            coords.add(coord);
        }
        PriorityQueue<Tuple2<R, R>> p = new PriorityQueue<>(Comparator.comparingDouble(a -> {
            double dx = a.getItem1().x() - a.getItem2().x();
            double dy = a.getItem1().y() - a.getItem2().y();
            double dz = a.getItem1().z() - a.getItem2().z();
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        }));
        List<R> coordList = new ArrayList<>(coords);
        for (int i = 0; i < coordList.size(); i++) {
            for (int j = i + 1; j < coordList.size(); j++) {
                p.add(Tuple2.of(coordList.get(i), coordList.get(j)));
            }
        }
        Map<R, Set<R>> part = new HashMap<>();
        var pairsProcessed = 0;

        while (pairsProcessed < 1000 && !p.isEmpty()) {
            var closest = p.poll();
            pairsProcessed++;

            Set<R> a = part.get(closest.getItem1());
            Set<R> b = part.get(closest.getItem2());
            
            if (a != null && a == b) {
                continue;
            }

            if (a == null && b == null) {
                var c = new HashSet<R>();
                c.add(closest.getItem1());
                c.add(closest.getItem2());
                part.put(closest.getItem1(), c);
                part.put(closest.getItem2(), c);
            }
            else if (a != null && b == null) {
                a.add(closest.getItem2());
                part.put(closest.getItem2(), a);
            }
            else if (a == null && b != null) {
                b.add(closest.getItem1());
                part.put(closest.getItem1(), b);
            }
            else {
                a.addAll(b);
                for (var k : b) {
                    part.put(k, a);
                }
            }
        }
        var found = new HashSet<R>();
        List<Long> sizes = new ArrayList<>();
        for (var e : part.entrySet()) {
            if (!found.contains(e.getKey())) {
                found.addAll(part.get(e.getKey()));
                sizes.add((long) e.getValue().size());
            }
        }
        sizes.sort(Collections.reverseOrder());
        var prod = 1L;
        for (int i = 0; i < 3; i++) {
            prod *= sizes.get(i);
        }
        System.out.println(prod);
    }
}
