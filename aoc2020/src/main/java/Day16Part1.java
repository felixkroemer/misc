import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16Part1 {

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));
    int i = 0;

    Pattern pat = Pattern.compile("[a-z]+:\\s(\\d+)-(\\d+)\\sor\\s(\\d+)-(\\d+)$");
    Set<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> rules = new HashSet<>();
    while (!lines.get(i).isBlank()) {
      var rule = lines.get(i);
      Matcher mat = pat.matcher(rule);
      mat.find();
      rules.add(
          Tuple2.of(
              Tuple2.of(Integer.parseInt(mat.group(1)), Integer.parseInt(mat.group(2))),
              Tuple2.of(Integer.parseInt(mat.group(3)), Integer.parseInt(mat.group(4)))));
      i++;
    }
    i += 5;
    var sum = 0;
    for (int j = i; j < lines.size(); j++) {
      var nums =
          Arrays.stream(lines.get(j).split(",")).map(Integer::parseInt).toList().stream()
              .filter(
                  v ->
                      rules.stream()
                          .allMatch(
                              r ->
                                  v < r.getItem1().getItem1()
                                      || (v > r.getItem1().getItem2()
                                          && v < r.getItem2().getItem1())
                                      || v > r.getItem2().getItem2()));
      sum += nums.mapToInt(v -> v).sum();
    }
    System.out.println(sum);
  }
}
