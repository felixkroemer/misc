import io.smallrye.mutiny.tuples.Tuple2;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day20Part2 {

  private static List<Tuple2<Integer, Integer>> a(List<List<String>> page) {
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
    for (int j = 0; j < page.size(); j++) {
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
    for (int j = 0; j < page.size(); j++) {
      if (page.getLast().get(j).equals("#")) {
        down += (int) Math.pow(2, page.size() - 1 - j);
        downR += (int) Math.pow(2, j);
      }
    }
    return List.of(
        Tuple2.of(top, topR),
        Tuple2.of(right, rightR),
        Tuple2.of(down, downR),
        Tuple2.of(left, leftR));
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));
    List<List<String>> page = new ArrayList<>();
    int i = 0;
    LinkedHashMap<Integer, List<Tuple2<Integer, Integer>>> edges = new LinkedHashMap<>();
    Map<Integer, List<List<String>>> pages = new HashMap<>();
    while (i < lines.size()) {
      var numLine = lines.get(i).split(" ");
      var num = numLine[1].substring(0, numLine[1].length() - 1);
      i++;
      page.clear();
      while (!lines.get(i).isBlank()) {
        page.add(Arrays.stream(lines.get(i).split("")).toList());
        i++;
      }

      edges.put(Integer.parseInt(num), a(page));
      pages.put(Integer.parseInt(num), new ArrayList<>(page));
      i++;
    }

    int start = -1;
    for (var tile : edges.entrySet()) {
      var matches = new LinkedList<Integer>();
      for (var other : edges.entrySet()) {
        if (Objects.equals(tile.getKey(), other.getKey())) {
          continue;
        }
        for (int j = 0; j < 4; j++) {
          for (int k = 0; k < 4; k++) {
            if (tile.getValue().get(j).getItem1().equals(other.getValue().get(k).getItem1())
                || tile.getValue().get(j).getItem1().equals(other.getValue().get(k).getItem2())
                || tile.getValue().get(j).getItem2().equals(other.getValue().get(k).getItem1())
                || tile.getValue().get(j).getItem2().equals(other.getValue().get(k).getItem2())) {
              matches.add(j);
            }
          }
        }
      }
      if (matches.size() == 2) {
        int diff = Math.abs(matches.get(0) - matches.get(1));
        if (diff == 1 || diff == 3) {
          start = tile.getKey();
          break;
        }
      }
    }

    var sideLen = (int) Math.sqrt(edges.size());
    var pageLen = pages.get(start).getFirst().size();

    var pageCopy = pages.get(start);

    for (int rot = 0; rot < 4; rot++) {
      for (int flip = 0; flip < 2; flip++) {
        for (int flipVert = 0; flipVert < 2; flipVert++) {
          try {
            var pageTransform = transformPage(pageCopy, rot, flip == 1, flipVert == 1);

            pages.put(start, pageTransform);
            edges.put(start, a(pageTransform));

            List<List<String>> field = new ArrayList<>();
            for (int j = 0; j < sideLen * (pageLen - 2); j++) {
              var l = new ArrayList<String>();
              for (int k = 0; k < sideLen * (pageLen - 2); k++) {
                l.add(" ");
              }
              field.add(l);
            }

            Map<Integer, Tuple2<Integer, Integer>> placements = new HashMap<>();

            var pos = Tuple2.of(0, 0);
            place(pos, pages.get(start), field);
            placements.put(start, pos);

            LinkedHashMap<Integer, List<Tuple2<Integer, Integer>>> transformedEdges =
                new LinkedHashMap<>();
            transformedEdges.put(start, edges.get(start));

            var queue = new LinkedList<Integer>();
            queue.add(start);
            while (!queue.isEmpty()) {
              var key = queue.pop();
              var edge = transformedEdges.get(key);
              var placement = placements.get(key);
              for (var other : edges.entrySet()) {
                if (other.getKey().equals(key) || transformedEdges.containsKey(other.getKey())) {
                  continue;
                }
                var otherEdge = other.getValue();
                int r;
                for (int j = 0; j < 4; j++) {
                  for (int k = 0; k < 4; k++) {
                    List<List<String>> transform = null;
                    // gegenläufig -> flip
                    r = Math.floorMod(j - k - 2, 4);
                    if (edge.get(j).getItem1().equals(otherEdge.get(k).getItem1())) {
                      transform =
                          transformPage(
                              pages.get(other.getKey()), r, j == 0 || j == 2, j == 1 || j == 3);
                      // no flip
                    } else if (edge.get(j).getItem2().equals(otherEdge.get(k).getItem1())) {
                      transform = transformPage(pages.get(other.getKey()), r, false, false);
                    } else {
                      continue;
                    }

                    transformedEdges.put(other.getKey(), a(transform));
                    Tuple2<Integer, Integer> newPlacement = null;
                    switch (j) {
                      case 0 -> {
                        newPlacement = Tuple2.of(placement.getItem1() - 1, placement.getItem2());
                        place(newPlacement, transform, field);
                      }
                      case 1 -> {
                        newPlacement = Tuple2.of(placement.getItem1(), placement.getItem2() + 1);
                        place(newPlacement, transform, field);
                      }
                      case 2 -> {
                        newPlacement = Tuple2.of(placement.getItem1() + 1, placement.getItem2());
                        place(newPlacement, transform, field);
                      }
                      case 3 -> {
                        newPlacement = Tuple2.of(placement.getItem1(), placement.getItem2() - 1);
                        place(newPlacement, transform, field);
                      }
                    }
                    placements.put(other.getKey(), newPlacement);
                    queue.add(other.getKey());
                  }
                }
              }
            }

            var monster =
                List.of(
                    Arrays.stream("..................#.".split("")).toList(),
                    Arrays.stream("#....##....##....###".split("")).toList(),
                    Arrays.stream(".#..#..#..#..#..#...".split("")).toList());

            int hashtags = 0;
            for (var u : field) {
              for (var v : u) {
                if (v.equals("#")) {
                  hashtags++;
                }
              }
              System.out.println();
            }

            List<List<String>> fieldCopy = new ArrayList<>();
            for (var row : field) {
              var col = new ArrayList<>(row);
              fieldCopy.add(col);
            }

            for (int j = 0; j < 3; j++) {
              for (int k = 0; k < 2; k++) {
                for (int l = 0; l < 2; l++) {
                  var transformedField = transformPage(fieldCopy, j, k == 1, l == 1);
                  var positions = findShapeOccurrences(transformedField, monster);
                  if (positions.isEmpty()) {
                    continue;
                  }
                  for (var u : transformedField) {
                    for (var v : u) {
                      System.out.print(v);
                    }
                    System.out.println();
                  }
                  System.out.println(hashtags - positions.size());
                }
              }
            }
          } catch (IndexOutOfBoundsException ignored) {
          }
        }
      }
    }
  }

  static List<List<String>> transformPage(
      List<List<String>> page, int rot, boolean flipHorizontal, boolean flipVertical) {
    if (page == null || page.isEmpty()) {
      return page;
    }

    List<List<String>> result = page;
    for (int r = 0; r < (rot % 4); r++) {
      result = rotate90Clockwise(result);
    }

    if (flipHorizontal) {
      result = flipHorizontal(result);
    }

    if (flipVertical) {
      result = flipVertical(result);
    }

    return result;
  }

  private static List<List<String>> rotate90Clockwise(List<List<String>> grid) {
    int rows = grid.size();
    int cols = grid.getFirst().size();
    List<List<String>> rotated = new ArrayList<>();

    for (int j = 0; j < cols; j++) {
      List<String> newRow = new ArrayList<>();
      for (int i = rows - 1; i >= 0; i--) {
        newRow.add(grid.get(i).get(j));
      }
      rotated.add(newRow);
    }

    return rotated;
  }

  private static List<List<String>> flipHorizontal(List<List<String>> grid) {
    List<List<String>> flipped = new ArrayList<>();

    for (List<String> row : grid) {
      List<String> newRow = new ArrayList<>(row);
      Collections.reverse(newRow);
      flipped.add(newRow);
    }

    return flipped;
  }

  private static List<List<String>> flipVertical(List<List<String>> grid) {
    List<List<String>> flipped = new ArrayList<>();

    for (int i = grid.size() - 1; i >= 0; i--) {
      flipped.add(new ArrayList<>(grid.get(i)));
    }

    return flipped;
  }

  static void place(
      Tuple2<Integer, Integer> placement, List<List<String>> page, List<List<String>> field) {
    var pageLen = page.getFirst().size() - 2;
    for (int i = 1; i < page.size() - 1; i++) {
      for (int j = 1; j < page.getFirst().size() - 1; j++) {
        field
            .get(placement.getItem1() * pageLen + i - 1)
            .set(placement.getItem2() * pageLen + j - 1, page.get(i).get(j));
      }
    }
  }

  public static HashSet<Tuple2<Integer, Integer>> findShapeOccurrences(
      List<List<String>> inputMap, List<List<String>> shape) {

    HashSet<Tuple2<Integer, Integer>> result = new HashSet<>();

    if (inputMap == null || shape == null || inputMap.isEmpty() || shape.isEmpty()) {
      return result;
    }

    int mapHeight = inputMap.size();
    int mapWidth = inputMap.get(0).size();
    int shapeHeight = shape.size();
    int shapeWidth = shape.get(0).size();

    if (shapeHeight > mapHeight || shapeWidth > mapWidth) {
      return result;
    }

    for (int startRow = 0; startRow <= mapHeight - shapeHeight; startRow++) {
      for (int startCol = 0; startCol <= mapWidth - shapeWidth; startCol++) {

        if (isShapeMatch(inputMap, shape, startRow, startCol)) {
          addShapePositions(result, shape, startRow, startCol);
        }
      }
    }

    return result;
  }

  private static boolean isShapeMatch(
      List<List<String>> inputMap, List<List<String>> shape, int startRow, int startCol) {

    for (int shapeRow = 0; shapeRow < shape.size(); shapeRow++) {
      for (int shapeCol = 0; shapeCol < shape.get(shapeRow).size(); shapeCol++) {
        int mapRow = startRow + shapeRow;
        int mapCol = startCol + shapeCol;

        String shapeCell = shape.get(shapeRow).get(shapeCol);
        String mapCell = inputMap.get(mapRow).get(mapCol);

        if ("#".equals(shapeCell) && !("#".equals(mapCell))) {
          return false;
        }
      }
    }
    return true;
  }

  private static void addShapePositions(
      HashSet<Tuple2<Integer, Integer>> result,
      List<List<String>> shape,
      int startRow,
      int startCol) {

    for (int shapeRow = 0; shapeRow < shape.size(); shapeRow++) {
      for (int shapeCol = 0; shapeCol < shape.get(shapeRow).size(); shapeCol++) {
        String shapeCell = shape.get(shapeRow).get(shapeCol);

        if ("#".equals(shapeCell)) {
          int mapRow = startRow + shapeRow;
          int mapCol = startCol + shapeCol;
          result.add(Tuple2.of(mapRow, mapCol));
        }
      }
    }
  }
}
