import java.nio.file.Files;
import java.nio.file.Path;

public class Day1Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var idx = 50;
        var count = 0;
        for(var line : lines) {
          var dir = line.substring(0,1);
          var num = Integer.parseInt(line.substring(1));
          count += num / 100;
          num = Math.floorMod(num, 100);
          if(dir.equals("L")) {
            if(idx != 0 && num >= idx) {
              count++;
            }
            idx = Math.floorMod(idx - num, 100);
          } else {
            if(idx != 0 && idx + num >= 100) {
              count++;
            }
            idx = Math.floorMod(idx + num, 100);
          }
        }
        System.out.println(count);
    }
}
