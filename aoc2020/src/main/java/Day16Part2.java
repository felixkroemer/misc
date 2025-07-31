import io.smallrye.mutiny.tuples.Tuple2;
import jdk.jshell.spi.SPIResolutionException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16Part2 {

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));
    int i = 0;

    Pattern pat = Pattern.compile("([a-z\\s]+):\\s(\\d+)-(\\d+)\\sor\\s(\\d+)-(\\d+)$");
    Map<String, Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> rules = new HashMap<>();
    while (!lines.get(i).isBlank()) {
      var rule = lines.get(i);
      Matcher mat = pat.matcher(rule);
      mat.find();
      rules.put(
          mat.group(1),
          Tuple2.of(
              Tuple2.of(Integer.parseInt(mat.group(2)), Integer.parseInt(mat.group(3))),
              Tuple2.of(Integer.parseInt(mat.group(4)), Integer.parseInt(mat.group(5)))));
      i++;
    }
    i += 2;
    var myTicket = Arrays.stream(lines.get(i).split(",")).map(Integer::parseInt).toList();
    i += 3;
    Map<Integer, Set<String>> pos = new HashMap<>();
    for (int j = 0; j < myTicket.size(); j++) {
      pos.put(j, new HashSet<>(rules.keySet()));
    }

    for (int j = i; j < lines.size(); j++) {
      if (Arrays.stream(lines.get(j).split(",")).map(Integer::parseInt).toList().stream()
          .anyMatch(
              v ->
                  rules.values().stream()
                      .allMatch(
                          r ->
                              v < r.getItem1().getItem1()
                                  || (v > r.getItem1().getItem2() && v < r.getItem2().getItem1())
                                  || v > r.getItem2().getItem2()))) {
        continue;
      }
      var line = Arrays.stream(lines.get(j).split(",")).map(Integer::parseInt).toList();
      for (int k = 0; k < line.size(); k++) {
        var num = line.get(k);
        pos.put(
            k,
            pos.get(k).stream()
                .filter(
                    v ->
                        num >= rules.get(v).getItem1().getItem1()
                                && num <= rules.get(v).getItem1().getItem2()
                            || num >= rules.get(v).getItem2().getItem1()
                                && num <= rules.get(v).getItem2().getItem2())
                .collect(Collectors.toSet()));
      }
    }

    /*
    0 departure date
    1 departure time
    2 route
    3 class
    4 price
    5 departure platform
    6
    7
    8 type
    9 departure track
    10
    11
    12 departure station
    13 zone
    14 arrival track
    15 wagon
    16
    17 arrival location
    18
    19 departure location
     */


    var keys = List.of(0, 1, 5, 9, 12, 19);

    long prod = 1;
    for (var key : keys) {
      prod *= myTicket.get(key);
    }

    System.out.println(prod);
  }
}
