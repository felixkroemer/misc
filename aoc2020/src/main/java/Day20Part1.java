import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day20Part1 {

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));
    List<List<String>> page = new ArrayList<>();
    int i = 0;
    Map<Integer, List<Tuple2<Integer, Integer>>> edges = new HashMap<>();
    while (i < lines.size()) {
      var numLine = lines.get(i).split(" ");
      var num = numLine[1].substring(0, numLine[1].length() - 1);
      i++;
      page.clear();
      while (!lines.get(i).isBlank()) {
        page.add(Arrays.stream(lines.get(i).split("")).toList());
        i++;
      }
      int top = 0;
      int topR = 0;
      for (int j = 0; j < page.size(); j++) {
        if (page.getFirst().get(j).equals("#")) {
          top += (int) Math.pow(2, j);
          topR += (int) Math.pow(2, page.size() - 1 - j);
        }
      }
      int left = 0;
      int leftR = 0;
      for (int j = page.size() - 1; j >= 0; j--) {
        if (page.get(j).getFirst().equals("#")) {
          left += (int) Math.pow(2, page.size() - 1 - j);
          leftR += (int) Math.pow(2, j);
        }
      }
      int right = 0;
      int rightR = 0;
      for (int j = 0; j < page.size(); j++) {
        if (page.get(j).get(page.size() - 1).equals("#")) {
          right += (int) Math.pow(2, j);
          rightR += (int) Math.pow(2, page.size() - 1 - j);
        }
      }
      int down = 0;
      int downR = 0;
      for (int j = page.size() - 1; j >= 0; j--) {
        if (page.get(page.size() - 1).get(j).equals("#")) {
          down += (int) Math.pow(2, page.size() - 1 - j);
          downR += (int) Math.pow(2, j);
        }
      }
      edges.put(
          Integer.parseInt(num),
          List.of(
              Tuple2.of(top, topR),
              Tuple2.of(right, rightR),
              Tuple2.of(down, downR),
              Tuple2.of(left, leftR)));
      i++;
    }
    long prod = 1;
    for (var tile : edges.entrySet()) {
      var matches = 0;
      var firstMatch = -1;
      boolean nMatch = false;
      for (var other : edges.entrySet()) {
        if (tile == other) {
          continue;
        }
        for (int j = 0; j < 4; j++) {
          for (int k = 0; k < 4; k++) {
            if (tile.getValue().get(j).getItem1().equals(other.getValue().get(k).getItem1())
                || tile.getValue().get(j).getItem2().equals(other.getValue().get(k).getItem1())) {
              if (firstMatch == -1) {
                firstMatch = j;
              } else {
                if ((Math.abs(j - firstMatch) == 1 || Math.abs(j - firstMatch) == 3)) {
                  nMatch = true;
                }
              }
              matches++;
            }
          }
        }
      }
      if (nMatch && matches == 2) {
        prod *= tile.getKey();
      }
    }
    System.out.println(prod);
  }
}
