import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;


public class Day10Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var sum = 0;
        for (var line : lines) {
            var parts = List.of(line.split(" "));
            var config = parts.getFirst();
            var buttons = parts.subList(1, parts.size() - 1).stream().map(s -> Arrays.stream(s.substring(1, s.length() - 1).split(",")).map(Integer::parseInt).toList()).toList();

            var sol = new BitSet(config.length() - 2);
            config = config.substring(1, config.length() - 1);
            for (int i = 0; i < config.length(); i++) {
                if (config.charAt(i) == '#') {
                    sol.set(i);
                }
            }
            var min = Integer.MAX_VALUE;
            var bitset = new BitSet(config.length() - 2);
            for (int i = 1; i < Math.pow(2, buttons.size()); i++) {
                bitset.clear();
                var chosenButtons = new ArrayList<List<Integer>>();
                for (int j = 0; j < buttons.size(); j++) {
                    if (((i >> j) & 1) == 1) {
                        chosenButtons.add(buttons.get(j));
                        for (int k = 0; k < buttons.get(j).size(); k++) {
                            var light = buttons.get(j).get(k);
                            bitset.set(light, !bitset.get(light));
                        }
                    }
                }
                if (bitset.equals(sol)) {
                    if (chosenButtons.size() < min) {
                        min = chosenButtons.size();
                    }
                }
            }
            sum += min;
        }
        System.out.println(sum);
    }
}
