import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        List<String> mask = null;
        Map<Integer, Long> nums = new HashMap<>();
        for (var line : lines) {
            if (line.startsWith("mask")) {
                mask = Arrays.stream(line.split(" = ")[1].split("")).toList();
                continue;
            }
            var parts = line.split(" ");
            var idx = Integer.parseInt(parts[0].substring(4, parts[0].length() - 1));
            var num = String.format("%36s", Integer.toBinaryString(Integer.parseInt(parts[2]))).replace(' ', '0');
            var n = num.split("");
            assert mask != null;
            for (int i = 0; i < mask.size(); i++) {
                if (mask.get(i).equals("1") || mask.get(i).equals("0"))
                    n[i] = mask.get(i);
            }
            nums.put(idx, Long.parseLong(String.join("", n), 2));
        }
        System.out.println(nums.values().stream().mapToLong(Long::longValue).sum());
    }


}

