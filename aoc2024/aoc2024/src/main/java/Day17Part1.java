import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day17Part1 {

    public static void main(String[] args) throws Exception {
        System.out.println();
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            int regA = Integer.parseInt(lines.get(0).split(" ")[2]);
            int regB = Integer.parseInt(lines.get(1).split(" ")[2]);
            int regC = Integer.parseInt(lines.get(2).split(" ")[2]);
            var program = Arrays.stream(lines.get(4).split(" ")[1].split(",")).map(Integer::parseInt).toList();

            List<String> out = new ArrayList<>();

            int pc = 0;
            while (pc >= 0 && pc < program.size()) {
                int c = program.get(pc);
                switch (c) {
                    case 0 -> {
                        int op = switch (program.get(pc + 1)) {
                            case 4 -> regA;
                            case 5 -> regB;
                            case 6 -> regC;
                            default -> program.get(pc + 1);
                        };
                        regA = (int) (regA / Math.pow(2, op));
                        pc += 2;
                    }
                    case 1 -> {
                        regB = regB ^ program.get(pc + 1);
                        pc += 2;
                    }
                    case 2 -> {
                        int op = switch (program.get(pc + 1)) {
                            case 4 -> regA;
                            case 5 -> regB;
                            case 6 -> regC;
                            default -> program.get(pc + 1);
                        };
                        regB = op % 8;
                        pc += 2;
                    }
                    case 3 -> {
                        if (regA != 0) {
                            pc = program.get(pc + 1);
                        } else {
                            pc += 2;
                        }
                    }
                    case 4 -> {
                        regB = regB ^ regC;
                        pc += 2;
                    }
                    case 5 -> {
                        int op = switch (program.get(pc + 1)) {
                            case 4 -> regA;
                            case 5 -> regB;
                            case 6 -> regC;
                            default -> program.get(pc + 1);
                        };
                        out.add("" + (op % 8));
                        pc += 2;
                    }
                    case 6 -> {
                        int op = switch (program.get(pc + 1)) {
                            case 4 -> regA;
                            case 5 -> regB;
                            case 6 -> regC;
                            default -> program.get(pc + 1);
                        };
                        regB = (int) (regA / Math.pow(2, op));
                        pc += 2;
                    }
                    case 7 -> {
                        int op = switch (program.get(pc + 1)) {
                            case 4 -> regA;
                            case 5 -> regB;
                            case 6 -> regC;
                            default -> program.get(pc + 1);
                        };
                        regC = (int) (regA / Math.pow(2, op));
                        pc += 2;
                    }
                    default -> {
                        throw new RuntimeException();
                    }
                }
            }
            return String.join(",", out);
        }).subscribe().with(System.out::println);
    }
}
