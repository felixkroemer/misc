import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Part2 {

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
        var sum = 0;
        String regexTemplate = eval(rules.get("0"), rules);
        for(int k = 1; k<20; k++) {
            int j = i;
            var regex = regexTemplate.replaceAll("TIMES", "" + k);
            while (j < lines.size()) {
                if(lines.get(j).matches(regex)) sum++;
                j++;
            }
        }
        System.out.println(sum);
    }

    public static String eval(String src, Map<String, String> rules) {
        src = src.trim();
        if (src.equals("42")) {
            return "(" + eval(rules.get("42"), rules) + ")+";
        } else if (src.equals("42 31")) {
            var id = (int) (Math.random() * 100000);
            return "((?<A" + id + ">(" + eval(rules.get("42"), rules) + "){TIMES})(?<B" + id + ">(" + eval(rules.get("31"), rules) + "){TIMES}))";
        } else if (src.contains("|")) {
            var parts = src.split("\\|");
            var left = parts[0].trim().split(" ");
            var right = parts[1].trim().split(" ");
            var lRes = "";
            for (var l : left) {
                lRes += eval(rules.get(l), rules);
            }
            var rRes = "";
            for (var r : right) {
                rRes += eval(rules.get(r), rules);
            }
            return "(" + lRes + "|" + rRes + ")";
        } else if (src.contains("\"")) {
            return src.substring(1, 2);
        } else {
            String res = "";
            var nums = Arrays.stream(src.split(" ")).toList();
            for (var num : nums) {
                res += eval(rules.get(num), rules);
            }
            return res;
        }
    }
}
