import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class Day1Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var idx = 50;
        var count = 0;
        for(var line : lines) {
          var dir = line.substring(0,1);
          if(dir.equals("L")) {
            idx = Math.floorMod(idx - Integer.parseInt(line.substring(1)), 100);
          } else {
            idx = Math.floorMod(idx + Integer.parseInt(line.substring(1)), 100);
          }
          if(idx == 0) {
            count++;
          }
        }
        System.out.println(count);
    }
}
