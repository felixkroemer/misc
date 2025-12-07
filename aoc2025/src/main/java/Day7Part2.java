import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day7Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(s -> Arrays.stream(s.split("")).toList()).toList();
        var splitters = new ArrayList<HashSet<Integer>>();
        for (var line : lines) {
            var h = new HashSet<Integer>();
            splitters.add(h);
            int j = 0;
            for (var c : line) {
                if (c.equals("S") || c.equals("^")) {
                    h.add(j);
                }
                j++;
            }
        }
        Map<Integer, Long> beams = new HashMap<>();
        beams.put(splitters.get(0).stream().findFirst().get(), 1L);
        for (int i = 0; i < lines.size() - 1; i++) {
            Map<Integer, Long> newBeams = new HashMap<>();
            for (var e : beams.entrySet()) {
                var beam = e.getKey();
                if (lines.get(i + 1).get(beam).equals("^")) {
                    newBeams.compute(beam - 1, (k, v) -> v == null ? e.getValue() : v + e.getValue());
                    newBeams.compute(beam + 1, (k, v) -> v == null ? e.getValue() : v + e.getValue());
                } else {
                    newBeams.compute(beam, (k, v) -> v == null ? e.getValue() : v + e.getValue());
                }
            }
            beams = newBeams;
        }
        System.out.println(beams.values().stream().mapToLong(v -> v).sum());
    }
}
