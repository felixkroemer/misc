import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Pattern pat = Pattern.compile("([a-z]+\\s[a-z]+)\\sbags|(\\d+) ([a-z]+\\s[a-z]+)\\sbag");

        Map<String, Set<Tuple2<Integer, String>>> map = new HashMap<>();

        for(var line : lines) {
            Matcher mat = pat.matcher(line);
            mat.find();
            var src = mat.group(1);
            while(mat.find()) {
                if(mat.group(0).equals("no other bags")) continue;
                var dst = mat.group(3);
                if(!map.containsKey(src)) {
                    map.put(src, new HashSet<>());
                }
                map.get(src).add(Tuple2.of(Integer.parseInt(mat.group(2)), dst));
            }
        }

        Stack<Tuple2<Integer, String>> s = new Stack<>();
        s.add(Tuple2.of(1, "shiny gold"));
        var sum = 0;
        while(!s.isEmpty()) {
            var node = s.pop();
            var dst = map.get(node.getItem2());
            if(dst != null && !dst.isEmpty()) {
                for(var d : dst) {
                    sum += node.getItem1() * d.getItem1();
                    s.add(Tuple2.of(node.getItem1() * d.getItem1(), d.getItem2()));
                }
            }
        }
        System.out.println(sum);
    }

}
