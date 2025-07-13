import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11Part2 {

    public static void main(String[] args) throws Exception {
        List<List<String>> a = new ArrayList<>(Files.readAllLines(Path.of("input")).stream().map(s -> new ArrayList<>(Arrays.stream(s.split("")).toList())).toList());
        List<List<String>> b = new ArrayList<>(a.size());
        for (int i = 0; i < a.size(); i++) {
            List<String> x = new ArrayList<>(a.getFirst().size());
            b.add(x);
            for (int j = 0; j < a.getFirst().size(); j++) {
                x.add(a.get(i).get(j));
            }
        }
        int k = 0;
        while (true) {
            for (int i = 0; i < a.size(); i++) {
                for (int j = 0; j < a.getFirst().size(); j++) {
                    if (a.get(i).get(j).equals(".")) {
                        continue;
                    }
                    int c = 0;
                    for (var dir : new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1},}) {
                        int m = 1;
                        label:
                        while (true) {
                            try {
                                var d = a.get(i + dir[0] * m).get(j + dir[1] * m);
                                switch (d) {
                                    case "#":
                                        c++;
                                        break label;
                                    case ".":
                                        m++;
                                        break;
                                    case "L":
                                        break label;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                break;
                            }
                        }
                    }
                    if (a.get(i).get(j).equals("L") && c == 0) {
                        b.get(i).set(j, "#");
                    }
                    if (a.get(i).get(j).equals("#") && c >= 5) {
                        b.get(i).set(j, "L");
                    }
                }
            }
            if (a.hashCode() == b.hashCode()) {
                int s = 0;
                for (int i = 0; i < a.size(); i++) {
                    for (int j = 0; j < a.getFirst().size(); j++) {
                        if (a.get(i).get(j).equals("#")) {
                            s++;
                        }
                    }
                }
                System.out.println(s);
                return;
            } else {
                k++;
            }
            for (int i = 0; i < a.size(); i++) {
                for (int j = 0; j < a.getFirst().size(); j++) {
                    a.get(i).set(j, b.get(i).get(j));
                }
            }
        }

    }


}

