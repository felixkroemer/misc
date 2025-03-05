import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day21Part1 {

    static HashMap<String, Tuple2<Integer, Integer>> coordsB = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
             .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {

                 int sum = 0;
                 int machines = 3;

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
                     var k = visit(0, line, coordsA);
                     var num = Integer.parseInt(String.join("", line).replaceAll("[^0-9]*", ""));
                     System.out.println(String.join("", line));
                     System.out.println(k + " " + num);
                     sum += k * num;
                 }
                 return sum;
             }).subscribe().with(System.out::println);
    }

    static int visit(int depth, List<String> pattern, HashMap<String, Tuple2<Integer, Integer>> coords) {
        if (depth == 3) {
            System.out.print(String.join("", pattern));
            return pattern.size();
        }
        int sum = 0;
        String pointer = "A";
        for (var item : pattern) {
            int yDiff = coords.get(item).getItem1() - coords.get(pointer).getItem1();
            int xDiff = coords.get(item).getItem2() - coords.get(pointer).getItem2();
            List<String> replace = new ArrayList<>();
            if (xDiff == 0 || yDiff == 0) {
                replace.addAll(Collections.nCopies(Math.abs(xDiff), xDiff < 0 ? "<" : ">"));
                replace.addAll(Collections.nCopies(Math.abs(yDiff), yDiff < 0 ? "^" : "v"));
                replace.add("A");
                sum += visit(depth + 1, replace, coordsB);
            } else {
                boolean lrudAllowed = true;
                var pos = coords.get(pointer);
                var xAfterMove = pos.getItem2() + xDiff;
                if (coords.size() == 11 && xAfterMove == 0 && pos.getItem1() == 3) {
                    lrudAllowed = false;
                }
                if (coords.size() == 5 && xAfterMove == 0 && pos.getItem1() == 0) {
                    lrudAllowed = false;
                }
                replace.addAll(Collections.nCopies(Math.abs(yDiff), yDiff < 0 ? "^" : "v"));
                replace.addAll(Collections.nCopies(Math.abs(xDiff), xDiff < 0 ? "<" : ">"));
                replace.add("A");
                int min = visit(depth + 1, replace, coordsB);
                if (lrudAllowed) {
                    replace.clear();
                    replace.addAll(Collections.nCopies(Math.abs(xDiff), xDiff < 0 ? "<" : ">"));
                    replace.addAll(Collections.nCopies(Math.abs(yDiff), yDiff < 0 ? "^" : "v"));
                    replace.add("A");
                    var val = visit(depth + 1, replace, coordsB);
                    if (val < min) {
                        min = val;
                    }
                }
                sum += min;
            }
            pointer = item;
        }
        return sum;
    }
}
