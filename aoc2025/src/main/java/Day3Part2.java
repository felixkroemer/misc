import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day3Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(l -> Arrays.stream(l.split("")).map(Integer::parseInt).toList()).toList();
        int k = 12;
        long sum = 0;
        for(var line : lines) {
            int[] j = new int[k];
            for(int i = 0; i<k; i++) {
                j[i] = line.size() - k + i;
            }
            for(int h = 0; h<k; h++) {
                for(int i = j[h]; i > ((h == 0) ? -1 : j[h-1]); i--) {
                    if(line.get(i) >= line.get(j[h])) {
                        j[h] = i;
                    }
                }
            }
            for(int i = 0; i<k; i++) {
                sum += (long) (Math.pow(10, k - i - 1) * line.get(j[i]));
            }
        }
        System.out.println(sum);
    }
}
