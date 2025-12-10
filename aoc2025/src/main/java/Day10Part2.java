import com.google.ortools.Loader;
import com.google.ortools.sat.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;


public class Day10Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        long sum = 0;
        for (var line : lines) {
            var parts = List.of(line.split(" "));
            var config = Arrays.stream(parts.getLast().substring(1, parts.getLast().length() - 1).split(",")).map(Long::valueOf).toList();
            var buttons = parts.subList(1, parts.size() - 1).stream().map(s -> Arrays.stream(s.substring(1, s.length() - 1).split(",")).map(Integer::parseInt).toList()).toList();

            Loader.loadNativeLibraries();
            CpModel model = new CpModel();

            long[][] A = new long[config.size()][buttons.size()];
            for (int i = 0; i < config.size(); i++) {
                for (int j = 0; j < buttons.size(); j++) {
                    A[i][j] = buttons.get(j).contains(i) ? 1 : 0;
                }
            }
            long[] b = new long[config.size()];
            for (int i = 0; i < config.size(); i++) {
                b[i] = config.get(i);
            }

            int numButtons = A[0].length;

            IntVar[] x = new IntVar[numButtons];
            for (int j = 0; j < numButtons; j++) {
                x[j] = model.newIntVar(0, 10000, "x" + j);
            }

            for (int i = 0; i < A.length; i++) {
                model.addEquality(
                        LinearExpr.weightedSum(x, A[i]),
                        b[i]
                );
            }
            model.minimize(LinearExpr.sum(x));

            CpSolver solver = new CpSolver();
            solver.solve(model);

            long presses = 0;
            for (int j = 0; j < numButtons; j++) {
                presses += solver.value(x[j]);
            }
            sum += presses;
        }
        System.out.println(sum);
    }
}
