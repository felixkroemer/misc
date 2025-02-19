import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day15Part2 {

    public static void main(String[] args) throws Exception {
        Multi.createFrom().items(Files.readAllLines(Path.of("input")).stream())
                .map(line -> Arrays.asList(line.split(""))).collect().asList().map(map -> {
                    var splitIndex = map.indexOf(map.stream().filter(l -> l.size() == 1).findFirst().get());
                    var field = map.subList(0, splitIndex);
                    var movements = map.subList(splitIndex, map.size()).stream()
                            .flatMap(List::stream).filter(s -> !s.equals("\n") && !s.isEmpty())
                            .toList();

                    int[] head = new int[2];

                    field.replaceAll(elements -> Arrays.asList(String.join("", elements).replaceAll("#", "##").replaceAll("O", "[]").replaceAll("\\.", "..").replaceAll("@", "@.").split("")));

                    var dirs = new HashMap<String, int[]>();
                    dirs.put("^", new int[]{-1, 0});
                    dirs.put(">", new int[]{0, 1});
                    dirs.put("<", new int[]{0, -1});
                    dirs.put("v", new int[]{1, 0});

                    for (var m : movements) {
                        for (int i = 0; i < field.size(); i++) {
                            for (int j = 0; j < field.getFirst().size(); j++) {
                                if (field.get(i).get(j).equals("@")) {
                                    head[0] = i;
                                    head[1] = j;
                                }
                            }
                        }
                        int[] dir = dirs.get(m);
                        Map<Tuple2<Integer, Integer>, String> relocate = new LinkedHashMap<>();
                        if (m.equals("<") || m.equals(">")) {
                            var next = new int[]{head[0], head[1]};
                            var br = false;
                            while (!br) {
                                relocate.put(Tuple2.of(next[0], next[1]), field.get(next[0]).get(next[1]));
                                switch (field.get(next[0] + dir[0]).get(next[1] + dir[1])) {
                                    case "[":
                                    case "]":
                                        next[0] += dir[0];
                                        next[1] += dir[1];
                                        break;
                                    case ".":
                                        br = true;
                                        break;
                                    case "#":
                                        relocate.clear();
                                        br = true;
                                }
                            }
                        } else {
                            ArrayList<Set<Tuple2<Integer, Integer>>> levels = new ArrayList<>();
                            levels.add(new HashSet<>());
                            levels.getFirst().add(Tuple2.of(head[0], head[1]));
                            var br = false;
                            while (true) {
                                if (br || levels.getLast().isEmpty()) {
                                    break;
                                }
                                levels.add(new HashSet<>());
                                for (var item : levels.get(levels.size() - 2)) {
                                    relocate.put(Tuple2.of(item.getItem1(), item.getItem2()), field.get(item.getItem1()).get(item.getItem2()));
                                    if (field.get(item.getItem1() + dir[0]).get(item.getItem2()).equals("[")) {
                                        levels.getLast().add(Tuple2.of(item.getItem1() + dir[0], item.getItem2()));
                                        levels.getLast().add(Tuple2.of(item.getItem1() + dir[0], item.getItem2() + 1));
                                    }
                                    if (field.get(item.getItem1() + dir[0]).get(item.getItem2()).equals("]")) {
                                        levels.getLast().add(Tuple2.of(item.getItem1() + dir[0], item.getItem2()));
                                        levels.getLast().add(Tuple2.of(item.getItem1() + dir[0], item.getItem2() - 1));
                                    }
                                    if (field.get(item.getItem1() + dir[0]).get(item.getItem2()).equals("#")) {
                                        relocate.clear();
                                        br = true;
                                        break;
                                    }
                                }
                            }
                        }
                        List<Map.Entry<Tuple2<Integer, Integer>, String>> entries = new ArrayList<>(relocate.entrySet());
                        Collections.reverse(entries);
                        for (var entry : entries) {
                            field.get(entry.getKey().getItem1() + dir[0]).set(entry.getKey().getItem2() + dir[1], entry.getValue());
                            field.get(entry.getKey().getItem1()).set(entry.getKey().getItem2(), ".");
                        }
                    }
                    return field;
                }).map(field -> {
                    var sum = 0;
                    for (int i = 0; i < field.size(); i++) {
                        for (int j = 0; j < field.get(0).size(); j++) {
                            if (field.get(i).get(j).equals("[")) {
                                sum += 100 * i + j;
                            }
                        }
                    }
                    return sum;
                }).subscribe().with(System.out::println);
    }
}
