import io.smallrye.mutiny.Uni;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day17Part2 {

    public static void main(String[] args) throws Exception {
        System.out.println();
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            long num = 0;

            var program = Arrays.stream(lines.get(4).split(" ")[1].split(",")).map(Integer::parseInt).toList();

            p(0, program.size() - 1, program);
            return 0;
        }).subscribe().with(System.out::println);
    }


    static void p(long num, int i, List<Integer> program) {
        if (i == -1) {
            System.out.println(num);
            return;
        }
        num = num * 8;
        for (int c = 7; c >= 0; c--) {
            int b = program.get(i);
            if (c == (num + (b ^ 5 ^ c ^ 1) >> (b ^ 5 ^ c)) % 8) {
                b = b ^ c;
                b = b ^ 5;
                b = b ^ 1;
                p(num + b, i - 1, program);
            }
        }
    }
}

