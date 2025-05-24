import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day21Part1 {

    public static void main(String[] args) throws Exception {
        var a = new ArrayList<Integer>();
        a.add(10);
        var b = new ArrayList<Integer>();
        b.add(6);
        long aSum = 0;
        long bSum = 0;
        long rolls = 0;
        int dice = 0;
        while (true) {
            var sum = 0;
            sum += (dice % 100) + 1;
            dice++;
            sum += (dice % 100) + 1;
            dice++;
            sum += (dice % 100) + 1;
            dice++;
            sum = ((sum + a.getLast() - 1) % 10) + 1;
            aSum += sum;
            a.add(sum);
            rolls += 3;

            if (aSum >= 1000) {
                break;
            }

            sum = 0;
            sum += (dice % 100) + 1;
            dice++;
            sum += (dice % 100) + 1;
            dice++;
            sum += (dice % 100) + 1;
            dice++;
            sum = ((sum + b.getLast() - 1) % 10) + 1;
            bSum += sum;
            b.add(sum);
            rolls += 3;

            if (bSum >= 1000) {
                break;
            }

        }

        System.out.println(rolls * bSum);

    }
}
