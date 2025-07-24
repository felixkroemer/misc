import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day14Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        List<String> mask = null;
        Map<Long, Long> nums = new HashMap<>();
        for (var line : lines) {
            if (line.startsWith("mask")) {
                mask = Arrays.stream(line.split(" = ")[1].split("")).toList();
                continue;
            }
            var parts = line.split(" ");
            var idx = Integer.parseInt(parts[0].substring(4, parts[0].length() - 1));
            long val = Long.parseLong(parts[2]);

            var addr = String.format("%36s", Integer.toBinaryString(idx)).replace(' ', '0').split("");
            assert mask != null;
            for (int i = 0; i < mask.size(); i++) {
                if (mask.get(i).equals("1")) {
                    addr[i] = "1";
                }
                if (mask.get(i).equals("X")) {
                    addr[i] = "X";
                }
            }

            List<Long> a = new ArrayList<>();
            calc(String.join("", addr), 0, 0, a);

            for (var e : a) {
                nums.put(e, val);
            }

        }

        System.out.println(nums.values().stream().mapToLong(Long::longValue).sum());
    }

    static void calc(String s, int offset, long sum, List<Long> nums) {
        if (offset == 36) {
            nums.add(sum);
            return;
        }
        if (s.charAt(offset) == '1') {
            var l = (long) Math.pow(2, 35 - offset);
            calc(s, offset + 1, sum + l, nums);
        } else if (s.charAt(offset) == '0') {
            calc(s, offset + 1, sum, nums);
        } else if (s.charAt(offset) == 'X') {
            var l = (long) Math.pow(2, 35 - offset);
            calc(s, offset + 1, sum + l, nums);
            calc(s, offset + 1, sum, nums);
        }
    }


}

