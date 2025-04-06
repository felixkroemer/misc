import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Day10Part1 {


    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        Map<String, Integer> values = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);
        Map<String, String> c = Map.of(")", "(", "]", "[", "}", "{", ">", "<");
        var sum = 0;
        for (var line : lines) {
            var stack = new Stack<String>();
            boolean br = false;
            for (int i = 0; i < line.length(); i++) {
                if (br) break;
                var val = String.valueOf(line.charAt(i));
                switch (val) {
                    case "(", "[", "{", "<":
                        stack.add(val);
                        break;
                    case ")", "]", "}", ">":
                        if (stack.isEmpty()) {
                            sum += values.get(val);
                            br = true;
                            break;
                        } else {
                            var match = stack.pop();
                            if (!match.equals(c.get(val))) {
                                sum += values.get(val);
                                br = true;
                                break;
                            }
                        }
                }
            }
        }
        System.out.println(sum);
    }
}
