import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day4Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        Map<String, String> fields = new HashMap<>();
        int sum = 0;

        for (var line : lines) {
            if (line.isBlank()) {
                try {
                    int byr = Integer.parseInt(fields.get("byr"));
                    if (byr < 1920 || byr > 2002) {
                        continue;
                    }

                    int iyr = Integer.parseInt(fields.get("iyr"));
                    if (iyr < 2010 || iyr > 2020) {
                        continue;
                    }

                    int eyr = Integer.parseInt(fields.get("eyr"));
                    if (eyr < 2020 || eyr > 2030) {
                        continue;
                    }

                    String hgt = fields.get("hgt");
                    var hgtVal = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
                    if (hgt.endsWith("cm")) {
                        if (hgtVal < 150 || hgtVal > 193) {
                            continue;
                        }
                    } else if (hgt.endsWith("in")) {
                        if (hgtVal < 59 || hgtVal > 76) {
                            continue;
                        }
                    } else {
                        continue;
                    }

                    var hcl = fields.get("hcl");
                    if (!hcl.matches("#[0-9a-f]{6}")) {
                        continue;
                    }

                    var ecl = fields.get("ecl");
                    var eclVariants = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
                    if (!eclVariants.contains(ecl)) {
                        continue;
                    }

                    var pid = fields.get("pid");
                    if (pid.length() != 9) {
                        continue;
                    }
                    Long.parseLong(pid);

                    sum++;
                } catch (Exception e) {
                } finally {
                    fields.clear();
                }
                continue;
            }
            String[] parts = line.split(" ");
            for (var part : parts) {
                var field = part.split(":");
                fields.put(field[0], field[1]);
            }
        }


        System.out.println(sum);
    }

}
