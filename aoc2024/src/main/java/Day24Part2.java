import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day24Part2 {

    static int BITS = 45;

    enum Type {
        XOR, OR, AND
    }

    record Gate(String l, String r, String out, Type type) {
    }

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            Set<String> unknownStates = new HashSet<>();
            Map<String, Gate> outGates = new HashMap<>();
            Map<String, Set<Gate>> inGates = new HashMap<>();
            Set<Gate> defective = new HashSet<>();
            for (var item : lines.subList(lines.indexOf("") + 1, lines.size())) {
                var parts = item.split(" ");
                var l = parts[0];
                var type = Type.valueOf(parts[1]);
                var r = parts[2];
                var out = parts[4];
                var gate = new Gate(l, r, out, type);
                outGates.put(out, gate);
                inGates.putIfAbsent(l, new HashSet<>());
                inGates.putIfAbsent(r, new HashSet<>());
                inGates.get(l).add(gate);
                inGates.get(r).add(gate);
                if (!l.startsWith("x") && !l.startsWith("y")) {
                    unknownStates.add(l);
                }
                if (!r.startsWith("x") && !r.startsWith("y")) {
                    unknownStates.add(r);
                }
                unknownStates.add(out);
            }
            for (var gate : outGates.values()) {
                if (gate.out.startsWith("z") && !gate.out.equals("z45")) {
                    if (gate.type != Type.XOR) {
                        defective.add(gate);
                    }
                }
                if (gate.l.equals("x") || gate.l.equals("y") || gate.r.equals("x") || gate.r.equals("y")) {
                    if (gate.type != Type.XOR && gate.type != Type.AND) {
                        defective.add(gate);
                    }
                }
                if (gate.type == Type.AND && inGates.get(gate.out) != null && inGates.get(gate.out).size() != 1 && !gate.out.equals("bdk")) {
                    defective.add(gate);
                }
                if (gate.type == Type.XOR) {
                    if (inGates.get(gate.out) != null) {
                        if (inGates.get(gate.out).size() != 2) {
                            defective.add(gate);
                        }
                        if (!gate.l.startsWith("x") && !gate.l.startsWith("y") && !gate.r.startsWith("x") && !gate.r.startsWith("y")) {
                            defective.add(gate);
                        }
                    }
                }

            }
            var defectiveList = new ArrayList<>(defective.stream().map(d -> d.out).toList());
            Collections.sort(defectiveList);
            return String.join(",", defectiveList);
        }).subscribe().with(System.out::println);
    }

    static Map<String, Boolean> getStates(long x, long y) {
        Map<String, Boolean> states = new HashMap<>();
        for (int i = 0; i < BITS; i++) {
            if ((x & (1L << i)) != 0) {
                states.put(String.format("x%02d", i), true);
            } else {
                states.put(String.format("x%02d", i), false);
            }
            if ((y & (1L << i)) != 0) {

                states.put(String.format("y%02d", i), true);
            } else {
                states.put(String.format("y%02d", i), false);
            }
        }
        return states;
    }

}
