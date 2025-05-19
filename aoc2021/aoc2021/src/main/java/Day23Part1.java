import io.smallrye.mutiny.tuples.Tuple3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day23Part1 {

    public static void main(String[] args) throws Exception {
        List<String> places = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            places.add("");
        }
        //List<String> holes = List.of("b1", "c1", "b2", "d1", "a1", "d2", "c2", "a2");
        List<String> holes = List.of("c1", "b1", "d1", "d2", "b2", "c2", "a1", "a2");
        Map<String, Long> cache = new HashMap<>();
        Map<String, Tuple3<Integer, Integer, Long>> dest = new HashMap<>();
        dest.put("a", Tuple3.of(0, 4, 1L));
        dest.put("b", Tuple3.of(1, 5, 10L));
        dest.put("c", Tuple3.of(2, 6, 100L));
        dest.put("d", Tuple3.of(3, 7, 1000L));
        System.out.println(solve(places, holes, 0, dest, cache));
    }

    static long solve(List<String> places, List<String> holes, long points, Map<String, Tuple3<Integer, Integer, Long>> dest, Map<String, Long> cache) {
        if (
                (holes.get(0).equals("a1") && holes.get(4).equals("a2") || holes.get(0).equals("a2") && holes.get(4).equals("a1")) &&
                        (holes.get(1).equals("b1") && holes.get(5).equals("b2") || holes.get(1).equals("b2") && holes.get(5).equals("b1")) &&
                        (holes.get(2).equals("c1") && holes.get(6).equals("c2") || holes.get(2).equals("c2") && holes.get(6).equals("c1")) &&
                        (holes.get(3).equals("d1") && holes.get(7).equals("d2") || holes.get(3).equals("d2") && holes.get(7).equals("d1"))) {
            return points;
        } else {
            var key = String.join(",", places) + "," + String.join(",", holes) + points;
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            var maxPoints = Long.MAX_VALUE;
            int i = -1;
            for (var hole : holes) {
                i++;
                if (i >= 4 && !holes.get(i - 4).isEmpty()) {
                    continue;
                }
                var idx = 2 * (i % 4) + 2;
                if (!hole.isEmpty()) {
                    var destination = dest.get(hole.substring(0, 1));
                    if (i < 4) {
                        if (destination.getItem1().equals(i)) {
                            if (holes.get(i + 4).substring(0, 1).equals(hole.substring(0, 1))) {
                                continue;
                            }
                        }
                    } else {
                        if (destination.getItem2().equals(i)) {
                            continue;
                        }
                    }
                    if (places.get(idx).isEmpty()) {
                        List<String> newPlaces;
                        List<String> newHoles;
                        long res;
                        var rIndex = idx + 1;
                        while (rIndex < 11 && places.get(rIndex).isEmpty()) {
                            newPlaces = new ArrayList<>(places);
                            newPlaces.set(rIndex, hole);
                            newHoles = new ArrayList<>(holes);
                            newHoles.set(i, "");
                            res = solve(newPlaces, newHoles, points + destination.getItem3() * ((i < 4 ? 1 : 2) + rIndex - idx), dest, cache);
                            if (res < maxPoints) {
                                maxPoints = res;
                            }
                            rIndex++;
                        }
                        var lIndex = idx - 1;
                        while (lIndex >= 0 && places.get(lIndex).isEmpty()) {
                            newPlaces = new ArrayList<>(places);
                            newPlaces.set(lIndex, hole);
                            newHoles = new ArrayList<>(holes);
                            newHoles.set(i, "");
                            res = solve(newPlaces, newHoles, points + destination.getItem3() * ((i < 4 ? 1 : 2) + idx - lIndex), dest, cache);
                            if (res < maxPoints) {
                                maxPoints = res;
                            }
                            lIndex--;
                        }
                    }
                }
            }
            i = -1;
            for (var place : places) {
                i++;
                if (!place.isEmpty()) {
                    var destination = dest.get(place.substring(0, 1));
                    var idx = 2 * (destination.getItem1() % 4) + 2;
                    var start = Math.min(idx, i + 1);
                    var end = Math.max(idx, i - 1);
                    var blocked = false;
                    for (int j = start; j <= end; j++) {
                        if (!places.get(j).isEmpty()) {
                            blocked = true;
                            break;
                        }
                    }
                    if (blocked) {
                        continue;
                    }
                    if (holes.get(destination.getItem1()).isEmpty() && holes.get(destination.getItem2()).isEmpty()) {
                        var newPlaces = new ArrayList<>(places);
                        newPlaces.set(i, "");
                        var newHoles = new ArrayList<>(holes);
                        newHoles.set(destination.getItem2(), place);
                        var res = solve(newPlaces, newHoles, points + destination.getItem3() * (Math.abs(idx - i) + 2), dest, cache);
                        if (res < maxPoints) {
                            maxPoints = res;
                        }
                    } else {
                        if (!holes.get(destination.getItem2()).substring(0, 1).equals(place.substring(0, 1))) {
                            continue;
                        }
                        var newPlaces = new ArrayList<>(places);
                        newPlaces.set(i, "");
                        var newHoles = new ArrayList<>(holes);
                        newHoles.set(destination.getItem1(), place);
                        var res = solve(newPlaces, newHoles, points + destination.getItem3() * (Math.abs(idx - i) + 1), dest, cache);
                        if (res < maxPoints) {
                            maxPoints = res;
                        }
                    }
                }
            }
            cache.put(key, maxPoints);
            return maxPoints;
        }
    }
}
