import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(Long::parseLong).toList();
        for (int i = 25; i < lines.size(); i++) {
            boolean found = false;
            for (int j =  i - 25; j<i; j++) {
                if(found) break;
                for(int k = j + 1; k < i; k++) {
                    if(lines.get(j) + lines.get(k) == lines.get(i)) {
                        found = true;
                    }
                }
            }
            if(!found) {
                System.out.println(lines.get(i));
                return;
            }
        }
    }


}

