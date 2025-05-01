import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day20Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input")).stream().map(s -> Arrays.stream(s.split("")).toList()).toList();

        var field = lines.subList(2, lines.size());

        var pattern = lines.getFirst();

        var toggle = false;
        for(int i = 0; i<50; i++)          {
            field = enhance(field, pattern, toggle);
            toggle = !toggle;
        }

        int count = 0;
        for (var line : field) {
            for (var e : line) {
                if (e.equals("#")) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    static List<List<String>> enhance(List<List<String>> field, List<String> pattern, boolean toggle) {
        var newField = new ArrayList<List<String>>(field.size() + 2);
        for (int i = 0; i < field.size() + 2; i++) {
            newField.add(new ArrayList<>(Arrays.stream(".".repeat(field.getFirst().size() + 2).split("")).toList()));
        }
        for (int i = 0; i < newField.size(); i++) {
            for (int j = 0; j < newField.getFirst().size(); j++) {
                int p = 8;
                int val = 0;
                for (int[] dir : new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}) {
                    try {
                        if (field.get(i - 1 + dir[0]).get(j - 1 + dir[1]).equals("#")) {
                            val += (int) Math.pow(2, p);
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                        if (toggle) val += (int) Math.pow(2, p);
                    } finally {
                        p--;
                    }
                }
                newField.get(i).set(j, pattern.get(val));
            }
        }
        return newField;
    }

}
