import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day4Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var nums = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).toList();
        lines = lines.subList(2, lines.size());
        List<List<List<Integer>>> boards = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        for (var line : lines) {
            if (line.isBlank()) {
                boards.add(new ArrayList<>(tmp.stream().map(String::strip)
                                              .map(s -> Arrays.stream(s.split("\\s+")).map(Integer::valueOf).toList())
                                              .toList()));
                tmp.clear();
            } else {
                tmp.add(line);
            }
        }
        boards.add(new ArrayList<>(tmp.stream().map(String::strip)
                                      .map(s -> Arrays.stream(s.split("\\s+")).map(Integer::valueOf).toList())
                                      .toList()));
        var called = new HashSet<Integer>();
        boolean found = false;
        for (var num : nums) {
            if (found) {
                break;
            }
            called.add(num);
            for (var board : boards) {
                for (int i = 0; i < 5; i++) {
                    boolean test = true;
                    for (int j = 0; j < 5; j++) {
                        if (!called.contains(board.get(i).get(j))) {
                            test = false;
                            break;
                        }
                    }
                    if (test) {
                        found = true;
                        break;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    boolean test = true;
                    for (int j = 0; j < 5; j++) {
                        if (!called.contains(board.get(j).get(i))) {
                            test = false;
                            break;
                        }
                    }
                    if (test) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    var sum = 0;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (!called.contains(board.get(i).get(j))) {
                                sum += board.get(i).get(j);
                            }
                        }
                    }
                    System.out.println(sum * num);
                    break;
                }
            }
        }
    }
}
