import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day22Part1 {

    static HashMap<String, Tuple2<Integer, Integer>> coordsB = new HashMap<>();

    static long MOD = 16777216;

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            long sum = 0;
            for (var line : lines) {
                var num = Long.parseLong(line);
                for (int i = 0; i < 2000; i++) {
                    num = calculate(num);
                }
                sum += num;
            }
            return sum;
        }).subscribe().with(System.out::println);
    }

    static long calculate(long secretNumber) {
        long m = secretNumber << 6;
        secretNumber = (secretNumber ^ m) % MOD;
        long d = secretNumber >> 5;
        secretNumber = (secretNumber ^ d) % MOD;
        long mm = secretNumber << 11;
        secretNumber = (secretNumber ^ mm) % MOD;
        return secretNumber;
    }

}
