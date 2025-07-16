import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day13Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var time = Integer.parseInt(lines.get(0));
        var buses = Arrays.stream(lines.get(1).split(",")).filter(s -> s.matches("\\d+")).map(Integer::valueOf).toList();

        var min = Integer.MAX_VALUE;
        var minBus = 0;
        for(var bus : buses) {
            var rem = bus - (time % bus);
            if (rem < min) {
                min = rem;
                minBus = bus;
            }
        }

        System.out.println(min * minBus);

    }


}

