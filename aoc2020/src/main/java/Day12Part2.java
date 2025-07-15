import java.nio.file.Files;
import java.nio.file.Path;

public class Day12Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));


        int sy = 0;
        int sx = 0;
        int wy = 1;
        int wx = 10;

        for (var line : lines) {
            var d = line.substring(0, 1);
            var arg = Integer.parseInt(line.substring(1));

            switch (d) {
                case "N" -> {
                    wy += arg;
                }
                case "E" -> {
                    wx += arg;
                }
                case "S" -> {
                    wy -= arg;
                }
                case "W" -> {
                    wx -= arg;
                }
                case "L", "R" -> {
                    if(arg == 180) {
                        wy = -wy;
                        wx = -wx;
                    } else if (d.equals("L") && arg == 90 || d.equals("R") && arg == 270) {
                        var tmp = wx;
                        wx = -wy;
                        wy = tmp;
                    } else if ((d.equals("R") && arg == 90 || d.equals("L") && arg == 270)) {
                        var tmp = wx;
                        wx = wy;
                        wy = -tmp;
                    }
                }
                case "F" -> {
                    sx += wx * arg;
                    sy += wy * arg;
                }
            }

        }
        System.out.println(Math.abs(sx) + Math.abs(sy));
    }


}

