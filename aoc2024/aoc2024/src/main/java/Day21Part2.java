import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day21Part2 {

    static HashMap<String, Tuple2<Integer, Integer>> coordsB = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {

                 long sum = 0;

                 HashMap<String, Tuple2<Integer, Integer>> coordsA = new HashMap<>();
                 coordsA.put("7", Tuple2.of(0, 0));
                 coordsA.put("8", Tuple2.of(0, 1));
                 coordsA.put("9", Tuple2.of(0, 2));
                 coordsA.put("4", Tuple2.of(1, 0));
                 coordsA.put("5", Tuple2.of(1, 1));
                 coordsA.put("6", Tuple2.of(1, 2));
                 coordsA.put("1", Tuple2.of(2, 0));
                 coordsA.put("2", Tuple2.of(2, 1));
                 coordsA.put("3", Tuple2.of(2, 2));
                 coordsA.put("0", Tuple2.of(3, 1));
                 coordsA.put("A", Tuple2.of(3, 2));

                 coordsB.put("^", Tuple2.of(0, 1));
                 coordsB.put("A", Tuple2.of(0, 2));
                 coordsB.put("<", Tuple2.of(1, 0));
                 coordsB.put("v", Tuple2.of(1, 1));
                 coordsB.put(">", Tuple2.of(1, 2));
                 for (var line : map) {
                     HashMap<Tuple2<String, Integer>, Long> cache = new HashMap<>();
                     long k = visit(0, line, coordsA, cache);
                     long num = Long.parseLong(String.join("", line).replaceAll("[^0-9]*", ""));
                     System.out.println(String.join("", line));
                     System.out.println(k + " " + num);
                     sum += k * num;
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }

    static long visit(int depth, List<String> pattern, HashMap<String, Tuple2<Integer, Integer>> coords,
                      Map<Tuple2<String, Integer>, Long> cache) {
        var key = Tuple2.of(String.join("", pattern), depth);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (depth == 26) {
            return pattern.size();
        }
        long sum = 0;
        String pointer = "A";
        for (var item : pattern) {
            int yDiff = coords.get(item).getItem1() - coords.get(pointer).getItem1();
            int xDiff = coords.get(item).getItem2() - coords.get(pointer).getItem2();
            List<String> replace = new ArrayList<>();
            if (xDiff == 0 || yDiff == 0) {
                replace.addAll(Collections.nCopies(Math.abs(xDiff), xDiff < 0 ? "<" : ">"));
                replace.addAll(Collections.nCopies(Math.abs(yDiff), yDiff < 0 ? "^" : "v"));
                replace.add("A");
                sum += visit(depth + 1, replace, coordsB, cache);
            } else {
                long min = Integer.MAX_VALUE;
                var pos = coords.get(pointer);
                boolean udlrAllowed = true;
                var yAfterMove = pos.getItem1() + yDiff;
                if (coords.size() == 11 && yAfterMove == 3 && pos.getItem2() == 0) {
                    udlrAllowed = false;
                }
                if (coords.size() == 5 && yAfterMove == 0 && pos.getItem2() == 0) {
                    udlrAllowed = false;
                }
                if (udlrAllowed) {
                    replace.addAll(Collections.nCopies(Math.abs(yDiff), yDiff < 0 ? "^" : "v"));
                    replace.addAll(Collections.nCopies(Math.abs(xDiff), xDiff < 0 ? "<" : ">"));
                    replace.add("A");
                    min = visit(depth + 1, replace, coordsB, cache);
                }
                boolean lrudAllowed = true;
                var xAfterMove = pos.getItem2() + xDiff;
                if (coords.size() == 11 && xAfterMove == 0 && pos.getItem1() == 3) {
                    lrudAllowed = false;
                }
                if (coords.size() == 5 && xAfterMove == 0 && pos.getItem1() == 0) {
                    lrudAllowed = false;
                }
                if (lrudAllowed) {
                    replace.clear();
                    replace.addAll(Collections.nCopies(Math.abs(xDiff), xDiff < 0 ? "<" : ">"));
                    replace.addAll(Collections.nCopies(Math.abs(yDiff), yDiff < 0 ? "^" : "v"));
                    replace.add("A");
                    var val = visit(depth + 1, replace, coordsB, cache);
                    if (val < min) {
                        min = val;
                    }
                }
                sum += min;
            }
            pointer = item;
        }
        cache.put(key, sum);
        return sum;
    }
}
