import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var items = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        Set<String> fields = new HashSet<>();
        int sum = 0;

        for(var line : lines) {
            if(line.isBlank()) {
                if(fields.containsAll(items)) {
                    sum++;
                }
                fields.clear();
            }
            String[] parts = line.split(" ");
            for(var part : parts) {
                var field = part.split(":")[0];
                fields.add(field);
            }
        }

        System.out.println(sum);
    }

}
