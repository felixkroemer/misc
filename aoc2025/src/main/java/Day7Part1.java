import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day7Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(s  -> Arrays.stream(s.split("")).toList()).toList();
        int sum = 0;
        var splitters = new ArrayList<HashSet<Integer>>();
        for(var line : lines) {
            var h = new HashSet<Integer>();
            splitters.add(h);
            int j = 0;
            for( var c : line)  {
                if(c.equals("S") || c.equals("^")) {
                    h.add(j);
                }
                j++;
            }
        }
        Set<Integer> beams = new HashSet<>(splitters.get(0));
        for(int i = 0; i<lines.size()-1; i++) {
            Set<Integer> newBeams = new HashSet<>();
            for(var beam : beams) {
                if(lines.get(i+1).get(beam).equals("^")) {
                    newBeams.add(beam-1);
                    newBeams.add(beam + 1);
                    sum++;
                } else {
                    newBeams.add(beam);
                }
            }
            beams = newBeams;
        }

        System.out.println(sum);
    }
}
