import io.smallrye.mutiny.tuples.Tuple3;

import java.util.*;


public class Day23Part2 {

    enum Type {
        A, B, C, D, E;
    }

    record Pod(Type type) {
    }

    static int HOLES = 16;

    public static void main(String[] args) throws Exception {
        List<Pod> places = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            places.add(new Pod(Type.E));
        }
        List<Pod> holes = //List.of(new Pod(Type.B), new Pod(Type.C), new Pod(Type.B), new Pod(Type.D),
                List.of(new Pod(Type.C), new Pod(Type.B), new Pod(Type.D), new Pod(Type.D),
                        new Pod(Type.D), new Pod(Type.C), new Pod(Type.B), new Pod(Type.A),
                        new Pod(Type.D), new Pod(Type.B), new Pod(Type.A), new Pod(Type.C),
                        //new Pod(Type.A), new Pod(Type.D), new Pod(Type.C), new Pod(Type.A));
                        new Pod(Type.B), new Pod(Type.C), new Pod(Type.A), new Pod(Type.A));
        //List<Pod> holes = List.of(new Pod(Type.C), new Pod(Type.B), new Pod(Type.D), new Pod(Type.D), new Pod(Type.B), new Pod(Type.C), new Pod(Type.A), new Pod(Type.A));
        Map<String, Long> cache = new HashMap<>();
        Map<Type, Tuple3<Integer, Integer, Long>> dest = new HashMap<>();
        dest.put(Type.A, Tuple3.of(0, 4, 1L));
        dest.put(Type.B, Tuple3.of(1, 5, 10L));
        dest.put(Type.C, Tuple3.of(2, 6, 100L));
        dest.put(Type.D, Tuple3.of(3, 7, 1000L));
        System.out.println(solve(places, holes, dest, 0, cache));
    }

    static long solve(List<Pod> places, List<Pod> holes, Map<Type, Tuple3<Integer, Integer, Long>> dest, long points, Map<String, Long> cache) {
        if (holes.stream().allMatch(Objects::nonNull) &&
                holes.get(0).type == Type.A
                && holes.get(4).type == Type.A
                && holes.get(8).type == Type.A
                && holes.get(12).type == Type.A
                && holes.get(1).type == Type.B
                && holes.get(5).type == Type.B
                && holes.get(9).type == Type.B
                && holes.get(13).type == Type.B
                && holes.get(2).type == Type.C
                && holes.get(6).type == Type.C
                && holes.get(10).type == Type.C
                && holes.get(14).type == Type.C
                && holes.get(3).type == Type.D
                && holes.get(7).type == Type.D
                && holes.get(11).type == Type.D
                && holes.get(15).type == Type.D
        ) {
            return 0;
        } else {
            StringBuilder sb = new StringBuilder();
            for (var place : places) {
                sb.append(place.type);
            }
            for (var hole : holes) {
                sb.append(hole.type);
            }
            var key = sb.toString();
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            long additionalPoints = 1000000;
            long usedPoints = 1000000;
            int i = -1;
            for (var hole : holes) {
                i++;
                if (hole.type == Type.E) {
                    continue;
                }
                int k = i - 4;
                boolean blocked = false;
                while (k >= 0) {
                    if (holes.get(k).type != Type.E) {
                        blocked = true;
                        break;
                    }
                    k -= 4;
                }
                if (blocked) {
                    continue;
                }
                var destination = dest.get(hole.type);
                if (destination.getItem1().equals(i % 4)) {
                    boolean canStay = true;
                    int l = i + 4;
                    while (l < HOLES) {
                        if (holes.get(l).type != hole.type) {
                            canStay = false;
                            break;
                        }
                        l += 4;
                    }
                    if (canStay) {
                        continue;
                    }
                }
                var idx = 2 * (i % 4) + 2;
                if (places.get(idx).type == Type.E) {
                    List<Pod> newPlaces;
                    List<Pod> newHoles;
                    long res;
                    var rIndex = idx + 1;
                    while (rIndex < 11 && places.get(rIndex).type == Type.E) {
                        if (rIndex == 2 || rIndex == 4 || rIndex == 6 || rIndex == 8) {
                            rIndex++;
                            continue;
                        }
                        newPlaces = new ArrayList<>(places);
                        newPlaces.set(rIndex, hole);
                        newHoles = new ArrayList<>(holes);
                        newHoles.set(i, new Pod(Type.E));
                        long p = destination.getItem3() * (((i / 4) + 1) + rIndex - idx);
                        res = solve(newPlaces, newHoles, dest, points + p, cache);
                        if (res + p < additionalPoints + usedPoints) {
                            additionalPoints = res;
                            usedPoints = p;
                        }
                        rIndex++;
                    }
                    var lIndex = idx - 1;
                    while (lIndex >= 0 && places.get(lIndex).type == Type.E) {
                        if (lIndex == 2 || lIndex == 4 || lIndex == 6 || lIndex == 8) {
                            lIndex--;
                            continue;
                        }
                        newPlaces = new ArrayList<>(places);
                        newPlaces.set(lIndex, hole);
                        newHoles = new ArrayList<>(holes);
                        newHoles.set(i, new Pod(Type.E));
                        long p = destination.getItem3() * (((i / 4) + 1) + idx - lIndex);
                        res = solve(newPlaces, newHoles, dest, points + p, cache);
                        if (res + p < additionalPoints + usedPoints) {
                            additionalPoints = res;
                            usedPoints = p;
                        }
                        lIndex--;
                    }
                }
            }
            i = -1;
            for (var place : places) {
                i++;
                if (place.type == Type.E) {
                    continue;
                }
                var destination = dest.get(place.type);
                var idx = 2 * (destination.getItem1() % 4) + 2;
                var start = Math.min(idx, i + 1);
                var end = Math.max(idx, i - 1);
                var blocked = false;
                for (int j = start; j <= end; j++) {
                    if (places.get(j).type != Type.E) {
                        blocked = true;
                        break;
                    }
                }
                if (blocked) {
                    continue;
                }

                boolean mismatchedType = false;
                int l = destination.getItem1();
                while (l < HOLES) {
                    if (holes.get(l).type != Type.E && holes.get(l).type != place.type) {
                        mismatchedType = true;
                        break;
                    }
                    l += 4;
                }
                if (mismatchedType) {
                    continue;
                }

                int m = destination.getItem1();
                int n = -1;
                while (m < HOLES && holes.get(m).type == Type.E) {
                    n = m;
                    m += 4;
                }

                var newPlaces = new ArrayList<>(places);
                newPlaces.set(i, new Pod(Type.E));
                var newHoles = new ArrayList<>(holes);
                newHoles.set(n, place);
                long p = destination.getItem3() * (Math.abs(idx - i) + ((n / 4) + 1));
                var res = solve(newPlaces, newHoles, dest, points + p, cache);
                if (res + p < additionalPoints + usedPoints) {
                    additionalPoints = res;
                    usedPoints = p;
                }

            }
            cache.put(key, additionalPoints + usedPoints);
            return additionalPoints + usedPoints;
        }
    }
}
