import java.util.*;


public class Day21Part2 {

    public static void main(String[] args) throws Exception {
        var values = new ArrayList<Integer>();
        for (int i = 0; i < 27; i++) {
            var a = (i % 3) + 1;
            var b = ((i / 3) % 3) + 1;
            var c = (i / 9) + 1;
            values.add(a + b + c);
        }
        var freq = new HashMap<Integer, Integer>();
        for (var value : values) {
            if (!freq.containsKey(value)) {
                freq.put(value, 0);
            }
            freq.put(value, freq.get(value) + 1);
        }
        var distinctValues = new HashSet<>(values);

        var path = new ArrayList<Integer>();
        long sum = getPaths(distinctValues, 10, 6, path, 0, 0, true, freq);
        System.out.println(sum);
    }

    static long getPaths(Set<Integer> distinctValues, int posA, int posB, List<Integer> path, int sumA, int sumB, boolean aTurn, Map<Integer, Integer> freq) {
        if (sumA >= 21) { // 306719685234774
            return 0;
        } else if (sumB >= 21) { // 145727874122839
            long prod = 1;
            for (var e : path) {
                prod *= freq.get(e);
            }
            return prod;
        } else {
            long sum = 0;
            for (var value : distinctValues) {
                if (aTurn) {
                    var newPos = ((posA - 1 + value) % 10) + 1;
                    path.add(value);
                    sum += getPaths(distinctValues, newPos, posB, path, sumA + newPos, sumB, !aTurn, freq);
                } else {
                    var newPos = ((posB - 1 + value) % 10) + 1;
                    path.add(value);
                    sum += getPaths(distinctValues, posA, newPos, path, sumA, sumB + newPos, !aTurn, freq);
                }
                path.removeLast();
            }
            return sum;
        }
    }
}
