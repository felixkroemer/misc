import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day24Part1 {

    enum Type {
        XOR, OR, AND
    }

    record Gate(String l, String r, String out, Type type) {
    }


    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            HashMap<String, Boolean> states = new HashMap<>();
            HashSet<String> unknownStates = new HashSet<>();
            HashSet<Gate> gates = new HashSet<>();
            for (var item : lines.subList(0, lines.indexOf(""))) {
                var wire = item.split(":")[0];
                states.putIfAbsent(wire, item.split(" ")[1].equals("1"));
            }
            for (var item : lines.subList(lines.indexOf("") + 1, lines.size())) {
                var parts = item.split(" ");
                var l = parts[0];
                var type = Type.valueOf(parts[1]);
                var r = parts[2];
                var out = parts[4];
                gates.add(new Gate(l, r, out, type));
                if (!states.containsKey(l)) {
                    unknownStates.add(l);
                }
                if (!states.containsKey(r)) {
                    unknownStates.add(r);
                }
                if (!states.containsKey(out)) {
                    unknownStates.add(out);
                }
            }
            while (!unknownStates.isEmpty()) {
                for (var gate : gates) {
                    if (states.containsKey(gate.l) && states.containsKey(gate.r)) {
                        boolean val = false;
                        switch (gate.type) {
                            case XOR -> {
                                val = states.get(gate.l) ^ states.get(gate.r);
                            }
                            case OR -> {
                                val = states.get(gate.l) | states.get(gate.r);
                            }
                            case AND -> {
                                val = states.get(gate.l) & states.get(gate.r);
                            }
                        }
                        states.put(gate.out, val);
                        unknownStates.remove(gate.out);
                    }
                }
            }
            long res = 0;
            for (var state : states.entrySet()) {
                if (state.getKey().startsWith("z")) {
                    var val = Integer.parseInt(state.getKey().substring(1));
                    if (state.getValue()) {
                        res += (long) Math.pow(2, val);
                    }
                }
            }
            return res;
        }).subscribe().with(System.out::println);
    }
}
