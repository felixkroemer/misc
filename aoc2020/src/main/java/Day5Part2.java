import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day5Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        Set<Integer> seats = new HashSet<>();

        for(var line : lines) {
            String row = line.substring(0,7);
            String col = line.substring(7);

            int lowerRow = 0;
            int higherRow = 127;
            for(int i = 0; i<row.length(); i++) {
                var diff = ((higherRow - lowerRow) / 2) + 1;
                if(row.charAt(i) == 'F') {
                    higherRow -= diff;
                } else {
                    lowerRow += diff;
                }
            }

            int lowerCol = 0;
            int higherCol = 7;
            for(int i = 0; i<col.length(); i++) {
                var diff = ((higherCol - lowerCol) / 2) + 1;
                if(col.charAt(i) == 'L') {
                    higherCol -= diff;
                } else {
                    lowerCol += diff;
                }
            }

            var val = lowerRow * 8 + lowerCol;
            seats.add(val);

        }

        for(int i = 0; i<1024; i++) {
            if(!seats.contains(i)) {
                System.out.println(i);
            }
        }


    }

}
