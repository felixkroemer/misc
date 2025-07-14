import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12Part1 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        var dir = 90;
        int y = 0;
        int x = 0;

        for (var line : lines) {
            var d = line.substring(0, 1);
            var arg = Integer.parseInt(line.substring(1));

            switch (d) {
                case "N" -> {
                    y += arg;
                }
                case "E" -> {
                    x += arg;
                }
                case "S" -> {
                    y -= arg;
                }
                case "W" -> {
                    x -= arg;
                }
                case "L", "R" -> {
                    dir = Math.floorMod(d.equals("L") ? (dir - arg) : (dir + arg), 360);
                }
                case "F" -> {
                    switch (dir) {
                        case 0 -> {
                            y += arg;
                        }
                        case 90 -> {
                            x += arg;
                        }
                        case 180 -> {
                            y -= arg;
                        }
                        case 270 -> {
                            x -= arg;
                        }
                    }
                }
            }

        }
        System.out.println(Math.abs(x) + Math.abs(y));
    }


}

