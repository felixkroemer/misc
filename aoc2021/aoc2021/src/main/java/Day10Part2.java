import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;

public class Day10Part2 {

    static Map<String, String> c = Map.of(")", "(", "]", "[", "}", "{", ">", "<");
    static Map<String, String> rev = Map.of("(", ")", "[", "]", "{", "}", "<", ">");
    static Map<String, Integer> values = Map.of(")", 1, "]", 2, "}", 3, ">", 4);


    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().filter(Day10Part2::test).toList();
        var scores = new ArrayList<Long>();
        for (var line : lines) {
            var stack = new Stack<String>();
            for (int i = 0; i < line.length(); i++) {
                var val = String.valueOf(line.charAt(i));
                switch (val) {
                    case "(", "[", "{", "<":
                        stack.add(val);
                        break;
                    case ")", "]", "}", ">":
                        stack.pop();
                }
            }
            String completion = "";
            while (!stack.isEmpty()) {
                var val = stack.pop();
                completion += rev.get(val);
            }
            long s = 0;
            for (int i = 0; i < completion.length(); i++) {
                s *= 5;
                s += values.get(String.valueOf(completion.charAt(i)));
            }
            scores.add(s);
        }
        Collections.sort(scores);
        System.out.println(scores.get(scores.size() / 2));
    }

    static boolean test(String line) {
        var stack = new Stack<String>();
        for (int i = 0; i < line.length(); i++) {
            var val = String.valueOf(line.charAt(i));
            switch (val) {
                case "(", "[", "{", "<":
                    stack.add(val);
                    break;
                case ")", "]", "}", ">":
                    if (stack.isEmpty()) {
                        return false;
                    } else {
                        var match = stack.pop();
                        if (!match.equals(c.get(val))) {
                            return false;
                        }
                    }
            }
        }
        return true;
    }

}
