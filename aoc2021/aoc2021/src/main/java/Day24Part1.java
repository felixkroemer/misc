import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day24Part1 {

    record Config(boolean zDiv26, int xAdd, int yAdd) {
    }

    public static void main(String[] args) throws Exception {
        Config c1 = new Config(false, 15, 13);
        Config c2 = new Config(false, 10, 16);
        Config c3 = new Config(false, 12, 2);
        Config c4 = new Config(false, 10, 8);
        Config c5 = new Config(false, 14, 11);
        Config c6 = new Config(true, -11, 6);
        Config c7 = new Config(false, 10, 12);
        Config c8 = new Config(true, -16, 2);
        Config c9 = new Config(true, -9, 2);
        Config c10 = new Config(false, 11, 15);
        Config c11 = new Config(true, -8, 1);
        Config c12 = new Config(true, -8, 10);
        Config c13 = new Config(true, -10, 14);
        Config c14 = new Config(true, -9, 10);
        var configs = List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14);
        solve(0, configs.size() - 1, configs, new ArrayList<>());
    }

    private static void monad(List<String> lines, Map<String, Long> vars, String input) {
        var idx = 0;
        for (var line : lines) {
            var parts = line.split(" ");
            var op = parts[0];
            var variable = parts[1];
            if (op.equals("inp")) {
                vars.put(variable, Long.parseLong(input.substring(idx, idx + 1)));
                idx++;
            } else {
                long value;
                try {
                    value = Long.parseLong(parts[2]);
                } catch (NumberFormatException e) {
                    value = vars.get(parts[2]);
                }
                switch (op) {
                    case "add" -> {
                        vars.put(variable, vars.get(variable) + value);
                    }
                    case "mul" -> {
                        vars.put(variable, vars.get(variable) * value);
                    }
                    case "div" -> {
                        vars.put(variable, vars.get(variable) / value);
                    }
                    case "mod" -> {
                        if (vars.get(variable) < 0 || value <= 0) {
                            throw new RuntimeException();
                        }
                        vars.put(variable, vars.get(variable) % value);
                    }
                    case "eql" -> {
                        vars.put(variable, vars.get(variable) == value ? 1L : 0L);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + op);
                }
            }
        }
    }

    private static void solve(long targetZ, int step, List<Config> configs, List<Integer> values) {
        if (step == -1) {
            if (targetZ == 0) {
                System.out.println(String.join("", values.reversed().stream().map(v -> "" + v).toList()) + " " + targetZ);
                System.exit(0);
            } else {
                return;
            }
        }

        var config = configs.get(step);

        var zMods = new ArrayList<Tuple2<Integer, Integer>>();
        for (int w = 9; w > 0; w--) {
            var zMod = w - config.xAdd;
            if (zMod >= 0 && zMod < 26) {
                zMods.add(Tuple2.of(w, zMod));
                long z;
                if (config.zDiv26()) {
                    z = targetZ * 26 + zMod;
                } else {
                    z = zMod;
                    if (z != targetZ) {
                        continue;
                    }
                }
                values.add(w);
                solve(z, step - 1, configs, values);
                values.removeLast();
            }
        }

        //targetZ = (z / 26) * 26 + config.yAdd + w;
        //(targetZ - config.yAdd - w) / 26 = z / 26

        for (int w = 9; w > 0; w--) {
            var z = (targetZ - config.yAdd - w) / 26;
            if (config.zDiv26()) {
                z = z * 26;
                for (int zMod = 0; zMod < 26; zMod++) {
                    if (((z + zMod) / 26) * 26 + config.yAdd + w != targetZ || zMods.contains(Tuple2.of(w, zMod))) {
                        continue;
                    }
                    values.add(w);
                    solve(z + zMod, step - 1, configs, values);
                    values.removeLast();
                }
            } else {
                if (z * 26 + config.yAdd + w != targetZ || zMods.contains(Tuple2.of(w, z % 26))) {
                    continue;
                }
                values.add(w);
                solve(z, step - 1, configs, values);
                values.removeLast();
            }
        }
    }
}
