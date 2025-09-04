import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day21Part2 {

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));

    Map<String, Set<Integer>> allergyMappings = new HashMap<>();
    Map<Integer, Set<String>> lineMapping = new HashMap<>();
    var allIngredients = new HashSet<String>();

    int i = 0;
    for (var line : lines) {
      var parts = line.split("\\(contains ");
      var ingredients = Arrays.asList(parts[0].strip().split(" "));
      Set<String> a = lineMapping.computeIfAbsent(i, k -> new HashSet<>());
      a.addAll(ingredients);
      allIngredients.addAll(a);
      var allergies = parts[1].substring(0, parts[1].length() - 1).split(", ");
      for (var allergy : allergies) {
        Set<Integer> list = allergyMappings.computeIfAbsent(allergy, k -> new HashSet<>());
        list.add(i);
      }
      i++;
    }

    Map<String, String> mapping = new HashMap<>();
    while (!allergyMappings.isEmpty()) {
      String toRemove = null;
      for (var e : allergyMappings.entrySet()) {
        Set<String> possible = new HashSet<>();
        for (var line : e.getValue()) {
          if (possible.isEmpty()) {
            possible.addAll(lineMapping.get(line));
          } else {
            possible =
                possible.stream()
                    .filter(p -> lineMapping.get(line).contains(p))
                    .collect(Collectors.toSet());
          }
        }
        if (possible.size() == 1) {
          var ingredient = possible.iterator().next();
          toRemove = e.getKey();
          mapping.put(e.getKey(), ingredient);
          lineMapping.replaceAll(
              (l, v) ->
                  lineMapping.get(l).stream()
                      .filter(p -> !p.equals(ingredient))
                      .collect(Collectors.toSet()));
          break;
        }
      }
      if (toRemove != null) {
        allergyMappings.remove(toRemove);
      } else {
        throw new RuntimeException("indeterminate");
      }
    }
    List<String> sortedValues =
        mapping.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    System.out.println(String.join(",", sortedValues));
  }
}
