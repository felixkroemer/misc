import io.smallrye.mutiny.tuples.Tuple3;

import java.util.*;


public class Day23Part1 {

    enum Type {
        A, B, C, D;
    }

    record Pod(Type type) {
    }

    record Key(List<Pod> places, List<Pod> holes, long score) {
    }


    public static void main(String[] args) throws Exception {
        List<Pod> places = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            places.add(null);
        }
        List<Pod> holes = List.of(new Pod(Type.B), new Pod(Type.C), new Pod(Type.B), new Pod(Type.D), new Pod(Type.A), new Pod(Type.D), new Pod(Type.C), new Pod(Type.A));
        //List<Pod> holes = List.of(new Pod(Type.C), new Pod(Type.B), new Pod(Type.D), new Pod(Type.D), new Pod(Type.B), new Pod(Type.C), new Pod(Type.A), new Pod(Type.A));
        Map<Key, Long> cache = new HashMap<>();
        Map<Type, Tuple3<Integer, Integer, Long>> dest = new HashMap<>();
        dest.put(Type.A, Tuple3.of(0, 4, 1L));
        dest.put(Type.B, Tuple3.of(1, 5, 10L));
        dest.put(Type.C, Tuple3.of(2, 6, 100L));
        dest.put(Type.D, Tuple3.of(3, 7, 1000L));
        System.out.println(solve(places, holes, 0, dest, cache));
    }

    static long solve(List<Pod> places, List<Pod> holes, long points, Map<Type, Tuple3<Integer, Integer, Long>> dest, Map<Key, Long> cache) {
        if (holes.stream().allMatch(Objects::nonNull) &&
                holes.get(0).type == Type.A && holes.get(4).type == Type.A &&
                holes.get(1).type == Type.B && holes.get(5).type == Type.B &&
                holes.get(2).type == Type.C && holes.get(6).type == Type.C &&
                holes.get(3).type == Type.D && holes.get(7).type == Type.D

        ) {
            return points;
        } else {
            var key = new Key(Collections.unmodifiableList(places), Collections.unmodifiableList(holes), points);
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            var maxPoints = Long.MAX_VALUE;
            int i = -1;
            for (var hole : holes) {
                i++;
                if (i >= 4 && holes.get(i - 4) != null) {
                    continue;
                }
                var idx = 2 * (i % 4) + 2;
                if (hole != null) {
                    var destination = dest.get(hole.type);
                    if (i < 4) {
                        if (destination.getItem1().equals(i)) {
                            if (holes.get(i + 4).type == hole.type) {
                                continue;
                            }
                        }
                    } else {
                        if (destination.getItem2().equals(i)) {
                            continue;
                        }
                    }
                    if (places.get(idx) == null) {
                        List<Pod> newPlaces;
                        List<Pod> newHoles;
                        long res;
                        var rIndex = idx + 1;
                        while (rIndex < 11 && places.get(rIndex) == null) {
                            newPlaces = new ArrayList<>(places);
                            newPlaces.set(rIndex, hole);
                            newHoles = new ArrayList<>(holes);
                            newHoles.set(i, null);
                            res = solve(newPlaces, newHoles, points + destination.getItem3() * ((i < 4 ? 1 : 2) + rIndex - idx), dest, cache);
                            if (res < maxPoints) {
                                maxPoints = res;
                            }
                            rIndex++;
                        }
                        var lIndex = idx - 1;
                        while (lIndex >= 0 && places.get(lIndex) == null) {
                            newPlaces = new ArrayList<>(places);
                            newPlaces.set(lIndex, hole);
                            newHoles = new ArrayList<>(holes);
                            newHoles.set(i, null);
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
                if (place != null) {
                    var destination = dest.get(place.type);
                    var idx = 2 * (destination.getItem1() % 4) + 2;
                    var start = Math.min(idx, i + 1);
                    var end = Math.max(idx, i - 1);
                    var blocked = false;
                    for (int j = start; j <= end; j++) {
                        if (places.get(j) != null) {
                            blocked = true;
                            break;
                        }
                    }
                    if (blocked) {
                        continue;
                    }
                    if (holes.get(destination.getItem1()) == null && holes.get(destination.getItem2()) == null) {
                        var newPlaces = new ArrayList<>(places);
                        newPlaces.set(i, null);
                        var newHoles = new ArrayList<>(holes);
                        newHoles.set(destination.getItem2(), place);
                        var res = solve(newPlaces, newHoles, points + destination.getItem3() * (Math.abs(idx - i) + 2), dest, cache);
                        if (res < maxPoints) {
                            maxPoints = res;
                        }
                    } else {
                        if (holes.get(destination.getItem2()).type != place.type) {
                            continue;
                        }
                        var newPlaces = new ArrayList<>(places);
                        newPlaces.set(i, null);
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
