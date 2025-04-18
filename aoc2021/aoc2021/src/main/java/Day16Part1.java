import io.smallrye.mutiny.tuples.Tuple2;

import javax.management.MBeanTrustPermission;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day16Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        var line = lines.getFirst();

        var binaryString = "";
        for (int i = 0; i < line.length(); i++) {
            var binSegment = Integer.toBinaryString(Integer.parseInt(line.substring(i, i + 1), 16));
            while (binSegment.length() < 4) {
                binSegment = "0" + binSegment;
            }
            binaryString += binSegment;
        }
        var ret = handle(0, binaryString);
        System.out.println(ret.getItem1());
    }

    static Tuple2<Integer, Integer> handle(int start, String value) {
        var version = Integer.parseInt(value.substring(start, start + 3), 2);
        var typeId = Integer.parseInt(value.substring(start + 3, start + 6), 2);
        if (typeId == 4) {
            int i = 0;
            while (true) {
                var ident = Integer.parseInt(value.substring(start + 6 + i * 5, start + 6 + i * 5 + 1), 2);
                var val = Integer.parseInt(value.substring(start + 7 + i * 5, start + 7 + i * 5 + 4), 2);
                if (ident == 1) {
                    i++;
                } else {
                    break;
                }
            }
            return Tuple2.of(version, start + 6 + (i + 1) * 5);
        } else {
            var sum = version;
            var lengthTypeId = Integer.parseInt(value.substring(start + 6, start + 7), 2);
            if (lengthTypeId == 0) {
                var length = Integer.parseInt(value.substring(start + 7, start + 22), 2);
                int parsed = 0;
                while (parsed < length) {
                    var ret = handle(start + 22 + parsed, value);
                    sum += ret.getItem1();
                    parsed += ret.getItem2() - (start + 22 + parsed);
                }
                return Tuple2.of(sum, start + 22 + length);
            } else {
                var number = Integer.parseInt(value.substring(start + 7, start + 18), 2);
                int parsed = 0;
                for (int i = 0; i < number; i++) {
                    var ret = handle(start + 18 + parsed, value);
                    sum += ret.getItem1();
                    parsed += ret.getItem2() - (start + 18 + parsed);
                }
                return Tuple2.of(sum, start + 18 + parsed);
            }
        }
    }
}
