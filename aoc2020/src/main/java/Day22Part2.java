import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day22Part2 {

    record GameState(List<Integer> a, List<Integer> b) {
    }

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        LinkedList<Integer> a = new LinkedList<>();
        LinkedList<Integer> b = new LinkedList<>();

        int i = 1;
        boolean t = false;
        while (i != lines.size()) {
            if (lines.get(i).isEmpty()) {
                i += 2;
                t = true;
            }
            if (!t) {
                a.add(Integer.parseInt(lines.get(i)));
            } else {
                b.add(Integer.parseInt(lines.get(i)));
            }
            i++;
        }
        var winner = game(a, b).getItem2();
        int sum = 0;
        int mult = winner.size();
        for (var x : winner) {
            sum += x * mult;
            mult--;
        }
        System.out.println(sum);
    }

    static Tuple2<Boolean, LinkedList<Integer>> game(LinkedList<Integer> a, LinkedList<Integer> b) {
        Set<Integer> states = new HashSet<>();
        while (!a.isEmpty() && !b.isEmpty()) {
            int gs = new GameState(a, b).hashCode();
            if (states.contains(gs)) {
                return Tuple2.of(true, a);
            } else {
                states.add(gs);
            }
            List<Integer> winner;
            var aTop = a.pop();
            var bTop = b.pop();
            if (a.size() >= aTop && b.size() >= bTop) {
                var res = game(new LinkedList<>(a.subList(0, aTop)), new LinkedList<>(b.subList(0, bTop)));
                winner = res.getItem1() ? a : b;
            } else if (aTop > bTop) {
                winner = a;
            } else {
                winner = b;
            }
            if (winner == a) {
                a.add(aTop);
                a.add(bTop);
            } else {
                b.add(bTop);
                b.add(aTop);
            }
        }
        var winner = a.isEmpty() ? b : a;
        return Tuple2.of(winner == a, winner);
    }
}
