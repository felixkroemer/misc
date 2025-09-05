import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day22Part1 {

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
        while (!a.isEmpty() && !b.isEmpty()) {
            var aTop = a.pop();
            var bTop = b.pop();
            if (aTop > bTop) {
                a.add(aTop);
                a.add(bTop);
            } else {
                b.add(bTop);
                b.add(aTop);
            }
        }
        int sum = 0;
        int mult = a.isEmpty() ? b.size() : a.size();
        for (var x : a.isEmpty() ? b : a) {
            sum += x * mult;
            mult--;
        }
        System.out.println(sum);
    }
}
