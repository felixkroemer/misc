import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Part1 {

    record Node(Node left, Node right, String op, int val) {
    }

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        Map<String, String> rules = new HashMap<>();
        int i = 0;
        while (!lines.get(i).isBlank()) {
            var line = lines.get(i);
            var parts = line.split(":");
            rules.put(parts[0], parts[1]);
            i++;
        }
        String regex = eval(rules.get("0"), rules);
        i++;
        var sum = 0;
        while(i < lines.size()) {
            if(lines.get(i).matches(regex)) {
                sum ++;
            }
            i++;
        }
        System.out.println(sum);
    }

    public static String eval(String src,Map<String, String> rules) {
        src = src.trim();
        if(src.contains("|")) {
            var parts = src.split("\\|");
            var left = parts[0].trim().split(" ");
            var right = parts[1].trim().split(" ");
            var lRes = "";
            for(var l : left) {
                lRes += eval(rules.get(l), rules);
            }
            var rRes = "";
            for(var r : right) {
                rRes += eval(rules.get(r), rules);
            }
            return "(" + lRes + "|" + rRes + ")";
        } else if(src.contains("\"")){
            return src.substring(1, 2);
        } else {
            String res = "";
            var nums = Arrays.stream(src.split(" ")).toList();
            for(var num : nums) {
                res += eval(rules.get(num), rules);
            }
            return res;
        }
    }
}
