import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day16Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var line = lines.getFirst();

        var binaryString = "";
        for (int i = 0; i < line.length(); i++) {
            var binSegment = Long.toBinaryString(Long.parseLong(line.substring(i, i + 1), 16));
            while (binSegment.length() < 4) {
                binSegment = "0" + binSegment;
            }
            binaryString += binSegment;
        }
        var ret = handle(0, binaryString);
        System.out.println(ret.getItem1());
    }

    static Tuple2<Long, Integer> handle(int start, String value) {
        var typeId = Integer.parseInt(value.substring(start + 3, start + 6), 2);
        if (typeId == 4) {
            int i = 0;
            var s = "";
            while (true) {
                var ident = Long.parseLong(value.substring(start + 6 + i * 5, start + 6 + i * 5 + 1), 2);
                s += value.substring(start + 7 + i * 5, start + 7 + i * 5 + 4);
                if (ident == 1L) {
                    i++;
                } else {
                    break;
                }
            }
            return Tuple2.of(Long.parseLong(s, 2), start + 6 + (i + 1) * 5);
        } else {
            List<Long> values = new ArrayList<>();
            var lengthTypeId = Long.parseLong(value.substring(start + 6, start + 7), 2);
            int end;
            if (lengthTypeId == 0) {
                var length = Integer.parseInt(value.substring(start + 7, start + 22), 2);
                int parsed = 0;
                while (parsed < length) {
                    var ret = handle(start + 22 + parsed, value);
                    values.add(ret.getItem1());
                    parsed += ret.getItem2() - (start + 22 + parsed);
                }
                end = start + 22 + length;
            } else {
                var number = Long.parseLong(value.substring(start + 7, start + 18), 2);
                int parsed = 0;
                for (int i = 0; i < number; i++) {
                    var ret = handle(start + 18 + parsed, value);
                    values.add(ret.getItem1());
                    parsed += ret.getItem2() - (start + 18 + parsed);
                }
                end = start + 18 + parsed;
            }
            long result = switch (typeId) {
                case 0 -> values.stream().reduce(0L, Long::sum);
                case 1 -> {
                    long prod = 1L;
                    for (var val : values) {
                        prod *= val;
                    }
                    yield prod;
                }
                case 2 -> values.stream().mapToLong(v -> v).min().getAsLong();
                case 3 -> values.stream().mapToLong(v -> v).max().getAsLong();
                case 5 -> (values.get(0) > values.get(1)) ? 1 : 0;
                case 6 -> (values.get(0) < values.get(1)) ? 1 : 0;
                case 7 -> (Objects.equals(values.get(0), values.get(1))) ? 1 : 0;
                default -> throw new IllegalStateException("Unexpected value: " + typeId);
            };
            return Tuple2.of(result, end);
        }
    }
}
