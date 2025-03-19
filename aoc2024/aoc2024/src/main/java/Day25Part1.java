import io.smallrye.mutiny.Uni;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day25Part1 {
    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            List<List<String>> items = new ArrayList<>();
            List<String> currentSublist = new ArrayList<>();
            for (var item : lines) {
                if (!item.isEmpty()) {
                    currentSublist.add(item);
                } else {
                    items.add(new ArrayList<>(currentSublist));
                    currentSublist.clear();
                }
            }
            items.add(new ArrayList<>(currentSublist));

            List<List<Integer>> locks = new ArrayList<>();
            List<List<Integer>> keys = new ArrayList<>();

            for (var item : items) {

                if (item.getFirst().equals(".....")) {
                    var key = new ArrayList<>(List.of(0, 0, 0, 0, 0));
                    for (int i = 0; i < item.size(); i++) {
                        for (int j = 0; j < item.get(i).length(); j++) {
                            if (key.get(j) == 0 && item.get(i).charAt(j) != '.') {
                                key.set(j, 7 - i - 1);
                            }
                        }
                    }
                    keys.add(key);
                } else {
                    var lock = new ArrayList<>(List.of(0, 0, 0, 0, 0));
                    for (int i = 0; i < item.size(); i++) {
                        for (int j = 0; j < item.get(i).length(); j++) {
                            if (item.get(i).charAt(j) != '.') {
                                lock.set(j, i);
                            }
                        }
                    }
                    locks.add(lock);
                }
            }
            int sum = 0;
            for (var lock : locks) {
                for (var key : keys) {
                    boolean valid = true;
                    for (int i = 0; i < key.size(); i++) {
                        if (lock.get(i) + key.get(i) >= 6) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        sum++;
                    }
                }
            }

            return sum;
        }).subscribe().with(System.out::println);
    }

}
