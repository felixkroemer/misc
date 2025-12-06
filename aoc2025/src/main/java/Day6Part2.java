import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class Day6Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(s -> s.split("")).toList();
        long sum = 0;
        int idx = 0;
        int lastNum = 0;
        while (true) {
            var op = lines.get(lines.size() - 1)[idx];
            var mult = op.equals("*");
            idx++;
            while (idx < lines.get(0).length && lines.get(lines.size() - 1)[idx].equals(" ")) {
                idx++;
            }
            if (idx == lines.get(0).length) idx++;
            var nums = new ArrayList<String>();
            for (int j = idx - 2; j >= lastNum; j--) {
                var num = "";
                for (int i = 0; i < lines.size() - 1; i++) {
                    num += lines.get(i)[j];
                }
                nums.add(num);
            }
            long res = mult ? 1 : 0;
            for (var number : nums) {
                res = mult ? res * Long.parseLong(number.trim()) : res + Long.parseLong(number.trim());
            }
            sum += res;
            lastNum = idx;
            if (idx == lines.get(0).length + 1) {
                break;
            }
        }
        System.out.println(sum);
    }
}
