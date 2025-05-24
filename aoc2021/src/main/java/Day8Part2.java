import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day8Part2 {

    static Set<String> l0 = Set.of("a", "b", "c", "e", "f", "g");
    static Set<String> l1 = Set.of("c", "f");
    static Set<String> l2 = Set.of("a", "c", "d", "e", "g");
    static Set<String> l3 = Set.of("a", "c", "d", "f", "g");
    static Set<String> l4 = Set.of("b", "c", "d", "f");
    static Set<String> l5 = Set.of("a", "b", "d", "f", "g");
    static Set<String> l6 = Set.of("a", "b", "d", "e", "f", "g");
    static Set<String> l7 = Set.of("a", "c", "f");
    static Set<String> l8 = Set.of("a", "b", "c", "d", "e", "f", "g");
    static Set<String> l9 = Set.of("a", "b", "c", "d", "f", "g");
    static List<Set<String>> nums = List.of(l0, l1, l2, l3, l4, l5, l6, l7, l8, l9);

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int sum = 0;

        for (var line : lines) {
            var inputs = Arrays.stream(line.split("\\|")[0].strip().split(" ")).toList();
            var outputs = Arrays.stream(line.split("\\|")[1].strip().split(" ")).toList();
            List<String> items = List.of("a", "b", "c", "d", "e", "f", "g");
            var possible = new HashMap<String, Set<String>>();
            for (var item : items) {
                possible.put(item, new HashSet<>(items));
            }
            for (var input : inputs) {
                var in = Arrays.stream(input.split("")).toList();
                switch (in.size()) {
                    case 2: // 1
                        possible.get("c").retainAll(in);
                        possible.get("f").retainAll(in);
                        break;
                    case 3: // 7
                        possible.get("a").retainAll(in);
                        possible.get("c").retainAll(in);
                        possible.get("f").retainAll(in);
                        break;
                    case 4: // 4
                        possible.get("b").retainAll(in);
                        possible.get("c").retainAll(in);
                        possible.get("d").retainAll(in);
                        possible.get("f").retainAll(in);
                        break;
                    default:
                }
            }
            Map<String, String> selected = new HashMap<>();
            var res = testConfig("a", possible, new ArrayList<>(), inputs, outputs, selected);
            System.out.println(res);
            sum += res;
        }
        System.out.println(sum);
    }

    static int testConfig(String c, Map<String, Set<String>> possible, List<String> picked, List<String> inputs,
                          List<String> outputs, Map<String, String> selected) {
        if (selected.size() == 7) {
            var inv = selected.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            var combined = new ArrayList<>(inputs);
            combined.addAll(outputs);
            for (var number : combined) {
                var sources = Arrays.stream(number.split("")).map(inv::get).toList();
                if (nums.stream().filter(n -> n.size() == sources.size() && n.containsAll(sources)).findAny()
                        .isEmpty()) {
                    return 0;
                }
            }
            int sum = 0;
            var index = 0;
            for (var number : outputs) {
                var sources = Arrays.stream(number.split("")).map(inv::get).toList();
                var idx = 0;
                for (var num : nums) {
                    if (num.size() == sources.size() && num.containsAll(sources)) {
                        sum += idx * (int) Math.pow(10, 3 - index);
                        break;
                    }
                    idx++;
                }
                index++;
            }
            return sum;
        }
        for (var p : possible.get(c)) {
            if (!picked.contains(p)) {
                var l = new ArrayList<>(picked);
                l.add(p);
                selected.put(c, p);
                var res = testConfig("" + (char) (c.charAt(0) + 1), possible, l, inputs, outputs, selected);
                if (res != 0) {
                    return res;
                } else {
                    selected.remove(c);
                }
            }
        }
        return 0;
    }
}
