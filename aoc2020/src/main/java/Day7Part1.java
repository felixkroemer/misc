import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Pattern pat = Pattern.compile("([a-z]+\\s[a-z]+)\\sbags|(\\d+) ([a-z]+\\s[a-z]+)\\sbag");

        Map<String, Set<String>> map = new HashMap<>();

        for(var line : lines) {
            Matcher mat = pat.matcher(line);
            mat.find();
            var dst = mat.group(1);
            while(mat.find()) {
                var src = mat.group(3);
                if(!map.containsKey(src)) {
                    map.put(src, new HashSet<>());
                }
                map.get(src).add(dst);
            }
        }

        Set<String> found = new HashSet<>();
        Stack<String> s = new Stack<>();
        s.add("shiny gold");
        while(!s.isEmpty()) {
            var node = s.pop();
            found.add(node);
            var dst = map.get(node);
            if(dst != null && !dst.isEmpty()) {
                s.addAll(dst);
            }
        }

        System.out.println(found.size() - 1);

    }

}
