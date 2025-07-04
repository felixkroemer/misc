import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Day10Part1 {

    public static void main(String[] args) throws Exception {
        var nums = new ArrayList<>(Files.readAllLines(Path.of("input")).stream().map(Long::parseLong).toList());
        var diff1 = 0;
        var diff3 = 0;
        Collections.sort(nums);
        nums.add(0, 0L);
        nums.add(nums.getLast() + 3);
        for(int i = 0; i<nums.size()-1; i++) {
            var diff = nums.get(i+1) - nums.get(i);
            if(diff == 1) {
                diff1++;
            }
            if(diff == 3) {
                diff3++;
            }
        }
        System.out.println(diff1 * diff3);

    }


}

